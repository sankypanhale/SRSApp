package srsapp.util;

import java.sql.Connection;

public class Worker {

	private Connection connection;
	public Worker(Connection conn){
		setConnection(conn);
	}
	
	public void printMenu(){
		System.out.println("User Menu\n1.Display a Table.\n2.Enroll a student in a class.\n3.Show details of the class.\n4.Show Prerequisites of a course.\n5.Drop a Student from the class.");
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
