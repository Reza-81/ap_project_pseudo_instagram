package userVeiw;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import postManagement.Post;
import tools.GetInput;
import userManagement.User;

public class ProfilePage {

	public static void run(User user) throws NoSuchAlgorithmException, IOException {
		user.show_user_information(user.getUsername());
		while(true) {
			System.out.println("your posts: ");
			ArrayList<Post> user_posts = Post.get_user_posts(user);
			if (user_posts.isEmpty()) {
				System.out.println("===========================");
				System.out.println("sorry. there is no post yet!");
				System.out.println("===========================");
				return;
			}
			System.out.println(user_posts);
			System.out.println("===========================");
			for(Post post : user_posts) {
				System.out.println(post.getUsername() + "->" + post.getId());
			}
			System.out.println("===========================");
			System.out.println("*** if you want to see your posts\n"
							 + "    enter 'post_id' or enter -1 to back:");
			int post_id = GetInput.get_number();
			if(post_id == -1) {
				return;
			}
			Post post = Post.search(post_id);
			if (post != null) {
				post.show_post(post, user);
			}
		}
	}

}
