package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	private static final String PERMISSION_DENIED = " (permission denied)";
	private String fileName;

	public Logger(String fileName) {
		this.fileName = fileName;

	}

	private void write(String content) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(
				new FileWriter(fileName, true)))) {
			out.println(content);
		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}
	}

	public void logRead(String userID, String mrID) {
		write(date() + userID + " read " + mrID);
	}

	public void logWrite(String userID, String mrID) {
		write(date() + userID + " wrote to " + mrID);
	}

	public void logRemove(String userID, String mrID) {
		write(date() + userID + " removed " + mrID);
	}

	public void logCreate(String userID, String mrID) {
		write(date() + userID + " created " + mrID);
	}

	public void logFailedRead(String userID, String mrID) {
		write(date() + userID + " tried to read " + mrID + PERMISSION_DENIED);
	}

	public void logFailedWrite(String userID, String mrID) {
		write(date() + userID + " tried to write to " + mrID + PERMISSION_DENIED);

	}

	public void logFailedRemove(String userID, String mrID) {
		write(date() + userID + " tried to remove " + mrID + PERMISSION_DENIED);

	}

	public void logFailedCreate(String userID, String mrID) {
		write(date() + userID + " tried to create " + mrID + PERMISSION_DENIED);
	}

	private String date() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
		return "[" + df.format(date) + "] ";
	}
}
