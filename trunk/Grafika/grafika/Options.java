package grafika;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class Options extends JPanel implements ActionListener{

	// Set of options at the right of the screen
	JButton b1 = new JButton("clear");
	JButton b2 = new JButton("run");
	JButton b3 = new JButton("step");
	JButton b4 = new JButton("reset");
	JButton b5 = new JButton("example");
	JButton b6 = new JButton("exit");

	Grafika parent;   
	boolean locked=false;

	Options(Grafika myparent) {
		parent = myparent;
		setBackground(Color.WHITE);
		
		setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	c.fill = GridBagConstraints.HORIZONTAL;
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);

        b1.setActionCommand("CLEAR");
        b2.setActionCommand("RUN");
        b3.setActionCommand("STEP");
        b4.setActionCommand("RESET");
        b5.setActionCommand("EXAMPLE");
        b6.setActionCommand("EXIT");
        
        // Global settings
        c.insets = new Insets(10,0,0,0);
        c.gridwidth = 1;
        c.ipady=10;
        
        // Button settings
        c.gridx = 0;
        c.gridy = 0;
    	add(b1,c);
    	c.gridx = 0;
    	c.gridy = 1;
    	add(b2,c);
    	c.gridx = 0;
    	c.gridy = 2;
    	add(b3,c);
    	c.gridx = 0;
    	c.gridy = 3;
    	add(b4,c);
    	c.gridx = 0;
    	c.gridy = 4;
    	add(b5,c); 
    	c.gridx = 0;
    	c.gridy = 5;
    	c.weighty=1.0; // Zadnji prostor razsirimo cez preostanek JPanela
    	c.anchor=GridBagConstraints.NORTH; // Zadnji gumb postavimo zgoraj
    	add(b6,c);
    	
    	// Omogocimo, da je ToolTip viden cez vse elemente layouta
    	JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    	ToolTipManager ttm = ToolTipManager.sharedInstance();
    	ttm.setLightWeightPopupEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			if (e.getActionCommand().equals("CLEAR")) {
				if (!locked) {
					parent.GrafikaCanvas.clear();
				}
				else parent.documentation.doctext.showline("LOCKED");
			}
			if (e.getActionCommand().equals("RUN")) {
				if(!locked) {
					
				}
				else parent.documentation.doctext.showline("LOCKED");
			}				
			if (e.getActionCommand().equals("STEP")) { 
				if(!locked) {
					
				}
				else parent.documentation.doctext.showline("LOCKED");
			}
			if (e.getActionCommand().equals("RESET")) { 
				if(!locked) {
					
				}
				else parent.documentation.doctext.showline("LOCKED");
			}
			if (e.getActionCommand().equals("EXAMPLE")) {
				if(!locked) {
					
				}
				else parent.documentation.doctext.showline("LOCKED");
			}
			if (e.getActionCommand().equals("EXIT")) {
				if(!locked) {
					System.exit(0);
				}
				else parent.documentation.doctext.showline("LOCKED");
			} 
		}                   
    }

	public void lock() {
		locked=true;
	}


	public void unlock() {
		locked=false;
	} 
}    