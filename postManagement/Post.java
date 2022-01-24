package postManagement;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import database.PostDatabase;
import tools.GetInput;
import tools.ShowImage;
import userManagement.User;

public class Post {
	private static long idForPosts = -1;
	private static ArrayList<Post> list_posts = new ArrayList<>();

	private long id;
	private String file_path;
	private String username;
	private String caption;
	private String date;
	private ArrayList<String> list_tags = new ArrayList<>();
	private ArrayList<String> list_likes = new ArrayList<>();
	
	public Post(long id, String username, String caption, String file_path, String date, ArrayList<String> list_tags) throws NoSuchAlgorithmException {
		this.id = id;
		this.username = username;
		this.file_path = file_path;
		this.date = date;
		this.caption = caption;
		this.list_tags = list_tags;
	}
	
	public static void load_post_from_database() throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {
		PostDatabase data = new PostDatabase();
		ResultSet result;
		
		result = data.getPostDatabase();
		Post post;
		while(result.next()) {
			idForPosts = Long.parseLong(result.getString("id"));
			post = new Post(Long.parseLong(result.getString("id")), result.getString("username"), result.getString("caption")
				, result.getString("file_path"), result.getString("date"), GetInput.get_list(result.getString("list_tags")));
			post.list_likes = GetInput.get_list(result.getString("list_likes"));
			list_posts.add(post);
		}
	}
	
	public static Post creat_post(String username) throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		System.out.println("*** enter the image path:");
		System.out.println(" like: E:\\advanced programing\\elon.jpg");
		String file_path = GetInput.get_string();
		System.out.println("*** enter your caption");
		String caption = GetInput.get_string();
		System.out.println("if you want to tag some one, enter the username:");
		System.out.println("after tags people, enter 0:");
		ArrayList<String> list_tags = new ArrayList<>();
		while(true) {
			String temp = GetInput.get_string();
			if (temp.equals("0")) {
				break;
			}
			if(User.search(temp) == null) {
				System.out.println("there is no such username! enter enother one:");
				continue;
			}
			list_tags.add(temp);
		}
		idForPosts ++;
		list_posts.add(new Post(idForPosts, username, caption, file_path, LocalDateTime.now().toString(), list_tags));
		
		ArrayList<String> temp = new ArrayList<>();
		PostDatabase.addPost(Long.toString(idForPosts), file_path, username, caption, LocalDateTime.now().toString(), list_tags.toString(), temp);
		return list_posts.get(list_posts.size() - 1);
	}
	
	public void like(String username) throws ClassNotFoundException, SQLException {
		list_likes.add(username);
		PostDatabase.update_list_likes(list_likes, Long.toString(id));
	}
	
	public void dislike(String username) throws ClassNotFoundException, SQLException {
		list_likes.remove(username);
		PostDatabase.update_list_likes(list_likes, Long.toString(id));
	}
	
	public long getId() {
		return id;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getDate() {
		return date;
	}
	
	public ArrayList<String> getList_tags() {
		return list_tags;
	}
	
	public void tag(User user) {
		if(!list_tags.contains(user.getUsername())) {
			list_tags.add(user.getUsername());
		}
	}
	
	public void remove_tag(User user) {
		list_tags.remove(user.getUsername());
	}
	
	public ArrayList<String> getList_likes() {
		return list_likes;
	}

	public static void delete_post(Post post) {
		list_posts.remove(post);
	}

	public String getUsername() {
		return username;
	}
	
	public static ArrayList<Post> get_posts(String username){
		ArrayList<Post> user_posts = new ArrayList<>();
		for(Post post : list_posts) {
			if(post.username == username) {
				user_posts.add(post);
			}
		}
		return user_posts;
	}
	
	public static Post search(long id) {
		for(Post post : list_posts) {
			if(post.id == id) {
				return post;
			}
		}
		return null;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public static ArrayList<Post> get_following_posts(User user) {
		ArrayList<Post> following_posts = new ArrayList<>();
		for(Post post : list_posts) {
			for(String following : user.getList_followings()) {
				if(post.username.equals(following)) {
					following_posts.add(post);
				}
			}
		}
		return following_posts;
	}
	
	public boolean show_post(Post post, User user) throws NoSuchAlgorithmException, IOException, ClassNotFoundException, SQLException {
		if(User.search(username).getList_blocks().contains(user.getUsername())) {
			System.out.println("you can't see the " + username + " posts and profile!\n");
			System.out.println("because you blocked by " + username);
			return false;
		}
		System.out.println("===========================");
		System.out.println("post id = " + id);
		System.out.println("owner : " + username);
		System.out.println("date = " + date);
		System.out.println("caption: " + caption);
		System.out.println("likes " + list_likes.size());
		System.out.println("comments " + Comment.get_comments(id).size());
		System.out.println("taged people:");
		for(String tag : list_tags) {
			System.out.println("  " + tag);
		}
		System.out.println("===========================");
		ShowImage.run(new File(file_path));
		
		System.out.println("if you want enter one of these options:");
		System.out.println("1.like");
		System.out.println("2.dislike");
		System.out.println("3.see comments");
		System.out.println("4.put comment");
		System.out.println("5.skip");
		int selectedOption = GetInput.get_number();
		switch (selectedOption){
			case 1:
				if(!list_likes.contains(user.getUsername())) {
					post.like(user.getUsername());
					System.out.println("you liked this post!");
				}
				break;
			case 2:
				if(list_likes.contains(user.getUsername())) {
					post.dislike(user.getUsername());
					System.out.println("you disliked this post!");
				}
				break;
			case 3:
				for(Comment comment : Comment.get_comments(id)) {
					comment.show_comment();
				}
				System.out.println("===========================");
				break;
			case 4:
				System.out.println("enter your comment:");
				Comment.put_comment(user.getUsername(), id);
				System.out.println("you commented on this post!");
				break;
			case 5:
				break;
		}
		return true;
	}

	public static ArrayList<Post> get_expelor() {
		if(list_posts.size() <= 20) {
			return list_posts;
		}
		ArrayList<Post> expelor_posts = new ArrayList<>();
		expelor_posts.add(list_posts.get((int)(Math.random() * list_posts.size())));
		return expelor_posts;
	}

	public static ArrayList<Post> get_user_posts(User user) {
		ArrayList<Post> user_posts = new ArrayList<>();
		for(Post post : list_posts) {
			if(post.username.equals(user.getUsername())) {
				user_posts.add(post);
			}
		}
		return user_posts;
	}
	
}
