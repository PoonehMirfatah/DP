package controller;

import Models.PublicMessage;
import Models.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Session {
    private ArrayList<User> allUsers = new ArrayList<>();
    private Map<Socket, User> onlineUsers = new HashMap<>();
    private static Session session = new Session();

    private Session() {

    }

    public static Session getInstance() {
        return session;
    }

    public String showOnlineUsers() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allUsers.size(); i++) {
            sb.append(i + 1).append(" - ").append(allUsers.get(i).getUsername()).append("\n");
        }
        return sb.toString();
    }

    public Socket getSocket(int index) {
        String username = null;
        for (int i = 0; i < allUsers.size(); i++) {
            if (i + 1 == index) {
                username = allUsers.get(i + 1).getUsername();
            }
        }
        for (Map.Entry<Socket, User> map : onlineUsers.entrySet()) {
            if (map.getValue().getUsername().equals(username)) {
                return map.getKey();
            }
        }
        return null;
    }
    public String getUserName(int index) {
        String username = null;
        for (int i = 0; i < allUsers.size(); i++) {
            if (i + 1 == index) {
                username = allUsers.get(i).getUsername();
                return username;
            }
        }
        return "not found";
    }

    public static Session getSession() {
        return session;
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setSession(Session session) {
        Session.session = session;
    }

    public Map<Socket, User> getOnlineUsers() {
        return onlineUsers;
    }

    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }

    public void setOnlineUsers(Map<Socket, User> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }
}


