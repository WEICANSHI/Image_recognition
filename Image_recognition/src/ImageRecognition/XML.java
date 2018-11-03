package ImageRecognition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;;

public class XML {
	public static String imageDir = "./Image/";
	public static String XMLDir = "./XML/";
	public static String classifyRecord = "classRecord.xml";
	
	public static void classifyRecord(String Class, List<String> images) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = null;
		Element rootElement = null;
		boolean appendRoot = false;
		try {
			doc = builder.parse(new File(XMLDir + classifyRecord));
			rootElement = doc.getDocumentElement();
		}catch(FileNotFoundException e) {
			doc = builder.newDocument();
			rootElement = doc.createElement("IMAGES");
			appendRoot = true;
		}
		
		for(int i = 0; i < images.size(); i++) {
			Element element = doc.createElement("Image");
			Element name = doc.createElement("name");
			Element c = doc.createElement("class");
			Text textname = doc.createTextNode(images.get(i));
			Text textclass = doc.createTextNode(Class);
			name.appendChild(textname);
			c.appendChild(textclass);
			element.appendChild(name);
			element.appendChild(c);
			rootElement.appendChild(element);
		}
		
		if(appendRoot) doc.appendChild(rootElement);
		
		
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		t.transform(new DOMSource(doc), new StreamResult(new File(XMLDir + classifyRecord)));
	}
	
	public static Map<String, List<String> > load(){
		Map<String, List<String> > record = new HashMap<>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(XMLDir + classifyRecord));
			Element root = doc.getDocumentElement();
			
			NodeList children = root.getChildNodes();
			
			for(int i = 0; i < children.getLength(); i ++) {
				String name = "";
				String C = "";
				Node child = children.item(i);
				if(child instanceof Element) {
					Element childElement = (Element) child;
					NodeList subchildren = childElement.getChildNodes();
					for(int j = 0; j < subchildren.getLength(); j ++) {
						Node subchild = subchildren.item(j);
						if(subchild instanceof Element) {
							Element subchildElement = (Element) subchild;
							Text textNode = (Text) subchildElement.getLastChild();
							String text = textNode.getData().trim();
							if(subchildElement.getTagName().equals("name")) {
								name = text;
							}else {
								C = text;
							}
						}
					}
					if(record.containsKey(C.toString())) {
						record.get(C).add(name);
					}else {
						record.put(C, new ArrayList<String> (Arrays.asList(name)));
					}
				}
			}
				//NodeList subchild = child.getChildNodes();
