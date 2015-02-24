package secure;

public class InputHandler {
	private AccessController accessController;
	
	public InputHandler(AccessController ac) {
		accessController = ac;
	}

	public String handleCommand(String c) throws ArrayIndexOutOfBoundsException {
		/*Split around quotation marks to get data - always last element (will have length 1 if no data exists) */
		String[] dataInput = c.split("\"");

		/* Split input around spaces and add dataInput if it exists */
		String[] input = dataInput[0].split(" ");

		/* Separate command from arguments */
		String command = input[0];

		/* Make room for data in case it exists */
		String[] arguments = new String[input.length];
		
		/* Add the elements in input to argument, except the first (command) */
		for (int i = 1; i < input.length; i++) {
			arguments[i-1] = input[i];
		}
		
		/* If data exists, add it to arguments */
		if(dataInput.length > 1){
			arguments[arguments.length - 1] = dataInput[1];
		}

		/* Handle each command in a separate method */
		String returnValue;
		switch (command) {
		case "read":
			returnValue = handleRead(arguments);
			break;
		case "write":
			returnValue = handleWrite(arguments);
			break;
		case "remove":
			returnValue = handleRemove(arguments);
			break;
		case "create":
			returnValue = handleCreate(arguments);
			break;
		default:
			returnValue = "Command: " + command + " does not exist.";
			break;
		}
		return returnValue;
	}

	private String handleRead(String[] arguments) throws ArrayIndexOutOfBoundsException {
		String mrID = arguments[0];
		return accessController.read(mrID);
	}

	private String handleWrite(String[] arguments) throws ArrayIndexOutOfBoundsException {
		String mrID = arguments[0];
		String data = arguments[1];
		return accessController.write(mrID, data);
	}

	private String handleRemove(String[] arguments) throws ArrayIndexOutOfBoundsException {
		String mrID = arguments[0];
		return accessController.remove(mrID);
	}

	private String handleCreate(String[] arguments) throws ArrayIndexOutOfBoundsException {
		String mrID = arguments[0];
		String patientID = arguments[1];
		String nurseID = arguments[2];
		String data = arguments[3];
		return accessController.create(mrID, patientID, nurseID, data);
	}
}
