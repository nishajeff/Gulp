

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

/**
 * Servlet implementation class GetProfile
 */
@WebServlet("/GetProfile")
public class GetProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
     String message=""; 
     Connection conn = null;
     ResultSet rs = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProfile() {
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
		try{
			message="";
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
      String sql = "";
      sql = "select * from rcustomer where user_id = " + uid;
      rs = s.executeQuery(sql);
      
      message += "<h4>Current Profile </h4>";
      while(rs.next()){
      message += "Name: " + rs.getString("user_name") + "<br> Email: " + rs.getString("email") + "<br> Zipcode: " + rs.getInt("zipcode");
      
      }
      request.setAttribute("message", message);
		getServletContext().getRequestDispatcher("/EditProfile.jsp").forward(request, response);
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
