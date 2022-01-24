package userVeiw;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import postManagement.Post;
import tools.GetInput;
import userManagement.User;

public class ShowOptions {

	public static void run(User user) throws NoSuchAlgorithmException, IOException, ClassNotFoundException, SQLException {
		while(true) {
			System.out.println("-1.exit");
		    System.out.println(" 1.home page");
		    System.out.println(" 2.expelor page");
		    System.out.println(" 3.search page");
		    System.out.println(" 4.creat post");
		    System.out.println(" 5.directs");
		    System.out.println(" 6.see your profile");
		    
			int selected_option = GetInput.get_number();
			switch (selected_option){
				case -1:
					return;
			    case 1:
			    	HomePage.run(user);
			        break;
			    case 2:
			    	Expelor.run(user);
			        break;
			    case 3:
			    	SearchPage.run(user);
			        break;
			    case 4:
			    	Post.creat_post(user.getUsername());
			        break;
			    case 5:
			    	SearchChatPage.run(user);
			        break;
			    case 6:
			    	ProfilePage.run(user);
			        break;
			    default:
			        System.out.println("there is not such an option");
			}
		}
	}

}
