package admin.Crawler;

public class ImdbCrawlerMovieThread extends Thread {
	private ImdbCrawlerMovie icm;
	private String film;
	private int year;
	public ImdbCrawlerMovieThread(String film, int year){
		this.film = film;
		this.year = year;
	}
	public ImdbCrawlerMovie getIcm() {
		return icm;
	}
	public void run(){
		icm = new ImdbCrawlerMovie(film,year);
	}
}
