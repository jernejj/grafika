package grafika;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
class GrafikaCanvas extends Canvas implements Runnable  {
	// mouse actions
	Point thispoint=new Point(0,0); // current mouseposition
	Point oldpoint=new Point(0, 0); // previous position of node being moved

	// list  
	Vector<Element> elementList = new Vector<Element>();
	Vector<Line> lineList = new Vector<Line>();
	int elementType = -1;
	
	// current action
	private boolean moveElement = false;
	private boolean drawLine = false;
	private boolean clicked = false;
	private Element selectedElement = null;
	private Element tempElement = null;
	private Pin selectedPin = null;
	private Line tempLine = null;
	private Point offset = null;

	// fonts
	Font roman= new Font("TimesRoman", Font.BOLD, 12);
	Font helvetica= new Font("Helvetica", Font.BOLD, 15);
	FontMetrics fmetrics = getFontMetrics(roman);

	int h = (int)fmetrics.getHeight()/3;

	// for double buffering
	private Image offScreenImage;
	private Graphics offScreenGraphics;
	private Dimension offScreenSize;

	// for run option
	Thread algrthm;

	boolean stepthrough=false;

	// locking the screen while running the algorithm
	boolean Locked = false;

	Grafika parent;

	GrafikaCanvas(Grafika myparent) {
		parent = myparent;
		init();
		setBackground(Color.white);
	}

	/** lock screen while running an algorithm */
	public void lock() {
		Locked=true;
	}

	public void unlock() {
		Locked=false;
	}

//	public void start() {
//		if (algrthm != null) algrthm.resume();
//	}

	public void init() {
		setBackground(Color.WHITE);
	}

	/** removes elements and arrows from screen */
	public void clear() {
		init();
		this.elementList.clear();
		this.lineList.clear();
		parent.unlock();
		repaint();
	}

	/** resets a graph after running an algorithm */
	public void reset() {
		init(); 
		// TODO: postavi generator na zacetek
		parent.unlock();
		repaint();
	}

//	/** gives an animation of the algorithm */
//	public void runalg() {
//		parent.lock();
//		initalg();
//		performalg = true;
//		algrthm = new Thread(this);
//		algrthm.start();
//	}

	/** lets you step through the algorithm */ 
//	public void stepalg() {
//		parent.lock();
//		initalg();
//		performalg = true;
//		nextstep();
//	}

//	public void initalg() {
//		init();
//		step=0;
//	}

	public void nextstep() {
		// TODO: step true generator
		repaint();
	}

//	public void stop() {
//		if (algrthm != null) algrthm.suspend();
//	}

	
	public void run() {
		// TODO: Algoritem za izracun
		for(int i=0; i<this.elementList.size(); i++){
			nextstep();
			try { Thread.sleep(2000); }
			catch (InterruptedException e) {}
		}
		algrthm = null;
	}
	
	// TODO: Dodajmo en primer za zacetek
	public void showexample() {
		// draws a graph on the screen
		int w, h;
		clear();
		init();

		w=this.getWidth()/8;
		h=this.getHeight()/8;
		
//		node[0]=new Point(w, h);     node[1]=new Point(3*w, h);   
//		node[2]=new Point(5*w, h);   node[3]=new Point(w, 4*h); 
//		node[4]=new Point(3*w, 4*h); node[5]=new Point(5*w, 4*h);
//		node[6]=new Point(w, 7*h);   node[7]=new Point(3*w, 7*h); 
//		node[8]=new Point(5*w, 7*h); node[9]=new Point(7*w, 4*h);


		repaint();
	}

