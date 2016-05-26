package videoconverter.businesslogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import videoconverter.folderstructure.FolderStructureNaming;

public class FFmpegTerminal {
	private String username;
	private String terminalCommand;
	private StringBuilder executionOutput;
	private String executionOutputAsString;
	
	public FFmpegTerminal() {
		this.username = "";
		this.terminalCommand = "";
		this.executionOutput = new StringBuilder();
		this.executionOutputAsString = "";
	}
	
	public String ExecuteCommand() {
		try {
			UpdateCommandWithPath();
			
			//ProcessBuilder pb = new ProcessBuilder(this.terminalCommand);
			//Process process = pb.start();
			Process process = Runtime.getRuntime().exec(this.terminalCommand);
			
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			
			while ( (line = inputReader.readLine()) != null) {
				this.executionOutput.append(line);
				this.executionOutput.append(System.getProperty("line.separator"));
			}
			
			line = null;
			
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			
			while ( (line = errorReader.readLine()) != null) {
				this.executionOutput.append(line);
				this.executionOutput.append(System.getProperty("line.separator"));
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			this.executionOutputAsString = this.executionOutput.toString();
		}
		
		return this.executionOutputAsString;
	}
	
	public void UpdateCommandWithPath() {
		UpdateUserVideo userVideo = new UpdateUserVideo();
		userVideo.SetUsername(this.username);
		String uploadedVideoName = userVideo.GetVideoNameUploaded();
		
		FolderStructureNaming folderNaming = new FolderStructureNaming();
		String videoUploadLocation = folderNaming.GetGlobalPath() + this.username + folderNaming.GetPathUpload() + uploadedVideoName;
		String videoDownloadLocation = folderNaming.GetGlobalPath() + this.username + folderNaming.GetPathDownload() + "converted_" + uploadedVideoName;
		
		
		if(this.terminalCommand.contains("ffprobe")) {
			this.terminalCommand += videoUploadLocation;
		}
		else if(this.terminalCommand.contains("ffmpeg")) {
			this.terminalCommand = new StringBuilder(this.terminalCommand).insert(7, "-i " + videoUploadLocation + " ").toString();
			this.terminalCommand += videoDownloadLocation;
			
			//System.err.println("FFMPEG TEST:" + this.terminalCommand);
		}
	}
	
	public void SetTerminalCommand(String _command) {
		this.terminalCommand = _command;
	}
	
	public void SetUsername(String _username) {
		this.username = _username;
	}
}
