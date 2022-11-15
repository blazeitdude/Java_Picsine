
import java.lang.Thread;

public class Program {
	private static int	thread_count = 0;
	private static final String	COMMAND  = "--count=";

	private static class Egg implements Runnable {
		@Override
		public void run() {
			printMessage("Egg");
		}
	}

	private static class Hen implements Runnable {
		@Override
		public void run() {
			printMessage("Hen");
		}
	}

	public static void	printMessage(String message) {
		for (int i = 0; i < thread_count ; i++ ) {
			System.out.println(message);
		}
	}

	public static void main(String[] args) throws InterruptedException{
		if (args.length != 1 || !args[0].startsWith(COMMAND)) {
			System.out.println("Error: invalid input argument format");
			System.exit(1);
		}
		try {
			thread_count = Integer.parseInt(args[0].substring(COMMAND.length()));
		} catch (NumberFormatException numberFormatException) {
			System.err.println(numberFormatException.getMessage());
			System.exit(1);
		}

		Thread	hen = new Thread(new Hen());
		Thread	egg = new Thread(new Egg());

		hen.start();
		egg.start();

		hen.join();
		egg.join();

		printMessage("Human");
	}
}
