import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.*;

public class Program {

	private static final LinkedHashSet<String> result = new LinkedHashSet<>();
	private static TreeSet<String> sortedResult = new TreeSet<>();

	private static final String RESULT_FILE = "dictionary.txt";

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Incorrect number of arguments");
			System.exit(1);
		}

		FileReader	fileA = null;
		FileReader	fileB = null;
		FileWriter result_fileWriter = null;
		try {
			fileA = new FileReader(args[0]);
			fileB = new FileReader(args[1]);
			result_fileWriter = new FileWriter(RESULT_FILE);
		} catch (FileNotFoundException fileNotFoundException) {
			System.out.println("Unable to open one of the files");
			System.exit(1);
		} catch (IOException ioException) {
			System.out.println("unable to find/create a file " + RESULT_FILE);
			System.exit(1);
		}

		HashMap<String, Integer>mapA = new HashMap<String, Integer>();
		HashMap<String, Integer>mapB = new HashMap<String ,Integer>();

		fillMap(new BufferedReader(fileA), mapA);
		fillMap(new BufferedReader(fileB), mapB);

		sortedResult = new TreeSet<>(result);

		ArrayList<Integer> countElementA = getFrequencyOfOccurence(mapA);
		ArrayList<Integer> countElementB = getFrequencyOfOccurence(mapB);

		try {
			fileA.close();
			fileB.close();
			for(String word : sortedResult) {
				result_fileWriter.write(word + " ");
				result_fileWriter.flush();

			}
			result_fileWriter.close();

		} catch (IOException ioException) {
			System.err.println(ioException.getMessage());
		}

		double	numerator = calculateNumerator(countElementA, countElementB);
		if (numerator == 0) {
			System.err.println("denominator = 0");
			System.exit(1);
		}
		double	denominator = calculateDenominator(countElementA, countElementB);
		numerator = numerator / denominator;
		System.out.format("similarity = %.3f", numerator);
	}

	public static void fillMap(BufferedReader bufferedReader, HashMap<String, Integer> map) {
		String	buff;

		try {
			while ((buff = bufferedReader.readLine()) != null) {
				String[]	splitedBuff = buff.split(" ");

				for (String word : splitedBuff) {
					if (!map.containsKey(word)) {
						map.put(word, 1);
					} else {
						map.put(word, map.get(word) + 1);
					}
				}
				for (Map.Entry<String, Integer> word : map.entrySet()) {
					result.add(word.getKey());
				}
			}
		} catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static ArrayList<Integer> getFrequencyOfOccurence(HashMap<String, Integer> hashMap) {
		ArrayList<Integer> tmp = new ArrayList<>();

		for (String word : sortedResult) {
			tmp.add(hashMap.getOrDefault(word, 0));
		}
		return tmp;
	}

	private static double calculateNumerator(ArrayList<Integer> countA, ArrayList<Integer> countB) {
		double	tmp = 0;

		for (int i = 0; i < sortedResult.size(); i++) {
			tmp += countA.get(i) * countB.get(i);
		}
		return (tmp);
	}

	private static double calculateDenominator(ArrayList<Integer> countA, ArrayList<Integer> countB) {
		double denominatorA = 0;
		double denominatorB = 0;

		for (int i : countA) {
			denominatorA += i * i;
		}

		for (int j : countB) {
			denominatorB += j * j;
		}
		return (Math.sqrt(denominatorA) * Math.sqrt(denominatorB));
	}
}

