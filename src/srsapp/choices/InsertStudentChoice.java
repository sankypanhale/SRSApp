package srsapp.choices;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import srsapp.util.ChoiceAbstract;
import srsapp.util.StudentInfo;

public class InsertStudentChoice extends ChoiceAbstract{

	private StudentInfo student;
	public InsertStudentChoice(BufferedReader in, Connection c){
		this.setInput(in);
		this.setConn(c);
	}
	
	@Override
	public void processUserInput() {
		student = getStudentInfoFromUser();
		insertStudentToDatabase();
	}

	public void insertStudentToDatabase(){
		 try {
				CallableStatement cs = conn.prepareCall("begin SRSJDBC.insertstudent(?,?,?,?,?,?); end;");
				cs.setString(1, student.getSid());
				cs.setString(2, student.getFirstname());
				cs.setString(3, student.getLastname());
				cs.setString(4, student.getStatus());
				cs.setString(5, student.getGpa());
				cs.setString(6, student.getEmail());
				
				 // execute and retrieve the result set
		        cs.executeUpdate();
		        cs.close();
		 }catch(SQLException e){
			 String ex = e.getLocalizedMessage().split("\n")[0].split(": ")[1];
			 System.out.println(ex);
		 }
	}
	public StudentInfo getStudentInfoFromUser(){
		StudentInfo myStudent = new StudentInfo();
		try{
			System.out.print("Enter Sid: ");
			myStudent.setSid(input.readLine());
			System.out.print("Enter Firstname: ");
			myStudent.setFirstname(input.readLine());
			System.out.print("Enter LastName: ");
			myStudent.setLastname(input.readLine());
			System.out.print("Enter status: ");
			myStudent.setStatus(input.readLine());
			System.out.print("Enter GPA: ");
			myStudent.setGpa(input.readLine());
			System.out.print("Enter Emailid: ");
			myStudent.setEmail(input.readLine());
		} catch(IOException e){
			System.out.println("Unable to read the input!");
			System.exit(0);
		}
		return myStudent;
	}
}
