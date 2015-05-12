package com.nikhil.servlet;

import java.io.IOException;
import java.text.DecimalFormat;

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
 * Servlet implementation class Detail
 */
@WebServlet("/Detail")
public class Detail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       ServiceProxy proxy = new ServiceProxy();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Detail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Servlet path --"+ request.getServletPath());
		String param = request.getQueryString();
		
		System.out.println("before equals --" + param.split("=")[0]);
		System.out.println("after equals -- " + param.split("=")[1]);
		
		if(request.getServletPath().equalsIgnoreCase("/detail/business")){
			System.out.println("element detail called");
			elementDetail(request,response);
		}else{
			System.out.println("category detail called");
			System.out.println();
			categoryDetail(request,response);	
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void elementDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String keyCol = null, keyVal = null, query = null;
		double avg_rating=0;
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		
		try{
			query = request.getQueryString();
					
			if(query.split("=")[0].trim().equals("id")){
				keyCol = "element_id";	
				keyVal = request.getParameter("id");
			}else{
				keyCol = "element_name";
				keyVal = request.getParameter("name");
			}
			
			ElementBean[] ele = (ElementBean[])proxy.getElementDetails(keyCol,keyVal);
	      //  System.out.println("ele-- size --" + ele.length);
	        request.setAttribute("business", ele);
	        
	        keyCol = "review_ele";
	        keyVal = ele[0].getName();
	        ReviewBean[] rev = (ReviewBean[])proxy.getUserReview(keyCol, keyVal);
	       // System.out.println("--rev size --" + rev.length);
	        request.setAttribute("array", rev);
	        int revLength = 0; 
	        try{
	        	int rating = 0;
	        	revLength = rev.length;
	        	for(ReviewBean r : rev){
	        		rating += Integer.parseInt(r.getRating());
	        	}
	        	avg_rating = (double)rating/revLength;
	       
	        }catch(NullPointerException e){
	        	avg_rating = 0;
	        }
	        
        	DecimalFormat df = new DecimalFormat("#.#");
        	request.setAttribute("avg_rating", df.format(avg_rating));
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
	        request.getRequestDispatcher("/view/detailselement.jsp").forward(request,response);
	        return;
		}catch(Exception e){
			e.printStackTrace();
		}
           
	}
	
	private void categoryDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyCol = null, keyVal = null;
		response.setContentType("text/html");
		proxy.setEndpoint("http://localhost:8080/Yelp_Of_Hope/services/Service");
		
		try{		
		
		keyVal = request.getParameter("id");
		keyCol = "category_id";
        CategoryBean[] cat = (CategoryBean[])proxy.getCategoryDetails(keyCol, keyVal);
       // System.out.println("Categories --" + cat + "-- size --" + cat.length);
        request.setAttribute("category", cat);
        
        keyCol = "element_category";
        keyVal = cat[0].getName();
        ElementBean[] ele = (ElementBean[])proxy.getElementDetails(keyCol,keyVal);
        request.setAttribute("business", ele);
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
        
        request.getRequestDispatcher("/view/detailscategory.jsp").forward(request,response);
        return;
        
	}catch(Exception e){
		e.printStackTrace();
	}
  }
}
