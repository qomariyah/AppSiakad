package Tool;

import java.sql.*;
import javax.swing.JOptionPane;

public class KoneksiDB {
    
    public Connection getConnection() throws SQLException{
        Connection cnn;
        try{
            String server = "jdbc:mysql://localhost/dbsiakad_161530018";
            String drever = "com.mysql.jdbc.Driver";
            Class.forName(drever);
            cnn = DriverManager.getConnection(server, "root", "");
            return cnn;
            
        }catch(SQLException | ClassNotFoundException se){
            JOptionPane.showMessageDialog(null, "error koneksi database : "+ se);
            return null;
        }
    }
}