	public boolean mouseDown(Event evt, int x, int y) {
		if (Locked)
			parent.documentation.doctext.showline("locked");
		else {
			Element temp;
			clicked = true;
			if (evt.controlDown()) {
				// Delete element
				if ( null != (temp=elementHit(x, y))) {
					elementDelete(temp);
				}
			}
			else if (null != (temp = elementHit(x, y))) { 
				// Zadel si element
				selectedElement = temp;
				offset = offset(selectedElement,new Point(x,y));
				moveElement = true;
				
				Pin tempPin = null;
				if(null != (tempPin = pinHit(temp,x,y))) {
					moveElement = false;
					drawLine = true;
					selectedPin = tempPin;
					if(tempPin.getType() == Pin.IN1) { 
						tempLine = new Line(null, null);
						tempLine.setPartTowardsIn(selectedElement);
						tempLine.setInPoint(new Point(x - offset.x + temp.getPin1upPosition().x  + tempPin.getPinPosition().x, y - offset.y + temp.getPin1upPosition().y + tempPin.getPinPosition().y));
						tempLine.setOutPoint(tempLine.getInPoint());
					}
					else if(tempPin.getType() == Pin.IN2) {
						tempLine = new Line(null, null);
						tempLine.setPartTowardsIn(selectedElement);
						tempLine.setInPoint(new Point(x - offset.x + temp.getPin2upPosition().x  + tempPin.getPinPosition().x, y - offset.y + temp.getPin2upPosition().y + tempPin.getPinPosition().y));
						tempLine.setOutPoint(tempLine.getInPoint());
					}
					else if(tempPin.getType() == Pin.OUT){ 
						tempLine = new Line(null, null);
						tempLine.setPartTowardsOut(selectedElement);
						tempLine.setOutPoint(new Point(x - offset.x + temp.getOutUpPosition().x  + tempPin.getPinPosition().x, y - offset.y + temp.getOutUpPosition().y + tempPin.getPinPosition().y));
						tempLine.setInPoint(tempLine.getOutPoint());
					}
					else {
						System.err.println("PIN ?!?");
					}
					lineList.add(tempLine);
					selectedElement.setLine(tempLine,selectedPin);	
				}
			}
			else {
				// Zadel si prazno polje, narisemo nov izbrani element!
				if(elementType == -1) {
					parent.documentation.doctext.showline("IZBERI_ELEMENT");
				}
				else {
					addNewElement(new Element(elementType, new Point(x,y)));
				}
			}
			repaint();
		}
		return true;
	}

	public boolean mouseDrag(Event evt, int x, int y) {
		if ( (!Locked) && clicked ) {
			if (moveElement) { 
				// premakni element popravi povezave 
				selectedElement.setPosition(dragElement(selectedElement, x, y));
				repositionLines(selectedElement, x, y);
				repaint();
			}
			else if (drawLine) {
				if(selectedPin.getType() == Pin.IN1) { 
					tempLine.setOutPoint(new Point(x,y));
				}
				else if (selectedPin.getType() == Pin.IN2) {
					tempLine.setOutPoint(new Point(x,y));
				}
				else if(selectedPin.getType() == Pin.OUT){ 
					tempLine.setInPoint(new Point(x,y));
				}
				else {
					System.err.println("PIN ?!?");
				}
				repaint();
			}
		}
		return true;
	}

	public boolean mouseUp(Event evt, int x, int y) {
		if ( (!Locked) && clicked ) {
			if(moveElement) {
				// premakni element popravi povezave 
				selectedElement.setPosition(dragElement(selectedElement, x, y));
				repositionLines(selectedElement, x, y);
				selectedElement = null;
				moveElement = false;
			}
			else if(drawLine) {
				if((null != (tempElement =  elementHit(x,y))) && (tempElement != selectedElement)) {
					Pin tempPin = null;

					// if (pinHit) -> tempPin is pinHit
					if(null != (tempPin = pinHit(tempElement, x, y))) {
						offset = offset(tempElement,new Point(x,y));
									
						// TODO: 5555 (glej list)

						if(tempPin.getType() == Pin.IN1) {
							tempLine.setPartTowardsIn(tempElement);
							tempElement.setLine(tempLine,tempPin);
							tempLine.setInPoint(new Point((x - offset.x) + tempElement.getPin1upPosition().x  + tempPin.getPinPosition().x, (y - offset.y) + tempElement.getPin1upPosition().y + tempPin.getPinPosition().y));
						}
						else if (tempPin.getType() == Pin.IN2) {
							tempLine.setPartTowardsIn(tempElement);
							tempElement.setLine(tempLine,tempPin);
							tempLine.setInPoint(new Point((x - offset.x) + tempElement.getPin2upPosition().x  + tempPin.getPinPosition().x, (y - offset.y) + tempElement.getPin2upPosition().y + tempPin.getPinPosition().y));
						}
						else if(tempPin.getType() == Pin.OUT){ 
							tempLine.setPartTowardsOut(tempElement);
							tempElement.setLine(tempLine,tempPin);
							tempLine.setOutPoint(new Point((x - offset.x) + tempElement.getOutUpPosition().x  + tempPin.getPinPosition().x, (y - offset.y) + tempElement.getOutUpPosition().y + tempPin.getPinPosition().y));
						}
						else {
							System.err.println("PIN ?!?");
						}
						tempElement = null;
					} else {
						this.lineList.remove(tempLine);
						tempElement = null;
					}
				} 
				else {
					this.lineList.remove(tempLine);
				}
				tempLine = null;
				drawLine = false;
			}
			repaint();
		}
		clicked = false;
		return true;
	}

