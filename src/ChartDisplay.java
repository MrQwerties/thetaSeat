import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Deque;

public class ChartDisplay extends Canvas {
	private thetaSeatChart chart;
	private ArrayList<Student> students;
	
	private ArrayList<Seat> highlighted;
	private Deque<ArrayList<Seat>> toUndo;
	private Deque<ArrayList<Seat>> toRedo;
	
	private int period;
	
	private SaveButton mySave;
	private SwitchButton mySwitch;
	private UndoButton myUndo;
	private RedoButton myRedo;
	
	private boolean hasChart;
	
	private final int TWOSIE_SIZE = 65;
	private final int TWOSIE_DOTS = 6;
	private final int CHART_ITERATIONS = 5000;
	private final int CLASS_SIZE = 32;
	
	public ChartDisplay(int p){
		super();
		setBackground(Color.WHITE);
		setSize(560, 60 + 7 * TWOSIE_SIZE);
		
		period = p;
		
		chart = new thetaSeatChart(new ArrayList<Seat>(), loadStudents(), loadOldTwosies());
		
		highlighted = new ArrayList<Seat>();
		toUndo = new ArrayDeque<ArrayList<Seat>>();
		toRedo = new ArrayDeque<ArrayList<Seat>>();
		
		hasChart = false;
		
		addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            	handleClick(e.getX(), e.getY());
            }
        });
	}
	
	public void paint(Graphics g){
		Graphics2D g2;
        g2 = (Graphics2D) g;
        
        g2.drawString("Period #: " + Integer.toString(period), 10, 20);
        g2.drawString("# of students: " + chart.numStudents(), 10, 35);
        
        //Draw the seats
        for(int i = 0; i < 2; i++){ //Vertical seats in inner ring
        	drawTwosiePair(g2, 50 + 2 * TWOSIE_SIZE, 20 + 2 * i * TWOSIE_SIZE, false);
        }
        drawTwosiePair(g2, getWidth()/2 - 2 * TWOSIE_SIZE, 20 + 4 * TWOSIE_SIZE, true);
  
        for(int i = 0; i < 3; i++){ //Vertical seats in outer ring
        	drawTwosiePair(g2, 20, 50 + 2 * i * TWOSIE_SIZE, false);
        }
        for(int i = 0; i < 2; i++){ //Horizontal seats in outer ring
        	drawTwosiePair(g2, 20 + 2 * i * TWOSIE_SIZE, 50 + 6 * TWOSIE_SIZE, true);
        }
        
        if(!hasChart){
        	createChart();
        } else{
        	drawChart(g);
        }
	}
	
	public void createChart(){ //Make the chart
		for(int i = 0; i < 2; i++){ //Vertical seats in inner ring
        	makeTwosiePair(50 + 2 * TWOSIE_SIZE, 20 + 2 * i * TWOSIE_SIZE, false, true);
        }
        makeTwosiePair(getWidth()/2 - 2 * TWOSIE_SIZE, 20 + 4 * TWOSIE_SIZE, true, true);
  
        for(int i = 0; i < 3; i++){ //Vertical seats in outer ring
        	makeTwosiePair(20, 50 + 2 * i * TWOSIE_SIZE, false, false);
        }
        for(int i = 0; i < 2; i++){ //Horizontal seats in outer ring
        	makeTwosiePair(20 + 2 * i * TWOSIE_SIZE, 50 + 6 * TWOSIE_SIZE, true, false);
        }
    }
	
	public double weightedAverage(double a, double aWeight, double b, double bWeight){
		return (a * aWeight + b * bWeight)/(aWeight + bWeight);
	}
	
	public void drawDottedLine(Graphics2D g, int x1, int y1, int x2, int y2, int dots){
		for(int i = 0; i < dots; i++){
			g.drawLine((int)weightedAverage(x1, 2 * i, x2, 2 * dots - 1 - 2 * i),
					(int)weightedAverage(y1, 2 * i, y2, 2 * dots - 1 - 2 * i),
					(int)weightedAverage(x1, 2 * i + 1, x2, 2 * dots - 2 - 2 * i),
					(int)weightedAverage(y1, 2 * i + 1, y2, 2 * dots - 2 - 2 * i));
		}
	}
	
	public void drawTwosie(Graphics2D g, int x, int y, boolean horizontal){
		if(horizontal){
			g.drawRect(x, y, 2 * TWOSIE_SIZE, TWOSIE_SIZE);
			drawDottedLine(g, x + TWOSIE_SIZE, y, x + TWOSIE_SIZE, y + TWOSIE_SIZE, TWOSIE_DOTS);
		} else{
			g.drawRect(x, y, TWOSIE_SIZE, 2 * TWOSIE_SIZE);
			drawDottedLine(g, x, y + TWOSIE_SIZE, x + TWOSIE_SIZE, y + TWOSIE_SIZE, TWOSIE_DOTS);
		}
	}
	
	public void drawTwosiePair(Graphics2D g, int x, int y, boolean horizontal){
		drawTwosie(g, x, y, horizontal);
		if(horizontal){
			drawTwosie(g, getWidth() - x - 2 * TWOSIE_SIZE, y, horizontal);
		} else{
			drawTwosie(g, getWidth() - x - TWOSIE_SIZE, y, horizontal);
		}
	}
	
	public void makeTwosie(int x, int y, boolean horizontal, boolean inner){
		chart.addSeat(new Seat(new Position(x, y), inner));
		
		if(horizontal){
			chart.addSeat(new Seat(new Position(x + TWOSIE_SIZE, y), inner));
		} else{
			chart.addSeat(new Seat(new Position(x, y + TWOSIE_SIZE), inner));
		}
	}
	
	public void makeTwosiePair(int x, int y, boolean horizontal, boolean inner){
		makeTwosie(x, y, horizontal, inner);
		if(horizontal){
			makeTwosie(getWidth() - x - 2 * TWOSIE_SIZE, y, horizontal, inner);
		} else{
			makeTwosie(getWidth() - x - TWOSIE_SIZE, y, horizontal, inner);
		}
	}
	
	public void drawStudent(Graphics2D g, Student s){
		g.setFont(new Font("Arial", Font.PLAIN, 9));
		Position p = s.getSeat().getPosition();
		drawName((Graphics)g, s);
	}
	
	public void drawStudent(Graphics g, Student s){
		Graphics2D g2;
	    g2 = (Graphics2D) g;
		drawStudent(g2, s);
	}
	
	public void makeNewChart(){
		chart.goodShuffle(CHART_ITERATIONS);
		
		Graphics g = getGraphics();
		
		for(Seat s : chart.getSeats()){ //Clear out the names
			g.clearRect((int)s.getPosition().x() + 1, (int)s.getPosition().y() + 1, TWOSIE_SIZE - 2, TWOSIE_SIZE - 2);
		}
		
		drawChart(g);
		resetAll();
		
		hasChart = true;
		
		mySave.setEnabled(true); //Now that there's a chart, the user can save it
	}
		
	public void drawChart(Graphics g){		
		for(Student s : chart.getStudents()){
			drawStudent(g, s);
		}
	}
	
	public String getFileName(){
		Scanner inFile = null;
		try{
			inFile = new Scanner(new File(Constants.PERIOD_LIST_FILE_NAME));
		} catch(FileNotFoundException e){}
		
		while(inFile.hasNextLine()){
			String line = inFile.nextLine();
			String[] parts = line.split(" ");
			if(period == Integer.parseInt(parts[0])){
				return parts[1];
			}
		}
		return "error"; //This shouldn't happen
	}
	
	public ArrayList<Student> loadStudents(){
		Scanner inFile = null;
		try{
			inFile = new Scanner(new File(getFileName()));
		} catch(FileNotFoundException e){}
		
		ArrayList<Student> result = new ArrayList<Student>();
		
		while(inFile.hasNextLine()){
			result.add(new Student(inFile.nextLine()));
		}
		
		if(result.size() > CLASS_SIZE){
			String removedMessage = "<html>This class has more students than the number of seats. Removed students:";
			while(result.size() > CLASS_SIZE){
				removedMessage += "<br>";
				removedMessage += result.remove(CLASS_SIZE).getName();
			}
			removedMessage += "</html>";
			JOptionPane.showMessageDialog((JFrame) SwingUtilities.getWindowAncestor(this),
					removedMessage,
					"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		
		return result;
	}
	
	public ArrayList<Twosie> loadOldTwosies(){
		Scanner inFile = null;
		try{
			inFile = new Scanner(new File("thetaSeat_period" + Integer.toString(period) + "_data"));
		} catch(FileNotFoundException e){
			return new ArrayList<Twosie>();
		}
		ArrayList<Twosie> result = new ArrayList<Twosie>();
		
		while(inFile.hasNextLine()){
			String line = inFile.nextLine();
			if(!line.equals("")){
				result.add(new Twosie(line));
			}
		}
		
		inFile.close();
		
		return result;
	}
	
	public void exportImage(String imageName) {
		resetHighlight();
		
	    BufferedImage image = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics = image.createGraphics();
	    paintAll(graphics);
	    
	    graphics.setColor(Color.BLACK);
	    drawChart(graphics);
	    graphics.dispose();
	    try {
	    	imageName = Constants.addExtension(imageName,  ".png");
	        FileOutputStream out = new FileOutputStream(imageName);
	        ImageIO.write(image, "png", out);
	        out.close();
	        
	        //Save the pairs
	        String dataFileName = Constants.getTwosieDataFileName(period);
	        PrintWriter printer = new PrintWriter(new File(dataFileName));
	        for(Twosie t : chart.getTwosies()){
	        	printer.println(t);
	        }
	        printer.close();
	        
	        Constants.hideFile(dataFileName);
	        
	        chart.setOldTwosies(loadOldTwosies());
	    } catch (FileNotFoundException e) {
	    } catch (IOException e) {
	    }    
	}
	
	public void handleClick(int x, int y){
		if(hasChart){
			for(Seat s : chart.getSeats()){
				if((s.getPosition().x() < x) && (x < s.getPosition().x() + TWOSIE_SIZE)
					&&(s.getPosition().y() < y) && (y < s.getPosition().y() + TWOSIE_SIZE)){
					clickSeat(s);
				}
			}
		}
	}
	
	public void switchSeats(Seat s1, Seat s2){
		Student temp = s1.getStudent();
		s1.setStudent(s2.getStudent());
		s2.setStudent(temp);
		
		Graphics g = getGraphics();
		
		clearSeat(g, s1);
		clearSeat(g, s2);
		drawName(g, s1.getStudent());
		drawName(g, s2.getStudent());
		
		ArrayList<Seat> switched = new ArrayList<Seat>();
		switched.add(s1); switched.add(s2);
		
		myUndo.setEnabled(true);
	}
	
	public void clearSeat(Graphics g, Seat s){
		g.clearRect((int)s.getPosition().x() + 1, (int)s.getPosition().y() + 1, TWOSIE_SIZE - 2, TWOSIE_SIZE - 2);
	}
	
	public void clickSeat(Seat s){
		if(highlighted.contains(s)){
			highlighted.remove(s);
			unhighlightSeat(s);
			mySwitch.setEnabled(false);
		} else{
			highlighted.add(s);
			highlightSeat(s);
			if(highlighted.size() > 2){
				unhighlightSeat(highlighted.get(0));
				highlighted.remove(0);
			}
			if(highlighted.size() >= 2){
				mySwitch.setEnabled(true);
			}
		}
	}
	
	public void highlightSeat(Seat s){
		Graphics g = getGraphics();
		g.setColor(Color.YELLOW);
		g.fillRect((int)s.getPosition().x() + 1, (int)s.getPosition().y() + 1, TWOSIE_SIZE - 2, TWOSIE_SIZE - 2);
		g.setColor(Color.BLACK);
		drawName(g, s.getStudent());
	}
	
	public void unhighlightSeat(Seat s){
		Graphics g = getGraphics();
		clearSeat(g, s);
		drawName(g, s.getStudent());
	}

	public void setSaveButton(SaveButton save) {
		mySave = save;
	}
	
	public void drawName(Graphics g, Student s){
		if(s != null){
			Position p = s.getPosition();
			g.setFont(new Font("Arial", Font.PLAIN, 9));
			g.drawString(s.getLast(), (int)p.x() + 2, (int)p.y() + TWOSIE_SIZE - 4);
			g.drawString(s.getFirst(), (int)p.x() + 2, (int)p.y() + TWOSIE_SIZE - 19);
		}
	}

	public void setSwitchButton(SwitchButton switcher) {
		mySwitch = switcher;
	}

	public void switchSelectedSeats() {
		if((highlighted.size() >= 2)){
			switchSeats(highlighted.get(0), highlighted.get(1));
			
			toUndo.push((ArrayList<Seat>)highlighted.clone());
		}
		
		while(highlighted.size() > 0){
			unhighlightSeat(highlighted.get(0));
			highlighted.remove(0);
		}
	}
	
	public void resetAll(){
		resetHighlight();
		toUndo = new ArrayDeque<ArrayList<Seat>>();
		toRedo = new ArrayDeque<ArrayList<Seat>>();
		
		myUndo.setEnabled(false);
		myRedo.setEnabled(false);
		mySwitch.setEnabled(false);
	}
	
	public void resetHighlight(){
		for(Seat s : highlighted){
			unhighlightSeat(s);
		}
		
		highlighted = new ArrayList<Seat>();		
	}
	
	public void undo(){
		if(toUndo.size() > 0){
			ArrayList<Seat> toSwitch = toUndo.pop();
			switchSeats(toSwitch.get(0), toSwitch.get(1));
			
			toRedo.push(toSwitch);
			myRedo.setEnabled(true);
			
			if(toUndo.size() == 0){
				myUndo.setEnabled(false);
			}
		}
	}
	
	public void redo(){
		if(toRedo.size() > 0){
			ArrayList<Seat> toSwitch = toRedo.pop();
			switchSeats(toSwitch.get(0), toSwitch.get(1));
			
			toUndo.push(toSwitch);
			myUndo.setEnabled(true);
			
			if(toRedo.size() == 0){
				myRedo.setEnabled(false);
			}
		}
	}

	public void setUndoButton(UndoButton undo) {
		myUndo = undo;
	}

	public void setRedoButton(RedoButton redo) {
		myRedo = redo;
	}
}
