package part11.Deadlock;

public class SimpleDeadLock {

	private static final Object left = new Object();
	private static final Object right = new Object();

	
	public static void leftRight(){
		synchronized (left) {
			System.out.println("leftRight::Got left and waiting for right");
			synchronized (right) {
				System.out.println("Acquired left --> right lock");
			}
		}
	}
	
	public static void rightLeft(){
		synchronized (right) {
			System.out.println("rightLeft::Got right and waiting for left");
			synchronized (left) {
				System.out.println("Acquired right--> left lock");
			}
		}
	}
	
	
	public static void main(String args[]){
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				leftRight();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				rightLeft();
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
