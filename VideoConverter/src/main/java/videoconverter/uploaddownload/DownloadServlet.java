package videoconverter.uploaddownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import videoconverter.businesslogic.DownloadVideoNaming;
import videoconverter.businesslogic.UpdateUserVideo;
import videoconverter.dbconfig.Dbconfig;
import videoconverter.folderstructure.FolderStructureNaming;
import videoconverter.model.SessionUser;

@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FolderStructureNaming folderNaming = new FolderStructureNaming();
	private DownloadVideoNaming videoNaming = new DownloadVideoNaming();
	private UpdateUserVideo updateVideo = new UpdateUserVideo();
 
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(true);
		SessionUser sessionUser = (SessionUser)session.getAttribute("sessionUser");
		
		if(sessionUser == null) {
			// User is not logged in.
			response.sendRedirect("LoginServlet");
		}
		else {
			updateVideo.SetUsername(sessionUser.GetUsername());
			String videoFormat = updateVideo.GetDownloadVideoFormat();
			
			if(videoFormat.equals("No format!")) {
				// Send the user to upload and convert a video.
				response.sendRedirect("UploadServlet");
			}
			else {
				try {
					String downloadFilePath = this.folderNaming.GetGlobalPath() + sessionUser.GetUsername() + this.folderNaming.GetPathDownload() +
							videoNaming.GetDownloadVideoName() + videoFormat;
					
					File downloadFile = new File(downloadFilePath);
			        FileInputStream inStream = new FileInputStream(downloadFile);
			        
			        // Forces download
			        String headerKey = "Content-Disposition";
			        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			        response.setHeader(headerKey, headerValue);
			        
			        // Obtain response's output stream
			        OutputStream outStream = response.getOutputStream();
			         
			        byte[] buffer = new byte[4096];
			        int bytesRead = -1;
			         
			        while ((bytesRead = inStream.read(buffer)) != -1) {
			            outStream.write(buffer, 0, bytesRead);
			        }
			        
			        inStream.close();
			        outStream.close();
				}
				catch(Exception e) {
					e.printStackTrace();
					response.sendRedirect("UploadServlet");
				}
			}
		}
    }
}