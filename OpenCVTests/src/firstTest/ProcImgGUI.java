package firstTest;


import org.opencv.core.Core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProcImgGUI extends Application {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		VBox root = new VBox();
		HBox imagebox = new HBox();
		HBox buttons = new HBox();
		Label lbl_text;
		Button btn_select;
		ImageView imageView = new ImageView();
		ImageSelect image = new ImageSelect();
		
		
		try {
			// scene elements
			lbl_text = new Label("Teste Processamento de Imagem");
			btn_select = new Button("Selecionar assinatura");
			btn_select.setId("btnid");
			
			// webcam opencv
			imageView.setFitWidth(230);
			imageView.setPreserveRatio(true);
			

			// actions
			btn_select.setOnAction(e -> image.Load(imageView, btn_select));

			// scene template
			imagebox.getChildren().addAll(imageView);
			buttons.getChildren().addAll(btn_select);
			root.getChildren().addAll(lbl_text, imagebox, buttons);

			// draw scene
			Scene scene = new Scene(root, 924, 528);
			stage.setTitle("OpenCV Teste");
			stage.setScene(scene);

			// attach style css
			
			buttons.getStyleClass().add("buttons");
			scene.getStylesheets().add("myStyle.css");

			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
