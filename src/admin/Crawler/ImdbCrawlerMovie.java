package admin.Crawler;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.text.WordUtils;

import databasemanager.Company;
import databasemanager.Movie;
import databasemanager.Person;

import admin.bean.MovieImageThread;
import admin.bean.PersonThread;

public class ImdbCrawlerMovie {
	private String filmName;
	private int year;
	private String inputStringFinal;
	// Movie Detail ok
	private Movie movie = new Movie();
	// Person: get all in cast and get director
	private ArrayList<Person> person = new ArrayList<Person>();

	// Company ok
	private ArrayList<Company> company = new ArrayList<Company>();
	// Genre ok
	private ArrayList genre = new ArrayList();
	// Short Story ok
	private String shortStory;
	// Full Story ok
	private String fullStory;
	// Image
	private ArrayList<String> movieImage = new ArrayList<String>();
	private ArrayList<String> personImage = new ArrayList<String>();
	// response
	String response;

	// Constructor
	public ImdbCrawlerMovie() {

	}

	public ImdbCrawlerMovie(String filmName, int year) {
		// Create link to find movie
		super();
		this.filmName = filmName;
		this.year = year;
		filmName = WordUtils.capitalizeFully(filmName).trim();
		String inputString;
		inputString = filmName + " %28" + year + "%29";
		char[] arr;
		arr = inputString.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == ' ') {
				arr[i] = '+';
			}
		}
		this.inputStringFinal = new String(arr);
		this.inputStringFinal = "http://www.imdb.com/find?q="
				+ this.inputStringFinal + "&s=all";
		// getResponse from IMDB
		getResponse(inputStringFinal);
		if (response != null) {
			getDetailsFromIMDB();
			getMovieImageFromIMDB();
			getCompanyFromIMDB();
			getGenreFromIMDB();
			getPersonIdFromIMDB();
			getPersonDetailsFromIMDB();
			saveDataToFolder();
		}
	}

	// Send Request
	public URLConnection getConnection(String u) {

		URLConnection con = null;
		URL imdb;
		try {
			// String u = "http://www.imdb.com/find?q=" + inputStringFinal
			// + "&s=all";

			imdb = new URL(u);
			con = imdb.openConnection();
			con.setRequestProperty("User-Agent", "imdb");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR ",
					JOptionPane.ERROR_MESSAGE);
		}
		return con;
	}

	// GET RESPONSE
	public void getResponse(String u) {
		URLConnection con = this.getConnection(u);
		String text = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			for (;;) {
				line = in.readLine();
				if (line == null)
					break;
				sb.append(line);
				sb.append(' ');
			}
			text = sb.toString();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response = text;
	}

	// get Movie shortStory fullStory
	public void getDetailsFromIMDB() {
		String regex = "<link rel=\"canonical\" href=\"http://www.imdb.com/title/(.+?)/\" />.+?<div class=\"titlePageSprite star-box-giga-star\">(.+?)</div>.+?<p itemprop=\"description\">(.+?)</p>.+?<h2>Storyline</h2>.+?<p>(.+?)<em.+?<h4 class=\"inline\">Country:</h4>.+?<a[^>]+>(.+?)</a>.+?<time itemprop=\"duration\" [^>]+>(.+?)</time>";
		Pattern moviePattern = Pattern.compile(regex);
		Matcher movieMatch = moviePattern.matcher(response);

		if (movieMatch.find()) {
			movie.setId(movieMatch.group(1).replace("tt", "").trim());
			movie.setRating(Float.parseFloat(movieMatch.group(2)));
			shortStory = movieMatch.group(3);
			fullStory = movieMatch.group(4);

			movie.setCountry(movieMatch.group(5));
			movie.setRuntime(Integer.parseInt(movieMatch.group(6)
					.replace("min", "").trim()));
			movie.setName(filmName);
			movie.setYear(year);

		}
		if (shortStory.indexOf("&#x27;") != -1) {
			shortStory.replace("&#x27;", "'");
		}
		if (fullStory.indexOf("&#x27;") != -1) {
			fullStory.replace("&#x27;", "'");
		}
	}

	// getGenre
	public void getGenreFromIMDB() {
		String regex3 = "href=\"/genre/(.+?)\".+?itemprop=\"genre\"";
		Pattern genrePattern = Pattern.compile(regex3);
		Matcher genreMatcher = null;
		if (response != null) {
			genreMatcher = genrePattern.matcher(response);
		}

		while (genreMatcher.find()) {
			genre.add(genreMatcher.group(1));
		}
	}

	// get Company
	public void getCompanyFromIMDB() {
		String regex4 = "<a.+?href=\"/company/co(.+?)/\".+?>(.+?)</a>";
		Pattern companyPattern = Pattern.compile(regex4);
		Matcher companyMatcher = companyPattern.matcher(response);
		Company tmp = new Company();
		while (companyMatcher.find()) {
			tmp.setId(companyMatcher.group(1));
			tmp.setName(companyMatcher.group(2));
			company.add(tmp);
		}
	}

	// get Movie Image
	public void getMovieImageFromIMDB() {
		String regex2 = "href=\"/media/rm(.+?)/.+?\"\\s*>\\s*<img.+?src=\"(.+?)\".+?/></a>";
		Pattern filmImagePatter = Pattern.compile(regex2);
		Matcher filmImageMatcher = filmImagePatter.matcher(response);

		while (filmImageMatcher.find()) {
			movieImage.add(filmImageMatcher.group(2));
		}
	}

	// save image, short description, long description to folder
	public void saveDataToFolder() {
		Image image1 = null;
		boolean success = new File("C:\\Users\\thanh\\Desktop\\"
				+ movie.getId()).mkdir();
		if (!success) {
			System.out.println("Folder creation failed or the folder is exist");
		}
		FileWriter f;
		BufferedWriter out1;
		MovieImageThread mit[] = new MovieImageThread[movieImage.size()];
		try {
			for (int i = 0; i < movieImage.size(); i++) {
				// saveMovieImage(i);
				mit[i] = new MovieImageThread(i, this);
				mit[i].start();
			}
			for (int i = 0; i < movieImage.size(); i++) {
				mit[i].join();
			}
			// Write Story
			if (shortStory != null) {
				f = new FileWriter("C:\\Users\\thanh\\Desktop\\"
						+ movie.getId() + "\\Short_Des.txt");
				out1 = new BufferedWriter(f);

				out1.write(shortStory);
				out1.close();
			}
			if (fullStory != null) {
				f = new FileWriter("C:\\Users\\thanh\\Desktop\\"
						+ movie.getId() + "\\Long_Des.txt");
				out1 = new BufferedWriter(f);
				out1.write(fullStory);
				out1.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// save one movie image
	public void saveMovieImage(int i) throws MalformedURLException,
			IOException, FileNotFoundException {
		URL url = new URL(movieImage.get(i));
		InputStream in1 = new BufferedInputStream(url.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1 != (n = in1.read(buf))) {
			out.write(buf, 0, n);
		}
		out.close();

		byte[] response = out.toByteArray();

		FileOutputStream fos = new FileOutputStream(
				"C:\\Users\\thanh\\Desktop\\" + movie.getId() + "\\" + i
						+ ".jpg");
		fos.write(response);
		fos.close();
	}

	// get all person id
	public void getPersonIdFromIMDB() {
		String reg = "<h4 class=\"inline\">\\s*Director:\\s*</h4>\\s*<a.+?href=\"/name/nm(.+?)/\".+?itemprop=\"director\"|<td class=\"name\">\\s*<a.+?href=\"/name/nm(.+?)/\"\\s*>";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(response);
		Person tmp;
		while (m.find()) {
			tmp = new Person();
			if (m.group(1) != null) {
				tmp.setId(m.group(1));
				tmp.setIsdirector("1");
				tmp.setIsstar("0");
			} else {
				tmp.setId(m.group(2));
				tmp.setIsdirector("0");
				tmp.setIsstar("1");
			}
			person.add(tmp);
		}

	}

	// get all person detail
	public void getPersonDetailsFromIMDB() {
		URLConnection con = null;
		URL imdb;
		String text = "";
		PersonThread pt[] = new PersonThread[person.size()];
		for (int i = 0; i < person.size(); i++) {
			pt[i] = new PersonThread(i, this);
			pt[i].start();

		}
		for (int i = 0; i < person.size(); i++) {
			try {
				pt[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public ArrayList<Person> getPerson() {
		return person;
	}

	// get one person details
	public void getOnePersonDetailsFromIMDB(int i) {
		URLConnection con;
		URL imdb;
		String text;
		try {
			// get Response
			String url = "http://www.imdb.com/name/nm" + person.get(i).getId()
					+ "/";

			imdb = new URL(url);
			con = imdb.openConnection();
			con.setRequestProperty("User-Agent", "imdb");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			for (;;) {
				line = in.readLine();
				if (line == null)
					break;
				sb.append(line);
				sb.append(' ');
			}

			text = sb.toString();

			in.close();
			// Get detail
			String reg1 = "<div class=\"infobar\">.+?<a.+?itemprop=\"jobTitle\".+?>(.+?)</a>|<time itemprop=\"birthDate\" datetime=\"(.+?)\">|</time> in\\s*<a[^>]+>(.+?)</a>|<title>(.+?) - IMDb</title>";
			Pattern p = Pattern.compile(reg1);
			Matcher m = p.matcher(text);
			while (m.find()) {

				if (m.group(1) != null) {
					if (m.group(1).equals("Actor")) {
						person.get(i).setSex("Male");
					} else if (m.group(1).equals("Actress")) {
						person.get(i).setSex("Female");
					} else {
						person.get(i).setSex("No information");
					}
				}
				if (m.group(2) != null) {
					String time = m.group(2);
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date date;
					date = (Date) formatter.parse(time);
					person.get(i).setDay(
							date.getDate() + "-" + (date.getMonth() + 1));
					person.get(i).setYear(date.getYear() + 1900 + "");
				}
				if (m.group(3) != null) {
					person.get(i).setBorn(m.group(3));
				}
				if (m.group(4) != null) {
					person.get(i).setName(m.group(4));
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR ",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// set get method
	public String getFilmName() {
		return filmName;
	}

	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getInputStringFinal() {
		return inputStringFinal;
	}

	public void setInputStringFinal(String inputStringFinal) {
		this.inputStringFinal = inputStringFinal;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public ArrayList<Company> getCompany() {
		return company;
	}

	public ArrayList<Person> getPersonId() {
		return person;
	}

	public void setPersonId(ArrayList<Person> personId) {
		this.person = personId;
	}

	public void setCompany(ArrayList<Company> company) {
		this.company = company;
	}

	public ArrayList getGenre() {
		return genre;
	}

	public void setGenre(ArrayList genre) {
		this.genre = genre;
	}

	public String getShortStory() {
		return shortStory;
	}

	public void setShortStory(String shortStory) {
		this.shortStory = shortStory;
	}

	public String getFullStory() {
		return fullStory;
	}

	public void setFullStory(String fullStory) {
		this.fullStory = fullStory;
	}

	public ArrayList<String> getMovieImage() {
		return movieImage;
	}

	public void setMovieImage(ArrayList<String> movieImage) {
		this.movieImage = movieImage;
	}

	public static void main(String[] args) {
		ImdbCrawlerMovie cm = new ImdbCrawlerMovie("kung fu panda", 2008);
		Movie movie = new Movie();
		// String shortStory, fullStory;
		// ArrayList<Company> c = new ArrayList<Company>();
		// ArrayList<String> genre = new ArrayList<String>();
		// ArrayList<String> image = new ArrayList<String>();
		ArrayList<Person> person = new ArrayList<Person>();
		person = cm.getPerson();
		for (int i = 0; i < person.size(); i++) {
			System.out.println(person.get(i).toString());
		}
	}
}
