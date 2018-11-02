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

public class Menu extends JPanel{
	
	private List<JButton> buttons = new ArrayList<JButton>();
	private List<String> image_files = new ArrayList<String>();
	
	public Menu() {
		//Image img = Menu.readImage("./Image/prez.jpg");
		//button.setIcon(new ImageIcon(img));	
		//this.add(button);
		this.setPreferredSize(new Dimension(1024, 768));
		this.setLayout(new GridLayout(3, 3));
		this.Initializer();
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
		//image = icon.getImage();
		//return image;
		return icon;
	}
	
	
	private void Initializer() {
		String dir = "./Image/";
		String files[] = {"prez.jpg", "test01.jpg", 
						"test02.jpg", "test03.jpg", "test04.jpg"};
		
		for(int i = 0; i < 5; i++) {
			JButton b = new JButton();
			ImageIcon icon = Menu.readImage(dir + files[i]);
			Image scaleImage = icon.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT);
			b.setIcon(new ImageIcon(scaleImage));	
			buttons.add(b);
		}	
	}

}
