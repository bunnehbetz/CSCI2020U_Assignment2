import java.io.BufferedReader;
import java.io.*;
import java.io.InputStreamReader;
import java.net.*;

public final class Server {
    private ServerSocket server_socket = null;
    boolean server_on = true;

    public Server(int port) {
        try {
            server_socket = new ServerSocket(port);
        } catch(IOException e) {
            e.printStackTrace();
        }

        while(server_on) {
            try {
                Socket client_socket = server_socket.accept();
                client_service_thread cli_thread = new client_service_thread(client_socket);
                cli_thread.run();
            } catch(IOException ioe) {
                System.out.println("Exception found on accept. Ignoring. Stack Trace :");
                ioe.printStackTrace();
            }
        }
    }

    class client_service_thread extends Thread {
        Socket client_socket;
        boolean thread_running = true;

        public client_service_thread(Socket s) {
            client_socket = s;
        }


        public void run() {
            while (thread_running) {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader
                            (client_socket.getInputStream()));

                    String line = null;

                    while ((line = in.readLine()) != null) {
                        String[] command = line.split(" ");
                        if (command[0].equals("upload")) {
                            file_receive(command[1]);
                        } else if (command[0].equals("download")){
                            file_send(command[1]);
                        }

                        break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Thread.interrupted()) {
                    thread_running = false;
                }
            }
        }

        public void file_send(String filename) {
            File file = new File(filename);
            try {
                byte[] bytes = new byte[8 * 1024];
                InputStream in = new FileInputStream(file);
                OutputStream out = client_socket.getOutputStream();

                int count;
                while ((count = in.read(bytes)) > 0) {
                    out.write(bytes, 0, count);
                }

                out.close();
                in.close();
                client_socket.close();

            } catch (IOException ioe) {
                System.out.println("Exception found on accept. Ignoring. Stack Trace :");
                ioe.printStackTrace();
            }
        }

        public void file_receive(String filename) {

        }

        public void send_file_list() {
//            try {
//                byte[] bytes = new byte[8 * 1024];
//                InputStream in = new FileInputStream(file);
//                OutputStream out = client_socket.getOutputStream();
//
//                int count;
//                while ((count = in.read(bytes)) > 0) {
//                    out.write(bytes, 0, count);
//                }
//
//                out.close();
//                in.close();
//                client_socket.close();
//
//            } catch (IOException ioe) {
//                System.out.println("Exception found on accept. Ignoring. Stack Trace :");
//                ioe.printStackTrace();
//            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8989);
    }
}
