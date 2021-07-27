package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Main;
import utils.Utils;

public class ProgramCard extends JPanel{
	
	private JLabel label, deleteBtn, iconLbl;
	private boolean selected = false;
	private ProgramCard thisCard;
	String name;
	public ProgramCard(String name, int width, int height) {
		thisCard = this;
		this.name = name;
		this.setSize(width, height);
		this.setBackground(Color.white);
		this.setMaximumSize(new Dimension(3000, height));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setAlignmentY(CENTER_ALIGNMENT);
		this.setAlignmentX(CENTER_ALIGNMENT);
		
		label = new JLabel();
		label.setText("  " + name);
		label.setFont(new Font("Rockwell", Font.PLAIN, 20));
		deleteBtn = new JLabel();
		deleteBtn.setIcon(new ImageIcon("deleteBtn.png"));
		deleteBtn.setVisible(false);
		iconLbl = new JLabel();
		this.add(label);
		this.add(deleteBtn);
		this.setVisible(true);
	}
	public void setIcon(ImageIcon icon) {
		if(iconLbl == null) {
			return;
		}
		Image image = icon.getImage();
		iconLbl.setIcon(new ImageIcon(Utils.getScaledImage(image, 25, 25)));
		iconLbl.setBorder(new EmptyBorder(0,5,0,0));
		this.removeAll();
		
		this.add(iconLbl);
		this.add(label);
		this.add(deleteBtn);
		this.setVisible(true);
		
	}
	public JLabel deleteBtn() {
		return deleteBtn;
	}
	public void toggleSelected() {
		
		selected = !selected;
		if(selected) {
			setBackground(Color.lightGray);
			deleteBtn.setVisible(true);
		}else {
			setBackground(Color.WHITE);
			deleteBtn.setVisible(false);
		}
		
	}
	public boolean isSelected() {
		return selected;
	}
	public String getName() {
		return name;
	}
	
	
	
}
