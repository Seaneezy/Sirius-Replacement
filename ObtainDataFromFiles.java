package sunquestConverter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class ObtainDataFromFiles {
	//public static int ?
	//public static int ?
	//simpler method for use in other classes
	//@param = file location of the text file to read
	public static List<String> getLines() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("C:/workspace/sunquestConverter/testFileLocation/DLSVC_63758_03752.txt"), StandardCharsets.UTF_8);
		return lines;
	}
	public static List<String> getLines(URI f) throws IOException {
		return Files.readAllLines(Paths.get(f));
	}
	/*
	public static boolean validateAllFiles() throws IOException {
		File f = new File(GUI.SQ_LOCATION);
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.startsWith("temp") && name.endsWith("txt");
		    }
		});
		for (int i = 0; i < matchingFiles.length; i++) {
			if 
		}
	}
	*/
	public static boolean validateAllLines() throws IOException {
		List<List<String>> lines = findValues();
		for (int i = 0; i < lines.size(); i++) {
			if (isValid(findPTAValues(findPTA((lines.get(i))))) == false) {
				System.out.println("false at line: "+(i+1));
				return false;
			}
		}
		return true;
	}
	public static List<List<String>> findValues() throws IOException {
		List<String> rawFile = getLines();
		List<List<String>> allValues = new ArrayList<List<String>>();
		for (int i = 0; i < rawFile.size(); i++) {
			List<String> values = new ArrayList<String>();
			String temp = rawFile.get(i);
			String[] parts = temp.split("\\|");
			//task
			values.add(parts[0]);
			//pta
			values.add(parts[1]);
			//date
			values.add(parts[3]);
			//price index
			values.add(parts[4]);
			//species code
			values.add(parts[5]);
			//patient id
			values.add(parts[6]);
			//patient name id
			values.add(parts[7]);
			System.out.println(values);
			allValues.add(values);
		}
		return allValues;
	}
	//FIND THE PTA STRING
	public static String findPTA(String text) {
		String[] parts = text.split("\\|");
		return parts[1];
	}
	public static String findPTA(List<String> lines) {
		return lines.get(1);
	}
	//FIND VALUES FOR P, T, AND A FROM PTA STRING
	public static List<String> findPTAValues(String PTA) throws IOException{
		List<String> list = new ArrayList<String>();
		//first 7 characters are P
		String P = PTA.substring(0,7);
		//splitting the rest into a remainder to find T and A
		String remainder = PTA.substring(7, PTA.length());
		//T has 1-3 digits, we can separate T from A by finding the first letter of the remainder
		if ((findFirstLetter(remainder) == -1)) {
			System.out.println("Invalid PTA!(Missing award number!)");
		}
		String T = remainder.substring(0,findFirstLetter(remainder));
		//A is the remainder of the remainder
		String A = remainder.substring(findFirstLetter(remainder), remainder.length());
		//System.out.println("P: "+P+", T: "+T+", A: "+A);
		list.add(P);
		list.add(T);
		list.add(A);
		return list;
	}
	//fin
	//@PARAM findPTAValues(x)
	public static boolean isValid(List<String> list) throws IOException {
		//https://ofweb.stanford.edu:8051/pls/OF1PRD/XXDL_GMS_WEB_SERVICE_PKG.validate_ptaeo?p_project_number=1162262&p_task_number=100&p_award_number=UZADN&P_EXPENDITURE_TYPE=E
		URL checkURL = new URL("https://ofweb.stanford.edu:8051/pls/OF1PRD"
				+ "/XXDL_GMS_WEB_SERVICE_PKG.validate_ptaeo?"
				//p
				+ "p_project_number="+list.get(0)
				//t
				+ "&p_task_number="+list.get(1)
				//a
				+ "&p_award_number="+list.get(2)
				+ "&P_EXPENDITURE_TYPE=E");
		URLConnection connection = checkURL.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String result = in.readLine().toString();
		//System.out.println(result);
		//need more error handling here
		if (result.contains("not found")) {
			return false;
		}
		else {
			return true;
		}
	}
	public static int findFirstLetter(String ex) {
		for (int i= 0; i < ex.length(); i++) {
			if (!Character.isDigit(ex.charAt(i))) {
				//found the letter
				return i;
			}
		}
		return -1;
	}
	public static void main(String[] args) throws IOException {
		//works
		List<String> lines = getLines();
		String line = lines.get(0);
		//System.out.println(isValid(findPTAValues(findPTA((line)))));
		double total = 0;
		for (int i = 0; i <=3; i++) {
			long startTime = System.currentTimeMillis();
			//System.out.println(validateAllLines());
			validateAllLines();
			long endTime = System.currentTimeMillis();
			total+= ((endTime - startTime)/1000);
		}
		double average = (total / 4);
		System.out.println("Seconds to complete on average: "+(average));
	}
}
