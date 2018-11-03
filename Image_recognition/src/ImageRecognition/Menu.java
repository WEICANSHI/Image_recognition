package ImageRecognition;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Menu extends JPanel{
	
	private List<JButton> buttons = new ArrayList<JButton>();
	private List<String> image_files = new ArrayList<String>();
	
	public Menu(String files[]) {
		this.setPreferredSize(new Dimension(1024, 768));
		this.setLayout(new GridLayout(4, 3));
		this.Initializer(files);
		for(int i = 0; i < buttons.size(); i ++) {
			this.add(buttons.get(i));
		}
	}
	
	public static ImageIcon readImage(String fileName) {
		BufferedImage img = null;	// image buffer
		//Image image = null; // Image initialized as null
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			throw new RuntimeException("Image Load Fail");
		}
		//get the imageIcon
		ImageIcon icon = new ImageIcon(img);
		return icon;
	}
	
	
	private void Initializer(String files[]) {
		String dir = "./Image/";
		for(int i = 0; i < files.length; i++) {
			JButton b = new JButton();
			ImageIcon icon = Menu.readImage(dir + files[i]);
			Image scaleImage = icon.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT);
			b.setIcon(new ImageIcon(scaleImage));	
			buttons.add(b);
		}	
	}

}
