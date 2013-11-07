import java.util.HashMap;

public class UnboundedSingleElevator extends AbstractElevator{
	boolean doorOpen;
	boolean inTransit;
	int currentFloor;
	HashMap<Integer, Thread> RiderList;
	
	public UnboundedSingleElevator(int numFloors, int elevatorId,
			int maxOccupancyThreshold) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized void OpenDoors() {
		// TODO Auto-generated method stub
		while(inTransit){
			try {
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("Elevator cannot open while in transit");
				e.printStackTrace();
			}
		}
		doorOpen = true;
		notifyAll();
	}

	@Override
	public void ClosedDoors() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void VisitFloor(int floor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized boolean Enter() {
		if(this.doorOpen){
			return true;
		}
		//Need to check if this.currentFloor is equal to the fromFloor under Building, but I'm not sure how to do that.
		return false;
	}

	@Override
	public void Exit() {
		if(this.doorOpen){
			//Need to have list of people in elevator, and then take them off the list.
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void RequestFloor(int floor) {
		// TODO Auto-generated method stub
		
	}
	
}