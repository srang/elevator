
public class Rider extends Thread{
	private int requestedFloor;
	public Rider(int floor){
		this.requestedFloor = floor;
	}
	public int getRequestedFloor(){
		return this.requestedFloor;
	}
}
