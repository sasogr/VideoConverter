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

import videoconverter.dbconfig.Dbconfig;
import videoconverter.model.SessionUser;
@WebServlet("/DownloadServlet")
 
public class DownloadServlet extends HttpServlet {
	

	static final Dbconfig dbconfig = new Dbconfig();
	static final String USER = dbconfig.GetUsernameDB();
	private static final String SAVE_DIR = USER+"/download";
	private SessionUser user=new SessionUser();
 
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // reads input file from an absolute path
        String filePath = SAVE_DIR;//"D:/DownloadTest/test.jpg";
        File downloadFile = new File(filePath);
        FileInputStream inStream = new FileInputStream(downloadFile);
        if(user.GetVideoUploaded())
        {
         
        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);
         
        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();
         
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
         
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
       
         
        inStream.close();
        outStream.close();  
        downloadFile.delete();
        
        
        }
        else
        {
        	request.setAttribute("message", "No video has been uploaded!");
        	getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
		}
        }
    }