package grafika;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.*;


public class Grafika extends JApplet {

	GrafikaCanvas GrafikaCanvas = new GrafikaCanvas(this);
	Options options = new Options(this);   
	Documentation documentation = new Documentation();
	Menu menu = new Menu(this);

	public void init() {
		this.setBackground(Color.WHITE);
		this.setForeground(Color.WHITE);
		setSize(1024,768);
		BorderLayout bl = new BorderLayout(0, 0);
		// TODO: 
		// bl.setHgap(0);
		// bl.setVgap(0);
		setLayout(bl);
		add("Center", GrafikaCanvas);
		add("North", documentation);
		add("East", options);
		add("West", menu);
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
}