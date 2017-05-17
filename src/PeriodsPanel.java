import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class PeriodsPanel extends JPanel{
	private HashMap<Integer, PeriodSelectionPanel> periods;
	private JPanel periodPanels;
	private static final String PERIOD_LIST_FILE_NAME = "thetaSeat_period_list";
	
	public PeriodsPanel(){
		super(new BorderLayout());		
		//This creates a thin black border with 30 pixels of padding around it where no other components can go		
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30),
				BorderFactory.createLineBorder(Color.black, 1)));
		
		JPanel instructions = new JPanel(new BorderLayout());		
		instructions.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
				BorderFactory.createLineBorder(Color.black, 1)));
		
		Font arialLarge = new Font("Arial", Font.PLAIN, 24);
		Font arialNormal = new Font("Arial", Font.PLAIN, 18);
		
		JLabel title = new JLabel("Period Selection", JLabel.CENTER);
		//The <html> tags ensure that if the text is too long, it wraps around instead of going off the page
		JLabel text = new JLabel("<html>Please select the period that you would like to generate a seating chart for.</html>");
		title.setFont(arialLarge);
		title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		text.setFont(arialNormal);
		text.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		instructions.add(title, BorderLayout.NORTH);
		instructions.add(text, BorderLayout.SOUTH);
		
		int[] periodList = loadPeriods();
		
		periodPanels = new JPanel(new GridLayout(0, 1));
		periodPanels.setMaximumSize(new Dimension(200, 200));
		
		periods = new HashMap<Integer, PeriodSelectionPanel>();
		for(int period : periodList){
			periods.put(period, new PeriodSelectionPanel(period, this));
			periodPanels.add(periods.get(period));
		}
		
		JScrollPane periodPanelScrollable = new JScrollPane(periodPanels, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		periodPanelScrollable.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		
		this.add(instructions, BorderLayout.NORTH);
		this.add(periodPanelScrollable, BorderLayout.CENTER);
	}
	
	public static int[] loadPeriods(){
		Scanner periodList = null;
		try{
			periodList = new Scanner(new File(PERIOD_LIST_FILE_NAME));
		} catch (FileNotFoundException e){
			try{
				//Creates a new file called thetaSeat_period_list and opens that
				PrintWriter out = new PrintWriter(PERIOD_LIST_FILE_NAME);
				out.close();
				periodList = new Scanner(new File(PERIOD_LIST_FILE_NAME));
			} catch (IOException f){
				return new int[0]; //This should never happen, if this line is executed, something has gone horribly wrong
			}
		}
		
		String line = periodList.nextLine().trim();
		String[] periodNames = line.split(" ");
		int[] result = new int[periodNames.length];
		
		for(int i = 0; i < periodNames.length; i++){
			result[i] = Integer.parseInt(periodNames[i]);
		}
		
		periodList.close();
		
		Arrays.sort(result);		
		return result;
	}
	
	public void removePeriod(int period){
		//Remove the entry for the period that got deleted
		periodPanels.remove(periods.get(period));
		periods.remove(period);
		
		//Update the GUI
		revalidate();
		repaint();
		
		//Update the period list
		ArrayList<Integer> periodList = new ArrayList<Integer>();
		for(int p : loadPeriods()){
			periodList.add(p);
		}
		
		periodList.remove(periodList.indexOf(period));
		
		try{
			PrintWriter out = new PrintWriter(PERIOD_LIST_FILE_NAME);
			for(int p : periodList){
				out.print(p + " ");
			}
			out.close();
		} catch (FileNotFoundException e){
			//This line shouldn't happen
		}
	}
}
