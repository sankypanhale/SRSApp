package srsapp.choices;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import srsapp.util.ChoiceAbstract;

public class DeleteStudentChoice extends ChoiceAbstract {
	
	public DeleteStudentChoice(BufferedReader in, Connection c) {
		this.setInput(in);
		this.setConn(c);
	}
	@Override
	public void processUserInput() {
		String sid = getStudentIdFromUser();
		deleteStudent(sid);
	}
	public void deleteStudent(String sid) {
		CallableStatement cs;
		try {
			cs = getConn().prepareCall("begin SRSJDBC.delete_student(?); end;");
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
			msg = getInput().readLine();
		} catch (IOException e) {
			System.out.println("Unable to read the input");
			System.exit(0);
		}
		return msg;
	}
}
