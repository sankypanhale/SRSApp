package srsapp.choices;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import oracle.jdbc.OracleTypes;
import srsapp.util.ChoiceAbstract;

@SuppressWarnings("serial")
public class DisplayTableChoice extends ChoiceAbstract{
	
	private int num_col;
	public DisplayTableChoice(BufferedReader in, Connection c){
		this.setInput(in);
		this.setConn(c);
	}
	
	public void displaySubmenu(){
		System.out.println("1.Show Students\n2.Show Enrollments\n3.Show Classes\n4.Show Courses\n5.Show Prerequisites\n6.Show Logs");
	}
	
	@Override
	public void processUserInput(){

		JButton jbStudents, jbEnrollments, jbClasses, jbCourses, jbPrerequisites, jbLogs, jbExit;
		setTitle("Student Registation System");
		getContentPane().setLayout(null);
		
		jbStudents = new JButton("Students");
		jbStudents.setSize(150,30);
		jbStudents.setLocation(125, 20);
		add(jbStudents);
		
		jbEnrollments = new JButton("Enrollments");
		jbEnrollments.setSize(150,30);
		jbEnrollments.setLocation(125, 70);
		add(jbEnrollments);
		
		jbClasses = new JButton("Classes");
		jbClasses.setSize(150,30);
		jbClasses.setLocation(125, 120);
		add(jbClasses);
		
		jbCourses = new JButton("Courses");
		jbCourses.setSize(150,30);
		jbCourses.setLocation(125, 170);
		add(jbCourses);
		
		jbPrerequisites = new JButton("Prerequisites");
		jbPrerequisites.setSize(150,30);
		jbPrerequisites.setLocation(125, 220);
		add(jbPrerequisites);
		
		jbLogs = new JButton("Logs");
		jbLogs.setSize(150,30);
		jbLogs.setLocation(125, 270);
		add(jbLogs);
		
		jbExit = new JButton("Back");
		jbExit.setSize(150,30);
		jbExit.setLocation(125, 420);
		add(jbExit);
		
		jbStudents.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				printTuples("students");
			}
		});
		
		jbEnrollments.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				printTuples("enrollments");
			}
		});

		jbClasses.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				printTuples("classes");
			}
		});

		jbCourses.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				printTuples("courses");
			}
		});

		jbPrerequisites.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				printTuples("prereq");
			}
		});

		jbLogs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				printTuples("logs");
			}
		});

		jbExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		setLocation(450,130);
		setSize(400,500);
		setVisible(true);
	}

	public void printTuples(String tableName) {
		 try {
			CallableStatement cs = conn.prepareCall("begin SRSJDBC.show_" + tableName + "(?); end;");
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			 // execute and retrieve the result set
	        cs.execute();
	        ResultSet rs = (ResultSet)cs.getObject(1);
	        ResultSetMetaData rsmd = rs.getMetaData();
	        num_col = rsmd.getColumnCount();
	        JFrame resultFrame = new JFrame();
	        Vector<String> columnNames = new Vector<String>();
	        for(int i = 1; i<=num_col; i++){
	        	columnNames.addElement(rsmd.getColumnLabel(i));
			}
	        Vector<Vector<String>> rowData = new Vector<Vector<String>>();
	        while(rs.next()){
	        	Vector<String> v = new Vector<String>();
	        	for(int i = 1;i<=num_col;i++){
	        		v.addElement(rs.getString(i));
	        	}
	        	rowData.addElement(v);
	        }
	        cs.close();
	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	        JTable resultTable = new JTable(rowData, columnNames);
	        for(int i = 0; i < resultTable.getColumnCount(); i++){
	        	resultTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	        }
	        resultTable.getTableHeader().setBackground(Color.BLUE);
	        resultTable.getTableHeader().setForeground(Color.white);
			JScrollPane scrollPane = new JScrollPane(resultTable);
			resultFrame.add(scrollPane, BorderLayout.CENTER);
			resultFrame.setFocusable(true);
			resultFrame.setLocation(350, 150);
			resultFrame.setSize(600,400);
			resultFrame.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL exception caught during printTuples()!");
		} 
	}

	public void printColumnTitles(ResultSetMetaData rsmdIn) throws SQLException {
		int num_col = rsmdIn.getColumnCount();
		for(int i = 1; i<=num_col; i++){
		//	System.out.format("%-20s", rsmdIn.getColumnLabel(i));
		}
		//System.out.println("\n");
	}

	@Override
	public void setFields() {		
	}
}
