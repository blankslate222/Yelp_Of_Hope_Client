package com.nikhil.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nikhil.bean.CategoryBean;
import com.nikhil.bean.ElementBean;
import com.nikhil.bean.ReviewBean;
import com.nikhil.connection.ServiceProxy;

/**
 * Servlet implementation class Home
 */
@SuppressWarnings("unused")
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServiceProxy proxy=new ServiceProxy();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String user=null;
    	CategoryBean[] cb = null;
    	ReviewBean[] rb = null;
    //	ElementBean[] eb = null;
    	//response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		
		try{
			
			cb = proxy.getCategory();
		//	eb = proxy.getElement();
			rb = proxy.getReview();
			
			request.setAttribute("category", cb);
		//	request.setAttribute("business", eb);
			request.setAttribute("review", rb);
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
	      user = (String)request.getSession().getAttribute("user");
	      
	      if(user.trim().equals("Guest")){
	    	  request.setAttribute("signinout", false);
	      }else{
	    	  request.setAttribute("signinout", true);
	      }
	      System.out.println(request.getContextPath());
	      
		request.getRequestDispatcher("/view/home.jsp").forward(request, response);
		return;
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
