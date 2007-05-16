package construction;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

@SuppressWarnings("serial")
public class ImageObserverExample extends Panel {
	Image image;
	String msg = "Loading Image";
	int n;

	public ImageObserverExample() {
		try {
			URL url = new URL(
			"http://www.thealpacastore.com/alpacacam/latest640.jpg");
			Toolkit toolkit = Toolkit.getDefaultToolkit();
//			use createImage instead of getImage - see api docs for why
			image = toolkit.createImage(url);
		} catch (MalformedURLException murle) {
			msg = "MalformedURLException";
		}

		setPreferredSize(new Dimension(400,300));
	}

	public void paint(Graphics g) {
//		show the number of calls to paint()
		System.out.println(++n);
//		if there is an image and drawing has not finished
//		note: if the image is null drawImage will return true
		if (image == null ||
				!g.drawImage(image,0,0,getWidth(),getHeight(),this )) {
//			draw the message
			g.drawString(msg,20,getHeight()/2);
			System.out.println(msg);
		}
	}

	public static void main(String[] args) {
		Frame frame = new Frame("ImageObserverExample");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		ImageObserverExample examp = new ImageObserverExample();
		frame.add(examp,BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
}