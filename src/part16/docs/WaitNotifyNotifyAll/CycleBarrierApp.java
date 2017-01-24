package part16.docs.WaitNotifyNotifyAll;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Task implements Runnable {

	final CyclicBarrier cyBarrier;

	public Task(CyclicBarrier cb) {
		this.cyBarrier = cb;
	}

	public void run() {

		try {
			System.out.println(Thread.currentThread().getName() + " finished half work and now waiting");
			cyBarrier.await();
			Thread.sleep(10000);
			System.out.println(Thread.currentThread().getName()	+ " has crossed the barrier, now lets finishe the remaining work as well");
		} catch (InterruptedException e) {
		} catch (BrokenBarrierException e) {
		}
	}
}

public class CycleBarrierApp {

	public static void main(String args[]) {
		final CyclicBarrier sharedCb = new CyclicBarrier(3, new Runnable() {

			@Override
			public void run() {
				System.out.println("All parties have arrived the barrier");
			}
		});
		
		Thread t1 = new Thread(new Task(sharedCb),"TW1");
		Thread t2 = new Thread(new Task(sharedCb),"TW2");
		Thread t3 = new Thread(new Task(sharedCb),"TW3");
		
		t1.start();
		t2.start();
		t3.start();
	}

}
