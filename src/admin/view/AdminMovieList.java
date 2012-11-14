package admin.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import movielisttab.CharClassify;
import databasemanager.DatabaseManager;
import databasemanager.Movie;

public class AdminMovieList extends JPanel implements ActionListener {
	private JTable table;
	private Object[] colname = new Object[] { "Title", "Country", "Year",
			"Rating", "Time", "Check", "Id" };
	private Object[][] data = {};
	private JScrollPane scrollpane = new JScrollPane();
	private Insets insets = getInsets();
	private CharClassify[] charclassify = new CharClassify[9];
	private DatabaseManager database = new DatabaseManager();
	private LinkedList list = database.getMovieList();
	private int previousOrderedCommand = 0, currentOrderedCommand;
	private int previousOrderedColumn = -1;
	private int currentbutton = 0;
	private static String buttonTiltle[] = { "Add", "Delete", "Update" };
	private static JButton button[] = new JButton[buttonTiltle.length];
	// override getColumnClass so the JTable will get the better sorter
	// rather than using toString() method in order to compare
	// 2 row's values
	private DefaultTableModel model = new DefaultTableModel(data, colname) {
		@Override
		public Class getColumnClass(int col) {
			Class returnValue;
			if ((col >= 0) && (col < getColumnCount())) {
				returnValue = getValueAt(0, col).getClass();
			} else {
				returnValue = Object.class;
			}
			return returnValue;
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			 if (col < 2) {
	                return false;
	            } else {
	                return true;
	            }
		}

	};

	public void resetModel(List<Movie> movieList) {
		DefaultTableModel model = (DefaultTableModel) this.getTable()
				.getModel();
		if (model.getRowCount() > 0) {
			for (int i = model.getRowCount() - 1; i > -1; i--) {
				model.removeRow(i);
			}
		}
		if (movieList != null) {
			for (int i = 0; i < movieList.size(); i++) {
				Movie movie = (Movie) movieList.get(i);
				model.addRow(new Object[] { movie.getName(),
						movie.getCountry(), movie.getYear(), movie.getRating(),
						movie.getRuntime(), new Boolean(false), movie.getId() });
			}
		}
	}


