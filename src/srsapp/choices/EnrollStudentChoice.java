package srsapp.choices;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import srsapp.util.ChoiceAbstract;

public class EnrollStudentChoice extends ChoiceAbstract{

	public EnrollStudentChoice(BufferedReader in, Connection c) {
		this.setInput(in);
		this.setConn(c);
	}
	@Override
	public void processUserInput() {
		String sid = getStudentIdFromUser();
		String class_id = getClassIdFromUser();
		enrollStudentInClass(sid, class_id);
	}
	
	
	public void enrollStudentInClass(String sid, String class_id) {
		CallableStatement cs;
		try {
			cs = conn.prepareCall("begin SRSJDBC.enroll_student(?, ?); end;");
			cs.setString(1, sid);
			cs.setString(2, class_id);
			cs.executeUpdate();
			cs.close();
		} catch (SQLException e) {
			String ex = e.getLocalizedMessage().split("\n")[0].split(": ")[1];
			System.out.println(ex);
		}
	}
	public String getClassIdFromUser() {
		String msg = null;
		System.out.print("Enter a Classid: ");
		try {
			msg = input.readLine();
		} catch (IOException e) {
			System.out.println("Unable to read the input");
			System.exit(0);
		}
		return msg;
	}
	public String getStudentIdFromUser() {
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
	
	

}
