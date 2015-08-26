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

/**
 * Servlet implementation class details
 */
@WebServlet("/details")
public class details extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String message="";  
    Connection conn=null;
    ResultSet rs=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public details() {
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
			message="";
			 String currentpage=request.getParameter("currentpage");
			String id=request.getParameter("ResID");
			int res_id=Integer.parseInt(id);
			
			  String url= "jdbc:oracle:thin:testuser/password@localhost"; 
			  Class.forName("oracle.jdbc.driver.OracleDriver");
	        //properties for creating connection to Oracle database
	        Properties props = new Properties();
	        props.setProperty("user", "testdb");
	        props.setProperty("password", "password");
	        conn = DriverManager.getConnection(url,props);
	        //creating connection to Oracle database using JDBC              
	        Statement s=conn.createStatement();
	        String query="select * from restaurant where rid="+res_id;
	        rs=s.executeQuery(query);
	        while(rs.next())
	        {
	        	message+="<h3>restaurant ID="+res_id+"</h3>";
	        	message+="<h3>Name: "+rs.getString("rname")+"</h3>";
	        	message+="<h4>Address: "+rs.getString("address")+"</h4>";
	        	message+="<h4>Description: "+rs.getString("description")+"</h4>";
	        }
	        String query1="select r.rid,r.review,r.rating,r.user_id,r.rdate,s.cnt,s.avrg from review r,(select rid,count(review)as cnt,avg(rating) as avrg from review group by rid) s where r.rid=s.rid and r.rid="+res_id;
	        //System.out.println(query1);
	        rs=s.executeQuery(query1);
	        if(rs.next()){
	        	message+="<h4>Number Of Reviews: "+rs.getInt("cnt")+"</h4>";
	        	message+="<h4>Average Rating: "+rs.getInt("avrg")+"</h4>";
	        }
	        message+="<div align=\"center\"><table style=\"border:2px solid black\">";
          message+="<th style=\" background-color:yellow;border:2px solid black\">Review</th><th style=\" background-color:yellow;border:2px solid black\">Rating</th><th style=\" background-color:yellow;border:2px solid black\">Customer ID</th><th style=\" background-color:yellow;border:2px solid black\">Date</th>";
          rs=s.executeQuery(query1);
          while(rs.next())
             {
        	  
        	  DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        	  String text = df.format(rs.getDate("rdate"));
        	 System.out.println(text);
                message+="<tr >"+
                		 "</td><td style=\" background-color:yellow;border:2px solid black\">"+rs.getString("review")+
             		   "</td><td style=\" background-color:yellow;border:2px solid black\">"+rs.getInt("rating")+
             		   "</td><td style=\"background-color:yellow;border:2px solid black\">" +rs.getInt("user_id")+
             		   "</td><td style=\"background-color:yellow;border:2px solid black\">" +text+
             		  "</td></tr>" ;  

//               System.out.println(rs.getString("rdate"));
          }
          

          message+="\n</tbody>\n</table></div>";
          
	        request.setAttribute("message", message);
	        if(currentpage.equalsIgnoreCase("restaurants"))
 		getServletContext().getRequestDispatcher("/restaurants.jsp").forward(request, response);
	        else if(currentpage.equalsIgnoreCase("EditRes"))
	       		getServletContext().getRequestDispatcher("/EditRes.jsp").forward(request, response);
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
