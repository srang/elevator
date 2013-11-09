import java.util.ArrayList;
import java.util.HashMap;

public class UnboundedSingleElevator extends AbstractElevator{
	boolean doorOpen;
	boolean inTransit;
	int currentFloor;
	HashMap<Integer, ArrayList<Thread>> riderList;
	direction elevator_Direction;
	AbstractBuilding myBuilding;
	
	
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
		elevator_Direction = direction.NOT_MOVING;
	}
	
	public void setBuilding(AbstractBuilding building){
		myBuilding = building;
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
		//elevator_Direction = direction.MOVING_UP;
		myBuilding.riderQueue.get(currentFloor).notify();
	}

	@Override
	public synchronized void ClosedDoors() {
		doorOpen = false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void VisitFloor(int floor) {
		currentFloor = floor;
		OpenDoors();
		while(riderList.get(currentFloor).size()>0){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ClosedDoors();		
	}

	@Override
	public synchronized boolean Enter() {
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		if(doorOpen && currentFloor == rider.getRequestedFloor() && 
				elevator_Direction == rider.getRequestedDirection()){
			return true;
		} return false;
	}

	@Override
	public synchronized void Exit() {
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		riderList.get(rider.getRequestedFloor()).remove(riderThread);
		// TODO Auto-generated method stub
	}

	@Override
	public void RequestFloor(int floor) {
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		riderList.get(floor).add(Thread.currentThread());
		while(!doorOpen && floor != rider.getRequestedFloor()){
			try {
				riderThread.wait();
			} catch (InterruptedException e) {
				System.out.println("Rider tried to exit while elevator was in transit/door wasn't open");
				e.printStackTrace();
			}
		}
	}
}