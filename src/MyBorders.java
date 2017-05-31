import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class MyBorders {
	public static Border borderWithPadding(Color c, int thickness, int top, int left, int bottom, int right){
		return BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(top, left, bottom, right),
				BorderFactory.createLineBorder(c, thickness));
	}
	
	public static Border borderWithPadding(Color c, int top, int left, int bottom, int right){
		return borderWithPadding(c, 1, top, left, bottom, right);
	}
	
	public static Border borderWithPadding(Color c, int padding){
		return borderWithPadding(c, 1, padding, padding, padding, padding);
	}
	
	public static Border borderWithPadding(Color c, int thickness, int padding){
		return borderWithPadding(c, thickness, padding, padding, padding, padding);
	}
	
	public static Border borderWithPadding(int thickness, int top, int left, int bottom, int right){
		return borderWithPadding(Color.BLACK, thickness, top, left, bottom, right);
	}
	
	public static Border borderWithPadding(int top, int left, int bottom, int right){
		return borderWithPadding(Color.BLACK, 1, top, left, bottom, right);
	}
	
	public static Border borderWithPadding(int thickness, int padding){
		return borderWithPadding(thickness, padding, padding, padding, padding);
	}
	
	public static Border borderWithPadding(int padding){
		return borderWithPadding(1, padding);
	}
}
