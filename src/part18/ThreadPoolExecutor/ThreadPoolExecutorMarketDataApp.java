package part18.ThreadPoolExecutor;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MarketData{
	final String stockName;
	final int price;
	public MarketData(String stkName,int price){
		this.stockName = stkName;
		this.price = price;
	}
}

public class ThreadPoolExecutorMarketDataApp {
	
	//static final BlockingQueue<Integer> lb  = new LinkedBlockingDeque<>();	
	static final BlockingQueue<Integer> lb = new ArrayBlockingQueue<>(10);
	
	
	static void produce(){
		try {
			Random random = new Random();
			while (true) {
				System.out.println(Thread.currentThread().getName()
						+ " is putting items to the queue");
				lb.put(random.nextInt(10) + 10);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void consume(){
		
		try {
			Random random = new Random();

			while (true) {
				System.out.println(Thread.currentThread().getName() +" checking .."
						+ lb.peek() + ", and the size is " + lb.size());
				Thread.sleep(1000);
				if (random.nextInt(10) == 0) {
					Integer value = lb.take();
					System.out.println("Taken value from the Queue is " + value
							+ ", and the size is " + lb.size());
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String args[]){
		
		ExecutorService prodPool = Executors.newFixedThreadPool(4);
		
		for(int i=0;i<4;i++){
			prodPool.submit(new Runnable() {
				@Override
				public void run() {
					produce();
					
				}
			});
		}
		
		ExecutorService consPool = Executors.newCachedThreadPool();
		
		for(int i=0;i<4;i++){
			consPool.submit(new Runnable() {
				@Override
				public void run() {
					consume();
				}
			});
		}
		
	}

}
