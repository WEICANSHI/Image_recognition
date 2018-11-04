package UI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class ClassifyPanel extends JFrame{
	public Panel p;
	public UI ref;
	
	public ClassifyPanel(UI ref) {
		this.ref = ref;
		p = new Panel(ref, this);
		//this.classes = new HashMap<>();
		this.add(p);
		this.setVisible(true);
		this.setSize(700, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}

@SuppressWarnings("serial")
class Panel extends JPanel implements MouseListener{
	public JTextField textname= new JTextField(5);
	public JButton brows = new JButton("brows");
	public JButton finish = new JButton("Finish");
	public List<String> classname = new ArrayList<String>();
	public List<String> filename = new ArrayList<String>();
	public Map<String, String> classes = new HashMap<>();
	public UI ref;
	public ClassifyPanel pf;
	
	public Panel(UI ref, ClassifyPanel pf) {
		this.pf = pf;
		this.ref = ref;
		this.add(textname);
		this.add(brows);
		this.add(finish);
		textname.setBounds(100, 400, 200, 50);
		brows.setBounds(320, 400, 100, 50);
		finish.setBounds(450, 400, 100, 50);
		this.setLayout(null);
		brows.addMouseListener(this);
		finish.addMouseListener(this);
		this.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		int y = 20;
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
		for(int i = 0; i < classname.size(); i++) {
			g2.drawString(classname.get(i), 50, y);
			g2.drawString(filename.get(i), 300, y);
			y += 50;
		}
	}
	
	public void addclass(String file) {
		String name = textname.getText();
		classname.add(name);
		filename.add(file);
		classes.put(name, file);
		classes.put(name, file);
		textname.setText("");
		this.repaint();
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource() == brows) {
			JFileChooser chooser = new JFileChooser("./Zip/"); 
			chooser.showSaveDialog(null);
			File file=chooser.getSelectedFile();
			if(file != null)
				this.addclass(file.getName());
		}
		
		if(arg0.getSource() == finish) {
			int ran = (int) (Math.random() * 100);
			System.out.println(ran);
			ref.quickcheck.addClassifier("menue" + ran, classes);
			classes.clear();
			pf.setVisible(false);
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
