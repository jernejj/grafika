package grafika;

import java.awt.FileDialog;
import java.awt.Point;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

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
				String pin;
				while (itl.hasNext())
				{
					line = itl.next();
					if (line == line.getPartTowardsIn().getLineToPin1())
						pin = "Pin1";
					else //if (line == line.getPartTowardsIn().getLineToPin2())
						pin = "Pin2";

					out.write("\t\t<Line>\r\n");
					out.write("\t\t\t<Out>" +line.getPartTowardsOut().getName()+ "</Out>\r\n");
					out.write("\t\t\t<In>" +line.getPartTowardsIn().getName()+ "</In>\r\n");
					out.write("\t\t\t<InPin>" + pin + "</InPin>\r\n");
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
			if (!file.endsWith(".xml"))
				parent.documentation.doctext.showline("LOAD_FAILED");
			
			else
			{
				file = d.getDirectory()+file;
				try
				{
		            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		            Document doc = docBuilder.parse(new File(file));

		            // Normalize text representation
		            doc.getDocumentElement().normalize();
		            
		            if (!(doc.getDocumentElement().getNodeName().equals("GrafikaCanvas")))
		            	parent.documentation.doctext.showline("LOAD_INVALID");
		            
		            else
		            {
			            NodeList numOfElements = doc.getElementsByTagName("NumberOfElements");
			            org.w3c.dom.Element firstElNode = (org.w3c.dom.Element)numOfElements.item(0);
			            NodeList firstEleNodeValue = firstElNode.getChildNodes();
			            Grafika.element = Integer.parseInt(((Node)firstEleNodeValue.item(0)).getNodeValue().trim());
			            
			            NodeList listOfElements = doc.getElementsByTagName("Element");
			            
			            String name;
	            		int type;
	            		int posX;
	            		int posY;
	            		int pin1;
	            		int pin2;
	            		int out;
			            
			            // Sprehodimo se po vseh elementih
			            for (int i=0; i<listOfElements.getLength(); i++)
			            {	
			            	Node firstElementNode = listOfElements.item(i);
			            	if (firstElementNode.getNodeType() == Node.ELEMENT_NODE)
			            	{
			            		org.w3c.dom.Element firstElement = (org.w3c.dom.Element)firstElementNode;
			            		
			            		// Za Element ID
			            		NodeList elementID = firstElement.getElementsByTagName("ID");
			            		org.w3c.dom.Element elementIDelement = (org.w3c.dom.Element)elementID.item(0);
			            		NodeList textElIDList = elementIDelement.getChildNodes();
			            		name = ((Node)textElIDList.item(0)).getNodeValue().trim();
			            		
			            		// Za Element Type
			            		NodeList elementType = firstElement.getElementsByTagName("Type");
			            		org.w3c.dom.Element elementTypeElement = (org.w3c.dom.Element)elementType.item(0);
			            		NodeList textElTypeList = elementTypeElement.getChildNodes();
			            		type = Integer.parseInt(((Node)textElTypeList.item(0)).getNodeValue().trim());
			            		
			            		// Za Element PosX
			            		NodeList elementPosX = firstElement.getElementsByTagName("PosX");
			            		org.w3c.dom.Element elementPosXElement = (org.w3c.dom.Element)elementPosX.item(0);
			            		NodeList textElPosXList = elementPosXElement.getChildNodes();
			            		posX = Integer.parseInt(((Node)textElPosXList.item(0)).getNodeValue().trim());
			            		
			            		// Za Element PosY
			            		NodeList elementPosY = firstElement.getElementsByTagName("PosY");
			            		org.w3c.dom.Element elementPosYElement = (org.w3c.dom.Element)elementPosY.item(0);
			            		NodeList textElPosYList = elementPosYElement.getChildNodes();
			            		posY = Integer.parseInt(((Node)textElPosYList.item(0)).getNodeValue().trim());
			            		
			            		// Za Element Pin1
			            		NodeList elementPin1 = firstElement.getElementsByTagName("Pin1");
			            		org.w3c.dom.Element elementPin1Element = (org.w3c.dom.Element)elementPin1.item(0);
			            		NodeList textElPin1List = elementPin1Element.getChildNodes();
			            		pin1 = Integer.parseInt(((Node)textElPin1List.item(0)).getNodeValue().trim());
			            		
			            		// Za Element Pin2
			            		NodeList elementPin2 = firstElement.getElementsByTagName("Pin2");
			            		org.w3c.dom.Element elementPin2Element = (org.w3c.dom.Element)elementPin2.item(0);
			            		NodeList textElPin2List = elementPin2Element.getChildNodes();
			            		pin2 = Integer.parseInt(((Node)textElPin2List.item(0)).getNodeValue().trim());
			            		
			            		// Za Element Out
			            		NodeList elementOut = firstElement.getElementsByTagName("Out");
			            		org.w3c.dom.Element elementOutElement = (org.w3c.dom.Element)elementOut.item(0);
			            		NodeList textElOutList = elementOutElement.getChildNodes();
			            		out = Integer.parseInt(((Node)textElOutList.item(0)).getNodeValue().trim());
			            		
			            		Element element = new Element (parent, type, new Point (posX, posY));
			            		element.setPin1value(pin1);
			            		element.setPin2value(pin2);
			            		element.setOut(out);
			            		element.setName(name);
			            		
			            		parent.GrafikaCanvas.elementList.add(element);
			            	}
			            }
			            NodeList listOfLines = doc.getElementsByTagName("Line");
		            	
		            	String lOut;
		            	String lIn;
		            	int lInPosX;
		            	int lInPosY;
		            	int lOutPosX;
		            	int lOutPosY;
		            	String pinIn;
		            	
		            	// Sprehodimo se po vseh povezavah
			            for (int i=0; i<listOfLines.getLength(); i++)
			            {
			            	Node firstLineNode = listOfLines.item(i);
			            	if (firstLineNode.getNodeType() == Node.ELEMENT_NODE)
			            	{
			            		org.w3c.dom.Element firstLine = (org.w3c.dom.Element)firstLineNode;
			            		
			            		// Za Line Element Out
			            		NodeList lineOut = firstLine.getElementsByTagName("Out");
			            		org.w3c.dom.Element lineOutElement = (org.w3c.dom.Element)lineOut.item(0);
			            		NodeList textLineOutList = lineOutElement.getChildNodes();
			            		lOut = ((Node)textLineOutList.item(0)).getNodeValue().trim();
			            		
			            		// Za Line Element In
			            		NodeList lineIn = firstLine.getElementsByTagName("In");
			            		org.w3c.dom.Element lineInElement = (org.w3c.dom.Element)lineIn.item(0);
			            		NodeList textLineInList = lineInElement.getChildNodes();
			            		lIn = ((Node)textLineInList.item(0)).getNodeValue().trim();
			            		
			            		// Za Line In Point
			            		NodeList LineInPoint = doc.getElementsByTagName("InPoint");
			            		Node InPoint = LineInPoint.item(i);
			            		org.w3c.dom.Element firstPoint = (org.w3c.dom.Element)InPoint;
			            		
			            		// Za Line In Point PosX
			            		NodeList firstX = firstPoint.getElementsByTagName("PosX");
			            		org.w3c.dom.Element firstXElement = (org.w3c.dom.Element)firstX.item(0);
			            		NodeList textFirstXList = firstXElement.getChildNodes();
			            		lInPosX = Integer.parseInt((((Node)textFirstXList.item(0)).getNodeValue().trim()));
			            		
			            		// Za Line In Point PosY
			            		NodeList firstY = firstPoint.getElementsByTagName("PosY");
			            		org.w3c.dom.Element firstYElement = (org.w3c.dom.Element)firstY.item(0);
			            		NodeList textFirstYList = firstYElement.getChildNodes();
			            		lInPosY = Integer.parseInt((((Node)textFirstYList.item(0)).getNodeValue().trim()));
			            		
			            		// Za Line Out Point
			            		NodeList LineOutPoint = doc.getElementsByTagName("OutPoint");
			            		Node OutPoint = LineOutPoint.item(i);
			            		org.w3c.dom.Element secondPoint = (org.w3c.dom.Element)OutPoint;
			            		
			            		// Za Line Out Point PosX
			            		NodeList secondX = secondPoint.getElementsByTagName("PosX");
			            		org.w3c.dom.Element secondXElement = (org.w3c.dom.Element)secondX.item(0);
			            		NodeList textSecondXList = secondXElement.getChildNodes();
			            		lOutPosX = Integer.parseInt((((Node)textSecondXList.item(0)).getNodeValue().trim()));
			            		
			            		// Za Line Out Point PosY
			            		NodeList secondY = secondPoint.getElementsByTagName("PosY");
			            		org.w3c.dom.Element secondYElement = (org.w3c.dom.Element)secondY.item(0);
			            		NodeList textSecondYList = secondYElement.getChildNodes();
			            		lOutPosY = Integer.parseInt((((Node)textSecondYList.item(0)).getNodeValue().trim()));
			            		
			            		// Za InPin
			            		NodeList whichPin = firstLine.getElementsByTagName("InPin");
			            		org.w3c.dom.Element whichPinElement = (org.w3c.dom.Element)whichPin.item(0);
			            		NodeList textWhichPinList = whichPinElement.getChildNodes();
			            		pinIn = (((Node)textWhichPinList.item(0)).getNodeValue().trim());

			            		Vector<Element> elementList = parent.GrafikaCanvas.elementList;
			    				Iterator<Element> ite = elementList.iterator();
			    				
			    				Element element;
			    				Element lineElementOut = null;
			    				Element lineElementIn = null;
			    				
			    				while (ite.hasNext())
			    				{
			    					element = ite.next();
			    					if (element.getName().equals(lOut))
			    						lineElementOut = element;
			    					else if (element.getName().equals(lIn))
			    						lineElementIn = element;
			    				}
			    				
			    				Line line = new Line (lineElementOut, lineElementIn);
			    				line.setOutPoint(new Point(lOutPosX, lOutPosY));
			    				line.setInPoint(new Point (lInPosX, lInPosY));
			    				// Elementu pripnemo povezavo na vhod
			    				lineElementOut.setLineToOut(line);
			    				// Elementu pripnemo povezavo na vhod
			    				if (pinIn.equals("Pin1"))
			    					lineElementIn.setLineToPin1(line);
			    				else
			    					lineElementIn.setLineToPin2(line);
			    				
			    				parent.GrafikaCanvas.lineList.add(line);
			            	}
			            }
			            
		            	parent.documentation.doctext.showline("LOADED");
		            }
		        }
				catch (SAXParseException err)
				{
			        System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
			        System.out.println(" " + err.getMessage ());
		        }
				catch (SAXException e)
		        {
			        Exception x = e.getException ();
			        ((x == null) ? e : x).printStackTrace ();
		        }
				catch (Throwable t)
				{
		        	t.printStackTrace ();
				}
			}
		}
		
		else
			parent.documentation.doctext.showline("LOAD_CANCELLED");
	}
}
