import java.util.LinkedList;


public class EventBarrier extends AbstractEventBarrier {
	
	private boolean isInProgress = false;
	private LinkedList<Thread> consumerList;
	private LinkedList<Thread> producerList;

	/** 
	 * Instantiate EventBarrier
	 */
	public EventBarrier() {
		consumerList = new LinkedList<Thread>();
		producerList = new LinkedList<Thread>();
	}

	@Override
	public synchronized void arrive() {
		Thread ct = Thread.currentThread();		
		System.out.println("Thread: " + ct.toString() + " arrived at event barrier and is waiting");
		consumerList.add(ct);
		while(!checkInProgress()){ 
			try {
				ct.wait();
			} catch (InterruptedException e) {
				System.out.println("Thread: " + ct.toString() + " was interrupted");
				e.printStackTrace();
			}
		}
		
	}

	public boolean checkInProgress() {
		//lock(this)
		return isInProgress;
	}

	@Override
	public synchronized void raise() {
		// isInProgress = true;
		// while (waiters()>0) { wait }
		// isInProgress = false;
	}

	@Override
	public void complete() {
		//lock(this)
		//Thread ct = Thread.currentThread();
		//ct.run();
		//consumerList.remove(ct);
	}

	@Override
	public int waiters() {
		//lock(this) {
		return consumerList.size();
		//}
	}
}
