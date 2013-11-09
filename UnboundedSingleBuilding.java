import java.util.ArrayList;
import java.util.LinkedList;

public class UnboundedSingleBuilding extends AbstractBuilding{
	public UnboundedSingleBuilding(int numFloors, int numElevators) {
		super(numFloors, numElevators);
		for(int i=1; i < numFloors; i++){
			riderQueue.set(i, new LinkedList<Rider>());
		}
	}

	@Override
	public AbstractElevator CallUp(int fromFloor) {
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		riderQueue.get(rider.getOriginFloor()).add(rider);
		notifyAll();
		return null;
	}

	@Override
	public AbstractElevator CallDown(int fromFloor) {
		// TODO Auto-generated method stub
		Thread riderThread = Thread.currentThread();
		Rider rider = (Rider) riderThread;
		riderQueue.get(rider.getOriginFloor()).add(rider);
		notifyAll();
		return null;
	}

}
