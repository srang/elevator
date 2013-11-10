public class ElevatorTest {
	public static void test() {
		SingleBuildingVersion2 myBuilding = new SingleBuildingVersion2(20,1,Integer.MAX_VALUE);
		Elevator elevator = new Elevator(myBuilding);
		
		Rider rider = new Rider(1, 10, 2, myBuilding);
		rider.start();
		Rider rider2 = new Rider(2,12,6,myBuilding);
		rider2.start();
		Rider rider3 = new Rider(3,15,12,myBuilding);
		rider3.start();
		elevator.start();
		//rider2.start();
	}
}
