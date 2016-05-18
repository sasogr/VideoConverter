package videoconverter.businesslogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.File;

import videoconverter.dbconfig.Dbconfig;
import videoconverter.folderstructure.FolderStructureNaming;

/**
 * Updates the user video entry in the database.
 * Defines functionality to check for existing uploaded video and deletes the video.
 * 
 * @author dejanstamenov
 *
 */
public class UpdateUserVideo {
	private String username;
	private String videoName;
	private FolderStructureNaming folderNaming;
	
	public UpdateUserVideo() {
		this.username = "";
		this.videoName = "";
		this.folderNaming = new FolderStructureNaming();
	}
	
	public void SetUsername(String _username) {
		this.username = _username;
	}
	
	public void SetVideoName(String _videoName) {
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
	
	public void CheckAndDeleteExistingVideo() {
		String pathToVideo = this.folderNaming.GetGlobalPath() + this.username + this.folderNaming.GetPathUpload();
		
		Dbconfig dbconfig = new Dbconfig();
		
		// Declare the JDBC objects.
        Connection con = null;
        ResultSet rs = null;
		
        try {
        	// Establish the connection.
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbconfig.GetConnectionURL(), dbconfig.GetUsernameDB(), dbconfig.GetPasswordDB());
            
            String SQL = "SELECT videoName " +
        				 "FROM USER_VIDEOS " +
        				 "WHERE username = ?";
            
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, this.username);
            
            rs = preparedStatement.executeQuery();
            
            // Check if we have any data in the result set.
            if(rs.isBeforeFirst()) {
                // There is data in the set.
            	rs.next();
                String dbVideoName = rs.getString("videoName");
                
                if(dbVideoName != null && !dbVideoName.isEmpty()) {
                	// User has previous video already uploaded. Remove it.
                	// Add the video name to the path.
                	pathToVideo += dbVideoName;
                	
                	File videoToBeDeleted = new File(pathToVideo);
                	videoToBeDeleted.delete();
                }
            }
            
            // Update the entry again.
            SQL = "UPDATE USER_VIDEOS SET videoUploaded = ?, videoName = ? WHERE username = ?";
            
            preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, null);
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
	
	public String GetVideoNameUploaded() {
		String videoNameUploaded = "";
		
		Dbconfig dbconfig = new Dbconfig();
		
		// Declare the JDBC objects.
        Connection con = null;
        ResultSet rs = null;
		
		try {
			// Establish the connection.
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbconfig.GetConnectionURL(), dbconfig.GetUsernameDB(), dbconfig.GetPasswordDB());
            
            String SQL = "SELECT videoName " +
        				 "FROM USER_VIDEOS " +
        				 "WHERE username = ?";
            
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, this.username);
            
            rs = preparedStatement.executeQuery();
            
            // Check if we have any data in the result set.
            if(rs.isBeforeFirst()) {
                // There is data in the set.
            	rs.next();
                String dbVideoName = rs.getString("videoName");
                
                if(dbVideoName != null && !dbVideoName.isEmpty()) {
                	videoNameUploaded = dbVideoName;
                }
                else {
                	videoNameUploaded = "No video uploaded!";
                }
            }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return videoNameUploaded;
	}
	
	public boolean CheckVideoUploaded() {
		boolean videoUploaded = false;
		
		Dbconfig dbconfig = new Dbconfig();
		
		// Declare the JDBC objects.
        Connection con = null;
        ResultSet rs = null;
		
		try {
			// Establish the connection.
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbconfig.GetConnectionURL(), dbconfig.GetUsernameDB(), dbconfig.GetPasswordDB());
            
            String SQL = "SELECT videoUploaded " +
        				 "FROM USER_VIDEOS " +
        				 "WHERE username = ?";
            
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, this.username);
            
            rs = preparedStatement.executeQuery();
            
            // Check if we have any data in the result set.
            if(rs.isBeforeFirst()) {
                // There is data in the set.
            	rs.next();
                int dbVideoUploaded = rs.getInt("videoUploaded");
                
                if(dbVideoUploaded == 1) {
                	videoUploaded = true;
                }
            }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return videoUploaded;
	}
	
}
