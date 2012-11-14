package persondisplaypanel;

import javax.swing.JLabel;

/**
 *
 * @author uNstOppAbLe
 */
public class BornLabel extends JLabel {
    public BornLabel (String day, String year)
    {
        super();
        if (day.equals("N/I") && year.equals("N/I"))
        {
            setText("<html><font size = 3><b>Born: </b>No Information</font></html>");
        }
        else if (day.equals("N/I"))
        {
            setText("<html><font size = 3><b>Born: </b>"+year+"</font></html>");
        }
        else if (year.equals("N/I"))
        {
            setText("<html><font size = 3><b>Born: </b>"+day+"</font></html>");
        }
        else
        {
            setText("<html><font size = 3><b>Born: </b>"+day+"-"+year+"</font></html>");
        }

    }
}
