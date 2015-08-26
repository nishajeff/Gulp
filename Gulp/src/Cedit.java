
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.io.IOException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Cedit")
public class Cedit extends HttpServlet {
	private static final long serialVersionUID = 1L;
    String message = "";   
    Connection conn = null;
    ResultSet rs = null;
    
    public Cedit() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
try{
			HttpSession session = request.getSession(true);
			int uid =Integer.parseInt((String) session.getAttribute("userid"));
	
			int rate = 0;
			String r = request.getParameter("rating");			
			String review =  request.getParameter("review");			
			int rid = Integer.parseInt(request.getParameter("id"));
			if (!(r.equals("")))
			{
				rate = Integer.parseInt(r);
			}
			  String url= "jdbc:oracle:thin:testuser/password@localhost"; 
			  Class.forName("oracle.jdbc.driver.OracleDriver");
	        //properties for creating connection to Oracle database
	        Properties props = new Properties();
	        props.setProperty("user", "testdb");
	        props.setProperty("password", "password");
	        conn = DriverManager.getConnection(url,props);
	        //creating connection to Oracle database using JDBC              
	        Statement s=conn.createStatement();
	        String sql = "";
	        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	      if (!r.equals("")  && !review.equals("") )
	    	 sql = "update review set review ='" + review + "', rating = " + rate + ", rdate = TO_DATE(sysdate) where rid = " + rid + "and user_id =" +uid;
	      else if (!review.equals(""))
	    	  sql = "update review set review ='" + review + "', rdate = TO_DATE(sysdate) where rid = " + rid + "and user_id =" +uid;
	      else if (!r.equals("") ) 
	    	  sql = "update review set rating = " + rate + ", rdate = TO_DATE(sysdate) where rid = " + rid + "and user_id =" +uid;
	     
	      s.executeQuery(sql);
	      sql = "select * from review where rid = " + rid + "and user_id =" +uid;
	      rs = s.executeQuery(sql);
	      
	      message += "<h4> Review/Rating edited! </h4>";
	      while(rs.next()){
	    	  
	    	  String text = df.format(rs.getDate("rdate"));
	      message += "Review: " + rs.getString("review") + "<br> Rating: " + rs.getInt("rating") + "<br> Date: " + text;
	      message+="<form action=\"review.jsp\">"+
	  	        "<label>&nbsp;</label>"+
	  	        "<input type=\"submit\" value=\"Go Back to Review\" id=\"submit\">"+
	  	        "</form>";
	      }
	      request.setAttribute("message", message);
 		getServletContext().getRequestDispatcher("/output.jsp").forward(request, response);
		}catch(Exception e){e.printStackTrace();
			System.out.println(e.getMessage());

		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
