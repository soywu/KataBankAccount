package bank;

public class IdProvider {

	private static int count = 0;

	public static void initCount() {
		count = 0;
	}

	public static int getCountAndPlusOne() {
		return count++;
	}

}
