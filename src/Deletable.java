import java.util.ArrayList;

public class Deletable implements Command {
	public ArrayList<Drawing> shapes = new ArrayList<>();
public 	WorkSpace ws;

	public void execute() {
		// TODO Auto-generated method stub
		for (Drawing drawing : shapes) {
			drawing.delete(ws.pane);
		}
	}

	public void undo() {
		// TODO Auto-generated method stub
		for (Drawing drawing : shapes) {
			drawing.add(ws.pane);
		}
	}
}