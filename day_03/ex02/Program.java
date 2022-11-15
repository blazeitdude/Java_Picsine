import java.util.Random;

public class Program {
	private static int	THREADS_COUNT = 0;
	private static int	ARRAY_SIZE = 0;
	private static final String	COMMAND1  = "--arraySize=";
	private static final String	COMMAND2  = "--threadsCount=";

	public static void main(String[] args) {
		int	sum = 0;
		long	thread_sum = 0;
		int	steps = 0;

		if (args.length != 2 || !args[0].startsWith(COMMAND1) || !args[1].startsWith(COMMAND2)) {
			System.out.println("Error: invalid input argument format");
			System.exit(1);
		}
		try {
			ARRAY_SIZE	 = Integer.parseInt(args[0].substring(COMMAND1.length()));
			THREADS_COUNT = Integer.parseInt(args[1].substring(COMMAND2.length()));
			if (ARRAY_SIZE > 2000000 || THREADS_COUNT > ARRAY_SIZE)  {
				System.err.println("Bad argument!");
				System.exit(-1);
			}
		} catch (NumberFormatException numberFormatException) {
			System.err.println(numberFormatException.getMessage());
			System.exit(1);
		}
		int[]	array = new int[ARRAY_SIZE];
		Random	random = new Random();

		for (int i = 0; i < ARRAY_SIZE; i++) {
			array[i] = random.nextInt(1000);
		}
		for (int j = 0; j < ARRAY_SIZE; j++) {
			sum += array[j];
		}
		System.out.println("Sum: " + sum);
		ThreadsArray[]	threadsArrays = new ThreadsArray[THREADS_COUNT];
		steps = ARRAY_SIZE / THREADS_COUNT + 1;
		for (int k = 0; k < THREADS_COUNT; k++) {
			if ((THREADS_COUNT - 1) > k) {
				int notLast = (k + 1) * steps - 1;
				threadsArrays[k] = new ThreadsArray(k, steps, notLast, array);
			} else {
				threadsArrays[k] = new ThreadsArray(k, steps, ARRAY_SIZE - 1, array);
			}
			threadsArrays[k].start();
		}
		for (int i = 0; i < THREADS_COUNT; i++) {
			try {
				threadsArrays[i].join();
			} catch (InterruptedException interruptedException) {
				interruptedException.printStackTrace();
			}
		}
		for (int k = 0; k < THREADS_COUNT; k++) {
			thread_sum += threadsArrays[k].getSum();
		}
		System.out.println("Sum by threads: " + thread_sum);
	}
}
