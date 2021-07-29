package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalTime;

public class OS {
	
	public enum osType {
		MAC,
		LINUX,
		WINDOWS
	}
	
	public osType type = osType.WINDOWS;
	
	public OS() {
		init();
	}
	public void init() {
		// TODO: Confirm MAC and create Linux
		String os = System.getProperty("os.name");

		if(os.contains("Windows")) {
			type = osType.WINDOWS;
		}else if(os.contains("IOS")){
			type = osType.MAC;
		}

		
	}
	public void killProcess(String process) throws IOException{
		
		switch(type) {
			case WINDOWS:
				Runtime.getRuntime().exec("taskkill /IM "+ process+" /t /f");
				break;
			default:
				break;
				
		}
	}

	public String[] runningPrograms() throws IOException {

		Process p = null;
		String[] programs = null;
		switch (type) {
			case WINDOWS:
				p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe -fo list");
				BufferedReader inStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
	
				String input = "";
				String data = "";
	
				while ((input = inStream.readLine()) != null) {
					if (!input.isEmpty()) {
						data += input + "\n";
					}
				}
				String[] temp = data.split("\n");
				programs = new String[temp.length / 5];
				int x = 0;
				for (int i = 0; i < temp.length; i++) {
					if (temp[i].contains("Image Name:")) {
						programs[x] = temp[i].replaceAll("Image Name:", "").replaceAll("\s", "");
						x++;
					}
				}
			break;
		}

		return programs;
	}
	
	public String getMAC() {
		
		InetAddress ip;
		String MAC = "";
		try {

			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			MAC = sb.toString();
		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e) {

			e.printStackTrace();

		}
		return MAC;

	}
	
	public static LocalTime now() {
		// TODO: switch methods to use inet
		return LocalTime.now();
	}
	
}
