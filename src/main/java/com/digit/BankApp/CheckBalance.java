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

@WebServlet("/CheckBalance")
public class CheckBalance extends HttpServlet {
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
        //Database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pwd);

            pstmt = con.prepareStatement("select Balance from bankapp where AccNo=?");
            
            pstmt.setInt(1, accno);          
            
            resultSet = pstmt.executeQuery();
            if(resultSet.next()==true) {
            	session.setAttribute("balance", resultSet.getInt("Balance"));
                resp.sendRedirect("/BankingApplication/Balance.jsp");
            }
            else {
                resp.sendRedirect("/BankApplication/BalanceFail.jsp");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
}
