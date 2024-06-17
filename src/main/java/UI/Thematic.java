/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package UI;


import Classes.ChuyenDe;
import Daos.ChuyenDeDao;
import Utils.Auth;
import Utils.Msgbox;
import Utils.XImage;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author DELL
 */
public class Thematic extends javax.swing.JInternalFrame {
    ChuyenDeDao dao = new ChuyenDeDao();
    JFileChooser fileChooser = new JFileChooser();
    int row = 0;
    /**
     * Creates new form ChuyenDe
     */
    public Thematic() {
        initComponents();
        fillTable();
        updateStatus();
    }
    

    public void fillTable(){
        DefaultTableModel model = (DefaultTableModel) tblChuyenDe.getModel();
        model.setRowCount(0);
        try{
            List<ChuyenDe> list = dao.selectAll();
            for(ChuyenDe cd : list){
                Object[] row = {
                    cd.getMaCD(),
                    cd.getTenCD(),
                    cd.getHocPhi(),
                    cd.getThoiLuong(), 
                    cd.getHinh()
                };
                model.addRow(row);
            }
        }catch(Exception e){
            Msgbox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    void chonAnh(){
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName());
            lblAnh.setIcon(icon);
            lblAnh.setToolTipText(file.getName());
        }
    }
    
    void setForm(ChuyenDe model){
        txtMaCD.setText(model.getMaCD());
        txtTenCD.setText(model.getTenCD());
        txtThoiLuong.setText(String.valueOf(model.getThoiLuong()));
        txtHocPhi.setText(String.valueOf(model.getHocPhi()));
        txtMoTa.setText(model.getMoTa());
        String hinh = model.getHinh();
        if (hinh != null && !hinh.isEmpty()) {
            lblAnh.setToolTipText(model.getHinh());
            lblAnh.setIcon(XImage.read(model.getHinh()));
        } else {
            lblAnh.setIcon(null);
            lblAnh.setToolTipText("");
        }
        
    }
    
    ChuyenDe getForm(){
        ChuyenDe cd = new ChuyenDe();
        cd.setMaCD(txtMaCD.getText());
        cd.setTenCD(txtTenCD.getText());
        cd.setThoiLuong(Integer.parseInt(txtThoiLuong.getText()));
        cd.setHocPhi(Double.parseDouble(txtHocPhi.getText()));
        cd.setMoTa(txtMoTa.getText());
        cd.setHinh(lblAnh.getToolTipText());
        return cd;
    }
    
    
    void edit(){
        try{
            String maCD = (String) tblChuyenDe.getValueAt(this.row, 0);
            ChuyenDe cd = dao.selectById(maCD);
            if(cd != null){
                setForm(cd);
                updateStatus();
                tabs.setSelectedIndex(0);               
            }
        }catch(Exception e){
            Msgbox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void updateStatus(){
        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblChuyenDe.getRowCount()-1;
        // trang thái form
        txtMaCD.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        
        btnFirst.setEnabled(edit&&!first);
        btnPrev.setEnabled(edit&&!first);
        btnNext.setEnabled(edit&&!last);
        btnLast.setEnabled(edit&&!last);
    }
    
    void clearForm() {        
        this.setForm(new ChuyenDe());
        this.updateStatus();
        this.row = -1;
        updateStatus();
    }
    
    void insert() {
        ChuyenDe cd = getForm();
        try {
            dao.insert(cd);
            fillTable();
            clearForm();
            Msgbox.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            Msgbox.alert(this, "Thêm mới thất bại!");
        }
    }
    
    void update() {
        ChuyenDe cd = getForm();
        try {
            dao.update(cd);
            fillTable();
            Msgbox.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            Msgbox.alert(this, "Cập nhật thất bại!");
        }
    }
    //nhân viên tạo chuyên đề thì có thể xoá chuyên đề đc và cả trưởng phòng chú ý
    void delete() {
        if (!Auth.isManager()) {
            Msgbox.alert(this, "You're not authorized to delete employee!");
        } else {
            String id = txtMaCD.getText();
            if (Msgbox.confirm(this, "Do you want to delete this subject?")) {
                try {
                    dao.delete(id);
                    fillTable();
                    clearForm();
                    Msgbox.alert(this, "Delete sucessfully!");
                } catch (Exception e) {
                    Msgbox.alert(this, "Delete unsucessfully!");
                }
            }
        }
    }
    
    void fisrt(){
        row = 0;
        edit();
    }
    
    void prev(){
        if(row > 0){
            row--;
            edit();
        }
    }
    
    void next(){
        if(row < tblChuyenDe.getRowCount()-1){
            row++;
            edit();
        }
    }
    
    void last(){
        row = tblChuyenDe.getRowCount()-1;
        edit();       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblChuyenDe = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        lblHinhLogo = new javax.swing.JLabel();
        lblAnh = new javax.swing.JLabel();
        lblMaCD = new javax.swing.JLabel();
        txtMaCD = new javax.swing.JTextField();
        lblTenCD = new javax.swing.JLabel();
        txtTenCD = new javax.swing.JTextField();
        lblThoiLuong = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        lblHocPhi = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
        lblMoTa = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextPane();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChuyenDe = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        lblChuyenDe.setText("QUẢN LÝ CHUYÊN ĐỀ");

        lblHinhLogo.setText("Hình logo");

        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblAnhMousePressed(evt);
            }
        });

