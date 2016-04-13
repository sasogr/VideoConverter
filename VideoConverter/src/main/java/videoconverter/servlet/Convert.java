package videoconverter.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		//if(sessionUser == null) {
			// User is not logged in.
			
			// TODO: Change into the proper sign in controller when it is created.
			//response.sendRedirect("Signin");
		//}
		//else {
			// User is logged in.
			
			//if(sessionUser.GetVideoUploaded() == false) {
				// Video has not been uploaded.
				
				// TODO: Change into proper upload controller when it is created.
				//response.sendRedirect("Upload");
			//}
			//else {
				// Video has been uploaded.
				
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/convert.jsp");
		        rd.forward(request, response);
			//}

		//}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// POST request not needed into the /Convert servlet.
		doGet(request, response);
	}

}
