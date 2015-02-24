package secure;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import user.Doctor;
import user.GA;
import user.Nurse;
import user.Patient;
import user.Staff;
import user.User;
import data.ID;
import data.Logger;
import data.MedicalRecord;

public class AccessController {
	private Logger logger;
	private User currentUser;

	public AccessController(User currentUser) {
		logger = new Logger("logfile");
		this.currentUser = currentUser;
	}

	public boolean authenticate(ID ID) {
		return true;
	}

	public String read(MedicalRecord mr) {
		ArrayList<User> accessList = mr.getAccessList();
		String division = mr.getDivision();
		if (accessList.contains(currentUser)) {
			logger.logRead(currentUser.getID(), mr.getID());
			return mr.read();
		} else if (currentUser instanceof Staff && ((Staff) currentUser).belongsTo(division)) {
			logger.logRead(currentUser.getID(), mr.getID());
			return mr.read();
		} else if (currentUser instanceof GA) {
			logger.logRead(currentUser.getID(), mr.getID());
			return mr.read();
		}
		return null;
	}

	public void write(MedicalRecord mr, String data) {
		ArrayList<User> accessList = mr.getAccessList();
		if (accessList.contains(currentUser)) {
			if (currentUser instanceof Doctor || currentUser instanceof Nurse) {
				mr.write(data);
				logger.logWrite(currentUser.getID(), mr.getID());
			}
		}
	}

	public void remove(MedicalRecord mr) {
		if (currentUser instanceof GA) {
			mr.delete();
			logger.logRemove(currentUser.getID(), mr.getID());
		}
	}

	public void create(Patient patient, Nurse nurse, String data,
			int ID) {
		if (currentUser instanceof Doctor) {
			Doctor doctor = (Doctor) currentUser;
			MedicalRecord mr = new MedicalRecord(doctor, nurse, patient,
					doctor.getDivision(), data, ID);
			logger.logCreate(currentUser.getID(), mr.getID());
		}
	}

	private class RecordHandler {
		public ArrayList<MedicalRecord> read() {
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
