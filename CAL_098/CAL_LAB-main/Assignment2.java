import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class CourierServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/courierdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "password";

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found.");
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try (Connection conn = getConnection()) {
            if ("create".equalsIgnoreCase(action)) {
                String trackingNumber = request.getParameter("tracking_number");
                String sender = request.getParameter("sender");
                String receiver = request.getParameter("receiver");
                String status = request.getParameter("status");

                PreparedStatement ps = conn.prepareStatement("INSERT INTO couriers (tracking_number, sender, receiver, status) VALUES (?, ?, ?, ?)");
                ps.setString(1, trackingNumber);
                ps.setString(2, sender);
                ps.setString(3, receiver);
                ps.setString(4, status);
                ps.executeUpdate();

                out.println("Courier record created successfully.");

            } else if ("read".equalsIgnoreCase(action)) {
                String trackingNumber = request.getParameter("tracking_number");

                PreparedStatement ps = conn.prepareStatement("SELECT * FROM couriers WHERE tracking_number = ?");
                ps.setString(1, trackingNumber);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    out.println("<h3>Tracking Info:</h3>");
                    out.println("Tracking Number: " + rs.getString("tracking_number") + "<br>");
                    out.println("Sender: " + rs.getString("sender") + "<br>");
                    out.println("Receiver: " + rs.getString("receiver") + "<br>");
                    out.println("Status: " + rs.getString("status") + "<br>");
                } else {
                    out.println("No courier found with this tracking number.");
                }

            } else if ("update".equalsIgnoreCase(action)) {
                String trackingNumber = request.getParameter("tracking_number");
                String status = request.getParameter("status");

                PreparedStatement ps = conn.prepareStatement("UPDATE couriers SET status = ? WHERE tracking_number = ?");
                ps.setString(1, status);
                ps.setString(2, trackingNumber);
                int updated = ps.executeUpdate();

                if (updated > 0) {
                    out.println("Courier status updated.");
                } else {
                    out.println("No matching courier found.");
                }

            } else if ("delete".equalsIgnoreCase(action)) {
                String trackingNumber = request.getParameter("tracking_number");

                PreparedStatement ps = conn.prepareStatement("DELETE FROM couriers WHERE tracking_number = ?");
                ps.setString(1, trackingNumber);
                int deleted = ps.executeUpdate();

                if (deleted > 0) {
                    out.println("Courier deleted successfully.");
                } else {
                    out.println("No courier found with this tracking number.");
                }

            } else {
                out.println("Invalid action. Use action=create/read/update/delete.");
            }

        } catch (SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
  
  
  
  
  
  /*CREATE TABLE couriers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tracking_number VARCHAR(100) NOT NULL,
    sender VARCHAR(100),
    receiver VARCHAR(100),
    status VARCHAR(50)
);
*/
