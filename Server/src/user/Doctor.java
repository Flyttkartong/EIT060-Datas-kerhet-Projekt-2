package user;

import java.util.ArrayList;

public class Doctor extends Staff {
	private String division;
	private ArrayList<User> treating;

	public Doctor(String username, String password, String name, String division) {
		super(username, password, name, division);
	}
}
