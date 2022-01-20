package postManagement;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import tools.GetInput;
import userManagement.User;

public class Post {
	private static long idForPosts = -1;
	private static ArrayList<Post> list_posts = new ArrayList<>();

	private long id;
	private String file_path;
	private String username;
	private String caption;
	private LocalDateTime date;
	private ArrayList<String> list_tags = new ArrayList<>();
	private ArrayList<String> list_likes = new ArrayList<>();
	
	public Post(String username, String caption, String file_path) throws NoSuchAlgorithmException {
		idForPosts ++;
		this.id = idForPosts;
		this.username = username;
		this.file_path = file_path;
		this.date = LocalDateTime.now();
		this.caption = caption;
	}
	
	public static Post creat_post(String username) throws NoSuchAlgorithmException {
		System.out.println("*** enter the image path:");
		System.out.println(" like: E:\\advanced programing\\elon.jpg");
		String file_path = GetInput.get_string();
		System.out.println("*** enter your caption");
		String caption = GetInput.get_string();
		list_posts.add(new Post(username, caption, file_path));
		return list_posts.get(list_posts.size() - 1);
	}
	
	public void like(User user) {
		list_likes.add(user.getUsername());
	}
	
	public void dislike(User user) {
		list_likes.remove(user.getUsername());
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
	
	public LocalDateTime getDate() {
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

	public String getUser() {
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
}
