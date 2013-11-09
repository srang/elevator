
public class SingleElevatorVersion2 extends AbstractElevator {
	int UP=0;
	int DOWN = 1;
	int NOT_MOVING = 2;
	EventBarrier[] barrierList;
	int currentFloor;
	boolean[][] Outside_RequestList;
	boolean[] Inside_RequestList;
	int currentDirection;
	EventBarrier eb;
	
	public SingleElevatorVersion2(int numFloors, int elevatorId,
			int maxOccupancyThreshold, EventBarrier[] barrierList) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		this.Outside_RequestList = new boolean[numFloors][2];
		this.Inside_RequestList = new boolean[numFloors];
		this.barrierList = barrierList;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void OpenDoors() {
		eb = barrierList[currentFloor-1];
		eb.raise();
	}

	@Override
	public void ClosedDoors() {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void VisitFloor(int floor) {
		eb = barrierList[floor-1];
		currentFloor = floor;
		//if(currentFloor == )
	}

	@Override
	public boolean Enter() {
		eb = barrierList[currentFloor-1];
		eb.complete();
		return false;
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RequestFloor(int floor) {
		// TODO Auto-generated method stub
		Inside_RequestList[floor-1] = true;
		
	}
	
	public void ReqeuestService(int floor, int direction){
		Outside_RequestList[floor-1][direction] = true;
	}
}
