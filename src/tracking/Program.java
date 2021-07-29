package tracking;

import java.io.Serializable;
import java.util.ArrayList;


public class Program extends TrackedObj implements Serializable{
	


	private ArrayList<DayLimit> dayLimits;
	
	private String name, process;
	private ArrayList<Downtime> downtimes;
	private String iconPath;
	
	// Time limit is in seconds.
	private int timeLimit = 0;
	private int timeLeft = 0;
	
	
	private boolean grouped = false;
	
	public Program(String name, String process) {
		super();
		this.name = name;
		this.process = process;
		downtimes = new ArrayList<Downtime>();
		dayLimits = new ArrayList<DayLimit>();
		stage = EnforceStage.RUNNING;
		
	}
	public void setDowntimes(ArrayList<Downtime> downtimes) {
		
		this.downtimes = downtimes;
		
	}
	public ArrayList<Downtime> getDowntimes() {
		
		return downtimes;
		
	}
	
	public int timeLimitSecs() {
		return timeLimit;
	}
	public int timeLeftSecs() {
		return timeLeft;
	}
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	/*
	 * Used for standard limits
	 */
	public void setTimeLimit(int timeLimit) {
		
		this.timeLimit = timeLimit;
		// Change this to > if you don't want to include banned programs in isTimeLimit ---------- OUTDATED
		
	}
	public String getName() {

		return name;
	}

	public String getProcess() {

		return process;
		
	}
	
	public boolean isTimeLimit() {
		
		return getDayLimits().size() > 0;
		
	}
	public void setWeekday(int day) {
		
		for(DayLimit lim : dayLimits) {
			if(lim.getWeekday() == day) {
					
				setTimeLimit(lim.getLimit());
				setTimeLeft(timeLimitSecs());
					
			}	
		}

		
	}
	public ArrayList<DayLimit> getDayLimits() {
		return dayLimits;
	}
	public void setDayLimits(ArrayList<DayLimit> dayLimits) {
		this.dayLimits = dayLimits;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public boolean isGrouped() {
		return grouped;
	}
	public void setGrouped(boolean grouped) {
		this.grouped = grouped;
	}
}
