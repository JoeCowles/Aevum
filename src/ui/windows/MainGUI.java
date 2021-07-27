package ui.windows;

import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import ui.AnimatedTitle;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;


public class MainGUI extends JFrame{
	
	int screenWidth, screenHeight;
	int windowWidth, windowHeight;
	
	JLabel programLimitsBtn;
	
	public MainGUI() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds(0, 0, screenWidth, screenHeight);
		windowHeight = this.getSize().height;
		windowWidth = this.getSize().width;
		getContentPane().setLayout(new GridLayout(2, 2, 0, 0));
		
		JLayeredPane programPanel = new JLayeredPane();
		OverlayLayout programPanelLayout = new OverlayLayout(programPanel);
		programPanel.setLayout(programPanelLayout);
		//programPanel.setLayout(null);
		programPanel.setBounds(0,0,windowWidth/2, windowHeight/2);
		getContentPane().add(programPanel);
		
		programLimitsBtn = new JLabel("");
		programLimitsBtn.setOpaque(true);
		programLimitsBtn.setBackground(null);
		programLimitsBtn.setIcon(new ImageIcon(resizeImage("keyboard.png", windowWidth/2, windowHeight/2)));
		
		programPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				programPanel.add(new AnimatedTitle("Programs", "unboxedIcon.png"), 2);
				setVisible(true);
			}
		});
		programPanel.add(programLimitsBtn,3);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		
		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3);
		
		
		
		
		setVisible(true);
	}
	
	private Image resizeImage(String path, int width, int height) {
		
		
		Image image = Toolkit.getDefaultToolkit().createImage(path);
		return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
	}
	@Override
	public void validate() {
		
		super.validate();
		
		windowWidth = this.getSize().width;
		windowHeight = this.getSize().height;

		//programLimitsBtn.setIcon(new ImageIcon(resizeImage("keyboard.png", windowWidth/2, windowHeight/2)));
		//systemLimitsBtn.setIcon(new ImageIcon(resizeImage("keyboard.png", windowWidth/2, windowHeight/2)));
		//timeUsedBtn.setIcon(new ImageIcon(resizeImage("keyboard.png", windowWidth/2, windowHeight/2)));
		//overrideBtn.setIcon(new ImageIcon(resizeImage("keyboard.png", windowWidth/2, windowHeight/2)));
		
	}
	
	
}
