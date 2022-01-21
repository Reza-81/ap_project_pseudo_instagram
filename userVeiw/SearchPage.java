package userVeiw;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import postManagement.Post;
import tools.GetInput;
import userManagement.User;

public class SearchPage {

	public static void run(User user) throws NoSuchAlgorithmException, IOException {
		System.out.println("*** in this page you can see the random users and follow them.\n"
						 + "    or you can search specific 'username' and see it's page.\n"
						 + "    to back to the previos page enter -1\n");
		while(true) {
			ArrayList<User> random_users = User.show_random_user();
			if(random_users.isEmpty()) {
				System.out.println("===========================");
				System.out.println("sorry. there is no user!");
				System.out.println("===========================");
				return;
			}
			System.out.println("===========================");
			for(User random_user : random_users) {
				System.out.println(random_user.getUsername());
			}
			System.out.println("*** enter username or enter -1 to back:");
			String username = GetInput.get_string();
			int input = 0;
			try {
				input = Integer.parseInt(username);
			}
			catch (NumberFormatException e) {
			}
			if(input == -1) {
				return;
			}
			User searched_user = User.search(username);
			if (searched_user == null) {
				System.out.println("there is no such username!");
				continue;
			}
			if(!searched_user.show_user_information(user.getUsername())) {
				break;
			}
			ArrayList<Post> user_posts = Post.get_user_posts(searched_user);
			while(true) {
				System.out.println(searched_user.getUsername() + " posts: ");
				if (user_posts.isEmpty()) {
					System.out.println("===========================");
					System.out.println("sorry. there is no post yet!");
					System.out.println("===========================");
					break;
				}
				System.out.println("===========================");
				for(Post post : user_posts) {
					System.out.println(post.getUsername() + "->" + post.getId());
				}
				System.out.println("===========================");
				System.out.println("*** if you want to see posts\n"
								 + "    enter 'post_id' or enter -1 to back:");
				int post_id = GetInput.get_number();
				if(post_id == -1) {
					break;
				}
				Post post = Post.search(post_id);
				if (post != null) {
					post.show_post(post, user);
				}
			}
			if(!user.equals(searched_user)) {
				System.out.println("if you want enter one of these options:");
				System.out.println("1.follow");
				System.out.println("2.unfollow");
				System.out.println("3.block");
				System.out.println("4.unblock");
				System.out.println("5.skip");
				int selectedOption = GetInput.get_number();
				switch (selectedOption){
					case 1:
						user.follow(searched_user.getUsername());
						System.out.println(searched_user.getUsername() + " added to followings!");
						break;
					case 2:
						user.unfollow(searched_user.getUsername());
						System.out.println(searched_user.getUsername() + " unfollowed!");
					case 3:
						user.block(searched_user.getUsername());
						System.out.println(searched_user.getUsername() + " blocked!");
						break;
					case 4:
						user.unblock(searched_user.getUsername());
						System.out.println(searched_user.getUsername() + " unblocked!");
						break;
					case 5:
						break;
				}
			}
		}
	}
	
}
