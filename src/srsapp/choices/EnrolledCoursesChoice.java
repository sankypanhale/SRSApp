package srsapp.choices;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;
import srsapp.util.ChoiceI;

public class EnrolledCoursesChoice implements ChoiceI {

	private BufferedReader input;
	private Connection conn;
	
	public EnrolledCoursesChoice(BufferedReader in, Connection c){
		input = in;
		conn = c;
	}
	@Override
	public void processUserInput() {
		String sid = getUserInput();
		printStudentCoursesInfo(sid);
	}
	
	public void printStudentCoursesInfo(String student){
		CallableStatement cs;
		try {
			cs = conn.prepareCall("begin SRSJDBC.show_course_for_student(?, ?); end;");
			cs.setString(1, student);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
			ResultSet rs = (ResultSet)cs.getObject(2);
			ResultSetMetaData rsmd = rs.getMetaData();
			int num_col = rsmd.getColumnCount();
			printColumnTitles(rsmd);
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
			System.exit(0);
		}
	}
	private String getUserInput() {
		String msg = null;
		System.out.print("Enter a Sid: ");
		try {
			msg = input.readLine();
		} catch (IOException e) {
			System.out.println("Unable to read the input");
			System.exit(0);
		}
		return msg;
	}
	
	private void printColumnTitles(ResultSetMetaData rsmdIn) throws SQLException {
		int num_col = rsmdIn.getColumnCount();
		for(int i = 1; i<=num_col; i++){
			System.out.format("%-20s", rsmdIn.getColumnLabel(i));
		}
		System.out.println("\n");
	}

}
