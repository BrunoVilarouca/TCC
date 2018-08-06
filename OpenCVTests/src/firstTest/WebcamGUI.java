package firstTest;

import org.opencv.core.Core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WebcamGUI extends Application {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		VBox root = new VBox();
		VBox optionsmenu = new VBox();
		HBox webcambox = new HBox();
		HBox buttons = new HBox();
		Label lbl_text;
		Label lbl_options;
		Button btn_analyze;
		CheckBox grayscale = new CheckBox();
		ImageView webcamView = new ImageView();
		ImageView histogram = new ImageView();
		WebcamVideo webcamVideo = new WebcamVideo();

		try {
			// scene elements
			lbl_text = new Label("Teste Processamento de Imagem");
			btn_analyze = new Button("iniciar");
			btn_analyze.setId("btnid");
			lbl_options = new Label ("Testes:");
			grayscale.setText("Escala de Cinza");

			// webcam opencv
			webcamView.setFitWidth(612);
			webcamView.setPreserveRatio(true);
			optionsmenu.setMinWidth(300);
			
			// actions
			btn_analyze.setOnAction(e -> webcamVideo.Record(webcamView, btn_analyze, grayscale, histogram));

			// scene template
			optionsmenu.getChildren().addAll(lbl_options, grayscale, histogram);
			webcambox.getChildren().addAll(webcamView, optionsmenu);
			buttons.getChildren().addAll(btn_analyze);
			root.getChildren().addAll(lbl_text, webcambox, buttons);

			// draw scene
			Scene scene = new Scene(root, 924, 528);
			stage.setTitle("OpenCV Teste");
			stage.setScene(scene);

			// attach style css
			optionsmenu.getStyleClass().add("optionsmenu");
			buttons.getStyleClass().add("buttons");
			scene.getStylesheets().add("myStyle.css");

			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
