package grafika;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.JApplet;


public class Grafika extends JApplet {

	private static final long serialVersionUID = -2741614183571105141L;

	GrafikaCanvas GrafikaCanvas;
	Options options;   
	Documentation documentation;
	Menu menu;
	Images images;
	
	static int element = 0;
	static Boolean verbose;

	int windowWidth;
	int windowHeight;
	
	public void init() { 
		this.setBackground(Color.WHITE);
		this.setForeground(Color.WHITE);
		
		Grafika.verbose = true;
		
		
		
		
		int requestedWidth = 0;
		String windowWidthString = this.getParameter("WIDTH");
		if (windowWidthString != null) {
		    try {
		        requestedWidth = Integer.parseInt(windowWidthString);
		    } catch (NumberFormatException e) {
		        //Use default width.
		    }
		}
		
		if(requestedWidth > 800 && requestedWidth < 2000) {
			this.windowWidth = requestedWidth;
		} else {
			this.windowWidth = 1024;
		}
		
		int requestedHeight = 0;
		String windowHeightString = this.getParameter("HEIGHT");
		if (windowWidthString != null) {
		    try {
		    	requestedHeight = Integer.parseInt(windowHeightString);
		    } catch (NumberFormatException e) {
		        //Use default height.
		    }
		}
		
		if(requestedHeight > 800 && requestedHeight < 2000) {
			this.windowHeight = requestedHeight;
		} 
		else {
			this.windowHeight = 768;
		}
		
		String debugVerbose = this.getParameter("VERBOSE");
		if( debugVerbose.equalsIgnoreCase("true")) {
			Grafika.verbose = true;
		} else {
			Grafika.verbose = false;
		}
		
		GrafikaCanvas = new GrafikaCanvas(this);
		options = new Options(this);
		documentation = new Documentation();
		menu = new Menu(this);
		this.setSize(windowWidth,windowHeight);
		BorderLayout bl = new BorderLayout(0, 0);
		this.setLayout(bl);
		this.add("Center", GrafikaCanvas);
		this.add("North", documentation);
		this.add("East", options);
		this.add("West", menu);
		this.images = new Images(this);
		
		//System.out.println(images.elementBufferedImageAND.toString());
	}

	/**
	 * Insets insets()
	 * Creates and initializes a new Insets object with the specified top, left, bottom, and right insets.
	 * 		Insets(int top, int left, int bottom, int right)
	 */
	public Insets insets() {
		return new Insets(10, 10, 10, 10);
	}

	public void lock() {
		GrafikaCanvas.lock();
		options.lock();
	} 

	public void unlock() {
		GrafikaCanvas.unlock();
		options.unlock();
	} 
	
    public String[][] getParameterInfo() {
        String[][] info = {
          // Parameter Name     Kind of Value   Description
            {"width",     "Pixels",          "Window width."},
            {"height",         "Pixels",          "Window height."},
            {"verbose",      "boolean",          "For debug output."},
        };
        return info;
    }    
    
    public String getAppletInfo() {
        return "Grafika applet by: \n\n- Jernej Jerebic\n- Samo Šela\n- Simon Štriker\n\nAll rights reserved!";
    }
}