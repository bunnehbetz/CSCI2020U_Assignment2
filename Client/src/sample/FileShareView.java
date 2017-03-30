package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import java.io.File;

public class FileShareView extends Scene {

    private static String hostname = "127.0.0.1";

    private File[] sFiles, cFiles;

    private Button uploadButton;

    private Button downloadButton;

    private static ListView<String> clientFiles = new ListView<>();

    private static ListView<String> serverFiles = new ListView<>();

    public FileShareView() {
        super(new BorderPane());
        initialize();
    }

    private void initialize() {

        BorderPane root = (BorderPane) getRoot();
        GridPane buttons = new GridPane();
        SplitPane fileViews = new SplitPane();

        uploadButton = new Button("Upload");
        downloadButton = new Button("Download");

        buttons.add(uploadButton, 0, 0);
        buttons.add(downloadButton, 1, 0);

        //add list views to split pane
        fileViews.getItems().add(clientFiles);
        fileViews.getItems().add(serverFiles);

        uploadButton.setOnAction(e -> upload());
        downloadButton.setOnAction(e -> download());

        root.setTop(buttons);
        root.setCenter(fileViews);
    }

    public void upload() {

    }

    public void download() {

    }

    public ListView<String> getServerFiles() {
        return serverFiles;
    }

    public void setServerFiles(File serverName) {
        sFiles = serverName.listFiles();

        int size = sFiles.length;
        String stringFiles[] = new String[size];
        for (int i = 0; i < size; i++) {
            stringFiles[i] = sFiles[i].getName();
        }

        serverFiles.getItems().addAll(stringFiles);
    }

    public ListView<String> getClientFiles() {
        return clientFiles;
    }

    public void setClientFiles(File clientName) {
        cFiles = clientName.listFiles();

        int size = cFiles.length;
        String stringFiles[] = new String[size];
        for (int i = 0; i < size; i++) {
            stringFiles[i] = cFiles[i].getName();
        }

        clientFiles.getItems().addAll(stringFiles);
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String ip) {
        hostname = ip;
    }
}
