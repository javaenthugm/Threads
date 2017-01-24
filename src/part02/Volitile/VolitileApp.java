package part02.Volitile;

import java.util.Scanner;

class Runner extends Thread {

	private volatile boolean isRunning = true;

	public void run() {

		while (isRunning) {
			System.out.println("Stop me");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void shutDown() {
		isRunning = false;
	}
}

public class VolitileApp {

	public static void main(String args[]) {
		Runner t1 = new Runner();
		
		t1.start();
		System.out.println("Press enter key to stop");
		Scanner scanner  = new Scanner(System.in);
		String enterKey = scanner.nextLine();
		System.out.println(enterKey);
		
		t1.shutDown();
	}

}
