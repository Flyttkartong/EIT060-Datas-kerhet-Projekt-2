package data;

import java.util.ArrayList;

import user.User;

public class MedicalRecord {
	private ArrayList<User> access;
	private String division;
	private String data;

	public MedicalRecord(ArrayList<User> access, String division, String data) {
		this.access = access;
		this.division = division;
		this.data = data;
	}

	public String read() {
		return data;
	}

	public void write(String data) {
		this.data = data;
	}
	
	public void delete(){
		access = null;
		division = null;
		data = null;
	}
	
	public void giveAccess(User user){
		access.add(user);
	}
	
	public void removeAccess(User user){
		access.remove(user);
	}
}
