package userManagement;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import tools.GetInput;
import tools.ShowImage;

public class User {

	private static ArrayList<User> list_users = new ArrayList<>();
	
	private String username;
	private String password;
	private String email;
	private String bio;
	private String profile_picture;
	private ArrayList<String> list_followings = new ArrayList<>();
	private ArrayList<String> list_blocks = new ArrayList<>();
	
	public User(String username, String email, String bio, String password, String profile_picture) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		this.username = username;
		this.email = email;
		this.bio = bio;
		this.password = Arrays.toString(md.digest(password.getBytes(StandardCharsets.UTF_8)));
		this.profile_picture = profile_picture;
	}
	
	public static User creat_account() throws NoSuchAlgorithmException {
		System.out.println("enter your username:");
		String username;
		while(true) {
			username = GetInput.get_string();
			if(search(username) == null) {
				break;
			}
			System.out.println("this username already exists! please choose another username:");
		}
		System.out.println("enter your email:");
		String email = GetInput.get_string();
		System.out.println("enter your password:");
		String password = GetInput.get_string();
		System.out.println("enter your bio:");
		String bio = GetInput.get_string();
		System.out.println("enter your prifile picture path:");
		System.out.println(" like: E:\\advanced programing\\elon.jpg");
		String profile_picture = GetInput.get_string();
		list_users.add(new User(username, email, bio, password, profile_picture));
		return list_users.get(list_users.size() - 1);
	}
	
	public static User search(String username) {
		for(User check_user : list_users) {
			if (username.equals(check_user.username)) {
				return check_user;
			}
		}
		return null;
	}
	
	public void follow(User user) {
		user.list_followings.add(user.username);
	}
	
	public void unfollow(User user) {
		user.list_followings.remove(user.username);
	}
	
	public void block(User user) {
		user.list_blocks.add(user.username);
	}
	
	public void unblock(User user) {
		user.list_blocks.remove(user.username);
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getBio() {
		return bio;
	}
	
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public String getProfile_picture() {
		return profile_picture;
	}
	
	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;
	}
	
	public ArrayList<String> getList_followings() {
		return list_followings;
	}
	
	public ArrayList<String> getList_blocks() {
		return list_blocks;
	}
	
	public static ArrayList<User> get_followers(String username) {
		ArrayList<User> list_followers = new ArrayList<>();
		for(User check_user : list_users) {
			for(String following : check_user.list_followings) {
				if (following.equals(username)) {
					list_followers.add(check_user);
					break;
				}
			}
		}
		return list_followers;
	}
	
	public void show_user_information() throws IOException {
		ShowImage.run(new File(profile_picture));
		System.out.println("===========================");
		System.out.println("username: " + username);
		System.out.println("email: " + email);
		System.out.println("followings: " + list_followings.size());
		System.out.println("followers: " + get_followers(username).size());
		System.out.println("bio: " + bio);
		System.out.println("===========================");
	}

	public static User login() throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		System.out.println("enter your username: ");
		String username = GetInput.get_string();
    	for(User user : list_users) {
        	if (user.username.equals(username)) {
        		System.out.println("enter your password: ");
        		String password = GetInput.get_string();
        		if(user.password.equals(Arrays.toString(md.digest(password.getBytes(StandardCharsets.UTF_8))))) {
        			return user;
        		}
        		System.out.println("your password is wrong!");
            	return null;
        	}
    	}
    	System.out.println("there is no such username!");
    	return null;
	}

	public static ArrayList<User> show_random_user() {
		if(list_users.size() <= 20) {
			return list_users;
		}
		ArrayList<User> random_users = new ArrayList<>();
		random_users.add(list_users.get((int)(Math.random() * list_users.size())));
		return random_users;
	}
	
}
