package videoconverter.businesslogic;

import java.io.File;


/**
 * Creates the /home/videoconverter/{username}/upload and /home/videoconverter/{username}/download folders for storing videos.
 * 
 * @author dejanstamenov
 *
 */
public class UserFolderStructure {
	private String username;
	
	public UserFolderStructure(String _username) {
		this.username = _username;
	}
	
	/**
	 * Creates user folder structure.
	 */
	public void CreateUserFolders() {
		File videoconverterDir = new File("/home/videoconverter/" + this.username + "/");
		File videoconverterUpload = new File("/home/videoconverter/" + this.username + "/upload/");
		File videoconverterDownload = new File("/home/videoconverter/" + this.username + "/download/");
		
		try {
			if(!videoconverterDir.exists()) {
				// Directory does not exist, create it.
				videoconverterDir.mkdir();
				
				if(!videoconverterUpload.exists()) {
					// Create upload directory.
					videoconverterUpload.mkdir();
				}
				
				if(!videoconverterDownload.exists()) {
					// Create download directory.
					videoconverterDownload.mkdir();
				}
			}
			else {
				// Directory does exist. Check if sub-directories exist. If they don't, create them.
				
				if(!videoconverterUpload.exists()) {
					// Create upload directory.
					videoconverterUpload.mkdir();
				}
				
				if(!videoconverterDownload.exists()) {
					// Create download directory.
					videoconverterDownload.mkdir();
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Checks the user folder structure, i.e. if all the folders exist.
	 * 
	 * @return boolean value, true - folder structure exists; false - otherwise.
	 */
	public boolean CheckUserFolders() {
		boolean userStructureExists = false;
		
		File videoconverterDir = new File("/home/videoconverter/" + this.username + "/");
		File videoconverterUpload = new File("/home/videoconverter/" + this.username + "/upload/");
		File videoconverterDownload = new File("/home/videoconverter/" + this.username + "/download/");
		
		if(videoconverterDir.exists() && videoconverterUpload.exists() && videoconverterDownload.exists()) {
			// All the folders exist.
			userStructureExists = true;
		}
		else {
			// Problem with the structure.
			userStructureExists = false;
		}
	
		return userStructureExists;
	}
}
