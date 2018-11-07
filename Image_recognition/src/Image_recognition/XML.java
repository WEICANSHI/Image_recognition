package Image_recognition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


public class XML {
	
	public static String Key;
	public static String XMLDir;
	public static String ZipDir;
	public static String version;
	public static String imageDir;
	public static String classifyRecord;
	public static String unclassifyRecord;
	public static String currentClassifier;
	
	/**
	 * Load the classification to the XML
	 * @param id: id of a classifier, assume to be new every time
	 * @param images: Class_name : List of images
	 * @throws Exception
	 */
	public static void classifyRecord(String id, Map<String, List<String> > images) {
		DocumentBuilder builder = null;
		Document doc = null;
		Element rootElement = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(XMLDir + classifyRecord));
			rootElement = doc.getDocumentElement();
		}catch(FileNotFoundException e) {
			doc = builder.newDocument();
			rootElement = doc.createElement("IMAGES");
			doc.appendChild(rootElement);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		// add id element at the front
		Element classElement = doc.createElement("Classifier");
		Element idElement = doc.createElement("id");
		Text textid = doc.createTextNode(id);
		idElement.appendChild(textid);
		classElement.appendChild(idElement);
		
		// add all the tag parallel to the idElement in classifier
		Iterator<Map.Entry<String, List<String> > > itr = images.entrySet().iterator();
		while(itr.hasNext()) {
			// Key: class name, values: images
			Map.Entry<String, List<String> > tmp = itr.next();
			for(int i = 0; i < tmp.getValue().size(); i++) {
				Element element = doc.createElement("Image");
				Element name = doc.createElement("name");
				Element c = doc.createElement("class");
				Text textname = doc.createTextNode(tmp.getValue().get(i));
				Text textclass = doc.createTextNode(tmp.getKey());
				name.appendChild(textname);
				c.appendChild(textclass);
				element.appendChild(name);
				element.appendChild(c);
				classElement.appendChild(element);
			}
		}
		
		rootElement.appendChild(classElement);
		
		try {
			// transform in to XML file
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			t.transform(new DOMSource(doc), new StreamResult(new File(XMLDir + classifyRecord)));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void unclassifyRecord(String filname, String CLASS) {
		DocumentBuilder builder = null;
		Document doc = null;
		Element rootElement = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(XMLDir + unclassifyRecord));
			rootElement = doc.getDocumentElement();
		}catch(FileNotFoundException e) {
			doc = builder.newDocument();
			rootElement = doc.createElement("IMAGES");
			doc.appendChild(rootElement);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		Element element = doc.createElement("Image");
		Element name = doc.createElement("name");
		Element c = doc.createElement("class");
		Text textname = doc.createTextNode(filname);
		Text textclass = doc.createTextNode(CLASS);
		System.out.println();
		name.appendChild(textname);
		c.appendChild(textclass);
		element.appendChild(name);
		element.appendChild(c);
		
		rootElement.appendChild(element);
		
		try {
			// transform in to XML file
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			t.transform(new DOMSource(doc), new StreamResult(new File(XMLDir + unclassifyRecord)));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void InitSetting(){
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File("./XML/setting.xml"));
			Element rootElement = doc.getDocumentElement();
			// assert the setting is correct
			assert(rootElement.getTagName().equals("setting"));
			
			// loop through all the setting
			NodeList children = rootElement.getChildNodes();
			for(int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if(child instanceof Element) {
					Element childElement = (Element) child;
					Text textNode = (Text) childElement.getFirstChild();
					String text = textNode.getData().trim();
					if(childElement.getTagName().equals("current_classifier"))
						currentClassifier = text;
					else if(childElement.getTagName().equals("ImageDir"))
						imageDir = text;
					else if(childElement.getTagName().equals("XMLDir"))
						XMLDir = text;
					else if(childElement.getTagName().equals("classifyRecord"))
						classifyRecord = text;
					else if(childElement.getTagName().equals("ZipDir"))
						ZipDir = text;
					else if(childElement.getTagName().equals("Key"))
						Key = text;
					else if(childElement.getTagName().equals("Server_version"))
						version = text;
					else if(childElement.getTagName().equals("unclassifyRecord"))
						unclassifyRecord = text;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) throws Exception {
		XML.InitSetting();
//		String type1[] = {"t10", "t11", "t12", "t13"};
//		String type2[] = {"t20", "t21", "t22"};
//		String type3[] = {"t30"};
//		Map<String, List<String> > record = new HashMap<>();
//		record.put("type1", Arrays.asList(type1));
//		record.put("type2", Arrays.asList(type2));
//		record.put("type3", Arrays.asList(type3));
//		
//		try {
//			XML.classifyRecord("test1111", record);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
	}
}
