import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class FreeShape extends Drawing {
	double x, y;
	private String[] pr;
	private double xfd;
	private double dsf;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Free";
	}

	FreeShape(Shape shape, WorkSpace ws) {
		this.shape = shape;
		bound = shape;
		this.ws = ws;
		ws.pane.getChildren().add(shape);

	}

	FreeShape() {

	}

	public void draw(WorkSpace ws, double x1, double y1) {
		this.ws = ws;
		shape = new Path();
		bound = shape;
		x = x1;
		y = y1;
		// shape.setPickOnBounds(true);
		ws.pane.getChildren().add(shape);
		ws.Shapes.add(this);
		if (ws.random.isSelected()) {
			shape.setStroke(ws.getRandomColor());
		} else {
			shape.setStroke(ws.stroke);
		}
		((Path) shape).getElements().add(new MoveTo(x1, y1));
		shape.setOpacity(ws.opacity.getValue());	
	}
	public void redraw(double x, double y) {
		this.x += x;
		this.y += y;
		((Path) shape).getElements().add(new LineTo(this.x, this.y));
		if (ws.random.isSelected()) {
			((Path) shape).setStroke(ws.getRandomColor());
		} else {
			((Path) shape).setStroke(ws.stroke);
		}
		((Path) shape).setStrokeWidth(ws.width);

	}

	public void select() {
		// TODO Auto-generated method stub
		if (!selected) {
			selected = true;
			ws.Selected.add(this);
			// f.setLayoutX(-187);
			// f.setLayoutY(-65);

			shape.getStrokeDashArray().addAll(3.0, 5.0, 3.0, 5.0);
		}
	}

	public void deselect() {
		// TODO Auto-generated method stub
		if (selected) {
			ws.Selected.remove(this);
			selected = false;
			shape.getStrokeDashArray().clear();
		}
	}

	public void move(double x, double y) {
		shape.setLayoutX(shape.getLayoutX() + x);
		shape.setLayoutY(shape.getLayoutY() + y);
		// is valid
		Shape d = Shape.subtract(shape, ws.background);
		if (!((Path) d).getElements().isEmpty()) {
			shape.setLayoutX(shape.getLayoutX() - x);
			shape.setLayoutY(shape.getLayoutY() - y);
		}
	}

	@Override
	public String save() {
		String x = "FreeShape,";
		x += shape.getFill() + ",";
		x += shape.getStroke() + ",";
		x += shape.getStrokeWidth() + ",";
		x += shape.getOpacity() + ",";
		x += ((MoveTo) ((Path) shape).getElements().get(0)).getX() + "#";
		x += ((MoveTo) ((Path) shape).getElements().get(0)).getY();
		for (int i = 1; i < ((Path) shape).getElements().size(); i++) {
			x += "," + ((LineTo) ((Path) shape).getElements().get(i)).getX() + "#";
			x += ((LineTo) ((Path) shape).getElements().get(i)).getY();
		}
		return x;
	}

	@Override
	public void load(String x, WorkSpace ws) {
		// TODO Auto-generated method stub
		String[] pro = x.split(",");
		this.ws = ws;
		shape = new Path();
		bound = shape;
		shape.setStrokeWidth(Double.parseDouble(pro[3]));
		// shape.setPickOnBounds(true);
		ws.pane.getChildren().add(shape);
		ws.Shapes.add(this);
		try {
			shape.setFill(Color.web(pro[1]));
		} catch (Exception ex) {
			shape.setFill(null);
		}
		shape.setStroke(Color.web(pro[2]));
		shape.setOpacity(Double.parseDouble(pro[4]));
		 pr = pro[5].split("#");
		((Path) shape).getElements().add(new MoveTo(Double.parseDouble(pr[0]), Double.parseDouble(pr[1])));
		for (int i = 6; i < pro.length; i++) {
			pr = pro[i].split("#");
			((Path) shape).getElements().add(new LineTo(Double.parseDouble(pr[0]), Double.parseDouble(pr[1])));
		}
	}

}
