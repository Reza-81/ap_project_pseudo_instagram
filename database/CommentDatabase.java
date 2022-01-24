package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommentDatabase {

	private static Connection con;
	private static boolean hasData = false;
	
	public ResultSet getCommentDatabase() throws ClassNotFoundException, SQLException {
		if (con == null) {
			getConnection();
		}
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT id, post_id, message,"
				        + " date, username FROM comment");
		return res;
	}
	
	private static void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:CommentDatabase.db");
		initialise();
	}

	private static void initialise() throws SQLException {
		if (!hasData) {
			hasData = true;
			
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='comment'");
			if(!res.next()) {
				System.out.println("Comment database created.");
				// need t built the table
				Statement state2 = con.createStatement();
				state2.execute("CREATE TABLE comment(id text,"
						+ "post_id text, message text, date text," + "username text);");
			}
		}
	}
	
	public static void addComment(String id, String post_id
			                  , String message, String date 
			                  , String username) throws ClassNotFoundException, SQLException {
		if(con == null) {
			getConnection();
		}
		PreparedStatement prep = con.prepareStatement("INSERT INTO comment values(?,?,?,?,?);");
		prep.setString(1, id);
		prep.setString(2, post_id);
		prep.setString(5, username);
		prep.setString(4, message);
		prep.setString(3, date);
		prep.execute();
	}
}
