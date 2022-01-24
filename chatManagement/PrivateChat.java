package chatManagement;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ChatDatabase;
import tools.GetInput;
import userManagement.User;

public class PrivateChat extends Chat{

	private String user1;
	private String user2;
	
	public PrivateChat(long id, String user1, String user2){
		super(id);
		this.user1 = user1;
		this.user2 = user2;
	}
	
	public static void load_private_from_database() throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {
		ChatDatabase data = new ChatDatabase();
		ResultSet result;
		
		result = data.getChatDatabase();
		PrivateChat chat;
		while(result.next()) {
			if(result.getString("type").equals("private")) {
				if(idForChats < Long.parseLong(result.getString("id"))) {
					idForChats = Long.parseLong(result.getString("id"));
				}
				chat = new PrivateChat(Long.parseLong(result.getString("id"))
						             , result.getString("user1")
						             , result.getString("user2"));
				list_chats.add(chat);
			}
		}
	}
	
	public static PrivateChat creat_private_chat(String username1, long id) throws ClassNotFoundException, SQLException {
		String user1 = username1;
		System.out.println("enter the username that you want to chat:");
		String user2 = GetInput.get_string();
		
		if(User.search(user2) == null) {
			System.out.println("there is no such username!");
			return null;
		}
		
		for(Chat chat : list_chats) {
			if (chat instanceof PrivateChat) {
				if ((((PrivateChat) chat).user1.equals(user1) 
					&& ((PrivateChat) chat).user2.equals(user2))
					|| 
					(((PrivateChat) chat).user1.equals(user2) 
					&& ((PrivateChat) chat).user2.equals(user1))) {
					System.out.println("this private chat alredy created between"
							           + " theese two users!");
					return (PrivateChat) chat;
				}
			}
		}
		ArrayList<String> temp = new ArrayList<>();
		ChatDatabase.addChat(Long.toString(id), "private", user1, user2, "null", temp, temp);
		return new PrivateChat(id, user1, user2);
	}
	
	public String getUser1() {
		return user1;
	}
	
	public String getUser2() {
		return user2;
	}

	public boolean show_permission(String username) {
		if (user1.equals(username) || user2.equals(username)) {
			return true;
		}
		return false;
	}

	public String getName(String username) {
		if(user1.equals(username)) {
			return user2;
		}
		return user1;
	}
}
