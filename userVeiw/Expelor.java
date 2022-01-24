package userVeiw;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import postManagement.Post;
import tools.GetInput;
import userManagement.User;

public class Expelor {

	public static void run(User user) throws NoSuchAlgorithmException, IOException, ClassNotFoundException, SQLException {
		System.out.println("*** in this page you can see 20 random posts.\n"
				         + "    you cansee the each post with 'post_id'.\n"
				         + "    to back to the previos page enter -1\n");
		while(true) {
			ArrayList<Post> expelor_posts = Post.get_expelor();
			if(expelor_posts.isEmpty()) {
				System.out.println("===========================");
				System.out.println("sorry. there is no post yet!");
				System.out.println("===========================");
				return;
			}
			System.out.println("posts: ");
			System.out.println("===========================");
			for(Post post : expelor_posts) {
				System.out.println(post.getUsername() + "->" + post.getId());
			}
			System.out.println("===========================");
			System.out.println("*** enter 'post_id' or enter -1 to back:");
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
