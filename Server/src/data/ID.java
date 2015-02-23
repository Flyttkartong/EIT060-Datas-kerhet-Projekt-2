package data;

public class ID {
	private final String ID;

	public ID(String s1, String s2) {
		// ID-implementation
		ID = s1 + s2;
	}

	@Override
	public String toString() {
		return ID;
	}
}
