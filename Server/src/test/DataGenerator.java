package test;

import java.util.HashMap;

import data.MedicalRecord;
import user.*;

public final class DataGenerator {
	public static HashMap<String, User> createUserMap() {
		Patient anna = new Patient("Anna");
		Patient fredrik = new Patient("Fredrik");
		Patient johan = new Patient("Johan");

		GA bengt = new GA("Bengt");

		Nurse klas = new Nurse("Klas", "Allsvenskan");
		Nurse urban = new Nurse("Urban", "Superettan");

		Doctor bob = new Doctor("Bob", "Allsvenskan");
		Doctor alice = new Doctor("Alice", "Allsvenskan");

		HashMap<String, User> users = new HashMap<String, User>();

		users.put(anna.getID(), anna);
		users.put(fredrik.getID(), fredrik);
		users.put(johan.getID(), johan);
		users.put(bengt.getID(), bengt);
		users.put(klas.getID(), klas);
		users.put(urban.getID(), urban);
		users.put(bob.getID(), bob);
		users.put(alice.getID(), alice);

		return users;
	}

	public static HashMap<String, MedicalRecord> createRecordMap() {
		// TODO
		return new HashMap<String, MedicalRecord>();
	}
}
