import java.util.ArrayList;
import java.util.PriorityQueue;


public class SingleElevatorVersion2 extends AbstractElevator {
	static final int UP=0;
	static final int DOWN = 1;
	static int NOT_MOVING = 2;
	EventBarrier[] inBarrierList;
	EventBarrier[] outBarrierList;
	int currentFloor = 1;
	boolean[][] Outside_RequestList;
	boolean[] Inside_RequestList;
	int currentDirection = UP;
	ArrayList<Rider> riderList;
	PriorityQueue<Rider> Outside_RequestQueue;
	
	//EventBarrier eb;
	
	public SingleElevatorVersion2(int numFloors, int elevatorId,
			int maxOccupancyThreshold, EventBarrier[] inBarrierList, EventBarrier[] outBarrierList) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		this.Outside_RequestList = new boolean[numFloors][2];
		this.Inside_RequestList = new boolean[numFloors];
		this.inBarrierList = inBarrierList;
		this.outBarrierList = outBarrierList;
		Outside_RequestQueue = new PriorityQueue<Rider>();
		riderList = new ArrayList<Rider>();
	}

	@Override
	/*Called by Elevator and opens the door. Waits for 
	all rider threads to come in by calling raise from EventBarrier*/
	public void OpenDoors() {
		System.out.println("Doors will now open");
		outBarrierList[currentFloor].raise();
		inBarrierList[currentFloor].raise();
		Inside_RequestList[currentFloor] = false;
		ClosedDoors();
	}

	@Override
	/*When the door closes, it is ready to move again to another 
	 floor, and hence calls ProcessNextRequest()*/
	public void ClosedDoors() {
		System.out.println("Door is closed");
		ProcessNextRequest();
	}

	@Override
	/*Visits indicated floor in parameter, and after visiting, door should open 
	(assuming VisitFloor is called only on floors that have actually been requested)*/
	public void VisitFloor(int floor) {
		System.out.println("Going to floor "+ (floor+1));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		currentFloor = floor;
		OpenDoors();
	}

	@Override
	/*Called by rider threads, and makes them enter the elevator. Since the rider has already arrived at 
	 * the event barrier, it completes in event barrier when the rider enters the elevator*/
	public boolean Enter() {
		EventBarrier inBarrier = inBarrierList[currentFloor];
		System.out.println("Rider is now entering");
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		if(currentDirection == rider.getRequestedDirection()){
			Outside_RequestQueue.remove(rider);
			riderList.add(rider);
			inBarrier.complete();
			System.out.println("Thread has entered");
			Outside_RequestList[currentFloor][currentDirection] = false;
			return true;
		}
		return false;
	}

	@Override
	public void Exit() {
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		if(currentFloor == rider.getRequestedFloor()-1){
			System.out.println("Thread got off");
			outBarrierList[currentFloor].complete();
			riderList.remove(rider);
		}
	}

	@Override
	public void RequestFloor(int floor) {
		Inside_RequestList[floor-1] = true;
		outBarrierList[floor-1].arrive();
	}
	
	public void ReqeuestService(int floor, int direction){
		Rider rider = (Rider) Thread.currentThread();
		Outside_RequestQueue.add(rider);
		Outside_RequestList[floor-1][direction] = true;
	}

	@Override
	public void ProcessNextRequest() {
		if(riderList.isEmpty() && !Outside_RequestQueue.isEmpty()){
			Rider rider = Outside_RequestQueue.poll();
			currentDirection = rider.getRequestedDirection();
			String direction = currentDirection == UP ? "(UP)" : "(DOWN)";
			System.out.println("Next request " + direction+ " is to F " + rider.getRequestedFloor());
			VisitFloor(rider.getRequestedFloor());
		}
		if(currentDirection == UP){
			for(int i=currentFloor; i < Inside_RequestList.length; i++){
				if(Inside_RequestList[i] || Outside_RequestList[i][UP]){
					System.out.println("Next request (UP) is to F" + (i+1));
					VisitFloor(i);
					break;
				}
			}
		}
		if(currentDirection == DOWN){
			for(int i=currentFloor; i >-1; i--){
				if(Inside_RequestList[i] || Outside_RequestList[i][DOWN]){
					System.out.println("Next request (DOWN) is to F" + (i+1));
					VisitFloor(i);
					break;
				}
			}
		}
	}
}
