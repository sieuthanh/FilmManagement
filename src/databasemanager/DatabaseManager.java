package databasemanager;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.LinkedList;
import java.util.Vector;

public class DatabaseManager {

	private Connection connection;
	private Statement statement;
	private ResultSet result = null;

	public DatabaseManager() {
		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String connectionUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=IMDB;user=sa;password=1";
			connection = DriverManager
					.getConnection(connectionUrl);
		} catch (SQLException ex) {
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (connection != null) {
			try {
				statement = connection.createStatement();
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	public boolean CheckConnection() {
		if (connection == null)
			return false;
		return true;
	}

	public LinkedList getRandomMovie() {
		LinkedList get = new LinkedList();
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT * FROM film ORDER BY RANDOM() LIMIT 10");
				while (result.next()) {
					Movie x = new Movie(result.getString(1),
							result.getString(2), result.getInt(3),
							result.getString(4), result.getFloat(5),
							result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	// get database's data from film table
	public LinkedList getMovieList() {
		LinkedList get = new LinkedList();
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT * FROM film ORDER BY name");
				while (result.next()) {
					Movie x = new Movie(result.getString(1),
							result.getString(2), result.getInt(3),
							result.getString(4), result.getFloat(5),
							result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public LinkedList getPersonList() {
		LinkedList get = new LinkedList();
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT * FROM person ORDER BY name");
				while (result.next()) {
					Person x = new Person(result.getString(1),
							result.getString(2), result.getString(3),
							result.getString(4), result.getString(5),
							result.getString(6), result.getString(7),
							result.getString(8));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public LinkedList getMovieListByOrder(String type, int t) {
		String getodertype = null;
		if (t == 0)
			getodertype = "ASC";
		else
			getodertype = "DESC";
		LinkedList get = new LinkedList();
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT id, name, year, country, rating, runtime FROM film ORDER BY "
								+ type + " " + getodertype);
				while (result.next()) {
					Movie x = new Movie(result.getString(1),
							result.getString(2), result.getInt(3),
							result.getString(4), result.getFloat(5),
							result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public LinkedList getMovieGenres(String fid) {
		LinkedList genre = new LinkedList();
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT genre FROM filmgenre WHERE fid = '"
								+ fid + "'");
				while (result.next()) {
					genre.add(result.getString(1));
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return genre;
	}

	public Movie getMovieinfo(String fid) {
		Movie x = null;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT * FROM film WHERE id = '" + fid
								+ "'");
				result.next();
				x = new Movie(result.getString(1), result.getString(2),
						result.getInt(3), result.getString(4),
						result.getFloat(5), result.getInt(6));
				return x;
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return x;
	}

	public String getMovieName(String fid) {
		String get = null;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT name FROM film WHERE id = '"
								+ fid + "'");
				while (result.next()) {
					get = result.getString(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public String getMovieCountry(String fid) {
		String get = null;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT country FROM film WHERE id = '"
								+ fid + "'");
				while (result.next()) {
					get = result.getString(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public String getMovieYear(String fid) {
		String get = null;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT year FROM film WHERE id = '"
								+ fid + "'");
				while (result.next()) {
					get = result.getString(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public float getMovieRating(String fid) {
		float get = 0;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT rating FROM film WHERE id = '"
								+ fid + "'");
				while (result.next()) {
					get = result.getFloat(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public int getMovieRuntime(String fid) {
		int get = 0;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT runtime FROM film WHERE id = '"
								+ fid + "'");
				while (result.next()) {
					get = result.getInt(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	// Get person info to class person
	public Person getPersonInfo(String pid) {
		Person x = null;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT * FROM person WHERE id = '" + pid
								+ "'");
				while (result.next()) {
					x = new Person(result.getString(1), result.getString(2),
							result.getString(3), result.getString(4),
							result.getString(5), result.getString(6),
							result.getString(7), result.getString(8));
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return x;
	}

	// get database's data from person table
	public String getPersonName(String pid) {
		String get = null;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT name FROM person WHERE id = '"
								+ pid + "'");
				while (result.next()) {
					get = result.getString(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public String getPersonSex(String pid) {
		String get = null;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT sex FROM person WHERE id = '"
								+ pid + "'");
				while (result.next()) {
					get = result.getString(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public String getPersonBornPlace(String pid) {
		String get = null;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT born FROM person WHERE id = '"
								+ pid + "'");
				while (result.next()) {
					get = result.getString(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public String getPersonBirthday(String pid) {
		String get = null;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT day FROM person WHERE id = '"
								+ pid + "'");
				while (result.next()) {
					get = result.getString(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public String getPersonYear(String pid) {
		String get = null;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT year FROM person WHERE id = '"
								+ pid + "'");
				while (result.next()) {
					get = result.getString(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public int getPersonIsDirector(String pid) {
		int get = 0;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT isdirector FROM person WHERE id = '"
								+ pid + "'");
				while (result.next()) {
					get = result.getInt(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public int getPersonIsStar(String pid) {
		int get = 0;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT isstar FROM person WHERE id = '"
								+ pid + "'");
				while (result.next()) {
					get = result.getInt(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	// Get company info for id
	public Company getCompanyInfo(String cid) {
		Company x = null;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT * FROM company WHERE id = '"
								+ cid + "'");
				while (result.next()) {
					x = new Company(result.getString(1), result.getString(2));
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return x;
	}

	// get company name
	public String getCompanyName(String cid) {
		String get = null;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT name FROM company WHERE id = '"
								+ cid + "'");
				while (result.next()) {
					get = result.getString(1);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	// get list
	public LinkedList getStarByMovie(String fid) {
		LinkedList get = new LinkedList();
		Person x;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT person.* FROM filmstar,person "
								+ "WHERE fid = '" + fid + "' AND sid = id");
				while (result.next()) {
					x = new Person(result.getString(1), result.getString(2),
							result.getString(3), result.getString(4),
							result.getString(5), result.getString(6),
							result.getString(7), result.getString(8));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public LinkedList getStarByMovie2(String fid) {
		LinkedList get = new LinkedList();
		Person x;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT person.* FROM filmstar,person "
								+ "WHERE fid = '" + fid + "' AND sid = id");
				while (result.next()) {
					x = new Person(result.getString(2));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}
	
	public LinkedList getDirectorByMovie(String fid) {
		LinkedList get = new LinkedList();
		Person x;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT person.* FROM filmdirector,person "
								+ "WHERE fid = '" + fid + "' AND did = id");
				while (result.next()) {
					x = new Person(result.getString(1), result.getString(2),
							result.getString(3), result.getString(4),
							result.getString(5), result.getString(6),
							result.getString(7), result.getString(8));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}
	
	public LinkedList getDirectorByMovie2(String fid) {
		LinkedList get = new LinkedList();
		Person x;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT person.* FROM filmdirector,person "
								+ "WHERE fid = '" + fid + "' AND did = id");
				while (result.next()) {
					x = new Person(result.getString(2));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public LinkedList getCompanyByMovie(String fid) {
		LinkedList get = new LinkedList();
		Company x;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT company.* FROM filmcompany,company "
								+ "WHERE fid = '" + fid + "' AND sid = id");
				while (result.next()) {
					x = new Company(result.getString(1), result.getString(2));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}
	
	public LinkedList getCompanyByMovie2(String fid) {
		LinkedList get = new LinkedList();
		Company x;
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT company.* FROM filmcompany,company "
								+ "WHERE fid = '" + fid + "' AND sid = id");
				while (result.next()) {
					x = new Company(result.getString(2));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public LinkedList getMovieByStar(String sid) {
		LinkedList get = new LinkedList();
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT film.* FROM film,filmstar WHERE id = fid AND sid = '"
								+ sid + "' ORDER BY year");
				while (result.next()) {
					Movie x = new Movie(result.getString(1),
							result.getString(2), result.getInt(3),
							result.getString(4), result.getFloat(5),
							result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public LinkedList getMovieByCompany(String cid) {
		LinkedList get = new LinkedList();
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT film.* FROM film,filmcompany WHERE id = fid AND sid = '"
								+ cid + "' ORDER BY year");
				while (result.next()) {
					Movie x = new Movie(result.getString(1),
							result.getString(2), result.getInt(3),
							result.getString(4), result.getFloat(5),
							result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public LinkedList getMovieByDirector(String did) {
		LinkedList get = new LinkedList();
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT film.* FROM film,filmdirector WHERE id = fid AND did = '"
								+ did + "' ORDER BY year");
				while (result.next()) {
					Movie x = new Movie(result.getString(1),
							result.getString(2), result.getInt(3),
							result.getString(4), result.getFloat(5),
							result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	// for list film tab
	public LinkedList getMovieByFirstCharacter(char c) {
		Movie x = null;
		LinkedList get = new LinkedList();
		if (connection != null) {
			try {
				if (c == '*') {
					result = statement
							.executeQuery("SELECT * FROM film WHERE name LIKE '0%' OR name LIKE '1%' OR name LIKE '2%' OR name LIKE '3%' OR name LIKE '4%' OR name LIKE '5%' OR name LIKE '6%' OR name LIKE '7%' OR name LIKE '8%' OR name LIKE '9%' OR name LIKE '(%'");
				} else {
					result = statement
							.executeQuery("SELECT * FROM film WHERE name LIKE '"
									+ c
									+ "%' OR name LIKE '"
									+ Character.toUpperCase(c) + "%'");
				}
				while (result.next()) {
					x = new Movie(result.getString(1), result.getString(2),
							result.getInt(3), result.getString(4),
							result.getFloat(5), result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	// Search
	public String getSearchCommand(String s) {
		String t = null;
		int i;
		while (s.indexOf("  ") != -1) {
			s = s.replaceAll("  ", " ");
		}
		if (s.length() > 0) {
			if (s.charAt(0) == ' ') {
				t = "";
				for (i = 0; i < s.length() - 1; i++)
					t += s.charAt(i + 1);
				s = t;
			}
			if (s.charAt(s.length() - 1) == ' ') {
				t = "";
				for (i = 0; i < s.length() - 1; i++)
					t += s.charAt(i);
				s = t;
			}
		}
		s = s.toLowerCase();
		return s;
	}

	public LinkedList SimpleMovieSearch(String s) {
		s = getSearchCommand(s);
		LinkedList get = new LinkedList();
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT id, name, year, country, rating, runtime FROM film WHERE LOWER(name) = '"
								+ s + "'");
				while (result.next()) {
					Movie x = new Movie(result.getString(1),
							result.getString(2), result.getInt(3),
							result.getString(4), result.getFloat(5),
							result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}

			try {
				result = statement
						.executeQuery("SELECT id, name, year, country, rating, runtime FROM film WHERE LOWER(name) LIKE '"
								+ s
								+ " %' OR LOWER(name) LIKE '% "
								+ s
								+ "' OR LOWER(name) LIKE '% " + s + " %'");
				while (result.next()) {
					Movie x = new Movie(result.getString(1),
							result.getString(2), result.getInt(3),
							result.getString(4), result.getFloat(5),
							result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}

			try {
				result = statement
						.executeQuery("SELECT id, name, year, country, rating, runtime FROM film WHERE LOWER(name) LIKE '%"
								+ s
								+ "%' EXCEPT SELECT id, name, year, country, rating, runtime FROM film WHERE LOWER(name) LIKE '"
								+ s
								+ " %' OR LOWER(name) LIKE '% "
								+ s
								+ "' OR LOWER(name) LIKE '% "
								+ s
								+ " %' OR LOWER(name) = '" + s + "'");
				while (result.next()) {
					Movie x = new Movie(result.getString(1),
							result.getString(2), result.getInt(3),
							result.getString(4), result.getFloat(5),
							result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		}
		return get;
	}

	public LinkedList SimplePersonSearch(String s) {
		s = getSearchCommand(s);
		LinkedList get = new LinkedList();
		if (connection != null) {
			try {
				result = statement
						.executeQuery("SELECT id, name, sex, day, year, born, isdirector, isstar FROM person WHERE LOWER(name) = '"
								+ s + "'");
				while (result.next()) {
					Person x = new Person(result.getString(1),
							result.getString(2), result.getString(3),
							result.getString(4), result.getString(5),
							result.getString(6), result.getString(7),
							result.getString(8));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}

			try {
				result = statement
						.executeQuery("SELECT id, name, sex, day, year, born, isdirector, isstar FROM person WHERE LOWER(name) LIKE '"
								+ s
								+ " %' OR LOWER(name) LIKE '% "
								+ s
								+ "' OR LOWER(name) LIKE '% " + s + " %'");
				while (result.next()) {
					Person x = new Person(result.getString(1),
							result.getString(2), result.getString(3),
							result.getString(4), result.getString(5),
							result.getString(6), result.getString(7),
							result.getString(8));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}

			try {
				result = statement
						.executeQuery("SELECT id, name, sex, day, year, born, isdirector, isstar FROM person WHERE LOWER(name) LIKE '%"
								+ s
								+ "%' EXCEPT SELECT id, name, sex, day, year, born, isdirector, isstar FROM person WHERE LOWER(name) LIKE '"
								+ s
								+ " %' OR LOWER(name) LIKE '% "
								+ s
								+ "' OR LOWER(name) LIKE '% "
								+ s
								+ " %' OR LOWER(name) = '" + s + "'");
				while (result.next()) {
					Person x = new Person(result.getString(1),
							result.getString(2), result.getString(3),
							result.getString(4), result.getString(5),
							result.getString(6), result.getString(7),
							result.getString(8));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public LinkedList AdvancePersonSearch(String s, String sex,
			String yearfrom, String yearto, int role) {
		s = getSearchCommand(s);
		String convertRole = null;
		String convertYear = null;
		if (yearfrom == "1850" && yearto == "2006")
			convertYear = "";
		else
			convertYear = " AND (year BETWEEN '" + yearfrom + "' AND '"
					+ yearto + "')";
		switch (role) {
		case 0:
			convertRole = " AND isstar = '1' AND isdirector = '1'";
			break;
		case 1:
			convertRole = " AND isstar = '1'";
			break;
		case 2:
			convertRole = " AND isdirector = '1'";
			break;
		case 3:
			convertRole = "";
			break;
		}
		LinkedList get = new LinkedList();
		if (connection != null) {
			try {
				// System.out.println("SELECT id, name, sex, day, year, born, isdirector, isstar FROM person WHERE LOWER(name) = '"+s+"' AND sex LIKE '%"+sex+"%'"+convertYear+" AND "+convertRole);
				result = statement
						.executeQuery("SELECT id, name, sex, day, year, born, isdirector, isstar FROM person WHERE LOWER(name) = '"
								+ s
								+ "' AND sex LIKE '%"
								+ sex
								+ "%'"
								+ convertYear + "" + convertRole);
				while (result.next()) {
					Person x = new Person(result.getString(1),
							result.getString(2), result.getString(3),
							result.getString(4), result.getString(5),
							result.getString(6), result.getString(7),
							result.getString(8));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}

			try {
				result = statement
						.executeQuery("SELECT id, name, sex, day, year, born, isdirector, isstar FROM person WHERE (LOWER(name) LIKE '"
								+ s
								+ " %' OR LOWER(name) LIKE '% "
								+ s
								+ "' OR LOWER(name) LIKE '% "
								+ s
								+ " %') AND sex LIKE '%"
								+ sex
								+ "%'"
								+ convertYear + "" + convertRole);
				// System.out.println("SELECT id, name, sex, day, year, born, isdirector, isstar FROM person WHERE (LOWER(name) LIKE '"+s+" %' OR LOWER(name) LIKE '% "+s+"' OR LOWER(name) LIKE '% "+s+" %') AND sex LIKE '%"+sex+"%'"+convertYear+" AND "+convertRole);
				while (result.next()) {
					Person x = new Person(result.getString(1),
							result.getString(2), result.getString(3),
							result.getString(4), result.getString(5),
							result.getString(6), result.getString(7),
							result.getString(8));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}

			try {
				result = statement
						.executeQuery("(SELECT id, name, sex, day, year, born, isdirector, isstar FROM person WHERE (LOWER(name) LIKE '%"
								+ s
								+ "%') AND sex LIKE '%"
								+ sex
								+ "%'"
								+ convertYear
								+ ""
								+ convertRole
								+ ")"
								+ " EXCEPT "
								+ "(SELECT id, name, sex, day, year, born, isdirector, isstar FROM person WHERE (LOWER(name) LIKE '"
								+ s
								+ " %' OR LOWER(name) LIKE '% "
								+ s
								+ "' OR LOWER(name) LIKE '% "
								+ s
								+ " %' OR LOWER(name) = '"
								+ s
								+ "') AND sex LIKE '%"
								+ sex
								+ "%'"
								+ convertYear + "" + convertRole + ")");
				while (result.next()) {
					Person x = new Person(result.getString(1),
							result.getString(2), result.getString(3),
							result.getString(4), result.getString(5),
							result.getString(6), result.getString(7),
							result.getString(8));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	public LinkedList AdvanceMovieSearch(String s, LinkedList genre,
			String yearfrom, String yearto, float ratingfrom, float ratingto,
			String country, int runtimefrom, int runtimeto) {
		s = getSearchCommand(s);
		String convertGenre = " AND id in (";
		for (int i = 0; i < genre.size(); i++) {
			if (i == 0)
				convertGenre += "SELECT fid FROM filmgenre WHERE genre='"
						+ (String) genre.get(i) + "'";
			else
				convertGenre += " INTERSECT SELECT fid FROM filmgenre WHERE genre='"
						+ (String) genre.get(i) + "'";
		}
		if (convertGenre == " AND id in (")
			convertGenre = "";
		else
			convertGenre += ")";
		String convertYear = "";
		if (yearfrom != "1900" || yearto != "2010") {
			convertYear = " AND year BETWEEN '" + yearfrom + "' AND '" + yearto
					+ "' ";
		}
		String convertRating = " AND rating BETWEEN " + ratingfrom + " AND "
				+ ratingto;
		String convertCountry = "";
		if (country != "Never mind")
			convertCountry = " AND country = '" + country + "'";
		String convertRuntime = " AND runtime BETWEEN " + runtimefrom + " AND "
				+ runtimeto;
		LinkedList get = new LinkedList();
		if (connection != null) {
			try {
				// System.out.println("SELECT id, name, year, country, rating, runtime FROM film WHERE LOWER(name) = '"+s+"'"+convertYear+convertGenre);
				result = statement
						.executeQuery("SELECT id, name, year, country, rating, runtime FROM film WHERE LOWER(name) = '"
								+ s
								+ "'"
								+ convertRating
								+ convertYear
								+ convertGenre
								+ convertCountry
								+ convertRuntime);
				while (result.next()) {
					Movie x = new Movie(result.getString(1),
							result.getString(2), result.getInt(3),
							result.getString(4), result.getFloat(5),
							result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
			try {
				// System.out.println("SELECT id, name, year, country, rating, runtime FROM film WHERE (LOWER(name) LIKE '"+s+" %' OR LOWER(name) LIKE '% "+s+"' OR LOWER(name) LIKE '% "+s+" %')"+convertRating+convertYear+convertGenre+convertCountry);
				result = statement
						.executeQuery("SELECT id, name, year, country, rating, runtime FROM film WHERE (LOWER(name) LIKE '"
								+ s
								+ " %' OR LOWER(name) LIKE '% "
								+ s
								+ "' OR LOWER(name) LIKE '% "
								+ s
								+ " %')"
								+ convertRating
								+ convertYear
								+ convertGenre
								+ convertCountry + convertRuntime);
				while (result.next()) {
					Movie x = new Movie(result.getString(1),
							result.getString(2), result.getInt(3),
							result.getString(4), result.getFloat(5),
							result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
			try {
				// System.out.println("(SELECT id, name, year, country, rating, runtime FROM film WHERE LOWER(name) LIKE '%"+s+"%')"+convertRating+convertYear+convertGenre+convertCountry+" EXCEPT "+" (SELECT id, name, year, country, rating, runtime FROM film WHERE LOWER(name) LIKE '"+s+" %' OR LOWER(name) LIKE '% "+s+"' OR LOWER(name) LIKE '% "+s+" %' OR LOWER(name) = '"+s+"') "+convertRating+convertYear+convertGenre+convertCountry);
				result = statement
						.executeQuery("SELECT id, name, year, country, rating, runtime FROM film WHERE LOWER(name) LIKE '%"
								+ s
								+ "%'"
								+ convertRating
								+ convertYear
								+ convertGenre
								+ convertCountry
								+ convertRuntime
								+ " EXCEPT "
								+ " SELECT id, name, year, country, rating, runtime FROM film WHERE (LOWER(name) LIKE '"
								+ s
								+ " %' OR LOWER(name) LIKE '% "
								+ s
								+ "' OR LOWER(name) LIKE '% "
								+ s
								+ " %' OR LOWER(name) = '"
								+ s
								+ "') "
								+ convertRating
								+ convertYear
								+ convertGenre
								+ convertCountry + convertRuntime);
				while (result.next()) {
					Movie x = new Movie(result.getString(1),
							result.getString(2), result.getInt(3),
							result.getString(4), result.getFloat(5),
							result.getInt(6));
					get.add(x);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return get;
	}

	// Update new rating
	public void updateRating(String id, float newrating) {
		if (connection != null) {
			try {
				statement.executeUpdate("UPDATE film SET rating=" + newrating
						+ " WHERE id = '" + id + "'");
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}
}
