/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.toedter.calendar.JTextFieldDateEditor;
import entity.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import util.AppointStatus;
import util.Convert;
import util.FillDetails;
import util.Generator;
import util.InitDictionary;
import util.Transfer;

/**
 *
 * @author LEGION
 */
public class UserAppoint extends javax.swing.JFrame implements FillDetails {

    private People user;
    private DefaultComboBoxModel cbxVacModel;
    private DefaultComboBoxModel cbxCenModel;
    private Map<String, Centre> centreDict;
    private Map<String, Vaccine> vacDictName;
    private Map<String, Vaccine> vacDictID;
    private Map<String, Citizen> czDict;
    private Map<String, NonCitizen> nczDict;
    private JTextFieldDateEditor dcEditor;
    private DefaultTableModel tblAppModel;
    private int pointer;
    private boolean cleared;

    /**
     * Creates new form UserAppoint
     */
    public UserAppoint() {
        initComponents();
        this.setLocationRelativeTo(null);
        czDict = InitDictionary.citizenDict();
        nczDict = InitDictionary.noncitizenDict();
        centreDict = InitDictionary.centreData();
        vacDictName = InitDictionary.vacData(true);
        vacDictID = InitDictionary.vacData(false);

        tblAppModel = (DefaultTableModel) tblAppoint.getModel();
        tblAppoint.setDefaultEditor(Object.class, null);

        cbxCenModel = (DefaultComboBoxModel) cbxCenID.getModel();
        for (String id: centreDict.keySet()) {
            cbxCenModel.addElement(id);
        }

        cbxVacModel = (DefaultComboBoxModel) cbxVac.getModel();
        for (String name: vacDictName.keySet()) {
            cbxVacModel.addElement(name);
        }
        LocalDate min = LocalDate.now().plusWeeks(2);
        LocalDate max = min.plusMonths(2);
        dcDate.setMaxSelectableDate(Convert.LocalDateToDate(max));
        dcDate.setMinSelectableDate(Convert.LocalDateToDate(min));
        dcEditor = (JTextFieldDateEditor) dcDate.getDateEditor();
        dcEditor.setEditable(false);
        lblAppIDData.setText("");
        lblStatusData.setText("");
        registerMode(false);

        Transfer.readAppoint(czDict, nczDict);
        this.user = czDict.get("IC000001");
        btnView.doClick();
        cleared = true;
    }

    public UserAppoint(People user) {
        initComponents();
        this.setLocationRelativeTo(null);
        czDict = InitDictionary.citizenDict();
        nczDict = InitDictionary.noncitizenDict();
        centreDict = InitDictionary.centreData();
        vacDictName = InitDictionary.vacData(true);
        vacDictID = InitDictionary.vacData(false);

        tblAppModel = (DefaultTableModel) tblAppoint.getModel();
        tblAppoint.setDefaultEditor(Object.class, null);

        cbxCenModel = (DefaultComboBoxModel) cbxCenID.getModel();
        for (String id: centreDict.keySet()) {
            cbxCenModel.addElement(id);
        }

        cbxVacModel = (DefaultComboBoxModel) cbxVac.getModel();
        for (String name: vacDictName.keySet()) {
            cbxVacModel.addElement(name);
        }
        LocalDate min = LocalDate.now().plusWeeks(2);
        LocalDate max = min.plusMonths(2);
        dcDate.setMaxSelectableDate(Convert.LocalDateToDate(max));
        dcDate.setMinSelectableDate(Convert.LocalDateToDate(min));
        dcEditor = (JTextFieldDateEditor) dcDate.getDateEditor();
        dcEditor.setEditable(false);
        lblAppIDData.setText("");
        lblStatusData.setText("");
        registerMode(false);

        Transfer.readAppoint(czDict, nczDict);
        if (user instanceof Citizen) {
            this.user = czDict.get(user.getID());
        }
        else {
            this.user = nczDict.get(user.getID());
        }
        btnView.doClick();
        cleared = true;
    }

