package chatManagement;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import database.MessageDatabase;
import tools.GetInput;

public class Message {
	private static long idForMessages = -1;
	private static ArrayList<Message> list_messages = new ArrayList<>();
	
	private long id;
	private long chat_id;
	private String text;
	private String date;
	private String sender;
	
	public Message(long id, String sender, long chat_id, String text, String date){
		this.id = id;
		this.chat_id = chat_id;
		this.sender = sender;
		this.date = date;
		this.text = text;
	}
	
	public static void load_message_from_database() throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {
		MessageDatabase data = new MessageDatabase();
		ResultSet result;
		
		result = data.getMessageDatabase();
		Message message;
		while(result.next()) {
			idForMessages = Long.parseLong(result.getString("id"));
//			System.out.println(result.getString("id"));
//			System.out.println(result.getString("chat_id"));
//			System.out.println(result.getString("username"));
//			System.out.println(result.getString("date"));
//			System.out.println(result.getString("txt"));
			message = new Message(Long.parseLong(result.getString("id"))
					        , result.getString("username"), Long.parseLong(result.getString("chat_id"))
					        , result.getString("txt"), result.getString("date"));
			list_messages.add(message);
		}
	}
	
	public static Message send_message(String sender, long chat_id) throws ClassNotFoundException, SQLException {
		System.out.println("eneter your message:");
		String text = GetInput.get_string();
		idForMessages ++;
		list_messages.add(new Message(idForMessages, sender, chat_id, text, LocalDateTime.now().toString()));
		
		MessageDatabase.addMessage(Long.toString(idForMessages), Long.toString(chat_id), text, LocalDateTime.now().toString(), sender);
		return list_messages.get(list_messages.size() - 1);
	}
	
	public long getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getSender() {
		return sender;
	}
	
	public static ArrayList<Message> get_messages(long chat_id) {
		ArrayList<Message> chat_messages = new ArrayList<>();
		for(Message message : list_messages) {
			if(message.chat_id == chat_id) {
				chat_messages.add(message);
			}
		}
		return chat_messages;
	}

	public void show() {
		System.out.println("---------------------------");
		System.out.println(sender + ":");
		System.out.println(text + "\n");
		System.out.println(date + "\n");
	}
	
}
