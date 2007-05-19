package grafika;

import java.awt.FileDialog;
import java.io.*;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;

public class Xml
{	
	public static void save(Grafika parent)
	{
		JFrame frame = new JFrame();
		
		FileDialog d = new FileDialog(frame, "Save As", FileDialog.SAVE);
		d.setVisible(true);
		String file = d.getFile();
		
		if(file != null)
		{
			int dot = file.indexOf(".");
			
			if (dot == -1)
				file = d.getDirectory()+file+".xml";
			else
			{
				file = (String)file.subSequence(0, dot);
				file = d.getDirectory()+file+".xml";
			}
			
			try
			{
				OutputStream fout = new FileOutputStream(file);
		        OutputStream bout = new BufferedOutputStream(fout);
		        OutputStreamWriter out = new OutputStreamWriter(bout, "8859_1");
		        
		        out.write("<?xml version=\"1.0\" ");
		        out.write("encoding=\"ISO-8859-1\"?>\r\n");
		        out.write("<GrafikaCanvas>\r\n");
		        out.write("\t<NumberOfElements>" +Grafika.element+ "</NumberOfElements>\r\n");
		        
				Vector<Element> elementList = parent.GrafikaCanvas.elementList;
				Iterator<Element> ite = elementList.iterator();
				out.write("\t<Elements>\r\n");
				
				Element element;
				while (ite.hasNext())
				{
					element = ite.next();
					out.write("\t\t<Element>\r\n");
					out.write("\t\t\t<ID>" +element.getName()+ "</ID>\r\n");
					out.write("\t\t\t<Type>" +element.getType()+ "</Type>\r\n");
					out.write("\t\t\t<PosX>" +element.getPosition().x + "</PosX>\r\n");
					out.write("\t\t\t<PosY>" +element.getPosition().y + "</PosY>\r\n");
					out.write("\t\t\t<Pin1>" +element.getPin1value() + "</Pin1>\r\n");
					out.write("\t\t\t<Pin2>" +element.getPin2value() + "</Pin2>\r\n");
					out.write("\t\t\t<Out>" +element.getOutValue() + "</Out>\r\n");
					out.write("\t\t</Element>\r\n");
				}
				out.write("\t</Elements>\r\n");
				out.write("\t<Lines>\r\n");
				
				Vector<Line> lineList = parent.GrafikaCanvas.lineList;
				Iterator<Line> itl = lineList.iterator();
				Line line;
				while (itl.hasNext())
				{
					line = itl.next();
					out.write("\t\t<Line>\r\n");
					out.write("\t\t\t<Out>" +line.getPartTowardsOut().getName()+ "</Out>\r\n");
					out.write("\t\t\t<In>" +line.getPartTowardsIn().getName()+ "</In>\r\n");
					out.write("\t\t\t<InPoint>\r\n");
					out.write("\t\t\t\t<PosX>" +line.getInPoint().x+ "</PosX>\r\n");
					out.write("\t\t\t\t<PosY>" +line.getInPoint().y+ "</PosY>\r\n");
					out.write("\t\t\t</InPoint>\r\n");
					out.write("\t\t\t<OutPoint>\r\n");
					out.write("\t\t\t\t<PosX>" +line.getOutPoint().x+ "</PosX>\r\n");
					out.write("\t\t\t\t<PosY>" +line.getOutPoint().y+ "</PosY>\r\n");
					out.write("\t\t\t</OutPoint>\r\n");
					out.write("\t\t</Line>\r\n");
				}
				
				out.write("\t</Lines>\r\n");
		        out.write("</GrafikaCanvas>\r\n");
		        out.flush();  // Don't forget to flush!
		        out.close();
		        parent.documentation.doctext.showline("SAVED");
			}
			catch (IOException e)
			{
		        System.out.println(e.getMessage());        
		    }
		}
		
		else
			parent.documentation.doctext.showline("SAVE_CANCELLED");
	}
	
	public static void load (Grafika parent)
	{
JFrame frame = new JFrame();
		
		FileDialog d = new FileDialog(frame, "Load", FileDialog.LOAD);
		d.setVisible(true);
		String file = d.getFile();
		
		if(file != null)
		{
			
		}
		
		else
			parent.documentation.doctext.showline("LOAD_CANCELLED");
	}
}
