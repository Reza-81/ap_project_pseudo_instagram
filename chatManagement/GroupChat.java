package chatManagement;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import tools.GetInput;
import userManagement.User;

public class GroupChat extends Chat {

	private ArrayList<String> list_members = new ArrayList<>();
	private ArrayList<String> list_admins = new ArrayList<>();
	private String name;
	
	public GroupChat(String name, ArrayList<String> list_members, ArrayList<String> list_admins) throws NoSuchAlgorithmException {
		this.name = name;
		this.list_members = list_members;
		this.list_admins = list_admins;
	}
	
	public static GroupChat creat_Group_chat(String username) throws NoSuchAlgorithmException {
		ArrayList<String> members = new ArrayList<>();
		ArrayList<String> admins = new ArrayList<>();
		
		System.out.println("enter the group name:");
		String name = GetInput.get_string();
		System.out.println("how many members are you? (exept you):");
		int number_of_members = GetInput.get_number();
		System.out.println("how many admins are you? (exept you):");
		int number_of_admins = GetInput.get_number();
		
		String member;
		for (int i = 0; i < number_of_members; i++) {
			System.out.println("enter member number " + (i+1) + " :");
			member = GetInput.get_string();
			if ( User.search(member) == null) {
				System.out.println("there is no such username!");
				return null;
			}
			members.add(member);
		}
		members.add(username);
		
		String admin;
		for (int i = 0; i < number_of_admins; i++) {
			System.out.println("enter admin number " + (i+1) + " :");
			admin = GetInput.get_string();
			if (User.search(admin) == null) {
				System.out.println("there is no such username!");
				return null;
			}
			admins.add(admin);
		}
		admins.add(username);
		return new GroupChat(name, members, admins);
	}
	
	public ArrayList<String> getList_members() {
		return list_members;
	}
	
	public void setList_members(ArrayList<String> list_members) {
		this.list_members = list_members;
	}
	
	public void remove_member(String username) {
		list_members.remove(username);
	}
	
	public void add_member(String username) {
		if (!list_members.contains(username)) {
			list_members.add(username);
		}
	}
	
	public void remove_admin(String username) {
		list_admins.remove(username);
	}
	
	public void add_admin(String username) {
		if (!list_admins.contains(username)) {
			list_admins.add(username);
		}
	}

	public String getName() {
		return name;
	}

	public boolean show_permission(String username) {
		for(String member : list_members) {
			if (member.equals(username)) {
				return true;
			}
		}
		for(String admin : list_admins) {
			if (admin.equals(username)) {
				return true;
			}
		}
		return false;
	}
	
}
