package edu.school21.numbers;

import edu.school21.numbers.NumberWorker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberWorkerTest {

	//expected: true
	@ParameterizedTest
	@ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23,
			29, 31, 37, 41, 43, 47, 53, 59, 61,
			67, 71, 73, 79, 83, 89, 97, 101, 103,
			107, 109, 113, 127, 131, 137, 139, 149, 151,
			157, 163, 167, 173, 179, 181, 191, 193,
			197, 199, 211, 223, 227, 229, 233, 239, 241,
			251, 257, 263, 269, 271, 277, 281, 283, 293, 307})
	public void test_isPrimeForPrimes( int prime ) throws Exception {
		assertTrue(new NumberWorker().isPrime(prime));
	}

	@ParameterizedTest
	@ValueSource(ints = {6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25, 26, 27, 28, 30, 32 })
	public void test_isPrimeForNotPrime( int prime ) throws Exception {
		assertFalse(new NumberWorker().isPrime(prime));
	}

	@ParameterizedTest
	@ValueSource(ints  = {-1, -20, 0, -151})
	public void	test_isPrimeForWrongNumbers( int prime ) throws Exception {
		assertThrows(RuntimeException.class, () -> new NumberWorker().isPrime(prime));
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/data.csv", delimiter = ',')
	public void	test_isDigitsSumCorrect( int sum, int expected ) throws Exception {
		assertEquals(expected, new NumberWorker().digitsSum(sum));
	}

	@Test
	public	void	test_dogitsSumForNegative() throws Exception {
		assertEquals(-10, new NumberWorker().digitsSum(-154));
	}
}
