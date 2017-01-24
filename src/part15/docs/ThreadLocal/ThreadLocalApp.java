package part15.docs.ThreadLocal;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MyThreadLocal implements Callable<String>{

	private ThreadLocal<Integer> mtl = new ThreadLocal<>();
	private int local;
	
	@Override
	public String call() throws Exception {
		
		Random random = new Random();
		int val = random.nextInt(100)+100;
		local = val;
		System.out.println("ThreadLocal set  :" +val +" and just Local"+local+" , by the thread "+ Thread.currentThread().getName());
		
		mtl.set(val);
		
		return mtl.get()+","+local;
	}
	
}

public class ThreadLocalApp {
	
	public static void main(String args[]) throws Exception{
		ExecutorService exe = Executors.newFixedThreadPool(10);
		
		for(int i=0;i<10;i++){
			Future<String> f = exe.submit(new MyThreadLocal());
			System.out.println("Value get :"+ f.get()+" , by the thread "+ Thread.currentThread().getName());
		}
	
	exe.shutdown();
	}
	
	

}
