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
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;


class GrafikaCanvas extends Canvas implements Runnable  {

	private static final long serialVersionUID = -1832060394418052832L;
	// mouse actions
	Point thispoint=new Point(0,0); // current mouseposition
	Point oldpoint=new Point(0, 0); // previous position of node being moved

	// list  
	Vector<Element> elementList = new Vector<Element>();
	// Vector<Element> outputList = new Vector<Element>();
	Vector<Line> lineList = new Vector<Line>();
	Vector<Element> listForGenerator = new Vector<Element>();
	int elementType = -1;

	// current action
	private boolean moveElement = false;
	private boolean drawLine = false;
	private boolean clicked = false;
	private Element selectedElement = null;
	private Element tempElement = null;
	private Element elementOnMouseDown = null;
	private Pin selectedPin = null;
	private Pin pinOnMouseDown = null;
	private Line tempLine = null;
	private Line selectedLine = null;
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
	private Vector<Element> startList = new Vector<Element>();
	private Vector<Element> AlgSet = new Vector<Element>();
	private boolean firstTime = true;
	private Random rnd = new Random();
	// private int step;
	int step;
	private int maxSteps;
	boolean performAlgorithm;
	boolean loop = false;
	boolean stepThrough=false;

	// locking the screen while running the algorithm
	boolean Locked = false;

	Grafika parent;

	GrafikaCanvas(Grafika myparent) {
		parent = myparent;
		setBackground(Color.WHITE);
	}

	/** lock screen while running an algorithm */
	public void lock() {
		Locked=true;
	}

	public void unlock() {
		Locked=false;
	}

	public void start() {
		if (algrthm != null) algrthm.resume();
	}

	public boolean init() {
		maxSteps = (int)Math.pow((double)2,(double)parent.options.number);
		Element tmpStart;
		for(Iterator<Element> i = this.elementList.iterator(); i.hasNext(); ) {
			tmpStart = i.next();
			if(tmpStart.getType() == Element.OUTPUT)
				startList.add(tmpStart);
		}
		return !startList.isEmpty();
	}

	/** removes elements and arrows from screen */
	public void clear() {
		setBackground(Color.WHITE);
		this.elementList.clear();
		// this.outputList.clear();
		this.listForGenerator.clear();
		this.lineList.clear();
		Grafika.element = 0;
		this.step = 0;
		parent.options.b3.setText("step");
		parent.options.b3.setActionCommand("STEP");
		parent.options.b3.setToolTipText("step");
		parent.unlock();
		repaint();
	}

	/** resets a graph after running an algorithm */
	public void reset() {
		init(); 
		this.step = 0;
		this.AlgSet.clear();
		this.firstTime = true;
		parent.unlock();
		repaint();
	}

//	/** gives an animation of the algorithm */
	public void runAlgorithm() {
		parent.lock();
//		initalg();
		performAlgorithm = true;
		algrthm = new Thread(this);
		algrthm.start();
	}

