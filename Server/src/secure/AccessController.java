package secure;

import java.util.ArrayList;
import java.util.HashMap;

import test.DataGenerator;
import user.*;
import data.Logger;
import data.MedicalRecord;

public class AccessController {
	private User currentUser;
	private HashMap<String, User> users;
	private HashMap<String, MedicalRecord> records;
	private static final String PERMISSION_DENIED = "Error: Permission denied";
	private static final String NOT_FOUND = "Error: Medical Record not found";

	public AccessController() {
		loadData();
	}
	
	/**
	 * Must be called before any other method. 
	 * Sets current user in this AccessController
	 */
	public void initialize(String userID) {
		currentUser = users.get(userID);
	}

	private void loadData() {
		// TODO Load users and medical records from file?
		users = DataGenerator.createUserMap();
		records = DataGenerator.createRecordMap();
	}

	public String read(String mrID) {
		String returnValue = PERMISSION_DENIED;
		MedicalRecord mr = records.get(mrID);
		if (mr == null) {
			returnValue = NOT_FOUND;
		}
		ArrayList<String> accessList = mr.getAccessList();
		String mrDivision = mr.getDivision();
		
		/* If currentUser: is in accessList OR is Staff and has the same division OR is a GA */
		if ((accessList.contains(currentUser.getID())) 
				|| (currentUser instanceof Staff && ((Staff) currentUser).getDivision().equals(mrDivision))
				|| (currentUser instanceof GA)) {
			Logger.logRead(currentUser.getID(), mrID);
			returnValue = mr.read();
		}
		Logger.logFailedRead(currentUser.getID(), mrID);
		return returnValue;
	}

	public String write(String mrID, String data) {
		String returnValue = PERMISSION_DENIED;
		if (currentUser instanceof Doctor || currentUser instanceof Nurse) {
			MedicalRecord mr = records.get(mrID);
			if (mr == null) {
				returnValue = NOT_FOUND;
			}
			ArrayList<String> accessList = mr.getAccessList();
			if (accessList.contains(currentUser.getID())) {
				mr.write(data);
				saveRecordsToFile();
				Logger.logWrite(currentUser.getID(), mrID);
				returnValue = "Write successful";
			}
		}
		Logger.logFailedWrite(currentUser.getID(), mrID);
		return returnValue;
	}

	public String remove(String mrID) {
		String returnValue = PERMISSION_DENIED;
		if (currentUser instanceof GA) {
			MedicalRecord mr = records.get(mrID);
			if(mr == null){
				returnValue = NOT_FOUND;
			}
			records.remove(mr.getID());
			Logger.logRemove(currentUser.getID(), mr.getID());
			returnValue = "The medical record was successfully removed";
		}
		Logger.logFailedRemove(currentUser.getID(), mrID);
		return returnValue;
	}

	public String create(String mrID, String patientID, String nurseID, String data) {
		String returnValue = PERMISSION_DENIED;
		if (currentUser instanceof Doctor) {
			Doctor doctor = (Doctor) currentUser;
			records.put(mrID, new MedicalRecord(mrID, patientID, nurseID, doctor.getID(), doctor.getDivision(), data));
			Logger.logCreate(currentUser.getID(), mrID);
			returnValue = "The medical record was successfully created";
		}
		Logger.logFailedCreate(currentUser.getID(), mrID);
		return returnValue;
	}
	
	private void saveRecordsToFile() {
		// TODO Auto-generated method stub
		
	}
}
