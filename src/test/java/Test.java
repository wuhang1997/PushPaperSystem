import java.util.Timer;

public class Test {
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		});
		t.start();
		Timer timer = new Timer();
		//timer.schedule(task, delay);
	}
}
