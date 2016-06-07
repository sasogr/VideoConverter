package videoconverter.ffmpegTests;

/*
 * AUTHOR: Danail Tavcioski
 * v1.0b
 */

import java.io.*;

public class GenerateFFmpegCommands {
	String outputCommandFile;

	public GenerateFFmpegCommands(String s) {
		this.outputCommandFile = s;
	}

	public static void main(String[] args) throws IOException {

	}

	public void generate() throws IOException {
		File outFile = new File(this.outputCommandFile);
		outFile.createNewFile();
		File arguments = new File("test/resources/ffmpeg_arguments.txt");
		String[] videoArguments = null;
		String[] audioArguments = null;
		String[] resolutionArguments = null;
		BufferedReader bf = null;

		try {
			bf = new BufferedReader(new FileReader(arguments));
			String line = "";
			while ((line = bf.readLine()) != null) {
				if (line.split(" ")[0].equals("video")) {
					videoArguments = line.split(": ")[1].split(",");

				}
				if (line.split(" ")[0].equals("audio")) {
					audioArguments = line.split(": ")[1].split(",");

				}
				if (line.split(" ")[0].equals("res")) {
					resolutionArguments = line.split(": ")[1].split(",");

				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//		for (String s : videoArguments) {
//			System.out.println(s);
//		}
//		for (String s : audioArguments) {
//			System.out.println(s);
//		}
//		for (String s : resolutionArguments) {
//			System.out.println(s);
//		}

		PrintWriter pw = new PrintWriter(outFile);

		String command = "ffmpeg -i [input_video_name] -vf scale=[res] -c:v [video_codec] -c:a [audio_codec] [output_file_name]";
		for (String vid : videoArguments) {
			for (String aud : audioArguments) {
				for (String res : resolutionArguments) {
					command = "ffmpeg -i [input_video_name] -vf scale=[res] -c:v [video_codec] -c:a [audio_codec] [output_file_name]";
					command = command.replace("[video_codec]", vid);
					command = command.replace("[audio_codec]", aud);
					if (res.equals("original")) {
						command = command.replace("-vf scale=[res] ", "");
					} else {
						command = command.replace("[res]", res + ":-1");
					}
					String appendToName = "_" + vid + "_" + aud + "_" + res + ".mp4";

					pw.println(command+"# "+appendToName);
				}
			}
		}
		pw.close();

	}
}
