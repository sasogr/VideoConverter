package videoconverter.uploaddownload;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

import videoconverter.businesslogic.UpdateUserVideo;
import videoconverter.dbconfig.Dbconfig;
import videoconverter.folderstructure.FolderStructureNaming;
import videoconverter.model.SessionUser;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 *50, // 50MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FolderStructureNaming folderNaming = new FolderStructureNaming();

	static final Dbconfig dbconfig = new Dbconfig();
	static final String USER = dbconfig.GetUsernameDB();
	private boolean validFormat=true;

	/**
	 * handles file upload
	 * @throws IOException 
	 * @throws ServletException 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(true);
		SessionUser sessionUser = (SessionUser)session.getAttribute("sessionUser");
		
		if(sessionUser == null) {
			// Redirect the user to the login page.
			response.sendRedirect("LoginServlet");
		}
		else {
			request.getRequestDispatcher("/jsp/upload.jsp").forward(request, response);
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		SessionUser sessionUser = (SessionUser)session.getAttribute("sessionUser");
		
		if(sessionUser == null) {
			// Redirect the user to the login page.
			response.sendRedirect("LoginServlet");
		}
		else {
			String savePath = folderNaming.GetGlobalPath() + sessionUser.GetUsername() + folderNaming.GetPathUpload();
			// Contains the whole video name, including the extension.
			String fullVideoName = "";
			
			UpdateUserVideo updateUserVideo = new UpdateUserVideo();
			updateUserVideo.SetUsername(sessionUser.GetUsername());
			
			updateUserVideo.CheckAndDeleteExistingVideo();
			
			try {
				for (Part part : request.getParts()) {
					String fileName = extractFileName(part);
					String extension = FilenameUtils.getExtension(fileName);
					if (extension.equals("mp4") || extension.equals("flv") || extension.equals("mkv") || extension.equals("3gp")
							|| extension.equals("wmv")  ) {

						part.write(savePath + File.separator + fileName);
						fullVideoName += fileName;
					}
					else {
						validFormat=false;
						throw new FileNotSupportedException();
					}
					
				}
				
				updateUserVideo.SetVideoName(fullVideoName);
				// Update video entry in the videos table for the user.
				updateUserVideo.UpdateVideoEntry();
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}

			if(validFormat)
			{
				request.setAttribute("message", "Upload has been done successfully!");
				sessionUser.SetVideoUploaded(true);
			}
			else
			{
				request.setAttribute("message", "Not valid video format! Supported formats: mp4, flv, mkv, 3gp, wmv.");
			}
			
			request.getRequestDispatcher("/jsp/message.jsp").forward(request, response);
		}

	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}
}