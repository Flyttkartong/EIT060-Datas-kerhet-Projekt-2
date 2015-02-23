package user;

public abstract class Staff extends User {
	private String division;

	public Staff(String username, String password, String name, String division) {
		super(username, password, name);
		this.division = division;
	}

	public boolean belongsTo(String division) {
		if (this.division.equals(division))
			return true;
		return false;
	}
}
