package ui.windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import ui.Alert;
import ui.AnimatedTitle;
import ui.ComboboxScrollbarUI;
import ui.CustomScrollBarUI;
import ui.DowntimeCard;
import ui.ProgramCard;
import ui.combobox.ComboBoxFilterDecorator;
import ui.combobox.CustomComboRenderer;
import utils.Colors;
import utils.Constants;
import utils.FileSystem;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.CellRendererPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import main.Main;
import tracking.DayLimit;
import tracking.Downtime;
import tracking.Program;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JTable;
import java.awt.Cursor;
import javax.swing.UIManager;
import java.awt.CardLayout;

public class ProgramLimitsWindow extends JFrame{
	private int windowHeight, windowWidth;
	private JTextField nameField;
	private ArrayList<ProgramCard> cards;
	private ArrayList<DowntimeCard> dtCards;
	private JTable table;
	private JPanel scrollCom;
	private boolean timeLimited = false;
	private JLabel timeLimitSw;
	private Program selectedProgram;
	private JPanel panel_4;
	private JPanel panel_1, dtPanel;
	private boolean editingDt = false;
	private DowntimeCard editingDtCard;
	private JComboBox<String> comboBox;
	
	private JPanel refreshPanel, panel_6, panel_2, panel_5;
	// Messy form of animation
	double rotation;
	int steps = 0;
	
