package persondisplaypanel;

import javax.swing.JLabel;

/**
 *
 * @author uNstOppAbLe
 */
public class BirthplaceLabel extends JLabel {
    public BirthplaceLabel (String birthplace)
    {
        super();
        if (birthplace.equals("N/I")) birthplace = "No Information";
        setText("<html><font size=3><b>In: </b>"+birthplace+"</html>");
    }
}
