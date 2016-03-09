import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class Drawing {
	public WorkSpace ws;
	public Shape shape;
	public boolean selected;
	public Shape bound;
	public int place;
	Drawable drawable;
	Colorable colorable;
	Deletable deletable;
	Movable movable;
	Resizable resizable;

	public Drawing() {

	}

	public void draw(WorkSpace ws, double x, double y) {

	}

	public void redraw(double disp_x, double disp_y) {

	}

	public static boolean check_parameter(String x) {
		try {
			double y = Double.parseDouble(x);

			if (y > 0) {
				return true;
			}

		} catch (NumberFormatException nfe) {
			return false;
		}
		return false;
	}

	public void delete(Pane pane) {
		deselect();
		pane.getChildren().remove(shape);
		ws.Shapes.remove(this);
		if (selected) {
			ws.Selected.remove(this);
		}
		pane.getChildren().remove(bound);

	}
	public void add(Pane pane) {
		select();
		pane.getChildren().add(shape);
		ws.Shapes.add(this);
	}

	public void select() {

	}

	public static int getPlace(double x, double y, double min_x, double min_y) {
		if (x > min_x) {
			if (y > min_y) {
				return 4;
			} else {
				return 2;
			}
		} else {
			if (y > min_y) {
				return 3;
			} else {
				return 1;
			}
		}
	}

	public void deselect() {

	}

	public void keyboard_Edit() {

	}

	public void move(double x, double y) {

	}

	public void resize(double disp_x, double disp_y) {

	}

	public boolean containPoint(double x, double y) {
		if (shape.contains(x - shape.getLayoutX(), y - shape.getLayoutY())) {
			return true;
		}
		return false;

	}

	public boolean isEqual(Path pa) {
		Path g = (Path) Shape.intersect(shape, shape);
		return ((pa.toString().equals(g.toString())));
	}

	public boolean isValidPlace(double x, double y, double width, double height) {
		if (x <= 0)
			return false;
		if (x + width >= ws.bound_x)
			return false;
		if (y < 0)
			return false;
		if (y + height > ws.bound_y)
			return false;
		return true;
	}

	public abstract String save();

	public abstract void load(String x, WorkSpace ws);
}
