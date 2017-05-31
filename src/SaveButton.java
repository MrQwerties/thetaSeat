import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SaveButton extends ChartButton {
	public SaveButton(ChartDisplay chart){
		super("SAVE", chart);
		
		this.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				    myChart.exportImage("LOL.png");
				  }
				} );
	}
}
