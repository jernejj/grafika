package dijkstra;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.Panel;

class Menu extends Panel {

    Button b1 = new Button("and");
    Button b2 = new Button("or");
    Button b3 = new Button("nand");
    Button b4 = new Button("nor");
    Button b5 = new Button("xor");
    Button b6 = new Button("xnor");
    Button b7 = new Button("not");
    Button b8 = new Button("BOX");

    Grafika parent;   

    boolean Locked=false;


    Menu(Grafika myparent) {
    	parent = myparent;
    	setLayout(new GridLayout(8, 1, 0, 10));
    	add(b1);
    	add(b2);
    	add(b3);
    	add(b4);
    	add(b5); 
    	add(b6);
    	add(b7);
    	add(b8);
    }

    public boolean action(Event evt, Object arg) {

		if (evt.target instanceof Button) {
			if (((String)arg).equals("and")) {
				if (!Locked) {
					/** TODO */ 
//					b3.setLabel("next step");
//					parent.GrafikaCanvas.stepalg();
				}
				
			}
			if (((String)arg).equals("or")) {
				if(!Locked) {
					/** TODO */
				}
				else parent.documentation.doctext.showline("locked");
			}				
			if (((String)arg).equals("nand")) { 
				if(!Locked) {
					/** TODO */
				}
				else parent.documentation.doctext.showline("locked");
			}
			if (((String)arg).equals("nor")) { 
				if(!Locked) {
					/** TODO */
				}
				else parent.documentation.doctext.showline("locked");
			}
			if (((String)arg).equals("xor")) {
				if(!Locked) {
					/** TODO */
				}
				else parent.documentation.doctext.showline("locked");
			}
			if (((String)arg).equals("xnor")) {
				if(!Locked) {
					/** TODO */
				}
				else parent.documentation.doctext.showline("locked");
			} 
			if (((String)arg).equals("not")) {
				if(!Locked) {
					/** TODO */
				}
				else parent.documentation.doctext.showline("locked");
			} 
			if (((String)arg).equals("BOX")) {
				if(!Locked) {
					/** TODO */
				}
				else parent.documentation.doctext.showline("locked");
			}
		}                   
		return true; 
	}

	public void lock() {
		Locked=true;
	}


	public void unlock() {
		Locked=false; 
	} 
}