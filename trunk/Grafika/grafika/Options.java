package grafika;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;


@SuppressWarnings("serial")
public class Options extends JPanel implements ActionListener,PropertyChangeListener {

	// Set of options at the right of the screen
	JButton b1 = new JButton("clear");
	JButton b2 = new JButton("run");
	JButton b3 = new JButton("step");
	JButton b4 = new JButton("reset");
	JButton b5 = new JButton("save");
	JButton b6 = new JButton("load");
	JButton b7 = new JButton("example");
	JButton b8 = new JButton("exit");
	JButton b9 = new JButton("generator");
	
	JLabel numLabel = new JLabel("#X: ");  
	JFormattedTextField numTextField = new JFormattedTextField(NumberFormat.getNumberInstance());

	public int number=0;
	
	Grafika parent;   
	boolean locked=false;
	
	//###################
	// 		Generator
	Generator gen;
	
	//###################

	public Options(Grafika myparent) {
		int line = 0;
		parent = myparent;
		setBackground(Color.WHITE);
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
    	GridBagConstraints c = new GridBagConstraints();
    	c.fill = GridBagConstraints.HORIZONTAL;
    	
    	numLabel.setToolTipText("Number of generated variables:");
    	numLabel.setBackground(Color.WHITE);
    	numTextField.setValue(new Integer(number));
    	numTextField.addPropertyChangeListener("value", this);
    	numTextField.setColumns(5);
    	
    	numLabel.setLabelFor(numTextField);
    	
    	JPanel numPanel = new JPanel(new GridLayout(1,2));
    	numPanel.setBackground(Color.WHITE);
    	numPanel.add(numLabel);
    	numPanel.add(numTextField);
    	
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);

        b1.setActionCommand("CLEAR");
        b2.setActionCommand("RUN");
        b3.setActionCommand("STEP");
        b4.setActionCommand("RESET");
        b5.setActionCommand("SAVE");
        b6.setActionCommand("LOAD");
        b7.setActionCommand("EXAMPLE");
        b8.setActionCommand("EXIT");
        b9.setActionCommand("GENERATOR");
        
        // Global settings
        c.insets = new Insets(10,10,0,0);
//        c.gridwidth = 1;
//        c.ipady=10;
        
        c.gridx = 0;
        c.gridy = line++;
    	add(numPanel,c);	
        // Button settings
        c.gridx = 0;
        c.gridy = line++;
    	add(b9,c);
        c.gridx = 0;
        c.gridy = line++;
    	add(b1,c);
    	c.gridx = 0;
    	c.gridy = line++;
    	add(b2,c);
    	c.gridx = 0;
    	c.gridy = line++;
    	add(b3,c);
    	c.gridx = 0;
    	c.gridy = line++;
    	add(b4,c);
    	c.gridx = 0;
    	c.gridy = line++;
    	add(b5,c); 
    	c.gridx = 0;
    	c.gridy = line++;
    	add(b6,c); 
    	c.gridx = 0;
    	c.gridy = 6;
    	add(b7,c); 
    	c.gridx = 0;
    	c.gridy = 7;
    	c.weighty=1.0; // Zadnji prostor razsirimo cez preostanek JPanela
    	c.anchor=GridBagConstraints.NORTH; // Zadnji gumb postavimo zgoraj
    	add(b8,c);
    	
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
			if (e.getActionCommand().equals("GENERATOR")) {
				if(!locked) {
					
					parent.GrafikaCanvas.generateElementList();
					
		
					gen = new Generator(this.parent);
					if(number > 0){
						gen.setNumber(number);
						gen.generate();
					}
					
					
					gen.setSize(800,600); // TODO: getSite() v Generatorju je potrebno dolociti veliksot, glede na stevilo primerov.. mogoce scroll bar
					gen.setVisible(true);
					if(Grafika.verbose) System.err.println("Options.actionPerformed(): Generator");
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
			if (e.getActionCommand().equals("SAVE")) { 
				if(!locked) {
					Xml.save(parent);
				}
				else parent.documentation.doctext.showline("LOCKED");
			}
			if (e.getActionCommand().equals("LOAD")) { 
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
	
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        if (source == numTextField) {
            number = ((Number)numTextField.getValue()).intValue();
        } 
        System.err.println(number);
    }
}    