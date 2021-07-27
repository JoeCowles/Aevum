package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;

import tracking.Monitor;
import tracking.Program;
import tracking.ProgramGroup;
import ui.InputWindow;
import ui.windows.DowntimeWindow;
import ui.windows.IncentivesWindow;
import ui.windows.LoginWindow;
import ui.windows.MainGUI;
import ui.windows.ProgramLimitsWindow;
import utils.FileSystem;
import utils.OS;

public class Main {

	public static OS os;
	public static ArrayList<Program> programs;
	public static ArrayList<ProgramGroup> groups;
	
	public static ProgramLimitsWindow plw;
	public static final long REFRESH_RATE = 5000l;
	
	public static void main(String[] args) {
		
		// Check the temp file in the install directory.
		
		// If the program is already running, kill it.
		
		// Check if the subscription is still valid
		
		// Load programs from the file
		/*Program program = new Program("Brave", "brave.exe");
		Downtime test = new Downtime(OS.now().plusSeconds(15), OS.now().plusMinutes(15));
		program.getDowntimes().add(test);
		programs.add(program);*/
		groups = new ArrayList<ProgramGroup>(); 
		try {
			programs = FileSystem.loadPrograms();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(programs == null) {
			programs = new ArrayList<Program>();
		}
		debug();
		// If computer is in downtime, give the user 2 min to connect to internet etc.
		
		// Then start enforcing downtimes/program limits
		
		// Check periodically if the program should stop
		
		// Check to see if it is the next day
		
		
		Timer timer = new Timer();
		timer.schedule(new Monitor(), 0l, REFRESH_RATE);
		
	}
	public static void debug() {
		os = new OS();
		//InputWindow in = new InputWindow("name");
		//plw = new ProgramLimitsWindow();
		IncentivesWindow win = new IncentivesWindow();
		//DowntimeWindow dw = new DowntimeWindow(LocalTime.now(), LocalTime.MIDNIGHT);
		//LoginWindow win = new LoginWindow();
		//MainGUI maingui = new MainGUI();
		
		
	}
	
	public static void save() {
		
		try {
			
			FileSystem.savePrograms(programs);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static Program searchPrograms(String name) {
		Program program = null;
		for(Program p : programs) {
			if(name.equals(p.getName())) {
				program = p;
			}
			
		}
		return program;
	}
	
	public static boolean dayChanged() {
		
		//TODO: This
		
		return false;
		
	}
	public static ProgramGroup searchProgramsGroups(String name) {
		ProgramGroup pGroup = null;
		for(ProgramGroup pg : groups) {
			if(name.equals(pg.getName())) {
				pGroup = pg;
			}
			
		}
		return pGroup;
	}
}
