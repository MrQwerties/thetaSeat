import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Constants{
	public static final String PERIOD_LIST_FILE_NAME = "thetaSeat_period_list";
	public static final Color BACKGROUND_COLOR = Color.WHITE;
	
	public static void hideFile(String name){
		setHidden(name, true);
	}

	public static void unhideFile(String name) {
		setHidden(name, false);
	}
	
	public static void setHidden(String name, boolean hidden){
		Path path = FileSystems.getDefault().getPath(name);
		try{
			Files.setAttribute(path, "dos:hidden", hidden);
		} catch(IOException e){
		}
	}
	
	public static void deleteFile(String name){
		File f = new File(name);
		
		f.delete();
	}

	public static String getTwosieDataFileName(int period) {
		return "thetaSeat_period" + Integer.toString(period) + "_data";
	}
	
	public static String addExtension(String fileName, String extension){
		if(fileName.length() < extension.length() || !fileName.substring(fileName.length() - extension.length()).equalsIgnoreCase(extension)){
			fileName += extension;
		}
		
		return fileName;
	}
}
