package grafika;

import java.awt.Color;
import java.awt.TextArea;

class DocText extends TextArea {
	final String AND = "Operator AND selected.";
	final String OR = "Operator OR selected.";
	final String NAND = "Operator NAND selected.";
	final String NOR = "Operator NOR selected.";
	final String XOR = "Operator XOR selected.";
	final String XNOR = "Operator XNOR selected.";
	final String NOT = "Operator NOT selected.";
	final String GND = "Operator GND selected."; 
	final String VCC = "Operator VCC selected."; 
	final String BOX = "Nothing to do!";
	final String LOCKED = "Selection not possible!";
	final String IZBERI_ELEMENT = "Izberi element!";
		
	final String EMPTY = "";
		
	DocText() {
		super(5, 2);
		setBackground(Color.WHITE);
		setText(EMPTY);
		setEditable(false);  //onemogoci pisanje v Doc area
	}

	public void showline(String str) {
		if (str.equals("AND")) setText(AND);
		else if (str.equals("OR")) setText(OR);
		else if (str.equals("NAND")) setText(NAND);
		else if (str.equals("NOR")) setText(NOR);
		else if (str.equals("XOR")) setText(XOR);
		else if (str.equals("XNOR")) setText(XNOR);
		else if (str.equals("NOT")) setText(NOT);
		else if (str.equals("GND")) setText(GND);
		else if (str.equals("VCC")) setText(VCC);
		else if (str.equals("BOX")) setText(BOX);
		else if (str.equals("LOCKED")) setText(LOCKED);
		else if (str.equals("IZBERI_ELEMENT")) setText(IZBERI_ELEMENT);
		else setText(EMPTY);

	}

}
