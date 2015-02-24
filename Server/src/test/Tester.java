package test;

import secure.*;

public class Tester {
	public static void main(String[] args) {
		AccessController ac = new AccessController("Alice", "./historylog");
		InputHandler ih = new InputHandler(ac);

		testCreate(ih);
		testRead(ih);
		testWrite(ih);
		testRead(ih);
		testRemove(ih);
	}

	private static void testCreate(InputHandler ih) {
		String command = "create anna-2015-02-24 anna klas \"Före write\"";
		System.out.println(ih.handleCommand(command));
	}

	private static void testWrite(InputHandler ih) {
		String command = "write anna-2015-02-24 \"Efter write\"";
		System.out.println(ih.handleCommand(command));
	}

	private static void testRead(InputHandler ih) {
		String command = "read anna-2015-02-24";
		System.out.println(ih.handleCommand(command));
	}

	private static void testRemove(InputHandler ih) {
		String command = "remove anna-2015-02-24";
		System.out.println(ih.handleCommand(command));
	}
}
