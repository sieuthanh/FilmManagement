package cart;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Cart extends JFrame {
	CartPanel cp;
	public boolean isOpen = false;

	public CartPanel getCp() {
		return cp;
	}

	public void setCp(CartPanel cp) {
		this.cp = cp;
	}

	public Cart()  {
		// TODO Auto-generated constructor stub

		cp = new CartPanel();
		JScrollPane jsp = new JScrollPane(cp,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		add(jsp);
		setSize(250, 200);
		setLocation(1090, 70);

	}
}
