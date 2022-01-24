package main;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import chatManagement.GroupChat;
import chatManagement.Message;
import chatManagement.PrivateChat;
import postManagement.Comment;
import postManagement.Post;
import userManagement.User;
import userVeiw.LoginSignupPage;

public class Main {
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ClassNotFoundException, SQLException{
		User.load_user_from_database();
		Post.load_post_from_database();
		Comment.load_comment_from_database();
		Message.load_message_from_database();
		GroupChat.load_group_from_database();
		PrivateChat.load_private_from_database();
		LoginSignupPage.run();
		return;
	}
}
