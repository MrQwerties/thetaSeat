import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PeriodsPanel extends JPanel{
	private HashMap<Integer, PeriodSelectionPanel> periods;
	private JPanel periodPanels;
	private int selectedPeriod;
	private JScrollPane periodPanelScrollable;
	
	public PeriodsPanel(){
		super(new BorderLayout());
		//This creates a thin black border with 30 pixels of padding around it where no other components can go		
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30),
				BorderFactory.createLineBorder(Color.black, 1)));
		setBackground(Constants.BACKGROUND_COLOR);
		
		JPanel instructions = new JPanel(new BorderLayout());
		instructions.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
				BorderFactory.createLineBorder(Color.black, 1)));
		instructions.setBackground(Constants.BACKGROUND_COLOR);
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
		
		selectedPeriod = -1;
		int[] periodList = loadPeriods();
		
		periodPanels = new JPanel(new GridLayout(0, 1));
		periodPanels.setMaximumSize(new Dimension(200, 200));
		periodPanels.setBackground(Constants.BACKGROUND_COLOR);
		
		periods = new HashMap<Integer, PeriodSelectionPanel>();
		for(int period : periodList){
			periods.put(period, new PeriodSelectionPanel(period, this));
			periodPanels.add(periods.get(period));
		}
		
		periodPanelScrollable = new JScrollPane(periodPanels, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		periodPanelScrollable.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		periodPanelScrollable.setBackground(Constants.BACKGROUND_COLOR);
		
		this.add(instructions, BorderLayout.NORTH);
		this.add(periodPanelScrollable, BorderLayout.CENTER);
	}
	
	public void redrawPeriods(){
		unselectPeriod();
		int[] periodList = loadPeriods();
		
		periodPanels.removeAll();
		for(int period : periodList){
			periodPanels.add(periods.get(period));
		}
		
		revalidate();
		repaint();
	}
	
	public void addPeriod(int period){
		periods.put(period, new PeriodSelectionPanel(period, this));
		redrawPeriods();
	}
	
	public static int[] loadPeriods(){
		//TODO: automatically remove bad files
		Constants.unhideFile(Constants.PERIOD_LIST_FILE_NAME);
		
		Scanner periodList = null;
		try{
			periodList = new Scanner(new File(Constants.PERIOD_LIST_FILE_NAME));
		} catch (FileNotFoundException e){
			try{
				//Creates a new file called thetaSeat_period_list and opens that
				PrintWriter out = new PrintWriter(Constants.PERIOD_LIST_FILE_NAME);
				out.close();
				periodList = new Scanner(new File(Constants.PERIOD_LIST_FILE_NAME));
			} catch (IOException f){
				return new int[0]; //This should never happen, if this line is executed, something has gone horribly wrong
			}
		}
		
		ArrayList<Integer> periodNumbers = new ArrayList<Integer>();
		while(periodList.hasNextLine()){
			String line = periodList.nextLine().trim();
			periodNumbers.add(Integer.parseInt(line.substring(0, line.indexOf(" "))));
		}
		
		periodList.close();
		
		Constants.hideFile(Constants.PERIOD_LIST_FILE_NAME);

		int[] result = new int[periodNumbers.size()];
		
		for(int i = 0; i < periodNumbers.size(); i++){
			result[i] = periodNumbers.get(i);
		}
		
		Arrays.sort(result);
		
		return result;
	}
	
	public void removePeriod(int period){
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		int response = JOptionPane.showConfirmDialog(topFrame, "Please confirm: do you want to delete period " + period + " from the list?",
				"CONFIRM", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(response == JOptionPane.YES_OPTION){
			trueRemovePeriod(period);
		}
	}
	
	public void trueRemovePeriod(int period){		
		if(selectedPeriod == period){
			periods.get(selectedPeriod).unhighlight();
			selectedPeriod = -1;
		}
		
		periodPanels.remove(periods.get(period));
		periods.remove(period);
		
		//Update the GUI
		revalidate();
		repaint();
		
		Scanner periodFile = null;
		try{
			periodFile = new Scanner(new File(Constants.PERIOD_LIST_FILE_NAME));
		} catch (FileNotFoundException e){
			e.printStackTrace();
			//This should never happen
		}
		
		String result = "";
		
		while(periodFile.hasNextLine()){
			String line = periodFile.nextLine();
			if(line.length() > 0 && Integer.parseInt(line.split(" ")[0]) != period){
				result += line + "\n";
			}
		}
		
		periodFile.close();
		
		Constants.deleteFile(Constants.getTwosieDataFileName(period));
		
		try{
			Constants.unhideFile(Constants.PERIOD_LIST_FILE_NAME); //PrintWriters apparently can't access hidden files
			PrintWriter out = new PrintWriter(Constants.PERIOD_LIST_FILE_NAME);
			out.print(result);
			out.close();
			Constants.hideFile(Constants.PERIOD_LIST_FILE_NAME);
		} catch (FileNotFoundException e){
			e.printStackTrace();
			//This line shouldn't happen
		}
	}

	public void choosePeriod(int period) {
		if (period == selectedPeriod){
			periods.get(selectedPeriod).unhighlight();
			selectedPeriod = -1;
		} else{
			if (selectedPeriod != -1) {
				periods.get(selectedPeriod).unhighlight();
			}
			selectedPeriod = period;
			periods.get(selectedPeriod).highlight();
		}
	}

	public int getSelectedPeriod() {
		return selectedPeriod;
	}

	public void unselectPeriod() {
		if(selectedPeriod != -1){
			periods.get(selectedPeriod).unhighlight();
			selectedPeriod = -1;
		}
	}
}
