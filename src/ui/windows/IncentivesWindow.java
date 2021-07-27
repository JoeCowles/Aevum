package ui.windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import main.Main;
import tracking.Program;
import tracking.ProgramGroup;
import ui.ComboboxScrollbarUI;
import ui.InputWindow;
import ui.ProgramCard;
import ui.combobox.ComboBoxFilterDecorator;
import ui.combobox.CustomComboRenderer;
import utils.Colors;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;
import java.awt.Component;
import javax.swing.JSpinner;

public class IncentivesWindow extends JFrame{
	
	private JTextField textField;
	private JComboBox<String> comboBox;
	private JPanel refreshPanel = null;
	double rotation = .01;
	int steps = 0;
	private JPanel pgList, bottomPanel, panel_1, blank, list, programList;
	ProgramGroup selectedPg;
	public IncentivesWindow() {

		setBounds(100, 100, 985, 614);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		comboBox = new JComboBox<String>();
		
		JPanel panel_3 = new JPanel();
		panel_3.setMaximumSize(new Dimension(4400, 100));
		panel_3.setBorder(null);
		panel_3.setBackground(new Color(46, 49, 49));
		panel_3.setMaximumSize(new Dimension(5000, 45));
		getContentPane().add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		
		JLabel programLbl = new JLabel("Add Program  ");
		programLbl.setForeground(Color.WHITE);
		programLbl.setFont(new Font("Rockwell", Font.PLAIN, 20));
		programLbl.setBorder(new EmptyBorder(10, 0, 10, 0));
		panel_3.add(programLbl);
		
		
		
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
		} catch(IOException e) {
			e.printStackTrace();
		}
		refreshPanel.setBackground(new Color(46, 49, 49));
		panel_3.add(refreshPanel);
		
		
		comboBox.setMinimumSize(new Dimension(100, 30));
		comboBox.setMaximumSize(new Dimension(200, 30));
		comboBox.setFont(new Font("Rockwell", Font.PLAIN, 15));
		comboBox.setEditable(true);
		comboBox.setBorder(null);
		comboBox.setBackground(new Color(46, 49, 49));
		panel_3.add(comboBox);
		
		textField = new JTextField();
		textField.setText("Program name");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Rockwell", Font.PLAIN, 17));
		textField.setColumns(10);
		textField.setBorder(null);
		panel_3.add(textField);
		
		JLabel createBtn = new JLabel("   Create");
		createBtn.setForeground(Color.WHITE);
		createBtn.setFont(new Font("Rockwell", Font.PLAIN, 20));
		panel_3.add(createBtn);
		
		bottomPanel = new JPanel();
		getContentPane().add(bottomPanel);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.setBackground(Colors.accent);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setMaximumSize(new Dimension(300, 4000));
		panel.setMinimumSize(new Dimension(300, 4000));
		bottomPanel.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel programLblPanel = new JPanel();
		panel.add(programLblPanel);
		programLblPanel.setLayout(new BoxLayout(programLblPanel, BoxLayout.X_AXIS));
		programLblPanel.setBackground(Color.WHITE);
		
		JLabel programsLbl = new JLabel("  Program Groups  ");
		programsLbl.setForeground(new Color(46, 49, 49));
		programsLbl.setFont(new Font("Rockwell", Font.PLAIN, 24));
		programLblPanel.add(programsLbl);
		
