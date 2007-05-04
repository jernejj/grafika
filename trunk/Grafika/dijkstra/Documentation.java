package dijkstra;

import java.awt.BorderLayout;
import java.awt.Panel;

class Documentation extends Panel {

// Documentation on top of the screen

    DocOptions docopt = new DocOptions(this);

    DocText doctext = new DocText();



    Documentation() {

	setLayout(new BorderLayout(10, 10));

	//add("West", docopt);

	add("Center", doctext);

    }

}

