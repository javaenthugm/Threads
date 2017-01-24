package part00.pluralsight;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClass {
	
	public static void main(String args[]){
		ContinuesPrinter cp = new ContinuesPrinter();
		
		ExecutorService e = Executors.newFixedThreadPool(1);
		e.submit(cp);
		
		
		for(int i=0;i<10;i++){
			System.out.println(Thread.currentThread().getName()+":"+i);
		}
		e.shutdown();
	}

}
