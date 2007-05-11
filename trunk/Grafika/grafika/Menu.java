package grafika;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;


@SuppressWarnings("serial")
class Menu extends JPanel implements ActionListener{
	
  	JButton b1 = new JButton(createImageIcon("../grafika/logicalOperators/andV.png"))
    	// Pozicija ToolTipa
    	{ public Point getToolTipLocation(MouseEvent event) { return new Point(getWidth()+10, 0); } };
    JButton b2 = new JButton(createImageIcon("../grafika/logicalOperators/orV.png"))
    	{ public Point getToolTipLocation(MouseEvent event) { return new Point(getWidth()+10, 0); } };
    JButton b3 = new JButton(createImageIcon("../grafika/logicalOperators/nandV.png"))
    	{ public Point getToolTipLocation(MouseEvent event) { return new Point(getWidth()+10, 0); } };
    JButton b4 = new JButton(createImageIcon("../grafika/logicalOperators/norV.png"))
    	{ public Point getToolTipLocation(MouseEvent event) { return new Point(getWidth()+10, 0); } };
    JButton b5 = new JButton(createImageIcon("../grafika/logicalOperators/xorV.png"))
    	{ public Point getToolTipLocation(MouseEvent event) { return new Point(getWidth()+10, 0); } };
    JButton b6 = new JButton(createImageIcon("../grafika/logicalOperators/xnorV.png"))
    	{ public Point getToolTipLocation(MouseEvent event) { return new Point(getWidth()+10, 0); } };
    JButton b7 = new JButton(createImageIcon("../grafika/logicalOperators/notV.png"))
    	{ public Point getToolTipLocation(MouseEvent event) { return new Point(getWidth()+10, 0); } };
    JButton b8 = new JButton("BOX")
    	{ public Point getToolTipLocation(MouseEvent event) { return new Point(getWidth()+10, 0); } };

    Grafika parent;   

    boolean locked=false;


    Menu(Grafika myparent) {
    	parent = myparent;
    	setBackground(Color.WHITE);
    	setLayout(new GridLayout(8, 1, 0, 10));
    	
        // Takoj prikazi ToolTip
        ToolTipManager.sharedInstance().setInitialDelay(0);
        
        // ToolTip prikazuj dokler je miska nad gumbom
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
        
    	ToolTips toolTip = new ToolTips("file:grafika/logicalOperators/andTruthTable.png");
        b1.setToolTipText(toolTip.getToolTip());
        toolTip.setToolTip("file:grafika/logicalOperators/orTruthTable.png");
        b2.setToolTipText(toolTip.getToolTip());
        toolTip.setToolTip("file:grafika/logicalOperators/nandTruthTable.png");
        b3.setToolTipText(toolTip.getToolTip());
        toolTip.setToolTip("file:grafika/logicalOperators/norTruthTable.png");
        b4.setToolTipText(toolTip.getToolTip());
        toolTip.setToolTip("file:grafika/logicalOperators/xorTruthTable.png");
        b5.setToolTipText(toolTip.getToolTip());
        toolTip.setToolTip("file:grafika/logicalOperators/xnorTruthTable.png");
        b6.setToolTipText(toolTip.getToolTip());
        toolTip.setToolTip("file:grafika/logicalOperators/notTruthTable.png");
        b7.setToolTipText(toolTip.getToolTip());
        toolTip.setToolTip("BOX");
        b8.setToolTipText(toolTip.getToolTip());
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b1.setActionCommand("AND");
        b2.setActionCommand("OR");
        b3.setActionCommand("NAND");
        b4.setActionCommand("NOR");
        b5.setActionCommand("XOR");
        b6.setActionCommand("XNOR");
        b7.setActionCommand("NOT");
        b8.setActionCommand("BOX");
    	add(b1);
    	add(b2);
    	add(b3);
    	add(b4);
    	add(b5); 
    	add(b6);
    	add(b7);
    	add(b8);
    	
    	// Omogocimo, da je ToolTip viden cez vse elemente layouta
    	JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    	ToolTipManager ttm = ToolTipManager.sharedInstance();
    	ttm.setLightWeightPopupEnabled(false);
    }


    public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			if (e.getActionCommand().equals("AND")) {
				if (!locked) {
					/** TODO */ 
					parent.GrafikaCanvas.elementType = Element.AND; 
					parent.documentation.doctext.showline("AND");
				}
				else parent.documentation.doctext.showline("LOCKED");
			}
			if (e.getActionCommand().equals("OR")) {
				if(!locked) {
					parent.GrafikaCanvas.elementType = Element.OR; 
					parent.documentation.doctext.showline("OR");
				}
				else parent.documentation.doctext.showline("LOCKED");
			}				
			if (e.getActionCommand().equals("NAND")) { 
				if(!locked) {
					parent.GrafikaCanvas.elementType = Element.NAND; 
					parent.documentation.doctext.showline("NAND");
				}
				else parent.documentation.doctext.showline("LOCKED");
			}
			if (e.getActionCommand().equals("NOR")) { 
				if(!locked) {
					parent.GrafikaCanvas.elementType = Element.NOR; 
					parent.documentation.doctext.showline("NOR");
				}
				else parent.documentation.doctext.showline("LOCKED");
			}
			if (e.getActionCommand().equals("XOR")) {
				if(!locked) {
					parent.GrafikaCanvas.elementType = Element.XOR; 
					parent.documentation.doctext.showline("XOR");
				}
				else parent.documentation.doctext.showline("LOCKED");
			}
			if (e.getActionCommand().equals("XNOR")) {
				if(!locked) {
					parent.GrafikaCanvas.elementType = Element.XNOR; 
					parent.documentation.doctext.showline("XNOR");
				}
				else parent.documentation.doctext.showline("LOCKED");
			} 
			if (e.getActionCommand().equals("NOT")) {
				if(!locked) {
					parent.GrafikaCanvas.elementType = Element.NOT; 
					parent.documentation.doctext.showline("NOT");
				}
				else parent.documentation.doctext.showline("LOCKED");
			} 
			if (e.getActionCommand().equals("BOX")) {
				if(!locked) {
					// parent.GrafikaCanvas.elementType = Element.BOX; 
					parent.documentation.doctext.showline("BOX");
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
	
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Grafika.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}