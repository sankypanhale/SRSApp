package srsapp.driver;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

public class MainDriver {

	public Connection mainMethod(String userName, String passWord) {
		
		//connection to Oracle Server
		OracleDataSource ds;
		try {
			System.out.println("I m here...!!");
			ds = new oracle.jdbc.pool.OracleDataSource();
			ds.setURL("jdbc:oracle:thin:@grouchoIII.cc.binghamton.edu:1521:ACAD111");
			Connection conn = ds.getConnection(userName, passWord);
			return conn;
		} catch (SQLException e) {
			return null;
		}
	}
}
