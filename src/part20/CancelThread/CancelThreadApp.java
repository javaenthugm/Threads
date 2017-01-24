package part20.CancelThread;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class PrimeGenerator implements Runnable{// Callable{

	private final ArrayList<BigInteger> primes = new ArrayList<>();
	private volatile boolean cancelled;
	
	@Override
	public void run() {
		BigInteger p = BigInteger.ONE;
		//Works - but not efficient especially when we use any blocking queues
		while(!cancelled){
			
			synchronized (this) {
				p = p.nextProbablePrime();
				System.out.println(Thread.currentThread().getName()+" adding prime");
				primes.add(p);
			}
		}
	}
	
	/*@Override
	public Object call() throws Exception {
		BigInteger p = BigInteger.ONE;
		//Works - but not efficient especially when we use any blocking queues
		while(!cancelled){
			
			synchronized (this) {
				p = p.nextProbablePrime();
				System.out.println(Thread.currentThread().getName()+" adding prime");
				primes.add(p);
			}
		}
		return null;
	}*/
	
	public void cancel(){
		cancelled = true;
	}
	public synchronized List<BigInteger>  get(){
		return new ArrayList<>(primes);
	}
	
	
}

public class CancelThreadApp {
	
	public static void main(String args[]) throws InterruptedException{
		PrimeGenerator generator = new PrimeGenerator();
		
		//new Thread(generator).start();
		ExecutorService ex = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		for(int i=0;i<Runtime.getRuntime().availableProcessors();i++){
			ex.execute(generator);
			//ex.submit(generator);
		}
		
		
		try{
			Thread.sleep(200);
		}finally{
			generator.cancel();
		}
		
		for(BigInteger b:generator.get()){
			System.out.print(b+",");
		}
		
		ex.shutdown();
	}

}
