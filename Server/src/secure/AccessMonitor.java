package secure;

import user.GA;
import user.User;
import data.ID;
import data.MedicalRecord;

public class AccessMonitor {

	public AccessMonitor() {
		
	}

	public boolean authenticate(ID ID){
		return true;
	}
	
	public void removeMedicalRecord(User user, MedicalRecord mr){
		if(user instanceof GA){
			mr.delete();
		}
	}
}