	public void algorithm(Element e) {
		if(e.getType() == Element.GND || e.getType() == Element.VCC || e.getType() == Element.GENOUT) {
			e.compute(step);
			this.AlgSet.add(e);
		} else {
			if(firstTime) {
				if(AlgSet.contains(e.getLineToPin1().getPartTowardsOut())) {
					if(e.getType() != Element.OUTPUT) {
						e.setOut(rnd.nextInt(2));
					} 
				} else {
					this.AlgSet.add(e);
					algorithm(e.getLineToPin1().getPartTowardsOut());
					this.AlgSet.remove(e);
				}
				if(e.getType() != Element.NOT && e.getType() != Element.OUTPUT) {
					if(AlgSet.contains(e.getLineToPin2().getPartTowardsOut())) {
						if(e.getType() != Element.OUTPUT) {
							e.setOut(rnd.nextInt(2));
						}
					} else {			
						this.AlgSet.add(e);
						algorithm(e.getLineToPin2().getPartTowardsOut());
						this.AlgSet.remove(e);
					}
				}
				this.AlgSet.add(e);
			}
			else {
				if(AlgSet.contains(e.getLineToPin1().getPartTowardsOut())) {
					if(e.getType() != Element.OUTPUT) {
						e.setOut(e.getLineToPin1().getPartTowardsOut().getOutValue());
					}
				} else {
					this.AlgSet.add(e);
					algorithm(e.getLineToPin1().getPartTowardsOut());
					this.AlgSet.remove(e);
				}
				if(e.getType() != Element.NOT && e.getType() != Element.OUTPUT) {
					if(AlgSet.contains(e.getLineToPin2().getPartTowardsOut())) {
						if(e.getType() != Element.OUTPUT) {
							e.setOut(e.getLineToPin2().getPartTowardsOut().getOutValue());
						}
					} else {		
						this.AlgSet.add(e);
						algorithm(e.getLineToPin2().getPartTowardsOut());
						this.AlgSet.remove(e);
					}
				}
				this.AlgSet.add(e);
			}
			e.compute(step);
		}		
	}


//	public void algorithm(Element e) {
//	if(e.getType() == Element.GND || e.getType() == Element.VCC || e.getType() == Element.GENOUT) {
//	e.compute(step);
//	} else {
//	algorithm(e.getLineToPin1().getPartTowardsOut());
//	if(e.getType() != Element.NOT && e.getType() != Element.OUTPUT) {
//	algorithm(e.getLineToPin2().getPartTowardsOut());
//	}
//	}		
//	e.compute(step);
//	}

	public void step() {
		if(step == maxSteps) {step = 0;}
		for(Iterator<Element> i = this.startList.iterator(); i.hasNext(); ) {
			algorithm(i.next());
			
		}
		this.firstTime = false;
		this.AlgSet.clear();
		step++;
		repaint();
	}

	public void stop() {
		if (algrthm != null) algrthm.suspend();
	}


	public void run() {
		// TODO: Algoritem za izracun
		if(loop) {
		for(int i=0; i<this.elementList.size(); i++){
			step();
			try { Thread.sleep(2000); }
			catch (InterruptedException e) {}
		}
		}
		else {
			;
		}
		algrthm = null;
	}

