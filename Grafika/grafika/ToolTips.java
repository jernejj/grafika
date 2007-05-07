package grafika;

public class ToolTips
{
	private String imageName;
	private String toolTip;
	
	/** Creates new ToolTip object */
	public ToolTips (String imageName)
	{
		this.imageName = imageName;
	}
	
	/** Sets the ToolTip of object */
	public void setToolTip (String imageName)
	{
		this.imageName = imageName;
	}
	
	/** Returns the ToolTip string*/
	public String getToolTip ()
	{
		if (!(imageName.equals("BOX")))
		{
			this.toolTip = "<html><img src="+this.imageName+"></html>";
			return this.toolTip;
		}
		
		else
			return "TODO";
	}
}