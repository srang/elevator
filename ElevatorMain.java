
public class ElevatorMain {

	public static void main(String[] args) {

		if(args.length==0) {
			TestEventBarrier.test();
			//System.out.println("Not enough arguments");
		} 
		else if(args.length > 2) {
			System.out.println("Too many arguments");
		} else // known just one arg
			if(args[1].equals("p1")){
				TestEventBarrier.test();
			}
		if(args[1].equals("p2part1")) {
			// call the elevator part1
		} else if(args[1].equals("p2part2")) {
			// call the elevator part2
		} else if(args[1].equals("p2part3")){
			// call the elevator part3
		} 
	}
}
