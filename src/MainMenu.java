import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class MainMenu extends JFrame{	
	public MainMenu(){
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("thetaSeat");
        this.setSize(600, 700);
        
        //Put page in middle of screen
        this.setLocationRelativeTo(null);
        
        JPanel bottomContainer = new JPanel(new BorderLayout());
        ImageIcon logo = new ImageIcon("thetaByte_logo.png", "logo");
        Image logoImage = getScaledImage(logo.getImage(), 150, 150);
        bottomContainer.add(new JLabel(new ImageIcon(logoImage)), BorderLayout.WEST);
        
        PeriodsPanel periodSelect = new PeriodsPanel();
        
        JPanel bottomMiddle = new JPanel(new BorderLayout());
        bottomMiddle.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 50));
        bottomMiddle.add(new AddPeriodButton(periodSelect), BorderLayout.WEST);
        bottomMiddle.add(new GenerateButton(periodSelect), BorderLayout.EAST);
        
        bottomContainer.add(bottomMiddle, BorderLayout.CENTER);
        
        this.add(new NamePanel(), BorderLayout.NORTH);
        this.add(periodSelect, BorderLayout.CENTER);
        this.add(bottomContainer, BorderLayout.SOUTH);
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
}