		JLabel addGroupBtn = new JLabel("");
		addGroupBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				InputWindow in = new InputWindow("name");
				in.getBtn().addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						String name = in.getField();
						addProgramGroup(name);
						in.dispose();

					}
				});
			}
		});
		
		programLblPanel.add(addGroupBtn);
		addGroupBtn.setIcon(new ImageIcon("plusBtn.png"));
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBorder(null);
		panel.add(scrollPane_3);
		
		pgList = new JPanel();
		pgList.setBackground(Color.WHITE);
		pgList.setBorder(null);
		scrollPane_3.setViewportView(pgList);
		pgList.setLayout(new BoxLayout(pgList, BoxLayout.Y_AXIS));
		
		panel_1 = new JPanel();
		bottomPanel.add(panel_1);
		panel_1.setBackground(Colors.accent);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Colors.accent);
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(null);
		panel_2.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		panel_5.add(panel_6);
		panel_6.setMaximumSize(new Dimension(10000, 130));
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel(" Incentives ");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Rockwell", Font.PLAIN, 26));
		panel_6.add(lblNewLabel);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		scrollPane_1.setBackground(Color.WHITE);
		panel_5.add(scrollPane_1);
		
		list = new JPanel();
		list.setBackground(Colors.accent);
		list.setBorder(new MatteBorder(0, 0, 0, 1, Colors.accent));
		scrollPane_1.setViewportView(list);
		list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
		
		JPanel panel_10 = new JPanel();
		panel_10.setBackground(Color.WHITE);
		FlowLayout flowLayout_5 = (FlowLayout) panel_10.getLayout();
		flowLayout_5.setHgap(0);
		flowLayout_5.setVgap(0);
		panel_6.add(panel_10);
		//panel_10.setBackground(Colors.accent);
		
		JComboBox<String> addInsCombo = new JComboBox<String>();
		addInsCombo.setFont(new Font("Rockwell", Font.PLAIN, 18));
		panel_10.add(addInsCombo);
		comboBoxUI(addInsCombo, Color.white);
		for(Program p : Main.programs) {
			
			addInsCombo.addItem(p.getName());
			
		}
		JLabel addInsBtn = new JLabel("");
		addInsBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String name = addInsCombo.getSelectedItem().toString();
				System.out.println("adding " + name );
				Program p = Main.searchPrograms(name);
				selectedPg.getIncentives().add(p);
				addIncentiveCard(name);
				
			}
		});
		addInsBtn.setIcon(new ImageIcon("plusBtn.png"));
		panel_10.add(addInsBtn);
		
		JPanel panel_7 = new JPanel();
		panel_2.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.WHITE);
		panel_7.add(panel_8);
		panel_8.setMaximumSize(new Dimension(10000, 100));
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));
		
		JLabel intLbl = new JLabel("Programs");
		intLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		intLbl.setHorizontalAlignment(SwingConstants.CENTER);
		intLbl.setFont(new Font("Rockwell", Font.PLAIN, 26));
		panel_8.add(intLbl);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBorder(null);
		panel_7.add(scrollPane_2);
		
		programList = new JPanel();
		scrollPane_2.setViewportView(programList);
		programList.setBackground(Colors.accent);
		programList.setLayout(new BoxLayout(programList, BoxLayout.Y_AXIS));
		
		JPanel panel_11 = new JPanel();
		panel_11.setBackground(Color.WHITE);
		FlowLayout flowLayout = (FlowLayout) panel_11.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panel_8.add(panel_11);
		
		JComboBox<String> programCombo = new JComboBox<String>();
		programCombo.setFont(new Font("Rockwell", Font.PLAIN, 18));
		programCombo.setEditable(true);
		panel_11.add(programCombo);
		comboBoxUI(programCombo, Color.white);
		for(Program p : Main.programs) {
			
			programCombo.addItem(p.getName());
			
		}
		JLabel addProgramBtn = new JLabel("");
		addProgramBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String name = programCombo.getSelectedItem().toString();
				System.out.println("adding " + name );
				Program p = Main.searchPrograms(name);
				selectedPg.getPrograms().add(p);
				addProgramCard(name);
				
			}
		});
		panel_11.add(addProgramBtn);
		addProgramBtn.setIcon(new ImageIcon("plusBtn.png"));
		
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_4.getLayout();
		flowLayout_2.setVgap(0);
		flowLayout_2.setHgap(0);
		panel_1.add(panel_4);
		panel_4.setBackground(Colors.accent);
		
		JLabel lblNewLabel_1 = new JLabel("For every  ");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Rockwell", Font.PLAIN, 18));
		panel_4.add(lblNewLabel_1);
		
		JSpinner spinner = new JSpinner();
		panel_4.add(spinner);
		
		JLabel lblNewLabel_2 = new JLabel("  minutes\r\n spent on incentivized programs, replenish  \r\n");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Rockwell", Font.PLAIN, 18));
		panel_4.add(lblNewLabel_2);
		JScrollPane scrollPane = new JScrollPane();
		
		
		comboBoxUI(comboBox);
		try {
			String temp = "";
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
		selectedPg("");
		setVisible(true);
		
	}
	private void comboBoxUI(JComboBox<String> comboBox) {
		
		
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
                IncentivesWindow::filter);
		comboBox.setRenderer(new CustomComboRenderer(decorate.getFilterTextSupplier()));
		comboBox.setEditable(true);
		comboBox.setBackground(Colors.secondary);
		comboBox.getEditor().getEditorComponent().setForeground(Color.white);
		JLabel editor = (JLabel) comboBox.getEditor().getEditorComponent();
		editor.setBorder(new MatteBorder(0,0,1,0,Color.WHITE));
		
		
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
				  JButton dropdown = new JButton(new ImageIcon("downArrowBlack.png"));
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
                IncentivesWindow::filter);
		comboBox.setRenderer(new CustomComboRenderer(decorate.getFilterTextSupplier()));
		comboBox.setEditable(true);
		comboBox.setBackground(color);
		comboBox.getEditor().getEditorComponent().setForeground(Color.BLACK);
		JLabel editor = (JLabel) comboBox.getEditor().getEditorComponent();
		editor.setBorder(new MatteBorder(0,0,1,0,Color.BLACK));
		
		
	}
	private void addProgramGroup(String name) {
		
		ProgramGroup pg = new ProgramGroup(name);
		Main.groups.add(pg);
		ProgramCard card = new ProgramCard(name, 200, 30);
		pgList.add(card);
		card.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				card.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						
						if(!card.isSelected()) {
							for(Component com : pgList.getComponents()) {
								ProgramCard c = (ProgramCard) com;
								if(c.isSelected()) {
									c.toggleSelected();
								}
							}
							card.toggleSelected();
							selectedPg(card.getName());
						}else {
							
							// Card unselected
							
							
						}
						
						
					}
				});
				
			}
		});
		setVisible(true);
		
	}
	private void repaintRefresh() {
		
		refreshPanel.repaint();
		
	}
	private void resetSteps() {
		steps = 0;
		rotation = 0;
	}
	private void incrementSteps() {
		rotation += .01;
		steps++;
	}
	
	public static boolean filter(String text, String other) {
		return text.toLowerCase().contains(other.toLowerCase());
	}
	
	private void selectedPg(String name) {
		
		if(name.isEmpty()) {
			
			//TODO: clear screen
			blank = new JPanel(new FlowLayout());
			bottomPanel.remove(panel_1);
			bottomPanel.add(blank);
			setVisible(true);
			return; 
			
		}else {
			if(blank != null && compContains(bottomPanel, blank)) {
				
				bottomPanel.remove(blank);
				bottomPanel.add(panel_1);
				setVisible(true);
				
			}
			// TODO: populate
			
		}
		
		selectedPg = Main.searchProgramsGroups(name);
		// TODO: fill data
		list.removeAll();
		programList.removeAll();
		
		for(Program p : selectedPg.getIncentives()) {
			
			addIncentiveCard(p.getName());
			
		}
		
		for(Program p : selectedPg.getPrograms()) {
			
			addProgramCard(p.getName());
			
		}
		
		
		
	}
	
	private void addIncentiveCard(String name) {
		
		ProgramCard card = new ProgramCard(name, 1000, 30); 
		list.add(card);
		card.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				card.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						if (!card.isSelected()) {
							for (Component com : list.getComponents()) {
								ProgramCard c = (ProgramCard) com;
								if (c.isSelected()) {
									c.toggleSelected();
								}
							}
							card.toggleSelected();
							//selectedPg(card.getName());
						} else {

							// Card unselected

						}

					}
				});

			}

		});
		card.setBorder(new MatteBorder(0, 0, 1, 0, Colors.accent));
		setVisible(true);
		
	}
	private void addProgramCard(String name) {
		
		ProgramCard card = new ProgramCard(name, 1000, 30);
		card.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				card.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						if (!card.isSelected()) {
							for (Component com : programList.getComponents()) {
								ProgramCard c = (ProgramCard) com;
								if (c.isSelected()) {
									c.toggleSelected();
								}
							}
							card.toggleSelected();
							//selectedPg(card.getName());
						} else {

							// TODO: Card unselected

						}

					}
				});

			}

		});
		card.setBorder(new MatteBorder(0, 0, 1, 0, Colors.accent));
		programList.add(card);
		setVisible(true);
		
	}
	private boolean compContains(JComponent com, Component child) {
		for(Component comp : com.getComponents()) {
			
			if(comp.equals(child)) {
				return true;
				
			}
		}
		return false;
	}
}
