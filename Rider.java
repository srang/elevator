public class Rider extends Thread{
	private final int UP = 0;
	private final int DOWN = 1;
	private int requestedFloor;
	private int originFloor;
	private int ID;
	private AbstractBuilding myBuilding;
	private int requestedDirection;
	
	/**
	 * A Rider represents a person riding on an elevator in a building. It stores
	 * an ID which is a unique key for that rider (used for logging). 
	 * @param ID
	 * @param desiredFloor
	 * @param currentFloor
	 * @param building
	 */
	public Rider(int ID, int desiredFloor, int currentFloor, AbstractBuilding building){
		this.ID = ID;
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
	
	public int getID(){
		return ID;
	}
	
	@Override
	public void run() {
		AbstractElevator myElevator;
		if(requestedFloor > originFloor) { 
			myElevator = myBuilding.CallUp(originFloor);
		} else {
			myElevator = myBuilding.CallDown(originFloor);
		}
		/*while(!(myElevator.Enter())) {
			if(requestedDirection == UP) { 
				myElevator = myBuilding.CallUp(originFloor);
			} else {
				myElevator = myBuilding.CallDown(originFloor);
			}
		} */
		System.out.println("f");
		myElevator.Enter();
		myElevator.RequestFloor(requestedFloor);
		myElevator.Exit();
	}
}
