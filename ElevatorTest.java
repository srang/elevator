public class ElevatorTest {
	public static void test() {
		SingleElevatorBuilding myBuilding= new SingleElevatorBuilding(20,1,Integer.MAX_VALUE);
		Elevator elevator = new Elevator(myBuilding);
		
		/*Rider rider = new Rider(1, 10, 2, myBuilding);
		rider.start();
		Rider rider2 = new Rider(2,12,6,myBuilding);
		rider2.start();
		Rider rider3 = new Rider(3,15,12,myBuilding);
		rider3.start();*/
		Rider rider1 = new Rider(1, 20 , 3 , myBuilding);
		rider1.start();
		Rider rider2 = new Rider(2,15,5,myBuilding);
		rider2.start();
		Rider rider3 = new Rider(3,5,8,myBuilding);
		rider3.start();
		Rider rider4 = new Rider(4, 2, 20, myBuilding);
		rider4.start();
		elevator.start();
		//rider2.start();
	}
}
