package user;

import java.util.ArrayList;

public class Doctor extends Staff {
	private ArrayList<String> treating;

	public Doctor(String userID, String division) {
		super(userID, division);
	}
	
	public void addPatient(String patientID) {
		treating.add(patientID);
	}
	
	public void removePatient(String patientID) {
		treating.remove(patientID);
	}
	
	public ArrayList<String> getTreatingList(){
		return treating;
	}
}