	// TODO: Dodajmo en primer za zacetek
	public void showexample() {
		// draws a graph on the screen

		clear();
		init();

		Error.error("Se ni primera!");


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
				//Delete line
				if(null != (selectedLine = lineHit(x, y, 0.05))){
					lineDelete(selectedLine);
				}
			}
			else if (null != (temp = elementHit(x, y))) { 
				// Zadel si element
				elementOnMouseDown = selectedElement = temp;
				offset = offset(selectedElement,new Point(x,y));
				moveElement = true;

				if(Grafika.verbose) System.err.println("GrafikaCanvas.mouseDown(): "+elementOnMouseDown);

				Pin tempPin = null;
				if(null != (tempPin = pinHit(temp,x,y))) {
					moveElement = false;
					if(tempPin.getType() == Pin.OUT) {
						drawLine = true;
						pinOnMouseDown = selectedPin = tempPin;
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
							System.err.println("GrafikaCanvas.mouseDown(): PIN ?!?");
						}
						lineList.add(tempLine);
						selectedElement.setLine(tempLine,selectedPin);	
					}
					else {
						if(!temp.isPinSet(tempPin.getType())) {
							drawLine = true;
							pinOnMouseDown = selectedPin = tempPin;
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
								System.err.println("GrafikaCanvas.mouseDown(): PIN ?!?");
							}
							lineList.add(tempLine);
							selectedElement.setLine(tempLine,selectedPin);	
						}
					}
				}
			}
			else if(null != (selectedLine = lineHit(x, y, 0.05))) {
				System.err.println("GrafikaCanvas.mouseDown() - SelectedLine: "+selectedLine.toString());
				if(selectedLine.getColor().equals(Color.BLACK))
					selectedLine.setColor(Color.RED);
				else if(selectedLine.getColor().equals(Color.RED))
					selectedLine.setColor(Color.BLACK);
			}
			else {
				// Zadel si prazno polje, narisemo nov izbrani element!
				if(elementType == -1) {
					parent.documentation.doctext.showline("IZBERI_ELEMENT");
				}
				else {
//					if(elementType == Element.OUTPUT)
//					addNewOutPutElement(new Element(parent, elementType, new Point(x,y)));
//					else
					addNewElement(new Element(parent, elementType, new Point(x,y)));
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
					System.err.println("GrafikaCanvas.mouseDrag(): PIN ?!?");
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
				if(null != (tempElement =  elementHit(x,y))) {					
					if(tempElement != elementOnMouseDown) {
						Pin tempPin = null;
						if(null != (tempPin = pinHit(tempElement, x, y))) {
							if((tempPin.getType() == Pin.OUT) && (pinOnMouseDown.getType() != tempPin.getType()) && ((pinOnMouseDown.getType() == Pin.OUT && tempPin.getType() == Pin.IN1) || (pinOnMouseDown.getType() == Pin.IN1 && tempPin.getType() == Pin.OUT) || (pinOnMouseDown.getType() == Pin.IN2 && tempPin.getType() == Pin.OUT) || (pinOnMouseDown.getType() == Pin.OUT && tempPin.getType() == Pin.IN2))) {
								offset = offset(tempElement,new Point(x,y));
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
									System.err.println("GrafikaCanvas.mouseUp(): PIN ?!?");
								}
								tempElement = null;

							} 
							else if((!tempElement.isPinSet(tempPin.getType())) && (pinOnMouseDown.getType() != tempPin.getType()) && ((pinOnMouseDown.getType() == Pin.OUT && tempPin.getType() == Pin.IN1) || (pinOnMouseDown.getType() == Pin.IN1 && tempPin.getType() == Pin.OUT) || (pinOnMouseDown.getType() == Pin.IN2 && tempPin.getType() == Pin.OUT) || (pinOnMouseDown.getType() == Pin.OUT && tempPin.getType() == Pin.IN2))) {
								offset = offset(tempElement,new Point(x,y));
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
									System.err.println("GrafikaCanvas.mouseUp(): PIN ?!?");
								}
								tempElement = null;
							} else {
								lineDelete(tempLine);
								tempElement = null;
							}
						} else {
							lineDelete(tempLine);
							tempElement = null;
						}
					} else {
						lineDelete(tempLine);
					}
				} 
				else {
					//	this.lineList.remove(tempLine);
					lineDelete(tempLine);
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
//		if(this.elementList.isEmpty())
//		return null;
		for (Iterator<Element> i = this.elementList.iterator(); i.hasNext(); ) {
			check = i.next();
			if ( ((x > check.getXposition() && (x < (check.getXposition() + check.getSizeX())))) &&  
					((y > check.getYposition() && (y < (check.getYposition() + check.getSizeY())))) ) {
				return check;
			}
		}
//		for (Iterator<Element> i = this.outputList.iterator(); i.hasNext(); ) {
//		check = i.next();
//		if ( ((x > check.getXposition() && (x < (check.getXposition() + check.getSizeX())))) &&  
//		((y > check.getYposition() && (y < (check.getYposition() + check.getSizeY())))) ) {
//		return check;
//		}
//		}
		return null;
	}

	public Pin pinHit(Element e, int x, int y){
		int absX = x - e.getXposition();
		int absY = y - e.getYposition();

		if(e.getType() == Element.NOT) {
			// Pin clicked
			if( absX >= e.getPin1upPosition().x &&  absX <= e.getPin1downPosition().x && absY >= e.getPin1upPosition().y &&  absY <= e.getPin1downPosition().y) {
				if(Grafika.verbose) System.out.println("GrafikaCanvas.pinHit(): Pin1 clicked.");

				return e.getPin1();
			}
			// Out clicked
			else if(absX >= e.getOutUpPosition().x &&  absX <= e.getOutDownPosition().x && absY >= e.getOutUpPosition().y &&  absY <= e.getOutDownPosition().y) {
				if(Grafika.verbose) System.out.println("GrafikaCanvas.pinHit(): Out clicked.");

				return e.getOut();
			}
		}
		else if (e.getType() == Element.OUTPUT) {
			// In clicked
			if(absX >= e.getPin1upPosition().x &&  absX <= e.getPin1downPosition().x && absY >= e.getPin1upPosition().y &&  absY <= e.getPin1downPosition().y) {
				if(Grafika.verbose) System.out.println("GrafikaCanvas.pinHit(): Out clicked.");

				return e.getPin1();
			}
		}
		else if (e.getType() == Element.GND) {
			// Out clicked
			if(absX >= e.getOutUpPosition().x &&  absX <= e.getOutDownPosition().x && absY >= e.getOutUpPosition().y &&  absY <= e.getOutDownPosition().y) {
				if(Grafika.verbose) System.out.println("GrafikaCanvas.pinHit(): Out clicked.");

				return e.getOut();
			}
		}
		else if (e.getType() == Element.VCC) {
			// Out clicked
			if(absX >= e.getOutUpPosition().x &&  absX <= e.getOutDownPosition().x && absY >= e.getOutUpPosition().y &&  absY <= e.getOutDownPosition().y) {
				if(Grafika.verbose) System.out.println("GrafikaCanvas.pinHit(): Out clicked.");

				return e.getOut();
			}
		}
		else if (e.getType() == Element.GENOUT) {
			// Out clicked
			if(absX >= e.getOutUpPosition().x &&  absX <= e.getOutDownPosition().x && absY >= e.getOutUpPosition().y &&  absY <= e.getOutDownPosition().y) {
				if(Grafika.verbose) System.out.println("GrafikaCanvas.pinHit(): Out clicked.");

				return e.getOut();
			}
		}		
		else {
			// Pin1 clicked
			if( absX >= e.getPin1upPosition().x &&  absX <= e.getPin1downPosition().x && absY >= e.getPin1upPosition().y &&  absY <= e.getPin1downPosition().y) {
				if(Grafika.verbose) System.out.println("GrafikaCanvas.pinHit(): Pin1 clicked.");

				return e.getPin1();
			}
			// Pin2 clicked
			else if(absX >= e.getPin2upPosition().x &&  absX <= e.getPin2downPosition().x && absY >= e.getPin2upPosition().y &&  absY <= e.getPin2downPosition().y) {
				if(Grafika.verbose) System.out.println("GrafikaCanvas.pinHit(): Pin2 clicked.");

				return  e.getPin2();
			}
			// Out clicked
			else if(absX >= e.getOutUpPosition().x &&  absX <= e.getOutDownPosition().x && absY >= e.getOutUpPosition().y &&  absY <= e.getOutDownPosition().y) {
				if(Grafika.verbose) System.out.println("GrafikaCanvas.pinHit(): Out clicked.");

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

	public Line lineHit(int x, int y, double dist) {
		Line tmpLine = null;
		double d,A,B;
//		float k;
		Point i;
		Point j;
		for(Iterator<Line> l = this.lineList.iterator(); l.hasNext();) {
			tmpLine = l.next();
			i = tmpLine.getOutPoint();
			j = tmpLine.getInPoint();

			d = d(i,j);
			A = d(i.x, i.y, x, y);
			B = d(j.x, j.y, x, y);

			if( ((A + B) < (d+dist)) &&  ((A + B) > (d-dist)))
				return tmpLine;

//			if((i.x - j.x) < 0.000000001)
//			k = 0;
//			else
//			k = (i.y - j.y)/(i.x - j.x);

//			if( (y <= ( k*(x - i.x) + i.y + dist)) && (y >= ( k*(x - i.x) + i.y - dist)))
//			return tmpLine;

		}
		return null;
	}

	public void elementDelete(Element e) {
		Line tmpLine;
		Element tmpElement;
		Vector<Line> tmpOutLines = e.getLinesToOut();
		Line tmpOneLine = null;
		for(Iterator<Line> i = tmpOutLines.iterator(); i.hasNext();) {
			tmpOneLine = i.next();
			if(null != (tmpLine = tmpOneLine)) {
				// if(Grafika.verbose) System.out.println("GrafikaCanvas.elementDelete(): "+tmpLine.toString());
				// Ce obstaja povezava pin_out, potem je potrebno pripadajocemu elementu nastaviti pin_in na null.
				tmpElement = tmpLine.getPartTowardsIn();
				if(tmpElement.getLineToPin1() == tmpLine)
					tmpElement.setLineToPin1(null);
				if(tmpElement.getLineToPin2() == tmpLine)
					tmpElement.setLineToPin2(null);
				this.lineList.remove(tmpLine);
			}
		}
		tmpOneLine = null;
		if(null != (tmpLine = e.getLineToPin1())) {
			// if(Grafika.verbose) System.out.println("GrafikaCanvas.elementDelete(): tmpLine.toString());
			// Ce obstaja povezava pin_in1, potem je potrebno pripadajocemu elementu nastaviti pin_out na null.
			tmpElement = tmpLine.getPartTowardsOut();
			tmpOutLines = tmpElement.getLinesToOut();
			for(Iterator<Line> i = tmpOutLines.iterator(); i.hasNext();) {
				tmpOneLine = i.next();

				if(tmpOneLine == tmpLine) {
					// tmpOutLines.remove(tmpOneLine);
					i.remove();
				}
			}
			this.lineList.remove(tmpLine);
		}

		tmpOneLine = null;
		if(null != (tmpLine = e.getLineToPin2())) {
			// if(Grafika.verbose) System.out.println("GrafikaCanvas.elementDelete(): tmpLine.toString());
			// Ce obstaja povezava pin_in2, potem je potrebno pripadajocemu elementu nastaviti pin_out na null.
			tmpElement = tmpLine.getPartTowardsOut();
			tmpOutLines = tmpElement.getLinesToOut();
			for(Iterator<Line> i = tmpOutLines.iterator(); i.hasNext();) {
				tmpOneLine = i.next();

				if(tmpOneLine == tmpLine) {
					// tmpOutLines.remove(tmpOneLine);
					i.remove();
				}
			}
			this.lineList.remove(tmpLine);
		}

		this.elementList.remove(e);
	}

	/**
	 * Pobrise Line l, ter se pobrise povezava na pinih elementov na katere je pritrjena 
	 * @param l
	 */
	public void lineDelete(Line l){
		Element tmpElement;
		if(null != (tmpElement = l.getPartTowardsIn())  ){
			//		if(Grafika.verbose) System.out.println("GrafikaCanvas.lineDelete(): tmpElement.toString());
			//na kateri pin je povezana povezava, potrebno je tistemu pinu pobrisat povezavo
			if(tmpElement.getLineToPin1() == l )
				tmpElement.setLineToPin1(null);
			else if(tmpElement.getLineToPin2() == l)
				tmpElement.setLineToPin2(null);
			else
				System.err.print("GrafikaCanvas.lineDelete(): Error in line delete in pin");
		}
		if(null !=(tmpElement = l.getPartTowardsOut() ) ) {
			//ce je line povezana na kaksen out pin, je treba ta out pin nastavit na null

			Vector<Line> tmpOutLines = tmpElement.getLinesToOut();
			Line tmpOneLine = null;
//			int zzz = 0;
//			System.out.println("Size: "+tmpOutLines.size());
			for(Iterator<Line> i = tmpElement.getLinesToOut().iterator(); i.hasNext();) {
//				System.out.println(tmpElement.toString());
//				System.out.println(tmpOutLines.isEmpty());
//				System.out.println("Ponovitev "+zzz);
				tmpOneLine = i.next();
				if(tmpOneLine == l ) {
//					System.out.println("IF stavek: "+tmpElement.toString());
					//tmpOutLines.remove(tmpOneLine);
					i.remove();
				}
				tmpOneLine = null;
			}
		}			
		this.lineList.remove(l);
	}

	// brez tega prihaja do blinkanja 
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
//		try {
//		img = ImageIO.read(e.symbol_path);

//		//img = new BufferedImage();
//		}
//		catch (IOException e1) {
//		e1.printStackTrace();
//		}
		g.drawImage(e.getBufferedImage(), e.getXposition(), e.getYposition(), e.getSizeX(), e.getSizeY(), null);
	}

	public void drawOutPutElement(Graphics g, Element e) {
		g.drawImage(e.getBufferedImage(), e.getXposition(), e.getYposition(), e.getSizeX(), e.getSizeY(), null);
	}

	public void drawOutPut(Graphics g, Element e ) {
		if(e.getType() == Element.OUTPUT)
			g.drawString(String.valueOf(e.getPin1value()), e.getXposition()+9, e.getYposition()+20);
		else if(e.getType() == Element.GENOUT)
			g.drawString(String.valueOf(e.getOutValue()), e.getXposition()+9, e.getYposition()+14);
		else
			Error.error("drawOutPut !");
	}

	public void addNewElement(Element e) {
		this.elementList.add(e);
		if(Grafika.verbose) {System.err.println("GrafikaCanvas.addNewElement(): "+e.getName());}
	}

	private void addNewOutPutElement(Element e) {
//		this.outputList.add(e);
		if(Grafika.verbose) {System.err.println("GrafikaCanvas.addNewOutPutElement(): "+e.getName());}
	}

	public void drawLine(Graphics g, Line l) {
		g.setColor(l.getColor());
//		if(Grafika.verbose) System.err.println("GrafikaCanvas.drawLine(): "+l.getColor().toString());
		g.drawLine(l.getOutPoint().x, l.getOutPoint().y, l.getInPoint().x, l.getInPoint().y);
		g.setColor(Color.BLACK);
	}

	private void repositionLines(Element e, int x, int y) {
		Line out;
		Line pin1;
		Line pin2;
		Pin tempPin;
		offset = offset(e,new Point(x,y));

		Vector<Line> tmpOutLines = e.getLinesToOut();
		Line tmpOneLine = null;
		for(Iterator<Line> i = tmpOutLines.iterator(); i.hasNext();) {
			tmpOneLine = i.next();
			if(null != (out = tmpOneLine)) {
				tempPin = e.getOut();
				out.setOutPoint(new Point((x - offset.x) + e.getOutUpPosition().x  + tempPin.getPinPosition().x, (y - offset.y) + e.getOutUpPosition().y + tempPin.getPinPosition().y));
			}
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

		// draw lines
		for(Iterator<Line> i = this.lineList.iterator(); i.hasNext(); ) {
			drawLine(g, i.next());
		}

		// draw elements
		for(Iterator<Element> i = this.elementList.iterator(); i.hasNext(); ) {
			drawElement(g, i.next());
		}

		// draw output values
		Element tmp;
		for(Iterator<Element> i = this.elementList.iterator(); i.hasNext(); ) {
			tmp = i.next();
			if(tmp.getType() == Element.OUTPUT || tmp.getType() == Element.GENOUT)
				drawOutPut(g, tmp);
		}
	}

	private double d(Point i, Point j) {
		return  Math.sqrt(Math.pow((double)(j.x-i.x), 2.0) + Math.pow((double)(j.y-i.y), 2.0));
	}

	private double d(int x1, int y1, int x2, int y2) {
		return  Math.sqrt(Math.pow((double)(x2-x1), 2.0) + Math.pow((double)(y2-y1), 2.0));
	}

	public void generateElementList() {
		this.listForGenerator.clear();
		Element temp;
		for(Iterator<Element> i = this.elementList.iterator(); i.hasNext(); ) {
			temp = i.next();
			if(temp.getType() != Element.GND && temp.getType() != Element.VCC && temp.getType() != Element.OUTPUT  && temp.getType() != Element.GENOUT)
				if(temp.getLineToPin1() == null || temp.getLineToPin1().getPartTowardsOut().getType() == Element.GENOUT) {
					this.listForGenerator.add(temp);
				}
				else if(temp.getLineToPin2() == null || temp.getLineToPin2().getPartTowardsOut().getType() == Element.GENOUT) {
					this.listForGenerator.add(temp);
				}
		}
		if(Grafika.verbose) System.err.println("GrafikaCanvas.generateElementList(): debug");
	}
}