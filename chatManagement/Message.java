package chatManagement;

import java.time.LocalDateTime;
import java.util.ArrayList;

import tools.GetInput;

public class Message {
	private static long idForMessages = -1;
	private static ArrayList<Message> list_messages = new ArrayList<>();
	
	private long id;
	private long chat_id;
	private String text;
	private LocalDateTime date;
	private String sender;
	
	public Message(String sender, long chat_id, String text){
		idForMessages ++;
		this.id = idForMessages;
		this.chat_id = chat_id;
		this.sender = sender;
		this.date = LocalDateTime.now();
		this.text = text;
	}
	
	public static Message send_message(String sender, long chat_id) {
		System.out.println("eneter your message:");
		String text = GetInput.get_string();
		list_messages.add(new Message(sender, chat_id, text));
		return list_messages.get(list_messages.size() - 1);
	}
	
	public long getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public LocalDateTime getDate() {
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
