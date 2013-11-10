import java.util.ArrayList;

public class SingleElevatorBuilding extends AbstractBuilding{
	EventBarrier[] inBarrierList; //EventBarrier list for people trying to get in the elevator from the outside
	EventBarrier[] outBarrierList;
	static final int UP = 0;
	static final int DOWN = 1;
	SingleElevator elevator;
	
	public SingleElevatorBuilding(int numFloors, int numElevators, int capacity) {
		super(numFloors, numElevators);
		inBarrierList = new EventBarrier[numFloors];
		outBarrierList = new EventBarrier[numFloors];
		for(int i=0; i< numFloors; i++){
			inBarrierList[i] = new EventBarrier();
			outBarrierList[i] = new EventBarrier();
		}
		elevator = new SingleElevator(numFloors,0, capacity, inBarrierList, outBarrierList);
	}

	@Override
	public AbstractElevator CallUp(int fromFloor) {
		EventBarrier eb = inBarrierList[fromFloor-1];
		elevator.ReqeuestService(fromFloor, UP);
		ElevatorMain.writer.println("Rider has called for the elevator from floor " + fromFloor);
		eb.arrive();
		return elevator;
	}

	@Override
	public AbstractElevator CallDown(int fromFloor) {
		EventBarrier eb = inBarrierList[fromFloor-1];
		elevator.ReqeuestService(fromFloor, DOWN);
		ElevatorMain.writer.println("Rider has called for the elevator from floor " + fromFloor);
		eb.arrive();
		return elevator;
	}
}
