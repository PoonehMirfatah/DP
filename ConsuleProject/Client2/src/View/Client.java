package View;


import Models.PublicMessage;
import Models.User;


import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {

        String hostname = "localhost";
        int port = 8080;
        try (Socket socket = new Socket(hostname, port)) {
            OutputStream output = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(output);
            InputStream input = socket.getInputStream();
            DataInputStream in=new DataInputStream(input);
            ObjectOutputStream os=new ObjectOutputStream(output);
            ObjectInputStream is=new ObjectInputStream(input);


            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String name;

            try {
                System.out.print("Enter Your name: ");
                name = consoleReader.readLine();
                User user=new User(name);
                os.writeObject(user);
                Scanner sc = new Scanner(System.in);
                do {
                    System.out.println("Welcome to ChatRoom\n");
                    System.out.println("Public Messages");
                    System.out.println((String)in.readUTF());
                    System.out.println("1.Send Message to Chatroom");
                    System.out.println("2.PV");
                    System.out.println("3.Refresh Messages");
                    System.out.println("ping");
                    System.out.println("search-[time1]to[time2]");
                    System.out.println("Search-[name]");
                    System.out.println("exit");

                    switch (sc.nextLine()) {
                        case "1":
                            out.writeUTF("1");
                            System.out.println("Public Messages");
                            System.out.println((String) in.readUTF());
                            Scanner sc2;
                            System.out.println("Write your message");
                            sc2 = new Scanner(System.in);
                            String message=sc2.nextLine();
                            System.out.println("Message sent successfully");
                            PublicMessage pm=new PublicMessage(user,message);
                            os.writeObject(pm);
                            break;
                        case "2":
                            out.writeUTF("2");
                            System.out.println("choose an online user");
                            String users=(String)in.readUTF();
                            System.out.println(users);
                            out.writeUTF(name);
                            Scanner sc3=new Scanner(System.in);
                            out.writeInt(sc3.nextInt());
                            Scanner sc4;
                            boolean stayInPV=true;
                            do {
                                System.out.println("PV Messages");
                                System.out.println((String) in.readUTF());
                                System.out.println("1.Send Message");
                                System.out.println("2.Clear History");
                                System.out.println("3.Finish");
                                System.out.println("4.Refresh Messages");
                                sc4 = new Scanner(System.in);
                                switch (sc4.nextLine()) {
                                    case "1":
                                        out.writeUTF("1");
                                        System.out.println("PV Messages");
                                        System.out.println((String) in.readUTF());
                                        System.out.println("Write your message");
                                        Scanner sc5 = new Scanner(System.in);
                                        out.writeUTF(sc5.nextLine());
                                        System.out.println("message Sent succssfully");
                                        break;
                                    case "2":
                                        out.writeUTF("2");
                                        System.out.println("History Cleared");
                                        break;
                                    case "3":
                                        out.writeUTF("3");
                                        stayInPV=false;
                                        break;
                                    case "4":
                                        out.writeUTF("4");
                                        System.out.println("PV Messages");
                                        System.out.println((String) in.readUTF());
                                }
                            }while (stayInPV);
                            break;
                        case "3":
                            out.writeUTF("3");
                            System.out.println("Public Messages");
                            System.out.println((String) in.readUTF());
                            break;
                        case "ping":
                            out.writeUTF("ping");
                            double time1=System.currentTimeMillis();
                            out.writeUTF(" ");
                            System.out.println((String)in.readUTF());
                            double time2=System.currentTimeMillis();
                            System.out.println("ping:"+(time2-time1)+" ms");
                            break;
                        case "search":
                            out.writeUTF("search");
                            Scanner scanner=new Scanner(System.in);
                            String period=scanner.nextLine();
                            out.writeUTF(period);
                            System.out.println((String)in.readUTF());
                            break;
                        case "Search":
                            out.writeUTF("Search");
                            Scanner scanner1=new Scanner(System.in);
                            String searchedName=scanner1.nextLine();
                            out.writeUTF(searchedName);
                            System.out.println((String)in.readUTF());
                            break;
                        case "exit":
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Incorrect command");
                    }
                }while (!sc.nextLine().equals("exit"));

            } catch (IOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}



