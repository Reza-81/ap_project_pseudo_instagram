package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PostDatabase {

	private static Connection con;
	private static boolean hasData = false;
	
	public ResultSet getPostDatabase() throws ClassNotFoundException, SQLException {
		if (con == null) {
			getConnection();
		}
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT id, file_path, username,"
				        + " caption, date, list_tags, list_likes FROM post");
		return res;
	}
	
	private static void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:PostDatabase.db");
		initialise();
	}

	private static void initialise() throws SQLException {
		if (!hasData) {
			hasData = true;
			
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='post'");
			if(!res.next()) {
				System.out.println("Post database created.");
				// need t built the table
				Statement state2 = con.createStatement();
				state2.execute("CREATE TABLE post(id text,"
						+ "file_path text, username text, caption text,"
					    + "date text, list_tags text,"
					    + "list_likes text);");
			}
		}
	}
	
	public static void addPost(String id, String file_path
			                  , String username, String caption
			                  , String date, String list_tags
			                  , ArrayList<String> list_likes) throws ClassNotFoundException, SQLException {
		if(con == null) {
			getConnection();
		}
		PreparedStatement prep = con.prepareStatement("INSERT INTO post values(?,?,?,?,?,?,?);");
		prep.setString(1, id);
		prep.setString(2, file_path);
		prep.setString(3, username);
		prep.setString(4, caption);
		prep.setString(5, date);
		prep.setString(6, list_tags);
		prep.setString(7, list_likes.toString());
		prep.execute();
	}
	
	public static void update_list_likes(ArrayList<String> list_likes, String id) throws ClassNotFoundException, SQLException {
	    // we will update only first name of a certain row
		if(con == null) {
			getConnection();
		} 
	    PreparedStatement ps = null; 
	    try {
	      String sql = "UPDATE post set list_likes = ? WHERE id = ? ";
	      ps = con.prepareStatement(sql); 
	      ps.setString(1, list_likes.toString());
	      ps.setString(2, id);
	      ps.execute();
	    } catch (SQLException e) {
	      // TODO: handle exception
	      System.out.println(e.toString());
	    }
	}
	
}
