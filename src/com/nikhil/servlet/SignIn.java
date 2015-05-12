package com.nikhil.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nikhil.connection.ServiceProxy;
import com.nikhil.bean.Category;
import com.nikhil.bean.SignInBean;

/**
 * Servlet implementation class SignIn
 */
@SuppressWarnings("unused")
@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServiceProxy proxy=new ServiceProxy();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
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
		request.getRequestDispatcher("/view/signin.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String password = null , user = null;
		SignInBean result = null;
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		
		try{
		user = request.getParameter("signin_user");
		password = request.getParameter("password");
	
		result = proxy.signIn(user,password);
		System.out.println("query res in servlet --" + result);
		//String url = request.getContextPath();
		String nullString = "";
		
		if(!nullString.equals(result.getResult()) && result.getResult().equals("true")){
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("last_login",result.getLast_login());
			System.out.println("sign in privilege -" + result.getPrivilege());
			request.getSession().setAttribute("privilege",result.getPrivilege());
			String url=request.getContextPath()+"/userHome";
			response.sendRedirect(url);
			 
		}else{
			request.setAttribute("hidden", false);
			request.getRequestDispatcher("/view/signin.jsp").forward(request,response);
		}
		}catch(Exception e){
			request.getSession().setAttribute("errorMsg", "Unknown error. Please try again." );
			String url=request.getContextPath()+"/error";
			response.sendRedirect(url);
			e.printStackTrace();
		}
	}

}
