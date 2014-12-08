package srsapp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import srsapp.driver.MainDriver;

@SuppressWarnings("serial")
public class LoginForm extends UserInterface {

	public LoginForm() {
		setFields();
	}
	public static void main(String[] args) {
		new LoginForm();
	}

	@Override
	public void setFields() {
		// TODO Auto-generated method stub
		JButton jbStuLogin;
		final JLabel jlInvalid;
		final JTextField jtStuUsername;
		final JTextField jtStuPassword;
		JLabel jlStuUsername,jlStuPassword;
		setTitle("Student Registation System");
		getContentPane().setLayout(null);
		
		jlStuUsername = new JLabel("User Name: ");
		jlStuUsername.setSize(100,20);
		jlStuUsername.setLocation(50,50);
		add(jlStuUsername);
		
		jlStuPassword = new JLabel("Password: ");
		jlStuPassword.setSize(100,20);
		jlStuPassword.setLocation(50,100);
		add(jlStuPassword);
		
		jlInvalid = new JLabel("Invalid Username or password");
		jlInvalid.setSize(100,20);
		jlInvalid.setLocation(150,180);
		jlInvalid.setVisible(false);
		add(jlInvalid);
		
		jtStuUsername = new JTextField();
		jtStuUsername.setToolTipText("Enter User Name");
		jtStuUsername.setSize(100,20);
		jtStuUsername.setLocation(150,50);
		add(jtStuUsername);
		
		jtStuPassword = new JPasswordField();
		jtStuPassword.setToolTipText("Enter Password");
		jtStuPassword.setSize(100,20);
		jtStuPassword.setLocation(150,100);
		add(jtStuPassword);
		
		jbStuLogin = new JButton("Login");
		jbStuLogin.setSize(100,30);
		jbStuLogin.setLocation(150, 150);
		add(jbStuLogin);
		
		jbStuLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Connection conn = null;
				MainDriver mainobj = new MainDriver();
				conn = mainobj.mainMethod(jtStuUsername.getText(),jtStuPassword.getText());
				if(conn == null)
				{
					//dispose();
					jtStuUsername.setText("");
					jtStuPassword.setText("");
					jlInvalid.setVisible(true);
				}
				else
				{
					dispose();
					new WorkerForm(conn);
				}
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(450,150);
		setSize(400,400);
		setVisible(true);
	}
}
