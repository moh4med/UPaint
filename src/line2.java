
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

public class line2 extends Drawing {
	int place;
	boolean wid = true, hei = true;
	Color st;

	public String toString() {
		// TODO Auto-generated method stub
		return "line2";
	}

	public void draw(WorkSpace ws, double x1, double y1) {
		shape = new Line();
		bound = new Rectangle();
		this.ws = ws;
		((Line) shape).startXProperty().bind(((Rectangle) bound).xProperty());
		((Line) shape).startYProperty().bind(((Rectangle) bound).yProperty());
		((Line) shape).endXProperty().bind(((Rectangle) bound).widthProperty().add(((Rectangle) bound).xProperty()));
		((Line) shape).endYProperty().bind(((Rectangle) bound).heightProperty().add(((Rectangle) bound).yProperty()));
		((Rectangle) bound).setX(x1);
		((Rectangle) bound).setY(y1);
		bound.setFill(Color.TRANSPARENT);
		bound.setStroke(Color.BLUE);
		bound.getStrokeDashArray().addAll(3.0, 7.0, 3.0, 7.0);
		ws.Shapes.add(this);
		((Rectangle) bound).setWidth(.5);
		shape.setStrokeWidth(ws.width);
		if (ws.random.isSelected()) {
			shape.setFill(ws.getRandomColor());
			shape.setStroke(ws.getRandomColor());
		} else {
			shape.setFill(ws.fil);
			shape.setStroke(ws.stroke);
		}
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
			ws.pane.getChildren().add(bound);
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
		minX.setPromptText("min_x");
		minY.setPromptText("min_y");
		width.setPromptText("Width");
		height.setPromptText("Height");
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

				((Rectangle) bound).setWidth(Double.parseDouble(width.getText()));
				((Rectangle) bound).setHeight(Double.parseDouble(height.getText()));
				((Rectangle) bound).setX(Double.parseDouble(minX.getText()));
				((Rectangle) bound).setY(Double.parseDouble(minY.getText()));

				st.close();
				deselect();
			}
		});
	}

	public void deselect() {
		// TODO Auto-generated method stub
		if (selected) {
			selected=false;
			ws.pane.getChildren().remove(bound);
			ws.Selected.remove(this);
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
				((Rectangle) bound).getWidth() + disp_x, ((Rectangle) bound).getHeight() + disp_y)) {
			((Rectangle) bound).setWidth(((Rectangle) bound).getWidth() + disp_x);
			((Rectangle) bound).setHeight(((Rectangle) bound).getHeight() + disp_y);
		}
	}
	@Override
	public String save() {
		String x = "line2,";
		x += shape.getFill()+",";
		x+=shape.getStroke()+",";
		x+=shape.getStrokeWidth()+",";
		x+=((Rectangle)bound).getX()+",";
		x+=((Rectangle)bound).getY()+",";
		x+=((Rectangle)bound).getWidth()+",";
		x+=((Rectangle)bound).getHeight()+",";
		x+=shape.getOpacity()+",";
		return x;
	}
	@Override
	public void load(String x,WorkSpace ws) {
		// TODO Auto-generated method stub
		String[] pro=x.split(",");
		shape = new Line();
		bound = new Rectangle();
		this.ws = ws;
		((Line) shape).startXProperty().bind(((Rectangle) bound).xProperty());
		((Line) shape).startYProperty().bind(((Rectangle) bound).yProperty());
		((Line) shape).endXProperty().bind(((Rectangle) bound).widthProperty().add(((Rectangle) bound).xProperty()));
		((Line) shape).endYProperty().bind(((Rectangle) bound).heightProperty().add(((Rectangle) bound).yProperty()));
		((Rectangle) bound).setX(Double.parseDouble(pro[4]));
		((Rectangle) bound).setY(Double.parseDouble(pro[5]));
		((Rectangle) bound).setWidth(Double.parseDouble(pro[6]));
		((Rectangle) bound).setHeight(Double.parseDouble(pro[7]));
		bound.setFill(Color.TRANSPARENT);
		bound.setStroke(Color.BLUE);
		bound.getStrokeDashArray().addAll(3.0, 7.0, 3.0, 7.0);
		ws.Shapes.add(this);
		shape.setStrokeWidth(Double.parseDouble(pro[3]));	
			shape.setFill(Color.web(pro[1]));
			shape.setStroke(Color.web(pro[2]));
			shape.setOpacity(Double.parseDouble(pro[8]));
		
		ws.pane.getChildren().add(shape);
		
	}

}