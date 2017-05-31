import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SaveButton extends ChartButton {
	public SaveButton(ChartDisplay chart){
		super("SAVE", chart);
		
		setEnabled(false);
		
		this.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
					JTextField nameField = new JTextField(20);
				    if(JOptionPane.showConfirmDialog(null, new SaveImagePopup(nameField), "SAVE", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
				    	myChart.exportImage(nameField.getText());
				    }
				  }
				} );
	}
}
