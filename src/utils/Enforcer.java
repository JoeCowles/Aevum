package utils;
import java.io.IOException;
import java.time.LocalTime;

import main.Main;
import tracking.Downtime;
import tracking.Program;
import ui.Alert;

public class Enforcer {

	
	public static void Enforce(Program program) {
		
		// If the limit is a time limit
		if(program.isTimeLimit()) {
			if(program.timeLeftSecs() < 300 && program.timeLeftSecs() > 0) {
				System.out.println("Program has less than 5 mins left: " + program.getName());
				if(!program.stageEnforced()) {
					System.out.println("Program is going to be enforced");
					program.setEnforcementStage(Program.EnforceStage.WARN);
				}
				
			}else if(program.timeLeftSecs() <= 0){
				
				program.advanceStage();
				
			}
		}
		
		// Check if the program is approaching or in a downtime.
		for(Downtime downtime : program.getDowntimes()) {
			
			if(downtime.getStartTime().minusSeconds(300).isBefore(OS.now()) && OS.now().isBefore(downtime.getStartTime())) {
				
				if(!program.stageEnforced()) {
					program.setEnforcementStage(Program.EnforceStage.WARN);
				}
				
			}else if (downtime.getStartTime().isBefore(OS.now()) && downtime.getEndTime().isAfter(OS.now())){
				
				program.advanceStage();
				
			}
			
		}
		
		
		executeStage(program);
		
	}
	
	private static void executeStage(Program program) {
		
		if(program.stageEnforced()) {
			return;
		}
		
		switch(program.getStage()) {
			case RUNNING:
				return;
			case WARN:
				Alert.createWarning("Approaching time limit for: " + program.getName());
				break;
			case ENFORCE:
				try {
					Main.os.killProcess(program.getProcess());
					System.out.println("Closing the program");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case SHUTDOWN:
				//TODO: Shut down
				System.out.println("Shutting down");
				break;
		
		}
		
		
		program.setStageEnforced(true);
	}
}
