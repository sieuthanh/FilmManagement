//once clicking this button, back to movie list

package movielisttab;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;

/**
 *
 * @author Fight For Fun Group
 */
public class BackToList extends JButton {
    private Boolean active = false;
    public BackToList() {
        super();
        setText("<html><u>Back to Movie List...</u></html>");
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(120, 30));
        setBorder(null);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.LIGHT_GRAY);
        Font font = new Font("Tahoma", Font.PLAIN, 13);
        setFont(font);        
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
        if (active) setForeground(Color.BLUE);
        else setForeground(Color.LIGHT_GRAY);
    }
}
