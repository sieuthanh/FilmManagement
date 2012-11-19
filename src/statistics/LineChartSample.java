package statistics;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class LineChartSample {

	static int state;
	static void initAndShowGUI(int state1) {
		// This method is invoked on Swing thread
		state = state1;
		final JFrame frame = new JFrame("FX");
		final JFXPanel fxPanel = new JFXPanel();

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(state == 1){
				initFX(fxPanel, frame);
				}if(state == 2){
					initFX2(fxPanel, frame);
				}else{
					
				}
			}
		});

	}

	private static void initFX(JFXPanel fxPanel, JFrame frame) {
		// This method is invoked on JavaFX thread
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Number of Month");
		// creating the chart
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(
				xAxis, yAxis);

		lineChart.setTitle("Stock Monitoring, 2010");
		// defining a series
		XYChart.Series series = new XYChart.Series();
		series.setName("My portfolio");
		// populating the series with data
		StatisticUtil su = new StatisticUtil();
		Date today = new Date();
		int month = today.getMonth() + 1;
		int year = today.getYear() + 1900;
		ArrayList<Integer> result = su.getStatisticEachDayInMonth(month,year);
		for (int i = 0; i < result.size(); i++) {
			series.getData().add(new XYChart.Data((i + 1), result.get(i)));
		}

		Scene scene = new Scene(lineChart, 800, 600);

		lineChart.getData().add(series);

		fxPanel.setScene(scene);
		frame.add(fxPanel);
		frame.pack();
		frame.setVisible(true);
	}

	private static void initFX2(JFXPanel fxPanel, JFrame frame) {
		// This method is invoked on JavaFX thread
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis,
				yAxis);
		bc.setTitle("Year Summary");
		xAxis.setLabel("Month");
		yAxis.setLabel("Amount");
		Date date = new Date();
		int year = date.getYear() + 1900;
		StatisticUtil su = new StatisticUtil();
		ArrayList<Integer> result = new ArrayList<>();
		result = su.getStatisticsEachMonthInYear(year);
		XYChart.Series series1 = new XYChart.Series();
		series1.setName(year + "");
		for (int i = 0; i < result.size(); i++) {
			series1.getData().add(new XYChart.Data((i + 1)+"", result.get(i)));

		}
		Scene scene = new Scene(bc, 800, 600);
		bc.getData().addAll(series1);
		fxPanel.setScene(scene);
		frame.add(fxPanel);
		frame.pack();
		frame.setVisible(true);
	}
	
}