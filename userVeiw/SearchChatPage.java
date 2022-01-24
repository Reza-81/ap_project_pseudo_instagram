package userVeiw;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import chatManagement.Chat;
import tools.GetInput;
import userManagement.User;

public class SearchChatPage {

	public static void run(User user) throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		while(true) {
			System.out.println("*** you can go to the chat with enter 'chat id'.\n"
			                 + "    -if you want to see chat messages and send message enter 'chat id'\n"
			                 + "    -if you want to creat new chat enter 'create'\n"
			                 + "    -if you want to go back to previos page enter -1\n");
			Chat.show_chats(user);
			System.out.println("*** your input:");
			String input = GetInput.get_string();
			if(input.toLowerCase().equals("create")) {
				System.out.println("*** [1.group] or [2.private]? (just enter number)");
				Chat.creat_chat(GetInput.get_number(), user.getUsername());
				continue;
			}
			
			long chat_id = -2;
			try {
				chat_id = Long.parseLong(input);
			}
			catch (NumberFormatException e) {
				System.out.println("*** this input is invalid! ***");
				continue;
			}
			if(chat_id == -1) {
				return;
			}
			
			Chat chat = Chat.search(chat_id, user.getUsername());
			if (chat == null) {
				continue;
			}
			Direct.run(user, chat);
		}
	}

}
