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
		System.out.println("Thread: " + ct.toString() + " arrived at event barrier and is waiting");
		consumerList.add(ct);
		while(consumerList != null){ 
			try {
				ct.wait();
			} catch (InterruptedException e) {
				System.out.println("Thread: " + ct.toString() + " was interrupted");
				e.printStackTrace();
			}
			complete();
		}
		
	}

	@Override
	public synchronized void raise() {
		notifyAll();
		while (consumerList.size()>0) { 
			try {
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("Thread: " + this.toString() + " was interrupted");
				e.printStackTrace();
			}
		}
	}

	@Override
	public synchronized void complete() {
		Thread ct = Thread.currentThread();
		consumerList.remove(ct);
	}

	@Override
	public synchronized int waiters() {
		return consumerList.size();
	}
}
