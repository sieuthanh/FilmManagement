//Exit Button for Main Window
package wom;

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author Fight For Fun Group
 */
public class ExitButton extends JButton{
    private Icon icon;
    private Icon rolloverIcon;
    private Icon pressedIcon;
    private GetTheme getTheme = new GetTheme();
    private String theme = getTheme.Theme();

    public ExitButton() {
        super();
        setFocusPainted(false);
        //add current state exit button
       
        icon = new IconManager("/resources/theme/" + theme + "/exit-button.png").getIcon();
        setIcon(icon);
        //add roll over button
        setRolloverEnabled(true);
        rolloverIcon = new IconManager("/resources/theme/" + theme + "/rolloverbutton.png").getIcon();
        setRolloverIcon(rolloverIcon);
        //add pressed button
        pressedIcon = new IconManager("/resources/theme/" + theme + "/pressedbutton.png").getIcon();
        setPressedIcon(pressedIcon);
        //set prefered size of exit button
	setPreferredSize(new Dimension(60, 30));
        setContentAreaFilled(false);
        setBorder(null);
    }        
}
