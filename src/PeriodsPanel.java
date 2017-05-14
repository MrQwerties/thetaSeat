import java.awt.*;
import javax.swing.*;

public class PeriodsPanel extends JPanel{
	public PeriodsPanel(){
		super(new BorderLayout());
		JPanel instructions = new JPanel(new BorderLayout());
		instructions.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30),
				BorderFactory.createLineBorder(Color.black, 1)));
		
		Font arialLarge = new Font("Arial", Font.PLAIN, 24);
		Font arialNormal = new Font("Arial", Font.PLAIN, 18);
		
		JLabel title = new JLabel("Period Selection", JLabel.CENTER);
		JLabel text = new JLabel("<html>Please select the period that you would like to generate a seating chart for.</html>");
		title.setFont(arialLarge);
		title.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		text.setFont(arialNormal);
		text.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		
		instructions.add(title, BorderLayout.NORTH);
		instructions.add(text, BorderLayout.SOUTH);
		
		this.add(instructions, BorderLayout.NORTH);
	}
}
