package View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
            int port = 8080;

            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server is listening on port " + port);

                while (true) {
                    long time1=System.currentTimeMillis();
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected");
                    long time2=System.currentTimeMillis();
                    System.out.println("ping: "+(time2-time1)+" ms");

                    Thread t1=new ServerThread(socket);
                    t1 .start();

                }

            } catch (IOException ex) {
                System.out.println("Socket.Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }

    }
}