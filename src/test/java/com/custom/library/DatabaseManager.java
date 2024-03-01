package com.custom.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

	private String databaseServerName = "localhost";
	private String databasePort = "1521";
	private String databaseNAme = "xe";
	private String userName = "hr";
	private String userPassword = "hr";
	private String connectionURL = "jdbc:oracle:thin:hr@//" + databaseServerName + ":" + databasePort+"/"+databaseNAme;
	
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	private void connectToOracleDB() throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.OracleDriver");
		connection = DriverManager.getConnection(connectionURL, userName, userPassword);
		statement = connection.createStatement();	
	}
	
	public ResultSet runSQLQuery(String sqlQuery) throws ClassNotFoundException, SQLException
	{
		connectToOracleDB();
		resultSet = statement.executeQuery(sqlQuery);
		return resultSet;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DatabaseManager dbManager = new DatabaseManager();
		ResultSet data = dbManager.runSQLQuery("Select * From Countries");
		System.out.println("result: " + data);	
		
		String tab = "\t";
		while(data.next())
		{
			String countryID = data.getString("COUNTRY_ID");
			String countryName = data.getString("COUNTRY_NAME");
			String regionID = data.getString("REGION_ID");
			System.out.println(countryID + tab + countryName +tab+tab+ regionID);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
