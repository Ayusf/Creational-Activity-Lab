CREATE TABLE couriers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tracking_number VARCHAR(100),
    sender VARCHAR(100),
    receiver VARCHAR(100),
    status VARCHAR(100)
);
Database 
  <!DOCTYPE html>
<html>
<head><title>Courier Tracking</title></head>
<body>
    <h2>Track Your Courier</h2>
    <form action="CourierServlet" method="post">

        Enter Tracking Number: <input type="text" name="tracking_number" required>
        <input type="submit" value="Track">
    </form>
</body>
</html>
Index jsp
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class CourierServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String trackingNumber = request.getParameter("tracking_number");

        String url = "jdbc:mysql://localhost:3306/courierdb";
        String user = "root";
        String pass = "password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM couriers WHERE tracking_number = ?");
            ps.setString(1, trackingNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("trackingNumber", rs.getString("tracking_number"));
                request.setAttribute("sender", rs.getString("sender"));
                request.setAttribute("receiver", rs.getString("receiver"));
                request.setAttribute("status", rs.getString("status"));
                RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
                rd.forward(request, response);
            } else {
                response.getWriter().println("<h3>No courier found with this tracking number.</h3>");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }
    }
}

Courier Sevrlrt
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head><title>Tracking Result</title></head>
<body>
    <h2>Tracking Details</h2>
    <p>Tracking Number: ${trackingNumber}</p>
    <p>Sender: ${sender}</p>
    <p>Receiver: ${receiver}</p>
    <p>Status: ${status}</p>
    <a href="index.jsp">Track Another</a>
</body>
</html>

Result.jsp
