package Models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PublicMessage implements Serializable {
    private int ID;
    private User sender;
    private Date date;
    private String content;
    public PublicMessage(User sender, String content) throws SQLException, ClassNotFoundException {
        this.ID=getMaxID()+1;
        this.sender=sender;
        this.content=content;
        this.date=new Date();
    }

    public String toString(){
        return sender.getUsername() +" : "+content+"\n"+date+"\n";
    }
    public boolean insertMessage( ) throws Exception {
        String SQLcom = String.format("INSERT INTO publicmessages (ID,UserID,messsage) VALUES (%s, %s,'%s')", ID,sender.getID(), content);
        MySQLConnection sql = new MySQLConnection();
        return sql.executeSQL(SQLcom);
    }
    public  int getMaxID() throws SQLException, ClassNotFoundException {
        String sqlCmd="Select MAX(ID) from publicmessages";
        MySQLConnection sql=new MySQLConnection();
        ResultSet rs=sql.executeQuery(sqlCmd);
        if(rs.next()){
            return rs.getInt(1);
        }else {
            return 0;
        }
    }
    public static String showMessages() throws Exception {
        StringBuilder sb = new StringBuilder();
        MySQLConnection sql = new MySQLConnection();
        String SQLcmd = "Select allusers.UserName,publicmessages.messsage,publicmessages.dateAndTime FROM allusers INNER JOIN publicmessages on allusers.UserID=publicmessages.UserID ORDER BY ID";
        ResultSet rs = sql.executeQuery(SQLcmd);
        if (rs == null) {
            return "Not Found!";
        } else {
            while (rs.next()) {
                sb.append(rs.getString("UserName")).append(" : ").append(rs.getString("messsage"));
                sb.append("\t\t\t").append(rs.getString("dateAndTime")).append("\n");
            }
        }
        return sb.toString();
    }
    public static String showSearchedTime(String time1,String time2) throws SQLException {
        StringBuilder sb = new StringBuilder();
        MySQLConnection sql = new MySQLConnection();
        String SQLcmd = String.format("Select allusers.UserName,publicmessages.messsage,publicmessages.dateAndTime FROM allusers INNER JOIN publicmessages on allusers.UserID=publicmessages.UserID WHERE publicmessages.dateAndTime>='%s' AND publicmessages.dateAndTime<='%s' ORDER BY ID",time1,time2);
        ResultSet rs = sql.executeQuery(SQLcmd);
        if (rs == null) {
            return "Not Found!";
        } else {
            while (rs.next()) {
                sb.append(rs.getString("UserName")).append(" : ").append(rs.getString("messsage"));
                sb.append("\t\t\t").append(rs.getString("dateAndTime")).append("\n");
            }
        }
        return sb.toString();
    }
    public static String showSearchedName(String name) throws SQLException {
        StringBuilder sb = new StringBuilder();
        MySQLConnection sql = new MySQLConnection();
        String SQLcmd = String.format("Select allusers.UserName,publicmessages.messsage,publicmessages.dateAndTime FROM allusers INNER JOIN publicmessages on allusers.UserID=publicmessages.UserID WHERE allusers.UserName='%s' ORDER BY ID",name);
        ResultSet rs = sql.executeQuery(SQLcmd);
        if (rs == null) {
            return "Not Found!";
        } else {
            while (rs.next()) {
                sb.append(rs.getString("UserName")).append(" : ").append(rs.getString("messsage"));
                sb.append("\t\t\t").append(rs.getString("dateAndTime")).append("\n");
            }
        }
        return sb.toString();
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public User getSender() {
        return sender;
    }
}
