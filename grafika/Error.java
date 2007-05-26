package grafika;

import javax.swing.JOptionPane;

public class Error {
	
	public static void error(String error_msg) {
//		JOptionPane.showMessageDialog(this.jdialog,error_msg);
		JOptionPane.showMessageDialog(null,error_msg,"Error window",JOptionPane.PLAIN_MESSAGE);
	}	
}
