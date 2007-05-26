package grafika;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;

class Documentation extends JPanel {
	//	Documentation on top of the screen
	DocOptions docopt = new DocOptions(this);
	DocText doctext = new DocText();

	Documentation() {
		setLayout(new BorderLayout(10, 10));
		setBackground(Color.WHITE);
		add("Center", doctext);
	}
}

