package srsapp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import srsapp.util.Worker;

@SuppressWarnings("serial")
/** 
 * This class will show all the primary options available 
 * */
public class WorkerForm extends UserInterface{

	private Connection connection;
	//constructor
	public WorkerForm(Connection conn) {
		// TODO Auto-generated constructor stub
		setConnection(conn);
		setFields();
	}
	/**
	 * This method will create the frame and set all labels and buttons 
	 * */
	@Override
	public void setFields() {
		// TODO Auto-generated method stub
		JButton jbDisplayTable,jbInsertStudent,jbShowCoursesForStudent,jbShowPreq;
		JButton jbShowClassDetails,jbEnrollStudent,jbDropStudent,jbDeleteStudent,jbExit;
		
		
		setTitle("Student Registation System");
		getContentPane().setLayout(null);
		
		ImageIcon img = new ImageIcon("appicon.png");
		setIconImage(img.getImage());
		
		jbDisplayTable = new JButton("Show Tables");
		jbDisplayTable.setSize(200,30);
		jbDisplayTable.setLocation(100, 20);
		add(jbDisplayTable);
		
		jbInsertStudent = new JButton("Insert Student");
		jbInsertStudent.setSize(200,30);
		jbInsertStudent.setLocation(100, 70);
		add(jbInsertStudent);
		
		jbShowCoursesForStudent = new JButton("Show Courses for Student");
		jbShowCoursesForStudent.setSize(200,30);
		jbShowCoursesForStudent.setLocation(100, 120);
		add(jbShowCoursesForStudent);
		
		jbShowPreq = new JButton("Show Prerequisites Courses");
		jbShowPreq.setSize(200,30);
		jbShowPreq.setLocation(100, 170);
		add(jbShowPreq);
		
		jbShowClassDetails = new JButton("Show Class Details");
		jbShowClassDetails.setSize(200,30);
		jbShowClassDetails.setLocation(100, 220);
		add(jbShowClassDetails);
		
		jbEnrollStudent = new JButton("Enroll Student");
		jbEnrollStudent.setSize(200,30);
		jbEnrollStudent.setLocation(100, 270);
		add(jbEnrollStudent);
		
		jbDropStudent = new JButton("Drop Student from class");
		jbDropStudent.setSize(200,30);
		jbDropStudent.setLocation(100, 320);
		add(jbDropStudent);
		
		jbDeleteStudent = new JButton("Delete Student");
		jbDeleteStudent.setSize(200,30);
		jbDeleteStudent.setLocation(100, 370);
		add(jbDeleteStudent);
		
		jbExit = new JButton("Exit");
		jbExit.setSize(100,30);
		jbExit.setLocation(150, 420);
		add(jbExit);
		
		
		final Worker guiWorker = new Worker(connection);
		
		//choice 1
		jbDisplayTable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiWorker.processInput(1);
			}
		});
		
		//choice 2
		jbInsertStudent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiWorker.processInput(2);
			}
		});
		
		//choice 3
		jbShowCoursesForStudent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiWorker.processInput(3);
			}
		});
		
		//choice 4
		jbShowPreq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiWorker.processInput(4);
			}
		});
		
		//choice 5
		jbShowClassDetails.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiWorker.processInput(5);
			}
		});
		
		//choice 6
		jbEnrollStudent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiWorker.processInput(6);
			}
		});
		
		//choice 7
		jbDropStudent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiWorker.processInput(7);
			}
		});
		
		//choice 8
		jbDeleteStudent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiWorker.processInput(8);
			}
		});
		
		jbExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(450,130);
		setSize(400,500);
		setVisible(true);
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
