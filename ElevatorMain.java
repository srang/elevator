import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class ElevatorMain {
	
	public static PrintWriter writer = null;
	
	public static void main(String[] args) {
		
		try {
			writer = new PrintWriter("Project.log", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ElevatorFactory.open();
		
		if(args.length==0) {
			ElevatorFactory.open();
			//ElevatorTest.test();
			//System.out.println("Not enough arguments");
			writer.println("Not enough Arguments");
		} 
		else if(args.length > 1) {
			System.out.println("Too many arguments");
			writer.println("Too many arguments");
		} 
		else if(args[0].equals("p1"))
		{
			System.out.println("Event Barrier:");
			writer.println("Event Barrier:");
			TestEventBarrier.test();
		}
		else if(args[0].equals("p2part1")) {
			System.out.println("Single Unbounded Elevator:");
			writer.println("Single Unbounded Elevator:");
		}
		else if(args[0].equals("p2part2")) {
			System.out.println("Single Bounded Elevator:");
			writer.println("Single Bounded Elevator:");
			// call the elevator part2
		}
		else if(args[0].equals("p2part3")){
			System.out.println("Multiple Bounded Elevators:");
			writer.println("Multiple Bounded Elevators:");
			// call the elevator part3
		}
		else if(args[0].equals("Inputfile")){
			ElevatorFactory.open();
		}
		writer.close();
	}
}
