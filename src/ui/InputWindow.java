package ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import utils.Colors;

import java.awt.Font;
import javax.swing.JTextField;

public class InputWindow extends JFrame{
	

	private JLabel confirmBtn;
	private JTextField textField;
	
	
	public InputWindow(String fieldName) {
		
		setBounds(100, 100, 326, 204);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter "+ fieldName + ":");
		lblNewLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		lblNewLabel.setForeground(Color.white);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 11, 320, 35);
		getContentPane().add(lblNewLabel);
		
		JLabel roundedTextField = new JLabel("");
		roundedTextField.setHorizontalAlignment(SwingConstants.CENTER);
		roundedTextField.setBounds(0, 45, 320, 32);
		roundedTextField.setIcon(new ImageIcon("usernameField.png"));
		getContentPane().add(roundedTextField);
		setResizable(false);
		
		confirmBtn = new JLabel("");
		confirmBtn.setHorizontalAlignment(SwingConstants.CENTER);
		confirmBtn.setBounds(114, 88, 97, 68);
		confirmBtn.setIcon(new ImageIcon("exitBtn.png"));
		getContentPane().add(confirmBtn);
		getContentPane().setBackground(Colors.accent);
		
		textField = new JTextField();
		textField.setBorder(null);
		textField.setBounds(68, 45, 184, 32);
		getContentPane().add(textField);
		textField.setColumns(10);
		setVisible(true);
		
	}
	
	
	public String getField() {
		return textField.getText().toString();
	}
	
	public JLabel getBtn() {
		return confirmBtn;
	}
}
