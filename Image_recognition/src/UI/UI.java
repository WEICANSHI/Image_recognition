package UI;


import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import Image_recognition.QuickChecking;

@SuppressWarnings("serial")
public class UI extends JFrame implements MouseListener{
	private Menu menue;
	public Orderlist order = new Orderlist();
	private Tools tool = new Tools();
	public QuickChecking quickcheck = new QuickChecking();
	
	public UI(String images[], String names[]) {
		menue = new Menu(images, names, this);
		
		this.add(order, BorderLayout.WEST);
		this.add(tool, BorderLayout.SOUTH);
		this.add(menue);
		this.setVisible(true);
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tool.train.addMouseListener(this);
		tool.scan.addMouseListener(this);
		tool.clear.addMouseListener(this);
	}

	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource() == tool.train) {
			new ClassifyPanel(this);
		}
		if(arg0.getSource() == tool.scan) {
//			JFileChooser chooser = new JFileChooser("./test/"); 
//			chooser.showSaveDialog(null);
//			File file = chooser.getSelectedFile();
			JFileChooser chooser = new JFileChooser("./test/");
			chooser.setMultiSelectionEnabled(true);
			chooser.showOpenDialog(null);
			File[] files = chooser.getSelectedFiles();
			for(int i = 0; i < files.length; i ++) {
				if(files[i] != null) {
					String ret = quickcheck.scan("./test/" + files[i].getName());
					if(ret != null) {
						order.addOrder(ret);
					}else {
						order.check.updatefile("./test/" + files[i].getName());
					}
				}
			}
		}
		if(arg0.getSource() == tool.clear) {
			order.clear();
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
	public void mousePressed(MouseEvent arg0) {}


	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	public static void main(String args[]) {
		String files[] = {"bun.jpg", "chicken.jpg", 
				"congee.jpg", "custer.jpg", "dumpling.jpg", "riceroll.jpg","siumai.jpg"};
		String names[] = {"steamedbund", "chicken", "congee", 
				"eggcuster", "dumpling", "riceroll", "siumai"};
		new UI(files, names);
	}
}
