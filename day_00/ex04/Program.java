import java.util.Scanner;

public class Program {
	private static final int MAXIMUM_CHART_HEIGHT = 10;
	private static final int MAXIMUM_TOP_CHARACTERS = 10;
	private static final int MAXIMUM_CHARS = 65536;

	public static void main(String[] args) {
		int[][] topChars = new int[2][MAXIMUM_TOP_CHARACTERS];
		Scanner sc = new Scanner(System.in);
		freqChars(sc.nextLine(), topChars);
		printChart(topChars);
	}

	private static void freqChars(String str, int[][] topChars) {
		char[] charArray = str.toCharArray();
		char[] chars = new char[MAXIMUM_CHARS];
		int maximum[] = new int[2];

		for (char c: charArray)
			chars[c] += 1;
		for (int i = 0; i < MAXIMUM_TOP_CHARACTERS; ++i) {
			maximum[0] = 0;
			for (int j = 0; j < MAXIMUM_CHARS; ++j) {
				if (chars[j] > maximum[0]) {
					maximum[0] = chars[j];
					maximum[1] = j;
				}
			}
			if (maximum[0] == 0)
				break;
			chars[maximum[1]] = 0;
			topChars[0][i] = maximum[0];
			topChars[1][i] = (char)maximum[1];
		}
	}

	private static void printChart(int[][] topChars) {
		int maximum  = 0;
		for (int i = 0; i < topChars.length; i++) {
			if (topChars[0][i] > maximum)
				maximum = topChars[0][i];
		}
		for (int height = MAXIMUM_CHART_HEIGHT; height >= -1; --height) {
			for (int i = 0; i < MAXIMUM_TOP_CHARACTERS && topChars[0][i] > 0; ++i) {
				if (height == -1)
					System.out.printf("%3c", (char)topChars[1][i]);
				else if (height == MAXIMUM_CHART_HEIGHT * topChars[0][i] / maximum)
					System.out.printf("%3d", topChars[0][i]);
				else if (height < MAXIMUM_CHART_HEIGHT * topChars[0][i] / maximum)
					System.out.printf("%3c", '#');
				else
					System.out.printf("%c", ' ');
			}
			System.out.println();
		}

	}
}
