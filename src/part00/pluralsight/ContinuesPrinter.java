package part00.pluralsight;

public class ContinuesPrinter extends Thread{
	
	
	public void run() {
		for(int i=0;i<10;i++){
			System.out.println(Thread.currentThread().getName()+":"+i);
		}
	}

}
