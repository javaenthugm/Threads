package part08.WaitNotify;

import java.util.Scanner;

class ProducerConsumer{
	
	public void produce() throws InterruptedException{
		synchronized(this){
			System.out.println("Producer is running....");
			wait();
			System.out.println("Resumed...");
		}
	}
	
	public void consume() throws InterruptedException{
		Scanner scanner = new Scanner(System.in);
		Thread.sleep(1000);
		synchronized(this){
			System.out.println("Waiting for the return key....");
			scanner.nextLine();
			System.out.println("Return key is pressed");
			notify();
			Thread.sleep(3000);
			
		}
	}
}

public class WaitNotifyApp {

	static final ProducerConsumer pc = new ProducerConsumer();
	
	public static void main(String args[]) throws InterruptedException{
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					pc.produce();
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
		
		t1.join();
		t2.join();
	}
}
