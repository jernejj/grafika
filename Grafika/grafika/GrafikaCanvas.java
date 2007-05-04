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
import java.util.Vector;

class GrafikaCanvas extends Canvas implements Runnable {

	Vector<Element> elementList = new Vector<Element>();
	int elementType = -1;
	
	// #########################################################
	//	drawing area for the graph
	final int MAXNODES = 20;
	final int MAX = MAXNODES+1;
	final int NODESIZE = 26;
	final int NODERADIX = 13;
	final int DIJKSTRA = 1;

	// basic graph information
	Point node[] = new Point[MAX];          // node
	Point startp[][] = new Point[MAX][MAX]; // start and
	Point endp[][] = new Point[MAX][MAX];   // endpoint of arrow

	// graph information while running algorithm
	boolean algedge[][] = new boolean[MAX][MAX];
	int dist[] = new int[MAX];
	int finaldist[] = new int[MAX];
	Color colornode[] = new Color[MAX];
	boolean changed[] = new boolean[MAX];   // indicates distance change during algorithm   
	int numchanged =0; 
	int neighbours=0;

	int step=0;

	// information used by the algorithm to find the next 
	// node with minimum distance
	int mindist, minnode, minstart, minend;

	int numnodes=0;      // number of nodes
	int emptyspots=0;    // empty spots in array node[] (due to node deletion)
	int startgraph=0;    // start of graph
	int hitnode;         // mouse clicked on or close to this node
	int node1, node2;    // numbers of nodes involved in current action

	Point thispoint=new Point(0,0); // current mouseposition
	Point oldpoint=new Point(0, 0); // previous position of node being moved

	// current action
	boolean newarrow = false;
	boolean movearrow = false;
	boolean movestart = false;
	boolean deletenode = false;
	boolean movenode = false;
	boolean performalg = false;
	boolean clicked = false;

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

	// current algorithm, (in case more algorithms are added)
	int algorithm;

	// algorithm information to be displayed in documetation panel
	String showstring = new String("");

	boolean stepthrough=false;

	// locking the screen while running the algorithm
	boolean Locked = false;

	Grafika parent;