	// TODO: POPRAVI MEJE DA SE NE PREKRIVAJO ELEMENTI
	public Element elementHit(int x, int y) {
		// checks if you hit an element with your mouseclick
		Element check;
		if(this.elementList.isEmpty())
			return null;
		for (Iterator<Element> i = this.elementList.iterator(); i.hasNext(); ) {
			check = i.next();
			if ( ((x > check.getXposition() && (x < (check.getXposition() + check.getSizeX())))) &&  
					((y > check.getYposition() && (y < (check.getYposition() + check.getSizeY())))) ) {
				return check;
			}
		}
		return null;
	}
	
	public Pin pinHit(Element e, int x, int y){
		int absX = x - e.getXposition();
		int absY = y - e.getYposition();
		
		if(e.getType() == Element.NOT) {
			// Pin clicked
			if( absX >= e.getPin1upPosition().x &&  absX <= e.getPin1downPosition().x && absY >= e.getPin1upPosition().y &&  absY <= e.getPin1downPosition().y) {
				System.out.println("Pin1 clicked.");

				return e.getPin1();
			}
			// Out clicked
			else if(absX >= e.getOutUpPosition().x &&  absX <= e.getOutDownPosition().x && absY >= e.getOutUpPosition().y &&  absY <= e.getOutDownPosition().y) {
				System.out.println("Out clicked.");

				return e.getOut();
			}
		}
		else if (e.getType() == Element.GND) {
			// Out clicked
			if(absX >= e.getOutUpPosition().x &&  absX <= e.getOutDownPosition().x && absY >= e.getOutUpPosition().y &&  absY <= e.getOutDownPosition().y) {
				System.out.println("Out clicked.");

				return e.getOut();
			}
		}
		else if (e.getType() == Element.VCC) {
			// Out clicked
			if(absX >= e.getOutUpPosition().x &&  absX <= e.getOutDownPosition().x && absY >= e.getOutUpPosition().y &&  absY <= e.getOutDownPosition().y) {
				System.out.println("Out clicked.");

				return e.getOut();
			}
		}
		else {
			// Pin1 clicked
			if( absX >= e.getPin1upPosition().x &&  absX <= e.getPin1downPosition().x && absY >= e.getPin1upPosition().y &&  absY <= e.getPin1downPosition().y) {
				System.out.println("Pin1 clicked.");

				return e.getPin1();
			}
			// Pin2 clicked
			else if(absX >= e.getPin2upPosition().x &&  absX <= e.getPin2downPosition().x && absY >= e.getPin2upPosition().y &&  absY <= e.getPin2downPosition().y) {
				System.out.println("Pin2 clicked.");

				return  e.getPin2();
			}
			// Out clicked
			else if(absX >= e.getOutUpPosition().x &&  absX <= e.getOutDownPosition().x && absY >= e.getOutUpPosition().y &&  absY <= e.getOutDownPosition().y) {
				System.out.println("Out clicked.");
				
				return e.getOut();
			}
		}
		return null;
	}
		
	private Point offset(Element e, Point p) {
		int offsetX = p.x - e.getXposition();
		int offsetY = p.y - e.getYposition();
		return new Point(offsetX, offsetY);
	}
	
	public Point dragElement(Element e, int x, int y) {		
		return new Point(x - offset.x, y - offset.y);
	}
	
	public boolean lineHit(int x, int y, int dist) {
		// TODO: poglej, ce si zadel povezavo
		return false;
	}

