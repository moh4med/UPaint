import java.util.ArrayList;

import javafx.scene.paint.Color;

public abstract class Colorable implements Command {
	public ArrayList<Drawing> shapes = new ArrayList<>();
	public ArrayList<Double> opaciti = new ArrayList<>();
	public ArrayList<Color> colors = new ArrayList<>();
	WorkSpace ws;

	abstract public void execute();

	abstract public void undo();
}