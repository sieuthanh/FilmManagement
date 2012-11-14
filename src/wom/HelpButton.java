//Exit Button for Main Window
package wom;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author Fight For Fun Group
 */
public class HelpButton extends JButton{
    private Icon icon;
    private Icon rolloverIcon;
    private Icon pressedIcon;
    private GetTheme getTheme = new GetTheme();
    private String theme = getTheme.Theme();
    private Cursor hand = new Cursor(Cursor.HAND_CURSOR);

    public HelpButton() {
        super();
        setFocusable(false);
        setCursor(hand);
        setText("Cart");
        //add current state exit button
        icon = new IconManager("/resources/theme/" + theme + "/cart.png").getIcon();
        setIcon(icon);
        //add roll over button
        setRolloverEnabled(true);
        rolloverIcon = new IconManager("/resources/theme/" + theme + "/cart.png").getIcon();
        setRolloverIcon(rolloverIcon);
        //add pressed button
        pressedIcon = new IconManager("/resources/theme/" + theme + "/cart.png").getIcon();
        setPressedIcon(pressedIcon);
        //set prefered size of exit button
	setPreferredSize(new Dimension(70, 30));
        setContentAreaFilled(false);
        setBorder(null);
    }        
}
