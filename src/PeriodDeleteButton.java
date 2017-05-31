import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PeriodDeleteButton extends JButton{
	private PeriodSelectionPanel master;
	
	public PeriodDeleteButton(PeriodSelectionPanel m){
		super(new ImageIcon("XIcon.png", "X"));
		master = m;
		
		this.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				    deleteSelf();
				  } 
				} );
		
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		
		//TODO add different icons for rollover and pressed
	}
	
	public void deleteSelf(){
		master.deleteSelf();
	}
}
