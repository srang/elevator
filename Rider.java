
public class Rider extends Thread{
	private int requestedFloor;
	private int originFloor;
	private AbstractBuilding myBuilding;
	private AbstractElevator.direction requestedDirection;
	
	public Rider(int desiredFloor, int currentFloor, AbstractBuilding building){
		this.requestedFloor = desiredFloor;
		this.originFloor = currentFloor;
		this.myBuilding = building;
		requestedDirection = requestedFloor > originFloor ? AbstractElevator.direction.MOVING_UP 
				: AbstractElevator.direction.MOVING_DOWN; 
	}
	
	public int getRequestedFloor(){
		return this.requestedFloor;
	}
	public AbstractElevator.direction getRequestedDirection(){
		return this.requestedDirection;
	}
	public int getOriginFloor(){
		return this.originFloor;
	}
	@Override
	public void run() {
		AbstractElevator myElevator;
		if(requestedFloor > originFloor) { 
			myElevator = myBuilding.CallUp(originFloor);
		} else {
			myElevator = myBuilding.CallDown(originFloor);
		}
		myElevator.setBuilding(myBuilding);
		while(!(myElevator.Enter())) {
			if(requestedDirection == AbstractElevator.direction.MOVING_UP) { 
				myElevator = myBuilding.CallUp(originFloor);
			} else {
				myElevator = myBuilding.CallDown(originFloor);
			}
		} 
		myElevator.RequestFloor(requestedFloor);
		myElevator.Exit();
	}
}
