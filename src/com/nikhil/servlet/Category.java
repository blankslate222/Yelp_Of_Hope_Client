package com.nikhil.servlet;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nikhil.bean.CategoryBean;
import com.nikhil.bean.ElementBean;
import com.nikhil.connection.ServiceProxy;

/**
 * Servlet implementation class Category
 */
@SuppressWarnings("unused")
@WebServlet("/Category")
public class Category extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServiceProxy proxy=new ServiceProxy();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Category() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Servlet path --"+ request.getServletPath());
		System.out.println("context path --"+ request.getContextPath());
		if(request.getServletPath().equalsIgnoreCase("/category/new")){
				displayForm(request,response);
		}else{
			displayList(request,response);	
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		handlePost(request,response);
	}

	private void displayList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
        CategoryBean[] cat = (CategoryBean[])proxy.getCategory();
       // System.out.println("Categories --" + cat + "-- size --" + cat.length);
        request.setAttribute("array", cat);
        request.setAttribute("heading", "Categories available");
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
      String user = (String)request.getSession().getAttribute("user");
      
      if(user.trim().equals("Guest")){
    	  request.setAttribute("signinout", false);
      }else{
    	  request.setAttribute("signinout", true);
      }
        request.getRequestDispatcher("/view/listcategory.jsp").forward(request,response);
        return;
	}
	
	private void displayForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		
		if("Guest".equals(request.getSession().getAttribute("user")) || "".equals(request.getSession().getAttribute("user"))){
			
			request.getSession().setAttribute("errorMsg", "Forbidden request. You are not authorized. Please login and try again" );
			String url=request.getContextPath()+"/error";
			response.sendRedirect(url);
			return;
		}
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
	      String user = (String)request.getSession().getAttribute("user");
	      
	      if(user.trim().equals("Guest")){
	    	  request.setAttribute("signinout", false);
	      }else{
	    	  request.setAttribute("signinout", true);
	      }
		request.getRequestDispatcher("/view/createcategory.jsp").forward(request,response);
	
}
	private void handlePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		//HttpSession session = request.getSession();
		
		CategoryBean cat = new CategoryBean();
		cat.setName(request.getParameter("category_name"));
		cat.setDescription(request.getParameter("category_desc"));
		
		String nullString = "";
		String result = proxy.newCategory(cat);
		System.out.println("query res in servlet --" + result);
		String url = request.getContextPath();
		if(!nullString.equals(result) && result.equals("true")){
			url+="/category-list";
			response.sendRedirect(url);
		}else{
			request.getSession().setAttribute("errorMsg", result );
			url+="/error";
			response.sendRedirect(url);
		}
	}
}
