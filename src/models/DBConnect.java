/* 
   Programmer: Pablo Aramburu Garcia (A20432692)
   File Name: DBConnect.java 
   Assignment: Lab 4
   Course: ITMS 510 
   Due Date: 11/11/2018
   
   This class is used to be able to connect to the database given by profesoor
 */

/*package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

// Code database URL
	static final String DB_URL = "jdbc:mysql://www.papademas.net:3307/fp510?autoReconnect=true&useSSL=false";
// Database credentials
	static final String USER = "fpuser", PASS = "510";

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);

	}
}

*/


package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect  {

	public static Connection connection;
	public Connection getConnection() {
		return connection;
	}

	private static String url = "jdbc:mysql://www.papademas.net:3307/fp510?autoReconnect=true&useSSL=false";
	private static String username = "fpuser";
	private static String password = "510";

	public DBConnect() {
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("Error creating connection to database: " + e);
			System.exit(-1);
		}
	}
}
	