package secure;

import java.util.ArrayList;

import user.GA;
import user.Staff;
import user.User;
import data.ID;
import data.MedicalRecord;

public class AccessMonitor {

	public AccessMonitor() {

	}

	public boolean authenticate(ID ID) {
		return true;
	}

	public String read(User user, MedicalRecord mr) {
		ArrayList<User> accessList = mr.getAccessList();
		String division = mr.getDivision();
		if (accessList.contains(user)) {
			return mr.read();
		} else if (user instanceof Staff && ((Staff) user).belongsTo(division)) {
			return mr.read();
		} else if (user instanceof GA) {
			return mr.read();
		}
		return null;
	}

	public void removeMedicalRecord(User user, MedicalRecord mr) {
		if (user instanceof GA) {
			mr.delete();
		}
	}
}
