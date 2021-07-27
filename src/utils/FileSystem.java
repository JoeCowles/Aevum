package utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

import main.Main;

import tracking.Program;

public class FileSystem {
	
	private static File settingsFile;
	private static File programFile;
	private static String iconPath;
	public static void savePrograms(ArrayList<Program> programs) throws FileNotFoundException, IOException {
		
		programFile = new File("programs.bin");
		if(!programFile.exists()) {
			programFile.createNewFile();
		}
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(programFile));
		for(Program p : programs) {
			
			oos.writeObject(p);
			
		}
	}
	public static ArrayList<Program> loadPrograms() throws IOException {
		
		programFile = new File("programs.bin");
		if(!programFile.exists()) {
			programFile.createNewFile();
		}
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(programFile));
		ArrayList<Program> programs = new ArrayList<Program>();
		
		while(true) {
			try {
				
				Program p = (Program) ois.readObject();
				programs.add(p);
				
			}catch(Exception e) {
				break;
			}
		}
		
		return programs;
		
	}
	public static void saveDowntimes() {
		
	}
	public static void loadDowntimes() {
		
	}
	
	public static void saveSettings(Setting setting) throws FileNotFoundException, IOException {
		
		settingsFile = new File("settings.bin");
		if(!settingsFile.exists()) {
			settingsFile.createNewFile();
		}
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(settingsFile));
		
		oos.writeObject(setting);
	}
	public static Setting loadSettings() throws FileNotFoundException, IOException, ClassNotFoundException {
		settingsFile = new File("settings.bin");
		if(!settingsFile.exists()){
			return null;
		}
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(settingsFile));
		
		
		return (Setting) ois.readObject();
	}
	
	
	public static Image resizeImage(String path, int width, int height) {
		
		
		Image image = Toolkit.getDefaultToolkit().createImage(path);
		return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
	}
	public static BufferedImage toBufferedImage(Image img){
	    if (img instanceof BufferedImage){
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	public static void setIcon(Program program) {
		
		if(Main.os.type == OS.osType.WINDOWS) {
			final String dir1 = File.listRoots()[0].getAbsolutePath() + File.separator + "Program Files (x86)" + File.separator;
			//final String dir2 = File.listRoots()[0].getAbsolutePath() + File.separator + "Program Files" + File.separator;
		
			Thread iconRetriever = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Icon icon = extractIcon(dir1, program.getProcess());
					if(icon != null) {
						program.setIconPath(iconPath);
						Main.save();
						return;
					}
					
					
				}
				
				
			});
			iconRetriever.start();
			
		} // TODO: Support more os types
		
	}
	
	
	public static Icon extractIcon(String name) {
		 
		String dir = "";
		if(Main.os.type == OS.osType.WINDOWS) {
			dir = File.listRoots()[0].getAbsolutePath() + File.separator + "Program Files (x86)" + File.separator;
		} // TODO: Support more os types
		
		iconPath = "";
		if(findFile(dir, name)) {
			
			return FileSystemView.getFileSystemView().getSystemIcon(new File(iconPath));
			
			
		}else {
			return null;
		}
		
	}
	public static Icon extractIcon(String dir, String name) {
		
		iconPath = "";
		if(findFile(dir, name) && !iconPath.isEmpty()) {
			
			return FileSystemView.getFileSystemView().getSystemIcon(new File(iconPath));
			
			
		}else {
			return null;
		}
		
	}
	public static Icon getIconFromPath(String path) {
		
		return FileSystemView.getFileSystemView().getSystemIcon(new File(path));

		
	}
	public static boolean findFile(String parentDirectory, String filename) {
		
		File[] filesInDirectory = new File(parentDirectory).listFiles();
		  //Added this line.

		  if(filesInDirectory != null){   
		    for(File f : filesInDirectory){   
		      if(f.isDirectory()){   
		    	  if(findFile(f.getAbsolutePath(), filename)) {
		    		  return true;
		    	  }
		      }   
		      System.out.print(f.getName() + " ");  
		      if(f.getName().toLowerCase().contains(filename.toLowerCase())) {
		    	  System.out.println("Found the File! === ");  
		    	  //TODO: Extract icon
		    	  iconPath = f.getPath();
		    	  return true;
		      }
		                 
		    }
		  }
		System.out.println("");
		return false;
	}
}
