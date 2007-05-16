package construction;

// "Java Tech"
//  Code provided with book for educational purposes only.
//  No warranty or guarantee implied.
//  This code freely available. No copyright claimed.
//  2003
//

// Begun with StartJApplet6

import javax.swing.*;
import java.awt.*;

/** Illustrate use of ImageObserver for image loading. **/
@SuppressWarnings("serial")
public class  ImageObsApplet extends JApplet
                       implements Runnable
{
  // Need a reference to the panel for the
  // thread loop.
  DrawingPanel fDrawingPanel;

  Image fImg;
  int fImageNum = 0;
  String fMessage ="Loading...";
  boolean fDoneLoadingImage = false;

  /** Use a ImageObserver to load an image. **/
  public void init () {
    Container content_pane = getContentPane ();

    // Create an instance of DrawingPanel
    fDrawingPanel = new DrawingPanel (this);

    // Add the DrawingPanel to the contentPane.
    content_pane.add (fDrawingPanel);

    // Get image and monitor its loading.
    fImg = getImage (getCodeBase (),
                     "construction/07-JG-01-pan-A074R1_br2.jpg" );

  } // init

  /**
    * Start the thread to monitor the image loading.
   **/
  public void start () {
    Thread thread = new Thread (this);
    thread.start ();
  }

  /** Use a thread to wait for the image to load
    * before painting it.
   **/
  public void run ()  {

    // Checking the image width will initiate the loading.
    int width = fImg.getWidth (this);

    // If the width is not equal to -1 then the file has already
    // been loaded. This can happen if the applet page was loaded
    // once before and then loaded again into the browser.
    if (width >= 0) {
        fDoneLoadingImage = true;
        repaint ();
        return;
    }

    // Wait if the image is not loaded yet
    while (!fDoneLoadingImage) {
      try {
        Thread.sleep (500);
      }
      catch (InterruptedException ie) {
      }
      // Repaint with either the image or the text message
      repaint ();
    }
  } // run

  /** Override the ImageObserver imageUpdate method and monitor
    * the loading of the image. Set a flag when it is loaded.
   **/
  public boolean imageUpdate (Image img, int info_flags,
                             int x, int y, int w, int h) {

    if (info_flags != ALLBITS) {
        // Indicates image has not finished loading
        // Returning true will tell the image loading
        // thread to keep drawing until image fully
        // drawn loaded.
        return true;
    } else {
        fDoneLoadingImage = true;
        return false;
    }
  } // imageUpdate

}// class ImageObsApplet


/** This JPanel subclass draws an image on the panel if
  * the image is loaded. Otherwise, it draws a text message.
 **/
@SuppressWarnings("serial")
class DrawingPanel extends JPanel {
  ImageObsApplet fParent;

  DrawingPanel (ImageObsApplet parent) {
    fParent = parent;
  }// ctor

  public void paintComponent (Graphics g) {
    super.paintComponent (g);

    // Add your drawing instructions here
    if (fParent.fDoneLoadingImage)
        g.drawImage (fParent.fImg,10,10,this);
    else
        g.drawString (fParent.fMessage, 10,10);
  } // paintComponent

} // class DrawingPanel
