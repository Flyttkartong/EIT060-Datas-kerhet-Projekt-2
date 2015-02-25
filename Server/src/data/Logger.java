package data;

import java.io.BufferedWriter;
import java.io.File;
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
		if(!new File(fileName).exists()){
			write("Time\t   User\t\tAction\t\tMedical Record");
		}
		
	}

	private void write(String content) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(
				new FileWriter(fileName, true)))) {
			out.println(content);
		} catch (IOException e) {
			System.out.println("Error: Logfile could not be found, exiting");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void logRead(String userID, String mrID) {
		write(date() + userID + "\tread\t\t" + mrID);
	}

	public void logWrite(String userID, String mrID) {
		write(date() + userID + "\twrote to\t" + mrID);
	}

	public void logRemove(String userID, String mrID) {
		write(date() + userID + "\tremoved\t\t" + mrID);
	}

	public void logCreate(String userID, String mrID) {
		write(date() + userID + "\tcreated\t\t" + mrID);
	}

	public void logFailedRead(String userID, String mrID) {
		write(date() + userID + "\ttried to read\t" + mrID + PERMISSION_DENIED);
	}

	public void logFailedWrite(String userID, String mrID) {
		write(date() + userID + "\ttried to write to " + mrID + PERMISSION_DENIED);

	}

	public void logFailedRemove(String userID, String mrID) {
		write(date() + userID + "\ttried to remove\t" + mrID + PERMISSION_DENIED);

	}

	public void logFailedCreate(String userID, String mrID) {
		write(date() + userID + "\ttried to create " + mrID + PERMISSION_DENIED);
	}

	private String date() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
		return "[" + df.format(date) + "] ";
	}
}
