package user;

import data.ID;

public abstract class User {
	private ID ID;
	private String name;

	public User(String username, String password, String name) {
		ID = new ID(username, password);
		this.name = name;
	}

	public ID getID() {
		return ID;
	}
}
