package secure;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import user.*;
import data.Logger;
import data.MedicalRecord;

public class AccessController {
	private Logger logger;
	private User currentUser;
	private HashMap<String, User> users;
	private HashMap<String, MedicalRecord> records;
	private final String DENIED = "Permission denied.";

	public AccessController(String userID) {
		loadData();
		currentUser = users.get(userID);
		logger = new Logger("logfile");
	}

	private void loadData() {
		// TODO Load users and medical records from file
		users = new HashMap<String, User>();
		records = new HashMap<String, MedicalRecord>();
	}

	public String read(String mrID) {
		MedicalRecord mr = records.get(mrID);
		ArrayList<String> accessList = mr.getAccessList();
		String mrDivision = mr.getDivision();
		if (accessList.contains(currentUser.getID())) {
			logger.logRead(currentUser.getID(), mrID);
			return mr.read();
		} else if (currentUser instanceof Staff && ((Staff) currentUser).getDivision().equals(mrDivision)) {
			logger.logRead(currentUser.getID(), mrID);
			return mr.read();
		} else if (currentUser instanceof GA) {
			logger.logRead(currentUser.getID(), mrID);
			return mr.read();
		}
		return DENIED;
	}

	public String write(String mrID, String data) {
		MedicalRecord mr = records.get(mrID);
		ArrayList<String> accessList = mr.getAccessList();
		if (accessList.contains(currentUser.getID())) {
			if (currentUser instanceof Doctor || currentUser instanceof Nurse) {
				mr.write(data);
				logger.logWrite(currentUser.getID(), mrID);
				return "Write successful!";
			}
		}
		return DENIED;
	}

	public String remove(String mrID) {
		if (currentUser instanceof GA) {
			records.remove(mrID);
			logger.logRemove(currentUser.getID(), mrID);
			return "The medical record was successfully removed!";
		}
		return DENIED;
	}

	public String create(String mrID, String patientID, String nurseID, String data) {
		if (currentUser instanceof Doctor) {
			Doctor doctor = (Doctor) currentUser;
			records.put(mrID, new MedicalRecord(mrID, patientID, nurseID, doctor.getID(), doctor.getDivision(), data));
			logger.logCreate(currentUser.getID(), mrID);
			return "The medical record was successfully created!";
		}
		return DENIED;
	}

	private class RecordHandler {
		public HashMap<String, User> read() {
			Charset charset = Charset.forName("US-ASCII");
			try (BufferedReader reader = Files.newBufferedReader(
					Paths.get("./Medical Records"), charset)) {
				String line = null;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
			return null;
		}
	}
}
