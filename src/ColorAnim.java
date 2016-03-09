import java.util.Random;

import javafx.scene.paint.Color;

public class ColorAnim implements Runnable {
	WorkSpace ws;
	public boolean cont;

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			while (cont) {
				for (Drawing x : ws.Selected) {
					x.shape.setFill(ws.getRandomColor());
					x.shape.setStroke(ws.getRandomColor());
					x.move(new Random().nextDouble()*20-10, new Random().nextDouble()*20-10);
				}
				Thread.sleep(500);
			}
		} catch (Exception e1) { // TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
