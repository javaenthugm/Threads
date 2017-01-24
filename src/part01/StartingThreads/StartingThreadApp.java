package part01.StartingThreads;

class RunnerExtender extends Thread{
	public void run(){
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("RunnerExtender "+Thread.currentThread().getName()+" "+i);
		}
	}
}

class RunnerImplemnter implements Runnable{
	public void run(){
		for(int i=0;i<10;i++){
			System.out.println("RunnerImplemnter "+Thread.currentThread().getName()+" "+i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}


public class StartingThreadApp {
	
	public static void main(String args[]){
		RunnerExtender t1 = new RunnerExtender();
		t1.start();
		RunnerExtender t2 = new RunnerExtender();
		t2.start();
		
		Thread t3 = new Thread(new RunnerImplemnter());
		t3.start();
		Thread t4 = new Thread(new RunnerImplemnter());
		t4.start();
		
		Thread t5 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<10;i++){
					System.out.println("Anonymous Thread "+ Thread.currentThread().getName()+" "+i);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		t5.start();
		
		System.out.println("Main Thread "+Thread.currentThread().getName());
		
	}

}
