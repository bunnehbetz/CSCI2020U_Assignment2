package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;

public class StartView extends Scene {

    private File sDirectory, cDirectory;

    private static String sServerDirectory, sClientDirectory;

    private TextField txtServerDirectory;

    private TextField txtClientDirectory;

    private TextField txtIP;

    private Button serverChooseButton;

    private Button clientChooseButton;

    private Button submitButton;

    public StartView() {
        super(new GridPane(),345,150);
        initialize();
    }

    private void initialize() {

        GridPane root = (GridPane) getRoot();
        root.setVgap(10);
        root.setHgap(10);
        root.setPadding(new Insets(10));

        txtServerDirectory = new TextField();
        txtClientDirectory = new TextField();
        txtIP = new TextField();

        serverChooseButton = new Button("Choose...");
        clientChooseButton = new Button("Choose...");
        submitButton = new Button("Submit");

        serverChooseButton.setOnAction(e -> sChooseDirectory());
        clientChooseButton.setOnAction(e -> cChooseDirectory());
        submitButton.setOnAction(e -> submit());

        root.add(new Label("Server Directory:"), 0, 0);
        root.add(new Label("Client Directory:"), 0, 1);
        root.add(new Label("IP (Optional):"), 0, 2);

        root.add(txtServerDirectory, 1, 0);
        root.add(txtClientDirectory, 1, 1);
        root.add(txtIP, 1, 2);

        root.add(serverChooseButton, 2, 0);
        root.add(clientChooseButton, 2, 1);
        root.add(submitButton, 2, 3);
    }

    public void sChooseDirectory() {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File main_directory = directoryChooser.showDialog(stage);
        txtServerDirectory.setText(main_directory.toString());
    }

    public void cChooseDirectory() {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File main_directory = directoryChooser.showDialog(stage);
        txtClientDirectory.setText(main_directory.toString());
    }

    public void submit() {
        FileShareView fileShareView = new FileShareView();

        this.sServerDirectory = txtServerDirectory.getText();
        this.sClientDirectory = txtClientDirectory.getText();

        this.sDirectory = new File(sServerDirectory);
        this.cDirectory = new File(sClientDirectory);

        if (txtIP.getLength() != 0) {
            fileShareView.setHostname(txtIP.getText());
        }

        if (txtServerDirectory.getLength() != 0 && txtClientDirectory.getLength() != 0) {

            fileShareView.setServerFiles(sDirectory);
            fileShareView.setClientFiles(cDirectory);

            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
            Main main = new Main();
            main.FileSharer();
        }
    }

    public String getSServerDirectory() {
        return sServerDirectory;
    }

    public String getSClientDirectory() {
        return sClientDirectory;
    }
}