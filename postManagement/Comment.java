package postManagement;

import java.time.LocalDateTime;
import java.util.ArrayList;

import tools.GetInput;

public class Comment {
	private static long idForComments = -1;
	private static ArrayList<Comment> list_comments = new ArrayList<>();

	private long id;
	private long post_id;
	private String text;
	private LocalDateTime date;
	private String username;
	
	public Comment(long post_id, String username, String text){
		idForComments ++;
		this.id = idForComments;
		this.post_id = post_id;
		this.username = username;
		this.date = LocalDateTime.now();
		this.text = text;
	}
	
	public static Comment put_comment(String username, long post_id) {
		System.out.println("enter your comment:");
		String text = GetInput.get_string();
		list_comments.add(new Comment(post_id, username, text));
		return list_comments.get(list_comments.size() - 1);
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
	
	public String getUser() {
		return username;
	}

	public long getPost_id() {
		return post_id;
	}
	
	public static ArrayList<Comment> get_comments(long post_id) {
		ArrayList<Comment> post_comments = new ArrayList<>();
		for(Comment comment : list_comments) {
			if(comment.post_id == post_id) {
				post_comments.add(comment);
			}
		}
		return post_comments;
	}
	
}
