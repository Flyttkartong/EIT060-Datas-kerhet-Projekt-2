package data;

import java.util.ArrayList;

import user.Doctor;
import user.Nurse;
import user.Patient;
import user.User;

public class MedicalRecord {
	private int ID;
	private Doctor doctor;
	private Nurse nurse;
	private Patient patient;
	private String division;
	private String data;

	public MedicalRecord(Doctor doctor, Nurse nurse, Patient patient,
			String division, String data, int ID) {
		this.patient = patient;
		this.nurse = nurse;
		this.doctor = doctor;
		this.division = division;
		this.data = data;
		this.ID = ID;
	}

	public String read() {
		return data;
	}

	public void write(String data) {
		this.data = data;
	}

	public void delete() {
		patient = null;
		nurse = null;
		doctor = null;
		division = null;
		data = null;
	}

	public ArrayList<User> getAccessList() {
		ArrayList<User> accessList = new ArrayList<User>();
		accessList.add(doctor);
		accessList.add(nurse);
		accessList.add(patient);
		return accessList;
	}

	public String getDivision() {
		return division;
	}

	public int  getID() {
		return ID;
	}
}
