package grafika;

import java.awt.Image;
import java.awt.Point;
import java.io.File;

public class Element {
	private int type;
	private File symbol;
	private short pin1;
	private short pin2;
	private short out;
	
	private Povezava naPin1;
	private Povezava naPin2;
	private Povezava naOut;
	
	private Point pin1up;
	private Point pin1down;
	private Point pin2up;
	private Point pin2down;
	private Point outup;
	private Point outdown;
	
	private Image slika;
	
	private int size;
	
	public static final int AND = 0;
	public static final int OR = 1;
	public static final int NAND = 2;
	public static final int NOR = 3;
	public static final int XOR = 4;
	public static final int XNOR = 5;
	public static final int NOT = 6;
	
	private int sizeX;
	private int sizeY;
	
	private Point position; 
	
	public Element() {}
	
	public Element(int type, Point position) {
		this.position = position;
		// TODO: potrebno je dodati k vsakemu gradniku, ko se spremenijo slike in to globalno pobrisati
		// sizeX, sizeY, size
		sizeX = 40;
		sizeY = 62;
		size = sizeX * sizeY;
		setType(type);	
	}
	
	private void setPin1(short value) {
		pin1 = value;
	}
	
	private void setPin2(short value) {
		pin2 = value;
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
		setPin1(valuePin1);
		setPin2(valuePin2);
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
				type=type;
				symbol = new File("grafika/logicalOperators/and.png");
				sizeX = 30;
				sizeY = 47;
				size = sizeX * sizeY;
				this.pin1up = new Point(6,0);
				this.pin1down = new Point(9,5);
				this.pin2up = new Point(20,0);
				this.pin2down = new Point(23,5);
				this.outup = new Point(13,41);
				this.outdown = new Point(16,46);
				break;
			case OR:
				type=type;
				symbol = new File("grafika/logicalOperators/or.png");
				break;
			case NAND:
				type=type;
				symbol = new File("grafika/logicalOperators/nand.png");
				break;
			case NOR:
				type=type;
				symbol = new File("grafika/logicalOperators/nor.png");
				break;
			case XOR:
				type=type;
				symbol = new File("grafika/logicalOperators/xor.png");
				break;
			case XNOR:
				type=type;
				symbol = new File("grafika/logicalOperators/xnor.png");
				break;
			case NOT:
				type=type;
				symbol = new File("grafika/logicalOperators/not.png");
				break;
			default:
				System.err.print("Invalid Type!");
		}
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
	
	public Point getPin1up() {
		return this.pin1up;
	}
	
	public Point getPin1down() {
		return this.pin1down;
	}
	public Point getPin2up() {
		return this.pin2up;
	}
	
	public Point getPin2down() {
		return this.pin2down;
	}
	
	public Point getOutUp() {
		return this.outup;
	}
	
	public Point getOutDown() {
		return this.outdown;
	}
	
	public File getSymbol() {
		return symbol;
	}
	
	public Image getImage() {
		return slika;
	}
}
