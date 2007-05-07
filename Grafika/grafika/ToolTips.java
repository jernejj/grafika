package grafika;

public class ToolTips
{
	private String operator;
	private String toolTip;
	
	/** Creates new ToolTip object */
	public ToolTips (String operator)
	{
		this.operator = operator;
	}
	
	/** Sets the ToolTip of object */
	public void setToolTip (String operator)
	{
		this.operator = operator;
	}
	
	/** Returns the ToolTip string*/
	public String getToolTip ()
	{
		this.toolTip = 
		"<html>" +
		"<head>" +
		"<style type='text/css'>" +
		"<!--" +
		".bold12 {font-size: 12pt; font-weight: bold;}" +
		".normal11 {font-size: 11pt}" +
		".normal10 {font-size: 10pt}" +
		"-->" +
		"</style>" +
		"</head>" +

		"<body>";
		
		// V primeru, ko ni NOT operator
		if (!(this.operator.equals("NOT")))
		{
			this.toolTip +=
			"<table style='border:solid 1px #000000' width='100' cellspacing='0' cellpadding='0'>" +
			"  <tr>" +
			"    <td colspan='3' align='center'><span class='bold12'>"+ this.operator +"</span></td>" +
			"  </tr>" +
			"  <tr>" +
			"    <td colspan='3' align='center'><span class='normal10'>&nbsp;</span></td>" +
			"  </tr>" +
			"  <tr>" +
			"    <td width='15' align='center'><span class='normal11'>a</span></td>" +
			"    <td width='15' align='center'><span class='normal11'>b</span></td>" +
			"    <td width='70' align='center'><span class='normal11'>a "+ this.operator +" b</span></td>" +
			"  </tr>" +
			"  <tr>" +
			"    <td align='center'><span class='normal10'>0</span></td>" +
			"    <td align='center'><span class='normal10'>0</span></td>" +
			"    <td align='center'><span class='normal10'>";
			
			// Prva vrstica
			if (this.operator.equals("AND"))
				this.toolTip += "0";
			else if (this.operator.equals("OR"))
				this.toolTip += "0";
			else if (this.operator.equals("NAND"))
				this.toolTip += "1";
			else if (this.operator.equals("NOR"))
				this.toolTip += "1";
			else if (this.operator.equals("XOR"))
				this.toolTip += "0";
			else if (this.operator.equals("XNOR"))
				this.toolTip += "1";
			
			this.toolTip += "</span></td>" +
			"  </tr>" +
			"  <tr>" +
			"    <td align='center'><span class='normal10'>0</span></td>" +
			"    <td align='center'><span class='normal10'>1</span></td>" +
			"    <td align='center'><span class='normal10'>";
			
			// Druga vrstica
			if (this.operator.equals("AND"))
				this.toolTip += "0";
			else if (this.operator.equals("OR"))
				this.toolTip += "1";
			else if (this.operator.equals("NAND"))
				this.toolTip += "1";
			else if (this.operator.equals("NOR"))
				this.toolTip += "0";
			else if (this.operator.equals("XOR"))
				this.toolTip += "1";
			else if (this.operator.equals("XNOR"))
				this.toolTip += "0";
			
			this.toolTip += "</span></td>" +
			"  </tr>" +
			"  <tr>" +
			"    <td align='center'><span class='normal10'>1</span></td>" +
			"    <td align='center'><span class='normal10'>0</span></td>" +
			"    <td align='center'><span class='normal10'>";
			
			// Tretja vrstica
			if (this.operator.equals("AND"))
				this.toolTip += "0";
			else if (this.operator.equals("OR"))
				this.toolTip += "1";
			else if (this.operator.equals("NAND"))
				this.toolTip += "1";
			else if (this.operator.equals("NOR"))
				this.toolTip += "0";
			else if (this.operator.equals("XOR"))
				this.toolTip += "1";
			else if (this.operator.equals("XNOR"))
				this.toolTip += "0";
			
			this.toolTip += "</span></td>" +
			"  </tr>" +
			"  <tr>" +
			"    <td align='center'><span class='normal10'>1</span></td>" +
			"    <td align='center'><span class='normal10'>1</span></td>" +
			"    <td align='center'><span class='normal10'>";
			
			// Cetrta vrstica
			if (this.operator.equals("AND"))
				this.toolTip += "1";
			else if (this.operator.equals("OR"))
				this.toolTip += "1";
			else if (this.operator.equals("NAND"))
				this.toolTip += "0";
			else if (this.operator.equals("NOR"))
				this.toolTip += "0";
			else if (this.operator.equals("XOR"))
				this.toolTip += "0";
			else if (this.operator.equals("XNOR"))
				this.toolTip += "1";
			
			this.toolTip += "</span></td>" +
			"  </tr>" +
			"</table>";
		}
		
		// NOT operator
		else
		{
			this.toolTip +=
			"<table style='border:solid ' width='60' cellspacing='0' cellpadding='0'>" +
			"  <tr>" +
			"    <td colspan='2' align='center'><span class='bold12'>NOT</span></td>" +
			"  </tr>" +
			"  <tr>" +
			"    <td colspan='2' align='center'><span class='normal10'>&nbsp;</span></td>" +
			"  </tr>" +
			"  <tr>" +
			"    <td width='15' align='center'><span class='normal11'>a</span></td>" +
			"    <td width='45' align='center'><span class='normal11'>NOT a</span></td>" +
			"  </tr>" +
			"  <tr>" +
			"    <td align='center'><span class='normal10'>0</span></td>" +
			"    <td align='center'><span class='normal10'>1</span></td>" +
			"  </tr>" +
			"  <tr>" +
			"    <td align='center'><span class='normal10'>1</span></td>" +
			"    <td align='center'><span class='normal10'>0</span></td>" +
			"  </tr>" +
			"</table>";
		}
		
		this.toolTip +=
			"</body>" +
			"</html>";

		return this.toolTip;
	}
}
