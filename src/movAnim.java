import java.util.Random;

public class movAnim implements Runnable {
	WorkSpace ws;
	public boolean cont;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (cont) {
			
				
				for (Drawing x : ws.Selected) {
					System.out.println("ok");
				}
				Thread.sleep(200);
			}
		} catch (Exception e1) { // TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}