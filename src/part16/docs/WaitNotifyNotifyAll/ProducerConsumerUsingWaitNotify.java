package part16.docs.WaitNotifyNotifyAll;

import java.util.LinkedList;
import java.util.Queue;

class Producer implements Runnable {
	final Queue<Integer> sharedQ;

	public Producer(Queue<Integer> q) {

		this.sharedQ = q;
		System.out.println("Prodcer sharedQ hashValue:"+System.identityHashCode(sharedQ));
	}

	public void run() {
		for (int i = 0; i < 4; i++) {
			synchronized (sharedQ) {

				while (sharedQ.size() >= 1) {
					System.out.println(Thread.currentThread().getName()
							+ " waiting");
					try {
						sharedQ.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("Producing "+i);
				sharedQ.add(i);
				sharedQ.notify();
			}
		}
	}
}

class Consumer implements Runnable{

	final Queue<Integer> sharedQ;
	public Consumer(Queue<Integer> q){
		this.sharedQ = q;
		System.out.println("Consumer sharedQ hashValue:"+System.identityHashCode(sharedQ));
	}
	 
	@Override
	public void run() {
		while(true){
			synchronized (sharedQ) {
				while(sharedQ.size()==0){
					System.out.println(Thread.currentThread().getName()+" Waiting");
					try {
						sharedQ.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				int number = sharedQ.poll();
				System.out.println("Consuming :" +number);
				sharedQ.notify();
				if(number==3)break;
			}
		}
		
	}
	
}

public class ProducerConsumerUsingWaitNotify {
	
	public static void main(String args[]){
		final Queue<Integer> sharedQ = new LinkedList<>();
		System.out.println("New Object:"+System.identityHashCode(sharedQ));
		Thread prod = new Thread(new Producer(sharedQ),"Producer");
		Thread cons = new Thread(new Consumer(sharedQ),"Consumer");
		prod.start();
		
		cons.start();
	}

}
