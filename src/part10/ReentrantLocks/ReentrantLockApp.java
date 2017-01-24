package part10.ReentrantLocks;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Runner {
	private int count = 0;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	private void increment() {
		for (int i = 0; i < 10000; i++) {
			count++;
		}
	}

	public void firstThread() throws InterruptedException {
		// synchronized (this) {
		lock.lock();
		System.out.println("Waiting...");
		condition.await();
		System.out.println("Woken up...");
		try {
			increment();
		} finally {
			lock.unlock();
		}

		// }

	}

	public void secondThread()  throws InterruptedException{
		Thread.sleep(1000);		
		// synchronized (this) {		
		lock.lock();		
		System.out.println("Press the return key");
		new Scanner(System.in).nextLine();
		System.out.println("Return key is pressed");
		condition.signal();
		
		try {
			increment();
		} finally {
			lock.unlock();
		}
		// }

	}

	public void finished() {
		System.out.println("Count " + count);
	}
}

public class ReentrantLockApp {

	public static void main(String args[]) {
		final Runner runner = new Runner();

		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					runner.firstThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					runner.secondThread();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		runner.finished();
	}

}
