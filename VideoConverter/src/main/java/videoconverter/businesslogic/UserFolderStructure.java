package videoconverter.businesslogic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}
