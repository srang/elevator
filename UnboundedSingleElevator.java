import java.util.ArrayList;
import java.util.HashMap;

public class UnboundedSingleElevator extends AbstractElevator{
	boolean doorOpen;
	boolean inTransit;
	int currentFloor;
	boolean goingUp;
	HashMap<Integer, ArrayList<Thread>> riderList;
	
	public UnboundedSingleElevator(int numFloors, int elevatorId,
			int maxOccupancyThreshold) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		riderList = new HashMap<Integer, ArrayList<Thread>>();
		for(int i=0; i < numFloors; i++){
			riderList.put(i,new ArrayList<Thread>());
		}
		currentFloor = 1;
		inTransit = false;
		doorOpen = false;
		goingUp = false;
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
		while(doorOpen){
			try {
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("Elevator moved while door was open");
				e.printStackTrace();
			}
		}
		for(int i=0; i< riderList.get(floor).size();i++){
			notify();
		}
	}

	@Override
	public synchronized boolean Enter() {
		Thread rider = Thread.currentThread();
		while(doorOpen && currentFloor == 1){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(this.doorOpen && currentFloor == 1){
			return true;
		}
		//Need to check if this.currentFloor is equal to the fromFloor under Building, but I'm not sure how to do that.
		return false;
	}

	@Override
	public synchronized void Exit() {
		
		
		// TODO Auto-generated method stub
	}

	@Override
	public void RequestFloor(int floor) {
		// TODO Auto-generated method stub
		riderList.get(floor).add(Thread.currentThread());
	}
}