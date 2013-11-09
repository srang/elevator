import java.util.ArrayList;


public class SingleBuildingVersion2 extends AbstractBuilding{
	EventBarrier[] barrierList;
	int UP = 0;
	int DOWN = 1;
	SingleElevatorVersion2 elevator;
	
	public SingleBuildingVersion2(int numFloors, int numElevators) {
		super(numFloors, numElevators);
		barrierList = new EventBarrier[numFloors];
		for(int i=0; i< numFloors; i++){
			barrierList[i] = new EventBarrier();
		}
		elevator = new SingleElevatorVersion2(numFloors,0, Integer.MAX_VALUE, barrierList);
	}

	@Override
	public AbstractElevator CallUp(int fromFloor) {
		EventBarrier eb = barrierList[fromFloor-1];
		elevator.ReqeuestService(fromFloor, UP);
		eb.arrive();
		return null;
	}

	@Override
	public AbstractElevator CallDown(int fromFloor) {
		EventBarrier eb = barrierList[fromFloor-1];
		elevator.ReqeuestService(fromFloor, DOWN);
		eb.arrive();
		return null;
	}

}
