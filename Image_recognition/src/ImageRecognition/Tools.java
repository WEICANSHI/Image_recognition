package ImageRecognition;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Tools extends JPanel{
	public JButton train = new JButton("Admin Tools");
	public JButton scan = new JButton("Scan");
	public JButton check = new JButton("Check");
	
	public Tools() {
		this.add(scan);
		this.add(check);
		this.add(train);
		this.setPreferredSize(new Dimension(1024, 100));
		this.setLayout(new GridLayout(1, 3));
		
	}
}
