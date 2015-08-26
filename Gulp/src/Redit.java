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


@WebServlet("/Redit")
public class Redit extends HttpServlet {
	private static final long serialVersionrid = 1L;
    String message = "";
    Connection conn = null;
    ResultSet rs = null;
    public Redit() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			
			
			String name=request.getParameter("name");
			String address =  request.getParameter("address");
			String descript = request.getParameter("des");
			int rid = Integer.parseInt(request.getParameter("id"));
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
	        
	      if (!name.equals("")  && !address.equals("") && !descript.equals("") )
	    	 sql = "update restaurant set rname ='" + name + "', address = '" + address + "', description = '" + descript + "' where rid = " + rid;
	      else if (!name.equals("")  && !address.equals(""))
	    	  sql = "update restaurant set rname ='" + name + "', address = '" + address + "'" + " where rid = " + rid;
	      else if (!name.equals("") && !descript.equals("")) 
	    	  sql = "update restaurant set rname ='" + name + "', description = '" + descript + "' where rid = " + rid;
	      else if (!address.equals("") && !descript.equals(""))
	    	  sql = "update restaurant set address = '" + address + "', description = '" + descript + "' where rid = " + rid;
	      else if (!name.equals("") )
	    	  sql = "update restaurant set rname ='" + name + "' where rid = " + rid;
	      else if ( !address.equals(""))
	    	  sql = "update restaurant set address = '" + address + "' where rid = " + rid;
	      else
	    	  sql = "update restaurant set description = '" + descript + "' where rid = " + rid;
	      s.executeQuery(sql);
	      sql = "select * from restaurant where rid = " + rid;
	      rs = s.executeQuery(sql);
	      
	      message += "<h4> Restaurant edited! </h4>";
	      while(rs.next()){
	      message += "Name: " + rs.getString("rname") + "<br> address: " + rs.getString("address") + "<br> description: " + rs.getString("description");
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
