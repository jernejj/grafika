package grafika;

import java.awt.Point;

public class Line {
	private Element out;
	private Element in;
	
	private Point outPoint;
	private Point inPoint;
	
	public Line() {
		
	}
	
	public Line(Element out, Element in) {
		this.out = out;
		this.in = in;
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
}
