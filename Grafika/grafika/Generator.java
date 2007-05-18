package grafika;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Generator extends JFrame implements ActionListener,PropertyChangeListener{

	private static final long serialVersionUID = 0L;
	private int number = -1;
	private short[][] tabela;


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

	private void listInputPins() {
		Element temp;
		for(Iterator<Element> i = parent.GrafikaCanvas.listForGenerator.iterator(); i.hasNext(); ) {
			temp = i.next();
			if(temp.getLineToPin1() == null) {
				// TODO: dodam primer: 
				// AND3 - Pin1: izberes iz menija

			}
			if(temp.getLineToPin2() == null) {
				// TODO: dodam primer: 
				// AND3 - Pin2: izberes iz menija
			}
		}
	}



	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}


	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}


	public void generate() {
		int n = number;
		tabela = new short[(int)Math.pow((double)2,(double)n)][n];
		String bin="";

		int indeks = 0;
		for(int i=0; i < tabela.length; i++) {
			bin=Integer.toBinaryString(i);
			indeks = bin.length();
			for(int j = tabela[0].length - 1; j >= tabela[0].length - bin.length(); j--)
				tabela[i][j] = Short.parseShort(String.valueOf((bin.charAt(--indeks))));
		}
		if(Grafika.verbose) print(tabela);
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
}
