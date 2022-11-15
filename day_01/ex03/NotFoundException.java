public class NotFoundException extends RuntimeException {
	@Override
	public String toString() {
		return ("User not found\n");
	}
}