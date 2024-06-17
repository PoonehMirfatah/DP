package View;

import Models.PrivateMessage;
import Models.PublicMessage;
import Models.User;
import controller.Session;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ServerThread extends Thread {
    private final Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (
                InputStream input = socket.getInputStream();
                DataInputStream in = new DataInputStream(input);
                OutputStream output = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(output);
                ObjectInputStream oi = new ObjectInputStream(input);
                ObjectOutputStream ou = new ObjectOutputStream(output);
        ) {
            User user;
            if ((user = (User) oi.readObject()) != null) {
                Session.getInstance().getAllUsers().add(user);
                Session.getInstance().getOnlineUsers().put(socket, user);
                System.out.println(user.insertUser());
            }

            while (true) {
                try {
                   out.writeUTF(PublicMessage.showMessages());
                    String input2 = in.readUTF();
                    switch (input2) {
                        case "1":
                            PublicMessage pm;
                            out.writeUTF(PublicMessage.showMessages());
                            if ((pm = (PublicMessage) oi.readObject()) != null) {
                                System.out.println(pm.insertMessage());
                            }
                            break;
                        case "2":
                            String onlineUsers = Session.getInstance().showOnlineUsers();
                            out.writeUTF(onlineUsers);
                            String sender = in.readUTF();
                            String receiver = Session.getInstance().getUserName(in.readInt());
                            String input3;
                            boolean stayInPV=true;
                            do {
                                out.writeUTF(PrivateMessage.showPvMessages(sender, receiver));
                                input3 = in.readUTF();
                                switch (input3) {
                                    case "1":
                                        out.writeUTF(PrivateMessage.showPvMessages(sender, receiver));
                                        String message = in.readUTF();
                                        PrivateMessage pv = new PrivateMessage(sender, receiver, message);
                                        System.out.println(pv.insertMessage());
                                        break;
                                    case "2":
                                        System.out.println(PrivateMessage.deleteMessages(sender, receiver));
                                        break;
                                    case "3":
                                        stayInPV=false;
                                        break;
                                    case "4":
                                        out.writeUTF(PrivateMessage.showPvMessages(sender, receiver));
                                        break;
                                }
                            }while (stayInPV);

                            break;
                        case "3":
                            out.writeUTF(PublicMessage.showMessages());
                            break;
                        case "ping":
                            String received = in.readUTF();
                            out.writeUTF("Connected to server");
                            break;
                        case "search":
                            String period = in.readUTF();
                            String[] time = period.split(" to ");
                            out.writeUTF(PublicMessage.showSearchedTime(time[0], time[1]));
                            break;
                        case "Search":
                            String name = in.readUTF();
                            out.writeUTF(PublicMessage.showSearchedName(name));
                            break;
                    }
                } catch (IOException e) {
                    Session.getInstance().getAllUsers().remove(user);
                    System.out.println("Client disconnected: " + socket.getInetAddress());
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Class not found: " + e.getMessage());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Class not found: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
