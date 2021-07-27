package tracking;

import java.io.Serializable;

public class DayLimit implements Serializable{
	
	public static final int SUNDAY = 7;
	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRIDAY = 5;
	public static final int SATURDAY = 6;
	
	private int weekday, limit;
	
	public DayLimit(int day, int limit) {
		
		weekday = day;
		this.limit = limit;
		
	}
	public int getWeekday() {
		return weekday;
	}
	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