	GrafikaCanvas(Grafika myparent) {
		parent = myparent;
		init();
		algorithm=DIJKSTRA;
		setBackground(Color.white);
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

	public void init() {
		setBackground(Color.WHITE);
		for (int i=0;i<MAXNODES;i++) {
			colornode[i]=Color.gray;
			for (int j=0; j<MAXNODES;j++)
				algedge[i][j]=false;
		}
		colornode[startgraph]=Color.blue;
		performalg = false;
	}

	/** removes graph from screen */
	public void clear() {
		startgraph=0;
		numnodes=0;
		emptyspots=0;
		init();
		for(int i=0; i<MAXNODES; i++) {
			node[i]=new Point(0, 0);
			for (int j=0; j<MAXNODES;j++)
				; // weight[i][j]=0;
		}
		if (algrthm != null) algrthm.stop();
		parent.unlock();
		repaint();
	}

	/** resets a graph after running an algorithm */
	public void reset() {
		init(); 
		// TODO: postavi generator na zacetek
		if (algrthm != null) algrthm.stop();
		parent.unlock();
		repaint();
	}

	/** gives an animation of the algorithm */
	public void runalg() {
		parent.lock();
		initalg();
		performalg = true;
		algrthm = new Thread(this);
		algrthm.start();
	}

	/** lets you step through the algorithm */ 
	public void stepalg() {
		parent.lock();
		initalg();
		performalg = true;
		nextstep();
	}

	public void initalg() {
		init();
		for(int i=0; i<MAXNODES; i++) {
			dist[i]=-1;
			finaldist[i]=-1;
			for (int j=0; j<MAXNODES;j++)
				algedge[i][j]=false;
		}
		dist[startgraph]=0;
		finaldist[startgraph]=0;
		step=0;
	}

	public void nextstep() {
		// calculates a step in the algorithm (finds a shortest 
		// path to a next node).
		finaldist[minend]=mindist;
		algedge[minstart][minend]=true;
		colornode[minend]=Color.orange;
		// build more information to display on documentation panel
		step++;
		repaint();
	}

	public void stop() {
		if (algrthm != null) algrthm.suspend();
	}

	public void run() {
		for(int i=0; i<(numnodes-emptyspots); i++){
			nextstep();
			try { algrthm.sleep(2000); }
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

		numnodes=10;
		emptyspots=0;

		for(int i=0; i<MAXNODES; i++) {
			node[i]=new Point(0, 0);
			for (int j=0; j<MAXNODES;j++)
				; // weight[i][j]=0;
		}

		w=this.size().width/8;
		h=this.size().height/8;
		node[0]=new Point(w, h);     node[1]=new Point(3*w, h);   
		node[2]=new Point(5*w, h);   node[3]=new Point(w, 4*h); 
		node[4]=new Point(3*w, 4*h); node[5]=new Point(5*w, 4*h);
		node[6]=new Point(w, 7*h);   node[7]=new Point(3*w, 7*h); 
		node[8]=new Point(5*w, 7*h); node[9]=new Point(7*w, 4*h);

		for (int i=0;i<numnodes;i++)
			for (int j=0;j<numnodes;j++)
				if ( true /* weight[i][j]>0 */)
					arrowupdate(i, j);
		repaint();
	}

	public boolean mouseDown(Event evt, int x, int y) {
		if (Locked)
			parent.documentation.doctext.showline("locked");
		else {
			clicked = true;
			if (evt.shiftDown()) {
				// move a node (gradnik)
				if (nodehit(x, y, NODESIZE)) {
					oldpoint = node[hitnode];
					node1 = hitnode;
					movenode=true;
				}
			}
			else if (evt.controlDown()) {
				// delete a node (gradnik)
				if (nodehit(x, y, NODESIZE)) {
					node1 = hitnode;
					if (startgraph==node1) {
						movestart=true;
						thispoint = new Point(x,y);
						colornode[startgraph]=Color.gray;
					}
					else
						deletenode= true;
				}
				// TODO: else if( pinHit() )
			}
			else if (arrowhit(x, y, 5)) {
				// change weihgt of an edge
				movearrow = true;
				repaint();
			}
			else if (nodehit(x, y, NODESIZE)) { // if(pinHit( ) ) => draw new line
				// draw a new arrow
				if (!newarrow) {
					newarrow = true;
					thispoint = new Point(x, y);
					node1 = hitnode;
				}
			}
			else if ( !nodehit(x, y, 50) && !arrowhit(x, y, 50) )  {
				// draw new node
				if (emptyspots==0) {
					// take the next available spot in the array
					if (numnodes < MAXNODES)
						node[numnodes++]=new Point(x, y);
					else  parent.documentation.doctext.showline("maxnodes");
				}
				else {
					// take an empty spot in the array (from previously deleted node)
					int i;
					for (i=0;i<numnodes;i++)
						if (node[i].x==-100) break;
					node[i]=new Point(x, y);
					emptyspots--;
				}
			}
			else {
				// mouseclick to close to a point or arrowhead, so probably an error
				parent.documentation.doctext.showline("toclose");
			}
			repaint();
		}
		return true;
	}

	public boolean mouseDrag(Event evt, int x, int y) {
		if ( (!Locked) && clicked ) {
			if (movenode) { // ce premikamo gradnik
				// move node and adjust arrows coming into/outof the node
				node[node1]=new Point(x, y);
				for (int i=0;i<numnodes;i++) {
					if (true) // popravi samo tiste, ki so povezani s tem gradnikom 
						arrowupdate(i, node1);
				}
				repaint();
			}
			else if (newarrow) {
				thispoint = new Point(x, y);
				repaint();
			}
		}
		return true;
	}

	public boolean mouseUp(Event evt, int x, int y) {
		if ( (!Locked) && clicked ) {
			if (movenode) {
				// move the node if the new position is not to close to 
				// another node or outside of the panel
				node[node1]=new Point(0, 0);
				if ( nodehit(x, y, 50) || (x<0) || (x>this.size().width) || 
						(y<0) || (y>this.size().height) ) {
					node[node1]=oldpoint;
					parent.documentation.doctext.showline("toclose");
				}
				else  {
					node[node1]=new Point(x, y);
				}
				movenode=false;
			}
			else if (deletenode) {
				nodedelete();
				deletenode=false;
			}
			else if (newarrow) {
				newarrow = false;
				if (nodehit(x, y, NODESIZE)) {
					node2=hitnode;
					if (node1!=node2) {
						arrowupdate(node1, node2);
						parent.documentation.doctext.showline("change weights");
					}
					else System.out.println("zelfde");
				}
			}
			repaint();
		}
		return true;
	}

	// TODO: rabimo pinHit
	public boolean nodehit(int x, int y, int dist) {
		// checks if you hit a node with your mouseclick
		for (int i=0; i<numnodes; i++)
			if ( (x-node[i].x)*(x-node[i].x) + 
					(y-node[i].y)*(y-node[i].y) < dist*dist ) {
				hitnode = i;
				return true;
			}
		return false;
	}
	
	public boolean arrowhit(int x, int y, int dist) {
		// checks if you hit an arrow with your mouseclick
		for (int i=0; i<numnodes; i++)
			for (int j=0; j<numnodes; j++) {
				// ce arrow obstaja && in smo kliknili v okolico puscice x^2 + y^2 < z^2 (kroznica) 
				if ( true /*( weight[i][j]>0 ) && (Math.pow(x-arrow[i][j].x, 2) + 	Math.pow(y-arrow[i][j].y, 2) < Math.pow(dist, 2) )*/ ) {
					node1 = i;
					node2 = j;
					return true;
				}
			}
		return false;
	}

	/** TODO: pobrisi gradnik in pripadajoce povezave
	 * delete a node and the arrows coming into/outof the node
	 */
	public void nodedelete() {
		node[node1]=new Point(-100, -100);
		for (int j=0;j<numnodes;j++) {
			// weight[node1][j]=0;
			// weight[j][node1]=0;
		}
		emptyspots++;
	}

	// TODO: line update
	public void arrowupdate(int p1, int p2) {
		// make a new arrow from pin p1 to p2  
		// calculate the resulting position of the arrowhead
		// int dx, dy;
		// direction line between p1 and p2
		// dx = node[p2].x - node[p1].x;
		// dy = node[p2].y - node[p1].y;
		// calculate the start and endpoints of the arrow,
		startp[p1][p2] = new Point(node[p1].x, node[p1].y);
		endp[p1][p2] = new Point(node[p2].x, node[p2].y);
	}

	public String intToString(int i) {
		return ""+(char)((int)'a'+i);
	}
	
	// TODO: kaj pocne ta funkcija? - "to klice applet" 
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

	
	public void drawNewElement(Graphics g, Element e) {
		
	}
	
	public void drawarrow(Graphics g, int i, int j) {
		// draw arrow between node i and node j
		
		// if edge already chosen by algorithm change color
		// if (algedge[i][j]) g.setColor(Color.orange);

		// draw arrow
		g.drawLine(startp[i][j].x, startp[i][j].y, endp[i][j].x, endp[i][j].y);
	}
	
	public void paint(Graphics g) {
		mindist=0;
		minnode=MAXNODES;
		minstart=MAXNODES;
		minend=MAXNODES;
		for(int i=0; i<MAXNODES; i++) 
			changed[i]=false;
		numchanged=0;
		neighbours=0;
		g.setFont(roman);
		g.setColor(Color.black);
		if (step==1)
			showstring="Grafika running: red arrows point to nodes reachable from " +
			" the startnode.\nThe distance to: ";
		else 
			showstring="Step " + step + ": Red arrows point to nodes reachable from " +
			"nodes that already have a final distance." +
			"\nThe distance to: ";
		// draw a new arrow upto current mouse position
		if (newarrow)
			g.drawLine(node[node1].x, node[node1].y, thispoint.x, thispoint.y);
		// draw all arrows
		for (int i=0; i<numnodes; i++)
			for (int j=0; j<numnodes; j++)
				if (true  /*weight [i][j]>0 */) { // TODO: narisi vse povezave
					// CE se pomnikamo po vrsticah generatorja osvezi vrednosti na posameznih nozicah (oziroma na izhodni)!
					if (performalg)
						; //detailsalg(g, i, j);
					drawarrow(g, i, j);
				}

		// draw the nodes
		for (int i=0; i<numnodes; i++)
			if (node[i].x>0) {
				g.setColor(colornode[i]);
				g.fillOval(node[i].x-NODERADIX, node[i].y-NODERADIX, 
						NODESIZE, NODESIZE);
			}
		// reflect the startnode being moved
		g.setColor(Color.blue);
		if (movestart)
			g.fillOval(thispoint.x-NODERADIX, thispoint.y-NODERADIX, 
					NODESIZE, NODESIZE);
		g.setColor(Color.black);
		
		// draw black circles around nodes, write their names to the screen
		g.setFont(helvetica);
		for (int i=0; i<numnodes; i++)
			if (node[i].x>0) {
				g.setColor(Color.black);
				g.drawOval(node[i].x-NODERADIX, node[i].y-NODERADIX, 
						NODESIZE, NODESIZE);
				g.setColor(Color.blue);
				g.drawString(intToString(i), node[i].x-14, node[i].y-14);
			}
	}
}