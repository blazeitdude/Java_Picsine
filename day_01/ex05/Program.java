import java.util.UUID;

public class Program {
	public static void main(String[] args) {
		Menu	menu = new Menu();
		if (args.length == 0) {
			System.err.println("arguments are missing");
			System.exit (-1);
		}
		else if (args[0].equals("--profile=dev"))
			menu.setProfile();
		menu.start();
	}
}
