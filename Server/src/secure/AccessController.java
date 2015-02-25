package secure;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import test.DataGenerator;
import user.*;
import data.Logger;
import data.MedicalRecord;

public class AccessController {
	private Logger logger;
	private User currentUser;
	private HashMap<String, User> users;
	private HashMap<String, MedicalRecord> records;
	private static final String PERMISSION_DENIED = "Error: Permission denied";
	private static final String NOT_FOUND = "Error: Medical Record not found";

	public AccessController(String logFilePath) {
		loadData();
		logger = new Logger(logFilePath);
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
		MedicalRecord mr = records.get(mrID);
		if (mr == null) {
			return NOT_FOUND;
		}
		ArrayList<String> accessList = mr.getAccessList();
		String mrDivision = mr.getDivision();
		if (accessList.contains(currentUser.getID())) {
			logger.logRead(currentUser.getID(), mrID);
			return mr.read();
		} else if (currentUser instanceof Staff
				&& ((Staff) currentUser).getDivision().equals(mrDivision)) {
			logger.logRead(currentUser.getID(), mrID);
			return mr.read();
		} else if (currentUser instanceof GA) {
			logger.logRead(currentUser.getID(), mrID);
			return mr.read();
		}
		logger.logFailedRead(currentUser.getID(), mrID);
		return PERMISSION_DENIED;
	}

	public String write(String mrID, String data) {
		if (currentUser instanceof Doctor || currentUser instanceof Nurse) {
			MedicalRecord mr = records.get(mrID);
			if (mr == null) {
				return NOT_FOUND;
			}
			ArrayList<String> accessList = mr.getAccessList();
			if (accessList.contains(currentUser.getID())) {
				mr.write(data);
				saveRecordsToFile();
				logger.logWrite(currentUser.getID(), mrID);
				return "Write successful";
			}
		}
		logger.logFailedWrite(currentUser.getID(), mrID);
		return PERMISSION_DENIED;
	}

	public String remove(String mrID) {
		if (currentUser instanceof GA) {
			MedicalRecord mr = records.get(mrID);
			if(mr == null){
				return NOT_FOUND;
			}
			records.remove(mr.getID());
			logger.logRemove(currentUser.getID(), mr.getID());
			return "The medical record was successfully removed";
		}
		logger.logFailedRemove(currentUser.getID(), mrID);
		return PERMISSION_DENIED;
	}

	public String create(String mrID, String patientID, String nurseID, String data) {
		if (currentUser instanceof Doctor) {
			Doctor doctor = (Doctor) currentUser;
			records.put(mrID, new MedicalRecord(mrID, patientID, nurseID, doctor.getID(), doctor.getDivision(), data));
			logger.logCreate(currentUser.getID(), mrID);
			return "The medical record was successfully created";
		}
		logger.logFailedCreate(currentUser.getID(), mrID);
		return PERMISSION_DENIED;
	}
	
	private void saveRecordsToFile() {
		// TODO Auto-generated method stub
		
	}
}
