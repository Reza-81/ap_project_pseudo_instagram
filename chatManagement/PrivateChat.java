package chatManagement;

import tools.GetInput;
import userManagement.User;

public class PrivateChat extends Chat{

	private String user1;
	private String user2;
	
	public PrivateChat(String user1, String user2){
		this.user1 = user1;
		this.user2 = user2;
	}
	
	public static PrivateChat creat_private_chat(String username1) {
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
		return new PrivateChat(user1, user2);
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
