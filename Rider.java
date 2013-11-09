/**
 * 
 * @author Sam Rang
 *
 */
public class Rider extends Thread{
	private int requestedFloor;
	private int originFloor;
	private AbstractBuilding myBuilding;
	public Rider(int desiredFloor, int currentFloor, AbstractBuilding building){
		this.requestedFloor = desiredFloor;
		this.originFloor = currentFloor;
		this.myBuilding = building;
	}
	public int getRequestedFloor(){
		return this.requestedFloor;
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
			if(requestedFloor > originFloor) { 
				myElevator = myBuilding.CallUp(originFloor);
			} else {
				myElevator = myBuilding.CallDown(originFloor);
			}
		} 
		myElevator.RequestFloor(requestedFloor);
		myElevator.Exit();
	}
}
