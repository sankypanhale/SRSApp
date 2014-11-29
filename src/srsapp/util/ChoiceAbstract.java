package srsapp.util;

import java.io.BufferedReader;
import java.sql.Connection;

public abstract class ChoiceAbstract {

	protected BufferedReader input;
	protected Connection conn;
	
	protected abstract void processUserInput();
	
	public BufferedReader getInput() {
		return input;
	}
	public void setInput(BufferedReader input) {
		this.input = input;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
}
