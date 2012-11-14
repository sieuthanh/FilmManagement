/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wom;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class ThemeChooser extends JFrame {
    private GetTheme getTheme = new GetTheme();
    private String theme = getTheme.Theme();
    
    private JPanel panel = new JPanel();
    private List themelist = new LinkedList();
    private ThemeLabel x;
    
    public ThemeChooser() {
        panel.setLayout(new FlowLayout());
        panel.add(x = new ThemeLabel("default"));
        themelist.add(x);
        panel.add(x = new ThemeLabel("black"));
        themelist.add(x);
        panel.add(x = new ThemeLabel("green"));
        themelist.add(x);
        panel.add(x = new ThemeLabel("pink"));
        themelist.add(x);
        panel.add(x = new ThemeLabel("red"));
        themelist.add(x);
        panel.add(x = new ThemeLabel("violet"));
        themelist.add(x);
        panel.setPreferredSize(new Dimension(550,270));
        
        add(panel);
        pack();
        setTitle("Theme Chooser");
        setIconImage(new ImageIcon("resources/img/logo.png").getImage());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public List getThemeLabelList() {
        return themelist;
    }
}
