import java.util.ArrayList;

public class SingleElevator extends AbstractElevator {
	static final int UP=0;
	static final int DOWN = 1;
	static int NOT_MOVING = 2;
	EventBarrier[] inBarrierList;
	EventBarrier[] outBarrierList;
	int currentFloor = 0;
	boolean[][] Outside_RequestList;
	boolean[] Inside_RequestList;
	int currentDirection = UP;
	ArrayList<Rider> riderList;
	ArrayList<Rider> Ordered_Outside_RequestList;
	
	public SingleElevator(int numFloors, int elevatorId,
			int maxOccupancyThreshold, EventBarrier[] inBarrierList, EventBarrier[] outBarrierList) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		this.Outside_RequestList = new boolean[numFloors][2];
		this.Inside_RequestList = new boolean[numFloors];
		this.inBarrierList = inBarrierList;
		this.outBarrierList = outBarrierList;
		Ordered_Outside_RequestList = new ArrayList<Rider>();
		riderList = new ArrayList<Rider>();
	}

	@Override
	/*Called by Elevator and opens the door. Waits for 
	all rider threads to come in by calling raise from EventBarrier*/
	public void OpenDoors() {
		System.out.println("Doors will now open");
		int sizeBeforeExit = riderList.size();
		outBarrierList[currentFloor].raise();
		if(riderList.size() ==0 && sizeBeforeExit!=0){
			currentDirection = currentDirection == UP ? DOWN : UP;
		}
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
		if(currentFloor<floor){
			for(int i = currentFloor+1; i<= floor; i++){
			System.out.println("Currently at floor " + (i+1));
			}
		}
		if(currentFloor > floor){
			for(int i = currentFloor-1; i >= floor; i--){
				System.out.println("Currently at floor " + (i+1));
			}
		}
		currentFloor = floor;
		OpenDoors();
	}

	@Override
	/*Called by rider threads, and makes them enter the elevator. Since the rider has already arrived at 
	 * the event barrier, it completes in event barrier when the rider enters the elevator*/
	public boolean Enter() {
		EventBarrier inBarrier = inBarrierList[currentFloor];
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		int prevSize = riderList.size();
		if(currentDirection == rider.getRequestedDirection() && riderList.size() < maxOccupancyThreshold){
			System.out.println("Rider " + rider.getID() + " is now entering");
			Ordered_Outside_RequestList.remove(rider);
			riderList.add(rider);
			System.out.println("Rider " + rider.getID() + " has entered");
			inBarrier.complete();
			Outside_RequestList[currentFloor][currentDirection] = false;
			return true;
		}
		else if(prevSize>=maxOccupancyThreshold){
			System.out.println("Elevator was full. Rider " + rider.getID() + " could not enter.");
			//Ordered_Outside_RequestList.remove(rider);
			//Ordered_Outside_RequestList.add(rider);
			inBarrier.couldNotComplete();
		}
		return false;
	}

	@Override
	public void Exit() {
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		if(currentFloor == rider.getRequestedFloor()-1){
			System.out.println("Thread " + rider.getID() +" got off");
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
		Ordered_Outside_RequestList.add(rider);
		Outside_RequestList[floor-1][direction] = true;
	}

	@Override
	public void ProcessNextRequest() {
		if(riderList.isEmpty() && !Ordered_Outside_RequestList.isEmpty()){
			System.out.println("There are no riders");
			Rider rider = Ordered_Outside_RequestList.get(0);
			Ordered_Outside_RequestList.remove(0);
			currentDirection = rider.getRequestedDirection();
			String direction = currentDirection == UP ? "(UP)" : "(DOWN)";
			System.out.println("Next request " + direction+ " is to F " + rider.getOriginFloor());
			VisitFloor(rider.getOriginFloor()-1);
			return;
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
