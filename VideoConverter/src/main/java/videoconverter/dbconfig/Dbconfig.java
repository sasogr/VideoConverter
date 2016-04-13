package videoconverter.dbconfig;

/**
 * Holds MySQL connection information.
 * 
 * @author dejanstamenov
 *
 */

public class Dbconfig {
	private final String connectionURL;
	private final String usernameDB;
	private final String passwordDB;
	
	/**
	 * Sets the DB connection parameters.
	 */
	public Dbconfig() {
		this.connectionURL = "jdbc:mysql://127.0.0.1:3306/videoconverter";
		this.usernameDB = "root";
		this.passwordDB = "root";
	}
	
	/**
	 * Return the connection URL.
	 * 
	 * @return a string containing connection URL.
	 */
	public String GetConnectionURL() {
		return this.connectionURL;
	}
	
	/**
	 * Return the username for logging in the database.
	 * 
	 * @return a string containing the username for logging.
	 */
	public String GetUsernameDB() {
		return this.usernameDB;
	}
	
	/**
	 * Return the password for logging in the database.
	 * 
	 * @return a string containing the password for logging.
	 */
	public String GetPasswordDB() {
		return this.passwordDB;
	}
	
}
