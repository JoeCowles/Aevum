package ui;

import javax.swing.JOptionPane;

public class Alert {

	public static void createWarning(String message) {
		
		JOptionPane.showMessageDialog(null, message, "WARNING", JOptionPane.WARNING_MESSAGE);
		
	}
}
