package grafika;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Generator extends JFrame implements ActionListener,PropertyChangeListener{
 
	private static final long serialVersionUID = 0L;

	
	//Grafika parent;
	Grafika parent;
	
	JPanel panel = new JPanel();
	
	public Generator(Grafika parent) {
		this.parent = parent;
		BorderLayout bl = new BorderLayout(0, 0);
		this.setLayout(bl);
		this.add(panel);
		//this.set
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
}
