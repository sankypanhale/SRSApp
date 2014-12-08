package srsapp.driver;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

/** 
 * This class creates an connection
 * */
public class MainDriver {

	/** 
	 * This method accepts username and password and returns the created connection
	 * */
	public Connection mainMethod(String userName, String passWord) {
		
		//connection to Oracle Server
		OracleDataSource ds;
		try {
			ds = new oracle.jdbc.pool.OracleDataSource();
			ds.setURL("jdbc:oracle:thin:@grouchoIII.cc.binghamton.edu:1521:ACAD111");
			Connection conn = ds.getConnection(userName, passWord);
			return conn;
		} catch (SQLException e) {
			return null;
		}
	}
}
