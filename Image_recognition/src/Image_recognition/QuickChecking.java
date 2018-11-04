package Image_recognition;

import java.io.FileNotFoundException;
import java.util.Map;

public class QuickChecking {
	public FileManager filemanager;
	public Connector connector;
	
	public QuickChecking() {
		XML.InitSetting();
		filemanager = new FileManager();
		connector = new Connector();
	}
	
	public void addClassifier(String newid, Map<String, String > classes) {
		Map<String, String> images = filemanager.addClassifier(newid, classes);
		try {
			connector.createClassifier(newid, images);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String scan(String filename) {
		try {
			String ret = connector.Scan(filename, "me");
			int classindex = ret.indexOf("\"class\":");
			if(classindex == -1) return null;
			ret = ret.substring(classindex + 7);
			int class_st = ret.indexOf(": \"");
			int class_ed = ret.indexOf("\",");
			if(class_st == -1 || class_ed == -1) return null;
			String CLASS = ret.substring(class_st + 3, class_ed);
			System.out.println(CLASS);
			// parse score
			int scoreindex = ret.indexOf("\"score\"");
			if(scoreindex == -1) return null;
			ret = ret.substring(scoreindex + 9);
			String score = "";
			for(int i = 0; i < ret.length(); i++) {
				if(Character.isDigit(ret.charAt(i)) || ret.charAt(i) == '.' ) {
					score += ret.charAt(i);
				}else {
					break;
				}
			}
			if(score.equals("")) return null;
			double digscore = Double.parseDouble(score.trim());
			if(digscore < 0.9) return null;
			return CLASS; 
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String args[]) {
		QuickChecking q = new QuickChecking();
//		Map<String, String> classes = new HashMap<>();
//		classes.put("beagle",  XML.ZipDir + "beagle.zip");
//		classes.put("goldenretriever", XML.ZipDir + "goldenretriever.zip");
//		classes.put("husky",  XML.ZipDir + "husky.zip");
//		q.addClassifier("test", classes);
		String ret = q.scan("./test/fruitbowl.jpg");
		System.out.println(ret);
	}
}
