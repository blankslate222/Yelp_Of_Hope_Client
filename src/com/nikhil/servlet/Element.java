package com.nikhil.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nikhil.bean.CategoryBean;
import com.nikhil.bean.ElementBean;
import com.nikhil.connection.ServiceProxy;

/**
 * Servlet implementation class Element
 */
@SuppressWarnings("unused")
@WebServlet("/Element")
public class Element extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServiceProxy proxy=new ServiceProxy();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Element() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	System.out.println("Servlet path --"+ request.getServletPath());
		if(request.getServletPath().equalsIgnoreCase("/business/new")){
			System.out.println("new form called");
				displayForm(request,response);
		}else{
			displayList(request,response);	
			System.out.println("list called");
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("inside post");
		handlePost(request,response);
	}

	private void displayList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
           
        ElementBean[] ele = (ElementBean[])proxy.getElement();
      //  System.out.println("Categories --" + ele + "-- size --" + ele.length);
        request.setAttribute("array", ele);
        request.setAttribute("heading", "Businesses available");
        
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
        
        request.getRequestDispatcher("/view/listelement.jsp").forward(request,response);
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
		
		CategoryBean[] cat = (CategoryBean[])proxy.getCategory();
	       // System.out.println("Categories --" + cat + "-- size --" + cat.length);
	        request.setAttribute("array", cat);
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
		request.getRequestDispatcher("/view/createelement.jsp").forward(request,response);
		return;
	}
	
	private void handlePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		//HttpSession session = request.getSession();
		
		ElementBean ele = new ElementBean();
		ele.setAddress(request.getParameter("business_address"));
		ele.setCategory(request.getParameter("business_category"));
		ele.setContact(request.getParameter("business_contact"));
		ele.setDescription(request.getParameter("business_desc"));
		ele.setName(request.getParameter("business_name"));
		ele.setCity(request.getParameter("business_city"));
		
		String nullString = "";
		String result = proxy.newElement(ele);
		System.out.println("query res in servlet --" + result);
		String url = request.getContextPath();
		if(!nullString.equals(result) && result.equals("true")){
			url+="/business-list";
			response.sendRedirect(url);
		}else{
			request.getSession().setAttribute("errorMsg", result );
			url+="/error";
			response.sendRedirect(url);
		}
	}
}
