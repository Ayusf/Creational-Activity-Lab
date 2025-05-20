package register;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class Registrationn
 */
@WebServlet("/Registrationn")
public class Registrationn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registrationn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		 String name = request.getParameter("name");
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");
	        String phone = request.getParameter("phone");

	        try (Connection con = DatabaseConnection.getConnection()) {
	            PreparedStatement ps = con.prepareStatement("INSERT INTO users (name, email, password, phone) VALUES (?, ?, ?, ?)");
	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, password);
	            ps.setString(4, phone);	
	            ps.executeUpdate();
	            response.sendRedirect("login.jsp");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	}

}	
