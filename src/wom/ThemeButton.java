/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wom;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author uNstOppAbLe
 */
public class ThemeButton extends JButton {
    private Icon icon;
    private Icon rolloverIcon;
    private Icon pressedIcon;
    private GetTheme getTheme = new GetTheme();
    private String theme = getTheme.Theme();
    private Cursor hand = new Cursor(Cursor.HAND_CURSOR);

    public ThemeButton () { //Constructor
        super();
        setFocusable(false);
        setText("Theme");
        setCursor(hand);
        //add current state theme button
        icon = new IconManager("/resources/theme/" + theme + "/themebtn.png").getIcon();
        setIcon(icon);
        //add roll over button
        setRolloverEnabled(true);
        rolloverIcon = new IconManager("/resources/theme/" + theme + "/themebtn_roll.png").getIcon();
        setRolloverIcon(rolloverIcon);
        //add pressed button
        pressedIcon = new IconManager("/resources/theme/" + theme + "/themebtn_press.png").getIcon();
        setPressedIcon(pressedIcon);
        //set prefered size of theme button
	setPreferredSize(new Dimension(70, 30));
        setContentAreaFilled(false);
        setBorder(null);
    }
}
