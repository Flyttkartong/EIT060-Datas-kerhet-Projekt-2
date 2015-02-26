package data;

import java.io.Serializable;
import java.util.ArrayList;

public class MedicalRecord implements Serializable {
	private static final long serialVersionUID = 2975813329401800727L;
	
	private String mrID, doctorID, nurseID, patientID, division, data;

	public MedicalRecord(String mrID, String patientID, String nurseID, String doctorID, String division, String data) {
		this.mrID = mrID;
		this.patientID = patientID;
		this.nurseID = nurseID;
		this.doctorID = doctorID;
		this.division = division;
		this.data = data;
	}

	public String read() {
		return data;
	}

	public void write(String data) {
		this.data = data;
	}

	public ArrayList<String> getAccessList() {
		ArrayList<String> accessList = new ArrayList<String>();
		accessList.add(doctorID);
		accessList.add(nurseID);
		accessList.add(patientID);
		return accessList;
	}

	public String getDivision() {
		return division;
	}

	public String getID() {
		return mrID;
	}
}
