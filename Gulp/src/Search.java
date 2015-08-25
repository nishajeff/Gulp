

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String message="";  
    Connection conn=null;
    ResultSet rs=null;
    public Search() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{	
			int user=Integer.parseInt(request.getParameter("userID"));
			  String url= "jdbc:oracle:thin:testuser/password@localhost"; 
			  Class.forName("oracle.jdbc.driver.OracleDriver");
	        //properties for creating connection to Oracle database
	        Properties props = new Properties();
	        props.setProperty("user", "testdb");
	        props.setProperty("password", "password");
	        conn = DriverManager.getConnection(url,props);
	        //creating connection to Oracle database using JDBC              
	        Statement s=conn.createStatement();
	        String q="select * from rcustomer left outer join review on review.USER_ID = rcustomer.USER_ID left join restaurant on review.RID=restaurant.RID where rcustomer.USER_ID="+user;
	        rs=s.executeQuery(q);
	        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	        if(rs.next()){
	        	String text = df.format(rs.getDate("rdate"));
	        	message+="Name:"+rs.getString("user_name")+"<br>Email: "+rs.getString("email")+"<br>Zip Code: "+rs.getInt("zipcode")+"<br><h3>REVIEWS:</h3>";
	        	 message+="<div align=\"center\"><table style=\"border:2px solid black\">";
		         
	 	        message+="<th style=\" background-color:yellow;border:2px solid black\">Restaurant</th><th style=\" background-color:yellow;border:2px solid black\">Review</th><th style=\" background-color:yellow;border:2px solid black\">Rating</th><th style=\" background-color:yellow;border:2px solid black\">Date</th>";
	 	       message+="<tr >"+
	 	    		  "<td style=\"background-color:yellow;border:2px solid black\">" +rs.getString("rname")+
              		 "</td><td style=\" background-color:yellow;border:2px solid black\">"+rs.getString("review")+
           		   "</td><td style=\" background-color:yellow;border:2px solid black\">"+rs.getInt("rating")+
           		   "</td><td style=\" background-color:yellow;border:2px solid black\">"+text+
           		  "</td></tr>" ;   
	        
	        }
	       
	        while(rs.next()){
	        	String text = df.format(rs.getDate("rdate"));
	        			 message+= "<tr><td style=\"background-color:yellow;border:2px solid black\">" +rs.getString("rname")+
	              		 "</td><td style=\" background-color:yellow;border:2px solid black\">"+rs.getString("review")+
	           		   "</td><td style=\" background-color:yellow;border:2px solid black\">"+rs.getInt("rating")+
	           		     "</td><td style=\" background-color:yellow;border:2px solid black\">"+text+
	           		  "</td></tr>" ;   
	        }
	        message+="\n</tbody>\n</table></div>";
	        message+="<form action=\"Profile.jsp\">"+
	        "<label>&nbsp;</label>"+
	        "<input type=\"submit\" value=\"Go Back to Search\" id=\"submit\">"+
	        "</form>";
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
