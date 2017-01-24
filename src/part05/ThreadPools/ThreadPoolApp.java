package part05.ThreadPools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable {

	private final int id;

	public Processor(int id) {
		this.id = id;
	}

	public void run() {
		System.out.println("Thread " + Thread.currentThread().getName() + ",Id "
				+ id + " started");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread " + Thread.currentThread().getName() + ",Id "
				+ id + " finished");
	}
}


class CallableProcessor implements Callable<Integer>{
	private final int id;
	
	public CallableProcessor(int id){
		this.id = id;
	}
	
	public Integer call(){
		System.out.println("Thread "+Thread.currentThread().getName()+",Id"+id+" started");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return id;
	}
}

public class ThreadPoolApp {

	public static void main(String args[]) {
		/*
		 * The java.util.concurrent.ExecutorService interface represents 
		 * an asynchronous execution mechanism which is capable of 
		 * executing tasks in the background. 
		 * An ExecutorService is thus very similar to a thread pool. 
		 * In fact, the implementation of ExecutorService present 
		 * in the java.util.concurrent package is a thread pool implementation.
		 * 
		 */
		ExecutorService executor = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 5; i++) {
			executor.execute(new Processor(i));
			//executor.submit(new Processor(i)); 
			/*
			 * what is the difference between execute and submit?
			 * execute.(Runnable) will not return anything - return type is void
			 * executerService.submit(Runnable/Callable) will return Future object
			 * 
			 */
			Future<Integer> future = executor.submit(new CallableProcessor(i));
			
			try {
				System.out.println((int)future.get()+" is completed?");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		executor.shutdown();

		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("All taks have been completed");

	}
}
