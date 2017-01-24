package part16.docs.WaitNotifyNotifyAll;

public class WaitNotifyNotifyAllApp {

	private volatile boolean go = false;

	public static void main(String args[]) throws Exception{

		final WaitNotifyNotifyAllApp test = new WaitNotifyNotifyAllApp();

		Thread waitTask = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					test.shouldGo();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		Thread nofityTask = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					test.go();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread tw1 = new Thread(waitTask,"tw1");
		Thread tw2 = new Thread(waitTask,"tw2");
		Thread tw3 = new Thread(waitTask,"tw3");
		
		tw1.start();
		tw2.start();
		tw3.start();
		
		Thread.sleep(2000);
		Thread nt1 = new Thread(nofityTask,"nt1"); //notify
		nt1.start();
		
		tw1.join();
		tw2.join();
		tw3.join();
		nt1.join();
		
		
	}

	private synchronized void shouldGo() throws InterruptedException {
		while (go != true) {
			System.out.println(Thread.currentThread().getName()
					+ " is going to wait on this object");
			wait();
			System.out.println(Thread.currentThread().getName() + " woken up");
		}
		go = false;
	}

	private synchronized void go() throws InterruptedException {
		while (go == false) {
			System.out.println(Thread.currentThread().getName()
					+ " is going to wake up all threads");
			go = true;
			notifyAll();
			//notify();
		}
	}

}
