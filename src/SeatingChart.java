import java.util.ArrayList;

public abstract class SeatingChart {
	protected ArrayList<Seat> seats;
	protected ArrayList<Student> students;
	
	public SeatingChart(ArrayList<Seat> se, ArrayList<Student> st){
		seats = se;
		students = st;
	}
	
	public abstract void shuffleStudents();
	
	public void goodShuffle(int iterations){
		double record = -1;
		ArrayList<Student> best = (ArrayList<Student>)students.clone();
		
		for(int i = 0; i < iterations; i++){
			shuffleStudents();
			if(record == -1 || badness() < record){
				record = badness();
				best = (ArrayList<Student>)students.clone();
			}
		}
		
		students = best;
		
		setSeats();
	}
	
	public double badness(){
		double total = 0;
		for(int i = 0; i < students.size(); i++){
			for(int j = i; j < students.size(); j++){
				if(students.get(i) != null && students.get(j) != null){
					total += students.get(i).badness(students.get(j));
				}
			}
		}
		
		return total;
	}
	
	public void setSeats(){
		for(int i = 0; i < students.size(); i++){
			students.get(i).setSeat(seats.get(i));
		}
	}
	
	public void clearSeats(){
		seats = new ArrayList<Seat>();
	}
	
	public int numStudents(){
		return students.size();
	}
	
	public void addSeat(Seat s){
		seats.add(s);
	}
	
	public ArrayList<Student> getStudents(){
		return students;
	}
	
	public ArrayList<Seat> getSeats(){
		return seats;
	}
}
