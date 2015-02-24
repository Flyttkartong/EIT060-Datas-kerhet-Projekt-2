package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
	private static final String PERMISSION_DENIED = " (permission denied)";
	private String fileName;

	public Logger(String fileName) {
		this.fileName = fileName;
	}
	
	private void write(String content) {
		try {
			File file = new File(fileName);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logRead(String userID, String mrID) {
		write("User: " + userID + " read Medical Record: " + mrID);
	}

	public void logWrite(String userID, String mrID) {
		write("User: " + userID + " wrote to Medical Record: " + mrID);
	}

	public void logRemove(String userID, String mrID) {
		write("User: " + userID + " removed Medical Record: " + mrID);
	}

	public void logCreate(String userID, String mrID) {
		write("User: " + userID + " created Medical Record: " + mrID);
	}

	public void logFailedRead(String userID, String mrID) {
		write("User: " + userID + " tried to read Medical Record: " + mrID + PERMISSION_DENIED);
	}

	public void logFailedWrite(String userID, String mrID) {
		write("User: " + userID + " tried to write to Medical Record: " + mrID + PERMISSION_DENIED);
		
	}

	public void logFailedRemove(String userID, String mrID) {
		write("User: " + userID + " tried to remove Medical Record: " + mrID + PERMISSION_DENIED);
		
	}

	public void logFailedCreate(String userID, String mrID) {
		write("User: " + userID + " tried to create Medical Record: " + mrID + PERMISSION_DENIED);
	}
}
