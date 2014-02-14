package ma.ensat.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author imad BOUHAMIDI (imad.bouhamidi@gmail.com)
 *
 */

public class ConnectionMysql {
	private static String DATABASE = "ecole";
	private static String HOST = "localhost";
	private static String PORT = "3306";
	private static String LOGIN = "root";
	private static String PASSWORD = "";
	
	private static String urlConnection = null;
	private Connection connection = null;

	private static ConnectionMysql instance = null;

	private ConnectionMysql() throws ClassNotFoundException, SQLException {
		urlConnection = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
				+ "?user=" + LOGIN + "&password=" + PASSWORD;
		// chargement du jdbc mysql
		Class.forName("com.mysql.jdbc.Driver");
		// creation de la connection vers la base de donn�es
		connection = DriverManager.getConnection(urlConnection);
	}

	private ConnectionMysql(String url) throws ClassNotFoundException,
			SQLException {
		urlConnection = url;
		// chargement du jdbc mysql
		Class.forName("com.mysql.jdbc.Driver");
		// creation de la connection vers la base de donn�es
		connection = DriverManager.getConnection(urlConnection);
	}

	/**
	 * Singleton il retourne une seule connection valable pour tous les acces a
	 * la base de donn�es
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static ConnectionMysql getInstance() throws ClassNotFoundException,
			SQLException {
		if (instance == null) {
			instance = new ConnectionMysql();
			return instance;
		}
		return instance;
	}

	public static ConnectionMysql getInstance(String url) throws ClassNotFoundException,
			SQLException {
		if (instance == null) {
			instance = new ConnectionMysql(url);
			return instance;
		}
		return instance;
	}

	public Connection getConnection() {
		return connection;
	}

}
