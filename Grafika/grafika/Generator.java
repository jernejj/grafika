package grafika;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Generator extends JFrame implements ActionListener,PropertyChangeListener{

	private static final long serialVersionUID = 0L;
	Grafika parent;
	private int number = -1;
	private short[][] tabela;
	
	private Vector<Element> list = new Vector<Element>();
	JPanel generatorPanel = new JPanel();
	GridBagConstraints genPanelConst;
	
	public Generator(Grafika parent) {
		this.parent = parent;
		this.setTitle("Generator Window");
		this.generatorPanel.setLayout(new BoxLayout(generatorPanel, BoxLayout.PAGE_AXIS));
		this.generatorPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.generatorPanel.setOpaque(true);
		this.setContentPane(this.generatorPanel);
		
		// Da nastavitve za pin postavimo vodoravno
		GridBagLayout genPanel = new GridBagLayout();
		this.setLayout(genPanel);
		genPanelConst = new GridBagConstraints();
		genPanelConst.fill = GridBagConstraints.HORIZONTAL;
	}

	private void listInputPins() {
		Element tmpElement;
		JPanel tmpPanel;
		JPanel rowPanel;
		int j=0;
		for(Iterator<Element> i = parent.GrafikaCanvas.listForGenerator.iterator(); i.hasNext(); ) {
			tmpElement = i.next();
			if (!((tmpElement.getLineToPin1() != null) && tmpElement.getType() == 6))
			{	
				this.generatorPanel.add(Box.createRigidArea(new Dimension(0, 5)));
			
			
			tmpPanel = new JPanel();
			tmpPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(tmpElement.getName()),
                    BorderFactory.createEmptyBorder(5,5,5,5)));
			
			// Sem postavljamo kvadratke s pini za posamezen element
			GridBagLayout gblMain = new GridBagLayout();
			tmpPanel.setLayout(gblMain);
			GridBagConstraints cMain = new GridBagConstraints();
			cMain.fill = GridBagConstraints.HORIZONTAL;
			
			//Add the combo box.
			Element[] tmpElArray = new Element[list.size()];
			tmpElArray = list.toArray(tmpElArray); 
	        // Za Pin1
			JComboBox variableChooser1 = new JComboBox(tmpElArray);
	        variableChooser1.setSelectedIndex(0);
	        variableChooser1.addActionListener(this);
	        // Za Pin2
	        JComboBox variableChooser2 = new JComboBox(tmpElArray);
	        variableChooser2.setSelectedIndex(0);
	        variableChooser2.addActionListener(this);
	        
	        
	        
			if(tmpElement.getLineToPin1() == null) {
				// TODO: dodam primer: 
				// AND3 - Pin1: izberes iz menija
				rowPanel = new JPanel();
				GridBagLayout gbl = new GridBagLayout();
				rowPanel.setLayout(gbl);
				GridBagConstraints c = new GridBagConstraints();
		    	c.fill = GridBagConstraints.HORIZONTAL;
		    	c.insets = new Insets(10,10,0,0);
		    	c.gridx = 0;
		        c.gridy = 0;
		    	rowPanel.add(new JLabel("Pin1: "),c);
		    	c.gridx = 1;
		        c.gridy = 0;
		        rowPanel.add(variableChooser1,c);
		    	c.gridx = 2;
		        c.gridy = 0;
		        rowPanel.add(Box.createGlue(),c);
		        cMain.gridx = 0;
		        cMain.gridy = 0;
		    	tmpPanel.add(rowPanel,cMain);
		    	

			}
			if((tmpElement.getLineToPin2() == null && (tmpElement.getType() != 6))) {
				// TODO: dodam primer: 
				// AND3 - Pin2: izberes iz menija
				rowPanel = new JPanel();
				GridBagLayout gbl = new GridBagLayout();
				rowPanel.setLayout(gbl);
				GridBagConstraints c = new GridBagConstraints();
		    	c.fill = GridBagConstraints.HORIZONTAL;
		    	c.insets = new Insets(10,10,0,0);
		    	c.gridx = 0;
		        c.gridy = 0;
		    	rowPanel.add(new JLabel("Pin2: "),c);
		    	c.gridx = 1;
		        c.gridy = 0;
		        rowPanel.add(variableChooser2,c);
		    	c.gridx = 2;
		        c.gridy = 0;
		        rowPanel.add(Box.createGlue(),c);
		        cMain.gridx = 0;
		        cMain.gridy = 1;
		    	tmpPanel.add(rowPanel,cMain);
			}
			tmpPanel.add(Box.createGlue());
			// Nastavitve za pin postavimo vodoravno
			genPanelConst.gridx=j++;
			genPanelConst.gridy=0;
			this.add(tmpPanel,genPanelConst);
		}
		this.generatorPanel.add(Box.createGlue());
		}
	}






	public void generate() {
		int n = number;
		tabela = new short[n][(int)Math.pow((double)2,(double)n)];
		String bin="";

		int indeks = 0;
		for(int i=0; i < tabela[0].length; i++) {
			bin=Integer.toBinaryString(i);
			indeks = bin.length();
			for(int j = tabela.length - 1; j >= tabela.length - bin.length(); j--)
				tabela[j][i] = Short.parseShort(String.valueOf((bin.charAt(--indeks))));
		}
		
		for(int i = 0; i < number; i++){
			this.list.add(new Element(this.parent, Element.GENOUT, i, tabela[number -1 -i]));
		}
			
		if(Grafika.verbose) print(tabela);
		
		this.listInputPins();
	}

	private static void print(short[][] t) {
		for(int i = 0; i < t.length; i++) {
			for(int j = 0; j < t[0].length; j++ ) {
				System.out.print(t[i][j]+ " ");
			}
			System.out.println("");
		}
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}


	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}
}