    public void registerMode(boolean state) {
        cbxVac.setEnabled(state);
        cbxCenID.setEnabled(state);
        cbxHour.setEnabled(state);
        cbxMinute.setEnabled(state);
        lblStatus.setVisible(!state);
        lblStatusData.setVisible(!state);
        dcDate.setEnabled(state);
        btnOk.setVisible(state);
        btnCancel.setVisible(state);
        tblAppoint.setEnabled(!state);
        btnCancelApp.setEnabled(!state);
        btnView.setEnabled(!state);
    }

    @Override
    public void fillDetails() {
        if (pointer != -1) {
            lblAppIDData.setText(tblAppoint.getValueAt(pointer, 0).toString());
            cbxVac.setSelectedItem(tblAppoint.getValueAt(pointer, 1).toString());
            cbxCenID.setSelectedItem(tblAppoint.getValueAt(pointer, 2).toString());
            LocalDateTime datetime = Convert.StringToLocalDateTime((String) tblAppoint.getValueAt(pointer, 3), "dd/MM/yyyy HH:mm");
            LocalDate date = datetime.toLocalDate();
            LocalTime time = datetime.toLocalTime();
            cbxHour.setSelectedItem(String.format("%02d", time.getHour()));
            cbxMinute.setSelectedItem(String.format("%02d", time.getMinute()));
            dcDate.getDateEditor().setDate(Convert.LocalDateToDate(date));
            lblStatusData.setText(tblAppoint.getValueAt(pointer, 4).toString());
            cleared = false;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnMyAccount = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnCentre = new javax.swing.JButton();
        btnVac = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnVacStatus = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        tglRegister = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        cbxVac = new javax.swing.JComboBox<>();
        cbxCenID = new javax.swing.JComboBox<>();
        lblAppIDData = new javax.swing.JLabel();
        lblCenNameData = new javax.swing.JLabel();
        lblStreetData = new javax.swing.JLabel();
        lblCityData = new javax.swing.JLabel();
        cbxHour = new javax.swing.JComboBox<>();
        cbxMinute = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        dcDate = new com.toedter.calendar.JDateChooser();
        lblStatus = new javax.swing.JLabel();
        lblStatusData = new javax.swing.JLabel();
        btnCancelApp = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAppoint = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Appointment");

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        btnMyAccount.setText("My Account");
        btnMyAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMyAccountActionPerformed(evt);
            }
        });

        jButton2.setText("Appointment");
        jButton2.setEnabled(false);

        btnCentre.setText("Centre");
        btnCentre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCentreActionPerformed(evt);
            }
        });

        btnVac.setText("Vaccine");
        btnVac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVacActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnVacStatus.setText("Vaccination Status");
        btnVacStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVacStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMyAccount)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(btnCentre, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVac)
                .addGap(18, 18, 18)
                .addComponent(btnVacStatus)
                .addGap(18, 18, 18)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMyAccount)
                    .addComponent(jButton2)
                    .addComponent(btnCentre)
                    .addComponent(btnVac)
                    .addComponent(btnLogout)
                    .addComponent(btnVacStatus))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnView.setText("View");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        tglRegister.setText("Register");
        tglRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglRegisterActionPerformed(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Appointment ID");

        jLabel2.setText("Vaccine");

        jLabel3.setText("Centre ID");

        jLabel4.setText("Centre Name");

        jLabel5.setText("Centre Address");

        jLabel6.setText("Street");

        jLabel7.setText("City");

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel9.setText("Time");

        cbxCenID.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCenIDItemStateChanged(evt);
            }
        });

        lblAppIDData.setText("AXXXXXX");

        lblCenNameData.setText("name");

        lblStreetData.setText("street");

        lblCityData.setText("city");

        cbxHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "09", "10", "11", "12", "13", "14", "15" }));

        cbxMinute.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel8.setText("Date");

        lblStatus.setText("Status");

        lblStatusData.setText("status");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAppIDData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxVac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxCenID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dcDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cbxHour, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbxMinute, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 468, Short.MAX_VALUE))
                            .addComponent(lblStatusData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCenNameData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblStreetData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCityData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblAppIDData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxVac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbxCenID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblCenNameData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblStreetData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblCityData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbxHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStatus)
                            .addComponent(lblStatusData))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnOk)
                            .addComponent(btnCancel)))
                    .addComponent(dcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel3);

        btnCancelApp.setText("Cancel Appointment");
        btnCancelApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelAppActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tglRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelApp)
                        .addGap(18, 18, 18)
                        .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnView)
                    .addComponent(tglRegister)
                    .addComponent(btnCancelApp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblAppoint.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblAppoint.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblAppoint.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblAppoint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAppointMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAppoint);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxCenIDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCenIDItemStateChanged
        // TODO add your handling code here:
        String cenID = cbxCenID.getSelectedItem().toString();
        Centre ctr = centreDict.get(cenID);
        lblCenNameData.setText(ctr.getName());
        lblStreetData.setText(ctr.getAddr().getStreet());
        lblCityData.setText(ctr.getAddr().getCity());
    }//GEN-LAST:event_cbxCenIDItemStateChanged

    private void tglRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglRegisterActionPerformed
        // TODO add your handling code here:
        if (tglRegister.isSelected()) {
            lblAppIDData.setText(Generator.generateAppID());
            dcDate.getDateEditor().setDate(null);
            registerMode(true);
        }
        else {
            btnCancel.doClick();
        }
    }//GEN-LAST:event_tglRegisterActionPerformed

    private void btnCancelAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelAppActionPerformed
        // TODO add your handling code here:
        if (!cleared) {
            if (lblStatusData.getText().equals("Waiting")) {
                boolean okay = PopupDialog.confirm("Are you sure to cancel this appointment?");
                if (okay) {
                    List<Appointment> appList = user.getAppoint();
                    for (Appointment app: appList) {
                        if (app.getID().equals(lblAppIDData.getText())) {
                            appList.remove(app);
                            String cenID = cbxCenID.getSelectedItem().toString();
                            String vacName = cbxVac.getSelectedItem().toString();
                            List<VaccineStock> vStock = centreDict.get(cenID).getStockVaccines();
                            for (VaccineStock stock: vStock) {
                                if (stock.getName().equals(vacName)) {
                                    stock.setQuantity(stock.getQuantity() + 1);
                                    break;
                                }
                            }
                            Citizen[] czArr = czDict.values().toArray(new Citizen[0]);
                            NonCitizen[] nczArr = nczDict.values().toArray(new NonCitizen[0]);
                            Transfer.writeAppoint(czArr, nczArr);
                            Centre[] cenArr = centreDict.values().toArray(new Centre[0]);
                            Transfer.exportObject(cenArr, Centre.FILENAME);
                            break;
                        }
                    }
                }
            }
            else {
                PopupDialog.error("Unable to cancel completed appointment");
            }
        }
        else {
            PopupDialog.error("Please select an entry first");
        }
        btnView.doClick();
    }//GEN-LAST:event_btnCancelAppActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        lblAppIDData.setText("");
        lblStatusData.setText("");
        dcDate.getDateEditor().setDate(null);
        tglRegister.setSelected(false);
        registerMode(false);
        cleared = true;
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        String vacName = cbxVac.getSelectedItem().toString();
        String cenID = cbxCenID.getSelectedItem().toString();
        List<Appointment> appList = user.getAppoint();
        String feedback = null;
        if (appList.size() > 0) {
            Appointment first = appList.get(0);
            if (vacName.equals(first.getVaccine().getName())) {
                if (first.getStatus().equals(AppointStatus.COMPLETED)) {
                    if (appList.size() == first.getVaccine().getDose()) {
                        feedback = "Max dose reached";
                    }
                    else {
                        feedback = "Registration successful";
                    }
                }
                else {
                    feedback = "There is an appointment in progress";
                }
            }
            else {
                feedback = "Vaccine does not match previous appointment."
                        + "\nPrevious: " + first.getVaccine().getName();
            }
        }
        else {
            feedback = "Registration successful";
        }

        if (feedback.equals("Registration successful")) {
            Centre ctr = centreDict.get(cenID);
            for (VaccineStock stock: ctr.getStockVaccines()) {
                if (stock.getName().equals(vacName)) {
                    if (stock.getQuantity() > 0) {
                        stock.setQuantity(stock.getQuantity() - 1);
                    }
                    else {
                        feedback = "Vaccine out of stock in selected centre";
                    }
                    break;
                }
            }
        }

        if (feedback.equals("Registration successful")) {
            String hour = String.valueOf(cbxHour.getSelectedItem());
            String minute = String.valueOf(cbxMinute.getSelectedItem());
            String hm = hour + ":" + minute;
            LocalTime time = Convert.StringToLocalTime(hm, "HH:mm");
            LocalDate date = Convert.DateToLocalDate(dcDate.getDate());
            LocalDateTime dt = LocalDateTime.of(date, time);
            user.getAppoint().add(new Appointment(
                    lblAppIDData.getText(),
                    vacDictName.get(vacName),
                    centreDict.get(cenID),
                    dt
            ));
            Citizen[] czArr = czDict.values().toArray(new Citizen[0]);
            NonCitizen[] nczArr = nczDict.values().toArray(new NonCitizen[0]);
            Centre[] cenArr = centreDict.values().toArray(new Centre[0]);
            Transfer.writeAppoint(czArr, nczArr);
            Transfer.exportObject(cenArr, Centre.FILENAME);
            PopupDialog.notif(feedback);
            btnCancel.doClick();
        }
        else {
            PopupDialog.error(feedback);
        }
        btnView.doClick();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        tblAppModel.setRowCount(0);
        String[] colId = {
                "Appointment ID",
                "Vaccine Name",
                "Centre ID",
                "Date",
                "Status"
        };
        tblAppModel.setColumnIdentifiers(colId);
        List<Appointment> appList = user.getAppoint();
        for (Appointment app: appList) {
            Object[] temp = {
                app.getID(),
                app.getVaccine().getName(),
                app.getCentre().getCentreID(),
                Convert.LocalDateTimeToString(app.getDateTime(), "dd/MM/yyyy HH:mm"),
                app.getStatus(),
            };
            tblAppModel.addRow(temp);
        }
    }//GEN-LAST:event_btnViewActionPerformed

    private void tblAppointMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAppointMouseClicked
        // TODO add your handling code here:
        pointer = tblAppoint.getSelectedRow();
        fillDetails();
    }//GEN-LAST:event_tblAppointMouseClicked

    private void btnMyAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMyAccountActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new UserAccount(user).setVisible(true);
    }//GEN-LAST:event_btnMyAccountActionPerformed

    private void btnCentreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCentreActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new UserCentre(user).setVisible(true);
    }//GEN-LAST:event_btnCentreActionPerformed

    private void btnVacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVacActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new UserVaccine(user).setVisible(true);
    }//GEN-LAST:event_btnVacActionPerformed

    private void btnVacStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVacStatusActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new UserStatus(user).setVisible(true);
    }//GEN-LAST:event_btnVacStatusActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new LoginForm().setVisible(true);
    }//GEN-LAST:event_btnLogoutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserAppoint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserAppoint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserAppoint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserAppoint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserAppoint().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCancelApp;
    private javax.swing.JButton btnCentre;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMyAccount;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnVac;
    private javax.swing.JButton btnVacStatus;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox<String> cbxCenID;
    private javax.swing.JComboBox<String> cbxHour;
    private javax.swing.JComboBox<String> cbxMinute;
    private javax.swing.JComboBox<String> cbxVac;
    private com.toedter.calendar.JDateChooser dcDate;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAppIDData;
    private javax.swing.JLabel lblCenNameData;
    private javax.swing.JLabel lblCityData;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusData;
    private javax.swing.JLabel lblStreetData;
    private javax.swing.JTable tblAppoint;
    private javax.swing.JToggleButton tglRegister;
    // End of variables declaration//GEN-END:variables
}
