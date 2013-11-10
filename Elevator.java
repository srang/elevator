
public class Elevator extends Thread{
	AbstractElevator myElevator;
	
	public Elevator(SingleBuildingVersion2 building){
		myElevator = building.elevator;
	}
	
	@Override
	public void run() {
		while(true) {
			myElevator.ProcessNextRequest();
			System.out.println("Querry");
		}
	}
}
