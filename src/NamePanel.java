import java.awt.*;
import javax.swing.*;

public class NamePanel extends JPanel{
	public NamePanel(){
		super(new BorderLayout());
        //Draw a border, and also create 10 pixels of padding
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
        		BorderFactory.createLineBorder(Color.BLACK)));
        
        JLabel softwareName = new JLabel("thetaByte", JLabel.CENTER);
        JLabel companyName = new JLabel("thetaByte Technologies, Inc.", JLabel.CENTER);
        
        //Create font from Bree Serif file
        Font breeSerif;
        
        try{
        	breeSerif = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("BreeSerif-Regular.ttf").openStream());
        } catch(Exception e){
        	breeSerif = null;
        }

        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(breeSerif);
        
        //Create the different font sizes
        Font breeSerifLarge = breeSerif.deriveFont(24f);
        Font breeSerifNormal = breeSerif.deriveFont(18f);
        
        softwareName.setFont(breeSerifLarge);
        companyName.setFont(breeSerifNormal);
        
        this.add(softwareName, BorderLayout.NORTH);
        this.add(companyName, BorderLayout.SOUTH);
	}
}
