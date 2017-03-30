package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        promptUser();
    }

    public void promptUser() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("StartWindow.fxml"));
            stage.setTitle("FileSharer v1.0");
            stage.setScene(new Scene(root, 500, 130));
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void FileSharer(){
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FileSharer.fxml"));
            stage.setTitle("FileSharer v1.0");
            stage.setScene(new Scene(root, 500, 600));
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
