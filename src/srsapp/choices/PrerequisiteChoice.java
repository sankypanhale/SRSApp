package srsapp.choices;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;
import srsapp.util.ChoiceAbstract;

public class PrerequisiteChoice extends ChoiceAbstract{

	public PrerequisiteChoice(BufferedReader in, Connection c) {
		this.setInput(in);
		this.setConn(c);
	}
	@Override
	public void processUserInput() {
		String deptCode = getDeptCode();
		String courseNum = getCourseId();
		printPrerequisites(deptCode, courseNum);
	}
	
	public void printPrerequisites(String dept, String course){
		CallableStatement cs;
		try {
			cs = conn.prepareCall("begin SRSJDBC.get_prereq(?, ?, ?); end;");
			cs.setString(1, dept);
			cs.setString(2, course);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			 // execute and retrieve the result set
	        cs.execute();
	        ResultSet rs = (ResultSet)cs.getObject(3);
	        while(rs.next()){
	        	String temp_dept = rs.getString(3);
	        	String temp_course = rs.getString(4);
	        	System.out.println(temp_dept + temp_course);
	        	printPrerequisites(temp_dept,temp_course);
	        }
			cs.close();
		}catch(SQLException e){
			System.out.println("Error in finding prerequisites courses!");
		}
	}
	private String getDeptCode() {
		String dept = null;
		System.out.print("Enter Department code(e.g. CS): ");
		try {
			dept = input.readLine();
		} catch (IOException e) {
			System.out.println("Unable to read from input!");
			System.exit(0);
		}
		return dept;
	}
	private String getCourseId() {
		String courseId = null;
		System.out.print("Enter course number: ");
		try {
			courseId = input.readLine();
		} catch (IOException e) {
			System.out.println("Unable to read from input!");
			System.exit(0);
		}
		return courseId;
	}

}
