package ImageRecognition;


import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class UI extends JFrame implements MouseListener{
	private Menu menue;
	private Orderlist order = new Orderlist();
	private Tools tool = new Tools();
	
	public UI(String images[]) {
		menue = new Menu(images);
		
		this.add(order, BorderLayout.WEST);
		this.add(tool, BorderLayout.SOUTH);
		this.add(menue);
		this.setVisible(true);
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		
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
	
	public static void main(String args[]) {
		String files[] = {"prez.jpg", "test01.jpg", 
				"test02.jpg", "test03.jpg", "test04.jpg"};
		new UI(files);
	}
}
