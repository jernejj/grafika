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

	public void init() { 
		this.setBackground(Color.WHITE);
		this.setForeground(Color.WHITE);
		
		Grafika.verbose = true;
		
		GrafikaCanvas = new GrafikaCanvas(this);
		options = new Options(this);
		documentation = new Documentation();
		menu = new Menu(this);
		
		int requestedWidth = 0;
		String windowWidthString = this.getParameter("WINDOWWIDTH");
		if (windowWidthString != null) {
		    try {
		        requestedWidth = Integer.parseInt(windowWidthString);
		    } catch (NumberFormatException e) {
		        //Use default width.
		    }
		}
		
		this.setSize(1024,768);
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
	
	// TODO:
//    public String[][] getParameterInfo() {
//        String[][] info = {
//          // Parameter Name     Kind of Value   Description
//            {"imagesource",     "URL",          "a directory"},
//            {"startup",         "URL",          "displayed at startup"},
//            {"background",      "URL",          "displayed as background"},
//            {"startimage",      "int",          "start index"},
//            {"endimage",        "int",          "end index"},
//            {"namepattern",     "URL",          "used to generate indexed names"},
//            {"pause",           "int",          "milliseconds"},
//            {"pauses",          "ints",         "milliseconds"},
//            {"repeat",          "boolean",      "repeat or not"},
//            {"positions",       "coordinates",  "path"},
//            {"soundsource",     "URL",          "audio directory"},
//            {"soundtrack",      "URL",          "background music"},
//            {"sounds",          "URLs",         "audio samples"},
//        };
//        return info;
//    }    
    
    public String getAppletInfo() {
        return "Grafika applet by: \n\n- Jernej Jerebic\n- Samo Šela\n- Simon Štriker\n\nAll rights reserved!";
    }
}