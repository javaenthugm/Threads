package part17.garbagecollection;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProfileApp {

	private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(
			10);

	public static void main(String args[]) throws InterruptedException {
		Thread producer = new Thread(new Runnable() {
			public void run() {
				try {
					produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"Producer");

		Thread consumer = new Thread(new Runnable() {
			public void run() {
				try {
					consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"Consumer");

		producer.start();
		consumer.start();

		/*producer.join();
		consumer.join();*/
	}

	public static void produce() throws InterruptedException {
		Random random = new Random();
		while (true) {
			System.out.println(Thread.currentThread().getName()
					+ " - is putting items to the queue");
			queue.put(random.nextInt(10) + 10);
		}
	}

	public static void consume() throws InterruptedException {
		Random random = new Random();

		while (true) {
			System.out.println(Thread.currentThread().getName() + "-The value may be taken from the queue is "
					+ queue.peek() + ", and the size is " + queue.size());
			Thread.sleep(1000);
			if (random.nextInt(10) == 0) {
				Integer value = queue.take();
				System.out.println("Taken value from the Queue is " + value
						+ ", and the size is " + queue.size());
			}
		}
	}

}
