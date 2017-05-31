public class Student {
	private String firstName;
	private String lastName;
	private String gender;
	private String race;
	private boolean visionProblems;
	
	private Seat location;
	
	private final double BADNESS_FROM_RACE = 2;
	private final double BADNESS_FROM_GENDER = 3;
	
	public Student(String last, String first, String g, String r, boolean vision){
		lastName = last;
		firstName = first;
		gender = g;
		race = r;
		visionProblems = vision;
	}
	
	public Student(String[] parts){
		this(parts[0], parts[1], parts[2], parts[3], parts[4].toUpperCase().equals("YES"));
	}
	
	public Student(String data){
		this(data.replaceAll(" ", "").split(","));
	}
	
	public double distance(Student other){
		return location.distance(other.location);
	}
	
	public boolean canSitHere(){
		return !visionProblems || location.isInner();
	}
	
	public double badness(Student other){
		double d = Math.pow(distance(other), 2);
		double total = 0;
		if(gender.equals(other.gender)){
			total += BADNESS_FROM_GENDER/d;
		}
		if(race.equals(other.race)){
			total += BADNESS_FROM_RACE/d;
		}
		return total;
	}
	
	public void setSeat(Seat s){
		location = s;
		if(location.getStudent() != this){
			location.setStudent(this);
		}
	}
	
	public Seat getSeat(){
		return location;
	}
	
	public String getGender(){
		return gender;
	}
	
	public String getFirst(){
		return firstName;
	}
	
	public String getLast(){
		return lastName;
	}
	
	public String getName(){
		return getFirst() + " " + getLast();
	}
	
	public boolean equals(Student other){
		return firstName.equals(other.firstName) && lastName.equals(other.lastName) && gender.equals(other.gender)
				&& race.equals(other.race) && (visionProblems == other.visionProblems);
	}
	
	public String toString(){
		return lastName + "," + firstName + "," + gender + "," + race + "," + (visionProblems ? "YES" : "NO");
	}

	public Position getPosition() {
		return location.getPosition();
	}
}