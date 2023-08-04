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

@WebServlet("/loan")
public class LoanApply extends HttpServlet {
	String url = "jdbc:mysql://localhost:3306/bankApp";
	String user = "root";
	String pwd = "1234";
	private Connection conn;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String typeOfLoan = req.getParameter("loan");

		String query = "SELECT * FROM Loan WHERE Ltype = ?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pwd);

			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, typeOfLoan);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int loanID = rs.getInt("Lid");
				String loanType = rs.getString("Ltype");
				int tenure = rs.getInt("tenure");
				float intrest = rs.getFloat("interest");
				String description = rs.getString("description");
				Loan curLoanObject = new Loan(loanID, loanType, tenure, intrest, description);
				HttpSession session = req.getSession();
				session.setAttribute("curLoanObject", curLoanObject);
				resp.sendRedirect("/BankApplication/LoanDetails.jsp");
				return;
			} else {
				resp.sendRedirect("/BankApplication/LoanDetailsFail.jsp");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("/BankApplication/LoanDetailsFail.jsp");
			return;
		}
	}
}
