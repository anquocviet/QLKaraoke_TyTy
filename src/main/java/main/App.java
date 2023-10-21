package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("AppFrame"), 1280, 740);
        stage.setTitle("Quản Lý Karaoke Tỷ Tỷ");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxmlNewChild) throws IOException {
        Parent child = loadFXML(fxmlNewChild);
        Parent root = scene.getRoot();
        ObservableList<Node> paneChildren =  ((Pane) root.getChildrenUnmodifiable().get(0)).getChildren();
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
