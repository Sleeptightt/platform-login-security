package model;

import java.sql.*;

public class DatabaseConnection {
	
	
	public static Connection getConnection() {
		
		try {
	    	Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/platform-login-security", "postgres", "sleepata123");
	    	return con;
			
		} catch (SQLException ex) {
		
			System.out.println(ex.toString());
			return null;
		}
	}
}