	public ProgramLimitsWindow() {
		setBounds(100, 100, 917, 628);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		windowHeight = this.getSize().height;
		windowWidth = this.getSize().width;
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().setBackground(Colors.secondary);
		cards = new ArrayList<ProgramCard>();
		dtCards = new ArrayList<DowntimeCard>();
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(null);
		getContentPane().add(panel_3);
		panel_3.setMaximumSize(new Dimension(4400, 100));
		panel_3.setBackground(Colors.secondary);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		
		JLabel programLbl = new JLabel("Add Program  ");
		programLbl.setForeground(Color.WHITE);
		programLbl.setFont(new Font("Rockwell", Font.PLAIN, 20));
		panel_3.add(programLbl);
		programLbl.setBorder(new EmptyBorder(10, 0, 10, 0));
		try {
			final BufferedImage bi = ImageIO.read(new File("refreshBtn.png"));
			rotation = 0.01;
			refreshPanel = new JPanel() {
				@Override
				public Dimension getPreferredSize() {
					return new Dimension(bi.getWidth(), bi.getHeight());
				}
	
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2 = (Graphics2D) g;
					g2.rotate(Math.PI * rotation, bi.getWidth() / 2, bi.getHeight() / 2);
					g2.drawImage(bi, 0, 0, null);
				}
				
	
			};
		} catch(IOException e) {
			e.printStackTrace();
		}
		refreshPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String temp = "";
				try {
					for(String program : Main.os.runningPrograms()) {
						if(!temp.contains(program)) {
							comboBox.addItem(program);
							temp += program + " ";
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Timer timer = new Timer();
				TimerTask animation = new TimerTask() {

					@Override
					public void run() {
						if(steps > 50){
							
							resetSteps();
							refreshPanel.repaint();
							timer.cancel();
						}
						System.out.println("Rotating");
						refreshPanel.repaint();
						incrementSteps();
					}
					
					
				};
				timer.scheduleAtFixedRate(animation, 0l, 20l);
				
			}
		});
		refreshPanel.setBackground(Colors.secondary);
        panel_3.add(refreshPanel);

		
		//panel_3.add(refreshBtn);
		
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Rockwell", Font.PLAIN, 15));
		panel_3.add(comboBox);
		comboBox.setBorder(null);
		comboBox.setMaximumSize(new Dimension(200, 30));
		comboBox.setMinimumSize(new Dimension(100, 30));
		try {
			String temp = "";
			for(String program : Main.os.runningPrograms()) {
				if(!temp.contains(program)) {
					comboBox.addItem(program);
					temp += program + " ";
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		comboBoxUI(comboBox);
		nameField = new JTextField();
		nameField.setFont(new Font("Rockwell", Font.PLAIN, 17));
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(nameField);
		nameField.setColumns(10);
		nameField.setBorder(null);
		nameField.setText("Program name");
		nameField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nameField.setText("");
			}
		});
		JLabel createBtn = new JLabel("   Create");
		createBtn.setForeground(Color.WHITE);
		createBtn.setFont(new Font("Rockwell", Font.PLAIN, 20));
		createBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(nameField.getText().isBlank()) {
					Alert.createWarning("Name field is empty!");
					return;
				}else if(Main.searchPrograms(nameField.getText().toString()) != null){
					
					Alert.createWarning("Name already used!");
					return;
				}
				createProgram(nameField.getText().toString(), comboBox.getSelectedItem().toString());
				nameField.setText("");
				comboBox.setSelectedIndex(0);
			}
		});
		panel_3.add(createBtn);

		//createBtn.setIcon(new ImageIcon("createBtn.png"));
		panel_2 = new JPanel();
		panel_2.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_2.setBackground(Colors.accent);
		getContentPane().add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_5.setMaximumSize(new Dimension(200, 10000));
		panel_2.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		JLabel programsLbl = new JLabel("     Programs     ");
		programsLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		programsLbl.setFont(new Font("Rockwell", Font.PLAIN, 30));
		panel_5.add(programsLbl);
		
		scrollCom = new JPanel();
		scrollCom.setBackground(Color.WHITE);
		JScrollPane scrollPane = new JScrollPane((Component) scrollCom);
		scrollCom.setLayout(new BoxLayout(scrollCom, BoxLayout.Y_AXIS));
		scrollPane.setBorder(null);
		panel_5.add(scrollPane);
		
		
		for(Program program : Main.programs) {
			
			addCard(program);
			
		}
		panel_6 = new JPanel();
		panel_6.setBackground(Colors.accent);
		FlowLayout fl_panel_6 = new FlowLayout();
		fl_panel_6.setVgap(0);
		panel_6.setLayout(fl_panel_6);
		
		JPanel panel = new JPanel();
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.setBorder(null);
		
		panel_2.add(panel_6);
		panel_6.add(panel);
		panel.setBackground(Colors.accent);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel timeLblPanel = new JPanel();
		timeLblPanel.setBackground(Colors.accent);
		timeLblPanel.setLayout(new BoxLayout(timeLblPanel, BoxLayout.X_AXIS));
		
		JLabel timeLimitlbl = new JLabel("   Time Limits ");
		timeLimitlbl.setForeground(Color.WHITE);
		timeLimitlbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		timeLimitlbl.setFont(new Font("Rockwell", Font.PLAIN, 40));
		timeLimitlbl.setBorder(null);
		timeLblPanel.add(timeLimitlbl);
		
		
		
		panel.add(timeLblPanel);
		
		timeLimitSw = new JLabel("");
		timeLimitSw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				timeLimited = !timeLimited;
				if(timeLimited) {
					timeLimitSw.setIcon(new ImageIcon(FileSystem.resizeImage("switchon.png", 27, 27)));
				}else {
					timeLimitSw.setIcon(new ImageIcon(FileSystem.resizeImage("switchoff.png", 27, 27)));
				}
			}
		});
		timeLimitSw.setIcon(new ImageIcon(FileSystem.resizeImage("switchoff.png", 27, 27)));
		timeLblPanel.add(timeLimitSw);
		setTableData();
		
		JLabel lblNewLabel = new JLabel("(in mins)");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Leave blank to remove time limit");
		lblNewLabel_1.setFont(new Font("Rockwell", Font.PLAIN, 14));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblNewLabel_1);
		//panel.add(table.getTableHeader());
		panel.add(table);
		
		table.setMaximumSize(new Dimension(windowWidth/3, 200));
		table.getTableHeader().setMaximumSize(new Dimension(windowWidth/3, 20));
		table.getTableHeader().setBorder(new EmptyBorder(5,5,5,5));
		table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 13));
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(null);
		//table.setFont(new Font("Rockwell", Font.BOLD, 20));
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setBorder(null);
		table.setBackground(Colors.accent);
		table.setForeground(Color.WHITE);
		table.setRowHeight(20);
		panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_2.add(panel_1);
		panel_1.setBackground(Colors.accent);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		dtPanel = new JPanel();
		dtPanel.setBackground(Colors.accent);
		panel_1.add(dtPanel);
		dtPanel.setLayout(new BoxLayout(dtPanel, BoxLayout.Y_AXIS));
		
		panel_4 = new JPanel();
		panel_4.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_4.setBackground(Colors.accent);
		dtPanel.add(panel_4);
		panel_1.add(dtPanel);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		JLabel downtimesLbl = new JLabel("Downtimes");
		downtimesLbl.setForeground(Color.WHITE);
		downtimesLbl.setFont(new Font("Rockwell", Font.PLAIN, 40));
		panel_4.add(downtimesLbl);
		
		JLabel plusBtn = new JLabel("");
		plusBtn.setIcon(new ImageIcon("plusBtn.png"));
		plusBtn.setBorder(new EmptyBorder(5, 10, 0, 0));
		plusBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(selectedProgram == null) {
					Alert.createWarning("Please select a program to add a downtime.");
					return;
				}
				editingDt = false;
				DowntimeWindow win = new DowntimeWindow(null, null);
				
			}
		});
		panel_4.add(plusBtn);
		
		panel.setMinimumSize(new Dimension(0, windowHeight));
		
		JLabel saveBtn = new JLabel("Save");
		saveBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (timeLimited) {

					ArrayList<DayLimit> limits = new ArrayList<DayLimit>();
					for (int i = 0; i < 7; i++) {
						
						int secs = table.getModel().getValueAt(i, 1).toString() == null || table.getModel().getValueAt(i, 1).toString().isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(table.getModel().getValueAt(i, 1).toString()) * 60;
						
						DayLimit day = new DayLimit(i + 1, secs);
						
						limits.add(day);
					}

					selectedProgram.setDayLimits(limits);
				}else {
					
					selectedProgram.setDayLimits(new ArrayList<DayLimit>());
					
				}
			}
		});
		saveBtn.setBorder(new EmptyBorder(10, 0, 0, 0));
		saveBtn.setFont(new Font("Rockwell", Font.PLAIN, 40));
		saveBtn.setForeground(Color.WHITE);
		saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(saveBtn);
		
		JPanel blank = new JPanel();
		blank.setBackground(Colors.accent);
		
		panel_2.remove(panel_6);
		panel_2.remove(panel_1);
		panel_2.add(blank);
		panel_2.add(blank);
		setIconImage(Toolkit.getDefaultToolkit().getImage("frameIcon.png"));
		setTitle(Constants.PROGRAM_NAME);
		getContentPane().setBackground(Colors.primary);
		setVisible(true);
		
		
	}
	private void incrementSteps() {
		steps++;
		rotation+=.04;
	}
	private void resetSteps() {
		steps = 0;
		rotation = .01;
	}
	private void comboBoxUI(JComboBox comboBox) {
		
		
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
		@SuppressWarnings("unchecked")
		ComboBoxFilterDecorator<String> decorate = ComboBoxFilterDecorator.decorate(comboBox,
                CustomComboRenderer::getText,
                ProgramLimitsWindow::filter);
		comboBox.setRenderer(new CustomComboRenderer(decorate.getFilterTextSupplier()));
		comboBox.setEditable(true);
		comboBox.setBackground(Colors.secondary);
		comboBox.getEditor().getEditorComponent().setForeground(Color.white);
		JLabel editor = (JLabel) comboBox.getEditor().getEditorComponent();
		editor.setBorder(new MatteBorder(0,0,1,0,Color.WHITE));
	}
	private void createProgram(String name, String process) {
		// TODO: Check if there is already a program with this process
		System.out.println("Looking for process " + process);
		Program program = new Program(name, process);
		Main.programs.add(program);
		FileSystem.setIcon(program);
		addCard(program);
		Main.save();
		programSelected(name);
	}
	private ProgramCard addCard(Program program) {
		
		ProgramCard card = new ProgramCard(program.getName(), windowWidth/3, 50);
		card.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				card.toggleSelected();
				if(card.isSelected()) {
					for(ProgramCard c : cards) {
						if(c.isSelected()) {
							c.toggleSelected();
						}
					}
					panel_2.removeAll();
					panel_2.add(panel_5);
					panel_2.add(panel_6);
					panel_2.add(panel_1);
					Main.plw.setVisible(true);
					card.toggleSelected();
					programSelected(card.getName());
				}else {
					
					JPanel blank = new JPanel();
					blank.setBackground(Colors.accent);
					panel_2.remove(panel_6);
					panel_2.remove(panel_1);
					panel_2.add(blank);
					panel_2.add(blank);
					Main.plw.setVisible(true);
				}
				
				
			}
		});
		card.deleteBtn().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selection = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?");
				if(selection == JOptionPane.YES_OPTION) {
					deleteProgram(card);
				}
			}
		});
		if(program.getIconPath() != null && !program.getIconPath().isEmpty()) {
			System.out.println(program.getIconPath());
			card.setIcon((ImageIcon)FileSystem.getIconFromPath(program.getIconPath()));
		}
		cards.add(card);
		scrollCom.add(card);
		setVisible(true);
		return card;
		
	}
	private void programSelected(String name) {
		
		System.out.println("Program selected: " + name);
		selectedProgram = Main.searchPrograms(name);
		System.out.println("Program: " + selectedProgram.getName());
		if(selectedProgram.getDayLimits().size() == 0) {
			setTableData();
			System.out.println("No limits");
			timeLimited = false;
		}else {
			int[] times = new int[7];
			for(int i = 0; i < 7; i++) {
				
				times[i] = selectedProgram.getDayLimits().get(i).getLimit()/60;
				
			}
			timeLimited = true;
			setTableData(times);
		}
		clearDtPanel();
		for(Downtime dt : selectedProgram.getDowntimes()) {
			
			addDowntimeCard(dt);
			
		}
		if(timeLimited) {
			timeLimitSw.setIcon(new ImageIcon(FileSystem.resizeImage("switchon.png", 27, 27)));
		}else {
			timeLimitSw.setIcon(new ImageIcon(FileSystem.resizeImage("switchoff.png", 27, 27)));
		}
		
	}
	private void setTableData() {
		System.out.println("Table data cleared!");
		String[] columns = new String[]{ "Day","Time (in mins)"};
		String[][] data = new String[][]{ {"Mon", "0"},
			{"Tues", "0"},
			{"Wed", "0"},
			{"Thur", "0"},
			{"Fri", "0"},
			{"Sat", "0"},
			{"Sun", "0"}};
		DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable (int row, int col) {
                return col == 1;
            }
			
		};
		if(table == null) {
			table = new JTable();
		}
		table.setModel(model);
		table.setBorder(null);
		table.setFont(new Font("Rockwell", Font.BOLD, 20));
		setVisible(true);
	}
	private void setTableData(int[] times) {
		
		System.out.println("Table data set to: " + times.toString());
		String[] columns = new String[]{ "Day","Time (in mins)"};
		String[][] data = new String[7][2];
		data[0][0] = "Mon";
		data[1][0] = "Tues";
		data[2][0] = "Wed";
		data[3][0] = "Thur";
		data[4][0] = "Fri";
		data[5][0] = "Sat";
		data[6][0] = "Sun";
		for(int i = 0; i < 7; i++) {
			
			if( times[i] > 2000) {
				data[i][1] = "";
			}else {
				data[i][1] = times[i] + "";
			}
			
			
		}
		DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable (int row, int col) {
                return col == 1;
            }
			
		};
		table.setModel(model);
		table.setBorder(null);
		table.setFont(new Font("Rockwell", Font.BOLD, 20));
		setVisible(true);
	}
	public void deleteProgram(ProgramCard card) {
		
		Main.programs.remove(Main.searchPrograms(card.getName()));
		scrollCom.remove(card);
		cards.remove(card);
		pack();
		setVisible(true);
		Main.save();
	}
	public static boolean filter(String text, String other) {
		return text.toLowerCase().contains(other.toLowerCase());
	}
	public void addDowntime(LocalTime startTime, LocalTime endTime) {
		
		
		if(!editingDt && selectedProgram != null) {
			Downtime dt = new Downtime(startTime, endTime);
			selectedProgram.getDowntimes().add(dt);
			DowntimeCard card = new DowntimeCard(startTime, endTime, dt);
			dtCards.add(card);
			dtPanel.add(card);
			card.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			setVisible(true);
		}else if(editingDt && editingDtCard != null){
			dtPanel.remove(editingDtCard);
			selectedProgram.getDowntimes().remove(editingDtCard.getDownTime());
			dtCards.remove(editingDtCard); 
			
			Downtime dt = new Downtime(startTime, endTime);
			DowntimeCard card = new DowntimeCard(startTime, endTime, dt);
			card = new DowntimeCard(startTime, endTime, editingDtCard.getDownTime());
			card.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			dtCards.add(card);
			dtPanel.add(card);
			selectedProgram.getDowntimes().add(dt);
			card.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			setVisible(true);
		}
		
		Main.save();
	}
	// Solely UI version of the method
	public void addDowntimeCard(Downtime dt) {

		DowntimeCard card = new DowntimeCard(dt.getStartTime(), dt.getEndTime(), dt);
		dtCards.add(card);
		dtPanel.add(card);
		card.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setVisible(true);

	}
	
	public void setDtEditing(DowntimeCard instance) {
		editingDt = true;
		editingDtCard = instance;
		DowntimeWindow window = new DowntimeWindow(instance.getStart(), instance.getEnd());
	}
	
	public void removeDowntime(DowntimeCard card) {
		
		System.out.println("Removing " + card.getName());
		dtPanel.remove(card);
		dtCards.remove(card);
		selectedProgram.getDowntimes().remove(card.getDownTime());
		setVisible(true);
		
	}
	
	public void removeDowntimeCard(DowntimeCard card) {
		
		dtPanel.remove(card);
		dtCards.remove(card);
		//selectedProgram.getDowntimes().remove(card.getDownTime());
		setVisible(true);
		
	}
	
	private void clearDtPanel() {
		
		dtPanel.removeAll();
		dtPanel.add(panel_4);
		setVisible(true);
		
	}
}
