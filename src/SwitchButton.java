import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SwitchButton extends ChartButton {
	public SwitchButton(ChartDisplay chart){
		super("SWITCH", chart);
		
		setEnabled(false);
		
		this.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
					chart.switchSelectedSeats();
				  }
				} );
	}
}
