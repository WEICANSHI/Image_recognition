package ImageRecognition;


import java.awt.BorderLayout;

import javax.swing.JFrame;

public class UI extends JFrame{
	private Menu menue = new Menu();
	private Orderlist order = new Orderlist();
	
	public UI() {
		this.add(order, BorderLayout.WEST);
		this.add(menue);
		this.setVisible(true);
		//this.setSize(1024, 768);
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public static void main(String args[]) {
		new UI();
	}
}
