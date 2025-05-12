package com.billing.servlets;

import com.billing.db.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            response.sendRedirect("login.html?error=Missing+credentials");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT user_id, password_hash FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                // For simplicity, using SHA-256 hash comparison (should use salted hash in production)
                String inputHash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);

                if (storedHash.equals(inputHash)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", rs.getInt("user_id"));
                    session.setAttribute("username", username);
                    response.sendRedirect("bill.html");
                    return;
                }
            }
            response.sendRedirect("login.html?error=Invalid+username+or+password");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.html?error=Server+error");
        }
    }
}
