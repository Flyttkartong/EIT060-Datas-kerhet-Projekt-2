package user;

public abstract class Staff extends User {
	private String division;

	public Staff(String userID, String division) {
		super(userID);
		this.division = division;
	}

	public String getDivision() {
		return division;
	}
}
