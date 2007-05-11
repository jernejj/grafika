package grafika;

import java.awt.Image;
import java.awt.Point;
import java.io.File;

public class Element {
	private int type;
	private File symbol;
	private Pin pin1;
	private Pin pin2;
	private Pin out;
	
	private Line toPin1;
	private Line toPin2;
	private Line toOut;
	
	private Image slika;
	
	private int size;
	// picture width
	private int sizeX;
	// picture height
	private int sizeY;
	
	public static final int AND = 0;
	public static final int OR = 1;
	public static final int NAND = 2;
	public static final int NOR = 3;
	public static final int XOR = 4;
	public static final int XNOR = 5;
	public static final int NOT = 6;
	
	private Point position; 
	
	public Element(int type, Point position) {
		this.position = position;
		setType(type);	
	}
	
	public int getSizeX() {
		return sizeX;
	}
	
	public int getSizeY() {
		return sizeY;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setPins(short valuePin1, short valuePin2) {
		setPin1value(valuePin1);
		setPin2value(valuePin2);
	}
	
	public Point getPosition() {
		return position;
	}
	
	public int getXposition() {
		return position.x;
	}
	
	public int getYposition() {
		return position.y;
	}
	
	public void setPosition(Point newPosition) {
		position = newPosition;
	}
	
	public void setPosition(int newX, int newY) {
		position = new Point(newX,newY);
	}
	
	public void setType(int type) {
		switch(type) {
			case AND:
				this.type = Element.AND;
				symbol = new File("grafika/logicalOperators/and.png");
				sizeX = 30;
				sizeY = 47;
				size = sizeX * sizeY;
				this.pin1 = new Pin(Pin.IN);
				this.pin2 = new Pin(Pin.IN);
				this.out = new Pin(Pin.OUT);
				this.pin1.setUp(new Point(6,0));
				this.pin1.setDown(new Point(9,5));
				this.pin2.setUp(new Point(20,0));
				this.pin2.setDown(new Point(23,5));
				this.out.setUp(new Point(13,41));
				this.out.setDown(new Point(16,46));
				break;
			case OR:
				this.type = Element.OR;
				symbol = new File("grafika/logicalOperators/or.png");
				sizeX = 40;
				sizeY = 62;
				size = sizeX * sizeY;
				this.pin1 = new Pin(Pin.IN);
				this.pin2 = new Pin(Pin.IN);
				this.out = new Pin(Pin.OUT);
				break;
			case NAND:
				this.type = Element.NAND;
				symbol = new File("grafika/logicalOperators/nand.png");
				sizeX = 40;
				sizeY = 62;
				size = sizeX * sizeY;
				this.pin1 = new Pin(Pin.IN);
				this.pin2 = new Pin(Pin.IN);
				this.out = new Pin(Pin.OUT);
				break;
			case NOR:
				this.type = Element.NOR;
				symbol = new File("grafika/logicalOperators/nor.png");
				sizeX = 40;
				sizeY = 62;
				size = sizeX * sizeY;
				this.pin1 = new Pin(Pin.IN);
				this.pin2 = new Pin(Pin.IN);
				this.out = new Pin(Pin.OUT);
				break;
			case XOR:
				this.type = Element.XOR;
				symbol = new File("grafika/logicalOperators/xor.png");
				sizeX = 40;
				sizeY = 62;
				size = sizeX * sizeY;
				this.pin1 = new Pin(Pin.IN);
				this.pin2 = new Pin(Pin.IN);
				this.out = new Pin(Pin.OUT);
				break;
			case XNOR:
				this.type = Element.XNOR;
				symbol = new File("grafika/logicalOperators/xnor.png");
				sizeX = 40;
				sizeY = 62;
				size = sizeX * sizeY;
				this.pin1 = new Pin(Pin.IN);
				this.pin2 = new Pin(Pin.IN);
				this.out = new Pin(Pin.OUT);
				break;
			case NOT:
				this.type = Element.NOT;
				symbol = new File("grafika/logicalOperators/not.png");
				sizeX = 40;
				sizeY = 62;
				size = sizeX * sizeY;
				this.pin1 = new Pin(Pin.IN);
				this.out = new Pin(Pin.OUT);
				break;
			default:
				System.err.print("Invalid Type!");
		}
	}
	
	public int getType() {
		return this.type;
	}
	
	public String getElementType(int type) {
		switch(type) {
			case AND:
				return "AND";
			case OR:
				return "OR";
			case NAND:
				return "NAND";
			case NOR:
				return "NOR";
			case XOR:
				return "XOR";
			case XNOR:
				return "XNOR";
			case NOT:
				return "NOT";
			default:
				return "Invalid Type!";
		}
	}		
	
	public void compute() {
		switch(this.type) {
			case AND:
				this.out.setValue(this.pin1.getValue() & this.pin2.getValue());
				break;
			case OR:
				this.out.setValue(this.pin1.getValue() | this.pin2.getValue());
				break;
			case NAND:
				this.out.setValue(~(this.pin1.getValue() & this.pin2.getValue()));
				break;
			case NOR:
				this.out.setValue(~(this.pin1.getValue() | this.pin2.getValue()));
				break;
			case XOR:
				this.out.setValue(this.pin1.getValue() ^ this.pin2.getValue());
				break;
			case XNOR:
				this.out.setValue(~(this.pin1.getValue() ^ this.pin2.getValue()));
				break;
			case NOT:
				this.out.setValue(~this.pin1.getValue());
				break;
			default:
				System.err.print("Invalid Type!");
		}
	}
	
	public Pin getPin1() {
		return this.pin1;
	}
	
	public Pin getPin2(){
		return this.pin2;
	}
	
	public Pin getOut(){
		return this.out;
	}
	
	public Point getPin1upPosition() {
		return this.pin1.getUp();
	}
	
	public Point getPin1downPosition() {
		return this.pin1.getDown();
	}
	public Point getPin2upPosition() {
		return this.pin2.getUp();
	}
	
	public Point getPin2downPosition() {
		return this.pin2.getDown();
	}
	
	public Point getOutUpPosition() {
		return this.out.getUp();
	}
	
	public Point getOutDownPosition() {
		return this.out.getDown();
	}
	
	public File getSymbol() {
		return symbol;
	}
	
	public Image getImage() {
		return slika;
	}
	
	public int getPinValue() {
		if(this.type != Element.NOT) {
			System.err.println("getPinValue() is only for NOT");
			System.exit(102);
		}
		return this.pin1.getValue();
	}
	
	public void setPinValue(int value) {
		if(this.type != Element.NOT) {
			System.err.println("getPinValue() is only for NOT");
			System.exit(102);
		}
		this.pin1.setValue(value);
	}
	public int getPin1value() {
		return this.pin1.getValue();
	}
	
	public void setPin1value(int value) {
		this.pin1.setValue(value);
	}
	
	public int getPin2value() {
		return this.pin2.getValue();
	}
	
	public void setPin2value(int value) {
		this.pin2.setValue(value);
	}

	public int getOutValue() {
		return this.out.getValue();
	}
	
	public void setOut(int value) {
		this.out.setValue(value);
	}
}