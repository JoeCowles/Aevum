package ui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnimatedTitle extends JPanel{
	
	
	public AnimatedTitle(String title, String icon) {

		super();
		//setBounds(0,0,100, 200);
		
		if(icon == null || icon.isBlank()) {
			icon = "unboxedIcon.png";
		}
		if(title == null) {
			title = "";
		}
		JLabel titleLbl = new JLabel();
		titleLbl.setText(title);
		
		JLabel iconLbl = new JLabel();
		iconLbl.setVisible(true);
		iconLbl.setIcon(new ImageIcon(icon));
		iconLbl.setSize(100, 100);
		
		add(iconLbl, JLabel.CENTER);
		add(titleLbl, JLabel.CENTER);
		
		
		setBackground(null);
		setOpaque(true);
		setVisible(true);
		
	}
	
}
