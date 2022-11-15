
public class Program {
	private static int	thread_count;
	private static final String	COMMAND  = "--count=";

	public static void main(String[] args) throws InterruptedException {
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

		SynchronisedPrint print = new SynchronisedPrint();

		Thread	hen = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < thread_count; ++i) {
					print.printHen();
				}
			}
		});

		Thread	egg = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < thread_count; ++i) {
					print.printEgg();
				}
			}
		});

		hen.start();
		egg.start();

	}
}
