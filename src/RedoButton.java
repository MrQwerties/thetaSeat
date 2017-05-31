import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RedoButton extends ChartButton {
		public RedoButton(ChartDisplay chart){
			super("REDO", chart);
			
			setEnabled(false);
			
			this.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent e) { 
						chart.redo();
					  }
					} );
		}
}
