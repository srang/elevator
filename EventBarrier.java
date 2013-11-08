import java.util.LinkedList;


public class EventBarrier extends AbstractEventBarrier {

	private LinkedList<Thread> consumerList;


	/** 
	 * Instantiate EventBarrier
	 */
	public EventBarrier() {
		consumerList = new LinkedList<Thread>();
	}

	@Override
	public synchronized void arrive() {
		Thread ct = Thread.currentThread();		
		System.out.println("Thread: " + ct.getId() + " arrived at event barrier");
		consumerList.add(ct);
		try {
			while(consumerList != null){ 
				this.wait();
			}
		} catch (InterruptedException e) {
			System.out.println("Thread: " + ct.getId() + " was interrupted");
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void raise() {
		notifyAll();
		Thread ct = Thread.currentThread();
		try {
			while (waiters() > 0) { 
				System.out.println("Waking up all Threads");
				this.wait();
			}
		} catch (InterruptedException e) {
			System.out.println("Thread: " + ct.getId() + " was interrupted");
			e.printStackTrace();
		}

		System.out.println("All Threads complete");
	}

	@Override
	public synchronized void complete() {
		Thread ct = Thread.currentThread();
		consumerList.remove(ct);
		System.out.println("Thread " + ct.getId() + " has completed running. " 
				+ waiters() + " Threads remain");
		if(waiters() == 0) {
			notifyAll();
		}
	}

	@Override
	public synchronized int waiters() {
		return consumerList.size();
	}
}
