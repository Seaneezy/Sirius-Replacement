package sunquestConverter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
public class ObtainDataFromTextFile {
	/** TESTING CLASS: WORKS
	public static void main(String[] args) throws IOException{
		//returns each line in the file. Useful so I could separate the input from the method of reading the data easily.
		System.out.println(getLines("C:/Users/Summer Student 2/My Documents/other project things/testScanner.txt"));
	}
	*/
	//simpler method for use in other classes
	//@param = file location of the text file to read
	public static List<String> getLines(String aFileLocation) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(aFileLocation), StandardCharsets.UTF_8);
		return lines;
	}
	
	

}
