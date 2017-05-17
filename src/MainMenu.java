import java.awt.*;
import javax.swing.*;

public class MainMenu extends JFrame{	
	public MainMenu(){
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("thetaSeat");
        this.setSize(500, 500);
        
        //Put page in middle of screen
        this.setLocationRelativeTo(null);
        
        this.add(new NamePanel(), BorderLayout.NORTH);
        this.add(new PeriodsPanel(), BorderLayout.CENTER);
	}
}