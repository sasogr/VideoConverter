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
	
	public boolean CreateUserFolders() throws Exception {
		boolean foldersCreated = false;
		
		File videoconverterDir = new File("/home/videoconverter/" + username + "/");
		File videoconverterUpload = new File("/home/videoconverter/" + username + "/upload/");
		File videoconverterDownload = new File("/home/videoconverter/" + username + "/download/");
		
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
			
			foldersCreated = true;
		}
		else {
			foldersCreated = true;
		}

		return foldersCreated;
	}
}
