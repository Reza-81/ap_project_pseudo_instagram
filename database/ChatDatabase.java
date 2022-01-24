package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChatDatabase {
	
	private static Connection con;
	private static boolean hasData = false;
	
	public ResultSet getChatDatabase() throws ClassNotFoundException, SQLException {
		if (con == null) {
			getConnection();
		}
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT id, type, user1, user2,"
				                       + " name, list_members, list_admins FROM chat");
		return res;
	}
	
	private static void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:ChatDatabase.db");
		initialise();
	}

	private static void initialise() throws SQLException {
		if (!hasData) {
			hasData = true;
			
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='chat'");
			if(!res.next()) {
				System.out.println("Chat database created.");
				// need t built the table
				Statement state2 = con.createStatement();
				state2.execute("CREATE TABLE chat(id text,"
						+ "type text, user1 text, user2 text,"
					    + "name text, list_members text,"
					    + "list_admins text);");
			}
		}
	}
	
	public static void addChat(String id, String type
			                  , String user1, String user2
			                  , String name
			                  , ArrayList<String> list_members
			                  , ArrayList<String> list_admins) throws ClassNotFoundException, SQLException {
		if(con == null) {
			getConnection();
		}
		PreparedStatement prep = con.prepareStatement("INSERT INTO chat values(?,?,?,?,?,?,?);");
		prep.setString(1, id);
		prep.setString(2, type);
		prep.setString(3, user1);
		prep.setString(4, user2);
		prep.setString(5, name);
		prep.setString(6, list_members.toString());
		prep.setString(7, list_admins.toString());
		prep.execute();
	}
}
