package com.billing.test;

import com.billing.servlets.UserLoginServlet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserLoginServletTest extends Mockito {

    @Test
    public void testDoPostValidLogin() throws Exception {
        UserLoginServlet servlet = new UserLoginServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getParameter("username")).thenReturn("user1");
        when(request.getParameter("password")).thenReturn("user1password");
        when(request.getSession()).thenReturn(session);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("username"), eq("user1"));
        verify(response).sendRedirect("bill.html");
    }

    @Test
    public void testDoPostInvalidLogin() throws Exception {
        UserLoginServlet servlet = new UserLoginServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("username")).thenReturn("invaliduser");
        when(request.getParameter("password")).thenReturn("wrongpassword");

        servlet.doPost(request, response);

        verify(response).sendRedirect("login.html?error=Invalid+username+or+password");
    }
}
