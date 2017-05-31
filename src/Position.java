public class Position {
	private double myX;
	private double myY;
	
	public Position(double x, double y){
		myX = x;
		myY = y;
	}
	
	public double distance(Position other){
		return Math.pow(Math.pow(other.myX - this.myX, 2) + Math.pow(other.myY - this.myY, 2), 0.5);
	}
	
	public double x(){
		return myX;
	}
	
	public double y(){
		return myY;
	}
}
