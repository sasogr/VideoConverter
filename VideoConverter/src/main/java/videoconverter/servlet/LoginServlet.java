package videoconverter.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import videoconverter.UserServiceImpl.UserServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie [] cookies = request.getCookies();
		boolean flag = false;
		if(cookies != null){
			for(Cookie cookie:cookies){
				if(cookie.getName().equals("username"))
				{
					flag = true;
					request.getRequestDispatcher("index.jsp").forward(request, response);
					
				}
				
			}
			if(!flag){
				request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 response.setContentType("application/json");
	        response.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int result = 0;
        JSONArray resp = null;
		try {
			
			result = UserServiceImpl.check(username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       // response.getWriter().write(resp.toString());
		if(result == 1){
			Cookie cookie = new Cookie("username", username);
			response.addCookie(cookie);
			request.getRequestDispatcher("index.jsp").forward(request,response);
		}else{
			request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
		}
	}

}
