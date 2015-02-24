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

public class AccessMonitor {
	private Logger logger;

	public AccessMonitor() {
		logger = new Logger("logfile");
	}

	public boolean authenticate(ID ID) {
		return true;
	}

	public String read(User user, MedicalRecord mr) {
		ArrayList<User> accessList = mr.getAccessList();
		String division = mr.getDivision();
		if (accessList.contains(user)) {
			logger.logRead(user.getID(), mr.getID());
			return mr.read();
		} else if (user instanceof Staff && ((Staff) user).belongsTo(division)) {
			logger.logRead(user.getID(), mr.getID());
			return mr.read();
		} else if (user instanceof GA) {
			logger.logRead(user.getID(), mr.getID());
			return mr.read();
		}
		return null;
	}

	public void write(User user, MedicalRecord mr, String data) {
		ArrayList<User> accessList = mr.getAccessList();
		if (accessList.contains(user)) {
			if (user instanceof Doctor || user instanceof Nurse) {
				mr.write(data);
				logger.logWrite(user.getID(), mr.getID());
			}
		}
	}

	public void remove(User user, MedicalRecord mr) {
		if (user instanceof GA) {
			mr.delete();
			logger.logRemove(user.getID(), mr.getID());
		}
	}

	public void create(User user, Nurse nurse, Patient patient, String data,
			int ID) {
		if (user instanceof Doctor) {
			Doctor doctor = (Doctor) user;
			MedicalRecord mr = new MedicalRecord(doctor, nurse, patient,
					doctor.getDivision(), data, ID);
			logger.logCreate(user.getID(), mr.getID());
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
