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
public class AboutButton extends JButton {
	private Icon icon;
	private Icon rolloverIcon;
	private Icon pressedIcon;
	private Cursor hand = new Cursor(Cursor.HAND_CURSOR);

	public AboutButton() {
		super();
		setFocusable(false);
		setText("Pay");
		setCursor(hand);
		// add current state about button
		icon = new IconManager("/resources/theme/default/aboutbtn.png")
				.getIcon();
		setIcon(icon);
		// add roll over button
		setRolloverEnabled(true);
		rolloverIcon = new IconManager(
				"/resources/theme/default/aboutbtn_roll.png").getIcon();
		setRolloverIcon(rolloverIcon);
		// add pressed button
		pressedIcon = new IconManager(
				"/resources/theme/default/aboutbtn_press.png").getIcon();
		setPressedIcon(pressedIcon);
		// set prefered size of exit button
		setPreferredSize(new Dimension(70, 30));
		setContentAreaFilled(false);
		setBorder(null);
	}
}
