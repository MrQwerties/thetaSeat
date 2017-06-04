import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.Scanner;

public class AddPeriodButton extends JButton{
	PeriodsPanel master;
	
	public AddPeriodButton(PeriodsPanel m){ 
		super("ADD PERIOD");
		
		master = m;
		
		this.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				    addPeriod();
				    
				    m.redrawPeriods();
				  } 
				} );
	}
	
	public void addPeriod(){
		JTextField nameField = new JTextField(20);
		JTextField numberField = new JTextField(20);
		int result = JOptionPane.showConfirmDialog(null, new AddPeriodPopup(nameField, numberField), "ADD PERIOD", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			try{
				int period = Integer.parseInt(numberField.getText());
				Scanner tryFile = new Scanner(new File(nameField.getText()));
				tryFile.close();
				Scanner periodList = null;
				try{
					periodList = new Scanner(new File(Constants.PERIOD_LIST_FILE_NAME));
				} catch(FileNotFoundException e){
					PrintWriter out = new PrintWriter(new File(Constants.PERIOD_LIST_FILE_NAME));
					out.close();
					periodList = new Scanner(new File(Constants.PERIOD_LIST_FILE_NAME));
				}
				String toWrite = "";
				boolean added = false;
				if(!periodList.hasNextLine()){
					toWrite = period + " " + nameField.getText() + "\n";
				}
				else{
					while(periodList.hasNextLine()){
						String line = periodList.nextLine();
						if(!added && Integer.parseInt(line.split(" ")[0]) > period){
							added = true;
							toWrite += period + " " + nameField.getText() + "\n";
						}
						if(Integer.parseInt(line.split(" ")[0]) != period){
							toWrite += line + "\n";
						}
					}
					if(!added){
						toWrite += period + " " + nameField.getText() + "\n";
					}
				}
				periodList.close();
				PrintWriter out = new PrintWriter(new File(Constants.PERIOD_LIST_FILE_NAME));
				out.print(toWrite);
				out.close();
				master.addPeriod(period);
			} catch (NumberFormatException e){
				JOptionPane.showMessageDialog(null, "<html>Invalid input for the period number. The period number must be<br>entered in integer form (ex: 4).</html>",
						"ERROR", JOptionPane.ERROR_MESSAGE);
			} catch (FileNotFoundException e){
				JOptionPane.showMessageDialog(null, "<html>The program cannot find the file entered. Please check that the<br>file name is entered correctly and that the file itself is in the<br>correct folder.</html>",
						"ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
