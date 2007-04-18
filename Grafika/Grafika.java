
import java.awt.BorderLayout;
import java.awt.Insets;


public class Grafika extends java.applet.Applet {



    GrafikaCanvas GrafikaCanvas = new GrafikaCanvas(this);

    Options options = new Options(this);   

    Documentation documentation = new Documentation();



    public void init() {

	setLayout(new BorderLayout(10, 10));

	add("Center", GrafikaCanvas);

	add("North", documentation);

	add("East", options);
	
//	add("West", menu);

    }



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

