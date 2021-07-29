package tracking;

import java.util.ArrayList;

public class ProgramGroup extends TrackedObj{


	private ArrayList<Program> programs;
	private ArrayList<Program> incentives;
	private String name;
	private int time;
	private boolean timed;
	private float ratio; // Ratio of program time to incentive time
	private int timeRemaining;
	
	public ProgramGroup(String name) {
		super();
		this.name = name;
		ratio = 1.0f;
		programs = new ArrayList<Program>();
		incentives = new ArrayList<Program>();
	}

	public ArrayList<Program> getPrograms() {
		return programs;
	}

	public void setPrograms(ArrayList<Program> programs) {
		this.programs = programs;
	}

	public ArrayList<Program> getIncentives() {
		return incentives;
	}

	public void setIncentives(ArrayList<Program> incentives) {
		this.incentives = incentives;
	}

	public String getName() {
		return name;
	}

	public float getRatio() {
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public boolean isTimed() {
		return timed;
	}

	public void setTimed(boolean timed) {
		this.timed = timed;
	}
	public boolean containsProgram(Program program) {
		
		for(Program p : programs) {
			if(program.equals(p)) {
				return true;
			}
		}
		
		return false;
	}
	public boolean containsIncentive(Program program) {
		
		for(Program p : incentives) {
			if(p.equals(program)) {
				return true;
			}
		}
		return false;
	}

	public int getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(int timeRemaining) {
		this.timeRemaining = timeRemaining;
	}
}
