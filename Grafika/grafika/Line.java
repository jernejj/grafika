package grafika;

public class Line {
	Element oneEnd;
	Element otherEnd;
	
	public Line(Element first, Element second) {
		this.oneEnd = first;
		this.otherEnd = second;
	}
}