//				Text textname = (Text) subchild.item(0).getFirstChild();
//				Text C = (Text) subchild.item(1).getFirstChild();
//				
//				if(record.containsKey(C.toString())) {
//					record.get(C.toString()).add(textname.toString());
//				}else {
//					record.put(C.toString(), Arrays.asList(textname.toString()));
//				}
				
			
			return record;
		}catch(IOException | ParserConfigurationException | SAXException e) {
			return null;
		}
	}
	
	public static void createXML(String jason) throws Exception {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element rootElement = doc.createElement("OutputStream");
		Integer check = XML.parser(jason, rootElement, doc, 0);
		doc.appendChild(rootElement);
		assert(check == -1);
		
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		t.transform(new DOMSource(doc), new StreamResult(new File("./XML/test01.xml")));
		
		DOMImplementation impl = doc.getImplementation();
		DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
		LSSerializer ser = implLS.createLSSerializer();
		ser.getDomConfig().setParameter("format-pretty-print", true);
		String str = ser.writeToString(rootElement);
		System.out.println(str);
		
	}
	
	private static Integer parser(String jason, Element rootElement, Document doc, int layer) {
		Stack<Character> parentheses = new Stack<Character>();
		int i = 0;
		while(i < jason.length()) {
			if(jason.charAt(i) == '{') {
				//System.out.println();
				System.out.println("{{{{{{{{{{{{{{{{{{{{{{{");
				parentheses.push('{');
			}
			
			else if(jason.charAt(i) == '[') {
				System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");
				System.out.println("layer : " + layer);
				parentheses.push('[');
				i = i + 1;
				while(i < jason.length() && jason.charAt(i) != ']') {
//					if(jason.charAt(i) == ' ') {
//						i += 1;
//						continue;
//					}
					System.out.println("layer : " + layer);
					System.out.println("********************************");
					System.out.println(jason.substring(i));
					String title = rootElement.getNodeName();
					title += "_list";
					Element element = doc.createElement(title);
					
					int skips = XML.parser(jason.substring(i), element, doc, layer + 1);
					if(element.hasChildNodes()) 
						rootElement.appendChild(element);
					i += skips;
				}
				assert(jason.charAt(i) == ']');
				parentheses.pop();
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				return i + 1;
			}
			
			else if(jason.charAt(i) == '\"') {
				int next = jason.indexOf('\"', i + 1);	// next parentheses
				String title = jason.substring(i + 1, next);	// title of row
				int colon = jason.indexOf(":", next + 1);	// index of colon ':'
				int comma = jason.indexOf(",", next + 1);	// index of comma ','
				
				// if ':' before ',', the title is a title 
				if(colon < comma || (comma == -1 && colon != -1)) {
					i = colon;
					System.out.println("layer : " + layer);
					System.out.println("+++++++++++++++++++++++++");
					System.out.println(title);
					System.out.println(jason);
					
					Element element = doc.createElement(title);
					int skips = XML.parser(jason.substring(colon + 1), element, doc, layer + 1);
					rootElement.appendChild(element);
					i +=  skips;
				}
				// if ',' before ':' , the title is a value
				else {
					System.out.println("layer : " + layer);
					System.out.println("------------------------");
					System.out.println(title);
					System.out.println(jason);
					
					i = next + 1;
					Text textNode = doc.createTextNode(title);
					rootElement.appendChild(textNode);
					return i;
				}
				
			}
			
			else if(Character.isDigit(jason.charAt(i))) {
				int tmp = i;
				while(Character.isDigit(jason.charAt(tmp)) || jason.charAt(tmp) == '.') {
					tmp ++;
				}
				String num = jason.substring(i, tmp);
				Text textNode = doc.createTextNode(num);
				rootElement.appendChild(textNode);
				return tmp;
			}
			
			else if(jason.charAt(i) == 't' || jason.charAt(i) == 'f') {
				String response = "";
				int length = 0;
				if(jason.charAt(i) == 't') {
					assert(jason.substring(i, i + 3).toLowerCase().equals("true"));
					response = "true";
					length = 4;
				}else {
					assert(jason.substring(i, i + 4).toLowerCase().equals("false"));
					response = "false";
					length = 5;
				}
				
				Text textNode = doc.createTextNode(response);
				rootElement.appendChild(textNode);
				return length;
			}
			
			else if(jason.charAt(i) == '}') {
				if(parentheses.peek() == '{')
					System.out.println("}}}}}}layer: " + layer);
					//parentheses.pop();
					//if(i + 1 >= jason.length() || jason.charAt(i + 1) != ',')
				//System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
					return i + 1;
				//}
			}
			
			else if(jason.charAt(i) == ']') {
				return i;
			}
			
			i += 1;
		}
		
		return -1;
	}

	
	
	public static void main(String args[]) {
		String type1[] = {"t10", "t11", "t12", "t13"};
		String type2[] = {"t20", "t21", "t22"};
		String type3[] = {"t30"};
		
		try {
			XML.classifyRecord("type1", Arrays.asList(type1));
			XML.classifyRecord("type2", Arrays.asList(type2));
			XML.classifyRecord("type3", Arrays.asList(type3));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		Map<String, List<String> > record = XML.load();
		Iterator<Map.Entry<String, List<String>> > itr = record.entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry<String, List<String>> tmp = itr.next();
			System.out.println(tmp.getKey() + "==================");
			for(int i = 0; i < tmp.getValue().size(); i++) {
				System.out.println(tmp.getValue().get(i));
			}
		}
		
//		String input = "{\n" + 
//				"  \"classifiers\": [\n" + 
//				"    {\n" + 
//				"      \"classifier_id\": \"dogs_1818370440\",\n" + 
//				"      \"name\": \"dogs\",\n" + 
//				"      \"owner\": \"fcc1e103-8d03-43c8-bf31-b72a276acee3\",\n" + 
//				"      \"status\": \"ready\",\n" + 
//				"      \"core_ml_enabled\": true,\n" + 
//				"      \"created\": \"2018-10-28T14:43:33.519\",\n" + 
//				"      \"classes\": [\n" + 
//				"        {\n" + 
//				"          \"class\": \"goldenretriever\"\n" + 
//				"        },\n" + 
//				"        {\n" + 
//				"          \"class\": \"husky\"\n" + 
//				"        },\n" + 
//				"        {\n" + 
//				"          \"class\": \"beagle\"\n" + 
//				"        }\n" + 
//				"      ],\n" + 
//				"      \"updated\": \"2018-10-28T14:43:33.519\"\n" + 
//				"    },\n" + 
//				"    {\n" + 
//				"      \"classifier_id\": \"newly_dogs_1669084498\",\n" + 
//				"      \"name\": \"newly_dogs\",\n" + 
//				"      \"owner\": \"fcc1e103-8d03-43c8-bf31-b72a276acee3\",\n" + 
//				"      \"status\": \"training\",\n" + 
//				"      \"core_ml_enabled\": true,\n" + 
//				"      \"created\": \"2018-11-02T20:19:35.801\",\n" + 
//				"      \"classes\": [\n" + 
//				"        {\n" + 
//				"          \"class\": \"goldenretriever\"\n" + 
//				"        },\n" + 
//				"        {\n" + 
//				"          \"class\": \"beagle\"\n" + 
//				"        },\n" + 
//				"        {\n" + 
//				"          \"class\": \"husky\"\n" + 
//				"        }\n" + 
//				"      ],\n" + 
//				"      \"updated\": \"2018-11-02T20:19:35.801\"\n" + 
//				"    }\n" + 
//				"  ]\n" + 
//				"}\n";
		
//		String input = "{\n" + 
//				"  \"classifiers\": [\n" + 
//				"    {\n" + 
//				"      \"classifier_id\": \"dogs_1818370440\",\n" + 
//				"      \"name\": \"dogs\",\n" + 
//				"      \"owner\": \"fcc1e103-8d03-43c8-bf31-b72a276acee3\",\n" + 
//				"      \"status\": \"ready\",\n" + 
//				"      \"core_ml_enabled\": true,\n" + 
//				"      \"created\": \"2018-10-28T14:43:33.519\",\n" + 
//				"      \"classes\": [\n" + 
//				"        {\n" + 
//				"          \"class\": \"goldenretriever\"\n" + 
//				"        },\n" + 
//				"        {\n" + 
//				"          \"class\": \"husky\"\n" + 
//				"        },\n" + 
//				"        {\n" + 
//				"          \"class\": \"beagle\"\n" + 
//				"        }\n" + 
//				"      ],\n" + 
//				"      \"updated\": \"change\"\n" + 
//				"    }\n" + 
//				"  ]\n" + 
//				"}";
		
//		System.out.println(input);
//		
//		try {
//			XML.createXML(input);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		
		


//		String input = "{\n" + 
//				"  \"images_processed\": 1,\n" + 
//				"  \"images\": [\n" + 
//				"    {\n" + 
//				"      \"faces\": [\n" + 
//				"        {\n" + 
//				"          \"age\": {\n" + 
//				"            \"min\": 15,\n" + 
//				"            \"max\": 18,\n" + 
//				"            \"score\": 0.99970555\n" + 
//				"          },\n" + 
//				"          \"gender\": {\n" + 
//				"            \"gender\": \"FEMALE\",\n" + 
//				"            \"score\": 0.9591896\n" + 
//				"          },\n" + 
//				"          \"face_location\": {\n" + 
//				"            \"width\": 112.0,\n" + 
//				"            \"height\": 122.0,\n" + 
//				"            \"left\": 172.0,\n" + 
//				"            \"top\": 111.0\n" + 
//				"          }\n" + 
//				"        }\n" + 
//				"      ],\n" + 
//				"      \"image\": \"prez.jpg\"\n" + 
//				"    }\n" + 
//				"  ]\n" + 
//				"}";
	}
}
