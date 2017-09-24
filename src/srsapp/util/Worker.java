package srsapp.util;

import java.io.BufferedReader;
import java.sql.Connection;
import srsapp.choices.*;

public class Worker {

	private Connection connection;
	private BufferedReader inputReader;
	public Worker(Connection conn){
		setConnection(conn);
	}
	public void processInput(int choice){
		ChoiceAbstract ch = null;
		switch(choice){
		case 1:
			ch = new DisplayTableChoice(inputReader,connection);
			ch.processUserInput();
			break;
		case 2:
			ch = new InsertStudentChoice(inputReader, connection);
			ch.processUserInput();
			break;
		case 3:
			ch = new EnrolledCoursesChoice(inputReader, connection);
			ch.processUserInput();
			break;
		case 4:
			ch = new PrerequisiteChoice(inputReader, connection);
			ch.processUserInput();
			break;
		case 5:
			ch = new ClassDetailsChoice(inputReader, connection);
			ch.processUserInput();
			break;
		case 6:
			ch = new EnrollStudentChoice(inputReader, connection);
			ch.processUserInput();
			break;
		case 7:
			ch = new DropStudentChoice(inputReader, connection);
			ch.processUserInput();
			break;
		case 8:
			ch = new DeleteStudentChoice(inputReader, connection);
			ch.processUserInput();
			break;
		default:
			System.out.println("Invalid Choice!! Please enter a valid Choice!");
			break;
		}
		
	}
	public void printMenu(){
		System.out.println("--------------User Menu------------------\n1.Display a Table.\n2.Insert a Student in students table.\n3.Show enrolled courses for a Student."
				+ "\n4.Show Prerequisites of a Course.\n5.Show Class details\n6.Enroll a Student in a Class\n7.Drop a Student from Class."
				+ "\n8.Delete Student from students table.\n9.Exit\nEnter a Choice:");
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
