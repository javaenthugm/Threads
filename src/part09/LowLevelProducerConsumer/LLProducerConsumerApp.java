package part09.LowLevelProducerConsumer;

import java.util.LinkedList;

class ProducerConsumer {

	private LinkedList<Integer> list = new LinkedList<Integer>();
	private final int LIMIT = 10;
	private Object lock = new Object();

	public void produce() throws InterruptedException {
		int value = 0;
		while (true) {
			synchronized (lock) {
				while (list.size() == LIMIT){
					lock.wait();
				}
				list.addLast(value++);
				lock.notify();
			}
			
		}
	}

	public void consume() throws InterruptedException{
		Thread.sleep(500);
		while (true) {
			synchronized (lock) {
				while(list.size()==0){
					lock.wait();
				}
				System.out.print("The size is " + list.size());
				int value = list.removeFirst();
				System.out.println(", value taken from the front is " + value);
				lock.notify();
			}
			Thread.sleep(500);
		}
	}
}

public class LLProducerConsumerApp {

	public static void main(String args[]) {
		final ProducerConsumer p = new ProducerConsumer();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					p.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					p.consume();
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
