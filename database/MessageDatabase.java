package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MessageDatabase {
	private static Connection con;
	private static boolean hasData = false;
	
	public ResultSet getMessageDatabase() throws ClassNotFoundException, SQLException {
		if (con == null) {
			getConnection();
		}
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT id, chat_id, txt, date, username FROM message");
		return res;
	}
	
	private static void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:MessageDatabase.db");
		initialise();
	}

	private static void initialise() throws SQLException {
		if (!hasData) {
			hasData = true;
			
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='message'");
			if(!res.next()) {
				System.out.println("Message database created.");
				// need t built the table
				Statement state2 = con.createStatement();
				state2.execute("CREATE TABLE message(id text,"
						+ "chat_id text, txt text, date text, username text);");
			}
		}
	}
	
	public static void addMessage(String id, String chat_id
			                  , String txt, String date
			                  , String username) throws ClassNotFoundException, SQLException {
		if(con == null) {
			getConnection();
		}
		PreparedStatement prep = con.prepareStatement("INSERT INTO message values(?,?,?,?,?);");
		prep.setString(1, id);
		prep.setString(2, chat_id);
		prep.setString(3, txt);
		prep.setString(4, date);
		prep.setString(5, username);
		prep.execute();
	}
}

