package database;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabase {
	
	private static Connection con;
	private static boolean hasData = false;
	
	public ResultSet getUserDatabase() throws ClassNotFoundException, SQLException {
		if (con == null) {
			getConnection();
		}
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT username, password, email,"
				        + " phone_number, bio, profile_picture, list_followings,"
				        + " list_blocks FROM user");
		return res;
	}
	
	private static void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:UserDatabase.db");
		initialise();
	}

	private static void initialise() throws SQLException {
		if (!hasData) {
			hasData = true;
			
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
			if(!res.next()) {
				System.out.println("User database created.");
				// need t built the table
				Statement state2 = con.createStatement();
				state2.execute("CREATE TABLE user(username text,"
						+ "email text, bio text, password text,"
					    + "profile_picture text, phone_number text,"
					    + "list_followings text, list_blocks text);");
			}
		}
	}
	
	public static void addUser(String username, String email
			                  , String bio, String password
			                  , String profile_picture, String phone_number
			                  , ArrayList<String> list_followings
			                  , ArrayList<String> list_blocks) throws ClassNotFoundException, SQLException {
		if(con == null) {
			getConnection();
		}
		PreparedStatement prep = con.prepareStatement("INSERT INTO user values(?,?,?,?,?,?,?,?);");
		prep.setString(1, username);
		prep.setString(2, email);
		prep.setString(3, bio);
		prep.setString(4, password);
		prep.setString(5, profile_picture);
		prep.setString(6, phone_number);
		prep.setString(7, list_followings.toString());
		prep.setString(8, list_blocks.toString());
		prep.execute();
	}
	
	public static void update_list_followings(ArrayList<String> list_followings, String username) throws ClassNotFoundException, SQLException {
	    // we will update only first name of a certain row
		if(con == null) {
			getConnection();
		} 
	    PreparedStatement ps = null; 
	    try {
	      String sql = "UPDATE user set list_followings = ? WHERE username = ? ";
	      ps = con.prepareStatement(sql); 
	      ps.setString(1, list_followings.toString());
	      ps.setString(2, username);
	      ps.execute();
	    } catch (SQLException e) {
	      // TODO: handle exception
	      System.out.println(e.toString());
	    }
	}
	
	public static void update_list_blocks(ArrayList<String> list_blocks, String username) throws ClassNotFoundException, SQLException {
	    // we will update only first name of a certain row
		if(con == null) {
			getConnection();
		} 
	    PreparedStatement ps = null; 
	    try {
	      String sql = "UPDATE user set list_blocks = ? WHERE username = ? ";
	      ps = con.prepareStatement(sql); 
	      ps.setString(1, list_blocks.toString());
	      ps.setString(2, username);
	      ps.execute();
	    } catch (SQLException e) {
	      // TODO: handle exception
	      System.out.println(e.toString());
	    }
	}


}
