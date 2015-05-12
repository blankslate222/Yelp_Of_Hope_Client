package com.nikhil.servlet;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nikhil.bean.ElementBean;
import com.nikhil.bean.ReviewBean;
import com.nikhil.connection.ServiceProxy;

/**
 * Servlet implementation class Review
 */
@WebServlet("/Review")
public class Review extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServiceProxy proxy=new ServiceProxy();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Review() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	System.out.println("Servlet path --"+ request.getServletPath());
		if(request.getServletPath().equalsIgnoreCase("/review/new")){
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
		handlePost(request,response);
	}

	private void displayList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
        ReviewBean[] rev = (ReviewBean[])proxy.getReview();
        //System.out.println("Categories --" + rev + "-- size --" + cat.length);
        request.setAttribute("array", rev);
        request.setAttribute("heading", "Reviews");
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
        
        request.getRequestDispatcher("/view/listreview.jsp").forward(request,response);
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
		
		 ElementBean[] ele = (ElementBean[])proxy.getElement();
	       // System.out.println("Categories --" + cat + "-- size --" + cat.length);
	        request.setAttribute("array", ele);
	        System.out.println("privilege -- "+request.getSession().getAttribute("privilege"));
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
		request.getRequestDispatcher("/view/createreview.jsp").forward(request,response);
		return;
	}
	
	private void handlePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		//HttpSession session = request.getSession();
		System.out.println("set sess user --" +(String) request.getSession().getAttribute("user") );
		ReviewBean rev = new ReviewBean();
		Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String cur_date = formatter.format(new Date());
		System.out.println("date --" + cur_date);
		rev.setElement(request.getParameter("business_name"));
		rev.setUser((String) request.getSession().getAttribute("user"));
		rev.setDate(cur_date);
		rev.setContent(request.getParameter("review_content"));
		rev.setRating(request.getParameter("review_rating"));
			
		String nullString = "";
		String result = proxy.newReview(rev);
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
