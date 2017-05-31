public class Twosie {
	private Student[] students;
	
	public Twosie(Student s1, Student s2){
		students = new Student[2];
		students[0] = s1;
		students[1] = s2;
	}
	
	public Twosie(String data){
		students = new Student[2];
		String[] parts = data.split(" ");
		for(int i = 0; i < 2; i++){
			students[i] = new Student(parts[i]);
		}
	}
	
	public Student[] getStudents(){
		return students;
	}
	
	public boolean equals(Twosie other){
		if(students[1] == null){
			return students[0].equals(other.students[0]) && students[1] == null;
		}
		return students[0].equals(other.students[0]) && students[1].equals(other.students[1]) ||
				students[0].equals(other.students[1]) && students[1].equals(other.students[0]);
	}
	
	public String toString(){
		if(students[0] != null && students[1] != null){
			return students[0].toString() + " " + students[1].toString();
		}
		return "";
	}
}
