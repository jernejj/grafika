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
	final String OUTPUT = "Output selected.";
	final String LOCKED = "Selection not possible!";
	final String IZBERI_ELEMENT = "Izberi element!";
	final String SAVE_CANCELLED = "Save cancelled by user!";
	final String SAVED = "Workspace successfully saved!";
	final String LOAD_CANCELLED = "Load cancelled by user!";
	final String LOAD_FAILED = "It is possible to load only .xml file!";
	final String LOAD_INVALID = "There was an attemp of loading invalid .xml file!";
	final String LOADED = "Workspace successfully loaded!";
		
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
		else if (str.equals("OUTPUT")) setText(OUTPUT);
		else if (str.equals("LOCKED")) setText(LOCKED);
		else if (str.equals("IZBERI_ELEMENT")) setText(IZBERI_ELEMENT);
		else if (str.equals("SAVE_CANCELLED")) setText(SAVE_CANCELLED);
		else if (str.equals("SAVED")) setText(SAVED);
		else if (str.equals("LOAD_CANCELLED")) setText(LOAD_CANCELLED);
		else if (str.equals("LOAD_FAILED")) setText(LOAD_FAILED);
		else if (str.equals("LOAD_INVALID")) setText(LOAD_INVALID);
		else if (str.equals("LOADED")) setText(LOADED);
		else setText(EMPTY);

	}

}
