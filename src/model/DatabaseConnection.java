package model;

import java.sql.*;

public class DatabaseConnection {
	
	/**
     *  Attempts to connect to the postgresql database.
     *
     * @return              the Connection object that represents the active connection to the database
     */
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
