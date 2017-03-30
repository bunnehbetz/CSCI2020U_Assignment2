package sample;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ClientController {

    private String hostname = "127.0.0.1";
    private int port = 8686;
    private File sDirectory, cDirectory;
    private File[] sFiles, cFiles;
    private List<File> sList, cList;

    @FXML
    public TextField txtServerDirectory, txtClientDirectory, txtIP;

    @FXML
    public ListView ServerDirectory;

    @FXML
    public ListView ClientDirectory;

    @FXML
    public Button btnChoose1, btnChoose2, btnSubmit, btnUpload, btnDownload;

    @FXML
    public void initialize() {

    }

    public void btnChoose1_OnAction(Event event) {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File main_directory = directoryChooser.showDialog(stage);
        txtServerDirectory.setText(main_directory.toString());
    }

    public void btnChoose2_OnAction(Event event) {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File main_directory = directoryChooser.showDialog(stage);
        txtClientDirectory.setText(main_directory.toString());
    }

    public void btnSubmit_OnAction(Event event) {
        this.sDirectory = new File(txtServerDirectory.getText());
        this.cDirectory = new File(txtClientDirectory.getText());

        if (txtIP.getLength() != 0) {
            this.hostname = txtIP.getText().toString();
        }

        if (txtServerDirectory.getLength() != 0 && txtClientDirectory.getLength() != 0){
            sFiles = new File(sDirectory.getAbsolutePath()).listFiles();
            sList = new ArrayList<File>(Arrays.asList(sFiles));
            //ServerDirectory.getItems().addAll(sFiles);
            ClientDirectory.getItems();
            //ServerDirectory.setCellFactory(ComboBoxListCell.forListView(sList));
            //for (int i = 0; i <sFiles.length; i++){
            //    System.out.print(sFiles[i].getName());
            //}

            cFiles = new File(cDirectory.getAbsolutePath()).listFiles();
            //ClientDirectory.getItems().addAll(cFiles);
            //ClientDirectory.itemsProperty().bind(cListProperty);

            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            stage.close();
            Main main = new Main();
            main.FileSharer();
        }
    }

    public void btnUpload_OnAction(Event event){
        System.out.print("Upload");
        //upload(file);
    }

    public void btnDownload_OnAction(Event event){
    }

    public void chooseDirectory(){//don't know if this is still needed
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File main_directory = directoryChooser.showDialog(stage);
        File[] local_files = new File(main_directory.getAbsolutePath()).listFiles();
        //listProperty.set(FXCollections.observableArrayList(local_files.toString()));
        //ClientDirectory.itemsProperty().bind(listProperty);
    }

    public void upload(File file) {
        try {
            Socket socket = new Socket(this.hostname, this.port);
            long length = file.length();
            byte[] bytes = new byte[8 * 1024];
            InputStream in = new FileInputStream(file);
            OutputStream out = socket.getOutputStream();

            int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }

            out.close();
            in.close();
            socket.close();

        } catch (IOException ioe) {
            System.out.println("Exception found on accept. Ignoring. Stack Trace :");
            ioe.printStackTrace();
        }

        //add something here to update the list view
    }

    public void submitQuery(String uri) {
        try {
            Socket socket = new Socket(this.hostname, this.port);

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("GET " + uri + " HTTP/1.0\r\n");
            out.println("Host: " + this.hostname + "\r\n\r\n");
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            out.close();
            in.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}