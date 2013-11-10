
public class Elevator extends Thread{
	AbstractElevator myElevator;
	
	public Elevator(SingleBuildingVersion2 building){
		myElevator = building.elevator;
	}
	
	@Override
	public void run() {
		while(1==1){
			myElevator.ProcessNextRequest();
		}
	}
}
