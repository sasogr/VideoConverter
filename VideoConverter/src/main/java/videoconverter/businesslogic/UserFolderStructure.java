package videoconverter.businesslogic;

import java.io.File;

import videoconverter.folderstructure.FolderStructureNaming;


/**
 * Creates the /home/videoconverter/{username}/upload and /home/videoconverter/{username}/download folders for storing videos.
 * 
 * @author dejanstamenov
 *
 */
public class UserFolderStructure {
	private String username;
	private FolderStructureNaming folderNaming;
	
	public UserFolderStructure(String _username) {
		this.username = _username;
		this.folderNaming =  new FolderStructureNaming();
	}
	
	/**
	 * Creates user folder structure.
	 */
	public void CreateUserFolders() {
		File videoconverterDir = new File(this.folderNaming.GetGlobalPath() + this.username + this.folderNaming.GetPathSlash());
		File videoconverterUpload = new File(this.folderNaming.GetGlobalPath() + this.username + this.folderNaming.GetPathUpload());
		File videoconverterDownload = new File(this.folderNaming.GetGlobalPath() + this.username + this.folderNaming.GetPathDownload());
		
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
		
		File videoconverterDir = new File(this.folderNaming.GetGlobalPath() + this.username + this.folderNaming.GetPathSlash());
		File videoconverterUpload = new File(this.folderNaming.GetGlobalPath() + this.username + this.folderNaming.GetPathUpload());
		File videoconverterDownload = new File(this.folderNaming.GetGlobalPath() + this.username + this.folderNaming.GetPathDownload());
		
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
