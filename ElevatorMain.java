
public class ElevatorMain {

	public static void main(String[] args) {

		if(args.length==0) {
			//TestEventBarrier.test();
			System.out.println("Not enough arguments");
		} 
		else if(args.length > 2) {
			System.out.println("Too many arguments");
		} 
		else if(args[0].equals("p1"))
		{
			System.out.println("Event Barrier:");
			TestEventBarrier.test();
		}
		else if(args[0].equals("p2part1")) {
			System.out.println("Single Unbounded Elevator:");
		}
		else if(args[0].equals("p2part2")) {
			System.out.println("Single Bounded Elevator:");
			// call the elevator part2
		}
		else if(args[0].equals("p2part3")){
			System.out.println("Multiple Bounded Elevators:");
			// call the elevator part3
		}
	}
}
