import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SaveImagePopup extends JPanel {
	public SaveImagePopup(JTextField nameField){
		super(new GridLayout(0, 1));
		
		Font normalFont = new Font("Arial", Font.PLAIN, 20);
		
		JLabel instructionsLabel = new JLabel("<html>The current seating chart will be saved as a PNG image to<br>the same folder as the program. Please input the desired name of the image.</html>");
		JLabel fileLabel = new JLabel("Image Name:");
		
		nameField.setFont(normalFont);
		instructionsLabel.setFont(normalFont);
		fileLabel.setFont(normalFont);
		
		this.add(instructionsLabel);
		this.add(fileLabel);
		this.add(nameField);
	}
}
