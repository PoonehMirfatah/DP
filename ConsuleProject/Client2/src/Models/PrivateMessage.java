package Models;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PrivateMessage implements Serializable {

    private int ID;
    private String sender;
    private String reciever;
    private Date date;
    private String content;
    public PrivateMessage(String sender,String reciever, String content) throws SQLException, ClassNotFoundException {
        this.ID=getMaxID()+1;
        this.reciever=reciever;
        this.sender=sender;
        this.content=content;
        this.date=new Date();
    }
    public  int getMaxID() throws SQLException, ClassNotFoundException {
        String sqlCmd="Select MAX(ID) from pvmessages";
        MySQLConnection sql=new MySQLConnection();
        ResultSet rs=sql.executeQuery(sqlCmd);
        if(rs.next()){
            return rs.getInt(1);
        }else {
            return 0;
        }
    }
    public String toString(){
        return sender+" : "+content+"\n"+date+"\n";
    }
    public boolean insertMessage( ) throws Exception {
        String SQLcom = String.format("INSERT INTO pvmessages (ID,Sender,Reciever,Content) VALUES (%s, '%s','%s','%s')", ID,sender,reciever, content);
        MySQLConnection sql = new MySQLConnection();
        return sql.executeSQL(SQLcom);
    }
    public static String showPvMessages(String sender,String reciever) throws Exception {
        StringBuilder sb = new StringBuilder();
        MySQLConnection sql = new MySQLConnection();
        String SQLcmd = String.format("Select pvmessages.Sender,pvmessages.Reciever,pvmessages.Content, pvmessages.dateAndTime FROM pvmessages WHERE pvmessages.Sender='%s' AND pvmessages.Reciever='%s' OR pvmessages.Sender='%s' AND pvmessages.Reciever='%s'",sender,reciever,reciever,sender);
        ResultSet rs = sql.executeQuery(SQLcmd);
        if (rs == null) {
            return "Not Found!";
        } else {
            while (rs.next()) {
                sb.append(rs.getString("Sender")).append(" : ").append(rs.getString("Content"));
                sb.append("\t\t\t").append(rs.getString("dateAndTime")).append("\n");
            }
        }
        return sb.toString();
    }
    public static boolean deleteMessages(String sender,String reciever ) throws Exception {
        String SQLcom = String.format("DELETE  FROM pvmessages WHERE pvmessages.Sender='%s' AND pvmessages.Reciever='%s' OR pvmessages.Sender='%s' AND pvmessages.Reciever='%s'",sender,reciever,reciever,sender);
        MySQLConnection sql = new MySQLConnection();
        return sql.executeSQL(SQLcom);
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

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getSender() {
        return sender;
    }

    public String getReciever() {
        return reciever;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
