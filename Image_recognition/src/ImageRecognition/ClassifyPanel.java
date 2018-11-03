package ImageRecognition;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class ClassifyPanel extends JFrame{
	public Panel p = new Panel();
	
	public ClassifyPanel() {
		this.add(p);
		this.setVisible(true);
		this.setSize(700, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	 
	 public static void main(String args[]) {
		 new ClassifyPanel();
	 }
}

@SuppressWarnings("serial")
class Panel extends JPanel implements MouseListener{
	public JTextField textname= new JTextField(5);
	public JButton brows = new JButton("brows");
	public JButton finish = new JButton("Finish");
	public List<String> classname = new ArrayList<String>();
	public List<String> filename = new ArrayList<String>();
	
	public Panel() {
		this.add(textname);
		this.add(brows);
		this.add(finish);
		textname.setBounds(100, 400, 200, 50);
		brows.setBounds(320, 400, 100, 50);
		finish.setBounds(450, 400, 100, 50);
		this.setLayout(null);
		brows.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		int y = 20;
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
		for(int i = 0; i < classname.size(); i++) {
			g2.drawString(classname.get(i), 50, y);
			g2.drawString(filename.get(i), 200, y);
			y += 50;
		}
	}
	
	public void addclass(String file) {
		String name = textname.getText();
		classname.add(name);
		filename.add(file);
		textname.setText("");
		this.repaint();
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource() == brows) {
			JFileChooser chooser = new JFileChooser("./Zips/"); 
			chooser.showSaveDialog(null);
			File file=chooser.getSelectedFile();
			if(file != null)
				this.addclass(file.getName());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
}
