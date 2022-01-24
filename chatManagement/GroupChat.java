package chatManagement;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ChatDatabase;
import tools.GetInput;
import userManagement.User;

public class GroupChat extends Chat {

	private ArrayList<String> list_members = new ArrayList<>();
	private ArrayList<String> list_admins = new ArrayList<>();
	private String name;
	
	public GroupChat(long id, String name, ArrayList<String> list_members, ArrayList<String> list_admins) throws NoSuchAlgorithmException {
		super(id);
		this.name = name;
		this.list_members = list_members;
		this.list_admins = list_admins;
	}
	
	public static void load_group_from_database() throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {
		ChatDatabase data = new ChatDatabase();
		ResultSet result;
		
		result = data.getChatDatabase();
		GroupChat chat;
		while(result.next()) {
			if(result.getString("type").equals("group")) {
				if(idForChats < Long.parseLong(result.getString("id"))) {
					idForChats = Long.parseLong(result.getString("id"));
				}
				chat = new GroupChat(Long.parseLong(result.getString("id")), result.getString("name")
						             , GetInput.get_list(result.getString("list_members"))
						             , GetInput.get_list(result.getString("list_admins")));
				list_chats.add(chat);
			}
		}
	}
	
	public static GroupChat creat_Group_chat(String username, long id) throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
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
		ChatDatabase.addChat(Long.toString(id), "group", "null", "null", name, members, admins);
		return new GroupChat(id, name, members, admins);
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
