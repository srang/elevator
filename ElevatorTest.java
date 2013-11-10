public class ElevatorTest {
	public static void test() {
		SingleBuildingVersion2 myBuilding = new SingleBuildingVersion2(20, 1);
		Elevator elevator = new Elevator(myBuilding);
		Rider rider = new Rider(1, 10, 2, myBuilding);
		rider.start();
		elevator.start();
	}
}
