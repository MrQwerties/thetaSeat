import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GenerateButton extends JButton{
	PeriodsPanel master;
	
	public GenerateButton(PeriodsPanel m){
		super("GENERATE");
		
		master = m;
		
		this.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				    generateChart();
				  } 
				} );
	}
	
	public void generateChart(){
		Frame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
		if(master.getSelectedPeriod() != -1){
			parent.dispose();
			//TODO: Make period selectable
			JFrame newPage = new SecondPage(master.getSelectedPeriod());
			newPage.setVisible(true);
		} else{
			JOptionPane.showMessageDialog(parent, "Please select a period from the list before clicking GENERATE.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
