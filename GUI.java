/**
 * @author Sean Sponsler

 * WARNINGS WILL EXIST UNTIL FULL IMPLEMENTATION
 * Impromptu to-do list:
 * First run check and initialization: DONE
 * Property file check and initialization: DONE
 * Input to change properties on first run: DONE(UNTIL GUI IMPLEMENTATION)
 * Obtain data from lines of text input: DONE
 * Creating new files in designated output folder: INCOMPLETE(need to know format)
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
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import javax.swing.*;
import javax.swing.JFrame;
public class GUI  extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final String CONF_LOCATION = "config.properties";
	public static String SQ_LOCATION = "";
	public static String OUT_LOCATION = "";
	public static final int LEFT_FRAME = 0;
	public static final int RIGHT_FRAME = 2;
	public static final int CENTER_FRAME = 1;
	public final Color GREY = new Color(155,155,155);
	private JLabel helloWorld = new JLabel("Hello World");
	private JButton convertButton = new JButton("Convert");
	private JFileChooser SQchooser = new JFileChooser();
	private JFileChooser OutputChooser = new JFileChooser();
	private JLabel welcomeText = new JLabel("This is your first run. Please specify a directory for the SUNQuest files: ");
	private JLabel welcomeText2 = new JLabel("Please specify a location for the output: ");	
	public GUI() throws IOException{
		File f = new File(CONF_LOCATION);
	    JPanel mainPanel = new JPanel(new FlowLayout());
	    mainPanel.setLayout(new FlowLayout());
	    mainPanel.setBackground(GREY);
	    convertButton.setBackground(Color.GREEN);
	    //convertButton.setSize(100,100);
	    convertButton.setPreferredSize(new Dimension(100,100));
	    //convertButton.setForeground(Color.BLACK);
		mainPanel.add(helloWorld);
		mainPanel.add(convertButton);
		this.setContentPane(mainPanel);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit program if close-window button clicked
	    setTitle("Sirius Replacement"); // "super" JFrame sets title
	    //setBackground(GREY);
	    setSize(600, 400);         // "super" JFrame sets initial size
	    setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
	    setVisible(true);  
		if (isFirstRun(f) == true) {
			firstRunInitialization();
			mainPanel.remove(helloWorld);
			mainPanel.remove(convertButton);
			mainPanel.add(welcomeText);
			SQchooser.setCurrentDirectory(new java.io.File("."));
			OutputChooser.setCurrentDirectory(new java.io.File("."));
			SQchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			OutputChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			SQchooser.setAcceptAllFileFilterUsed(false);
			OutputChooser.setAcceptAllFileFilterUsed(false);
			SQchooser.setDialogTitle("Choose directory for SUNQuest files");
			OutputChooser.setDialogTitle("Choose location for output");
			mainPanel.add(SQchooser);
			mainPanel.add(welcomeText2);
			mainPanel.add(OutputChooser);
		    if (SQchooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		        System.out.println("getCurrentDirectory(): " + SQchooser.getSelectedFile());
		        SQ_LOCATION = SQchooser.getSelectedFile().toString();
		      } else {
		    	  //needs to return to choose folderS
		        JOptionPane.showMessageDialog(null, "Invalid!");
		      }
		    mainPanel.remove(welcomeText);
		    if (OutputChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		    	System.out.println("getCurrentDirectory();: "+OutputChooser.getSelectedFile());
		    	OUT_LOCATION = OutputChooser.getSelectedFile().toString();
		    }
		    else {
		    	//needs to return to choose folder
		    	JOptionPane.showMessageDialog(null, "Invalid!");
		    }
		    mainPanel.remove(SQchooser);
		    mainPanel.remove(OutputChooser);
		    mainPanel.remove(welcomeText2);
		    mainPanel.add(helloWorld);
		    mainPanel.add(convertButton);
		    mainPanel.revalidate();	
		}
	}
	public static void firstRunInitialization() throws IOException{
		//MAKESHIFT INPUT UNTIL GUI IS IMPLEMENTED
		boolean SQFileExists = false;
		boolean OutputFileExists = false;
		String SQFilePath = "C:/";
		String OutputLocation = "C:/";
		while (SQFileExists == false) {
			File SQFile = new File(SQFilePath);
			if (SQFile.exists() == true) {
				SQ_LOCATION = SQFilePath;
				SQFileExists = true;
			}
			else {
				//System.out.println("Invalid location for SUNQuest files!");
			}
		}
		while (OutputFileExists == false) {
			File OutputFile = new File(OutputLocation);
			if (OutputFile.exists() == true) {
				OUT_LOCATION = OutputLocation;
				OutputFileExists = true;
			}
			else {
				//System.out.println("Invalid location for output file!");
			}
		updatePrefs();
			
		}
		/* this doesnt make sense to implement, just add to current folder
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
		*/
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
	
	//same method from ObtainDataFromTextFile, will probably delete the other file unless there needs to be more data collection
	public static List<String> readLines(String aFileName) throws IOException {
		return Files.readAllLines(Paths.get(aFileName), StandardCharsets.UTF_8);
	}
	public static boolean isFirstRun(File f) throws IOException {
		//names are not final
		//NEED TO FIND USERNAMES FOR MORE SPECIFIC TARGETING
		if (f.exists() == true && f.isDirectory() == false) {
			//test System.out.println("not first run");
			return false;
		}
		//test System.out.println("first run");
		return true;
	}
	public static void updatePrefs() throws IOException {
		Properties properties = new Properties();
		OutputStream output = null;
		output = new FileOutputStream("config.properties");
		//setting property values
		//names are not final
		properties.setProperty("SunquestFilePath", SQ_LOCATION);
		properties.setProperty("OutputFolderLocation", OUT_LOCATION);
		properties.store(output, null);
		//closing the output to the file
		if (output != null) {
			output.close();
		}
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
            try {
				new GUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  // Let the constructor do the job
	          }
	       });
	}

}
