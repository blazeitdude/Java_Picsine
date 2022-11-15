import java.util.Scanner;

public class Program {
	public static void main(String[] args) {
		Scanner	scanner = new Scanner(System.in);
		String	inputLine;
		int		inp;
		long	grades = 0;
		int		weeks = 0;

		while( weeks < 18 ) {
			System.out.print("-> ");
			inputLine = scanner.next();
			if (inputLine.equals("42")) {
				break;
			}
			else if (!inputLine.equals("Week") || scanner.nextInt() - weeks != 1) {
				System.err.print("IllegalArgument");
				System.exit(-1);
			}
			weeks++;
			int	min = 9;
			for (int i = 0; i < 5; i++) {
				inp = scanner.nextInt();
				if (inp < 1 || inp > 9) {
					System.err.print("IllegalArgument");
					System.exit(-1);
				}
				min = (inp < min ? inp : min);
			}
			grades = grades  * 10 + min;
		}
		displayResult(grades, weeks);
	}

	private static void	displayResult( long grades, int weeks ) {
		int	denominator = 1;

		while (--weeks > 0) {
			denominator *= 10;
		}
		while (denominator > 0) {
			System.out.print("Week " + ++weeks + ' ');
			int	tmp = (int)grades / denominator;
			grades %= denominator;
			denominator /= 10;
			while (tmp-- > 0) {
				System.out.print("=");
			}
			System.out.println(">");
		}
	}
}
