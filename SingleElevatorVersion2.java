import java.util.ArrayList;


public class SingleElevatorVersion2 extends AbstractElevator {
	static final int UP=0;
	static final int DOWN = 1;
	static int NOT_MOVING = 2;
	EventBarrier[] barrierList;
	int currentFloor = 1;
	boolean[][] Outside_RequestList;
	boolean[] Inside_RequestList;
	int currentDirection = UP;
	ArrayList<Rider> riderList;
	
	EventBarrier eb;
	
	public SingleElevatorVersion2(int numFloors, int elevatorId,
			int maxOccupancyThreshold, EventBarrier[] barrierList) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		this.Outside_RequestList = new boolean[numFloors][2];
		this.Inside_RequestList = new boolean[numFloors];
		this.barrierList = barrierList;
		riderList = new ArrayList<Rider>();
	}

	@Override
	/*Called by Elevator and opens the door. Waits for 
	all rider threads to come in by calling raise from EventBarrier*/
	public void OpenDoors() {
		eb = barrierList[currentFloor];
		Inside_RequestList[currentFloor] = false;
		System.out.println("Doors will now open");
		eb.raise();
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
		//eb = barrierList[floor];
		currentFloor = floor;
		OpenDoors();
	}

	@Override
	/*Called by rider threads, and makes them enter the elevator. Since the rider has already arrived at 
	 * the event barrier, it completes in event barrier when the rider enters the elevator*/
	public boolean Enter() {
		eb = barrierList[currentFloor];
		System.out.println("Rider is now entering");
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		if(currentDirection == rider.getRequestedDirection()){
			riderList.add(rider);
			eb.complete();
			System.out.println("Thread has entered");
			return true;
		}
		return false;
	}

	@Override
	public void Exit() {
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		riderList.remove(rider);
	}

	@Override
	public void RequestFloor(int floor) {
		Inside_RequestList[floor-1] = true;	
	}
	
	public void ReqeuestService(int floor, int direction){
		Outside_RequestList[floor-1][direction] = true;
	}

	@Override
	public void ProcessNextRequest() {
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
