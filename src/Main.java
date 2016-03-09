
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage)  {
		
		
		BorderPane borderPane = null;		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(WorkSpace.class.getResource("Application.fxml"));
		try {
			 borderPane = (BorderPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block		
			e.printStackTrace();
		}
		((WorkSpace)loader.getController()).st=primaryStage;
		Scene scene = new Scene(borderPane);
		
		primaryStage.getIcons().add(new Image("baset.png"));
		primaryStage.setScene(scene);
		primaryStage.setTitle("U Paint!");
		primaryStage.show();	

		primaryStage.setOnCloseRequest(t -> {
			if (((WorkSpace)loader.getController()).oper != null) {
				new Alert(AlertType.INFORMATION, ((WorkSpace)loader.getController()).oper, ButtonType.CLOSE).show();
				Runtime rt = Runtime.getRuntime();
				try {
					Process proc = rt.exec(((WorkSpace)loader.getController()).oper);
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
