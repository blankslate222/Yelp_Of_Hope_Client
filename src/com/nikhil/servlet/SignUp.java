package com.nikhil.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nikhil.bean.UserBean;
import com.nikhil.connection.ServiceProxy;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServiceProxy proxy=new ServiceProxy();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		request.setAttribute("hidden", true);
		request.setAttribute("error", "");
		request.getRequestDispatcher("/view/signup.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		//HttpSession session = request.getSession();
		
		UserBean usr = new UserBean();
		
		usr.setEmail(request.getParameter("user_email_id"));
		usr.setPassword(request.getParameter("user_password"));
		usr.setLast_name(request.getParameter("user_last_name"));
		usr.setFirst_name(request.getParameter("user_first_name"));
		usr.setCity(request.getParameter("user_city"));
		
		String nullString = "";
		String result = proxy.newUser(usr);
		System.out.println("query res in servlet --" + result);
		String url = request.getContextPath();
		if(!nullString.equals(result) && result.equals("true")){
			url+="/";
			response.sendRedirect(url);
		}else{
			request.setAttribute("hidden", false);
			request.setAttribute("error", result);
			request.getRequestDispatcher("/view/signup.jsp").forward(request,response);
		}
	}

}