	public AdminMovieList(List<Movie> movieList) {
		super();
		Dimension d;
		setLayout(null);
		setPreferredSize(new Dimension(640, 430));
		Border border = BorderFactory.createTitledBorder("Movie List");
		setBorder(border);
		setBackground(Color.white);

		// add first character movie classification panel
		CharClassifyInitilaize();
		// get movie list and also add data to table
		if (movieList != null) {
			for (int i = 0; i < movieList.size(); i++) {
				Movie movie = (Movie) movieList.get(i);
				model.addRow(new Object[] { movie.getName(),
						movie.getCountry(), movie.getYear(), movie.getRating(),
						movie.getRuntime(), new Boolean(false), movie.getId() });

			}
		}
		table = new JTable(model);
		table.getColumnModel()
				.removeColumn(table.getColumnModel().getColumn(6));
		/*
		 * RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		 * table.setRowSorter(sorter);
		 */
		//
		table.setFocusable(false);
		// set width of columns
		TableColumn col = null;
		for (int i = 0; i < colname.length - 1; i++) {
			col = table.getColumnModel().getColumn(i);
			switch (i) {
			case 0:
				col.setPreferredWidth(360);
				break;
			case 1:
				col.setPreferredWidth(95);
				break;// 75
			case 2:
				col.setPreferredWidth(75);
				break;
			case 3:
				col.setPreferredWidth(85);
				break;
			case 4:
				col.setPreferredWidth(70);
				break;
			case 5:
				col.setPreferredWidth(70);
				break;
			case 6:
				col.setPreferredWidth(70);
				break;
			}
		}
		// disable columns drag and resize
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		// set table's text and header font and size
		table.setFont(new Font("arial", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 13));
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTableHeader h = (JTableHeader) e.getSource();
				TableColumnModel columnModel = h.getColumnModel();
				int viewColumn = h.columnAtPoint(e.getPoint());
				int column = columnModel.getColumn(viewColumn).getModelIndex();
				clearTable(model);
				if (previousOrderedColumn == column)
					currentOrderedCommand = 1 - previousOrderedCommand;
				else {
					currentOrderedCommand = 1;
					previousOrderedColumn = column;
				}
				previousOrderedCommand = currentOrderedCommand;
				LinkedList orderedList = new LinkedList();
				switch (column) {
				case 0: {
					orderedList = database.getMovieListByOrder("name",
							currentOrderedCommand);
					break;
				}
				case 1: {
					orderedList = database.getMovieListByOrder("country",
							currentOrderedCommand);
					break;
				}
				case 2: {
					orderedList = database.getMovieListByOrder("year",
							currentOrderedCommand);
					break;
				}
				case 3: {
					orderedList = database.getMovieListByOrder("rating",
							currentOrderedCommand);
					break;
				}
				case 4: {
					orderedList = database.getMovieListByOrder("runtime",
							currentOrderedCommand);
					break;
				}
				case 5: {
					orderedList = database.getMovieListByOrder("id",
							currentOrderedCommand);
					break;
				}
				}
				orderedList = adjust(orderedList);
				for (int i = 0; i < orderedList.size(); i++) {
					Movie movie = (Movie) orderedList.get(i);
					model.addRow(new Object[] { movie.getName(),
							movie.getCountry(), movie.getYear(),
							movie.getRating(), movie.getRuntime(),
							new Boolean(false), movie.getId() });
				}
			}
		});
		// add table to scroll pane and also locate scroll pane in panel
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(621, 370));
		d = scrollpane.getPreferredSize();
		scrollpane.setBackground(Color.white);
		scrollpane.setBorder(null);
		scrollpane.setBounds(insets.left + 10, insets.top + 50, d.width,
				d.height);
		add(scrollpane);
		scrollpane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		table.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = e.getPoint();
				int column = table.columnAtPoint(p);
				int row = table.rowAtPoint(p);
				if (column == 0)
					table.setToolTipText(String.valueOf(table.getValueAt(row,
							column)));
				else
					table.setToolTipText(null);
			}
		});

	}

	public LinkedList adjust(LinkedList orderlist) {
		switch (currentbutton) {
		case 0:
			return orderlist;
		case 1: {
			int i = 0;
			while (i < orderlist.size()) {
				Movie movie = (Movie) orderlist.get(i);
				String name = movie.getName().toUpperCase();
				if (name.charAt(0) < 65 || name.charAt(0) > 68)
					orderlist.remove(i);
				else
					i++;
			}
			break;
		}
		case 2: {
			int i = 0;
			while (i < orderlist.size()) {
				Movie movie = (Movie) orderlist.get(i);
				String name = movie.getName().toUpperCase();
				if (name.charAt(0) < 69 || name.charAt(0) > 72)
					orderlist.remove(i);
				else
					i++;
			}
			break;
		}
		case 3: {
			int i = 0;
			while (i < orderlist.size()) {
				Movie movie = (Movie) orderlist.get(i);
				String name = movie.getName().toUpperCase();
				if (name.charAt(0) < 73 || name.charAt(0) > 76)
					orderlist.remove(i);
				else
					i++;
			}
			break;
		}
		case 4: {
			int i = 0;
			while (i < orderlist.size()) {
				Movie movie = (Movie) orderlist.get(i);
				String name = movie.getName().toUpperCase();
				if (name.charAt(0) < 77 || name.charAt(0) > 80)
					orderlist.remove(i);
				else
					i++;
			}
			break;
		}
		case 5: {
			int i = 0;
			while (i < orderlist.size()) {
				Movie movie = (Movie) orderlist.get(i);
				String name = movie.getName().toUpperCase();
				if (name.charAt(0) < 81 || name.charAt(0) > 84)
					orderlist.remove(i);
				else
					i++;
			}
			break;
		}
		case 6: {
			int i = 0;
			while (i < orderlist.size()) {
				Movie movie = (Movie) orderlist.get(i);
				String name = movie.getName().toUpperCase();
				if (name.charAt(0) < 85 || name.charAt(0) > 87)
					orderlist.remove(i);
				else
					i++;
			}
			break;
		}
		case 7: {
			int i = 0;
			while (i < orderlist.size()) {
				Movie movie = (Movie) orderlist.get(i);
				String name = movie.getName().toUpperCase();
				if (name.charAt(0) < 88 || name.charAt(0) > 90)
					orderlist.remove(i);
				else
					i++;
			}
			break;
		}
		case 8: {
			int i = 0;
			while (i < orderlist.size()) {
				Movie movie = (Movie) orderlist.get(i);
				String name = movie.getName().toUpperCase();
				if (name.charAt(0) >= 65 && name.charAt(0) <= 90)
					orderlist.remove(i);
				else
					i++;
			}
			break;
		}
		}
		return orderlist;
	}

	void clearTable(DefaultTableModel model) {
		while (model.getRowCount() > 0)
			model.removeRow(model.getRowCount() - 1);
	}

	public JTable getTable() {
		return table;
	}

	// Choice button functions
	public final void CharClassifyInitilaize() {
		charclassify[0] = new CharClassify("All");
		charclassify[0].setToolTipText("Show all movies");
		charclassify[0].setActive(true);
		charclassify[0].setFont(new Font("Tahoma", Font.BOLD, 17));
		//
		charclassify[1] = new CharClassify("A B C D");
		charclassify[1]
				.setToolTipText("Show movies that their titles start by A, B, C or D");
		//
		charclassify[2] = new CharClassify("E F G H");
		charclassify[2]
				.setToolTipText("Show movies that their titles start by E, F, G or H");
		//
		charclassify[3] = new CharClassify("I J K L");
		charclassify[3]
				.setToolTipText("Show movies that their titles start by I, J, K or L");
		//
		charclassify[4] = new CharClassify("M N O P");
		charclassify[4]
				.setToolTipText("Show movies that their titles start by M, N, O or P");
		//
		charclassify[5] = new CharClassify("Q R S T");
		charclassify[5]
				.setToolTipText("Show movies that their titles start by Q, R, S or T");
		//
		charclassify[6] = new CharClassify("U V W");
		charclassify[6]
				.setToolTipText("Show movies that their titles start by W, V, or W");
		//
		charclassify[7] = new CharClassify("X Y Z");
		charclassify[7]
				.setToolTipText("Show movies that their titles start by X, Y, or Z");
		//
		charclassify[8] = new CharClassify("Others");
		charclassify[8]
				.setToolTipText("Show movies that their titles start by sepecial characters like"
						+ " 0-9, /, (, ), etc");
		// add all buttons into panel
		for (int i = 0; i <= 8; i++) {
			Dimension d = charclassify[i].getPreferredSize();
			charclassify[i].setBounds(insets.left + 10 + i * 69,
					insets.top + 20, d.width, d.height);
			add(charclassify[i]);
		}
		// add action listener to those buttons
		for (int i = 0; i <= 8; i++)
			charclassify[i].addActionListener(this);
	}

	void buttonProcess(int i) {
		LinkedList list1, list2, list3, list4;
		list1 = list2 = list3 = list4 = null;
		switch (i) {
		case 0: {
			list1 = database.getMovieList();
			break;
		}
		case 1: {
			list1 = database.getMovieByFirstCharacter('a');
			list2 = database.getMovieByFirstCharacter('b');
			list3 = database.getMovieByFirstCharacter('c');
			list4 = database.getMovieByFirstCharacter('d');
			break;
		}
		case 2: {
			list1 = database.getMovieByFirstCharacter('e');
			list2 = database.getMovieByFirstCharacter('f');
			list3 = database.getMovieByFirstCharacter('g');
			list4 = database.getMovieByFirstCharacter('h');
			break;
		}
		case 3: {
			list1 = database.getMovieByFirstCharacter('i');
			list2 = database.getMovieByFirstCharacter('j');
			list3 = database.getMovieByFirstCharacter('k');
			list4 = database.getMovieByFirstCharacter('l');
			break;
		}
		case 4: {
			list1 = database.getMovieByFirstCharacter('m');
			list2 = database.getMovieByFirstCharacter('n');
			list3 = database.getMovieByFirstCharacter('o');
			list4 = database.getMovieByFirstCharacter('p');
			break;
		}
		case 5: {
			list1 = database.getMovieByFirstCharacter('q');
			list2 = database.getMovieByFirstCharacter('r');
			list3 = database.getMovieByFirstCharacter('s');
			list4 = database.getMovieByFirstCharacter('t');
			break;
		}
		case 6: {
			list1 = database.getMovieByFirstCharacter('u');
			list2 = database.getMovieByFirstCharacter('v');
			list3 = database.getMovieByFirstCharacter('w');
			break;
		}
		case 7: {
			list1 = database.getMovieByFirstCharacter('x');
			list2 = database.getMovieByFirstCharacter('y');
			list3 = database.getMovieByFirstCharacter('z');
			break;
		}
		case 8: {
			list1 = database.getMovieByFirstCharacter('*');
			break;
		}
		}
		if (list1 != null)
			for (int t = 0; t < list1.size(); t++) {
				Movie movie = (Movie) list1.get(t);
				model.addRow(new Object[] { movie.getName(),
						movie.getCountry(), movie.getYear(), movie.getRating(),
						movie.getRuntime(), new Boolean(false), movie.getId() });
			}
		if (list2 != null)
			for (int t = 0; t < list2.size(); t++) {
				Movie movie = (Movie) list2.get(t);
				model.addRow(new Object[] { movie.getName(),
						movie.getCountry(), movie.getYear(), movie.getRating(),
						movie.getRuntime(), new Boolean(false), movie.getId() });
			}
		if (list3 != null)
			for (int t = 0; t < list3.size(); t++) {
				Movie movie = (Movie) list3.get(t);
				model.addRow(new Object[] { movie.getName(),
						movie.getCountry(), movie.getYear(), movie.getRating(),
						movie.getRuntime(), new Boolean(false), movie.getId() });
			}
		if (list4 != null)
			for (int t = 0; t < list4.size(); t++) {
				Movie movie = (Movie) list4.get(t);
				model.addRow(new Object[] { movie.getName(),
						movie.getCountry(), movie.getYear(), movie.getRating(),
						movie.getRuntime(), new Boolean(false), movie.getId() });
			}
	}

	//

	public void actionPerformed(ActionEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
		int i;
		for (i = 0; i <= 8; i++)
			if ((e.getSource() == charclassify[i])
					&& !charclassify[i].isActive()) {
				for (int j = 0; j <= 8; j++)
					if (charclassify[j].isActive()) {
						charclassify[j].setActive(false);
						charclassify[j].setFont(new Font("Tahoma", Font.PLAIN,
								15));
					}
				currentbutton = i;
				charclassify[i].setActive(true);
				clearTable(model);
				buttonProcess(i);
			}
	}
}
