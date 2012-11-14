//Minimize Button for Main Window
package wom;

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author Fight For Fun Group
 */
public class MinimizeButton extends JButton{
    private Icon icon;
    private Icon rolloverIcon;
    private Icon pressedIcon;

    public MinimizeButton() {
        super();
        setFocusPainted(false);
        //add current state minimize button
        icon = new IconManager("/resources/theme/default/min-button.png").getIcon();
        setIcon(icon);
        //add roll over button
        setRolloverEnabled(true);
        rolloverIcon = new IconManager("/resources/theme/default/rollover-min-button.png").getIcon();
        setRolloverIcon(rolloverIcon);
        //add pressed button
        pressedIcon = new IconManager("/resources/theme/default/pressed-min-button.png").getIcon();
        setPressedIcon(pressedIcon);
        //set prefered size of minimize button
	setPreferredSize(new Dimension(60, 30));
        setContentAreaFilled(false);
        setBorder(null);
    }
}
