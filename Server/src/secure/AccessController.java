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
	public void login(String userID) {
		currentUser = users.get(userID);
		Logger.logLogin(currentUser.getID());
	}
	
	public void logout() {
		Logger.logLogout(currentUser.getID());
		currentUser = null;
	}

	private void loadData() {
		// TODO Load users and medical records from file?
		users = DataGenerator.createUserMap();
		records = DataGenerator.createRecordMap();
	}

	public String read(String mrID) {
		String returnValue;
		MedicalRecord mr = records.get(mrID);
		if (mr != null) {
			ArrayList<String> accessList = mr.getAccessList();
			String mrDivision = mr.getDivision();
			
			/* If currentUser: is in accessList OR is Staff and has the same division OR is a GA */
			if ((accessList.contains(currentUser.getID())) 
					|| (currentUser instanceof Staff && ((Staff) currentUser).getDivision().equals(mrDivision))
					|| (currentUser instanceof GA)) {
			Logger.logRead(currentUser.getID(), mrID);
			returnValue = mr.read();
			} else {
				Logger.logFailedRead(currentUser.getID(), mrID);
				returnValue = PERMISSION_DENIED;
			}
		} else {			
			returnValue = NOT_FOUND;
		}
		return returnValue;
	}

	public String write(String mrID, String data) {
		String returnValue;
		MedicalRecord mr = records.get(mrID);
		if (mr != null) {
			ArrayList<String> accessList = mr.getAccessList();
			if ((currentUser instanceof Staff) && accessList.contains(currentUser.getID())) {
				mr.write(data);
				saveRecordsToFile();
				Logger.logWrite(currentUser.getID(), mrID);
				returnValue = "Write successful";
			} else {
				Logger.logFailedWrite(currentUser.getID(), mrID);
				returnValue = PERMISSION_DENIED;
			}
		} else {
			returnValue = NOT_FOUND;
		}
		return returnValue;
	}

	public String remove(String mrID) {
		String returnValue;
		MedicalRecord mr = records.get(mrID);
		if(mr != null){
			if (currentUser instanceof GA) {
				records.remove(mr.getID());
				Logger.logRemove(currentUser.getID(), mr.getID());
				returnValue = "The medical record was successfully removed";
			} else {
				Logger.logFailedRemove(currentUser.getID(), mrID);
				returnValue = PERMISSION_DENIED;
			}
		} else {			
			returnValue = NOT_FOUND;
		}
		return returnValue;
	}

	public String create(String mrID, String patientID, String nurseID, String data) {
		String returnValue;
		if (currentUser instanceof Doctor) {
			Doctor doctor = (Doctor) currentUser;
			records.put(mrID, new MedicalRecord(mrID, patientID, nurseID, doctor.getID(), doctor.getDivision(), data));
			Logger.logCreate(currentUser.getID(), mrID);
			returnValue = "The medical record was successfully created";
		} else {
			Logger.logFailedCreate(currentUser.getID(), mrID);
			returnValue = PERMISSION_DENIED;
		}
		return returnValue;
	}
	
	private void saveRecordsToFile() {
		// TODO Auto-generated method stub
		
	}
}
