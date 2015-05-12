package com.nikhil.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nikhil.bean.CategoryBean;
import com.nikhil.bean.ElementBean;
import com.nikhil.connection.ServiceProxy;

/**
 * Servlet implementation class Update
 */
@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServiceProxy proxy = new ServiceProxy();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getServletPath().equalsIgnoreCase("/update/category")){
			displayCategoryForm(request,response);
		}
		else{
			displayElementForm(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getServletPath().equalsIgnoreCase("/update/category")){
			handleCategoryUpdate(request,response);
		}
		else{
			handleElementUpdate(request,response);
		}
		
	}

	private void displayCategoryForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyCol = null, keyVal = null;
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		
		if("Guest".equals(request.getSession().getAttribute("user")) || "".equals(request.getSession().getAttribute("user"))){
			
			request.getSession().setAttribute("errorMsg", "Forbidden request. You are not authorized. Please login and try again" );
			String url=request.getContextPath()+"/error";
			response.sendRedirect(url);
			return;
		}
		keyVal = (String) request.getParameter("category_id");
		keyCol = "category_id";
		CategoryBean[] category = proxy.getCategoryDetails(keyCol, keyVal);
		 
	      String user = (String)request.getSession().getAttribute("user");
	      
	      if(user.trim().equals("Guest")){
	    	  request.setAttribute("signinout", false);
	      }else{
	    	  request.setAttribute("signinout", true);
	      }
	      request.setAttribute("category",category);
		request.getRequestDispatcher("/view/updatecategory.jsp").forward(request,response);
	}
	
	private void displayElementForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyCol = null, keyVal = null;
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		
		if("Guest".equals(request.getSession().getAttribute("user")) || "".equals(request.getSession().getAttribute("user"))){
			
			request.getSession().setAttribute("errorMsg", "Forbidden request. You are not authorized. Please login and try again" );
			String url=request.getContextPath()+"/error";
			response.sendRedirect(url);
			return;
		}
		keyVal = (String) request.getParameter("business_id");
		keyCol = "element_id";
		CategoryBean[] category = proxy.getCategory();
		 request.setAttribute("array", category);
		 
		 ElementBean[] ele = proxy.getElementDetails(keyCol, keyVal);
		 request.setAttribute("business", ele);
	      String user = (String)request.getSession().getAttribute("user");
	      
	      if(user.trim().equals("Guest")){
	    	  request.setAttribute("signinout", false);
	      }else{
	    	  request.setAttribute("signinout", true);
	      }
	      request.setAttribute("category",category);
		request.getRequestDispatcher("/view/updateelement.jsp").forward(request,response);	
	}
	
	private void handleCategoryUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		//HttpSession session = request.getSession();
		
		CategoryBean cat = new CategoryBean();
		cat.setName(request.getParameter("category_name"));
		cat.setDescription(request.getParameter("category_desc"));
		
		String nullString = "";
		String idVal = (String) request.getParameter("category_id");
		String result = proxy.updateCategory(idVal, cat);
		
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
	
	private void handleElementUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		String idVal = (String) request.getParameter("business_id");
		String result = proxy.updateElement(idVal, ele);
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
