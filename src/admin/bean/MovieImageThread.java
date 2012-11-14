package admin.bean;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import admin.Crawler.ImdbCrawlerMovie;

public class MovieImageThread extends Thread {
	private int i;
	ImdbCrawlerMovie icm = new ImdbCrawlerMovie();
	public MovieImageThread(int i, ImdbCrawlerMovie icm){
		this.i = i;
		this.icm = icm;
	}
	public void run(){
		try {
			icm.saveMovieImage(i);
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
