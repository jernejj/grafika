package grafika;

import java.awt.Point;
import java.io.File;

public class Element {
	private int type;
	private File symbol;
	private short pin1;
	private short pin2;
	private short out;
	
	public static final int AND = 0;
	public static final int OR = 1;
	public static final int NAND = 2;
	public static final int NOR = 3;
	public static final int XOR = 4;
	public static final int XNOR = 5;
	public static final int NOT = 6;
	
	private Point position; 
	
	public Element(int type) {
		setType(type);
	}
	
	private void setPin1(short value) {
		pin1 = value;
	}
	
	private void setPin2(short value) {
		pin2 = value;
	}
	
	public void setPins(short valuePin1, short valuePin2) {
		setPin1(valuePin1);
		setPin2(valuePin2);
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point newPosition) {
		position = newPosition;
	}
	
	public void setPosition(int newX, int newY) {
		position = new Point(newX,newY);
	}
	
	// TODO: enumeration
	public void setType(int type) {
		switch(type) {
			case AND:
				type=type;
				symbol = new File(/*"grafika*/"logicalOperators/and.png");
				break;
			case OR:
				type=type;
				symbol = new File(/*"grafika*/"logicalOperators/or.png");
				break;
			case NAND:
				type=type;
				symbol = new File(/*"grafika*/"logicalOperators/nand.png");
				break;
			case NOR:
				type=type;
				symbol = new File(/*"grafika*/"logicalOperators/nor.png");
				break;
			case XOR:
				type=type;
				symbol = new File(/*"grafika*/"logicalOperators/xor.png");
				break;
			case XNOR:
				type=type;
				symbol = new File(/*"grafika*/"logicalOperators/xnor.png");
				break;
			case NOT:
				type=type;
				symbol = new File(/*"grafika*/"logicalOperators/not.png");
				break;
			default:
				System.err.print("Invalid Type!");
		}
	}
	
	public File getSymbol() {
		return symbol;
	}
}
