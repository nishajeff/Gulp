

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


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
      private String message; 
     Connection conn=null;
 	 ResultSet res=null;

    public Login() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// get parameters from the request
		int ID =Integer.parseInt(request.getParameter("userID"));
		
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
	        
	        String query1="select * from rcustomer where user_id = " + ID;
	        //System.out.println(query1);
	        res=s.executeQuery(query1);
	       if (res.next()){
		        
	
	   		getServletContext().getRequestDispatcher("/review.jsp").forward(request, response);
	        }
	        else{
	        	
	        	message += "<form action=\"CreateAccount\" method=\"post\">"+
	        	"<label>Name: </label>"+
	        	"<input  type=\"text\" name=\"Name\" required ><br>"+
	        	"<label>Email: </label>"+
	        	"<input  type=\"text\" name=\"Email\" required ><br>"+
	        	"<label>Zipcode: </label>"+
	        	"<input  type=\"text\" name=\"Zipcode\" required ><br>"+
	        	"<label>&nbsp;</label>"+
	        	"<input type=\"submit\" value=\"Enter\" id=\"submit\">"+
	        	"</form>";
	        	request.setAttribute("message", message);
	    		getServletContext().getRequestDispatcher("/output.jsp").forward(request, response);
	        }
		// set User object in request object and set URL
		
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
