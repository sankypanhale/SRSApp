package srsapp.choices;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.internal.OracleTypes;
import oracle.jdbc.oracore.OracleType;
import srsapp.util.ChoiceI;

public class DropStudentChoice implements ChoiceI {

	private BufferedReader input;
	private Connection conn;
	
	public DropStudentChoice(BufferedReader in, Connection c) {
		input = in;
		conn = c;
	}
	@Override
	public void processUserInput() {
		// TODO Auto-generated method stub
		String sid = getStudentIdFromUser();
		String class_id = getClassIdFromUser();
		dropStudentInClass(sid, class_id);
	}
	public void dropStudentInClass(String sid, String class_id) {
		CallableStatement cs;
		try {
			cs = conn.prepareCall("begin SRSJDBC.drop_student(?, ?, ?, ?); end;");
			cs.setString(1, sid);
			cs.setString(2, class_id);
			cs.registerOutParameter(3, OracleTypes.NUMBER);
			cs.registerOutParameter(4, OracleTypes.NUMBER);
			cs.executeUpdate();
			
			//ResultSet rs = (ResultSet)cs.getObject(3);
			int studCount = cs.getInt(3);
			int classCount = cs.getInt(4);
			if (studCount == 0)
			{
				System.out.println("This student is not enrolled in any classes.");
			}
			if (classCount == 0)
			{
				System.out.println("The class now has no students.");
			}
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
