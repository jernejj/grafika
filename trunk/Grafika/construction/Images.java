package construction;

import grafika.Grafika;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import javax.swing.ImageIcon;


public class Images implements Runnable, ImageObserver{
	static Grafika parent;
	
	boolean fDoneLoadingImage = false;
	
	public static final int AND = 0;
	public static final int OR = 1;
	public static final int NAND = 2;
	public static final int NOR = 3;
	public static final int XOR = 4;
	public static final int XNOR = 5;
	public static final int NOT = 6;
	public static final int GND = 7;
	public static final int VCC = 8;
	
	public Image elementImageAND;
	public Image elementImageOR;
	public Image elementImageNAND;
	public Image elementImageNOR;
	public Image elementImageXOR;
	public Image elementImageXNOR;
	public Image elementImageNOT;
	public Image elementImageGND;
	public Image elementImageVCC;

	public BufferedImage elementBufferedImageAND;
	public BufferedImage elementBufferedImageOR;
	public BufferedImage elementBufferedImageNAND;
	public BufferedImage elementBufferedImageNOR;
	public BufferedImage elementBufferedImageXOR;
	public BufferedImage elementBufferedImageXNOR;
	public BufferedImage elementBufferedImageNOT;
	public BufferedImage elementBufferedImageGND;
	public BufferedImage elementBufferedImageVCC;
	
	public ImageIcon imageIconAND;
	//symbol = new File("grafika/logicalOperators/and.png");
	
	public Images(Grafika myparent) {
		parent = myparent;
		
		//  Element images
		this.elementImageAND = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/and.png");
		this.elementImageOR = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/or.png");
		this.elementImageNAND = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/nand.png");
		this.elementImageNOR = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/nor.png");
		this.elementImageXOR = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/xor.png");
		this.elementImageXNOR = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/xnor.png");
		this.elementImageNOT = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/not.png");
		this.elementImageGND = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/gnd.png");
		this.elementImageVCC = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/vcc.png");
		
		System.out.println(this.elementImageAND.getWidth(null)+" "+this.elementImageAND.getHeight(null));
		while(!condition()) {
			try {
				Thread.sleep(250);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(this.elementImageAND.getWidth(null)+" "+this.elementImageAND.getHeight(null));
		System.out.println(parent.getCodeBase());

		this.imageIconAND = createImageIcon("grafika/logicalOperators/and.png");
		
		this.elementBufferedImageAND = convert(this.elementImageAND);
		this.elementBufferedImageOR = convert(this.elementImageOR);
	}
	
	 public BufferedImage convert(Image img)
	 {
	    BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics bg = bi.getGraphics();
	    bg.drawImage(img, 0, 0, null);
	    bg.dispose();
	    return bi;
	 }	
	
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        URL imgURL = parent.getCodeBase();
        if (imgURL != null && path != "") {
            return new ImageIcon(imgURL.toString()+path);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void start() {
    	Thread thread = new Thread(this);
    	thread.start();
    }
    
	public void run() {
	    // Checking the image width will initiate the loading.
		int width = this.elementImageVCC.getWidth(this);

	    // If the width is not equal to -1 then the file has already
	    // been loaded. This can happen if the applet page was loaded
	    // once before and then loaded again into the browser.
	    if (width >= 0) {
	        fDoneLoadingImage = true;
//	        repaint ();
	        return;
	    }

	    // Wait if the image is not loaded yet
	    while (!fDoneLoadingImage) {
	      try {
	        Thread.sleep (500);
	      }
	      catch (InterruptedException ie) {
	    	  ie.printStackTrace();
	      }
	      // Repaint with either the image or the text message
//	      repaint ();
	    }		
		
	}

	public boolean imageUpdate(Image img, int info_flags, int x, int y, int width, int height) {
	     if (info_flags != ALLBITS) {
	         // Indicates image has not finished loading
	         // Returning true will tell the image loading
	         // thread to keep drawing until image fully
	         // drawn loaded.
	         return true;
	     } else {
	         this.fDoneLoadingImage = true;
	         return false;
	     }
	}
	
	private boolean condition() {
		if(this.elementImageAND.getWidth(null) < 0 || 
				this.elementImageOR.getWidth(null) < 0 || 
				this.elementImageNAND.getWidth(null) < 0 ||
				this.elementImageNOR.getWidth(null) < 0 ||
				this.elementImageXOR.getWidth(null) < 0 ||
				this.elementImageXNOR.getWidth(null) < 0 ||
				this.elementImageNOT.getWidth(null) < 0 || 
				this.elementImageGND.getWidth(null) < 0 ||
				this.elementImageVCC.getWidth(null) < 0)
			return false;
		else 
			return true;
	}
}
