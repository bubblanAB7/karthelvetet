
public class DescribedPlace extends Place{
	
	private String description;

	public DescribedPlace(Position p, String s, String n, String t) {
		super(p, s, n);
		this.description = t;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String toString() {
		String s = "Described," + super.category + "," + super.pos.getX() + "," + super.pos.getY() + "," + super.name + "," + this.description;
		return s;
	}
	
}
