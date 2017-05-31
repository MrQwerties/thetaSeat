import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AddPeriodPopup extends JPanel{
	public AddPeriodPopup(JTextField nameField, JTextField numberField) {
		super(new GridLayout(0, 1));
		
		Font normalFont = new Font("Arial", Font.PLAIN, 20);
		
		JLabel instructionsLabel = new JLabel("<html>Please enter the period number that you would like to add<br>as well as the title of the matching text file.</html>");
		JLabel fileLabel = new JLabel("Filename:");
		JLabel periodLabel = new JLabel("Period number:");
		
		nameField.setFont(normalFont);
		numberField.setFont(normalFont);
		instructionsLabel.setFont(normalFont);
		fileLabel.setFont(normalFont);
		periodLabel.setFont(normalFont);
		
		this.add(instructionsLabel);
		this.add(fileLabel);
		this.add(nameField);
		this.add(periodLabel);
		this.add(numberField);
	}
}
