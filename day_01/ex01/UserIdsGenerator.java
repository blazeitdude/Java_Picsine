public class UserIdsGenerator {
	private static int	last_ID = 1;
	private static UserIdsGenerator instance = null;

	public static UserIdsGenerator getInstance() {
		if (instance == null)
			instance = new UserIdsGenerator();
		return (instance);
	}

	public int	generateId() {
		return last_ID++;
	}
}
