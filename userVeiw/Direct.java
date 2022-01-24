package userVeiw;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import chatManagement.Chat;
import chatManagement.Message;
import chatManagement.PrivateChat;
import chatManagement.GroupChat;
import tools.GetInput;
import userManagement.User;

public class Direct {

	public static void run(User user, Chat chat) throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		if(chat instanceof PrivateChat) {
			System.out.println("*** welcom to the '" + ((PrivateChat) chat).getName(user.getUsername()) + "' chat:");
		}
		else {
			System.out.println("*** welcom to the '" + ((GroupChat) chat).getName() + "' chat:");
		}
		System.out.println("-1.back");
		System.out.println(" 0.refresh the page");
		System.out.println(" 1.send message");
		
		System.out.println("===========================");
		for(Message message : Message.get_messages(chat.getId())) {
			message.show();
		}
		System.out.println("===========================");
		while(true) {
			System.out.println("*** your input:");
			int input = GetInput.get_number();
			if (input == 0) {
				System.out.println("===========================");
				for(Message message : Message.get_messages(chat.getId())) {
					message.show();
				}
				System.out.println("===========================");
				continue;
			}
			
			if (input == 1) {
				Message.send_message(user.getUsername(), chat.getId());
				System.out.println("===========================");
				for(Message message : Message.get_messages(chat.getId())) {
					message.show();
				}
				System.out.println("===========================");
			}
			
			if (input == -1) {
				return;
			}
		}
	}

}
