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
public class ClassDetailsChoice extends ChoiceAbstract{
	
	JLabel jlmsg;
	int num_col;
	private List<String> outputList;
	public ClassDetailsChoice(BufferedReader in, Connection connIn) {
		this.setInput(in);
		this.setConn(connIn);
		jlmsg = new JLabel();
		outputList = new ArrayList<String>();
	}
	@Override
	public void processUserInput() {
		getUserInput();
	}
	
	public void printClassInfo(String classIn){
		CallableStatement cs;
		jlmsg.setVisible(false);
		try {
			cs = conn.prepareCall("begin SRSJDBC.class_details(?, ?); end;");
			cs.setString(1, classIn);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
			ResultSet rs = (ResultSet)cs.getObject(2);
			ResultSetMetaData rsmd = rs.getMetaData();
			num_col = rsmd.getColumnCount();
			while(rs.next()){
				for(int i = 1;i<=num_col;i++){
					//System.out.format("%-20s", rs.getString(i));
					outputList.add(rs.getString(i).trim());
				}
				//System.out.print("\n");
			}
			cs.close();
		} catch (SQLException e) {
			String ex = e.getLocalizedMessage().split("\n")[0].split(": ")[1];
			jlmsg.setText(ex);
			jlmsg.setVisible(true);
		}
	}
	public void getUserInput() {
		JButton jbSubmit,jbCancel;
		final JTextField jtClassid;
		JLabel jlClassid;
		setTitle("Student Registation System");
		getContentPane().setLayout(null);
		
		jlClassid = new JLabel("Enter Classid: ");
		jlClassid.setSize(100,20);
		jlClassid.setLocation(50,50);
		add(jlClassid);
		
		jlmsg.setSize(200,50);
		jlmsg.setLocation(50,200);
		jlmsg.setVisible(false);
		add(jlmsg);
		
		jtClassid = new JTextField();
		jtClassid.setToolTipText("Enter Classid");
		jtClassid.setSize(100,20);
		jtClassid.setLocation(150,50);
		add(jtClassid);
		
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
				printClassInfo(jtClassid.getText());
				jtClassid.setText("");
				//do not continue if there is an exception in printClassInfo
				if(jlmsg.isVisible()){
					return;
				}
				JFrame resultFrame = new JFrame();
				Vector<Vector<String>> rowData = new Vector<Vector<String>>();
				ListIterator<String> i = outputList.listIterator();
				while(i.hasNext()){
					Vector<String> v = new Vector<String>();
					for(int j = 0; j<num_col; j++){
						v.addElement(i.next().toString().trim());
					}
					rowData.addElement(v);
				}
				Vector<String> columnNames = new Vector<String>();
				columnNames.addElement("CLASSID");
				columnNames.addElement("COURSE_TITLE");
				columnNames.addElement("SID");
				columnNames.addElement("FIRSTNAME");
				JTable resultTable = new JTable(rowData, columnNames);
				resultTable.getTableHeader().setBackground(Color.BLUE);
		        resultTable.getTableHeader().setForeground(Color.white);
				JScrollPane scrollPane = new JScrollPane(resultTable);
				resultFrame.add(scrollPane, BorderLayout.CENTER);
				resultFrame.setFocusable(true);
				resultFrame.setLocation(450, 150);
				resultFrame.setSize(400,400);
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
	}
}
