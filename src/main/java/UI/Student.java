/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package UI;

import Classes.ChuyenDe;
import Classes.HocVien;
import Classes.KhoaHoc;
import Classes.NguoiHoc;
import Daos.ChuyenDeDao;
import Daos.HocVienDao;
import Daos.KhoaHocDao;
import Daos.NguoiHocDao;
import Utils.Auth;
import Utils.Msgbox;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class Student extends javax.swing.JInternalFrame {

    ChuyenDeDao cdDAO = new ChuyenDeDao();
    KhoaHocDao khDAO = new KhoaHocDao();
    NguoiHocDao nhDAO = new NguoiHocDao();
    HocVienDao hvDAO = new HocVienDao();
    /**
     * Creates new form HocVien
     */
    public Student() {
        initComponents();
        fillComboboxChuyenDe();
    }

    void fillComboboxChuyenDe(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenDe.getModel();
        model.removeAllElements();
        try{
            List<ChuyenDe> list = cdDAO.selectAll();
            for(ChuyenDe cd : list){
                model.addElement(cd);
            }
            fillComboboxKhoaHoc();
        }catch(Exception e){
            Msgbox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboboxKhoaHoc(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhoaHoc.getModel();
        model.removeAllElements();
        try{
            ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
            if(chuyenDe != null){
                List<KhoaHoc> list = khDAO.selectKhoaHocByChuyenDe(chuyenDe.getMaCD());
                for(KhoaHoc khoaHoc : list){
                    model.addElement(khoaHoc);
                }
            }
            fillTableHocVien();
        }catch(Exception e){
            Msgbox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    public void fillTableNguoiHoc(){
        DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
        model.setRowCount(0);
        try{
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            if(kh != null){
                System.out.println("MaKH:" + kh.getMaKH());
                String keyword = txtTimKiem.getText();
                List<NguoiHoc> list = nhDAO.selectNotInCourse(kh.getMaKH(), keyword);
                for(NguoiHoc nh : list){
                    Object[] row = {
                        nh.getMaNH(),
                        nh.getHoTen(),
                        nh.getNgaySinh(),
                        nh.isGioiTinh() ? "Nam" : "Nữ",
                        nh.getDienThoai(),
                        nh.getEmail()
                    };
                    model.addRow(row);
                }
            }
        }catch(Exception e){
             Msgbox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    public void fillTableHocVien(){
        DefaultTableModel model = (DefaultTableModel) tblHocVien.getModel();
        model.setRowCount(0);
        try{
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            if(kh != null){
                System.out.println("MaKH:" + kh.getMaKH());
                List<HocVien> list = hvDAO.selectByKhoaHoc(kh.getMaKH());
                System.out.println("list:"+list.size());
                for(int i = 0; i<list.size();i++){
                    HocVien hv = list.get(i);
                    String hoTen = nhDAO.selectById(hv.getMaNH()).getHoTen();
                    Object[] row = {
                        i + 1, hv.getMaHV(),
                        hv.getMaNH(),
                        hoTen,
                        hv.getDiem()
                };
                    model.addRow(row);
                }
            }
            fillTableNguoiHoc();
        }catch(Exception e){
             Msgbox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void addHocVien(){
        KhoaHoc khoaHoc = (KhoaHoc)cboKhoaHoc.getSelectedItem();
        for(int row : tblNguoiHoc.getSelectedRows()){
            HocVien hv = new HocVien();
            hv.setMaKH(khoaHoc.getMaKH());
            hv.setDiem(0);
            hv.setMaNH((String)tblNguoiHoc.getValueAt(row, 0));
            System.out.println("=>"+hv.getMaKH()+"-"+hv.getMaNH()+"-"+hv.getDiem());
            hvDAO.insert(hv);
        }
        fillTableHocVien();
        tabs.setSelectedIndex(0);
    }
    
    void removeHocVien(){
        if(!Auth.isManager()){
            Msgbox.alert(this, "Bạn không đủ quyền để xoá học viên");
        }else{
            if(Msgbox.confirm(this, "Bạn muốn xoá các học viên được chọn")){
                for(int row:tblHocVien.getSelectedRows()){
                    int maHV = (Integer)tblHocVien.getValueAt(row, 1);
                    hvDAO.delete(maHV);
                }
                fillTableHocVien();
            }
        }
    }
    
    void updateDiem(){
        for(int i = 0; i < tblHocVien.getRowCount();i++){
            int maHV = (Integer)tblHocVien.getValueAt(i, 1);
            HocVien hocVien = hvDAO.selectById(maHV);
            hocVien.setDiem(Double.parseDouble(tblHocVien.getValueAt(i, 4).toString()));
            hvDAO.update(hocVien);
        }
        Msgbox.alert(this, "Cập nhật điểm thành công");
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
        cboChuyenDe = new javax.swing.JComboBox<>();
        lblKhoaHoc = new javax.swing.JLabel();
        cboKhoaHoc = new javax.swing.JComboBox<>();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHocVien = new javax.swing.JTable();
        btnSuaDiem = new javax.swing.JButton();
        btnXoaHV = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTimKiem = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        btnThemHV = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        lblChuyenDe.setText("CHUYÊN ĐỀ");

        cboChuyenDe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenDeActionPerformed(evt);
            }
        });

        lblKhoaHoc.setText("KHOÁ HỌC");

        cboKhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblHocVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "TT", "MẪ HV", "MÃ NH", "HỌ VÀ TÊN", "ĐIỂM"
            }
        ));
        jScrollPane1.setViewportView(tblHocVien);

        btnSuaDiem.setText("Cập nhật điểm");
        btnSuaDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaDiemActionPerformed(evt);
            }
        });

        btnXoaHV.setText("Xoá khỏi khoá học");
        btnXoaHV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnXoaHV)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaDiem))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuaDiem)
                    .addComponent(btnXoaHV))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        tabs.addTab("HỌC VIÊN", jPanel1);

        lblTimKiem.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 170, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MÃ HV", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "ĐIỆN THOẠI", "EMAIL"
            }
        ));
        jScrollPane2.setViewportView(tblNguoiHoc);

        btnThemHV.setText("Thêm vào khoá học");
        btnThemHV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTimKiem)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThemHV))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTimKiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThemHV)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tabs.addTab("NGƯỜI HỌC", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblChuyenDe)
                            .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(112, 112, 112)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblKhoaHoc)
                            .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChuyenDe)
                    .addComponent(lblKhoaHoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenDeActionPerformed
        // TODO add your handling code here:
        fillComboboxKhoaHoc();
    }//GEN-LAST:event_cboChuyenDeActionPerformed

    private void btnThemHVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHVActionPerformed
        // TODO add your handling code here:
        addHocVien();
    }//GEN-LAST:event_btnThemHVActionPerformed

    private void btnXoaHVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHVActionPerformed
        // TODO add your handling code here:
        removeHocVien();
    }//GEN-LAST:event_btnXoaHVActionPerformed

    private void btnSuaDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaDiemActionPerformed
        // TODO add your handling code here:
        updateDiem();
    }//GEN-LAST:event_btnSuaDiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSuaDiem;
    private javax.swing.JButton btnThemHV;
    private javax.swing.JButton btnXoaHV;
    private javax.swing.JComboBox<String> cboChuyenDe;
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblChuyenDe;
    private javax.swing.JLabel lblKhoaHoc;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblHocVien;
    private javax.swing.JTable tblNguoiHoc;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
