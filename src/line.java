
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class line extends Drawing {
	int place;
	boolean wid = true, hei = true;
	Color st;

	public String toString() {
		// TODO Auto-generated method stub
		return "line";
	}

	public void draw(WorkSpace ws, double x1, double y1) {
		shape = new Line(x1, y1, x1, y1);
		this.ws = ws;
		bound = shape;
		shape.setPickOnBounds(true);
		ws.Shapes.add(this);
		shape.setStrokeWidth(ws.width);
		if (ws.random.isSelected()) {
			shape.setFill(ws.getRandomColor());
			shape.setStroke(ws.getRandomColor());
		} else {
			shape.setFill(ws.fil);
			shape.setStroke(ws.stroke);
		}
		ws.pane.getChildren().add(shape);
		shape.setOpacity(ws.opacity.getValue());
		select();
	}

	public void redraw(double x1, double y1) {
		if (isValidPlace(((Line) shape).getStartX(), ((Line) shape).getStartY(), ((Line) shape).getEndX() + x1,
				((Line) shape).getEndY() + y1)) {
			((Line) shape).setEndX(((Line) shape).getEndX() + x1);
			((Line) shape).setEndY(((Line) shape).getEndY() + y1);
		}
	}

	public void select() {
		// TODO Auto-generated method stub
		if (!selected) {
			selected = true;
			ws.Selected.add(this);
			st = (Color) shape.getStroke();
			shape.setStroke(Color.BLUE);
			shape.getStrokeDashArray().addAll(3.0, 7.0, 3.0, 7.0);

		}

	}

	public void keyboard_Edit() {
		VBox info = new VBox();
		Scene scene = new Scene(info);
		Stage st = new Stage();
		st.setTitle("Edit");
		st.setScene(scene);
		st.setWidth(100);
		HBox h = new HBox();
		st.setHeight(300);
		st.show();
		st.centerOnScreen();

		Button ok = new Button("OK");
		h.getChildren().add(ok);
		h.setAlignment(Pos.CENTER);
		TextField width = new TextField();
		TextField height = new TextField();
		TextField minX = new TextField();
		TextField minY = new TextField();
		minX.setPromptText("start_x");
		minY.setPromptText("start_y");
		width.setPromptText("end_x");
		height.setPromptText("end_y");
		info.setSpacing(20);
		info.getChildren().addAll(minX, minY, width, height);
		ok.setOnAction(e -> {
			if (!check_parameter(minX.getText())) {
				minX.clear();
			} else if (!check_parameter(minY.getText())) {
				width.clear();
			} else if (!check_parameter(height.getText())) {
				height.clear();
			} else if (!check_parameter(width.getText())) {
				width.clear();
			} else if (!isValidPlace(Double.parseDouble(minX.getText()), Double.parseDouble(minY.getText()),
					Double.parseDouble(width.getText()), Double.parseDouble(height.getText()))) {
				System.out.println("can't edit");
				st.close();
			} else {

				st.close();
				deselect();
			}
		});
	}

	public void deselect() {
		// TODO Auto-generated method stub
		if (selected) {
			shape.setStroke(st);
			ws.Selected.remove(this);
			selected = false;
			shape.getStrokeDashArray().removeAll(3.0, 7.0, 3.0, 7.0);
		}
	}

	public void move(double x1, double y1) {
		if (isValidPlace(((Line) shape).getStartX() + x1, ((Line) shape).getStartY() + y1,
				((Line) shape).getEndX() + x1, ((Line) shape).getEndY() + y1)) {
			((Line) shape).setStartX(((Line) shape).getStartX() + x1);
			((Line) shape).setStartY(((Line) shape).getStartY() + y1);
			((Line) shape).setEndX(((Line) shape).getEndX() + x1);
			((Line) shape).setEndY(((Line) shape).getEndY() + y1);
		}

	}

	public boolean isValidPlace(double x, double y, double x1, double y1) {
		if (x <= 0 || x > ws.bound_x)
			return false;
		if (x1 <= 0 || x1 > ws.bound_x)
			return false;
		if (y <= 0 || y > ws.bound_y)
			return false;
		if (y1 <= 0 || y1 > ws.bound_x)
			return false;
		return true;
	}

	@Override
	public String save() {
		String x = "line,";
		x += shape.getFill() + ",";
		x += shape.getStroke() + ",";
		x += shape.getStrokeWidth() + ",";
		x += ((Line) bound).getStartX() + ",";
		x += ((Line) bound).getStartY() + ",";
		x += ((Line) bound).getEndX() + ",";
		x += ((Line) bound).getEndY() + ",";
		x+=shape.getOpacity()+",";
		return x;
	}

	@Override
	public void load(String x, WorkSpace ws) {
		// TODO Auto-generated method stub
		String[] pro = x.split(",");
		shape = new Line();
		((Line) shape).setStartX(Double.parseDouble(pro[4]));
		((Line) shape).setStartY(Double.parseDouble(pro[5]));
		((Line) shape).setEndX(Double.parseDouble(pro[6]));
		((Line) shape).setEndY(Double.parseDouble(pro[7]));
		this.ws = ws;
		bound = shape;
		shape.setPickOnBounds(true);
		ws.Shapes.add(this);
		shape.setStrokeWidth(Double.parseDouble(pro[3]));
		shape.setFill(Color.web(pro[2]));
		shape.setStroke(Color.web(pro[1]));
		ws.pane.getChildren().add(shape);
		shape.setOpacity(Double.parseDouble(pro[8]));


	}

}