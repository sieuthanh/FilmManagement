//Managing icons (including exit/minimize buttons and tabpane
package wom;

import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Fight For Fun Group
 */
public class IconManager {
    private Icon icon;
    private URL url;

    public IconManager(String link) {
        url = WindowBackgroundPanel.class.getResource(link);
        if (url == null) System.out.println("Image not found");
        icon = new ImageIcon(url);
    }

    public Icon getIcon() {   
        return icon;
    }
}
