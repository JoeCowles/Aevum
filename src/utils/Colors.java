package utils;

import java.awt.Color;

public class Colors {
	
	public static Color primary = hex2Rgb("#67809f");
	public static Color secondary = hex2Rgb("#2e3131");
	public static Color accent = hex2Rgb("#6c7a89");
	public static Color gray = hex2Rgb("#5a5b5a");
	
	public static  Color hex2Rgb(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
}
