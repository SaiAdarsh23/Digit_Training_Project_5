package com.digit.BankApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/changepword")
public class ChangePassword extends HttpServlet {
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet resultSet;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = "jdbc:mysql://localhost:3306/bankApp";
        String user = "root";
        String pwd = "1234";
        HttpSession session = req.getSession(true);
        int accno = (int)session.getAttribute("accno");
        String op = req.getParameter("op");
        String np = req.getParameter("np");
        String cp = req.getParameter("cp");
        int np1 = Integer.parseInt(np);
  
        //Database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pwd);

            if(np.equals(cp)) {
            	pstmt = con.prepareStatement("update bankapp set pin = ? where AccNo = ?");
                pstmt.setInt(1, np1);
                pstmt.setInt(2, accno);          
                
                int x = pstmt.executeUpdate();
                if(x>0) {
                    resp.sendRedirect("/BankApplication/PwordChangeSuccess.html");
                }
                else {
                    resp.sendRedirect("/BankApplication/PwordChangeFail.html");
                }
            }
            else {
            	
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
}

