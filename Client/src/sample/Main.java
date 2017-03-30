package sample;

import javafx.application.Application;
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
            stage.setScene(new StartView());
            stage.setTitle("FileSharer v1.0");
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void FileSharer(){
        try {
            Stage stage = new Stage();
            stage.setScene(new FileShareView());
            stage.setTitle("FileSharer v1.0");
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