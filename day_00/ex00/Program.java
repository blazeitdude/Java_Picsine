package org.example;

public class Program {
	public static void main(String[] args) {
		int	inp = 479598; // change the value
		int sum = 0;

		if ( inp < 100000 || inp > 999999 ) {
			System.out.print("Error: The input number is not six digits");
			System.exit(-1);
		}
		while (inp > 0) {
			sum += inp % 10;
			inp /= 10;
		}
		System.out.print(sum);
	}
}
