package persondisplaypanel;

import javax.swing.JLabel;

/**
 *
 * @author uNstOppAbLe
 */
public class RoleLabel extends JLabel {
    public RoleLabel (String isDirector, String isStar, String sex)
    {
        super();      
        if (isDirector.equals("1") && isStar.equals("1"))
        {
            if (sex.equals("Male")) setText("<html>Actor | Director</html>");
            else setText("<html>Actress | Director</html>");
        }
        else if (isDirector.equals("1"))
        {
            setText("<html>Director</html>");
        }
        else
        {
            if (sex.equals("Male")) setText("<html>Actor</html>");
            else setText("<html>Actress</html>");
        }
    }
}
