package com.nikhil.servlet;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nikhil.connection.ServiceProxy;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       ServiceProxy proxy = new ServiceProxy();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getServletPath().equalsIgnoreCase("/delete/category")){
			deleteCategory(request,response);
		}
		else{
			deleteElement(request,response);
		}
	}

	private void deleteElement(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		String keyVal = request.getParameter("business_id");
		try {
			String result = proxy.deleteElement(keyVal);
			String url = request.getContextPath();
			if(!"".equals(result) && "true".equals(result)){
				url+="/business-list";
				response.sendRedirect(url);
			}else{
				request.getSession().setAttribute("errorMsg", result );
				url+="/error";
				response.sendRedirect(url);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteCategory(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		String keyVal = request.getParameter("category_id");
		try {
			String result = proxy.deleteCategory(keyVal);
			String url = request.getContextPath();
			if(!"".equals(result) && "true".equals(result)){
				url+="/category-list";
				response.sendRedirect(url);
			}else{
				request.getSession().setAttribute("errorMsg", result );
				url+="/error";
				response.sendRedirect(url);
				
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception ie){
			ie.printStackTrace();
		}
		
	}

}
