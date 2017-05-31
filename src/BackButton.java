import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class BackButton extends JButton {
	public BackButton(){
		super("BACK");
		
		this.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				    goBack();
				  } 
				} );
	}
	
	public void goBack(){
		JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
		parent.dispose();
		JFrame newPage = new MainMenu();
		newPage.setVisible(true);
	}
}
