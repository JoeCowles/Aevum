package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Main;
import tracking.Downtime;
import utils.Colors;
import utils.FileSystem;

public class DowntimeCard extends JPanel{
	
	LocalTime start, end;
	private Downtime downtime;
	public DowntimeCard(LocalTime start, LocalTime end, Downtime downtime) {
		this.downtime = downtime;
		this.start = start;
		this.end = end;
		DowntimeCard instance = this;
		String startTime  = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(start);
		String endTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(end);
		JLabel times = new JLabel(startTime + " - " + endTime);
		JLabel deleteBtn = new JLabel("");
		JLabel editBtn = new JLabel("");
		editBtn.setIcon(new ImageIcon(FileSystem.resizeImage("editbtn.png", 27, 27)));
		deleteBtn.setIcon(new ImageIcon(FileSystem.resizeImage("deleteBtn.png", 27, 27)));
		times.setFont(new Font("Rockwell", Font.BOLD, 20));
		times.setForeground(Colors.secondary);
		editBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Main.plw.setDtEditing(instance);
				
			}
		});
		deleteBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Main.plw.removeDowntime(instance);
				
			}
		});
		this.setMinimumSize(new Dimension(100, 35));
		this.setMaximumSize(new Dimension(500, 35));
		this.add(deleteBtn);
		this.add(editBtn);
		this.add(times);
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		
		
	}
	public LocalTime getStart() {
		return start;
	}
	public LocalTime getEnd() {
		return end;
	}
	public Downtime getDownTime() {
		return downtime;
	}
}
 