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


@WebServlet("/GetRestaurants")
public class GetRestaurants extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	ResultSet rs = null;
	String message = "";
    

    public GetRestaurants() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try{
			 message="";
			 String currentpage=request.getParameter("currentpage");
			  String url= "jdbc:oracle:thin:testuser/password@localhost"; 
			  Class.forName("oracle.jdbc.driver.OracleDriver");
	        //properties for creating connection to Oracle database
	        Properties props = new Properties();
	        props.setProperty("user", "testdb");
	        props.setProperty("password", "password");
	        conn = DriverManager.getConnection(url,props);
	        //creating connection to Oracle database using JDBC              
	        Statement s=conn.createStatement();
	        
	        String query1="select r.rid,r.rname,NVL(s.average, 0) as avrg from restaurant r left outer join (select rid,avg(rating)as average FROM  REVIEW GROUP BY RID ) s on r.rid=s.rid ";
	        //System.out.println(query1);
	        rs=s.executeQuery(query1);	 
	        message+="<div align=\"center\"><table style=\"border:2px solid black\">";
            message+="<th style=\" background-color:yellow;border:2px solid black\">Restaurant ID</th><th style=\" background-color:yellow;border:2px solid black\">Name</th><th style=\" background-color:yellow;border:2px solid black\">Average Rating</th>";

            while(rs.next())

               {

                  message+="<tr ><td style=\" background-color:yellow;border:2px solid black\">"+
                  "<a href=\"details?ResID=" +rs.getInt("rid")+"&currentpage=" +currentpage + "\">"+rs.getInt("rid")+"</a>"+
               		   "</td><td style=\" background-color:yellow;border:2px solid black\">"+rs.getString("rname")+
               		   "</td><td style=\"background-color:yellow;border:2px solid black\">" +rs.getDouble("avrg")+
               		  "</td></tr>" ;  

                 
            }
            

            message+="\n</tbody>\n</table></div>";
            
	        request.setAttribute("message", message);
	        if(currentpage.equalsIgnoreCase("restaurants"))
   		getServletContext().getRequestDispatcher("/restaurants.jsp").forward(request, response);
   		
	       else if(currentpage.equalsIgnoreCase("EditRes"))
   		getServletContext().getRequestDispatcher("/EditRes.jsp").forward(request, response);
	       else if(currentpage.equalsIgnoreCase("review"))
	      		getServletContext().getRequestDispatcher("/review.jsp").forward(request, response);
	       else if(currentpage.equalsIgnoreCase("Change"))
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
