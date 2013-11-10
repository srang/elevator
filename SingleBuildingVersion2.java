import java.util.ArrayList;

public class SingleBuildingVersion2 extends AbstractBuilding{
	EventBarrier[] inBarrierList; //EventBarrier list for people trying to get in the elevator from the outside
	EventBarrier[] outBarrierList;
	static final int UP = 0;
	static final int DOWN = 1;
	SingleElevatorVersion2 elevator;
	
	public SingleBuildingVersion2(int numFloors, int numElevators) {
		super(numFloors, numElevators);
		inBarrierList = new EventBarrier[numFloors];
		outBarrierList = new EventBarrier[numFloors];
		for(int i=0; i< numFloors; i++){
			inBarrierList[i] = new EventBarrier();
			outBarrierList[i] = new EventBarrier();
		}
		elevator = new SingleElevatorVersion2(numFloors,0, Integer.MAX_VALUE, inBarrierList, outBarrierList);
	}

	@Override
	public AbstractElevator CallUp(int fromFloor) {
		EventBarrier eb = inBarrierList[fromFloor-1];
		elevator.ReqeuestService(fromFloor, UP);
		System.out.println("Rider has called for the elevator from floor " + fromFloor);
		eb.arrive();
		return elevator;
	}

	@Override
	public AbstractElevator CallDown(int fromFloor) {
		EventBarrier eb = inBarrierList[fromFloor-1];
		elevator.ReqeuestService(fromFloor, DOWN);
		eb.arrive();
		return elevator;
	}
}
