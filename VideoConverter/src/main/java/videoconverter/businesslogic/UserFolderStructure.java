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
	
	public boolean CreateUserFolders() {
		boolean foldersCreated = false;
		
		// Exception: java.nio.file.AccessDeniedException
		Path path = Paths.get("/home/videoconverter/" + this.username + "/");

		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			}
			catch(IOException io) {
				io.printStackTrace();
			}
		}
		
		// NOT WORKING - NO EXCEPTION THROWN AT ALL
		/*File videoconverterDir = new File("/home/videoconverter/" + this.username + "/");
		File videoconverterUpload = new File("/home/videoconverter/" + this.username + "/upload/");
		File videoconverterDownload = new File("/home/videoconverter/" + this.username + "/download/");
		
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
		}*/

		return foldersCreated;
	}
}