	public void elementDelete(Element e) {
		Line tmpLine;
		Element tmpElement;
		if(null != (tmpLine = e.getLineToOut())) {
			// System.out.println(tmpLine.toString());
			// Ce obstaja povezava pin_out, potem je potrebno pripadajocemu elementu nastaviti pin_in na null.
			tmpElement = tmpLine.getPartTowardsIn();
			if(tmpElement.getLineToPin1() == tmpLine)
				tmpElement.setLineToPin1(null);
			if(tmpElement.getLineToPin2() == tmpLine)
				tmpElement.setLineToPin2(null);
			this.lineList.remove(tmpLine);
		}
		if(null != (tmpLine = e.getLineToPin1())) {
			// System.out.println(tmpLine.toString());
			// Ce obstaja povezava pin_in1, potem je potrebno pripadajocemu elementu nastaviti pin_out na null.
			tmpElement = tmpLine.getPartTowardsOut();
			if(tmpElement.getLineToOut() == tmpLine)
				tmpElement.setLineToOut(null);
			this.lineList.remove(tmpLine);
		}
		if(null != (tmpLine = e.getLineToPin2())) {
			// System.out.println(tmpLine.toString());
			// Ce obstaja povezava pin_in2, potem je potrebno pripadajocemu elementu nastaviti pin_out na null.
			tmpElement = tmpLine.getPartTowardsOut();
			if(tmpElement.getLineToOut() == tmpLine)
				tmpElement.setLineToOut(null);
			this.lineList.remove(tmpLine);
		}
		this.elementList.remove(e);
	}
	
	// brez tega prihaja do blinkanja 
	@SuppressWarnings("deprecation")
	public final synchronized void update(Graphics g) {
		// prepare new image offscreen
		Dimension d = getSize();
		if ((offScreenImage == null) || (d.width != offScreenSize.width) ||	(d.height != offScreenSize.height)) {
			offScreenImage = createImage(d.width, d.height);
			offScreenSize = d;
			offScreenGraphics = offScreenImage.getGraphics();
		}
		offScreenGraphics.setColor(Color.white);
		offScreenGraphics.fillRect(0, 0, d.width, d.height);
		paint(offScreenGraphics);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	
	public void drawElement(Graphics g, Element e) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(e.getSymbol());
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		g.drawImage(img, e.getXposition(), e.getYposition(), e.getSizeX(), e.getSizeY(), null);
	}
	
	public void addNewElement(Element e) {
		this.elementList.add(e);
	}
	
	public void drawLine(Graphics g, Line l) {
		 g.drawLine(l.getOutPoint().x, l.getOutPoint().y, l.getInPoint().x, l.getInPoint().y);
	}
	
	private void repositionLines(Element e, int x, int y) {
		Line out;
		Line pin1;
		Line pin2;
		Pin tempPin;
		offset = offset(e,new Point(x,y));
		if(null != (out = e.getLineToOut())) {
			tempPin = e.getOut();
			out.setOutPoint(new Point((x - offset.x) + e.getOutUpPosition().x  + tempPin.getPinPosition().x, (y - offset.y) + e.getOutUpPosition().y + tempPin.getPinPosition().y));
		}
		if(null != (pin1 = e.getLineToPin1())) {
			tempPin = e.getPin1();
			pin1.setInPoint(new Point((x - offset.x) + e.getPin1upPosition().x  + tempPin.getPinPosition().x, (y - offset.y) + e.getPin1upPosition().y + tempPin.getPinPosition().y));
		}
		if(null != (pin2 = e.getLineToPin2())) {
			tempPin = e.getPin2();
			pin2.setInPoint(new Point((x - offset.x) + e.getPin2upPosition().x  + tempPin.getPinPosition().x, (y - offset.y) + e.getPin2upPosition().y + tempPin.getPinPosition().y));
		}
	}
	
	public void paint(Graphics g) {
		g.setFont(roman);
		g.setColor(Color.black);

		// draw element
		for(Iterator<Element> i = this.elementList.iterator(); i.hasNext(); ) {
				drawElement(g, i.next());
		}
		
		for(Iterator<Line> i = this.lineList.iterator(); i.hasNext(); ) {
			drawLine(g, i.next());
		}

	}
}