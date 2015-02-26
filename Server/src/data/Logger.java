package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	private static final String PERMISSION_DENIED = " (permission denied)";
	private static String fileName = "./historylog";

	private static void write(String content) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
			out.println(content);
			out.close();
		} catch (IOException e) {
			System.out.println("Error: Logfile could not be found, exiting");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void logRead(String userID, String mrID) {
		write(date() + userID + "\tread\t\t" + mrID);
	}

	public static void logWrite(String userID, String mrID) {
		write(date() + userID + "\twrote to\t" + mrID);
	}

	public static void logRemove(String userID, String mrID) {
		write(date() + userID + "\tremoved\t\t" + mrID);
	}

	public static void logCreate(String userID, String mrID) {
		write(date() + userID + "\tcreated\t\t" + mrID);
	}

	public static void logFailedRead(String userID, String mrID) {
		write(date() + userID + "\ttried to read\t" + mrID + PERMISSION_DENIED);
	}

	public static void logFailedWrite(String userID, String mrID) {
		write(date() + userID + "\ttried to write to " + mrID + PERMISSION_DENIED);

	}

	public static void logFailedRemove(String userID, String mrID) {
		write(date() + userID + "\ttried to remove\t" + mrID + PERMISSION_DENIED);

	}

	public static void logFailedCreate(String userID, String mrID) {
		write(date() + userID + "\ttried to create " + mrID + PERMISSION_DENIED);
	}

	private static String date() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return "[" + df.format(date) + "] ";
	}
}
