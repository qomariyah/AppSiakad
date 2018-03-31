package Form;

import Tool.KoneksiDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrUser extends javax.swing.JInternalFrame {

    Connection _Cnn;
    KoneksiDB getCnn = new KoneksiDB();
    
    String vid_user, vnama_user, vpass, vlev_user;
    String sqlselect, sqlinsert, sqldelete;
    DefaultTableModel tbluser;

    public IfrUser() {
        initComponents();
        
        clearInput();
        disableInput();
        setTabelUser();
        showDataUser();
    }
    
    private void clearInput(){
        cmbLevUser.setSelectedIndex(0);
        txtIdUser.setText("");
        txtNamaUser.setText("");
        btnTambah.setText("Tambah");
        btnSimpan.setText("Simpan");
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Image/trans-add.png")));
    }
    
    private void disableInput(){
        cmbLevUser.setEnabled(false);
        txtIdUser.setEnabled(false);
        txtNamaUser.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void enableInput(){
        cmbLevUser.setEnabled(true);
        txtIdUser.setEnabled(true);
        txtNamaUser.setEnabled(true);
        btnSimpan.setEnabled(true);
    }
    
    private void setTabelUser(){
        String[] kolom1 = {"ID. User", "Nama User", "Pasword", "Level User"};
        tbluser = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[] {
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
            };
            
            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }
            
            public boolean isCellEditable (int row, int col){
                int cola = tbluser.getColumnCount();
                return (col < cola) ? false : true;
            }    
        };
        tbDataUser.setModel(tbluser);
        tbDataUser.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataUser.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbDataUser.getColumnModel().getColumn(2).setPreferredWidth(200);
        tbDataUser.getColumnModel().getColumn(3).setPreferredWidth(150);
    }
    
    private void clearTabelUser(){
        int row = tbluser.getRowCount();
        for (int i = 0; i < row; i++) {         
            tbluser.removeRow(0);
        }
    }
    
    private void showDataUser(){
        try {
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbuser order by id_user asc";
            
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            clearTabelUser();
            while (res.next()) {
                vid_user    = res.getString("id_user");
                vnama_user  = res.getString("nama_user");
                vpass       = res.getString("pass");
                vlev_user   = res.getString("lev_user");
                
                Object[] data = {vid_user, vnama_user, vpass, vlev_user};
                tbluser.addRow(data);
            }
            lblRecord.setText("Record : " + tbDataUser.getRowCount());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error method showDataUser() : "+ex);
        }
    }
    
    private void aksiSimpan(){
        if (cmbLevUser.getSelectedIndex()<=0) {
            JOptionPane.showMessageDialog(this, "Anda belum memilih level user",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbLevUser.requestFocus(true);
        } else if (txtIdUser.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Anda belum mengisi ID. User",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtIdUser.requestFocus(true);
        } else if (txtNamaUser.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Anda belum mengisi Nama User",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtNamaUser.requestFocus(true);
        } else {
            vid_user    = txtIdUser.getText();
            vnama_user  = txtNamaUser.getText();
            vlev_user   = cmbLevUser.getSelectedItem().toString();
            
            try {
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                if (btnSimpan.getText().equals("Simpan")) {
                    sqlinsert = "insert into tbuser set id_user='"+ vid_user +"', nama_user='"+ vnama_user +"', "
                            + "pass = md5('admin'), lev_user='"+ vlev_user + "' ";
                } else if (btnSimpan.getText().equals("Ubah")) {
                    sqlinsert = "update tbuser set "
                            + "nama_user='" + vnama_user + "', pass='" + vpass + "', lev_user='" + vlev_user + "' "
                            + "where id_user='" + vid_user + "'";
                }
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
                
                clearInput();
                disableInput();
                showDataUser();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error method aksiSimpan() : " + ex);
            }
        }
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah anda akan menghapus data ini? kode " + vid_user,
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (jawab == JOptionPane.YES_OPTION) {
            try {
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqldelete = "delete from tbuser where id_user='"+ vid_user +"' ";
                Statement stat =  _Cnn.createStatement();
                stat.executeUpdate(sqldelete);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
                showDataUser();
                clearInput();
                disableInput();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error method aksiHapus() : " + ex);
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cmbLevUser = new javax.swing.JComboBox<>();
        txtNamaUser = new javax.swing.JTextField();
        txtIdUser = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataUser = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Form User");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Admin-Schoolar-Icon.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Input Data"));
        jPanel1.setOpaque(false);

        cmbLevUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "Administrator", "Staff Kemahasiswaan" }));
        cmbLevUser.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Level User :"));
        cmbLevUser.setOpaque(false);

        txtNamaUser.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Nama User :"));
        txtNamaUser.setOpaque(false);
        txtNamaUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaUserActionPerformed(evt);
            }
        });

        txtIdUser.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "ID. User :"));
        txtIdUser.setOpaque(false);
        txtIdUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbLevUser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNamaUser)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbLevUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNamaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
        jPanel2.setOpaque(false);

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/trans-add.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/save-black.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/trans-hapus.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tabel Data User : Klik 2x untuk mengubah/menghapus data"));

        tbDataUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbDataUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID. User", "Nama User", "Password", "Lev. User"
            }
        ));
        tbDataUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataUser);

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logo.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Form User");

        jLabel4.setText("Form ini digunakan untuk mengolah data user");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNamaUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaUserActionPerformed
        aksiSimpan();
    }//GEN-LAST:event_txtNamaUserActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if (btnTambah.getText().equals("Tambah")) {
            clearInput();
            enableInput();
            btnTambah.setText("Batal");
            cmbLevUser.requestFocus(true);
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Image/btn-delete.png")));
        } else if (btnTambah.getText().equals("Batal")) {
            clearInput();
            disableInput();
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Image/trans-add.png")));
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        aksiSimpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if (txtIdUser.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Informasi",
                    "Anda belum meilih data yang akan dihapus", JOptionPane.INFORMATION_MESSAGE);
        } else {
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataUserMouseClicked
        if (evt.getClickCount() == 2) {
            int row = tbDataUser.getSelectedRow();
            vid_user    = tbDataUser.getValueAt(row, 0).toString();
            vnama_user  = tbDataUser.getValueAt(row, 1).toString();
            vlev_user   = tbDataUser.getValueAt(row, 3).toString();
                        
            txtIdUser.setText(vid_user);
            txtNamaUser.setText(vnama_user);
            cmbLevUser.setSelectedItem(vlev_user);
            enableInput();
            txtIdUser.setEditable(false);
            btnHapus.setEnabled(true);
            btnSimpan.setText("Ubah");
        }
    }//GEN-LAST:event_tbDataUserMouseClicked

    private void txtIdUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdUserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbLevUser;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataUser;
    private javax.swing.JTextField txtIdUser;
    private javax.swing.JTextField txtNamaUser;
    // End of variables declaration//GEN-END:variables

}
