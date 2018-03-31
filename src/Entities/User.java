package Entities;

import Tool.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class User {
    
    public String vid_user, vnama_user, vpass, vlev_user;
    
    public Connection _Cnn;
    public KoneksiDB getCnn = new KoneksiDB();
    public String query;
    public boolean isUpdate;
    public PreparedStatement stat;
    public ResultSet res;
    public DefaultTableModel tbluser = new DefaultTableModel();
    public List<Object> list;
    
    public void select(){
        tbluser.setColumnIdentifiers(new Object[]{"ID. User", "Nama User", "Password", "Lev. User"});
        try {
            _Cnn    = getCnn.getConnection();
            query   = "select * from tbuser order by id_user asc";
            stat    = _Cnn.prepareStatement(query);
            res     = stat.executeQuery(query);
            list    = new ArrayList<>();
            while (res.next()) {
                vid_user   = res.getString("id_user");
                vnama_user = res.getString("nama_user");
                vpass      = res.getString("pass");
                vlev_user  = res.getString("lev_user");
                
                list.add(new Object[]{vid_user, vnama_user, vpass, vlev_user});
            }
            stat.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error method select() : " + ex);
        }
    }
    
    public void insert_update(){
        try {
            _Cnn = getCnn.getConnection();
            if (isUpdate == false) {
                query = "insert into tbuser values (?,?,admin,?)";
            } else {
                query = "update tbuser set nama_user=?, lev_user=?"
                        + " where id_user = '" + vid_user + "' ";
            }
            
            stat = _Cnn.prepareStatement(query);
            stat.setString(1, vid_user);
            stat.setString(2, vnama_user);
            stat.setString(3, vpass);
            stat.setString(4, vlev_user);
            stat.executeUpdate();
            stat.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error method insert_update() : " + ex);
        }
    }
    
    //menggunakan prepareStatement lebih cepat daripada statement.
    public void delete(String vid_user){
        try {
            _Cnn    = getCnn.getConnection();
            query   = "delete from tbuser where id_user ='" + vid_user + "' ";
            stat    = _Cnn.prepareStatement(query);
            stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
}
