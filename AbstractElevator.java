
public abstract class AbstractElevator {

	protected int numFloors; 
	protected int elevatorId;
	protected int maxOccupancyThreshold;
	public int currentDirection;
	public EventBarrier[] inBarrierList;
	
	public enum direction{
		MOVING_DOWN, MOVING_UP, NOT_MOVING
	}
	/**
	 * Other variables/data structures as needed goes here 
	 */

	public AbstractElevator(int numFloors, int elevatorId, int maxOccupancyThreshold) {
		this.numFloors = numFloors;
		this.elevatorId = elevatorId;
		this.maxOccupancyThreshold = maxOccupancyThreshold;
	}

	/**
	 * Elevator control interface: invoked by Elevator thread.
 	 */

	/* Signal incoming and outgoing riders */
	public abstract void OpenDoors(); 	

	/**
	 * When capacity is reached or the outgoing riders are exited and
	 * incoming riders are in. 
 	 */
	public abstract void ClosedDoors();

	/* Go to a requested floor */
	public abstract void VisitFloor(int floor);
	
	/*Processes the next request where "next" is not always necessarily time stamp based*/
	public abstract void ProcessNextRequest();

	/**
	 * Elevator rider interface (part 1): invoked by rider threads. 
  	 */

	/* Enter the elevator */
	public abstract boolean Enter();
	
	/* Exit the elevator */
	public abstract void Exit();

	/* Request a destination floor once you enter */
 	public abstract void RequestFloor(int floor);	
	
	/* Other methods as needed goes here */
}
