package hotmovie;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import wom.MainWindow;

public class HotMovieFrame {
	public static final Dimension SIZE = new Dimension(245, 245);
	private MainWindow mainWindow;
	public HotMovieFrame(ArrayList<String> idList, MainWindow mainWindow) {
		// TODO Auto-generated constructor stub
		JFrame frame = new JFrame("Hot Movie");
		frame.getContentPane().add(new HotMoviePanel(idList, mainWindow));
		frame.getContentPane().setPreferredSize(HotMovieFrame.SIZE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setLocation(0, 70);
		frame.setVisible(true);
	}
}
