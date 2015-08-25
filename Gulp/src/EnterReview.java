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


@WebServlet("/EnterReview")
public class EnterReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       Connection conn = null;
       ResultSet rs = null;
       String message = "";

    public EnterReview() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get parameters from the request
		message += "<nav class=\"navbar navbar-default\">" + 
 " <div class=\"container-fluid\">"+
    "<div class=\"navbar-header\">"+
     "<a class=\"navbar-brand\" href=\"Home.html\">Gulp</a>"+
    "</div>"+
    "<div>"+
      "<ul class=\"nav navbar-nav\">"+
     " <li class=\"active\"><a href=\"#\">Enter Review</a></li>"+
      "<li><a href=\"restaurants.jsp\">List of Restaurants</a></li>"+
      "<li><a href=\"Profile.jsp\">Profile Search</a></li>"+
      "</ul>"+
   " </div>" +
  "</div>" +
"</nav>";
		int Rating =Integer.parseInt(request.getParameter("Rating"));
		String Review = request.getParameter("review");System.out.println(Review);
		int rid =Integer.parseInt(request.getParameter("resID"));
		HttpSession session = request.getSession(true);
		int uid =Integer.parseInt((String) session.getAttribute("userid"));
		System.out.println(uid);
		// store data in User object and save User object in db
		 try{
			 
			  String url= "jdbc:oracle:thin:testuser/password@localhost"; 
			  Class.forName("oracle.jdbc.driver.OracleDriver");
	        //properties for creating connection to Oracle database
	        Properties props = new Properties();
	        props.setProperty("user", "testdb");
	        props.setProperty("password", "password");
	        conn = DriverManager.getConnection(url,props);
	        //creating connection to Oracle database using JDBC              
	        Statement s=conn.createStatement();
	        
	        String query1="insert into review values(" + rid + "," + uid  + ", '" + Review + "', " + Rating + ")";
	        //System.out.println(query1);
	        s.executeQuery(query1);
	        message += "<h3> Thanks for your rating! </h3>";
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
