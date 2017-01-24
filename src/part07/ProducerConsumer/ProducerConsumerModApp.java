package part07.ProducerConsumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ProdcuerConsumer {

	private BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

	public void prodcue() throws InterruptedException {
		Random random = new Random();
		while (true) {
			queue.put(random.nextInt(10) + 10);
		}
	}

	public void consume() throws InterruptedException {
		Random random = new Random();
		while (true) {
			Integer i = random.nextInt(10) ;
			/*System.out.println("If lucky, this " + queue.peek()
					+ " will be taken, and size " +queue.size()+", condition "+i);*/
			
			displayQueue(queue);
			Thread.sleep(1000);
			
			if (i == 0) {
				System.out.println("Condition is met");
				Integer value = queue.take();
				/*System.out.println("Value taken " + value + ", size "
						+ queue.size());*/
				displayQueue(queue);
			}
		}
	}
	
	private void displayQueue(BlockingQueue<Integer> queue){
		
		for(Integer i:queue){
			System.out.print("|"+i+"|");
		}
		System.out.println();
		
		
	}

}

public class ProducerConsumerModApp {

	static final ProdcuerConsumer pc = new ProdcuerConsumer();

	public static void main(String args[]) {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					pc.prodcue();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					pc.consume();
				} catch (InterruptedException e) {
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
	}
}
