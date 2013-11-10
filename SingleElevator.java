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
	boolean falsehood = false;
	
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
		ElevatorMain.writer.println("Doors will now open");
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
		ElevatorMain.writer.println("Door is closed");
		ProcessNextRequest();
	}

	@Override
	/*Visits indicated floor in parameter, and after visiting, door should open 
	(assuming VisitFloor is called only on floors that have actually been requested)*/
	public void VisitFloor(int floor) {
		ElevatorMain.writer.println("Going to floor "+ (floor+1));
		if(currentFloor<floor){
			for(int i = currentFloor+1; i<= floor; i++){
			ElevatorMain.writer.println("Currently at floor " + (i+1));
			}
		}
		if(currentFloor > floor){
			for(int i = currentFloor-1; i >= floor; i--){
				ElevatorMain.writer.println("Currently at floor " + (i+1));
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
			ElevatorMain.writer.println("Rider " + rider.getID() + " is now entering");
			Ordered_Outside_RequestList.remove(rider);
			riderList.add(rider);
			ElevatorMain.writer.println("Rider " + rider.getID() + " has entered");
			inBarrier.complete();
			Outside_RequestList[currentFloor][currentDirection] = false;
			return true;
		}
		else if(currentDirection != rider.getRequestedDirection()){
			Ordered_Outside_RequestList.remove(rider);
			inBarrier.complete();
			Outside_RequestList[currentFloor][currentDirection] = false;
			return false;
		}
		else if(prevSize >= maxOccupancyThreshold){
			ElevatorMain.writer.println("Elevator was full. Rider " + rider.getID() + " could not enter.");
			Ordered_Outside_RequestList.remove(rider);
			inBarrier.complete();
			Outside_RequestList[currentFloor][currentDirection] = false;
			return false;
		}
		return false;
	}

	@Override
	public void Exit() {
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		if(currentFloor == rider.getRequestedFloor()-1){
			ElevatorMain.writer.println("Thread " + rider.getID() +" got off");
			outBarrierList[currentFloor].complete();
			riderList.remove(rider);
		}
	}

	@Override
	public void RequestFloor(int floor) {
		Rider rider = (Rider) Thread.currentThread();
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
			ElevatorMain.writer.println("There are no riders");
			Rider rider = Ordered_Outside_RequestList.get(0);
			Ordered_Outside_RequestList.remove(0);
			currentDirection = rider.getRequestedDirection();
			String direction = currentDirection == UP ? "(UP)" : "(DOWN)";
			ElevatorMain.writer.println("Next request " + direction+ " is to F " + rider.getOriginFloor());
			VisitFloor(rider.getOriginFloor()-1);
			return;
		}
		if(currentDirection == UP){
			for(int i=currentFloor+1; i < Inside_RequestList.length; i++){
				if(Inside_RequestList[i] || Outside_RequestList[i][UP]){
					ElevatorMain.writer.println("Next request (UP) is to F" + (i+1));
					VisitFloor(i);
					break;
				}
			}
		}
		if(currentDirection == DOWN){
			for(int i=currentFloor-1; i >-1; i--){
				if(Inside_RequestList[i] || Outside_RequestList[i][DOWN]){
					ElevatorMain.writer.println("Next request (DOWN) is to F" + (i+1));
					VisitFloor(i);
					break;
				}
			}
		}
	}
}
