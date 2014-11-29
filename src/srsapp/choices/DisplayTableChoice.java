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

public class DisplayTableChoice extends ChoiceAbstract{
	
	public DisplayTableChoice(BufferedReader in, Connection c){
		this.setInput(in);
		this.setConn(c);
	}
	
	public void displaySubmenu(){
		System.out.println("1.Show Students\n2.Show Enrollments\n3.Show Classes\n4.Show Courses\n5.Show Prerequisites\n6.Show Logs");
	}
	
	@Override
	public void processUserInput(){
		String userInput;
		int choice = 0;
		displaySubmenu();
		try {
			userInput = input.readLine();
			choice = Integer.parseInt(userInput);
		} catch (IOException e) {
			System.out.println("Unable to read the input!");
		}
		switch(choice){
		
		case 1:
			printTuples("students");
			break;
		case 2:
			printTuples("enrollments");
			break;
		case 3:
			printTuples("classes");
			break;
		case 4:
			printTuples("courses");
			break;
		case 5:
			printTuples("prereq");
			break;
		case 6:
			printTuples("logs");
			break;
		default:
			System.out.println("Invalid Input");
			break;
		}
	}

	public void printTuples(String tableName) {
		 try {
			CallableStatement cs = conn.prepareCall("begin SRSJDBC.show_" + tableName + "(?); end;");
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			 // execute and retrieve the result set
	        cs.execute();
	        ResultSet rs = (ResultSet)cs.getObject(1);
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
			e.printStackTrace();
			System.out.println("SQL exception caught during printTuples()!");
		} 
	}

	public void printColumnTitles(ResultSetMetaData rsmdIn) throws SQLException {
		int num_col = rsmdIn.getColumnCount();
		for(int i = 1; i<=num_col; i++){
			System.out.format("%-20s", rsmdIn.getColumnLabel(i));
		}
		System.out.println("\n");
	}
}
