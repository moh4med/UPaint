import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.colorchooser.ColorSelectionModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WorkSpace {

	public Drawing shape;
	@FXML
	public ColorPicker fillColors;
	@FXML
	public ColorPicker strokesolors;
	@FXML
	public CheckBox transparent;
	@FXML
	public CheckBox random;
	@FXML
	public CheckBox transparent1;
	@FXML
	public CheckBox random1;
	@FXML
	public Pane pane;
	@FXML
	public ChoiceBox<String> shapeList;
	@FXML
	public ChoiceBox<Double> font;
	@FXML
	public ToggleButton Eraser;
	@FXML
	public ToggleButton Anim;
	@FXML
	public Button fill;
	@FXML
	public Button strok;
	@FXML
	public ToggleButton intersection;
	@FXML
	public Button Undo;
	@FXML
	public Button back;
	@FXML
	public Button front;
	@FXML
	public Button Redo;
	@FXML
	public Button Import;
	@FXML
	public Button New;
	@FXML
	public Button Loadimg;
	@FXML
	public Button Load;
	@FXML
	public Button Save;
	@FXML
	public Button Savepro;
	@FXML
	public Button PrtScr;
	@FXML
	public Slider opacity;
	@FXML
	public TextField Width;
	@FXML
	public TextField Height;
	@FXML
	public Label x1;
	@FXML
	public BorderPane borderPane;
	@FXML
	public Label y1;
	public MenuItem del = new MenuItem("delete");
	public MenuItem mov = new MenuItem("move");
	public MenuItem res = new MenuItem("resize");
	public MenuItem edit = new MenuItem("edit");
	public double bound_x = 1024, bound_y = 786;
	public Thread colorChanging;
	public ColorAnim ca = new ColorAnim();
	public Rectangle background;
	public ArrayList<Drawing> Shapes = new ArrayList<>();
	public ArrayList<Drawing> Selected = new ArrayList<>();
	public ArrayList<Drawing> deleted = new ArrayList<>();
	public ArrayList<Shape> intersected = new ArrayList<>();
	public ArrayList<FreeShape> added = new ArrayList<>();
	public ArrayList<String> saveshapes = new ArrayList<>();
	public Path d;
	public int place;
	public boolean erasing;
	public double disp_x;
	public double disp_y;
	public double ix;
	public double iy;
	public Stage st;
	public Drawing shapel;
	public DynamicURLClassLoader dynalLoader;
	public String oper;
	public double width;
	public Color fil;
	public Color stroke;
	public boolean resizable, movable;
	public History history;
	public Deletable deleting;
	public Drawable drawing;
	public Colorable coloring;
	public Movable moving;
	public Resizable resizing;

	public static Color getRandomColor() {
		Random rand = new Random();
		Color rx = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), rand.nextDouble());
		return rx;
	}

	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		history = new History();
		history.ws = this;
		URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		dynalLoader = new DynamicURLClassLoader(urlClassLoader);
		shapeList.getItems().addAll("pointer", "FreeShape", "circle", "rectangle", "line", "line2", "ellipse",
				"RightTriangle", "square", "IsoscelesTriangle");
		shapeList.setValue("pointer");
		for (int j = 1; j <= 10; j++) {
			font.getItems().add(j * 1.0);
		}
		font.setOnAction(e -> {
			width = font.getValue();
		});

		New.setOnMousePressed(e -> {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(WorkSpace.class.getResource("Application.fxml"));
			try {
				borderPane = (BorderPane) loader.load();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			Scene scene = new Scene(borderPane);
			Stage stag = new Stage();
			stag.getIcons().add(new Image("baset.png"));
			stag.setScene(scene);
			stag.setTitle("U Paint!");
			stag.show();
			((WorkSpace) loader.getController()).st = stag;
			stag.setOnCloseRequest(t -> {
				if (((WorkSpace) loader.getController()).oper != null) {
					new Alert(AlertType.INFORMATION, ((WorkSpace) loader.getController()).oper, ButtonType.CLOSE)
							.show();
					Runtime rt = Runtime.getRuntime();
					try {
						Process proc = rt.exec(((WorkSpace) loader.getController()).oper);
					} catch (IOException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}
			});
		});
		borderPane.setOnKeyPressed(e -> {
			keyevent(e);
			borderPane.requestFocus();
		});
		/*
		 * borderPane.setStyle("-fx-background-color: rgba(150, 150, 230, 0.5);"
		 * + " -fx-background-radius: 10;");
		 */

		/*
		 * borderPane.setStyle(
		 * "-fx-background-color: rgba(150, 150, 230, 0.5);"
		 * 
		 * );
		 */
		borderPane.setStyle("-fx-background-image: url('/image3.jpg');" + "-fx-background-repeat: stretch;"
				+ "    -fx-background-size: 1900 1000;"
				+ "-fx-effect: dropshadow(three-pass-box, black, 30, 0.5, 0, 0); ");
		opacity.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {

				coloring = new Colorable() {

					@Override
					public void execute() {
						// TODO Auto-generated method stub
						for (Drawing drawable : shapes) {
							drawable.shape.setOpacity(new_val.doubleValue());
						}
					}

					@Override
					public void undo() {
						// TODO Auto-generated method stub
						for (int i = 0; i < opaciti.size(); i++) {
							shapes.get(i).shape.setOpacity(opaciti.get(i));
						}
					}

				};
				coloring.ws = WorkSpace.this;
				coloring.shapes.addAll(Selected);
				for (Drawing drawing : Selected) {
					coloring.opaciti.add(drawing.shape.getOpacity());
				}
				history.execute(coloring);
			}

		});

		Eraser.setOnAction(e -> {

			if (Eraser.isSelected()) {
				Eraser.setOpacity(.5);
				erasing = true;
				fillColors.setDisable(true);

				strokesolors.setDisable(true);
				random.setDisable(true);
				random1.setDisable(true);
				transparent.setDisable(true);
				transparent1.setDisable(true);
				stroke = (Color) background.getFill();
				fil = (Color) background.getFill();
			} else {
				Eraser.setOpacity(1);
				fillColors.setDisable(false);
				strokesolors.setDisable(false);
				random.setDisable(false);
				random1.setDisable(false);
				transparent.setDisable(false);
				transparent1.setDisable(false);
				fil = fillColors.getValue();
				stroke = strokesolors.getValue();
				erasing = false;
			}
		});
		back.setOnAction(e -> {
			for (Drawing drawable : Selected) {
				drawable.shape.toBack();
				background.toBack();
			}
		});

		front.setOnAction(e -> {
			for (Drawing drawable : Selected) {
				drawable.shape.toFront();
				if (!drawable.toString().equals("Free")) {
					drawable.bound.toFront();
				}

			}
		});
		fill.setOnAction(e -> {

			if (Selected.isEmpty()) {
				if (random.isSelected()) {
					background.setFill(getRandomColor());
				} else {
					background.setFill(fil);
				}

			} else {
				coloring = new Colorable() {

					@Override
					public void execute() {
						// TODO Auto-generated method stub
						for (Drawing drawable : shapes) {
							if (ws.random.isSelected()) {
								drawable.shape.setFill(ws.getRandomColor());
							} else {
								drawable.shape.setFill(ws.fil);
							}
						}
					}

					@Override
					public void undo() {
						// TODO Auto-generated method stub
						for (int i = 0; i < colors.size(); i++) {
							shapes.get(i).shape.setFill(colors.get(i));
						}
					}
				};
				coloring.ws = WorkSpace.this;
				coloring.shapes.addAll(Selected);
				for (Drawing drawing : Selected) {
					coloring.colors.add((Color) drawing.shape.getFill());
				}
				history.execute(coloring);

			}

		});
		strok.setOnAction(e -> {

			if (Selected.isEmpty()) {
				if (random.isSelected()) {
					background.setStroke(getRandomColor());
				} else {
					background.setStroke(stroke);
				}
			} else {

				coloring = new Colorable() {

					@Override
					public void execute() {
						// TODO Auto-generated method stub
						for (Drawing drawable : shapes) {
							if (ws.random.isSelected()) {
								drawable.shape.setStroke(ws.getRandomColor());
							} else {
								drawable.shape.setStroke(ws.fil);
							}
						}
					}

					@Override
					public void undo() {
						// TODO Auto-generated method stub
						for (int i = 0; i < colors.size(); i++) {
							shapes.get(i).shape.setStroke(colors.get(i));
						}
					}
				};
				coloring.ws = WorkSpace.this;
				coloring.shapes.addAll(Selected);
				for (Drawing drawing : Selected) {
					coloring.colors.add((Color) drawing.shape.getStroke());
				}
				history.execute(coloring);

			}

		});
		fillColors.setOnAction(e -> {
			fil = fillColors.getValue();

		});

		transparent.setOnAction(e -> {
			random.setSelected(false);
			if (transparent.isSelected()) {
				fillColors.setDisable(true);
				fil = Color.TRANSPARENT;
			} else {
				fillColors.setDisable(false);
				fil = fillColors.getValue();
			}
		});
		random.setOnAction(e -> {
			transparent.setSelected(false);
			if (random.isSelected()) {
				fillColors.setDisable(true);
				strokesolors.setDisable(true);
			} else {
				fillColors.setDisable(false);
				strokesolors.setDisable(false);
				fil = fillColors.getValue();
				stroke = strokesolors.getValue();
			}
		});

		intersection.setOnAction(e -> {
			if (intersection.isSelected()) {
				intersection.setOpacity(.5);
				for (int i = 0; i < Shapes.size(); i++) {
					// Shapes.get(0).shape.setBlendMode(BlendMode.DARKEN);
					for (int j = i + 1; j < Shapes.size(); j++) {
						if (Shapes.get(i).shape.intersects(Shapes.get(j).shape.getLayoutBounds())) {
							Shape x = Shape.intersect(Shapes.get(i).shape, Shapes.get(j).shape);
							if (transparent.isSelected()) {
								x.setFill(background.getFill());
								x.setStroke(background.getStroke());
							} else if (random.isSelected()) {
								x.setFill(getRandomColor());
								x.setStroke(getRandomColor());
							} else {
								x.setFill(fil);
								x.setStroke(stroke);
							}
							x.setLayoutX(-pane.getBoundsInParent().getMinX());
							x.setLayoutY(-pane.getBoundsInParent().getMinY());
							pane.getChildren().add(x);
							intersected.add(x);
						}
					}
				}
			} else {
				intersection.setOpacity(1);

				for (Shape x : intersected) {
					pane.getChildren().remove(x);
				}
				intersected.clear();
			}
		});

		strokesolors.setOnAction(e -> {
			stroke = strokesolors.getValue();

		});

		Width.setOnAction(e -> {
			if (Drawing.check_parameter(Width.getText())) {
				background.setWidth(Double.parseDouble(Width.getText()));
				bound_x = background.getWidth();
				Width.setText(String.valueOf(bound_x));
				for (Drawing drawable : Shapes) {
					if (!drawable.toString().equals("Free")) {
						drawable.shape.getStrokeDashArray().clear();
					}
					if (!drawable.isEqual((Path) Shape.intersect(drawable.shape, background))) {
						d = (Path) Shape.intersect(drawable.shape, background);
						d.setStroke(drawable.shape.getStroke());
						if (!drawable.toString().equals("Free")) {
							d.setFill(drawable.shape.getFill());
						}
						d.setLayoutX(-pane.getBoundsInParent().getMinX());
						d.setLayoutY(-pane.getBoundsInParent().getMinY());
						added.add(new FreeShape(d, this));
						deleted.add(drawable);
					}
				}
				for (FreeShape x : added) {
					Shapes.add(x);
				}
				for (Drawing x : deleted) {
					x.delete(pane);
				}
				deleted.clear();
				added.clear();
			} else {
				Width.clear();
				Width.setPromptText(String.valueOf(bound_x));
			}
			pane.requestFocus();
		});
		Height.setOnAction(e -> {
			if (Drawing.check_parameter(Height.getText())) {
				background.setHeight(Double.parseDouble(Height.getText()));
				bound_y = background.getHeight();
				Height.setText(String.valueOf(bound_y));
				for (Drawing drawable : Shapes) {
					if (!drawable.toString().equals("Free")) {
						drawable.shape.getStrokeDashArray().clear();
					}
					if (!drawable.isEqual((Path) Shape.intersect(drawable.shape, background))) {
						d = (Path) Shape.intersect(drawable.shape, background);
						d.setStroke(drawable.shape.getStroke());
						if (!drawable.toString().equals("Free")) {
							d.setFill(drawable.shape.getFill());
							d.getStrokeDashArray().clear();
						}
						d.setLayoutX(-pane.getBoundsInParent().getMinX());
						d.setLayoutY(-pane.getBoundsInParent().getMinY());
						added.add(new FreeShape(d, this));
						deleted.add(drawable);
					}
				}
				for (FreeShape x : added) {
					Shapes.add(x);
				}
				for (Drawing x : deleted) {
					x.delete(pane);
				}
				deleted.clear();
				added.clear();
			}
			pane.requestFocus();
		});

		Width.setPromptText(String.valueOf(bound_x));
		Height.setPromptText(String.valueOf(bound_y));
		font.getSelectionModel().select(0);
		fillColors.setValue(Color.TRANSPARENT);

		strokesolors.setValue(Color.BLACK);
		stroke = Color.BLACK;
		fil = Color.TRANSPARENT;
		background = new Rectangle(0, 0, bound_x, bound_y);
		background.setFill(Color.WHITE);
		pane.getChildren().add(background);
		Image image = new Image("jerry.gif");
		pane.setCursor(new ImageCursor(image));
		final ContextMenu contextMenu = new ContextMenu();
		contextMenu.getItems().addAll(del, mov, res, edit);
		// del.disableProperty().bind(new BooleanProperty(Selected.isEmpty()));
		del.setOnAction(e -> {
			contextMenu.hide();
			for (Drawing x : Selected) {
				// System.out.println(x+"deleted");
				deleted.add(x);
			}
			deleting = new Deletable();
			deleting.shapes.addAll(deleted);
			deleting.ws = this;
			history.execute(deleting);
			deleted.clear();

		});
		Undo.setOnAction(e -> {
			history.undo();
		});
		Redo.setOnAction(e -> {
			history.redo();
		});
		mov.setOnAction(e -> {
			contextMenu.hide();
			for (Drawing x : Selected) {
				resizable = false;
				movable = true;
			}
		});

		edit.setOnAction(e -> {
			contextMenu.hide();
			Selected.get(0).keyboard_Edit();
		});
		res.setOnAction(e -> {
			contextMenu.hide();
			for (Drawing x : Selected) {
				resizable = true;
				movable = false;
			}
		});
		Anim.setOnMouseClicked(e -> {

			if (Anim.isSelected()) {
				Anim.setOpacity(.5);

				ca.ws = this;
				colorChanging = new Thread(ca);
				colorChanging.start();

				ca.cont = true;
				for (Drawing x : Selected) {
					pane.getChildren().remove(x.bound);
				}
			} else {
				Anim.setOpacity(1);
				Selected.clear();
				ca.cont = false;
			}

		});
		shapeList.setOnAction(e -> {
			movable = false;
			resizable = false;
		});

		Save.setOnAction(e -> {
			save();
		});
		pane.setOnMousePressed(t -> {
			pane.requestFocus();
			if (t.getX() >= 0 && t.getX() <= bound_x && t.getY() >= 0 && t.getY() <= bound_y) {
				disp_x = t.getX();
				disp_y = t.getY();
				ix = disp_x;
				iy = disp_y;
				if (!shapeList.getValue().equals("pointer")) { // create
					if (t.getButton() == MouseButton.PRIMARY) {
						try {

							if (shape != null) {
								shape.deselect();
							}
							shape = (Drawing) dynalLoader.loadClass(shapeList.getValue()).newInstance();
							background.getStrokeDashArray().clear();
							background.setStroke(Color.TRANSPARENT);
							drawing = new Drawable();
							drawing.ws = this;
							drawing.shape = shape;
							drawing.disp_x = disp_x;
							drawing.disp_y = disp_y;
							history.execute(drawing);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							shape = null;
						}
					}
				} else { // edit
					if (t.getButton() == MouseButton.SECONDARY) {
						// resize delete move
						if (Selected.isEmpty()) {
							del.setDisable(true);
							mov.setDisable(true);
							res.setDisable(true);
							edit.setDisable(true);
						} else {
							if (Selected.size() == 1) {
								edit.setDisable(false);
							} else {
								edit.setDisable(true);
							}
							del.setDisable(false);
							mov.setDisable(false);
							res.setDisable(false);
						}
						contextMenu.show(pane, t.getScreenX(), t.getScreenY());
					} else {
						if (movable) {
							moving = new Movable();
							moving.shapes.addAll(Selected);
						} else if (resizable) {
							resizing = new Resizable();
							resizing.shapes.addAll(Selected);
						}
						contextMenu.hide();

						if (t.getClickCount() == 2) {
							intersection.setSelected(false);
							for (Shape x : intersected) {
								pane.getChildren().remove(x);
							}
							intersected.clear();
							for (Drawing x : Shapes) {

								if (x.containPoint(t.getX(), t.getY())) {
									if (x.selected) {
										x.deselect();
									} else {
										background.getStrokeDashArray().clear();
										background.setStroke(Color.TRANSPARENT);
										x.select();
									}
								} else if (x.selected) {
									if (!t.isControlDown()) {
										x.deselect();
									}
								}

							}
							if (Selected.isEmpty()) {
								if (background.getStrokeDashArray().isEmpty()) {
									background.getStrokeDashArray().addAll(3.0, 7.0, 3.0, 7.0);
									background.setStroke(Color.BLUE);
								} else {
									background.getStrokeDashArray().clear();
									background.setStroke(Color.TRANSPARENT);
								}
							}

						}
					}
				}
			}

		});
		pane.setOnMouseDragged(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				if (e.getX() >= 0 && e.getX() <= bound_x && e.getY() >= 0 && e.getY() <= bound_y) {

					x1.setText("X    " + e.getX());
					y1.setText("Y     " + e.getY());
					disp_x = e.getX() - disp_x;
					disp_y = e.getY() - disp_y;
					if (shapeList.getValue().equals("pointer")) { // create
						for (Drawing dra : Selected) {
							if (dra.containPoint(e.getX(), e.getY())) {
								if (movable) {
									moving.disp_x = disp_x;
									moving.disp_y = disp_y;						
									history.execute(moving);									
								} else if (resizable) {
									resizing.disp_x = disp_x;
									resizing.disp_y = disp_y;
									history.execute(resizing);
								}
							break;
							}
						}
					} else {
						shape.redraw(disp_x, disp_y);
					}
				}

			}
			disp_x = e.getX();
			disp_y = e.getY();
		});

		pane.setOnMouseReleased(e -> {
			if (shapeList.getValue().equals("FreeShape")) {
				if (shape != null)
					shape.select();
			}
			if (erasing && !shapeList.getValue().equals("pointer") && shape != null) {
				Shape y = shape.shape;
				shape.delete(pane);
				pane.getChildren().add(y);
				for (Drawing drawable : Shapes) {
					if (!drawable.isEqual((Path) Shape.subtract(drawable.shape, y))) {
						d = (Path) Shape.subtract(drawable.shape, y);
						d.setStroke(drawable.shape.getStroke());
						d.setFill(drawable.shape.getFill());
						d.setLayoutX(-pane.getBoundsInParent().getMinX());
						d.setLayoutY(-pane.getBoundsInParent().getMinY());
						added.add(new FreeShape(d, this));
						deleted.add(drawable);
					}

				}
				pane.getChildren().remove(y);
				shape.delete(pane);
				for (FreeShape x : added) {
					Shapes.add(x);
				}
				for (Drawing x : deleted) {
					x.delete(pane);
				}
				deleted.clear();
				added.clear();

			}

		});
		pane.setOnMouseMoved(e -> {
			if (e.getX() >= 0 && e.getX() <= bound_x && e.getY() >= 0 && e.getY() <= bound_y) {
				x1.setText("X    " + e.getX());
				y1.setText("Y     " + e.getY());
				if (shapeList.getValue().equals("pointer")) { // create
					for (Drawing x : Shapes) {
						if (x.containPoint(e.getX(), e.getY())) {
							x.shape.setCursor(Cursor.HAND);
							if (movable) {
								x.bound.setCursor(Cursor.MOVE);
								break;
							} else if (resizable) {
								place = Drawing.getPlace(e.getX(), e.getY(), ix, iy);
								if (place == 1) {

									x.bound.setCursor(Cursor.NW_RESIZE);
									x.shape.setCursor(Cursor.NW_RESIZE);
								} else if (place == 2) {
									x.bound.setCursor(Cursor.NE_RESIZE);
									x.shape.setCursor(Cursor.NE_RESIZE);
								} else if (place == 3) {
									x.bound.setCursor(Cursor.SW_RESIZE);
									x.shape.setCursor(Cursor.SW_RESIZE);
								} else {
									x.bound.setCursor(Cursor.SE_RESIZE);
									x.shape.setCursor(Cursor.SE_RESIZE);
								}

								break;
							}
						}
					}
				}
			}
		});
		Import.setOnAction(e -> {

			importshape();
		});
		Savepro.setOnAction(e -> {
			savepro();
		});
		Load.setOnAction(e -> {
			loadpro();
		});

	}

	private void importshape() {
		// TODO Auto-generated method stub

		FileChooser fc = new FileChooser();

		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("class", "*.class"));
		fc.setTitle("import ");
		File file = fc.showOpenDialog(st);
		if (file != null) {

			oper = "jar uf "
					+ new java.io.File(WorkSpace.class.getProtectionDomain().getCodeSource().getLocation().getPath())
					+ " " + file.getAbsolutePath();
			String x = file.getName().substring(0, file.getName().indexOf("."));
			shapeList.getItems().add(x);

			try {

				dynalLoader.addURL(file.getParentFile().toURL());

			} catch (Exception e1) { // TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	public void keyevent(KeyEvent e) {

		if (e.getCode() == KeyCode.UP) {
			moving=new Movable();
			moving.disp_x = 0;
			moving.disp_y = -5;		
			moving.shapes.addAll(Selected);
			history.execute(moving);	
			
		}
		if (e.getCode() == KeyCode.DOWN) {
			moving=new Movable();
			moving.disp_x = 0;
			moving.disp_y = 5;		
			moving.shapes.addAll(Selected);
			history.execute(moving);
			
		}
		if (e.getCode() == KeyCode.RIGHT) {
			moving=new Movable();
			moving.disp_x = 5;
			moving.disp_y = 0;		
			moving.shapes.addAll(Selected);
			history.execute(moving);
			
		}
		if (e.getCode() == KeyCode.LEFT) {
			moving=new Movable();
			moving.disp_x = -5;
			moving.disp_y = 0;		
			moving.shapes.addAll(Selected);
			history.execute(moving);
			
		}
		if (e.getCode() == KeyCode.ADD) {
			resizing=new Resizable();
			resizing.disp_x = 5;
			resizing.disp_y = 5;		
			resizing.shapes.addAll(Selected);
			history.execute(moving);
			
		}
		if (e.getCode() == KeyCode.SUBTRACT) {
			resizing=new Resizable();
			resizing.disp_x = -5;
			resizing.disp_y = -5;		
			resizing.shapes.addAll(Selected);
			history.execute(moving);
		}

		if (e.getCode() == KeyCode.DELETE) {
			del.fire();
		}
		if (e.isControlDown()) {
			if (e.getCode() == KeyCode.A) {
				for (Drawing drawable : Shapes) {
					drawable.select();
				}
			}
			if (e.getCode() == KeyCode.Z) {
				Undo.fire();
			}
			if (e.getCode() == KeyCode.Y) {
				Redo.fire();
			}

		}
		if (e.getCode() == KeyCode.ENTER) {
			if (e.isAltDown())
				st.setFullScreen(true);
		}
	}

	public void save() {
		for (Drawing drawable : Selected) {
			deleted.add(drawable);
		}
		for (Drawing drawable : deleted) {
			drawable.deselect();
		}
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("png", "*.png"));
		fc.setTitle("Save ");
		File file = fc.showSaveDialog(st);
		if (file != null) {

			WritableImage wi = new WritableImage((int) bound_x, (int) bound_y);
			try {
				String x = fc.getSelectedExtensionFilter().getDescription();
				ImageIO.write(SwingFXUtils.fromFXImage(pane.snapshot(null, wi), null), x, file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		for (Drawing drawable : deleted) {
			drawable.select();
		}
		deleted.clear();
	}

	public void savepro() {
		for (Drawing drawable : Shapes) {
			saveshapes.add(drawable.save());
		}
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("UPaintProject", "*.ser"));
		fc.setTitle("Save ");
		File file = fc.showSaveDialog(st);
		if (file != null) {
			try {
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(saveshapes);
				oos.close();
				fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		saveshapes.clear();

	}

	public void loadpro() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("UPaintProject", "*.ser"));
		fc.setTitle("Load Project ");
		File file = fc.showOpenDialog(st);
		if (file != null) {
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				saveshapes = (ArrayList) ois.readObject();
				ois.close();
				fis.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
				return;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < saveshapes.size(); i++) {
			try {
				shapel = (Drawing) Class.forName((saveshapes.get(i).split(","))[0]).newInstance();
				shapel.load(saveshapes.get(i), this);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		saveshapes.clear();
	}

}