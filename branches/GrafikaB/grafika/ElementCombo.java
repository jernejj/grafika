package grafika;

import javax.swing.JComboBox;

public class ElementCombo {

	Element element;
	int pin;
	JComboBox comboBox;
	
	public ElementCombo (Element e, int p, JComboBox c)
	{
		this.element = e;
		this.pin = p;
		this.comboBox = c;
	}
	
	public Element getElement(){
		return this.element;
	}
	
	public int getPin() {
		return this.pin;
	}
	
	public JComboBox getComboBox(){
		return this.comboBox;
	}
}
