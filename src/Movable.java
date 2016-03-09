import java.util.ArrayList;

public class Movable implements Command {
	ArrayList<Drawing> shapes = new ArrayList<>();
	public double disp_x, disp_y;
	public double adisp_x, adisp_y;
	boolean start = true;

	public void execute() {
		// TODO Auto-generated method stub
		if (start) {
			for (Drawing drawable : shapes) {
				drawable.move(disp_x, disp_y);
			}
			adisp_x += disp_x;
			adisp_y += disp_y;
		} else {
			for (Drawing drawable : shapes) {
				drawable.move(adisp_x, adisp_y);
			}
		}
	}

	public void undo() {
		// TODO Auto-generated method stub
		for (Drawing drawable : shapes) {
			drawable.move(-adisp_x, -adisp_y);
		}
		start = false;
	}

}
