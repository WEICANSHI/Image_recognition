package Image_recognition;

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
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class FileManager {
	public String classifierId;
	
	public FileManager() {
		classifierId = XML.currentClassifier;
	}
	
	/**
	 * 
	 * @param newid: id of a classifier
	 * @param classes: Key: class name, Value: zip file name
	 */
	public Map<String, String> addClassifier(String newid, Map<String, String > classes) {
		Map<String, List<String> > imagesclass = new HashMap<String, List<String>>();
		Map<String, String> imagesZip = new HashMap<>();
		// unzip each file, and compress
		Iterator<Map.Entry<String, String> > itr = classes.entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry<String, String> tmp = itr.next();
			List<String> images = FileManager.unZip(XML.ZipDir + tmp.getValue());
			List<String> compressed = new ArrayList<>();
			// compress each image in the file
			for(int i = 0; i < images.size(); i++) {
				String compressfile = FileManager.compressPictureByQality(images.get(i));
				if(compressfile == null) continue;
				compressed.add(compressfile);
			}
			// generate new compressed file using compressed files, get the ziped name
			String zipname = FileManager.createZip(tmp.getKey() + "compressed", compressed);
			if(zipname == null || compressed.size() == 0) continue;
			// put the record into imagesclass
			imagesclass.put(tmp.getKey(), compressed);
			imagesZip.put(tmp.getKey(), zipname);
		}
		// record the files to XML
		XML.classifyRecord(newid, imagesclass);
		return imagesZip;
	}
	
	/**
	 * Create zip file from list of images name
	 * @param name: name of the zip files
	 * @param images: list of images in the zip file
	 * @return the ziped file name
	 * @author baeldung https://www.baeldung.com/java-compress-and-uncompress
	 */
	public static String createZip(String name, List<String> images) {
		try {
			List<String> srcFiles = new ArrayList<String>(images);
	        FileOutputStream fos = new FileOutputStream(XML.ZipDir + name + ".zip");
	        ZipOutputStream zipOut = new ZipOutputStream(fos);
	        for (String srcFile : srcFiles) {
	            File fileToZip = new File(XML.imageDir + srcFile);
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
	        return name + ".zip";
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Extract files from zipFile
	 * @param fileZip: the file waiting for extracting
	 * @return list of images name extracted
	 * @throws IOException
	 * @author baeldung https://www.baeldung.com/java-compress-and-uncompress
	 */
	public static List<String> unZip(String fileZip) {
		List<String> images = new ArrayList<String>();
        File destDir = new File(XML.imageDir);
        byte[] buffer = new byte[1024];
        try {
        	 ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
             ZipEntry zipEntry = zis.getNextEntry();
             while (zipEntry != null) {
                 File newFile = newFile(destDir, zipEntry);
                 images.add(zipEntry.getName());
                 FileOutputStream fos = new FileOutputStream(newFile);
                 int len;
                 while ((len = zis.read(buffer)) > 0) {
                     fos.write(buffer, 0, len);
                 }
                 fos.close();
                 zipEntry = zis.getNextEntry();
             }
             zis.closeEntry();
             zis.close();
        }catch(IOException e) {
        	e.printStackTrace();
        }
       
        return images;
	}
	
	/**
	 * 
	 * @param destinationDir
	 * @param zipEntry
	 * @return
	 * @throws IOException
	 * @author baeldung https://www.baeldung.com/java-compress-and-uncompress
	 */
	public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
        
         
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
         
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
         
        return destFile;
    }
	
	/**
	 * 
	 * @author https://stackoverflow.com/questions/44565500/how-can-i-compress-images-using-java
	 */
	public static String compressPictureByQality(String imagename) {
		try {
			// give a new name to the compressed image
			System.out.println(imagename);
			int end = Math.max(Math.max(imagename.indexOf(".JPG"),
						imagename.indexOf(".jpeg")), imagename.indexOf(".jpg"));
			
			if(end == -1) return null;

			String compressedname = imagename.substring(0, end) + "compress.jpg";
			File input = new File(XML.imageDir + imagename);
		    BufferedImage image = ImageIO.read(input);

		    File compressedImageFile = new File(XML.imageDir + compressedname);
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
		    return compressedname;
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
