package postManagement;

import java.time.LocalDateTime;
import java.util.ArrayList;

import tools.GetInput;

public class Reply {
	private static long idForReplies = -1;
	private static ArrayList<Reply> list_replies = new ArrayList<>();

	private long id;
	private String text;
	private LocalDateTime date;
	private long comment_id;
	private String username;
	
	public Reply(long comment_id, String username, String text){
		idForReplies ++;
		this.id = idForReplies;
		this.comment_id = comment_id;
		this.username = username;
		this.date = LocalDateTime.now();
		this.text = text;
	}
	
	public static Reply put_reply(String username, long comment_id) {
		System.out.println("enter your reply:");
		String text = GetInput.get_string();
		list_replies.add(new Reply(comment_id, username, text));
		return list_replies.get(list_replies.size() - 1);
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
	
	public long getComment_id() {
		return comment_id;
	}
	
	public String getUser() {
		return username;
	}
	
	public static ArrayList<Reply> get_replies(long comment_id){
		ArrayList<Reply> comment_replies = new ArrayList<>();
		for(Reply reply : list_replies) {
			if(reply.comment_id == comment_id) {
				comment_replies.add(reply);
			}
		}
		return comment_replies;
	}

}
