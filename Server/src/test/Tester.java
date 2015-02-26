package test;

import secure.*;

public class Tester {
	public static void main(String[] args) {
		AccessController ac = new AccessController();
		ac.initialize("Alice");
		InputHandler ih = new InputHandler(ac);

		testCreate(ih);
		testRead(ih);
		testWrite(ih);
		testRead(ih);
		ac.initialize("Bengt");
		testRemove(ih);
		
		testBadCommands(ih);
	}
	private static void testCreate(InputHandler ih) {
		String command = "create Anna-2015-02-24 Anna Klas \"Before write\"";
		System.out.println(ih.handleCommand(command));
	}

	private static void testWrite(InputHandler ih) {
		String command = "write Anna-2015-02-24 \"After write\"";
		System.out.println(ih.handleCommand(command));
	}

	private static void testRead(InputHandler ih) {
		String command = "read Anna-2015-02-24";
		System.out.println(ih.handleCommand(command));
	}

	private static void testRemove(InputHandler ih) {
		String command = "remove Anna-2015-02-24";
		System.out.println(ih.handleCommand(command));
	}
	
	private static void testBadCommands(InputHandler ih) {
		String command = "create a b";
		System.out.println(ih.handleCommand(command));
		
		command = "abc";
		System.out.println(ih.handleCommand(command));
	}
}
