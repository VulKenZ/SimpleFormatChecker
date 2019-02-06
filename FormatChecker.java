import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 
 * This java class takes in filenames from the commandline and validates the format on the given file
 * @author NicholasPrussen
 * @version 1.0 cs-221 Spring 2019
 *
 */
public class FormatChecker {
	
	/**
	 * This takes the input from the commandline and passes to the readFile() method and catches exceptions
	 * and prints to console
	 * @param args this is the list of filenames from the commandline
	 */
	public static void main(String[] args) {
		
		//Catch empty commandline
		if(args.length == 0) {
			System.out.println("Use this command to execute the class: java FormatChecker file1 [file2 ... fileN] e.g. valid1.dat");
		}
		//Execute file checking
		else {
			//loop through filenames
			for(int i = 0; i < args.length; i++) {
				String currentFilename = args[i];
				//determines final validity
				boolean valid = false;
				
				try {
					System.out.println(currentFilename);
					if(readFile(currentFilename) == "clear") {
						valid = true;
						System.out.println("VALID");
						System.out.println("");
					}
					else if(readFile(currentFilename) == "firstLineError"){
						System.out.println("java.lang.NumberFormatException: " + currentFilename + " expected {int} {int} on Line 1");
					}
				}
				catch(FileNotFoundException e){
					System.out.println("java.io.FileNotFoundExcepton: " + currentFilename + " (The System cannot find the file speciifed)");
				}
				catch(NumberFormatException e){
					System.out.println(e);
				}
				catch(InputMismatchException e){
					System.out.println(e);
				}
			
				if(!valid) {
					System.out.println("INVALID");
				}
					System.out.println("");
			
			}
		}
	}
	
	/**
	 * This method iterates through every filename and checks format validity
	 * @param filename currentfilename passed from main()
	 * @return String returned to determine exception or validity
	 * @throws FileNotFoundException
	 * @throws NumberFormatException
	 * @throws InputMismatchException
	 * @throws NoSuchElementException
	 */
	public static String readFile(String filename) throws FileNotFoundException, NumberFormatException, InputMismatchException, NoSuchElementException {	
			Scanner file = new Scanner(new File(filename));
			
			//initialize row & col
			int row;
			int col = 0;
			
			if (file.hasNextLine()) {
					
				//Pull in first line of file
				String firstLine = file.nextLine();
				String [] firstLineArray = firstLine.split("\\s+");
				
				//check for wrong input on the first line
				if(firstLineArray[0].contains(".") || firstLineArray[1].contains(".") || firstLineArray.length > 2) {
					return "firstLineError";
				}
				else {
					//assign row & col
					row = Integer.parseInt(firstLineArray[0]);
					col = Integer.parseInt(firstLineArray[1]);
				}
				
				//Placeholder for each next line
				String nextLine;
				String [] nextLineArray = new String [col];
				
				//Iterate through each line
				for(int i = 0; i < row; i++) {
					nextLine = file.nextLine();
					nextLineArray = nextLine.split("\\s+");
					
					//Catch more/less values on each lines
					if(nextLineArray.length < col || nextLineArray.length > col) {
						throw new NumberFormatException("Invalid grid format, expected " + col + " float values on line " + (i+1));
					}
					
					//Catch whether all characters can be parsed as doubles
					for(int j = 0; j < nextLineArray.length; j++) {
						double checkDouble = Double.parseDouble(nextLineArray[j]);
					}
					
				}
				
				//Catch extra lines of characters and bypass whitespace lines
				if(file.hasNextLine()) {
					if(file.hasNext()) {
						throw new InputMismatchException("Invalid grid format, " + row + " rows of float values expected");
					}
				}
			}
			//return valid if all checks pass
			return "clear";
	}
}
