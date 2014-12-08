package srsapp.choices;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import srsapp.util.ChoiceAbstract;
import srsapp.util.StudentInfo;

/**
* This class will insert new student to database
* */
@SuppressWarnings("serial")
public class InsertStudentChoice extends ChoiceAbstract{
	JLabel jlmsg;
	private StudentInfo student;
	public InsertStudentChoice(BufferedReader in, Connection c){
		this.setInput(in);
		this.setConn(c);
		jlmsg = new JLabel();
	}

	@Override
	public void processUserInput() {
		student = getStudentInfoFromUser();
	}

	/**
	 * This method will take firstname,lastname,status,gpa and email
	 * and insert the records into the database
	 * */
	public void insertStudentToDatabase(){
		try {
			jlmsg.setVisible(false);
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
			jlmsg.setText("Student has been Added");
			jlmsg.setVisible(true);
		}catch(SQLException e){
			String ex = e.getLocalizedMessage().split("\n")[0].split(": ")[1];
			jlmsg.setText(ex);
			jlmsg.setVisible(true);
		}
	}
	
	/**
	 * This method will create the frame and set all labels,textbox
	 * and buttons 
	 * */
	public StudentInfo getStudentInfoFromUser(){
		@SuppressWarnings("rawtypes")
		final JComboBox jcStatus;
		final StudentInfo myStudent = new StudentInfo();
		JButton jbStuLogin,jbCancel;
		final JTextField jtSid,jtFirstName,jtLastName,jtGPA,jtEmail;
		JLabel jlSid,jlFirstName,jlLastName,jlStatus,jlGPA,jlEmail;
		setTitle("Student Registation System");
		getContentPane().setLayout(null);

		ImageIcon img = new ImageIcon("appicon.png");
		setIconImage(img.getImage());
		
		jlmsg.setSize(200,50);
		jlmsg.setLocation(50,400);
		jlmsg.setVisible(false);
		add(jlmsg);
		
		jlSid = new JLabel("Sid: ");
		jlSid.setSize(100,20);
		jlSid.setLocation(50,20);
		add(jlSid);

		jlFirstName = new JLabel("FirstName: ");
		jlFirstName.setSize(100,20);
		jlFirstName.setLocation(50,70);
		add(jlFirstName);

		jlLastName = new JLabel("LastName: ");
		jlLastName.setSize(100,20);
		jlLastName.setLocation(50,120);
		add(jlLastName);

		jlStatus = new JLabel("Status: ");
		jlStatus.setSize(100,20);
		jlStatus.setLocation(50,170);
		add(jlStatus);

		jlGPA = new JLabel("GPA: ");
		jlGPA.setSize(100,20);
		jlGPA.setLocation(50,220);
		add(jlGPA);

		jlEmail = new JLabel("Email: ");
		jlEmail.setSize(100,20);
		jlEmail.setLocation(50,270);
		add(jlEmail);

		jtSid = new JTextField();
		jtSid.setSize(100,20);
		jtSid.setLocation(150,20);
		add(jtSid);

		jtFirstName = new JTextField();
		jtFirstName.setSize(100,20);
		jtFirstName.setLocation(150,70);
		add(jtFirstName);

		jtLastName = new JTextField();
		jtLastName.setSize(100,20);
		jtLastName.setLocation(150,120);
		add(jtLastName);

		String[] temp = {"freshman","sophomore","junior","senior","graduate"};
		jcStatus = new JComboBox<String>(temp);
		jcStatus.setSize(100,20);
		jcStatus.setLocation(150,170);
		add(jcStatus);

		jtGPA = new JTextField();
		jtGPA.setSize(100,20);
		jtGPA.setLocation(150,220);
		add(jtGPA);

		jtEmail = new JTextField();
		jtEmail.setSize(100,20);
		jtEmail.setLocation(150,270);
		add(jtEmail);

		jbStuLogin = new JButton("Submit");
		jbStuLogin.setSize(100,30);
		jbStuLogin.setLocation(50, 330);
		add(jbStuLogin);

		jbCancel = new JButton("Cancel");
		jbCancel.setSize(100,30);
		jbCancel.setLocation(180, 330);
		add(jbCancel);

		jbCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		jbStuLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				myStudent.setSid(jtSid.getText());
				myStudent.setFirstname(jtFirstName.getText());
				myStudent.setLastname(jtLastName.getText());
				myStudent.setStatus(jcStatus.getSelectedItem().toString());
				myStudent.setGpa(jtGPA.getText());
				myStudent.setEmail(jtEmail.getText());
				insertStudentToDatabase();
				jtSid.setText("");
				jtFirstName.setText("");
				jtLastName.setText("");
				jtGPA.setText("");
				jtEmail.setText("");
			}
		});

		setLocation(450,150);
		setSize(400,500);
		setVisible(true);
		return myStudent;
	}

	@Override
	public void setFields() {

	}
}
