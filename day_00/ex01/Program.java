import java.util.Scanner;

public class Program {
	public static void main(String[] args) {
		Scanner	scanner = new Scanner(System.in);
		System.out.print("-> ");
		int inp = scanner.nextInt();
		int	steps = 1;

		if ( inp < 2 ) {
			System.err.print("IllegalArgument");
			System.exit(-1);
		}

		for (int i = 2; i * i <= inp; i++) {
			if (inp % i == 0) {
				System.out.print("false " + steps);
				System.exit(0);
			}
			steps++;
		}
		System.out.print("true " + steps);
	}
}
