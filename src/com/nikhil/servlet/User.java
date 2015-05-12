package com.nikhil.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nikhil.bean.ReviewBean;
import com.nikhil.bean.UserBean;
import com.nikhil.connection.ServiceProxy;

/**
 * Servlet implementation class User
 */
@WebServlet("/User")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServiceProxy proxy=new ServiceProxy(); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	String url=null, user=null, keyCol = null;
    	UserBean[] ub = null;
    	ReviewBean[] rb = null;
    	response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		
		try{
		user = (String) request.getSession().getAttribute("user");
		keyCol = "review_user";
		user.trim();
		if("Guest".equals(user)){
			url=request.getContextPath()+"/home";
			response.sendRedirect(url);
			return;
		}
		
		ub = proxy.getUserDetails(user);
		
		rb = proxy.getUserReview(keyCol, user);
		
		request.setAttribute("user", ub);
		request.setAttribute("array", rb);
		
		  System.out.println("privilege -- "+request.getSession().getAttribute("privilege"));
	        String privs =(String) request.getSession().getAttribute("privilege");
	        int priv = Integer.parseInt(privs);
	        if(priv == 0){
	        //	System.out.println("inside if");
	        request.setAttribute("hidden", true);
	        } else{
	        	//System.out.println("inside else");
	        	request.setAttribute("hidden", false);
	        }
	      
	      if(user.trim().equals("Guest")){
	    	  request.setAttribute("signinout", false);
	      }else{
	    	  request.setAttribute("signinout", true);
	      }
	    
	     request.getRequestDispatcher("/view/userhome.jsp").forward(request,response);
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
	
}
