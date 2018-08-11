import java.util.*;

public class Position {
	private int x;
	private int y;

	Position(int x, int y){
		this.x = x;
		this.y = y;
		
	}
	
	public String toString() {
//		String s = "X: " + x + "Y: " + y;
		String s = "";
		return s;
	}
	
	public boolean equals(Object other){
		if (other instanceof Position){
			Position p = (Position)other;
			return x == p.x && y == p.y;
		}
		else
			return false;
	}
	
	public int hashCode(){
		return x * 10000 + y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
}
