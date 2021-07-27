package tracking;

import java.io.Serializable;
import java.time.LocalTime;

public class Downtime implements Serializable{
	
	private LocalTime startTime, endTime;
	
	public Downtime(LocalTime startTime, LocalTime endTime) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	
	
	
}
