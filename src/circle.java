
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class circle extends Drawing {
	int place;
	boolean wid = true, hei = true;
	Circle centre;

	public String toString() {
		// TODO Auto-generated method stub
		return "Circle";
	}

	public void draw(WorkSpace ws, double x1, double y1) {
		shape = new Circle();
		bound = new Rectangle();
		centre = new Circle();
		centre.setRadius(1);
		centre.setFill(Color.BLUE);
		this.ws = ws;
		((Rectangle) bound).setX(x1);
		((Rectangle) bound).setY(y1);
		ws.Shapes.add(this);
		centre.centerXProperty().bind(((Circle) shape).centerXProperty());
		centre.centerYProperty().bind(((Circle) shape).centerYProperty());
		((Circle) shape).radiusProperty().bind(((Rectangle) bound).widthProperty().divide(2));
		((Circle) shape).centerXProperty().bind(((Rectangle) bound).xProperty().add(((Circle) shape).radiusProperty()));
		((Circle) shape).centerYProperty().bind(((Rectangle) bound).yProperty().add(((Circle) shape).radiusProperty()));
		((Rectangle) bound).heightProperty().bind(((Rectangle) bound).widthProperty());
		((Rectangle) bound).setWidth(.5);
		if (ws.random.isSelected()) {
			shape.setFill(ws.getRandomColor());
			shape.setStroke(ws.getRandomColor());
		} else {
			shape.setFill(ws.fil);
			shape.setStroke(ws.stroke);
		}
		shape.setStrokeWidth(ws.width);
		shape.setOpacity(ws.opacity.getValue());
		ws.pane.getChildren().add(shape);
		select();
	}

	public void redraw(double disp_x, double disp_y) {
		resize(disp_x, disp_y);

	}

	public void select() {
		// TODO Auto-generated method stub
		if (!selected) {
			selected = true;
			ws.Selected.add(this);
			((Rectangle) bound).setWidth(((Circle) shape).getRadius() * 2);
			bound.setFill(Color.TRANSPARENT);
			bound.setStroke(Color.BLUE);
			bound.setStrokeWidth(1.5);
			bound.getStrokeDashArray().addAll(3.0, 7.0, 3.0, 7.0);
			ws.pane.getChildren().add(bound);
			ws.pane.getChildren().add(centre);
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
		st.setHeight(200);
		st.show();
		st.centerOnScreen();

		Button ok = new Button("OK");
		h.getChildren().add(ok);
		h.setAlignment(Pos.CENTER);
		TextField rad = new TextField();
		TextField cent_X = new TextField();
		TextField cent_Y = new TextField();
		rad.setPromptText("radius");
		cent_X.setPromptText("Center_X");
		cent_Y.setPromptText("Center_Y");
		info.setSpacing(20);
		info.getChildren().addAll(rad, cent_X, cent_Y, h);
		ok.setOnAction(e -> {
			if (!check_parameter(rad.getText())) {
				rad.clear();
			} else if (!check_parameter(cent_X.getText())) {
				cent_X.clear();
			} else if (!check_parameter(cent_Y.getText())) {
				cent_Y.clear();
			} else if (!isValidPlace(Double.parseDouble(cent_X.getText()), Double.parseDouble(cent_Y.getText()),
					Double.parseDouble(rad.getText()) * 2, Double.parseDouble(rad.getText()) * 2)) {
				System.out.println("can't edit");
				st.close();
			} else {

				((Rectangle) bound).setWidth(Double.parseDouble(rad.getText()) * 2);
				((Rectangle) bound).setX(((Rectangle) bound).getWidth() - Double.parseDouble(cent_X.getText()));
				((Rectangle) bound).setY(((Rectangle) bound).getWidth() - Double.parseDouble(cent_Y.getText()));

				st.close();
				deselect();
			}
		});
	}

	public void deselect() {
		// TODO Auto-generated method stub
		if (selected) {
			ws.Selected.remove(this);
			selected = false;
			ws.pane.getChildren().remove(bound);
			ws.pane.getChildren().remove(centre);
		}
	}

	public void move(double x1, double y1) {
		if (isValidPlace(((Rectangle) bound).getX() + x1, ((Rectangle) bound).getY() + y1,
				((Rectangle) bound).getWidth(), ((Rectangle) bound).getHeight())) {
			((Rectangle) bound).setX(((Rectangle) bound).getX() + x1);
			((Rectangle) bound).setY(((Rectangle) bound).getY() + y1);
		}

	}
	public void setCenter(double x1, double y1) {
		((Circle) shape).setCenterX(x1);
		((Circle) shape).setCenterY(y1);
	}

	public void resize(double disp_x, double disp_y) {
		// TODO Auto-generated method stub
		if (isValidPlace(((Rectangle) bound).getX(), ((Rectangle) bound).getY(),
				((Rectangle) bound).getWidth() + disp_x, ((Rectangle) bound).getWidth() + disp_x)) {
			((Rectangle) bound).setWidth(((Rectangle) bound).getWidth() + disp_x);
		}
	}

	@Override
	public String save() {
		String x = "circle";
		x += shape.getFill() + ",";
		x += shape.getStroke() + ",";
		x += shape.getStrokeWidth() + ",";
		x += ((Rectangle) bound).getX() + ",";
		x += ((Rectangle) bound).getY() + ",";
		x += ((Rectangle) bound).getWidth() + ",";
		x += shape.getOpacity() + ",";
		return x;
	}

	@Override
	public void load(String x, WorkSpace ws) {
		// TODO Auto-generated method stub
		String[] pro = x.split(",");
		shape = new Circle();
		bound = new Rectangle();
		centre = new Circle();
		centre.setRadius(1);
		centre.setFill(Color.BLUE);
		this.ws = ws;
		shape.setFill(Color.web(pro[1]));
		shape.setStroke(Color.web(pro[2]));
		shape.setStrokeWidth(Double.parseDouble(pro[3]));
		ws.Shapes.add(this);
		centre.centerXProperty().bind(((Circle) shape).centerXProperty());
		centre.centerYProperty().bind(((Circle) shape).centerYProperty());
		((Circle) shape).radiusProperty().bind(((Rectangle) bound).widthProperty().divide(2));
		((Circle) shape).centerXProperty().bind(((Rectangle) bound).xProperty().add(((Circle) shape).radiusProperty()));
		((Circle) shape).centerYProperty().bind(((Rectangle) bound).yProperty().add(((Circle) shape).radiusProperty()));
		((Rectangle) bound).heightProperty().bind(((Rectangle) bound).widthProperty());
		((Rectangle) bound).setX(Double.parseDouble(pro[4]));
		((Rectangle) bound).setY(Double.parseDouble(pro[5]));
		((Rectangle) bound).setWidth(Double.parseDouble(pro[6]));
		shape.setOpacity(Double.parseDouble(pro[7]));
		ws.pane.getChildren().add(shape);

	}

}