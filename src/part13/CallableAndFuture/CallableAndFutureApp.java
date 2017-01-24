package part13.CallableAndFuture;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFutureApp {

	public static void main(String args[]) {

		ExecutorService exec = Executors.newCachedThreadPool();
		Future<Integer> future =  exec.submit(new Callable<Integer>() {
		
			public Integer call() throws Exception{

				Random random = new Random();
				int duration = random.nextInt(4000);
				if(duration>2000){
					throw new IOException("Sleeping for long time "+duration);
				}
				System.out.println("Starting...");
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Finished...");
				
				return duration;
			}

		});
		
		exec.shutdown();
		
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			System.out.println(e.getMessage());
		}

	}

}
