package userVeiw;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import tools.GetInput;
import userManagement.User;

public class LoginSignupPage {
	public static void run() throws NoSuchAlgorithmException, IOException{
		while(true) {
			System.out.println("-1.exit");
		    System.out.println(" 1.login");
		    System.out.println(" 2.create");
		    
		    int selectedOption = GetInput.get_number();
		    switch (selectedOption){
		    	case -1:
		    		System.out.println("bye bye! :)");
		    		return;
			    case 1:
			    	User logedAccount = User.login();
			    	if (logedAccount != null) {
			    		ShowOptions.run(logedAccount);
			    	}
			        break;
			    case 2:
			        User createdAccount = User.creat_account();
			        if (createdAccount != null) {
			        	ShowOptions.run(createdAccount);
			        }
			        break;
			    default:
			        System.out.println("there is not such an option");
		    }
		}
	}
}
