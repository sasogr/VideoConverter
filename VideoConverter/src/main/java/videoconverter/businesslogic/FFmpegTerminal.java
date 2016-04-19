package videoconverter.businesslogic;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FFmpegTerminal {
	private String terminalCommand;
	private StringBuilder executionOutput;
	private String executionOutputAsString;
	
	public FFmpegTerminal() {
		this.terminalCommand = "";
		this.executionOutput = new StringBuilder();
		this.executionOutputAsString = "";
	}
	
	public String ExecuteCommand() {
		try {
			ProcessBuilder pb = new ProcessBuilder(this.terminalCommand);
			Process process = pb.start();
			
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
	
	public void SetTerminalCommand(String _command) {
		this.terminalCommand = _command;
	}
}
