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


public class XML2 {
	
	public static String Key;
	public static String XMLDir;
	public static String ZipDir;
	public static String version;
	public static String imageDir;
	public static String classifyRecord;
	public static String currentClassifier;
	
	/**
	 * Load the classification to the XML
	 * @param id: id of a classifier, assume to be new every time
	 * @param image_name: name of image
	 * @param class_name: name of the class
	 * @throws Exception
	 */
	public static void classifyRecord(String id, String image_name, String class_name) {
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
		//Iterator<Map.Entry<String, List<String> > > itr = images.entrySet().iterator();
		//while(itr.hasNext()) {
			// Key: class name, values: images
			//Map.Entry<String, List<String> > tmp = itr.next();
			//for(int i = 0; i < tmp.getValue().size(); i++) {
				Element element = doc.createElement("Image");
				Element name = doc.createElement("name");
				Element c = doc.createElement("class");
				Text textname = doc.createTextNode(image_name);
				Text textclass = doc.createTextNode(class_name);
				name.appendChild(textname);
				c.appendChild(textclass);
				element.appendChild(name);
				element.appendChild(c);
				classElement.appendChild(element);
			//}
		//}
		
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
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
