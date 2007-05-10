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
	int elementType = -1;
	
	// current action
	boolean deletenode = false;
	boolean moveElement = false;
	boolean performalg = false;
	boolean clicked = false;
	Element izbraniElement = null;

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

	/** removes graph from screen */
	public void clear() {
		init();
		this.elementList.clear();
		// TODO: pobrisi povezave
		if (algrthm != null) algrthm.stop();
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
				// delete element
				if ( null != (temp=elementHit(x, y))) {
					this.elementList.remove(temp);
					// TODO: pobrisi povezave, ce obstajajo!
				}
			}
			else if (null != (temp = elementHit(x, y))) { // if(pinHit( ) ) => draw new line
				izbraniElement = temp;
				moveElement = true;
				// TODO: if hit pin -> draw line !
				if(pinHit(temp,x,y)) {
					;
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
				izbraniElement.setPosition(new Point(x, y));
				// TODO: Popravi povezave!
				repaint();
			}
		}
		return true;
	}

	public boolean mouseUp(Event evt, int x, int y) {
		if ( (!Locked) && clicked ) {
			izbraniElement = null;
			moveElement = false;
			repaint();
		}
		clicked = false;
		return true;
	}

	// TODO: POPRAVI MEJE DA SE NE PREKRIVAJO ELEMENTI
	public Element elementHit(int x, int y) {
		// checks if you hit a node with your mouseclick
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
	
	public boolean pinHit(Element e, int x, int y){
		int absX = x - e.getXposition();
		int absY = y - e.getYposition();
		
		// Pin1 clicked
		if( absX > e.getPin1up().x &&  absX < e.getPin1down().x && absY > e.getPin1up().y &&  absY < e.getPin1down().y) {
			System.out.println("Pin1 clicked.");
			
			return true;
		}
		// Pin2 clicked
		else if(absX > e.getPin2up().x &&  absX < e.getPin2down().x && absY > e.getPin2up().y &&  absY < e.getPin2down().y) {
			System.out.println("Pin2 clicked.");
			
			return true;
		}
		// Out clicked
		else if(absX > e.getOutUp().x &&  absX < e.getOutDown().x && absY > e.getOutUp().y &&  absY < e.getOutDown().y) {
			System.out.println("Out clicked.");
			
			return true;
		}
		return false;
	}
	
	public boolean lineHit(int x, int y, int dist) {
		// TODO: poglej, ce si zadel povezavo
		return false;
	}

	/** TODO: pobrisi gradnik in pripadajoce povezave
	 * delete a node and the arrows coming into/outof the node
	 */
	public void elementDelete() {
		;
	}

	// TODO: line update
	public void lineUpdate(int p1, int p2) {
		
	}

	public String intToString(int i) {
		return ""+(char)((int)'a'+i);
	}
	
	// brez tega prihaja do blinkanja 
	@SuppressWarnings("deprecation")
	public final synchronized void update(Graphics g) {
		// prepare new image offscreen
		Dimension d=size();
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
	
	public void drawLine(Graphics g, int i, int j) {
		// g.drawLine();
	}
	
	public void paint(Graphics g) {
		g.setFont(roman);
		g.setColor(Color.black);

		// draw element
		for(Iterator<Element> i = this.elementList.iterator(); i.hasNext(); ) {
				drawElement(g, i.next());
		}

	}
}