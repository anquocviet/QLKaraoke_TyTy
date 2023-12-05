package main;

import connect.ConnectDB;
import controllers.SplashController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

/**
 * JavaFX App
 */
public class App extends Application {

	public static Stage primaryStage;
	public static Scene primaryScene;

	public static String user;
	public static final int widthModal = 800;
	public static final int heightModal = 684;
	public static final int widthModalLogin = 732;
	public static final int heightModalLogin = 517;
	public static final int widthModalBill = 450;
	public static final int heightModalBill = 760;
	public static final int VAT = 5;
	public static final int VATDV = 10;
	public static final int TTDB = 30;
	public static final long TIENPHONGTHEMDEM = 50000;

	@Override
	public void init() throws Exception {
		SplashController splash = new SplashController();
		splash.checkFuntions();
		ConnectDB.getInstance().connect();
	}

	@Override
	public void start(Stage stage) throws IOException {
		this.primaryStage = stage;
	}

	public static void openMainGUI() throws IOException {
//        Open Main GUI
		primaryScene = new Scene(loadFXML("AppFrame"), 1280, 740);
//		Stage stage = new Stage();
		primaryStage.setTitle("Quản Lý Karaoke Tỷ Tỷ");
		primaryStage.setResizable(false);
		primaryStage.setScene(primaryScene);
		primaryStage.setOnCloseRequest(event -> {
			Platform.exit();
			System.exit(0);
		});
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	public static void openModal(String fxml, int width, int height) throws IOException {
		Scene sceneModal = new Scene(loadFXML(fxml), width, height);
		Stage stageModal = new Stage();
		stageModal.setResizable(false);
		stageModal.initModality(Modality.APPLICATION_MODAL);
		stageModal.setScene(sceneModal);
		if (fxml.equals("GD_DangNhap")) {
			stageModal.setOnCloseRequest(event -> {
				Platform.exit();
				System.exit(0);
			});
		}
		stageModal.showAndWait();
	}

	public static void setRoot(String fxmlNewChild) throws IOException {
		Parent child = loadFXML(fxmlNewChild);
		Parent root = primaryScene.getRoot();
		ObservableList<Node> paneChildren = ((Pane) root.getChildrenUnmodifiable().get(0)).getChildren();
		paneChildren.clear();
		paneChildren.add(child);
	}

	public static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlFrame = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
		return fxmlFrame.load();
	}

	public static void main(String[] args) {
//		System.setProperty("javafx.preloader", AppPreloader.class.getName());
		launch(App.class, args);
	}

}
