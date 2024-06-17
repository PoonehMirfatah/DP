package Models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements Serializable {
    private String username;
    private int ID;

    public User(String name) throws SQLException, ClassNotFoundException {
        this.username=name;

    }
    public  int getMaxID() throws SQLException, ClassNotFoundException {
        String sqlCmd="Select MAX(UserID) from allusers";
        MySQLConnection sql=new MySQLConnection();
        ResultSet rs=sql.executeQuery(sqlCmd);

        if(rs.next()){
            return rs.getInt(1);
        }else {
            return 0;
        }
    }
    public boolean insertUser( ) throws Exception {
        this.ID=getMaxID()+1;
        String SQLcom = String.format("INSERT INTO allusers (UserID,UserName) VALUES ( %s,'%s')", ID,username);
        MySQLConnection sql = new MySQLConnection();
        return sql.executeSQL(SQLcom);
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
