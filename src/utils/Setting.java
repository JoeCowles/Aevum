package utils;

import java.io.Serializable;
import java.time.LocalTime;

public class Setting implements Serializable{


	private static final long serialVersionUID = 461581606071462322L;
	
	private String username,password;
	private LocalTime date;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalTime getDate() {
		return date;
	}
	public void setDate(LocalTime date) {
		this.date = date;
	}
	
	
}
