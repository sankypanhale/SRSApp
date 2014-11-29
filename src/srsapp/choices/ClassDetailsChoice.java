package srsapp.choices;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;
import srsapp.util.ChoiceAbstract;

public class ClassDetailsChoice extends ChoiceAbstract{
	
	public ClassDetailsChoice(BufferedReader in, Connection connIn) {
		this.setInput(in);
		this.setConn(connIn);
	}
	@Override
	public void processUserInput() {
		String classId = getUserInput();
		printClassInfo(classId);
	}
	
	public void printClassInfo(String classIn){
		CallableStatement cs;
		try {
			cs = conn.prepareCall("begin SRSJDBC.class_details(?, ?); end;");
			cs.setString(1, classIn);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
			ResultSet rs = (ResultSet)cs.getObject(2);
			ResultSetMetaData rsmd = rs.getMetaData();
			printColumnTitles(rsmd);
			int num_col = rsmd.getColumnCount();
			while(rs.next()){
				for(int i = 1;i<=num_col;i++){
					System.out.format("%-20s", rs.getString(i));
				}
				System.out.print("\n");
			}
			cs.close();
		} catch (SQLException e) {
			String ex = e.getLocalizedMessage().split("\n")[0].split(": ")[1];
			System.out.println(ex);
		}
	}
	public String getUserInput() {
		String class_id = null;
		System.out.print("Enter a class id: ");
		try {
			class_id = input.readLine();
		} catch (IOException e) {
			System.out.println("Unable to read from input!");
			System.exit(0);
		}
		return class_id;
	}

	public void printColumnTitles(ResultSetMetaData rsmdIn) throws SQLException {
		int num_col = rsmdIn.getColumnCount();
		for(int i = 1; i<=num_col; i++){
			System.out.format("%-20s", rsmdIn.getColumnLabel(i));
		}
		System.out.println("\n");
	}
}
