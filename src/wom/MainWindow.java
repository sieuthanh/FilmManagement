//Main Window
package wom;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.FileNotFoundException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

import checkout.AskForCheckOut;

import cart.Cart;
import moviedisplaypanel.WriteFile;

/**
 * 
 * @author Fight For Fun Group
 */
public class MainWindow extends JFrame implements ActionListener {
	private Point p = new Point();
	private WindowBackgroundPanel bgr = new WindowBackgroundPanel();
	private ExitButton extbutton = new ExitButton();
	private MinimizeButton minimizebutton = new MinimizeButton();
	private Tab tab = new Tab();
	public Tab getTab() {
		return tab;
	}

	public void setTab(Tab tab) {
		this.tab = tab;
	}

	private AboutButton aboutbtn = new AboutButton();
	private HelpButton helpbtn = new HelpButton();
	private ThemeButton themebtn = new ThemeButton();
	private Cart cart;
	private Image icon = new ImageIcon("resources/img/logo.png").getImage();
	public static Cart cartClick;

	public void setCartClick(Cart cartClick) {
		this.cartClick = cartClick;
	}

	public Cart getCartClick() {
		return cartClick;
	}

	class WindowEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent evt) {
			cartClick.isOpen = false;
		}
	}

	public MainWindow(String title) { // constructor
		super(title);
		
		
		//Main Window film
		cartClick = new Cart();
		cartClick.addWindowListener(new WindowEventHandler());
		cart = new Cart();
		setUndecorated(true);
		// add background panel and buttons (minimize and exit buttons)
		add(bgr);
		bgr.setLayout(null);
		setIconImage(icon);

		// Set properties of the buttons
		Insets insets = bgr.getInsets();// definite the position of buttons
		Dimension d = extbutton.getPreferredSize();
		extbutton.setBounds(insets.left + 740, insets.top + 5, d.width,
				d.height);
		minimizebutton.setBounds(insets.left + 700, insets.top + 5, d.width,
				d.height);
		d = aboutbtn.getPreferredSize();
		helpbtn.setBounds(insets.left + 530, insets.top + 49, d.width, d.height);
		aboutbtn.setBounds(insets.left + 610, insets.top + 49, d.width,
				d.height);
		themebtn.setBounds(insets.left + 690, insets.top + 49, d.width,
				d.height);
		helpbtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				cart = new Cart();
				if (cart.getCp().getCartElementList().size() == 0) {

				} else {
					cart.setUndecorated(true);
					cart.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
					cart.setVisible(true);

				}
			}

			public void mouseExited(MouseEvent e) {
				cart.setVisible(false);
				cart.dispose();

				System.gc();
			}

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					cartClick.getCp().setCartElementList();
					if (cartClick.getCp().getCartElementList().size() == 0) {
						JOptionPane.showMessageDialog(null,
								"Your cart is empty");
					} else {
						if (cartClick.isOpen) {

							cartClick.setVisible(false);
							cartClick.dispose();
							cartClick.getCp().setCartElementList();
							;
							cartClick.setVisible(true);
							cartClick.isOpen = true;

						} else {
							cartClick = new Cart();
							cartClick.isOpen = true;
							cartClick
									.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							cartClick.setResizable(false);
							cartClick.setVisible(true);

						}
					}
				}
			}
		});

		// Add buttons
		bgr.add(extbutton);
		bgr.add(minimizebutton);
		bgr.add(aboutbtn);
		bgr.add(helpbtn);
		bgr.add(themebtn);

		// Add Action Listeer
		extbutton.addActionListener(this);
		minimizebutton.addActionListener(this);
		aboutbtn.addActionListener(this);
		helpbtn.addActionListener(this);
		themebtn.addActionListener(this);

		// add tabpane and definite the position of tab
		d = tab.getPreferredSize();
		tab.setBounds(insets.left + 15, insets.top + 90, d.width, d.height);
		bgr.add(tab);

		// add drag and drop action
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getX() <= 800 && e.getY() <= 80) {
					p.x = e.getX();
					p.y = e.getY();
				} else
					p.x = p.y = -1;
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (p.x != -1 && p.y != -1) {
					Point fpoint = getLocation();
					setLocation(fpoint.x + e.getX() - p.x, fpoint.y + e.getY()
							- p.y);
				}
			}
		});
		setSize(800, 600);
		setLocationRelativeTo(null);
		// com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.5f);
		com.sun.awt.AWTUtilities.setWindowShape(this,
				new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25,
						25));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
		if (e.getSource() == extbutton)
			System.exit(0);
		if (e.getSource() == minimizebutton)
			setState(JFrame.ICONIFIED);
		if (e.getSource() == aboutbtn) {
			// JFrame about = new JFrame("About");
			// about.add(new About());
			// about.pack();
			// about.setIconImage(icon);
			// about.setLocationRelativeTo(null);
			// about.setAlwaysOnTop(true);
			// about.setVisible(true);
			if (cartClick.getCp().getCartElementList().size() > 0) {
				JFrame jf = new AskForCheckOut();
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jf.setLocationRelativeTo(null);
				jf.setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "Your cart is empty");
			}
		}
		if (e.getSource() == themebtn) {
			final ThemeChooser themechooser = new ThemeChooser();
			List themelist = themechooser.getThemeLabelList();
			for (int i = 0; i < themelist.size(); i++) {
				final ThemeLabel x = (ThemeLabel) themelist.get(i);
				x.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent evt) {
						WriteFile writeFile = new WriteFile("resources/theme/"
								+ "theme.cfg", x.getThemeName());
						themechooser.dispose();
						bgr.changeTheme();
						tab.changeTheme();
					}
				});
			}
		}
	}
}
