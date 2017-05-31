public class Seat {
	private Position location;
	private boolean inner;
	private Student occupant;
	
	public Seat(Position l, boolean in){
		location = l;
		inner = in;
	}
	
	public double distance(Seat other){
		return location.distance(other.location);
	}
	
	public void setStudent(Student s){
		occupant = s;
		if(s != null && s.getSeat() != this){
			s.setSeat(this);
		}
	}
	
	public void setPosition(Position p){
		location = p;
	}
	
	public Position getPosition(){
		return location;
	}
	
	public Student getStudent(){
		return occupant;
	}
	
	public boolean isInner(){
		return inner;
	}
}
