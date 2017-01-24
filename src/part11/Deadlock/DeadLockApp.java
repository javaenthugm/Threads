package part11.Deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {
	private int balance = 10000;

	public void deposit(int amount) {
		balance += amount;
	}

	public void withdraw(int amount) {
		balance -= amount;
	}

	public int getBalance() {
		return balance;
	}

	public static void transfer(Account act1, Account act2, int amount) {
		act1.withdraw(amount);
		act2.deposit(amount);
	}
}

class Runner {
	private Account act1 = new Account();
	private Account act2 = new Account();

	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

	private void acquireLocks(Lock firstLock, Lock secondLock) {
		while (true) {
			boolean gotFirstLock = false;
			boolean gotSecondLock = false;
			try {
				gotFirstLock = firstLock.tryLock();
				gotSecondLock = secondLock.tryLock();
			} finally {
				if (gotFirstLock && gotSecondLock) {
					return;
				}
				
				if(gotFirstLock){
					firstLock.unlock();
				}
				
				if(gotSecondLock){
					secondLock.unlock();
				}

			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void firstThread() {
		Random random = new Random();

		for (int i = 0; i < 10000; i++) {
			acquireLocks(lock1, lock2);

			try {
				Account.transfer(act1, act2, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}

	}

	public void secondThread() {
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			acquireLocks(lock1, lock2);

			try {
				Account.transfer(act2, act1, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}

	}

	public void finished() {
		System.out.println("Account 1 balance " + act1.getBalance());
		System.out.println("Account 2 balance " + act2.getBalance());
		System.out.println("Total balance "
				+ (act1.getBalance() + act2.getBalance()));
	}
}

public class DeadLockApp {

	public static void main(String args[]) {
		final Runner runner = new Runner();

		Thread t1 = new Thread(new Runnable() {
			public void run() {
				runner.firstThread();
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				runner.secondThread();
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
