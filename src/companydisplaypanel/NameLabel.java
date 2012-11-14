package companydisplaypanel;

import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author uNstOppAbLe
 */
public class NameLabel extends JLabel {

    public NameLabel (String name)
    {
        super();
        name = "<html><h1>" + name + "</h1></html>";
        setText(name);
        setForeground(Color.red);
    }

}
