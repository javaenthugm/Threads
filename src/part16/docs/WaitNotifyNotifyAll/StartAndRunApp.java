package part16.docs.WaitNotifyNotifyAll;

class MyThread extends Thread{
	public void run(){
		System.out.println(Thread.currentThread().getName()+" running...");
	}
}

public class StartAndRunApp {	
	public static void main(String args[]){
		MyThread t1 = new MyThread();
		t1.start();
		t1.run();
		
	}
}
