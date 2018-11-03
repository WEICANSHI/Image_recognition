package ImageRecognition;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class FileManager {
	public String dir = "./Image/";
	public Map<String, List<String> > classRecorder;
	
	public FileManager() {
		classRecorder = XML.load();
		if(classRecorder == null)
			classRecorder = new HashMap<String, List<String>>();
	}
	
	
	public void classify(String Class, List<String> images) {
		classRecorder.put(Class, images);
		try {
			XML.classifyRecord(Class, images);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createZip(String name, List<String> images) {
		try {
			List<String> srcFiles = new ArrayList<String>(images);
	        FileOutputStream fos = new FileOutputStream("./Zips/" + name + ".zip");
	        ZipOutputStream zipOut = new ZipOutputStream(fos);
	        for (String srcFile : srcFiles) {
	            File fileToZip = new File(srcFile);
	            FileInputStream fis = new FileInputStream(fileToZip);
	            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
	            zipOut.putNextEntry(zipEntry);
	 
	            byte[] bytes = new byte[1024];
	            int length;
	            while((length = fis.read(bytes)) >= 0) {
	                zipOut.write(bytes, 0, length);
	            }
	            fis.close();
	        }
	        zipOut.close();
	        fos.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void compressPictureByQality() {
		try {
			File input = new File("Image/test01.jpg");
		    BufferedImage image = ImageIO.read(input);

		    File compressedImageFile = new File("Image/test01compress.jpg");
		    OutputStream os = new FileOutputStream(compressedImageFile);

		    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
		    ImageWriter writer = (ImageWriter) writers.next();

		    ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		    writer.setOutput(ios);

		    ImageWriteParam param = writer.getDefaultWriteParam();

		    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		    param.setCompressionQuality(0.5f);  // Change the quality value you prefer
		    writer.write(null, new IIOImage(image, null, null), param);

		    os.close();
		    ios.close();
		    writer.dispose();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FileManager.compressPictureByQality();
    }
}
