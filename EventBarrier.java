import java.util.ArrayList;


public class EventBarrier extends AbstractEventBarrier {

	private boolean isProgressing;
	private int counter;

	/** 
	 * Instantiate EventBarrier
	 */
	public EventBarrier() {
		isProgressing = false;
		counter = 0;
	}

	@Override
	public synchronized void arrive() {

		//System.out.println("Thread: " + Thread.currentThread().getId() + " arrived at event barrier");
		counter++;
		while(!isProgressing){
			try {
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("Thread: " + Thread.currentThread().getId() + " was interrupted");
				e.printStackTrace();
			}
		}
	}

	@Override
	public synchronized void raise() {
		isProgressing = !(counter==0);
		while(isProgressing) {		
			notifyAll();
			try {
				//System.out.println("Waking up all Threads");
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("Thread: " + Thread.currentThread().getId() + " was interrupted");
				e.printStackTrace();
			}
		}
		//System.out.println("All Threads complete");
	}

	@Override
	public synchronized void complete() {
		counter--;
		//System.out.println("Thread " + Thread.currentThread().getId() + " has completed running. " 
			//	+ counter + " Threads remain");
		isProgressing = !(counter==0);
		notifyAll();
	}

	@Override
	public synchronized int waiters() {
		return counter;
	}
}
