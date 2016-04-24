package videoconverter.uploaddownload;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

import videoconverter.dbconfig.Dbconfig;
import videoconverter.model.SessionUser;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 *50, // 50MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadServlet extends HttpServlet {

	/**
	 * Name of the directory where uploaded files will be saved, relative to the
	 * web application directory.
	 */
	

	static final Dbconfig dbconfig = new Dbconfig();
	static final String USER = dbconfig.GetUsernameDB();
	private static final String SAVE_DIR = USER+"/upload";
	private boolean validFormat=true;
	private SessionUser user=new SessionUser();

	/**
	 * handles file upload
	 * @throws IOException 
	 * @throws ServletException 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/jsp/upload.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// gets absolute path of the web application
		String appPath = request.getServletContext().getRealPath("");//zavisi kako vi e patot na serverot, jas so ovoj rabotev
		// constructs path of the directory to save uploaded file
		String savePath = appPath + File.separator + SAVE_DIR;
		
		for (Part part : request.getParts()) {
			String fileName = extractFileName(part);
			String extension = FilenameUtils.getExtension(fileName);
			if (extension.equals("mp4") || extension.equals("flv") || extension.equals("mkv") || extension.equals("3gp")
					|| extension.equals("wmv")  ) {

				part.write(savePath + File.separator + fileName);
			}
			else
				validFormat=false;
			throw new FileNotSupportedException();
			
		}
		
		if(validFormat)
		{
		request.setAttribute("message", "Upload has been done successfully!");
		user.SetVideoUploaded(true);
		}
		else
		{
			request.setAttribute("message", "Not valid video format!");
		}
		getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
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