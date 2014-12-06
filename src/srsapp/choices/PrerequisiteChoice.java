package srsapp.choices;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import oracle.jdbc.OracleTypes;
import srsapp.util.ChoiceAbstract;

@SuppressWarnings("serial")
public class PrerequisiteChoice extends ChoiceAbstract{
	JLabel jlmsg;
	private List<String> outputList;
	public PrerequisiteChoice(BufferedReader in, Connection c) {
		this.setInput(in);
		this.setConn(c);
		jlmsg = new JLabel();
		outputList = new ArrayList<String>();
	}
	@Override
	public void processUserInput() {
		getDeptCode();
	}
	
	public void printPrerequisites(String dept, String course){
		CallableStatement cs;
		jlmsg.setVisible(false);
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
	        	outputList.add(temp_dept+temp_course);
	        	printPrerequisites(temp_dept,temp_course);
	        }
			cs.close();
		}catch(SQLException e){
			jlmsg.setText("Error in finding prerequisites courses!");
			jlmsg.setVisible(true);
		}
	}
	private void getDeptCode() {
		JButton jbSubmit,jbCancel;
		final JTextField jtDept,jtCourse;
		JLabel jlDept,jlClassid;
		setTitle("Student Registation System");
		getContentPane().setLayout(null);
		
		jlDept = new JLabel("Enter DepartmentName: ");
		jlDept.setSize(100,20);
		jlDept.setLocation(50,50);
		add(jlDept);
		
		jlClassid = new JLabel("Enter Course Number: ");
		jlClassid.setSize(100,20);
		jlClassid.setLocation(50,100);
		add(jlClassid);
		
		jlmsg.setSize(200,50);
		jlmsg.setLocation(50,200);
		jlmsg.setVisible(false);
		add(jlmsg);
		
		jtDept = new JTextField();
		jtDept.setToolTipText("Enter Sid");
		jtDept.setSize(100,20);
		jtDept.setLocation(150,50);
		add(jtDept);
		
		jtCourse = new JTextField();
		jtCourse.setToolTipText("Enter Course#");
		jtCourse.setSize(100,20);
		jtCourse.setLocation(150,100);
		add(jtCourse);
		
		jbSubmit = new JButton("Submit");
		jbSubmit.setSize(100,30);
		jbSubmit.setLocation(50, 150);
		add(jbSubmit);
		
		jbCancel = new JButton("Cancel");
		jbCancel.setSize(100,30);
		jbCancel.setLocation(180, 150);
		add(jbCancel);
		
		jbCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		jbSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				printPrerequisites(jtDept.getText(), jtCourse.getText());
				if(jlmsg.isVisible()){
					return;
				}
				jtDept.setText("");
				jtCourse.setText("");
				JFrame resultFrame = new JFrame();
				Vector<Vector<String>> rowData = new Vector<Vector<String>>();
				ListIterator<String> i = outputList.listIterator();
				while(i.hasNext()){
					Vector<String> v = new Vector<String>();
					v.addElement(i.next().toString());
					rowData.addElement(v);
				}
				Vector<String> columnNames = new Vector<String>();
				columnNames.addElement("Prerequisites");
				JTable resultTable = new JTable(rowData, columnNames);
				JScrollPane scrollPane = new JScrollPane(resultTable);
				resultFrame.add(scrollPane, BorderLayout.CENTER);
				resultFrame.setFocusable(true);
				resultFrame.setLocation(550, 150);
				resultFrame.setSize(200,400);
				resultFrame.setVisible(true);
				outputList.clear();
			}
		});
		
		setLocation(450,150);
		setSize(400,400);
		setVisible(true);
	}
	
	@Override
	public void setFields() {
		// TODO Auto-generated method stub
		
	}

}
