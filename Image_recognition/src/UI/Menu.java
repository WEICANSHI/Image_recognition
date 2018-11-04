package UI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Menu extends JPanel implements MouseListener{
	
	private List<JButton> buttons = new ArrayList<JButton>();
	private List<String> classname = new ArrayList<String>();
	private UI ui;
	
	public Menu(String files[], String names[], UI ui) {
		this.ui = ui;
		this.classname = new ArrayList<String>(Arrays.asList(names));
		this.setPreferredSize(new Dimension(1024, 768));
		this.setLayout(new GridLayout(4, 3));
		this.Initializer(files);
		for(int i = 0; i < buttons.size(); i ++) {
			this.add(buttons.get(i));
			buttons.get(i).addMouseListener(this);
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
		String dir = "./Icon/";
		for(int i = 0; i < files.length; i++) {
			JButton b = new JButton();
			ImageIcon icon = Menu.readImage(dir + files[i]);
			Image scaleImage = icon.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT);
			b.setIcon(new ImageIcon(scaleImage));	
			buttons.add(b);
		}	
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		for(int i = 0; i < buttons.size(); i++) {
			if(arg0.getSource() == buttons.get(i)) {
				ui.order.addOrder(classname.get(i));
				ui.order.check.file = null;
				break;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
