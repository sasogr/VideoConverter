package videoconverter.ffmpegTests;

/*
 * AUTHOR: Danail Tavcioski
 * v1.0b
 */

import java.io.*;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;



public class BasicCommands {

	static final String testsDirRes = "test/resources";
	static final String serverTestsDir = "/home/videoconverter/tests";
	static final String testVideo = serverTestsDir+"/testVid.mp4";

	public static void main(String[] args) throws IOException, InterruptedException {
		File FFPROBEcommandsFile = new File(testsDirRes + "/ffprobe_commands.txt");
		File FFMPEGcommandsFile = new File(testsDirRes + "/generatedFFmpegCommands.txt");
		File outDir = new File(serverTestsDir+"/out");
		
		if (outDir.exists()){
			System.out.println("destination dir full, clearing!");
			outDir.delete();
			FileUtils.deleteDirectory(outDir);
			if (outDir.exists()){
				System.out.println("STILL EXISTS");
			} else
			outDir.mkdir();
		} else {
			System.out.println("CREATING OUT DIR");
			outDir.mkdir();
		}

		ArrayList<String> FFPROBEcommandsList = new ArrayList<String>();
		ArrayList<String> FFMPEGcommandsList = new ArrayList<String>();

		BufferedReader cmdReader = new BufferedReader(new FileReader(FFPROBEcommandsFile));

		String line = "";
		while ((line = cmdReader.readLine()) != null) {
			String command = line.split("#")[0];
			FFPROBEcommandsList.add(command);
		}
		cmdReader.close();

		GenerateFFmpegCommands generator = new GenerateFFmpegCommands(FFMPEGcommandsFile.getAbsolutePath());
		generator.generate();

		cmdReader = new BufferedReader(new FileReader(FFMPEGcommandsFile));

		line = "";
		while ((line = cmdReader.readLine()) != null) {
			FFMPEGcommandsList.add(line);
		}

		cmdReader.close();

		System.out.println("FFPROBE testing");
		float probeCount =0;
		for (String s : FFPROBEcommandsList) {
			probeCount++;
			String out = testFFPROBECommand(s, testVideo);
			System.out.println("result: " + out);
			System.out.println("progress: "+(int) (probeCount/FFPROBEcommandsList.size()*100)+"%");
		}

		System.out.println("FFMPEG testing");
		probeCount =0;
		for (String s : FFMPEGcommandsList) {
			probeCount++;
			String command = s.split("#")[0];
			String outputAppend = s.split("#")[1].trim();
			String fname = outDir+"/testVid" + outputAppend;
			String out = testFFMPEGCommand(command, testVideo, fname);
			System.out.println("result: " + out);
			System.out.println("progress: "+(int) (probeCount/FFMPEGcommandsList.size()*100)+ "%");

		}
	}

	public static String testFFPROBECommand(String command, String testVideo) throws IOException, InterruptedException {
		System.out.println("______________");
		command = command.replace("[video_name]", testVideo);
		command = command.replace("[number_of_stream]", "1");
		int res = 0;

		System.out.println("Testing: " + command);
		StringBuilder outNormal = new StringBuilder();
		StringBuilder outError = new StringBuilder();
		Process process = Runtime.getRuntime().exec(command);

		// System.out.println(System.getenv("PATH"));
		BufferedReader bf = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = null;

		while ((line = bf.readLine()) != null) {
			outNormal.append(line);
			outNormal.append("\n");
		}
		line = null;

		bf.close();
		bf = new BufferedReader(new InputStreamReader(process.getErrorStream()));

		while ((line = bf.readLine()) != null) {
			outError.append(line);
			outError.append("\n");
		}

		res = process.waitFor();

		if (res == 0)
			return "passed";
		return "failed";

		//
	}

	public static String testFFMPEGCommand(String command, String testVideo, String outputFile)
			throws IOException, InterruptedException {
		System.out.println("______________");
		command = command.replace("[input_video_name]", testVideo);
		command = command.replace("[output_file_name]", outputFile);
		int res = 0;

		System.out.println("Testing: " + command);

		// StringBuilder outNormal = new StringBuilder();
		// StringBuilder outError = new StringBuilder();
		Process process = Runtime.getRuntime().exec(command);

		BufferedReader bf = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = null;

		while ((line = bf.readLine()) != null) {
			System.out.println(line);
			// outNormal.append(line);
			// outNormal.append("\n");
		}
		line = null;

		bf.close();
		bf = new BufferedReader(new InputStreamReader(process.getErrorStream()));

		while ((line = bf.readLine()) != null) {
			// outError.append(line);
			// outError.append("\n");
		}
		
		res = process.waitFor();



		if (res == 0)
			return "passed";
		return "failed";

	}
}
