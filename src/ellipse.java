
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ellipse extends Drawing {
	int place;
	boolean wid = true, hei = true;
	Circle centre;

	public String toString() {
		// TODO Auto-generated method stub
		return "Ellipse";
	}

	public void draw(WorkSpace ws, double x1, double y1) {
		shape = new Ellipse();
		bound = new Rectangle();
		centre = new Circle();
		centre.setRadius(1);
		centre.setFill(Color.BLUE);
		this.ws = ws;
		((Rectangle) bound).setX(x1);
		((Rectangle) bound).setY(y1);
		ws.Shapes.add(this);
		centre.centerXProperty().bind(((Ellipse) shape).centerXProperty());
		centre.centerYProperty().bind(((Ellipse) shape).centerYProperty());
		((Ellipse) shape).radiusXProperty().bind(((Rectangle) bound).widthProperty().divide(2));
		((Ellipse) shape).radiusYProperty().bind(((Rectangle) bound).heightProperty().divide(2));

		((Ellipse) shape).centerXProperty()
				.bind(((Rectangle) bound).xProperty().add(((Ellipse) shape).radiusXProperty()));
		((Ellipse) shape).centerYProperty()
				.bind(((Rectangle) bound).yProperty().add(((Ellipse) shape).radiusYProperty()));
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
			((Rectangle) bound).setWidth(((Ellipse) shape).getRadiusX() * 2);
			((Rectangle) bound).setHeight(((Ellipse) shape).getRadiusY() * 2);
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
		TextField radx = new TextField();
		TextField rady = new TextField();
		TextField cent_X = new TextField();
		TextField cent_Y = new TextField();
		radx.setPromptText("radius_X");
		rady.setPromptText("radius_Y");
		cent_X.setPromptText("Center_X");
		cent_Y.setPromptText("Center_Y");
		info.setSpacing(20);
		info.getChildren().addAll(radx, rady, cent_X, cent_Y, h);
		ok.setOnAction(e -> {
			if (!check_parameter(radx.getText())) {
				radx.clear();
			}
			if (!check_parameter(rady.getText())) {
				rady.clear();
			} else if (!check_parameter(cent_X.getText())) {
				cent_X.clear();
			} else if (!check_parameter(cent_Y.getText())) {
				cent_Y.clear();
			} else if (!isValidPlace(Double.parseDouble(cent_X.getText()), Double.parseDouble(cent_Y.getText()),
					Double.parseDouble(radx.getText()) * 2, Double.parseDouble(rady.getText()) * 2)) {
				System.out.println("can't edit");
				st.close();
			} else {

				((Rectangle) bound).setWidth(Double.parseDouble(radx.getText()) * 2);
				((Rectangle) bound).setHeight(Double.parseDouble(rady.getText()) * 2);

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

	public void resize(double disp_x, double disp_y) {
		// TODO Auto-generated method stub

		if (isValidPlace(((Rectangle) bound).getX(), ((Rectangle) bound).getY(),
				((Rectangle) bound).getWidth() + disp_x, ((Rectangle) bound).getWidth() + disp_y)) {
			((Rectangle) bound).setWidth(((Rectangle) bound).getWidth() + disp_x);
			((Rectangle) bound).setHeight(((Rectangle) bound).getHeight() + disp_y);

		}
	}

	@Override
	public String save() {
		String x = "ellipse,";
		x += shape.getFill() + ",";
		x += shape.getStroke() + ",";
		x += shape.getStrokeWidth() + ",";
		x += ((Rectangle) bound).getX() + ",";
		x += ((Rectangle) bound).getY() + ",";
		x += ((Rectangle) bound).getWidth() + ",";
		x += ((Rectangle) bound).getHeight() + ",";
		x+=shape.getOpacity()+",";
		return x;
	}

	@Override
	public void load(String x, WorkSpace ws) {
		// TODO Auto-generated method stub
		String[] pro = x.split(",");
		shape = new Ellipse();
		bound = new Rectangle();
		centre = new Circle();
		centre.setRadius(1);
		centre.setFill(Color.BLUE);
		this.ws = ws;
		((Rectangle) bound).setX(Double.parseDouble(pro[4]));
		((Rectangle) bound).setY(Double.parseDouble(pro[5]));
		ws.Shapes.add(this);
		centre.centerXProperty().bind(((Ellipse) shape).centerXProperty());
		centre.centerYProperty().bind(((Ellipse) shape).centerYProperty());
		((Ellipse) shape).radiusXProperty().bind(((Rectangle) bound).widthProperty().divide(2));
		((Ellipse) shape).radiusYProperty().bind(((Rectangle) bound).heightProperty().divide(2));
		((Ellipse) shape).centerXProperty()
				.bind(((Rectangle) bound).xProperty().add(((Ellipse) shape).radiusXProperty()));
		((Ellipse) shape).centerYProperty()
				.bind(((Rectangle) bound).yProperty().add(((Ellipse) shape).radiusYProperty()));
		((Rectangle) bound).setWidth(Double.parseDouble(pro[6]));
		((Rectangle) bound).setHeight(Double.parseDouble(pro[7]));
		shape.setFill(Color.web(pro[1]));
		shape.setStroke(Color.web(pro[2]));
		shape.setOpacity(Double.parseDouble(pro[8]));
		shape.setStrokeWidth(Double.parseDouble(pro[3]));
		ws.pane.getChildren().add(shape);

	}

}