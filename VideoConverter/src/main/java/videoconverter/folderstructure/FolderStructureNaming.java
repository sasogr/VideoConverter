package videoconverter.folderstructure;

/**
 * Holds the global folder path, along with upload and download path.
 * 
 * @author dejanstamenov
 *
 */
public class FolderStructureNaming {
	private String globalPath = "/home/videoconverter/";
	private String pathUpload = "/upload/";
	private String pathDownload = "/download/";
	private String pathSlash = "/";
	
	public FolderStructureNaming() {
		// Empty constructor.
	}
	
	public String GetGlobalPath() {
		return this.globalPath;
	}
	
	public String GetPathUpload() {
		return this.pathUpload;
	}
	
	public String GetPathDownload() {
		return this.pathDownload;
	}
	
	public String GetPathSlash() {
		return this.pathSlash;
	}
}
