package hotmovie;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import wom.MainWindow;

public class NewestMovieFrame {
	public static final Dimension SIZE = new Dimension(245, 245);
	private MainWindow mainWindow;
	public NewestMovieFrame(ArrayList<String> idList, MainWindow mainWindow) {
		// TODO Auto-generated constructor stub
		JFrame frame = new JFrame("Newest Movie");
		frame.getContentPane().add(new HotMoviePanel(idList, mainWindow));
		frame.getContentPane().setPreferredSize(HotMovieFrame.SIZE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setLocation(0, 370);
		frame.setVisible(true);
	}
}
