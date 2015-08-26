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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Pedit")
public class Pedit extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connection conn = null;
    ResultSet rs = null;
    String message = "";
	
    public Pedit() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			message="";
			int zipcode = 0;
			HttpSession session = request.getSession(true);
			int uid =Integer.parseInt((String) session.getAttribute("userid"));
			String name=request.getParameter("name");
			String email =  request.getParameter("email");
			String zip = request.getParameter("zipcode");
			System.out.println("Name =("+name+")");
			System.out.println("Email =("+email+")");
			System.out.println("Zip=("+zip+")");
			if (!(zip.equals("")))
			{
				zipcode = Integer.parseInt(zip);
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
	        
	      if (!name.equals("")  && !email.equals("") && !zip.equals("") )
	    	 sql = "update rcustomer set user_name ='" + name + "', email = '" + email + "', zipcode = " + zipcode + "where user_id = " + uid;
	      else if (!name.equals("")  && !email.equals(""))
	    	  sql = "update rcustomer set user_name ='" + name + "', email = '" + email + "'" + "where user_id = " + uid;
	      else if (!name.equals("") && !zip.equals("")) 
	    	  sql = "update rcustomer set user_name ='" + name + "', zipcode = " + zipcode + "where user_id = " + uid;
	      else if (!email.equals("") && !zip.equals(""))
	    	  sql = "update rcustomer set email = '" + email + "', zipcode = " + zipcode + "where user_id = " + uid;
	      else if (!name.equals("") )
	    	  sql = "update rcustomer set user_name ='" + name + "' where user_id = " + uid;
	      else if ( !email.equals(""))
	    	  sql = "update rcustomer set email = '" + email + "' where user_id = " + uid;
	      else
	    	  sql = "update rcustomer set zipcode = " + zipcode + "where user_id = " + uid;
	      s.executeQuery(sql);
	      sql = "select * from rcustomer where user_id = " + uid;
	      rs = s.executeQuery(sql);
	      
	      message += "<h4> Profile edited! </h4>";
	      while(rs.next()){
	      message += "Name: " + rs.getString("user_name") + "<br> Email: " + rs.getString("email") + "<br> Zipcode: " + rs.getInt("zipcode");
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
