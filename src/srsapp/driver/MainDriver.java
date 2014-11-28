package srsapp.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;
import srsapp.util.Worker;

public class MainDriver {

	public static void main(String[] args) {
		
		//connection to Oracle Server
		OracleDataSource ds;
		try {
			BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
			String userName = getUserName(sysIn);
			String passWord = getPassword(sysIn);
			ds = new oracle.jdbc.pool.OracleDataSource();
			ds.setURL("jdbc:oracle:thin:@grouchoIII.cc.binghamton.edu:1521:ACAD111");
			Connection conn = ds.getConnection(userName, passWord);
			Worker connWorker = new Worker(conn);
			connWorker.printMenu();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQL Exception");
			System.exit(0);
		}
	   

	}
	public static String getUserName(BufferedReader in){
		System.out.println("Enter UserName:");
		String userName = null;
		try {
			userName = in.readLine();
		} catch (IOException e) {
			System.out.println("Unable to read the userName from the Input!");
		}
		return userName;
	}
	
	public static String getPassword(BufferedReader in){
		System.out.println("Enter Password:");
		String pass= null;
		try {
			pass = in.readLine();
		} catch (IOException e) {
			System.out.println("Unable to read the password from the Input!");
		}
		return pass;
	}

}
