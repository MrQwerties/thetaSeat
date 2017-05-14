import java.awt.*;
import javax.swing.*;

public class MainMenu {	
	public MainMenu(){
		showGUI();
	}
	
	public void showGUI(){
        JFrame menuPage = new JFrame();
        
        menuPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPage.setTitle("thetaSeat");
        menuPage.setSize(500, 500);
        
        //Put page in middle of screen
        menuPage.setLocationRelativeTo(null);
        
        menuPage.add(new NamePanel(), BorderLayout.NORTH);
        menuPage.add(new PeriodsPanel(), BorderLayout.CENTER);
        
        menuPage.setVisible(true);
	}
}