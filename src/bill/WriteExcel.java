package bill;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import admin.DAO.HibernateUtil;

import checkout.CheckoutFrame;
import databasemanager.MoneyUnit;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class WriteExcel {

	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String inputFile;
	private ArrayList<BillElement> billELementList = new ArrayList<BillElement>();

	public WriteExcel(ArrayList<BillElement> billELementList) {
		// TODO Auto-generated constructor stub
		this.billELementList = billELementList;
	}

	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void write() throws IOException, WriteException {
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Report", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		createLabel(excelSheet);
		createContent(excelSheet);

		workbook.write();
		workbook.close();
	}

	private void createLabel(WritableSheet sheet) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(
				WritableFont.TIMES, 10, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

		// Write a few headers
		addCaption(sheet, 0, 0, "Film Name");
		addCaption(sheet, 1, 0, "Size");
		addCaption(sheet, 2, 0, "Quantity");
		addCaption(sheet, 3, 0, "Amount");

	}

	@SuppressWarnings("deprecation")
	private void createContent(WritableSheet sheet) throws WriteException,
			RowsExceededException {
		// Write a few number
		int total = 0;
		HibernateUtil hu = new HibernateUtil();
		SessionFactory sf = hu.getSessionFactory();
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(MoneyUnit.class);
		MoneyUnit mu = (MoneyUnit) criteria.uniqueResult();
		session.close();
		for (int i = 0; i < billELementList.size(); i++) {
			addLabel(sheet, 0, i + 1, billELementList.get(i).getFilmName());
			addLabel(sheet, 1, i + 1, billELementList.get(i).getSize());
			addLabel(sheet, 2, i + 1, billELementList.get(i).getQuantity());
			addLabel(sheet, 3, i + 1, billELementList.get(i).getAmount());
			total = total + Integer.parseInt(billELementList.get(i).getSize())
					* Integer.parseInt(billELementList.get(i).getQuantity())
					* mu.getUsd();
		}
		addCaption(sheet, 2, billELementList.size() + 1, "Total");
		addCaption(sheet, 3, billELementList.size() + 1, total + " USD");
		BillArchive ba = new BillArchive();
		ba.setTotal(total);
		Date date = new Date();
		ba.setDay(date.getDate());
		ba.setMonth(date.getMonth() + 1);
		ba.setYear(date.getYear() + 1900);
		session = sf.openSession();
		session.save(ba);
		session.beginTransaction().commit();
		session.close();

	}

	private void addCaption(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}

	private void addNumber(WritableSheet sheet, int column, int row,
			Integer integer) throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, integer, times);
		sheet.addCell(number);
	}

	private void addLabel(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}

}