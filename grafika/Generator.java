package grafika;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	//private Hashtable<String, TempGen> tempList = new Hashtable<String, TempGen>();
	private Vector<ElementCombo> elCombo = new Vector<ElementCombo>();
	private JPanel generatorPanel;
	private GridBagConstraints genPanelConst;

	private JButton OK = new JButton("OK");
	private JButton CANCEL = new JButton("CANCEL");

	public Generator(Grafika parent) {
		this.parent = parent;
		this.setTitle("Generator Window");
		setGeneratorPanel();
		OK.setActionCommand("OK");
		OK.addActionListener(this);
		CANCEL.setActionCommand("CANCEL");
		CANCEL.addActionListener(this);

	}

	private void setGeneratorPanel() {
		this.generatorPanel = new JPanel();
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

			this.generatorPanel.add(Box.createRigidArea(new Dimension(0, 5)));
			tmpPanel = new JPanel();
			tmpPanel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(tmpElement.getName()),
					BorderFactory.createEmptyBorder(5,5,5,5)));
			tmpPanel.setName(tmpElement.getName());

			// Sem postavljamo kvadratke s pini za posamezen element
			GridBagLayout gblMain = new GridBagLayout();
			tmpPanel.setLayout(gblMain);
			GridBagConstraints cMain = new GridBagConstraints();
			cMain.fill = GridBagConstraints.HORIZONTAL;

			//Add the combo box.
			Element[] tmpElArray = new Element[list.size()];
			tmpElArray = list.toArray(tmpElArray); 

			if(tmpElement.getLineToPin1() == null || tmpElement.getLineToPin1().getPartTowardsOut().getType() == Element.GENOUT) {
				// TODO: dodam primer: 
				// AND3 - Pin1: izberes iz menija
				JComboBox variableChooser1 = new JComboBox(tmpElArray);
				if (tmpElement.getLineToPin1() != null && tmpElement.getLineToPin1().getPartTowardsOut().getType() == Element.GENOUT)
				{
					variableChooser1.setSelectedIndex(tmpElement.getLineToPin1().getPartTowardsOut().getIndex());
				}else {
					variableChooser1.setSelectedIndex(0);
				}
				variableChooser1.addActionListener(this);

				rowPanel = new JPanel();
				rowPanel.setName("Pin1");
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

				elCombo.add(new ElementCombo(tmpElement,Pin.IN1,variableChooser1));
			}
			if((tmpElement.getLineToPin2() == null && (tmpElement.getType() != Element.NOT)) || ((tmpElement.getType() != Element.NOT) && (tmpElement.getLineToPin2().getPartTowardsOut().getType() == Element.GENOUT))) {
				// TODO: dodam primer: 
				// AND3 - Pin2: izberes iz menija
				JComboBox variableChooser2 = new JComboBox(tmpElArray);
				if (tmpElement.getLineToPin2() != null && tmpElement.getLineToPin2().getPartTowardsOut().getType() == Element.GENOUT)
				{
					variableChooser2.setSelectedIndex(tmpElement.getLineToPin2().getPartTowardsOut().getIndex());
				}else {
					variableChooser2.setSelectedIndex(0);
				}
				variableChooser2.addActionListener(this);

				rowPanel = new JPanel();
				rowPanel.setName("Pin2");
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
				elCombo.add(new ElementCombo(tmpElement,Pin.IN2,variableChooser2));
			}
			tmpPanel.add(Box.createGlue());
			// Nastavitve za pin postavimo vodoravno
			genPanelConst.gridwidth = 2;
			genPanelConst.gridx=0;
			genPanelConst.gridy=j++;
			this.add(tmpPanel,genPanelConst);

			this.generatorPanel.add(Box.createGlue());
		}

		this.genPanelConst.insets = new Insets(5,5,5,5);
		this.genPanelConst.gridwidth = 1;
		this.genPanelConst.gridx = 0;
		this.genPanelConst.gridy = j++;
		this.generatorPanel.add(OK, genPanelConst);
		this.genPanelConst.gridx = 1;
		this.generatorPanel.add(CANCEL, genPanelConst);
	}

	public void generate() {
		this.list.clear();
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
		if (e.getSource() instanceof JButton) { 
			if (e.getActionCommand().equals("OK")) {
				ElementCombo tempElementCombo;
				Element thisElement;
				Element newGenout;
				Pin tempPin;
				Line line;
				int index;
				for (Iterator<ElementCombo> i=this.elCombo.iterator();i.hasNext();)
				{
					tempElementCombo = i.next();
					thisElement = tempElementCombo.getElement();
					index = tempElementCombo.getComboBox().getSelectedIndex();

					if (thisElement.getType() != Element.NOT){
						if(tempElementCombo.getPin() == Pin.IN1) {
							if (thisElement.getLineToPin1() == null){
								newGenout = new Element(this.parent, Element.GENOUT, index, this.list.get(index).getTable());
								newGenout.setPosition(new Point(thisElement.getPosition().x-10, thisElement.getPosition().y - 30));
								line = new Line(newGenout, thisElement);
								tempPin = thisElement.getPin1();
								line.setInPoint(new Point(thisElement.getPosition().x + thisElement.getPin1upPosition().x  + tempPin.getPinPosition().x, thisElement.getPosition().y + thisElement.getPin1upPosition().y  + tempPin.getPinPosition().y));
								tempPin = newGenout.getOut();
								line.setOutPoint(new Point(newGenout.getPosition().x + newGenout.getOutUpPosition().x  + tempPin.getPinPosition().x, newGenout.getPosition().y + newGenout.getOutUpPosition().y  + tempPin.getPinPosition().y));
								newGenout.setLineToOut(line);
								thisElement.setLineToPin1(line);
								this.parent.GrafikaCanvas.elementList.add(newGenout);
								this.parent.GrafikaCanvas.lineList.add(line);
							}
							else
							{
								thisElement.getLineToPin1().getPartTowardsOut().setIndex(index);
							}
						}

						if(tempElementCombo.getPin() == Pin.IN2) {
							if (thisElement.getLineToPin2() == null){
								newGenout = new Element(this.parent, Element.GENOUT, index, this.list.get(index).getTable());
								newGenout.setPosition(new Point(thisElement.getPosition().x+20, thisElement.getPosition().y - 30));
								line = new Line(newGenout, thisElement);
								tempPin = thisElement.getPin2();
								line.setInPoint(new Point(thisElement.getPosition().x + thisElement.getPin2upPosition().x  + tempPin.getPinPosition().x, thisElement.getPosition().y + thisElement.getPin2upPosition().y  + tempPin.getPinPosition().y));
								tempPin = newGenout.getOut();
								line.setOutPoint(new Point(newGenout.getPosition().x + newGenout.getOutUpPosition().x  + tempPin.getPinPosition().x, newGenout.getPosition().y + newGenout.getOutUpPosition().y  + tempPin.getPinPosition().y));
								newGenout.setLineToOut(line);
								thisElement.setLineToPin2(line);
								this.parent.GrafikaCanvas.elementList.add(newGenout);
								this.parent.GrafikaCanvas.lineList.add(line);
							}
							else
							{
								thisElement.getLineToPin2().getPartTowardsOut().setIndex(index);
							}
						}
					}
					else{
						if (thisElement.getLineToPin1() == null){
							newGenout = new Element(this.parent, Element.GENOUT, index, this.list.get(index).getTable());
							newGenout.setPosition(new Point(thisElement.getPosition().x+3, thisElement.getPosition().y - 30));
							line = new Line(newGenout, thisElement);
							tempPin = thisElement.getPin1();
							line.setInPoint(new Point(thisElement.getPosition().x + thisElement.getPin1upPosition().x  + tempPin.getPinPosition().x, thisElement.getPosition().y + thisElement.getPin1upPosition().y  + tempPin.getPinPosition().y));
							tempPin = newGenout.getOut();
							line.setOutPoint(new Point(newGenout.getPosition().x + newGenout.getOutUpPosition().x  + tempPin.getPinPosition().x, newGenout.getPosition().y + newGenout.getOutUpPosition().y  + tempPin.getPinPosition().y));
							newGenout.setLineToOut(line);
							thisElement.setLineToPin1(line);
							this.parent.GrafikaCanvas.elementList.add(newGenout);
							this.parent.GrafikaCanvas.lineList.add(line);
						}
						else
						{
							thisElement.getLineToPin1().getPartTowardsOut().setIndex(index);
						}
					}
				}
				parent.GrafikaCanvas.listForGenerator.clear();
				this.elCombo.clear();
				this.parent.GrafikaCanvas.repaint();
				this.setGeneratorPanel();
				this.setVisible(false);
			}
			else if (e.getActionCommand().equals("CANCEL")) {
				//this.tempList.clear();
				parent.GrafikaCanvas.listForGenerator.clear();
				this.elCombo.clear();
				this.setGeneratorPanel();
				this.setVisible(false);
			}
		}
	}


	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
	}
}