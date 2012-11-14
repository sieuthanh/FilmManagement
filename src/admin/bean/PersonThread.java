package admin.bean;

import admin.Crawler.ImdbCrawlerMovie;

public class PersonThread extends Thread {
	private int i;
	private ImdbCrawlerMovie icm = new ImdbCrawlerMovie();

	public PersonThread(int i, ImdbCrawlerMovie icm) {
		this.i = i;
		this.icm = icm;
	}

	public void run() {
		icm.getOnePersonDetailsFromIMDB(i);

	}
}