        lblMaCD.setText("Mã chuyên đề");

        lblTenCD.setText("Tên chuyên đề");

        lblThoiLuong.setText("Thời lượng(giờ)");

        lblHocPhi.setText("Học phí");

        lblMoTa.setText("Mô tả chuyên đề");

        jScrollPane2.setViewportView(txtMoTa);

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblMoTa)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblHinhLogo))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblMaCD)
                                .addComponent(lblTenCD)
                                .addComponent(lblThoiLuong)
                                .addComponent(lblHocPhi)
                                .addComponent(txtMaCD, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(txtTenCD)
                                .addComponent(txtThoiLuong)
                                .addComponent(txtHocPhi)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnSua)
                            .addGap(18, 18, 18)
                            .addComponent(btnXoa)
                            .addGap(18, 18, 18)
                            .addComponent(btnMoi)
                            .addGap(18, 18, 18)
                            .addComponent(btnFirst)
                            .addGap(18, 18, 18)
                            .addComponent(btnPrev)
                            .addGap(18, 18, 18)
                            .addComponent(btnNext)
                            .addGap(18, 18, 18)
                            .addComponent(btnLast))))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHinhLogo)
                    .addComponent(lblMaCD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtMaCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTenCD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblThoiLuong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblHocPhi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lblMoTa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        tabs.addTab("CẬP NHẬT", jPanel2);

        tblChuyenDe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "MÃ CD", "TÊN CD", "HỌC PHÍ", "THỜI LƯỢNG", "HÌNH"
            }
        ));
        tblChuyenDe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblChuyenDeMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblChuyenDe);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("DANH SÁCH", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblChuyenDeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChuyenDeMousePressed
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            this.row =tblChuyenDe.rowAtPoint(evt.getPoint());
            edit();
        }
    }//GEN-LAST:event_tblChuyenDeMousePressed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        fisrt();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void lblAnhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMousePressed
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            chonAnh();
        }
    }//GEN-LAST:event_lblAnhMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblChuyenDe;
    private javax.swing.JLabel lblHinhLogo;
    private javax.swing.JLabel lblHocPhi;
    private javax.swing.JLabel lblMaCD;
    private javax.swing.JLabel lblMoTa;
    private javax.swing.JLabel lblTenCD;
    private javax.swing.JLabel lblThoiLuong;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblChuyenDe;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtMaCD;
    private javax.swing.JTextPane txtMoTa;
    private javax.swing.JTextField txtTenCD;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables

    
}
