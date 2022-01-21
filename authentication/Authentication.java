package authentication;

import java.util.Random;

import tools.GetInput;

public class Authentication {
	public static boolean run(String email) {
		// SendEmailVerification
		String code = getRandom();
		SendEmail.run(email, "verification code", code);
		System.out.println("check your email and enter the verification code:");
		if(code.equals(GetInput.get_string())) {
			return true;
		}
		return false;
	}
	
	private static String getRandom() {
        Random rnd = new Random();
        Integer number = rnd.nextInt(999999);
        return number.toString();
    }
}
