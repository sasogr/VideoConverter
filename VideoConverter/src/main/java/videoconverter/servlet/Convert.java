package videoconverter.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import videoconverter.businesslogic.UpdateUserVideo;
import videoconverter.model.SessionUser;

/**
 * Provides the main FFmpeg convert functionality. 
 * 
 * @author dejanstamenov
 */
@WebServlet("/Convert")
public class Convert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Convert() {
        super();
    }

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		return "Provides the main FFmpeg convert functionality."; 
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		SessionUser sessionUser = (SessionUser)session.getAttribute("sessionUser");
		
		if(sessionUser == null) {
			// User is not logged in.
			response.sendRedirect("LoginServlet");
		}
		else {
			// User is logged in.
			UpdateUserVideo userVideo = new UpdateUserVideo();
			userVideo.SetUsername(sessionUser.GetUsername());
			
			boolean userVideoUploaded = userVideo.CheckVideoUploaded();
			if(userVideoUploaded == false) {
				// Video has not been uploaded.
				response.sendRedirect("UploadServlet");
			}
			else {
				// Video has been uploaded.
				
				// Get the name of the uploaded video.
				String uploadedVideoName = userVideo.GetVideoNameUploaded();
				
				request.setAttribute("uploadedVideoName", uploadedVideoName);
				request.getRequestDispatcher("/jsp/convert.jsp").forward(request, response);
			}

		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// POST request not needed into the /Convert servlet.
		doGet(request, response);
	}

}
