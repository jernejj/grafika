package grafika;

import java.awt.Point;

public class Pin {
	private int value;
	private Point up;
	private Point down;
	private int type;
	
	public final static int IN = 1;
	public final static int OUT = 2;
	
	public Pin(int type) {
		setType(type);
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value & 0x01;
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
			case IN:
				this.type = IN;
				break;
			case OUT:
				this.type = OUT;
				break;
			default:
				System.err.println("Wrong pin type!");
				System.exit(101);
				break;
		}
	}
	
	public int getType() {
		return this.type;
	}
}
