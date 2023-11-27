package main;

import connect.ConnectDB;
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

    private static Scene scene;
    public static String user;
    public static final int widthModal = 800;
    public static final int heightModal = 684;
    public static final int widthModalLogin = 732;
    public static final int heightModalLogin = 517;

    @Override
    public void start(Stage stage) throws IOException {
        ConnectDB.getInstance().connect();
        
//        Open Modal Login
        openModal("GD_DangNhap", widthModalLogin, heightModalLogin);
    }
	
	public static void openMainGUI() throws IOException {
//        Open Main GUI
        scene = new Scene(loadFXML("AppFrame"), 1280, 740);
		Stage stage = new Stage();
        stage.setTitle("Quản Lý Karaoke Tỷ Tỷ");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        stage.centerOnScreen();
        stage.show();
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
        Parent root = scene.getRoot();
        ObservableList<Node> paneChildren = ((Pane) root.getChildrenUnmodifiable().get(0)).getChildren();
        paneChildren.clear();
        paneChildren.add(child);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlFrame = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlFrame.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
