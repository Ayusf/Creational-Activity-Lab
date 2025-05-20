import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class UserServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "password";

    private Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found");
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try (Connection conn = connect()) {
            switch (action) {
                case "create":
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    PreparedStatement psCreate = conn.prepareStatement("INSERT INTO users(name, email) VALUES(?, ?)");
                    psCreate.setString(1, name);
                    psCreate.setString(2, email);
                    psCreate.executeUpdate();
                    out.println("User added successfully!");
                    break;

                case "read":
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM users");
                    out.println("<h3>Users:</h3>");
                    while (rs.next()) {
                        out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Email: " + rs.getString("email") + "<br>");
                    }
                    break;

                case "update":
                    int uid = Integer.parseInt(request.getParameter("id"));
                    String newName = request.getParameter("name");
                    String newEmail = request.getParameter("email");
                    PreparedStatement psUpdate = conn.prepareStatement("UPDATE users SET name=?, email=? WHERE id=?");
                    psUpdate.setString(1, newName);
                    psUpdate.setString(2, newEmail);
                    psUpdate.setInt(3, uid);
                    psUpdate.executeUpdate();
                    out.println("User updated successfully!");
                    break;

                case "delete":
                    int delId = Integer.parseInt(request.getParameter("id"));
                    PreparedStatement psDelete = conn.prepareStatement("DELETE FROM users WHERE id=?");
                    psDelete.setInt(1, delId);
                    psDelete.executeUpdate();
                    out.println("User deleted successfully!");
                    break;

                default:
                    out.println("Invalid action. Use action=create/read/update/delete.");
            }
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
