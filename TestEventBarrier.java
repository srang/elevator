public class TestEventBarrier {
	public static void test() {
		for(int i = 0; i < 15; i++) {
			EventBarrier evBar = new EventBarrier();
			System.out.println("Created a new EventBarrier");
			for(int s = 0; s < 10; s++){
				Thread t = new helperThread(evBar);
				t.start();
				System.out.println("Creating a new Thread " + t.getId());
			}
			System.out.println("Call raise from Thread " + Thread.currentThread().getId());
			evBar.raise();
			System.out.println("Created a new EventBarrier");
			for(int s = 0; s < 10; s++){
				Thread t = new helperThread(evBar);
				t.start();
				System.out.println("Creating a new Thread " + t.getId());
			}
			System.out.println("Call raise from Thread " + Thread.currentThread().getId());
			
		}
	}
}
class helperThread extends Thread {
	private EventBarrier myEB;
	public helperThread(EventBarrier eb) {
		myEB = eb;
	}
	public void run() {  
		myEB.arrive();
		myEB.complete();
	}
}
