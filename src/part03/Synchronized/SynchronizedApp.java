package part03.Synchronized;

public class SynchronizedApp {

	private int counter = 0;
	
	private synchronized void incrementCounter(){
		counter++;
	}

	public static void main(String args[]) {
		SynchronizedApp app = new SynchronizedApp();
		app.doWork();
	}

	public void doWork() {

		Thread t1 = new Thread(new Runnable() {
			public void run() {

				for (int i = 0; i < 10000; i++) {
					//counter++;
					incrementCounter();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				for(int i=0;i<10000;i++){
					//counter++;
					incrementCounter();
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
		
		System.out.println("Counter "+counter);

	}

}
