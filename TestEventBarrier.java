import java.util.ArrayList;

public class TestEventBarrier {
	public void testInstantiate () {
		EventBarrier eb = new EventBarrier();
	}

	public void testArrive () {
		EventBarrier eb = new EventBarrier();
		eb.arrive();
	}
	
	Thread gatekeeper = new Thread();
	
	
	
	ArrayList<Thread> people = new ArrayList<Thread>();
}