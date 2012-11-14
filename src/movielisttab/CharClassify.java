
package movielisttab;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author Administrator
 */
public class CharClassify extends JButton {
    private Boolean active = false;
    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public CharClassify(String title) {
        super(title);
        setContentAreaFilled(false);
        setBorder(null);
        setPreferredSize(new Dimension(69, 30));
        setFont(new Font("Tahoma", Font.PLAIN, 15));
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter(){
            @Override
           public void mouseEntered(MouseEvent e) {
               setFont(new Font("Tahoma", Font.BOLD, 17));
           }
            @Override
           public void mouseExited(MouseEvent e) {
               if (!isActive())
                setFont(new Font("Tahoma", Font.PLAIN, 15));
           }

            @Override
            public void mousePressed(MouseEvent e) {
                setFont(new Font("Tahoma", Font.PLAIN, 15));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setFont(new Font("Tahoma", Font.BOLD, 17));
            }
        });
    }
}
