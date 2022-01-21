package postManagement;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
	
	public void like(String username) {
		list_likes.add(username);
	}
	
	public void dislike(String username) {
		list_likes.remove(username);
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
	
	public void show_post(Post post, User user) throws NoSuchAlgorithmException, IOException {
		System.out.println("===========================");
		System.out.println("post id = " + id);
		System.out.println("owner : " + username);
		System.out.println("date = " + date);
		System.out.println("description: " + caption);
		System.out.println("likes " + list_likes.size());
		System.out.println("comments " + Comment.get_comments(id).size());
		System.out.println("===========================");
		ShowImage.run(new File(file_path));
		
		if(list_likes.contains(user.getUsername())) {
			System.out.println("*if you want to dislike the post enter 1 or not enter 0: ");
			if(GetInput.get_number() == 1) {
				post.dislike(user.getUsername());
				System.out.println("you liked this post!");
			}
		}
		else {
			System.out.println("*if you want to like the post enter 1 or not enter 0: ");
			if(GetInput.get_number() == 1) {
				post.like(user.getUsername());
				System.out.println("you liked this post!");
			}
		}
		
		System.out.println("*if you want to see the comments enter 1 or not enter 0: ");
		if(GetInput.get_number() == 1) {
			for(Comment comment : Comment.get_comments(id)) {
				comment.show_comment();
			}
			System.out.println("===========================");
		}
		
		System.out.println("*if you want put comment enter 1 or not enter 0: ");
		if(GetInput.get_number() == 1) {
			System.out.println("enter your comment:");
			Comment.put_comment(user.getUsername(), id);
			System.out.println("you commented on this post!");
		}
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
