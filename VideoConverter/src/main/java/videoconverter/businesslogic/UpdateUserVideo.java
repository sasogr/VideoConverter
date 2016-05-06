package videoconverter.businesslogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import videoconverter.dbconfig.Dbconfig;

/**
 * Updates the user video entry in the database.
 * 
 * @author dejanstamenov
 *
 */
public class UpdateUserVideo {
	private String username;
	private String videoName;
	
	public UpdateUserVideo(String _username, String _videoName) {
		this.username = _username;
		this.videoName = _videoName;
	}
	
	public void UpdateVideoEntry() {
		Dbconfig dbconfig = new Dbconfig();
		
		// Declare the JDBC objects.
        Connection con = null;
        
        try {
        	// Establish the connection.
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbconfig.GetConnectionURL(), dbconfig.GetUsernameDB(), dbconfig.GetPasswordDB());
            
            String SQL = "UPDATE USER_VIDEOS SET videoUploaded = ?, videoName = ? WHERE username = ?";
            
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, this.videoName);
            preparedStatement.setString(3, this.username);
            
            preparedStatement.executeUpdate();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally {
        	try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
