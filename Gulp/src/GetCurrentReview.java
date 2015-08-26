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

/**
 * Servlet implementation class GetCurrentReview
 */
@WebServlet("/GetCurrentReview")
public class GetCurrentReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
     String message="";  
     Connection conn=null;
     ResultSet rs = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCurrentReview() {
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
		 try{
			 HttpSession session = request.getSession(true);
				int uid =Integer.parseInt((String) session.getAttribute("userid"));
			
			  String url= "jdbc:oracle:thin:testuser/password@localhost"; 
			  Class.forName("oracle.jdbc.driver.OracleDriver");
	        //properties for creating connection to Oracle database
	        Properties props = new Properties();
	        props.setProperty("user", "testdb");
	        props.setProperty("password", "password");
	        conn = DriverManager.getConnection(url,props);
	        //creating connection to Oracle database using JDBC              
	        Statement s=conn.createStatement();
	        
	        String query1="select * from review where user_id = " + uid;
	        //System.out.println(query1);
	        rs=s.executeQuery(query1);	 
	        message+="<div align=\"center\"><table style=\"border:2px solid black\">";
            message+="<th style=\" background-color:yellow;border:2px solid black\">Restaurant ID</th><th style=\" background-color:yellow;border:2px solid black\">Review</th><th style=\" background-color:yellow;border:2px solid black\">Rating</th>";

            while(rs.next())

               {

                  message+="<tr >"+
                 
               		   "<td style=\" background-color:yellow;border:2px solid black\">"+rs.getInt("rid")+
               		   "</td><td style=\"background-color:yellow;border:2px solid black\">" +rs.getString("review")+
               		    "</td><td style=\"background-color:yellow;border:2px solid black\">" +rs.getInt("rating")+
               		  "</td></tr>" ;  

                 
            }
            

            message+="\n</tbody>\n</table></div>";
            
	        request.setAttribute("message", message);
	       
   		getServletContext().getRequestDispatcher("/Change.jsp").forward(request, response);
   		
	       
  		
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
