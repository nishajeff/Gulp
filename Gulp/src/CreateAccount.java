import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private String message = "";
       static Connection conn = null;
       ResultSet res = null;

    public CreateAccount() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response)
;	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get parameters from the request
				int ZipCode =Integer.parseInt(request.getParameter("Zipcode"));
				String name = request.getParameter("Name");
				String email = request.getParameter("Email");
				HttpSession session = request.getSession(true);
				
				// store data in User object and save User object in db
				 try{
					  message="";  
					  String url= "jdbc:oracle:thin:testuser/password@localhost"; 
					  Class.forName("oracle.jdbc.driver.OracleDriver");
			        //properties for creating connection to Oracle database
			        Properties props = new Properties();
			        props.setProperty("user", "testdb");
			        props.setProperty("password", "password");
			        conn = DriverManager.getConnection(url,props);
			        //creating connection to Oracle database using JDBC              
			        Statement s=conn.createStatement();
			        
			        String query1="insert into rcustomer values(uid_seq.nextval, '" + name + "', '" + email + "', " + ZipCode + ")";
			        //System.out.println(query1);
			        s.executeQuery(query1);
			       res = s.executeQuery("select * from rcustomer where user_name = '" + name + "'"); 
			       message += "<nav class=\"navbar navbar-default\">" + 
			    		   " <div class=\"container-fluid\">"+
			    		      "<div class=\"navbar-header\">"+
			    		       "<a class=\"navbar-brand\" href=\"Home.html\">Gulp</a>"+
			    		      "</div>"+
			    		      "<div>"+
			    		        "<ul class=\"nav navbar-nav\">"+
			    		       " <li><a href=\"review.jsp\">Enter Review</a></li>"+
			    		        "<li><a href=\"restaurants.jsp\">List of Restaurants</a></li>"+
			    		        "<li><a href=\"Profile.jsp\">Profile Search</a></li>"+
			    		        "</ul>"+
			    		     " </div>" +
			    		    "</div>" +
			    		  "</nav>";
			      while (res.next())
			      {
			   
			       message += "<h3> Account Created! </h3>";
			       message += "User ID = " + res.getInt("user_id")+"<br> Name: "+ res.getString("user_name")+"<br> Email: "+ res.getString("email")+"<br> ZipCode: "+ res.getInt("Zipcode");
			      session.setAttribute("userid",String.valueOf(res.getInt("user_id")));
			      System.out.println(session.getAttribute("userid"));
			      }
			      request.setAttribute("message", message);
		    		getServletContext().getRequestDispatcher("/output.jsp").forward(request, response);
				}catch(Exception e){
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
