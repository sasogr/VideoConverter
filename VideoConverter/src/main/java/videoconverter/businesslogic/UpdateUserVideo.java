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
	private String linuxVideoName;
	private String videoDownloadFormat;
	private FolderStructureNaming folderNaming;
	private DownloadVideoNaming videoNaming;
	
	public UpdateUserVideo() {
		this.username = "";
		this.videoName = "";
		this.linuxVideoName = "";
		this.videoDownloadFormat = "";
		this.folderNaming = new FolderStructureNaming();
		this.videoNaming = new DownloadVideoNaming();
	}
	
	public void SetUsername(String _username) {
		this.username = _username;
	}
	
	public void SetVideoName(String _videoName) {
		this.videoName = _videoName;
		
		// Update the Linux video name
		String[] videoNameParts = this.videoName.split("\\s+");
		for(int i = 0; i < videoNameParts.length; i++) {
			if(i < videoNameParts.length - 1) {
				this.linuxVideoName += videoNameParts[i] + "\\ ";
			}
			else {
				this.linuxVideoName += videoNameParts[i];
			}
		}
	}
	
	public void SetVideoDownloadFormat(String _videoDownloadFormat) {
		this.videoDownloadFormat = _videoDownloadFormat;
	}
	
	public void UpdateVideoEntry() {
		Dbconfig dbconfig = new Dbconfig();
		
		// Declare the JDBC objects.
        Connection con = null;
        
        try {
        	// Establish the connection.
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbconfig.GetConnectionURL(), dbconfig.GetUsernameDB(), dbconfig.GetPasswordDB());
            
            String SQL = "UPDATE USER_VIDEOS SET videoUploaded = ?, videoName = ?, linuxVideoName = ? WHERE username = ?";
            
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, this.videoName);
            preparedStatement.setString(3, this.linuxVideoName);
            preparedStatement.setString(4, this.username);
            
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
	
	public void UpdateVideoDownloadFormat() {
		Dbconfig dbconfig = new Dbconfig();
		
		// Declare the JDBC objects.
        Connection con = null;
        
        try {
        	// Establish the connection.
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbconfig.GetConnectionURL(), dbconfig.GetUsernameDB(), dbconfig.GetPasswordDB());
            
            String SQL = "UPDATE USER_VIDEOS SET videoDownloadFormat = ? WHERE username = ?";
            
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, this.videoDownloadFormat);
            preparedStatement.setString(2, this.username);
            
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
		String pathToVideoUpload = this.folderNaming.GetGlobalPath() + this.username + this.folderNaming.GetPathUpload();
		String pathToVideoDownload = this.folderNaming.GetGlobalPath() + this.username + this.folderNaming.GetPathDownload() +
				this.videoNaming.GetDownloadVideoName();
		
		Dbconfig dbconfig = new Dbconfig();
		
		// Declare the JDBC objects.
        Connection con = null;
        ResultSet rs = null;
		
        try {
        	// Establish the connection.
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbconfig.GetConnectionURL(), dbconfig.GetUsernameDB(), dbconfig.GetPasswordDB());
            
            String SQL = "SELECT videoName, videoDownloadFormat " +
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
                String dbVideoDownloadFormat = rs.getString("videoDownloadFormat");
                
                if(dbVideoName != null && !dbVideoName.isEmpty()) {
                	// User has previous video already uploaded. Remove it.
                	// Add the video name to the path.
                	pathToVideoUpload += dbVideoName;
                	
                	// Delete previous uploaded video.
                	File uploadVideoToBeDeleted = new File(pathToVideoUpload);
                	if(uploadVideoToBeDeleted.exists()) {
                		uploadVideoToBeDeleted.delete();
                	}
                }
                
                if(dbVideoDownloadFormat != null && !dbVideoDownloadFormat.isEmpty()) {
                	pathToVideoDownload += dbVideoDownloadFormat;
                	
                	// Delete previous converted video.
                	File downloadVideoToBeDeleted = new File(pathToVideoDownload);
                	if(downloadVideoToBeDeleted.exists()) {
                		downloadVideoToBeDeleted.delete();
                	}
                }
            }
            
            // Update the entry again.
            SQL = "UPDATE USER_VIDEOS SET videoUploaded = ?, videoName = ?, linuxVideoName = ?, videoDownloadFormat = ? WHERE username = ?";
            
            preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, null);
            preparedStatement.setString(3, null);
            preparedStatement.setString(4, null);
            preparedStatement.setString(5, this.username);
            
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
	
	public void CheckAndDeleteExistingDownloadVideo() {
		String pathToVideoDownload = this.folderNaming.GetGlobalPath() + this.username + this.folderNaming.GetPathDownload() +
				this.videoNaming.GetDownloadVideoName();
		
		Dbconfig dbconfig = new Dbconfig();
		
		// Declare the JDBC objects.
        Connection con = null;
        ResultSet rs = null;
		
        try {
        	// Establish the connection.
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbconfig.GetConnectionURL(), dbconfig.GetUsernameDB(), dbconfig.GetPasswordDB());
            
            String SQL = "SELECT videoDownloadFormat " +
        				 "FROM USER_VIDEOS " +
        				 "WHERE username = ?";
            
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, this.username);
            
            rs = preparedStatement.executeQuery();
            
            // Check if we have any data in the result set.
            if(rs.isBeforeFirst()) {
                // There is data in the set.
            	rs.next();
                String dbVideoDownloadFormat = rs.getString("videoDownloadFormat");   
                
                if(dbVideoDownloadFormat != null && !dbVideoDownloadFormat.isEmpty()) {
                	pathToVideoDownload += dbVideoDownloadFormat;
                	
                	// Delete previous converted video.
                	File downloadVideoToBeDeleted = new File(pathToVideoDownload);
                	if(downloadVideoToBeDeleted.exists()) {
                		downloadVideoToBeDeleted.delete();
                	}
                }
            }
            
            // Update the entry again.
            SQL = "UPDATE USER_VIDEOS SET videoDownloadFormat = ? WHERE username = ?";
            
            preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, this.username);
            
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
	
	public String GetLinuxVideoNameUploaded() {
		String linuxVideoNameUploaded = "";
		
		Dbconfig dbconfig = new Dbconfig();
		
		// Declare the JDBC objects.
        Connection con = null;
        ResultSet rs = null;
		
		try {
			// Establish the connection.
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbconfig.GetConnectionURL(), dbconfig.GetUsernameDB(), dbconfig.GetPasswordDB());
            
            String SQL = "SELECT linuxVideoName " +
        				 "FROM USER_VIDEOS " +
        				 "WHERE username = ?";
            
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, this.username);
            
            rs = preparedStatement.executeQuery();
            
            // Check if we have any data in the result set.
            if(rs.isBeforeFirst()) {
                // There is data in the set.
            	rs.next();
                String dbVideoName = rs.getString("linuxVideoName");
                
                if(dbVideoName != null && !dbVideoName.isEmpty()) {
                	linuxVideoNameUploaded = dbVideoName;
                }
                else {
                	linuxVideoNameUploaded = "No video uploaded!";
                }
            }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return linuxVideoNameUploaded;
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
