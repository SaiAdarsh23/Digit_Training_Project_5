package com.digit.BankApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/transfer")
public class Transfer extends HttpServlet {
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet res;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int cid = Integer.parseInt(req.getParameter("cid"));
		String bname = req.getParameter("bname");
		String ifsc = req.getParameter("ifsc");
		int accno = Integer.parseInt(req.getParameter("accno"));
		String rifsc = req.getParameter("rifsc");
		int raccno = Integer.parseInt(req.getParameter("raccno"));
		int amount = Integer.parseInt(req.getParameter("amount"));
		int pin = Integer.parseInt(req.getParameter("pin"));
		
		String url = "jdbc:mysql://localhost:3306/bankApp";
        String user = "root";
        String pwd = "1234";
        
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement("select * from bankapp where Cust_id=? and IFSC_code=? and AccNo=? and pin=?");
			pstmt.setInt(1,cid);
			pstmt.setString(2,ifsc);
			pstmt.setInt(3,accno);
			pstmt.setInt(4,pin);
			res = pstmt.executeQuery();
			if(res.next()==true) {
				pstmt = con.prepareStatement("select * from bankapp where IFSC_code=? and AccNo=?");
				pstmt.setString(1,rifsc);
				pstmt.setInt(2,raccno);
				res = pstmt.executeQuery();
				if(res.next()==true) {
					pstmt = con.prepareStatement("select Balance from bankapp where AccNo=?");
					pstmt.setInt(1,accno);
					res = pstmt.executeQuery();
					res.first();
					int bal = res.getInt(1);
					if(bal>amount) {
						pstmt = con.prepareStatement("update bankapp set Balance=Balance-? where AccNo=?");
						pstmt.setInt(1,amount);
						pstmt.setInt(2,accno);
						int x = pstmt.executeUpdate();
						if(x>0) {
							pstmt = con.prepareStatement("update bankapp set Balance=Balance+? where AccNo=?");
							pstmt.setInt(1,amount);
							pstmt.setInt(2,raccno);
							int x1 = pstmt.executeUpdate();
							if(x1>0) {
								Random r = new Random();
								int transactionid = (100000 + r.nextInt(900000));
								pstmt = con.prepareStatement("insert into transactions values(?,?,?,?,?,?,?,?)");
								pstmt.setInt(1,transactionid);
								pstmt.setInt(2,cid);
								pstmt.setString(3,bname);
								pstmt.setString(4,ifsc);
								pstmt.setInt(5,accno);
								pstmt.setString(6,rifsc);
								pstmt.setInt(7,raccno);
								pstmt.setInt(8,amount);
								int x2 = pstmt.executeUpdate();
								if(x2>0) {
									resp.sendRedirect("/BankApplication/TransferSuccess.html");
								}
								else {
									resp.sendRedirect("/BankApplication/TransferFail.html");
								}
							}
							else {
								resp.sendRedirect("/BankApplication/TransferFail.html");
							}
						}
						else {
							resp.sendRedirect("/BankApplication/TransferFail.html");
						}
					}
					else {
						resp.sendRedirect("/BankApplication/TransferFail.html");
					}
				}
				else {
					resp.sendRedirect("/BankApplication/TransferFail.html");
				}
			}
			else {
				resp.sendRedirect("/BankApplication/TransferFail.html");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			resp.sendRedirect("/BankApplication/TransferFail.html");
			return;
		}
	}
}
