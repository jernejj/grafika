package grafika;

import java.awt.Color;
import java.awt.Point;

public class Line {
	private Element out;
	private Element in;
	
	private Color color;
	
	private Point outPoint;
	private Point inPoint;
	
	public Line() {
		this.color = Color.BLACK;
	}
	
	public Line(Element out, Element in) {
		this.out = out;
		this.in = in;
		this.color = Color.BLACK;
	}
	
	public void setPartTowardsOut(Element out) {
		this.out = out;
	}
	
	public void setPartTowardsIn(Element in) {
		this.in = in;
	}
	
	public Element getPartTowardsOut() {
		return this.out;
	}
	
	public Element getPartTowardsIn() {
		return this.in;
	}
	
	public Point getOutPoint() {
		return this.outPoint;
	}
	
	public void setOutPoint(Point out) {
		this.outPoint = out;
	}
	
	public Point getInPoint() {
		return this.inPoint;
	}
	
	public void setInPoint(Point in) {
		this.inPoint = in;
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public Color getColor() {
		return this.color;
	}
}
