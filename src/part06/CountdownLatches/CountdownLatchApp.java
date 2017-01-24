package part06.CountdownLatches;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable {

	private CountDownLatch latch;

	public Processor(CountDownLatch lt) {
		this.latch = lt;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName()+" started");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" finished");
		latch.countDown();
	}
}

public class CountdownLatchApp {

	public static void main(String args[]){
		CountDownLatch latch = new CountDownLatch(3);
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		for(int i=0;i<5;i++){
			executor.submit(new Processor(latch));
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Completed");
	}
	
}
