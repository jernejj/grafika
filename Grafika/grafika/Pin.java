package grafika;

import java.awt.Point;

public class Pin {
	private int value;
	private Point up;
	private Point down;
	private int type;
	
	public static final int IN1 = 1;
	public static final int IN2 = 2;
	public static final int OUT = 3;
	
	public Pin(int type) {
		setType(type);
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value & 0x01;
	}
	
	public void setValue(int value, Element e) {
		if(e.getType() == Element.OUTPUT || e.getType() == Element.GENOUT)
			this.value = value;
		else
			System.err.println("Pin.setValue(int,Element): Only for Element.OUTPUT");
	}
	
	public Point getUp() {
		return this.up;
	}
	
	public void setUp(Point up) {
		this.up = up;
	}

	public Point getDown() {
		return this.down;
	}
	
	public void setDown(Point down) {
		this.down = down;
	}
	
	private void setType(int type) {
		switch(type) {
			case IN1:
				this.type = Pin.IN1;
				break;
			case IN2:
				this.type = Pin.IN2;
				break;				
			case OUT:
				this.type = Pin.OUT;
				break;
			default:
				System.err.println("Pin.setType(): Wrong pin type!");
				System.exit(101);
				break;
		}
	}
	
	public int getType() {
		return this.type;
	}
	
	public Point getPinPosition() {
		return  new Point( (this.down.x - this.up.x)/2, (this.down.y - this.up.y)/2); 
	}
}
