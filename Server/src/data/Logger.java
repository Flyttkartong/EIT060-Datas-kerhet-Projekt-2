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
		write(date() + userID + " read " + "{" + mrID + "}");
	}

	public static void logWrite(String userID, String mrID) {
		write(date() + userID + " wrote to " + "{" + mrID + "}");
	}

	public static void logRemove(String userID, String mrID) {
		write(date() + userID + " removed " + "{" + mrID + "}");
	}

	public static void logCreate(String userID, String mrID) {
		write(date() + userID + " created " + "{" + mrID + "}");
	}

	public static void logFailedRead(String userID, String mrID) {
		write(date() + userID + " tried to read " + "{" + mrID + "}" + PERMISSION_DENIED);
	}

	public static void logFailedWrite(String userID, String mrID) {
		write(date() + userID + " tried to write to " + "{" + mrID + "}" + PERMISSION_DENIED);

	}

	public static void logFailedRemove(String userID, String mrID) {
		write(date() + userID + " tried to remove " + "{" + mrID + "}" + PERMISSION_DENIED);

	}

	public static void logFailedCreate(String userID, String mrID) {
		write(date() + userID + " tried to create " + "{" + mrID + "}" + PERMISSION_DENIED);
	}
	
	public static void logLogin(String userID) {
		write(date() + userID + " logged in");
	}
	
	public static void logLogout(String userID) {
		write(date() + userID + " logged out");
	}

	private static String date() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return "[" + df.format(date) + "] ";
	}
}
