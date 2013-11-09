/**
 * 
 * @author Sam Rang
 *
 */
public class Rider extends Thread{
	private final int UP = 0;
	private final int DOWN = 1;
	private int requestedFloor;
	private int originFloor;
	private AbstractBuilding myBuilding;
	private int requestedDirection;
	
	public Rider(int desiredFloor, int currentFloor, AbstractBuilding building){
		this.requestedFloor = desiredFloor;
		this.originFloor = currentFloor;
		this.myBuilding = building;
		requestedDirection = requestedFloor > originFloor ? UP : DOWN; 
	}
	
	public int getRequestedFloor(){
		return this.requestedFloor;
	}
	public int getRequestedDirection(){
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
		while(!(myElevator.Enter())) {
			if(requestedDirection == UP) { 
				myElevator = myBuilding.CallUp(originFloor);
			} else {
				myElevator = myBuilding.CallDown(originFloor);
			}
		} 
		myElevator.RequestFloor(requestedFloor);
		myElevator.Exit();
	}
}
