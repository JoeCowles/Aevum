package ui.windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import utils.Colors;
import utils.FileSystem;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.time.LocalTime;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import main.Main;
import ui.ComboboxScrollbarUI;
import ui.combobox.ComboBoxFilterDecorator;
import ui.combobox.CustomComboRenderer;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DowntimeWindow extends JFrame{
	
	private JSpinner startHour, startMin, endHour, endMin;
	private JComboBox<String> startCombo, endCombo;
	
	public DowntimeWindow(LocalTime startTime, LocalTime endTime) {
		getContentPane().setLayout(null);
		setBounds(100, 100, 450, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 213, 267);
		getContentPane().add(panel);
		panel.setBackground(Colors.secondary);
		panel.setLayout(null);
		
		JLabel fromLbl = new JLabel("Block from:");
		fromLbl.setForeground(Color.WHITE);
		fromLbl.setFont(new Font("Rockwell", Font.PLAIN, 20));
		fromLbl.setBounds(55, 37, 127, 28);
		panel.add(fromLbl);
		
		startHour = new JSpinner();
		startHour.setBounds(10, 76, 58, 28);
		panel.add(startHour);
		
		JLabel lblNewLabel_1 = new JLabel(":");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Rockwell", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(65, 76, 24, 28);
		panel.add(lblNewLabel_1);
		
		startMin = new JSpinner();
		startMin.setBounds(86, 76, 47, 28);
		panel.add(startMin);
		
		startCombo = new JComboBox<String>();
		startCombo.setBounds(143, 76, 60, 28);
		panel.add(startCombo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(212, 0, 236, 267);
		getContentPane().add(panel_1);
		panel_1.setBackground(Colors.accent);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("To:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		lblNewLabel.setBounds(103, 37, 51, 20);
		panel_1.add(lblNewLabel);
		
		endHour = new JSpinner();
		endHour.setBounds(26, 76, 51, 28);
		panel_1.add(endHour);
		
		JLabel lblNewLabel_2 = new JLabel(":");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Rockwell", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(78, 76, 14, 28);
		panel_1.add(lblNewLabel_2);
		
		endMin = new JSpinner();
		endMin.setBounds(92, 76, 43, 28);
		panel_1.add(endMin);
		
		endCombo = new JComboBox<String>();
		endCombo.setBounds(145, 77, 59, 27);
		panel_1.add(endCombo);
		
		JLabel saveBtn = new JLabel("");
		saveBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Main.plw.addDowntime(getStartTime(), getEndTime());
				closeWindow();
			}
		});
		saveBtn.setBounds(180, 140, 46, 71);
		panel_1.add(saveBtn);
		saveBtn.setIcon(new ImageIcon(FileSystem.resizeImage("exitBtn.png",30,30)));
		
		
		startHour.setBorder(null);
		for(JButton btn : getButtons(startHour)) {
			btn.setBorder(null);
			btn = new JButton();
		}
		startMin.setBorder(null);
		for(JButton btn : getButtons(startMin)) {
			btn.setBorder(null);
		}
		endHour.setBorder(null);
		for(JButton btn : getButtons(endHour)) {
			btn.setBorder(null);
		}
		endMin.setBorder(null);
		for(JButton btn : getButtons(endMin)) {
			btn.setBorder(null);
		}
		String[] hrs = new String[12];
		for(int i = 1; i < 13; i++) {
			if(i < 10) {
				hrs[i-1] = "0" + i;
			}else {
				hrs[i-1] = i + "";
			}
		}
		String[] mins = new String[60];
		for(int i = 0; i < 60; i++) {
			if(i < 10) {
				mins[i] = "0" + i;
			}else {
				mins[i] = i + "";
			}
				
		}
		startHour.setModel(new SpinnerListModel(hrs));
		endHour.setModel(new SpinnerListModel(hrs));
		startMin.setModel(new SpinnerListModel(mins));
		endMin.setModel(new SpinnerListModel(mins));
		
		comboBoxUI(startCombo, Colors.secondary);
		comboBoxUI(endCombo, Colors.accent);
		
		
		
		if(startTime != null && endTime != null) {
			if(startTime.getHour() != 0 && startTime.getHour() != 12) {
				startHour.setValue(hrs[startTime.getHour()%12 - 1]);
			}else {
				startHour.setValue(hrs[11]);
			}
			if(endTime.getHour() != 0 && endTime.getHour() != 12) {
				endHour.setValue(hrs[endTime.getHour()%12 - 1]);
			}else {
				endHour.setValue(hrs[11]);
			}
			System.out.println(startTime.getHour());
			
			startMin.setValue(mins[startTime.getMinute()]);
			//endHour.setValue(hrs[endTime.getHour() % 12 -1]);
			endMin.setValue(mins[endTime.getMinute()]);
			
			if(startTime.getHour() >= 12) {
				startCombo.addItem("PM");
				startCombo.addItem("AM");
			}else {
				startCombo.addItem("AM");
				startCombo.addItem("PM");
			}
			if(endTime.getHour() < 12) {
				endCombo.addItem("AM");
				endCombo.addItem("PM");
			}else {
				endCombo.addItem("PM");
				endCombo.addItem("AM");
			}
			
		}else {
			startCombo.addItem("AM");
			startCombo.addItem("PM");
			
			endCombo.addItem("AM");
			endCombo.addItem("PM");
		}
		setVisible(true);
	}
	
	private LocalTime getStartTime() {
		
		String startHr = startHour.getValue().toString();
		if(startCombo.getSelectedItem().equals("PM")) {
			startHr = (Integer.parseInt(startHr) + 12) + "";
			startHr = Integer.parseInt(startHr) > 23 ? 23+"" : startHr;
		}
		return LocalTime.parse(startHr + ":" + startMin.getValue() + ":00");
		
	}
	private LocalTime getEndTime() {
		
		String endHr = endHour.getValue().toString();
		if(endCombo.getSelectedItem().equals("PM")) {
			endHr = (Integer.parseInt(endHr) + 12) + "";
			endHr = Integer.parseInt(endHr) > 23 ? 23+"" : endHr;
		}
		return LocalTime.parse(endHr + ":" + endMin.getValue() + ":00");
		
	}
	private ArrayList<JButton> getButtons(JComponent spinner) {
		ArrayList<JButton> btns = new ArrayList<JButton>();
		for(Component com : spinner.getComponents()) {
			
			if(com instanceof JButton) {
				btns.add((JButton) com);
			}
		}
		return btns;
	}
	private void comboBoxUI(JComboBox<String> comboBox, Color color) {
		
		
		for (int i = 0; i < comboBox.getComponentCount(); i++) {
		    if (comboBox.getComponent(i) instanceof JComponent) {
		        ((JComponent) comboBox.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
		    }
		}
		comboBox.setUI(new BasicComboBoxUI() {
			
			
			@Override
			protected JButton createArrowButton() {
				  JButton dropdown = new JButton(new ImageIcon("downarrow.png"));
				  dropdown.setBackground(null);
				  dropdown.setBorder(null);
			      return dropdown;
			}
            @Override
            protected ComboPopup createPopup() {
                return new BasicComboPopup(comboBox) {
                    @Override
                    protected JScrollPane createScroller() {
                        JScrollPane scroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        
                        scroller.getVerticalScrollBar().setUI(new ComboboxScrollbarUI());
                        return scroller;
                    }
                };
            }
        });
		comboBox.setFont(new Font("Rockwell", Font.BOLD, 15));
		comboBox.setEditable(false);
		comboBox.setBackground(color);
		comboBox.setForeground(Color.white);
		comboBox.getEditor().getEditorComponent().setForeground(Color.WHITE);

	}
	private void closeWindow() {
		this.dispose();
	}
}
