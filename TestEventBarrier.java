public class TestEventBarrier {
	public void testInstantiate () {
		EventBarrier eb = new EventBarrier();
	}

	public void testArrive () {
		EventBarrier eb = new EventBarrier();
		eb.arrive();
	}
}