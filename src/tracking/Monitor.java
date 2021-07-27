package tracking;

import java.io.IOException;
import java.util.TimerTask;

import main.Main;
import utils.Enforcer;

public class Monitor extends TimerTask{

	

	@Override
	public void run() {

		String runningPrograms = "";
		
		// Check if the program should continue running 
		
		
		// Look for running programs
		try {
			for (String running : Main.os.runningPrograms()) {
				runningPrograms += running + " ";
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		// Program incentives
		
		
		// Enforce limits
		for (Program program : Main.programs) {

			if (runningPrograms.contains(program.getProcess())) {

				if (program.isTimeLimit() && !program.isGrouped()) {
					System.out
							.println("Program running: " + program.getName() + " Time left: " + program.timeLeftSecs());
					program.setTimeLeft(program.timeLeftSecs() - (int) (Main.REFRESH_RATE / 1000));
					System.out.println(
							"Program running: " + program.getName() + "  New Time left: " + program.timeLeftSecs());
					Enforcer.Enforce(program);
				}
				

			}

		}
		// Loop through the groups and look for running programs
		for(ProgramGroup group : Main.groups) {
			
			boolean incentivesRunning = false;
			
			for(Program p : group.getIncentives()) {
				if(runningPrograms.contains(p.getProcess())) {
					incentivesRunning = true;
					group.setTimeRemaining(group.getTimeRemaining() - (int) (Main.REFRESH_RATE / 1000));
				}
			}
			
			boolean programsRunning = false;
			if(!incentivesRunning) {
				
				for(Program p : group.getPrograms()) {
					
					if(runningPrograms.contains(p.getProcess())) {
						
						programsRunning = true;
						
					}
				}
				
				if(programsRunning) {
					
					group.setTimeRemaining(group.getTimeRemaining() + (int)((Main.REFRESH_RATE / 1000) * group.getRatio()));
					
				}
				
			}
			
			
			
		}

	}

}
