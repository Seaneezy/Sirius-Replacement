//@author Sean Sponsler
package sunquestConverter;

import java.io.IOException;
import java.util.List;
public class ParseToTextFile {
	public static void main(String[] args) throws IOException {
		//NEED AUTO DETECTION FOR PATH LOCATIONS
		String pathLocation = GUI.SQ_LOCATION;
		//Could have included this method in this class, however I'm keeping it organized in case I need more for data collection
		List<String> lines = ObtainDataFromFiles.getLines();
	}

}
