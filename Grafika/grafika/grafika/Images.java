package grafika;

import grafika.Grafika;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Images {
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
	public static final int OUTPUT = 9;
	public static final int GENOUT = 101;
	
	private Image elementImageAND;
	private Image elementImageOR;
	private Image elementImageNAND;
	private Image elementImageNOR;
	private Image elementImageXOR;
	private Image elementImageXNOR;
	private Image elementImageNOT;
	private Image elementImageGND;
	private Image elementImageVCC;
	private Image elementImageOUTPUT;
	private Image elementImageGENOUT;

	public BufferedImage elementBufferedImageAND;
	public BufferedImage elementBufferedImageOR;
	public BufferedImage elementBufferedImageNAND;
	public BufferedImage elementBufferedImageNOR;
	public BufferedImage elementBufferedImageXOR;
	public BufferedImage elementBufferedImageXNOR;
	public BufferedImage elementBufferedImageNOT;
	public BufferedImage elementBufferedImageGND;
	public BufferedImage elementBufferedImageVCC;
	public BufferedImage elementBufferedImageOUTPUT;
	public BufferedImage elementBufferedImageGENOUT;
	
	public Images(Grafika myparent) {
		parent = myparent;
		
		//  Element images
		/*try {
			this.elementImageAND = this.getImage("grafika/logicalOperators/and.png");
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} */ 
		this.elementImageAND = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/and.png");
		this.elementImageOR = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/or.png");
		this.elementImageNAND = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/nand.png");
		this.elementImageNOR = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/nor.png");
		this.elementImageXOR = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/xor.png");
		this.elementImageXNOR = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/xnor.png");
		this.elementImageNOT = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/not.png");
		this.elementImageGND = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/gnd.png");
		this.elementImageVCC = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/vcc.png");
		this.elementImageOUTPUT = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/output.png");
		this.elementImageGENOUT = parent.getImage(parent.getCodeBase(), "grafika/logicalOperators/genout.png");
		
		
		while(!condition()) {
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		this.elementBufferedImageAND = convert(this.elementImageAND);
		this.elementBufferedImageOR = convert(this.elementImageOR);
		this.elementBufferedImageNAND = convert(this.elementImageNAND);
		this.elementBufferedImageNOR = convert(this.elementImageNOR);
		this.elementBufferedImageXOR = convert(this.elementImageXOR);
		this.elementBufferedImageXNOR = convert(this.elementImageXNOR);
		this.elementBufferedImageNOT = convert(this.elementImageNOT);
		this.elementBufferedImageGND = convert(this.elementImageGND);
		this.elementBufferedImageVCC = convert(this.elementImageVCC);
		this.elementBufferedImageOUTPUT = convert(this.elementImageOUTPUT);
		this.elementBufferedImageGENOUT = convert(this.elementImageGENOUT);
		
		 // if(Grafika.verbose) System.out.println(this.elementBufferedImageAND.getWidth()+" "+this.elementBufferedImageAND.getHeight());
	}
	
	 public BufferedImage convert(Image img)
	 {
	    BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics bg = bi.getGraphics();
	    bg.drawImage(img, 0, 0, null);
	    bg.dispose();
	    return bi;
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
				this.elementImageVCC.getWidth(null) < 0 || 
				this.elementImageOUTPUT.getWidth(null) < 0 ||
				this.elementImageGENOUT.getWidth(null) < 0)
			return false;
		else 
			return true;
	}
	
	@SuppressWarnings("unused")
	private Image getImage(String relative_path) throws Exception {
		Image temp = parent.getImage(parent.getCodeBase(), relative_path);
		if( false) {
			return temp;
		} else {
			throw new Exception("Image not found.", new Throwable(relative_path));
		}
	}
}
