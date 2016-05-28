package videoconverter.businesslogic;

/**
 * Holds the name of the converted video for downloading.
 * 
 * @author dejanstamenov
 *
 */
public class DownloadVideoNaming {
	private String downloadVideoName = "";
	
	public DownloadVideoNaming() {
		this.downloadVideoName = "converted.";
	}
	
	public String GetDownloadVideoName() {
		return this.downloadVideoName;
	}
}
