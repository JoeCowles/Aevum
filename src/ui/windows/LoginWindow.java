package ui.windows;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import utils.Colors;

public class LoginWindow extends JFrame {
	
	
	private JTextField textField;
	private JPasswordField passwordField;
	
	public LoginWindow() {
		
		getContentPane().setBackground(Colors.primary);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Colors.secondary);
		panel.setBounds(0, 0, 268, 462);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel unboxedLabel = new JLabel("Unboxed");
		unboxedLabel.setForeground(Color.WHITE);
		unboxedLabel.setFont(new Font("Harlow Solid Italic", Font.PLAIN, 40));
		unboxedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		unboxedLabel.setBounds(10, 383, 152, 39);
		panel.add(unboxedLabel);
		
		JLabel programIcon = new JLabel("");
		programIcon.setHorizontalAlignment(SwingConstants.CENTER);
		programIcon.setBounds(10, 11, 248, 184);
		panel.add(programIcon);
		programIcon.setIcon(new ImageIcon("programIcon.png"));
		
		JLabel unboxedIcon = new JLabel("");
		unboxedIcon.setBounds(147, 357, 121, 94);
		panel.add(unboxedIcon);
		unboxedIcon.setHorizontalAlignment(SwingConstants.CENTER);
		unboxedIcon.setIcon(new ImageIcon("unboxedIcon.png"));
		
		JLabel lblNewLabel = new JLabel("Industries");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Harlow Solid Italic", Font.PLAIN, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(20, 415, 142, 22);
		panel.add(lblNewLabel);
		
		JLabel programLabel = new JLabel("Aevum");
		programLabel.setForeground(Color.WHITE);
		programLabel.setFont(new Font("Rockwell", Font.PLAIN, 60));
		programLabel.setHorizontalAlignment(SwingConstants.CENTER);
		programLabel.setBounds(31, 164, 207, 59);
		panel.add(programLabel);
		
		JLabel userIcon = new JLabel("");
		userIcon.setBounds(325, 96, 58, 52);
		getContentPane().add(userIcon);
		setBounds(100, 100, 712, 491);
		userIcon.setIcon(new ImageIcon("usericon.png"));
		
		textField = new JTextField();
		textField.setBounds(404, 108, 221, 32);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.setOpaque(false);
		textField.setBorder(null);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setFont(new Font("Rockwell", Font.BOLD, 16));
		textField.setForeground(Colors.gray);
		
		JLabel fieldBack = new JLabel("");
		fieldBack.setBounds(404, 108, 221, 32);
		getContentPane().add(fieldBack);
		fieldBack.setIcon(new ImageIcon("usernameField.png"));
		
		JLabel passwordIcon = new JLabel("");
		passwordIcon.setBounds(325, 183, 58, 58);
		getContentPane().add(passwordIcon);
		passwordIcon.setIcon(new ImageIcon("passwordicon.png"));
		passwordField = new JPasswordField();
		passwordField.setOpaque(false);
		passwordField.setBorder(null);
		passwordField.setBounds(404, 194, 221, 32);
		passwordField.setHorizontalAlignment(JTextField.CENTER);
		getContentPane().add(passwordField);
		
		JLabel passBack = new JLabel("");
		passBack.setBounds(404, 194, 221, 32);
		getContentPane().add(passBack);
		passBack.setIcon(new ImageIcon("usernameField.png"));
		
		JLabel loginBtn = new JLabel("Login");
		loginBtn.setFont(new Font("Rockwell", Font.PLAIN, 35));
		loginBtn.setHorizontalAlignment(SwingConstants.CENTER);
		loginBtn.setBounds(459, 289, 112, 42);
		getContentPane().add(loginBtn);
		loginBtn.setForeground(Color.white);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("frameIcon.png"));
		setTitle("Aevum");
		
	}
	
}
