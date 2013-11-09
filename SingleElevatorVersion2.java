import java.util.ArrayList;


public class SingleElevatorVersion2 extends AbstractElevator {
	static final int UP=0;
	static final int DOWN = 1;
	static int NOT_MOVING = 2;
	EventBarrier[] barrierList;
	int currentFloor;
	boolean[][] Outside_RequestList;
	boolean[] Inside_RequestList;
	int currentDirection;
	ArrayList<Rider> riderList;
	
	EventBarrier eb;
	
	public SingleElevatorVersion2(int numFloors, int elevatorId,
			int maxOccupancyThreshold, EventBarrier[] barrierList) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		this.Outside_RequestList = new boolean[numFloors][2];
		this.Inside_RequestList = new boolean[numFloors];
		this.barrierList = barrierList;
	}

	@Override
	public void OpenDoors() {
		eb = barrierList[currentFloor-1];
		Inside_RequestList[currentFloor-1] = false;
		eb.raise();
		ClosedDoors();
	}

	@Override
	public void ClosedDoors() {
		System.out.println("Door is closed");
		ProcessNextRequest();
	}

	@Override
	public void VisitFloor(int floor) {
		eb = barrierList[floor-1];
		currentFloor = floor;
		OpenDoors();
	}

	@Override
	public boolean Enter() {
		eb = barrierList[currentFloor-1];
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		if(currentDirection == rider.getRequestedDirection()){
			riderList.add(rider);
			eb.complete();
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
					VisitFloor(i);
					break;
				}
			}
		}
		if(currentDirection == DOWN){
			for(int i=currentFloor; i >-1; i--){
				if(Inside_RequestList[i] || Outside_RequestList[i][DOWN]){
					VisitFloor(i);
					break;
				}
			}
		}
	}
}
