public class TestEventBarrier {
	public static void test() {
		EventBarrier evBar = new EventBarrier();
		System.out.println("Created a new EventBarrier");
		for(int i = 0; i < 10; i++) {
			Thread t = new helperThread(evBar);
			t.start();
		}
		evBar.raise();
	}
} 
class helperThread extends Thread {
	private EventBarrier myEB;
	public helperThread(EventBarrier eb) {
		myEB = eb;
	}
	public void run() {
		System.out.println("Created a new Thread");    
		myEB.arrive();
	}
}
