import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ChartDisplay extends Canvas {
	private thetaSeatChart chart;
	private ArrayList<Student> students;
	
	private int period;
	
	private final int TWOSIE_SIZE = 65;
	private final int TWOSIE_DOTS = 6;
	
	public ChartDisplay(int p){
		super();
		setBackground(Color.WHITE);
		setSize(400, 60 + 7 * TWOSIE_SIZE);
		
		period = p;
		
		chart = new thetaSeatChart(new ArrayList<Seat>(), loadStudents(), loadOldTwosies());
	}
	
	public void paint(Graphics g){
        Graphics2D g2;
        g2 = (Graphics2D) g;
        
        g2.drawString ("Period #: " + Integer.toString(period), 10, 20);
        g2.drawString ("# of students: " + chart.numStudents(), 10, 35);
        
        //Draw the seats
        for(int i = 0; i < 2; i++){ //Vertical seats in inner ring
        	makeTwosiePair(g2, 50 + 2 * TWOSIE_SIZE, 20 + 2 * i * TWOSIE_SIZE, false, true);
        }
        makeTwosiePair(g2, getWidth()/2 - 2 * TWOSIE_SIZE, 20 + 4 * TWOSIE_SIZE, true, true);
  
        for(int i = 0; i < 3; i++){ //Vertical seats in outer ring
        	makeTwosiePair(g2, 20, 50 + 2 * i * TWOSIE_SIZE, false, false);
        }
        for(int i = 0; i < 2; i++){ //Horizontal seats in inner ring
        	makeTwosiePair(g2, 20 + 2 * i * TWOSIE_SIZE, 50 + 6 * TWOSIE_SIZE, true, false);
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
	
	public void makeTwosie(Graphics2D g, int x, int y, boolean horizontal, boolean inner){
		if(horizontal){
			g.drawRect(x, y, 2 * TWOSIE_SIZE, TWOSIE_SIZE);
			drawDottedLine(g, x + TWOSIE_SIZE, y, x + TWOSIE_SIZE, y + TWOSIE_SIZE, TWOSIE_DOTS);
			
			chart.addSeat(new Seat(new Position(x, y), inner));
			chart.addSeat(new Seat(new Position(x + TWOSIE_SIZE, y), inner));
		} else{
			g.drawRect(x, y, TWOSIE_SIZE, 2 * TWOSIE_SIZE);
			drawDottedLine(g, x, y + TWOSIE_SIZE, x + TWOSIE_SIZE, y + TWOSIE_SIZE, TWOSIE_DOTS);
			
			chart.addSeat(new Seat(new Position(x, y), inner));
			chart.addSeat(new Seat(new Position(x, y + TWOSIE_SIZE), inner));
		}
	}
	
	public void makeTwosiePair(Graphics2D g, int x, int y, boolean horizontal, boolean inner){
		makeTwosie(g, x, y, horizontal, inner);
		if(horizontal){
			makeTwosie(g, getWidth() - x - 2 * TWOSIE_SIZE, y, horizontal, inner);
		} else{
			makeTwosie(g, getWidth() - x - TWOSIE_SIZE, y, horizontal, inner);
		}
	}
	
	public void drawStudent(Graphics2D g, Student s){
		g.setFont(new Font("Arial", Font.PLAIN, 9));
		Position p = s.getSeat().getPosition();
		g.drawString(s.getLast(), (int)p.x() + 2, (int)p.y() + TWOSIE_SIZE - 4);
		g.drawString(s.getFirst(), (int)p.x() + 2, (int)p.y() + TWOSIE_SIZE - 19);
	}
	
	public void drawStudent(Graphics g, Student s){
		Graphics2D g2;
	    g2 = (Graphics2D) g;
		drawStudent(g2, s);
	}
	
	public void makeNewChart(){
		chart.goodShuffle(2);
		
		Graphics g = getGraphics();
		
		for(Seat s : chart.getSeats()){ //Clear out the names
			g.clearRect((int)s.getPosition().x() + 1, (int)s.getPosition().y() + 1, TWOSIE_SIZE - 2, TWOSIE_SIZE - 2);
		}
		
		drawChart(g);
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
	    BufferedImage image = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics = image.createGraphics();
	    paintAll(graphics);
	    
	    graphics.setColor(Color.BLACK);
	    drawChart(graphics);
	    graphics.dispose();
	    try {
	        FileOutputStream out = new FileOutputStream(imageName);
	        ImageIO.write(image, "png", out);
	        out.close();
	        PrintWriter printer = new PrintWriter(new File("thetaSeat_period" + Integer.toString(period) + "_data"));
	        for(Twosie t : chart.getTwosies()){
	        	printer.println(t);
	        }
	        printer.close();
	        
	        chart.setOldTwosies(loadOldTwosies());
	    } catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }    
	}
}
