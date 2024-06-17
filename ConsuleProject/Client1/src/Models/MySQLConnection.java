package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLConnection {
    private String URL="jdbc:mysql://localhost/messanger";
    private String password="123";
    private String username="root";
    public Boolean executeSQL(String SQLcmd) throws Exception {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, username, password);
            Statement s = con.prepareStatement(SQLcmd);
            s.execute(SQLcmd);
            con.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public ResultSet executeQuery(String SQLcmd){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, username, password);
            Statement s = con.prepareStatement(SQLcmd);
            ResultSet rs=s.executeQuery(SQLcmd);
            return rs;
        }catch (Exception e){
            return null;
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}