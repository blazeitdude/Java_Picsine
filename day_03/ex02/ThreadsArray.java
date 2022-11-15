public class ThreadsArray extends Thread {
	int		start;
	int		end;
	int		steps;
	int[]	array;
	long	sum;

	public ThreadsArray(int start, int steps, int end, int[] array) {
		this.start = start;
		this.end = end;
		this.steps = steps;
		this.array = array;
		sum = 0;
	}

	public long	getSum() {
		return sum;
	}

	@Override
	public void run() {
		for (int i = start * steps; i <= end; i++) {
			sum += array[i];
		}
		System.out.println("Thread " + (start + 1) + ": from " + start * steps + " to " + end + " sum is " + sum);
	}
}
