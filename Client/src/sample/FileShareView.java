package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import java.io.*;
import java.net.Socket;

public class FileShareView extends Scene {

    private static String hostname = "127.0.0.1";
    private static int port = 8989;

    private String selectedSFile, selectedCFile;

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
        System.out.println("start");
        StartView startView = new StartView();
        String filename = clientFiles.getSelectionModel().getSelectedItem();
        selectedCFile = startView.getSClientDirectory() + "/" + filename; //This is the path in String of the selected file
        File file = new File(selectedCFile);
        System.out.println(selectedCFile);
        //upload
        try {
            Socket socket = new Socket(this.hostname, this.port);
            byte[] bytes = new byte[16 * 1024];

            InputStream in = new FileInputStream(file);
            OutputStream out = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(osw);
            String sendCommand = "upload " + file;
            bw.write(sendCommand);
            bw.flush();

            int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }

        } catch (IOException ioe) {
            System.out.println("Exception found on accept. Ignoring. Stack Trace :");
            ioe.printStackTrace();
        }

    }

    public void download() {
        String filename = clientFiles.getSelectionModel().getSelectedItem();
        File file = new File(filename);

        try {
            Socket socket = new Socket(this.hostname, this.port);

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("download " + filename);

            out.close();
            socket.close();

        } catch (IOException ioe) {
            System.out.println("Exception found on accept. Ignoring. Stack Trace :");
            ioe.printStackTrace();
        }
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
