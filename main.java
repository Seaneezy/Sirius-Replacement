/**
 * @author Sean Sponsler
 * WARNINGS WILL EXIST UNTIL FULL IMPLEMENTATION
 * Impromptu to-do list:
 * First run check and initialization: DONE
 * Property file check and initialization: DONE
 * Input to change properties on first run: DONE(UNTIL GUI IMPLEMENTATION)
 * Obtain data from lines of text input: DONE
 * Parse data to text file format: INCOMPLETE
 * -
 * Parse data to Oracle format: INCOMPLETE
 * -
 * Parse data to Invoice format: INCOMPLETE
 * -
 * Locate errors in text file input and specify them: INCOMPLETE
 * -
 * Create GUI: INCOMPLETE
 * -
 */
package sunquestConverter;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.prefs.Preferences;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;
public class main {
	public static final String CONF_LOCATION = "config.properties";
	public static String SQ_LOCATION = "";
	public static String OUT_LOCATION = "";
	public static void main(String[] args) throws IOException {
		File file = new File(CONF_LOCATION);
		if (isFirstRun(file) == true) {
			firstRunInitialization();
		}
		
		Properties properties = new Properties();
		InputStream input = null;
		input = new FileInputStream("config.properties");
		String sunquestFilesLocation = properties.getProperty("SunquestFilePath");
		String outputFilesLocation = properties.getProperty("OutputFolderLocation");
	}
	public static void firstRunInitialization() throws IOException{
		Scanner in = new Scanner(System.in);
		//MAKESHIFT INPUT UNTIL GUI IS IMPLEMENTED
		boolean SQFileExists = false;
		boolean OutputFileExists = false;
		String SQFilePath = "C:/";
		String OutputLocation = "C:/";
		while (SQFileExists == false) {
			System.out.print("Please enter the location for the SUNQuest files(use '/' in place of '\'): ");
			SQFilePath = in.nextLine();
			File SQFile = new File(SQFilePath);
			if (SQFile.exists() == true) {
				SQ_LOCATION = SQFilePath;
				SQFileExists = true;
			}
			else {
				System.out.println("Invalid location for SUNQuest files!");
			}
		}
		while (OutputFileExists == false) {
			System.out.print("\nPlease enter the location for the output(use '/' in place of '\'): ");
			OutputLocation = in.nextLine();
			File OutputFile = new File(OutputLocation);
			if (OutputFile.exists() == true) {
				OUT_LOCATION = OutputLocation;
				OutputFileExists = true;
			}
			else {
				System.out.println("Invalid location for output file!");
			}
			
		}
		in.close();
		File directory = new File("C:/SQConverter");
		if (!directory.exists()) {
			System.out.println("Creating directory(C:/SQConverter)...");
			boolean successful = directory.mkdir();
			if (successful == true) {
				System.out.println("Creation successful");
			}
			else {
				System.out.println("Creation failed");
			}
		}
		//property file is located in the base directory so it will be in workspace until it is .jar'ed(90% sure on this, low priority)
		Properties properties = new Properties();
		OutputStream output = null;
		output = new FileOutputStream("config.properties");
		//setting property values
		//names are not final
		properties.setProperty("SunquestFilePath",SQFilePath);
		properties.setProperty("OutputFolderLocation", OutputLocation);
		properties.store(output, null);
		//closing the output to the file
		if (output != null) {
			output.close();
		}
	}
	
	public static boolean isFirstRun(File f) throws IOException {
		//names are not final
		//NEED TO FIND USERNAMES FOR MORE SPECIFIC TARGETING
		if (f.exists() == true && f.isDirectory() == false) {
			System.out.println("not first run");
			return false;
		}
		System.out.println("first run");
		return true;
	}
	//same method from ObtainDataFromTextFile, will probably delete the other file unless there needs to be more data collection
	public static List<String> readLines(String aFileName) throws IOException {
		return Files.readAllLines(Paths.get(aFileName), StandardCharsets.UTF_8);
	}
}
