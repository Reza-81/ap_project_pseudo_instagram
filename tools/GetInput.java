package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GetInput {
	static Scanner scanner = new Scanner(java.lang.System.in);
	
	public static int get_number() {
		int number;
		while(true) {
	    	try {
	    		number = Integer.parseInt(scanner.nextLine());
	    		return number;
	    	}catch (Exception e){
	            System.out.println("your input must be a number");
	            continue;
	        }
		}
	}

	public static String get_string() {
		return scanner.nextLine();
	}
	
	public static ArrayList<String> get_list(String string) {
		String string2 = string.substring(1, string.length()-1);
		if(string2 != "") {
		String[] string3 = string2.split(",");
			for(int i=0; i<string3.length; i++) {
				string3[i] = string3[i].strip();
			}
			return new ArrayList<String>(Arrays.asList(string3));
		}
		return new ArrayList<>();
	}
}

