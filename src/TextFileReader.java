

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextFileReader {
	
	public static String readFile(String filename){
		File file = new File(filename);
		StringBuilder string_builder = new StringBuilder();
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		while(scanner.hasNextLine()){
			string_builder.append(scanner.nextLine() + "\n");
		}
		scanner.close();
		return string_builder.toString();
	}
}
