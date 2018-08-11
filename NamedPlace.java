
public class NamedPlace extends Place{
	
	public NamedPlace(Position p, String s, String n) {
		super(p, s, n);
	}
	
	public String toString() {
		String s = "Named," + super.category + "," + super.pos.getX() + "," + super.pos.getY() + "," + super.name;
		return s;
	}

}