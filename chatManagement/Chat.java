package chatManagement;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import userManagement.User;

public class Chat {
	protected static long idForChats = -1;
	protected static ArrayList<Chat> list_chats = new ArrayList<>();
	
	private long id;
	
	public Chat(long id) {
		this.id = id;
	}
	
	public static void creat_chat(int type, String username) throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		if(type == 1) {
			idForChats ++;
			GroupChat g_chat = GroupChat.creat_Group_chat(username, idForChats);
			if (g_chat == null) {
				return;
			}
			add_new_chat(g_chat);
			return;
		}
		if(type == 2) {
			idForChats ++;
			PrivateChat p_chat = PrivateChat.creat_private_chat(username, idForChats);
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
	
	public static Chat search(long chat_id, String username) {
		for (Chat chat : list_chats) {
			if (chat.id == chat_id ) {
				if(chat instanceof PrivateChat) {
					if(((PrivateChat)chat).show_permission(username)) {
						return chat;
					}
				}
				if (chat instanceof GroupChat) {
					if(((GroupChat)chat).show_permission(username)) {
						return chat;
					}
				}
				System.out.println("*** your permission denied ***");
				return null;
			}
		}
		System.out.println("*** there is no chat with this id ***");
		return null;
	}
	
	public static void add_new_chat(Chat chat) {
		if(list_chats.contains(chat)) {
			return;
		}
		list_chats.add(chat);
	}

	public static void show_chats(User user) {
		System.out.println("your cahts:\n"
				         + "==============================");
		for (Chat chat : list_chats) {
			if (chat instanceof PrivateChat) {
				if (((PrivateChat) chat).show_permission(user.getUsername())) {
					System.out.println(((PrivateChat) chat).getName(user.getUsername()) + "->" + chat.id);
				}
			}
			else if(chat instanceof GroupChat) {
				if (((GroupChat) chat).show_permission(user.getUsername())) {
					System.out.println(((GroupChat) chat).getName() + "->" + chat.id);
				}
			}
		}
		System.out.println("==============================");
	}

}
