package part14.InterruptingThreads;

import java.util.Random;

public class InterruptingThreadApp {

	public static void main(String args[]) throws InterruptedException {
		System.out.println("Started..");
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				Random r = new Random();
				for (int i = 0; i < 1E8; i++) {
					
					if (Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted at iteration " + i);
						break;
					}
					

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						System.out.println("Interrupted at iteration " + i);
						break;
					}

					Math.sin(r.nextDouble());
				}

			}
		});

		t1.start();
		t1.interrupt();
		t1.join();
		System.out.println("Finished");

	}

}
