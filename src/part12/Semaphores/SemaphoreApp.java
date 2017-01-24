package part12.Semaphores;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

class Connection {
	private static final Connection instance = new Connection();
	private int connections;
	private Semaphore sem = new Semaphore(10);

	private Connection() {
	}

	public static Connection getInstance() {
		return instance;
	}

	public void connect() {
		try {
			sem.acquire();
			doCconnect();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sem.release();
		}
	}

	private void doCconnect() throws InterruptedException {
		synchronized (this) {
			connections++;
		}
		System.out.println("Available Connections " + Thread.currentThread().getName()+"->"+connections);

		Thread.sleep(2000);

		synchronized (this) {
			connections--;
			System.out.println("Reducing connections" + Thread.currentThread().getName()+"->"+connections);
		}

	}
}

public class SemaphoreApp {

	public static void main(String args[]) {
		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < 20; i++) {
			executor.submit(new Runnable() {
				public void run() {
					
					Connection.getInstance().connect();
				}
			},"Thread-"+i);
		}
		
		executor.shutdown();

	}

}
