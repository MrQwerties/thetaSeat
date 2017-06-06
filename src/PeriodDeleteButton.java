import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PeriodDeleteButton extends JButton{
	private PeriodSelectionPanel master;
	
	public PeriodDeleteButton(PeriodSelectionPanel m){
		super(new ImageIcon());
		ImageIcon xIcon = new ImageIcon(getClass().getClassLoader().getResource("XIcon.png"));
		
		setIcon(xIcon);
		master = m;
		
		addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				    deleteSelf();
				  } 
				} );
		
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		
		//TODO add different icons for rollover and pressed
	}
	
	public void deleteSelf(){
		master.deleteSelf();
	}
}
