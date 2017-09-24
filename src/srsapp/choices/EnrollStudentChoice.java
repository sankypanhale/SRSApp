package srsapp.choices;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import oracle.jdbc.OracleTypes;
import srsapp.util.ChoiceAbstract;

/**
* This class will enroll student to class
* */
@SuppressWarnings("serial")
public class EnrollStudentChoice extends ChoiceAbstract{

	JLabel jlmsg;
	public EnrollStudentChoice(BufferedReader in, Connection c) {
		this.setInput(in);
		this.setConn(c);
		jlmsg = new JLabel();
	}
	@Override
	public void processUserInput() {
		getStudentIdFromUser();
		
	}
	/**
	 * This method will take sid and class_id and enroll student in class
	 * */
	public void enrollStudentInClass(String sid, String class_id) {
		CallableStatement cs;
		jlmsg.setVisible(false);
		String mymsg = "Student has been enrolled.";
		try {
			cs = conn.prepareCall("begin SRSJDBC.enroll_student(?, ?, ?); end;");
			cs.setString(1, sid);
			cs.setString(2, class_id);
			cs.registerOutParameter(3, OracleTypes.NUMBER);
			cs.executeUpdate();
			int overloadcount = cs.getInt(3);
			if(overloadcount == 3){
				mymsg = mymsg + " You are Overloaded!";
			}
			cs.close();
			jlmsg.setText("<html>" + mymsg + "</html>");
			jlmsg.setVisible(true);
		} catch (SQLException e) {
			String ex = e.getLocalizedMessage().split("\n")[0].split(": ")[1];
			jlmsg.setText("<html>" + ex + "</html>");
			jlmsg.setVisible(true);
		}
	}
	
	/**
	 * This method will create the frame and set all labels,textbox
	 * and buttons 
	 * */
	public void getStudentIdFromUser() {
		JButton jbSubmit,jbCancel;
		final JTextField jtSid,jtClassid;
		JLabel jlSid,jlClassid;
		setTitle("Student Registation System");
		getContentPane().setLayout(null);
		
		ImageIcon img = new ImageIcon("appicon.png");
		setIconImage(img.getImage());
		
		jlSid = new JLabel("Enter Sid: ");
		jlSid.setSize(100,20);
		jlSid.setLocation(50,50);
		add(jlSid);
		
		jlClassid = new JLabel("Enter Classid: ");
		jlClassid.setSize(100,20);
		jlClassid.setLocation(50,100);
		add(jlClassid);
		
		jlmsg.setSize(200,300);
		jlmsg.setLocation(50,160);
		jlmsg.setVisible(false);
		add(jlmsg);
		
		jtSid = new JTextField();
		jtSid.setToolTipText("Enter Sid");
		jtSid.setSize(100,20);
		jtSid.setLocation(150,50);
		add(jtSid);
		
		jtClassid = new JTextField();
		jtClassid.setToolTipText("Enter Classid");
		jtClassid.setSize(100,20);
		jtClassid.setLocation(150,100);
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
				enrollStudentInClass(jtSid.getText(), jtClassid.getText());
				jtSid.setText("");
				jtClassid.setText("");
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
