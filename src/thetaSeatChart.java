import java.util.ArrayList;
import java.util.Collections;

public class thetaSeatChart extends SeatingChart {
	private ArrayList<Twosie> twosies;
	private ArrayList<Twosie> oldTwosies;
	
	private final String MALE_WORD = "male";
	private final String FEMALE_WORD = "female";
	
	private final int MAX_TRIES = 100;
	
	public thetaSeatChart(ArrayList<Seat> se, ArrayList<Student> st, ArrayList<Twosie> o){
		super(se, st);
		students = st;
		oldTwosies = o;
	}
	
	public void shuffleStudents() {
		boolean done = false;
		int counter = 0;

		while(!done && counter < MAX_TRIES){ //Try to make a good arrangement for MAX_TRIES times, and then give up and use a random one
			counter ++;
			
			generateTwosies();
			students = new ArrayList<Student>();
			for(int i = 0; i < twosies.size(); i++){
				Student[] pair = twosies.get(i).getStudents();
				students.add(pair[0]);
				if(pair[1] != null){
					students.add(pair[1]);
				}
			}
			
			setSeats();
			
			//Check that students are sitting in good seats
			for(Student s : students){
				if(!s.canSitHere()){
					done = false;
					break;
				}
			}
		}
	}
	
	public ArrayList<Twosie> makeTwosies(ArrayList<Student> boys, ArrayList<Student> girls){
		ArrayList<Twosie> newTwosies = new ArrayList<Twosie>();
		while(boys.size() > 0 && girls.size() > 0){ //Prioritize boy-girl pairs
			newTwosies.add(new Twosie(boys.get(boys.size() - 1), girls.get(girls.size() - 1)));
			boys.remove(boys.size() - 1);
			girls.remove(girls.size() - 1);
		}
		
		while(boys.size() > 1){ //Make boy-boy pairs if necessary
			newTwosies.add(new Twosie(boys.get(boys.size() - 1), boys.get(boys.size() - 2)));
			boys.remove(boys.size() - 1);
			boys.remove(boys.size() - 1);
		}
		
		while(girls.size() > 1){
			newTwosies.add(new Twosie(girls.get(girls.size() - 1), girls.get(girls.size() - 2)));
			girls.remove(girls.size() - 1);
			girls.remove(girls.size() - 1);
		}
		
		if(boys.size() == 1){ //Forever alone
			newTwosies.add(new Twosie(boys.get(0), null));
		}
		
		if(girls.size() == 1){
			newTwosies.add(new Twosie(girls.get(0), null));
		}
		
		return newTwosies;
	}
	
	public void generateTwosies(){
		//TODO: add support for non-binary genders or whatever [insert political debate]
		boolean done = false;
		int counter = 0;
		
		ArrayList<Student> boys = new ArrayList<Student>();
		ArrayList<Student> girls = new ArrayList<Student>();
		
		for(Student s : students){
			if(s.getGender().equals(MALE_WORD)){
				boys.add(s);
			} else{
				girls.add(s);
			}
		}
		
		while(!done && counter < MAX_TRIES){
			done = true;
			counter++;
			
			Collections.shuffle(boys);
			Collections.shuffle(girls);
			
			ArrayList<Twosie> newTwosies = makeTwosies((ArrayList<Student>)boys.clone(), (ArrayList<Student>)girls.clone());
			
			for(Twosie n : newTwosies){
				for(Twosie o : oldTwosies){
					if(n.equals(o)){
						done = false;
						break;
					}
				}
				
				if(!done){
					break;
				}
			}
			
			twosies = newTwosies;
		}
	}
	
	public ArrayList<Twosie> getTwosies(){
		return twosies;
	}
	
	public void setOldTwosies(ArrayList<Twosie> o){
		oldTwosies = o;
	}
}
