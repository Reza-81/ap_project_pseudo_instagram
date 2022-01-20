package chatManagement;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Chat {
	private static long idForChats = -1;
	protected static ArrayList<Chat> list_chats = new ArrayList<>();
	
	private long id;
	
	public Chat() {
		idForChats ++;
		this.id = idForChats;
	}
	
	public static void creat_chat(int type, String username) throws NoSuchAlgorithmException {
		if(type == 1) {
			GroupChat g_chat = GroupChat.creat_Group_chat(username);
			if (g_chat == null) {
				return;
			}
			add_new_chat(g_chat);
			return;
		}
		if(type == 2) {
			PrivateChat p_chat = PrivateChat.creat_private_chat(username);
			if (p_chat == null) {
				return;
			}
			add_new_chat(p_chat);
			return;
		}
		return;
	}
	
	public long getId() {
		return id;
	}
	
	public static Chat search(long id) {
		for(Chat chat : list_chats) {
			if(chat.id == id) {
				return chat;
			}
		}
		return null;
	}
	
	public static void add_new_chat(Chat chat) {
		if(list_chats.contains(chat)) {
			return;
		}
		list_chats.add(chat);
	}

}
