import static org.junit.Assert.*;

import org.junit.Test;


public class TestEventBarrier {
	@Test
	public void testInstantiate () {
		EventBarrier eb = new EventBarrier();
		assertNotNull("Barrier must not be null", eb);
	}

	@Test
	public void testArrive () {
		EventBarrier eb = new EventBarrier();
		eb.arrive();
	}
}

