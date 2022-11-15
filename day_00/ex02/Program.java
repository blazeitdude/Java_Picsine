import java.util.Scanner;

public class Program {

	private static int	getSumOfDigits(int inp) {
		int sum = 0;
		while (inp > 0) {
			sum += inp % 10;
			inp /= 10;
		}
		return (sum);
	}

	private static int	isPrimeOrNot(int inp) {

		if ( inp < 2 ) {
			return (0);
		}

		for (int i = 2; i * i <= inp; i++) {
			if (inp % i == 0) {
				return (0);
			}
		}
		return (1);
	}

	public static void main(String[] args) {
		Scanner	scanner = new Scanner(System.in);
		int		inp = 0;
		int		ret = 0;

		while(true) {
			System.out.print("-> ");
			inp = scanner.nextInt();
			if (inp == 42) {
				break;
			}
			ret += isPrimeOrNot(getSumOfDigits(inp));
		}
		System.out.print("Count of coffee-request - " + ret);
 	}
}
