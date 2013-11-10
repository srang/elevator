import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class ElevatorMain {
	
	public static PrintWriter writer = null;
	
	public static void main(String[] args) {
		
		try {
			writer = new PrintWriter("Elevator.log", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ElevatorFactory.open();
		
		if(args.length==0) {
			//ElevatorTest.test();
			//System.out.println("Not enough arguments");
			writer.println("Not enough Arguments");
		} 
		else if(args.length > 1) {
			System.out.println("Too many arguments");
			writer.println("Too many arguments");
		} 
		else if(args[0].equals("p1")) //Test cases for Event Barrier are "hard baked" into test class
		{
			System.out.println("Event Barrier:");
			writer.println("Event Barrier:");
			TestEventBarrier.test();
		}
		else if(args[0].equals("p2")){ //ElevatorFactory prompts for input file and figures out what type of elevator to
										// create from there.
			ElevatorFactory.open();
		}
		writer.close();
	}
}
