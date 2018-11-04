package UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Orderlist extends JPanel{
	private List<String> orders = new ArrayList<String>();
	public CheckPanel check;
	
	public Orderlist(){
		check = new CheckPanel();
		this.add(check);
		this.setLayout(null);
		check.setBounds(0, 568, 200, 200);
		this.setPreferredSize(new Dimension(200, 768));
	}
	
	public void paintComponent(Graphics g) {
		int y = 50;
		Graphics2D g2 = (Graphics2D)g;
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
		for(int i = 0; i < orders.size(); i ++) {
			g2.drawString(orders.get(i), 20, y);
			y += 50;
		}
	}
	
	
	public void addOrder(String order) {
		orders.add(order);
		this.repaint();
	}
}
