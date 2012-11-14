package admin.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import admin.controller.LoginController;

//VS4E -- DO NOT REMOVE THIS LINE!
public class LoginPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JButton jButton1;
	private JButton jButton0;
	private JTextField jTextField0;
	private JPasswordField jPasswordField0;

	public LoginPanel() {
		initComponents();
	}

	private void initComponents() {
		setBorder(BorderFactory
				.createTitledBorder(null, " Login ", TitledBorder.LEADING,
						TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(2, 10, 10), new Leading(
				21, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(5, 10, 10), new Leading(
				52, 10, 10)));
		add(getJButton1(), new Constraints(new Leading(117, 10, 10),
				new Leading(91, 12, 12)));
		add(getJButton0(), new Constraints(new Leading(33, 69, 10, 10),
				new Leading(91, 12, 12)));
		add(getJTextField0(), new Constraints(new Leading(77, 129, 10, 10),
				new Leading(19, 12, 12)));
		add(getJPasswordField0(), new Constraints(new Leading(78, 126, 12, 12),
				new Leading(50, 12, 12)));
		setSize(235, 146);
	}

	private JPasswordField getJPasswordField0() {
		if (jPasswordField0 == null) {
			jPasswordField0 = new JPasswordField();
			jPasswordField0.setEchoChar('•');
		}
		return jPasswordField0;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();

		}
		return jTextField0;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("OK");
			jButton0.addActionListener(this);
		}
		return jButton0;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Cancel");
			jButton1.addActionListener(this);
		}
		return jButton1;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Password");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Username");
		}
		return jLabel0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String ac = e.getActionCommand();
		if (ac.equals("OK")) {
			LoginController lgc = new LoginController();
			String username = jTextField0.getText().trim();
			String password = jPasswordField0.getText();
			String validateResult = lgc.validateLoginField(username, password);

			if (validateResult.equals("")) {
				boolean login = lgc.validateLoginInfo(username, password);
				if (login) {
					JOptionPane.showMessageDialog(this, "Success", "Success",
							JOptionPane.DEFAULT_OPTION);
					Container c = new Container();
					c = SwingUtilities.windowForComponent(this);
					if(c instanceof JFrame){
						c.setVisible(false);
					}
					AdminMainFrame2 amf = new AdminMainFrame2();

				} else {
					JOptionPane.showMessageDialog(this, "Login Failed",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, validateResult, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if (ac.equals("Cancel")) {
			System.exit(1);

		}
	}
}
