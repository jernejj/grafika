package grafika;

import java.awt.Point;

public class Line {
	Element oneEnd;
	Element otherEnd;
	
	private Point startPoint;
	private Point endPoint;
	
	public Line() {
		
	}
	
	public Line(Element first, Element second) {
		this.oneEnd = first;
		this.otherEnd = second;
	}
	
	public void setOneEndElement(Element e) {
		this.oneEnd = e;
	}
	
	public void setOtherEndElement(Element e) {
		this.otherEnd = e;
	}
	
	public Element getOneEndElement() {
		return this.oneEnd;
	}
	
	public Element getOtherEndElement() {
		return this.otherEnd;
	}
	
	public Point getStartPoint() {
		return this.startPoint;
	}
	
	public void setStartPoint(Point starting_position) {
		this.startPoint = starting_position;
	}
	
	public Point getEndPoint() {
		return this.endPoint;
	}
	
	public void setEndPoint(Point ending_position) {
		this.endPoint = ending_position;
	}
}
