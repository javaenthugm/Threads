package part19.CopyOnWriteArrayList;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListApp {
	public static void main(String args[]) throws InterruptedException{
		
		final CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>(Arrays.asList(1,2,3,4,5));
		
		new Thread(new Runnable() {
			@Override
			public void run() {
		
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				list.add(10);
				System.out.println(Thread.currentThread().getName()+" "+list);
			}
		},"First Thread-").start();;
		
		
		for(int i:list){
			System.out.println(i);
			Thread.sleep(250);
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+" "+list);
			}
		},"Second Thread-").start();
		
		for(int i:list){
			System.out.println(i);
			Thread.sleep(250);
		}
	}

}
