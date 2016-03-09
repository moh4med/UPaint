import java.util.ArrayList;

public class Drawable implements Command {
public	Drawing shape;
	public WorkSpace ws;
	public double disp_x, disp_y;

	public void execute() {
		// TODO Auto-generated method stub
		if (shape.shape == null) {
			shape.draw(ws, disp_x, disp_y);
		} else {
			shape.add(ws.pane);
		}
	}

	public void undo() {
		// TODO Auto-generated method stub
		shape.delete(ws.pane);
	}
}