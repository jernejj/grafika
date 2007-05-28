package grafika;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;


public class Options extends JPanel implements ActionListener,PropertyChangeListener,ItemListener {

	private static final long serialVersionUID = 7718176968975619237L;
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
	JCheckBox loopCheck = new JCheckBox("Loop: ",true);
	
	JLabel numLabel = new JLabel("#X: ");  
	JFormattedTextField numTextField = new JFormattedTextField(NumberFormat.getNumberInstance());
	JLabel timeLabel = new JLabel("Time in ms: ");  
	JFormattedTextField timeTextField = new JFormattedTextField(NumberFormat.getNumberInstance());

	//RELEASE: public int number=0;
	public int number=3;
	public int time=2000;
	
	Grafika parent;   
	boolean locked=false;
	
	//###################
	// 		Generator
	Generator gen;
	
	//###################

	public Options(Grafika myparent) {
		int line = 0;
		parent = myparent;
		gen = new Generator(this.parent);
		// V primeru, ko uporabnik klikne na X od generatorja
		gen.addWindowListener(new WindowAdapter()
		{public void windowClosing(WindowEvent winEvt) {
	        gen.close();
	    }});
		setBackground(Color.WHITE);
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
    	GridBagConstraints c = new GridBagConstraints();
    	c.fill = GridBagConstraints.HORIZONTAL;
    	
    	numLabel.setToolTipText("Number of generated variables:");
    	numLabel.setBackground(Color.WHITE);
    	numTextField.setValue(new Integer(number));
    	numTextField.addPropertyChangeListener("value", this);
    	//numTextField.setColumns(5);
    	
    	numLabel.setLabelFor(numTextField);
    	
    	JPanel numPanel = new JPanel(new GridLayout(1,2));
    	numPanel.setBackground(Color.WHITE);
    	numPanel.add(numLabel);
    	numPanel.add(numTextField);
    	
    	timeLabel.setToolTipText("Time (ms) to run the algorithm");
    	timeLabel.setBackground(Color.WHITE);
    	timeTextField.setValue(new Integer(time));
    	timeTextField.addPropertyChangeListener("value", this);
    	//timeTextField.setColumns(5);
    	
    	timeLabel.setLabelFor(timeTextField);
    	
    	JPanel timePanel = new JPanel(new GridLayout(1,2));
    	timePanel.setBackground(Color.WHITE);
    	timePanel.add(timeLabel);
    	timePanel.add(timeTextField);
    	
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        loopCheck.addItemListener(this);
        loopCheck.setSelected(true); parent.GrafikaCanvas.loop = true;
        loopCheck.setBackground(Color.WHITE);
        loopCheck.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

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
    	add(timePanel,c);
    	c.gridx = 0;
    	c.gridy = line++;
    	add(loopCheck,c);
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
    	c.gridy = line++;
    	add(b7,c); 
    	c.gridx = 0;
    	c.gridy = line++;
    	c.weighty=1.0; // Zadnji prostor razsirimo cez preostanek JPanela
    	c.anchor=GridBagConstraints.NORTH; // Zadnji gumb postavimo zgoraj
    	add(b8,c);
    	
    	// Omogocimo, da je ToolTip viden cez vse elemente layouta
    	JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    	ToolTipManager ttm = ToolTipManager.sharedInstance();
    	ttm.setLightWeightPopupEnabled(false);
	}
	
	public void itemStateChanged(ItemEvent e)
	{
		Object source = e.getItemSelectable();
		if (source == loopCheck)
		{
			// loopCheck ni bil izbran
			if (e.getStateChange() == ItemEvent.DESELECTED) {
				parent.GrafikaCanvas.loop = false;
				if(Grafika.verbose) System.out.println ("Loop deselected.");
			}
			// loopCheck je bil izbran
			else if (e.getStateChange() == ItemEvent.SELECTED) {
				parent.GrafikaCanvas.loop = true;
				if(Grafika.verbose) System.out.println ("Loop selected.");
			}
		}
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
					if(number > 0){
						gen.setNumber(number);
						gen.generate();
						gen.pack();
						gen.setVisible(true);
					} else {
						Error.error("Number must be greater than zero!");
					}

					if(Grafika.verbose) System.err.println("Options.actionPerformed(): Generator");
				}
				else parent.documentation.doctext.showline("LOCKED");
			}	
			if (e.getActionCommand().equals("RUN")) {
				if(!locked) {
					if(time > 0){
						parent.GrafikaCanvas.runAlgorithm();
						b2.setActionCommand("STOP");
						b2.setText("stop");
						b2.setToolTipText("stop the algorithm");
					} else {
						Error.error("Time number must be greater than 0!");
					}
				}
				else parent.documentation.doctext.showline("LOCKED");
			}		
			if (e.getActionCommand().equals("STOP")) {
				if(locked) {
					if(time > 0){
						parent.GrafikaCanvas.stop();
						b2.setActionCommand("RUN");
						b2.setText("run");
						b2.setToolTipText("stop the algorithm");
					} else {
						Error.error("Time number must be greater than 0!");
					}
				}
				else parent.documentation.doctext.showline("LOCKED");
			}				
			if (e.getActionCommand().equals("STEP")) { 
				if(!locked) {
					if(parent.GrafikaCanvas.init()) {
						parent.GrafikaCanvas.step();
						b3.setText("next step");
						b3.setActionCommand("NEXT STEP");
						b3.setToolTipText("next step");
					} else {
						Error.error("You have to have at least one output window!");
					}
				}
				else parent.documentation.doctext.showline("LOCKED");
			}
			if (e.getActionCommand().equals("NEXT STEP")) { 
				if(locked) {
					if(parent.GrafikaCanvas.step == 4)
						System.out.println();
					parent.GrafikaCanvas.step();					
				}
				else parent.documentation.doctext.showline("LOCKED");
			}
			if (e.getActionCommand().equals("RESET")) { 
				if(locked) {
					parent.GrafikaCanvas.reset();
					this.b3.setText("step");
					this.b3.setActionCommand("STEP");
					this.b3.setToolTipText("step");
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
					Xml.load(parent);
					parent.GrafikaCanvas.repaint();
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
	
	
	// TODO: Boljse preverjanje pravilnega vnosa.
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        if (source == numTextField) {
        	try {
        		number = Integer.parseInt(((Number)numTextField.getValue()).toString());       	
        		if( number < -1 || number > 20) {
        			number = 2;
        			numTextField.setValue(2);
        			Error.error("Number must be from an interval [1,20]!");
        		}
        	}
        	catch(NumberFormatException nfe) {
        		Error.error("Number is not an integer!");
        	}
        }
        if (source == timeTextField) {
        	try {
        		time = Integer.parseInt(((Number)timeTextField.getValue()).toString());       	
        		
        		if(time < 0) {
        			time = 2000;
        			timeTextField.setValue(2000);
        			Error.error("Time number must be a positive integer!");
        		}
        	}
        	catch(NumberFormatException nfe) {
        		Error.error("Time number is not an integer!");
        	}
        } 
        System.err.println(number);
    }
}    















