package videoconverter.model;

/**
 * Holds data about the user into session.
 * 
 * @author dejanstamenov
 */
public class SessionUser {
	private String username;
	private boolean videoUploaded;
	
	public SessionUser() {
		// By default, video is not uploaded.
		this.videoUploaded = false;
	}
	
	public void SetUsername(String _username) {
		this.username = _username;
	}
	
	public String GetUsername() {
		return this.username;
	}
	
	public void SetVideoUploaded(boolean _videoUploaded) {
		this.videoUploaded = _videoUploaded;
	}
	
	public boolean GetVideoUploaded() {
		return this.videoUploaded;
	}
}
