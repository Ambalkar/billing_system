package com.billing.servlets;

import com.billing.db.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import org.json.JSONObject;
import org.json.JSONArray;

@WebServlet("/bills")
public class BillServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Create a new bill
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        int userId = (int) session.getAttribute("userId");

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonRequest = new JSONObject(sb.toString());

        String billDate = jsonRequest.optString("billDate");
        String dueDate = jsonRequest.optString("dueDate");
        double totalAmount = jsonRequest.optDouble("totalAmount", -1);

        if (billDate.isEmpty() || dueDate.isEmpty() || totalAmount < 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO bills (user_id, bill_date, due_date, total_amount, status) VALUES (?, ?, ?, ?, 'pending')";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, userId);
            stmt.setDate(2, Date.valueOf(billDate));
            stmt.setDate(3, Date.valueOf(dueDate));
            stmt.setDouble(4, totalAmount);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int billId = generatedKeys.getInt(1);
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("billId", billId);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print(jsonResponse.toString());
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve bills for logged-in user
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        int userId = (int) session.getAttribute("userId");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT bill_id, bill_date, due_date, total_amount, status FROM bills WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            JSONArray billsArray = new JSONArray();
            while (rs.next()) {
                JSONObject bill = new JSONObject();
                bill.put("billId", rs.getInt("bill_id"));
                bill.put("billDate", rs.getDate("bill_date").toString());
                bill.put("dueDate", rs.getDate("due_date").toString());
                bill.put("totalAmount", rs.getDouble("total_amount"));
                bill.put("status", rs.getString("status"));
                billsArray.put(bill);
            }
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(billsArray.toString());
            out.flush();
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // Implement doPut and doDelete for update and delete operations as needed
}
