package UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CheckPanel extends JPanel{
	public String file;
	public CheckPanel() {
		this.setPreferredSize(new Dimension(200, 200));
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(file == null) return;
		Image img = CheckPanel.readImage(file);
		g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		System.out.println("read");
	}
	
	public static Image readImage(String fileName) {
		BufferedImage img = null;	// image buffer
		//Image image = null; // Image initialized as null
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			throw new RuntimeException("Image Load Fail");
		}
		//get the imageIcon
		ImageIcon icon = new ImageIcon(img);
		Image image = icon.getImage();
		return image;
	}
	
	public void updatefile(String file) {
		this.file = file;
		this.repaint();
	}
}
