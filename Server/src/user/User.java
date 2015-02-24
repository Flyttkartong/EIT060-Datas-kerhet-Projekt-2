package user;

public abstract class User {
	private String userID;

	public User(String userID) {
		this.userID = userID;
	}

	public String getID() {
		return userID;
	}
}
