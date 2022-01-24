package postManagement;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import database.CommentDatabase;
import tools.GetInput;

public class Comment {
	private static long idForComments = -1;
	private static ArrayList<Comment> list_comments = new ArrayList<>();

	private long id;
	private long post_id;
	private String message;
	private String date;
	private String username;
	
	public Comment(long id, long post_id, String username, String text, String date){
		this.id = id;
		this.post_id = post_id;
		this.username = username;
		this.date = date;
		this.message = text;
	}
	
	public static void load_comment_from_database() throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {
		CommentDatabase data = new CommentDatabase();
		ResultSet result;
		
		result = data.getCommentDatabase();
		Comment comment;
		while(result.next()) {
			idForComments = Long.parseLong(result.getString("id"));
			comment = new Comment(Long.parseLong(result.getString("id")), Long.parseLong(result.getString("post_id"))
					          , result.getString("username"), result.getString("message"), result.getString("date"));
			list_comments.add(comment);
		}
	}
	
	public static Comment put_comment(String username, long post_id) throws ClassNotFoundException, SQLException {
		String text = GetInput.get_string();
		idForComments ++;
		list_comments.add(new Comment(idForComments, post_id, username, text, LocalDateTime.now().toString()));
		
		CommentDatabase.addComment(Long.toString(idForComments), Long.toString(post_id), LocalDateTime.now().toString(), text, username);
		return list_comments.get(list_comments.size() - 1);
	}
	
	public long getId() {
		return id;
	}
	
	public String getText() {
		return message;
	}
	
	public String getDate() {
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

	public void show_comment() {
		System.out.println("===========================");
		System.out.println("comment id:" + id);
		System.out.println("owner:" + username);
		System.out.println("date: " + date);
		System.out.println("comment: " + message);
	}
	
}
