import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Place extends JComponent{

	protected Position pos;
	protected String category;
	protected String name;
	private Polygon pol;
	private Polygon markedPol;
	private Polygon offsetPol;
	private Color color;
	private Place place;
	private boolean marked;
	
	
	public Place(Position p, String s, String n) {
		
		this.pos = p;
		place = this;
		this.name = n;
		
		this.category = s;
		int[] xPointsPol = {0, 15, 30};
		int[] yPointsPol = {0, 30, 0};
		int[] xPointsMark = {0, 25, 50};
		int[] yPointsMark = {0, 50, 0};
		int[] xPointsOffset = {10, 25, 40};
		int[] yPointsOffset = {10, 40, 10};
		pol = new Polygon(xPointsPol, yPointsPol, 3);
		markedPol = new Polygon(xPointsMark, yPointsMark, 3);
		offsetPol = new Polygon(xPointsOffset, yPointsOffset, 3);
		switch (s) {
		case "Bus":
			color = Color.RED;
			break;
		case "Underground":
			color = Color.BLUE;
			break;
		case "Train":
			color = Color.GREEN;
			break;
		case "None":
			color = Color.BLACK;
			break;
		}
		
	}
	
	public void setMarked(boolean b) {
		this.marked = b;
	}	
	
	public boolean isMarked() {
		return this.marked;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!marked) {
			g.setColor(color);
			g.fillPolygon(pol);
		}
		else if (marked) {
			g.setColor(Color.CYAN);
			g.fillPolygon(markedPol);
			g.setColor(color);
			g.fillPolygon(offsetPol);
		}
	}
	
	public boolean equals(Object other){
		if (other instanceof Place){
			Place p = (Place)other;
			return name == p.name;
		}
		else
			return false;
	}
	
	public int hashCode(){
		return name.hashCode();
	}
	
	public Position getPosition() {
		return this.pos;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setBounds() {
		if (marked) {
			this.setBounds(pos.getX() - 25, pos.getY() - 50, 50, 50);
		}
		else
			this.setBounds(pos.getX() - 15, pos.getY() - 30, 30, 30);
	}
}
