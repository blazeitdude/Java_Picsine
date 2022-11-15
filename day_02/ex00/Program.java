import java.io.*;
import java.util.Scanner;

public class Program {

	private static final String SIGNATURES ="./signature.txt";
	private static final String RESULT = "result.txt";

	public static void main(String[] args) throws IOException {
		FileReader inputStream = null;
		FileInputStream fileInputStream = null;
		try {
			inputStream = new FileReader(SIGNATURES);
		} catch (FileNotFoundException e) {
			System.err.println("Error: File not found");
			System.exit(-1);
		}

		BufferedReader bufferInput = new BufferedReader(inputStream);
		SignatureAnalysator.createFromSignatureFile(bufferInput);

		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(RESULT, true);
		} catch (FileNotFoundException e) {
			System.err.println("Error: Can't create file");
			System.exit(-1);
		}

		Scanner console = new Scanner(System.in);
		String currentInput = "";
		while (!currentInput.equals("42")){
			System.out.print("Enter file path: ");
			currentInput = console.nextLine();
			try {
				fileInputStream = new FileInputStream(currentInput);
			} catch (FileNotFoundException e) {
				System.out.println("Error: File not found");
				continue;
			}

			BufferedInputStream bufferedReader = new BufferedInputStream(fileInputStream);
			if(SignatureAnalysator.checkSignature(bufferedReader)){
				SignatureAnalysator.extensionWrite(fileOutputStream);
				System.out.println("PROCESSED");
			}else {
				System.out.println("UNDEFINED");
			}
		}

//        while ((c = (byte) inputStream.read()) != -1){
//            System.out.format("%02X ", c);
//        }

		try {
			inputStream.close();
			fileInputStream.close();
			fileOutputStream.close();
		} catch (java.io.IOException e) {
			throw new RuntimeException(e);
		}
	}
}