package grafika;

import java.awt.Color;
import java.awt.Event;
import java.awt.GridLayout;


import javax.swing.*;

public class Options extends JPanel {

//	set of options at the right of the screen
	JButton b1 = new JButton("clear");
	JButton b2 = new JButton("run");
	JButton b3 = new JButton("step");
	JButton b4 = new JButton("reset");
	JButton b5 = new JButton("example");
	JButton b6 = new JButton("exit");

	Grafika parent;   

	boolean Locked=false;


	Options(Grafika myparent) {
		parent = myparent;
		setBackground(Color.WHITE);
		setLayout(new GridLayout(6, 1, 0, 10));
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		add(b5); 
		add(b6);
	}


	public boolean action(Event evt, Object arg) {

		if (evt.target instanceof JButton) {
			if (((String)arg).equals("step")) {
				if (!Locked) {
					b3.setLabel("next step");
					//parent.GrafikaCanvas.stepalg();
				}
				else parent.documentation.doctext.showline("locked");
			}
			if (((String)arg).equals("next step")) 
				parent.GrafikaCanvas.nextstep();
			if (((String)arg).equals("reset")) { 
				parent.GrafikaCanvas.reset();
				b3.setLabel("step");
				parent.documentation.doctext.showline("all items");
			}
			if (((String)arg).equals("clear")) { 
				parent.GrafikaCanvas.clear();
				b3.setLabel("step");
				parent.documentation.doctext.showline("all items");
			}
			if (((String)arg).equals("run")) {
				if (!Locked)  
					//parent.GrafikaCanvas.runalg();
				//else parent.documentation.doctext.showline("locked");
					;
			}
			if (((String)arg).equals("example")) {
				if (!Locked)   
					parent.GrafikaCanvas.showexample();
				else parent.documentation.doctext.showline("locked");
			} 
			if (((String)arg).equals("exit")) { 
				System.exit(0);
			} 
		}                   
		return true; 
	}



	public void lock() {
		Locked=true;
	}


	public void unlock() {
		Locked=false;
		b3.setLabel("step"); 
	} 
}    