package srsapp.choices;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.internal.OracleTypes;
import srsapp.util.ChoiceI;

public class DeleteStudentChoice implements ChoiceI {
	private BufferedReader input;
	private Connection conn;
	
	public DeleteStudentChoice(BufferedReader in, Connection c) {
		input = in;
		conn = c;
	}
	@Override
	public void processUserInput() {
		// TODO Auto-generated method stub
		String sid = getStudentIdFromUser();
		deleteStudent(sid);
	}
	public void deleteStudent(String sid) {
		CallableStatement cs;
		try {
			cs = conn.prepareCall("begin SRSJDBC.delete_student(?); end;");
			cs.setString(1, sid);
			cs.executeUpdate();
			cs.close();
		} catch (SQLException e) {
			String ex = e.getLocalizedMessage().split("\n")[0].split(": ")[1];
			System.out.println(ex);
		}
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
