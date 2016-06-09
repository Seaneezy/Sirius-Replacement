//@author Sean Sponsler
package sunquestConverter;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
public class ParseToTextFile {
	public static void main(String[] args) throws IOException {
		//need text formatting
		//NEED AUTO DETECTION FOR PATH LOCATIONS
		String pathLocation = main.SQ_LOCATION;
		//Could have included this method in this class, however I'm keeping it organized in case I need more for data collection
		ObtainDataFromTextFile.getLines(pathLocation);
	}

}
