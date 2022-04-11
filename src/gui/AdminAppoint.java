/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.toedter.calendar.JTextFieldDateEditor;
import entity.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import util.Generator;
import util.AppointStatus;
import util.Convert;
import util.Editable;
import util.Transfer;
import util.InitDictionary;

/**
 *
 * @author LEGION
 */
public class AdminAppoint extends javax.swing.JFrame implements Editable {

    private DefaultComboBoxModel cbxICPModel;
    private DefaultComboBoxModel cbxVacModel;
    private DefaultComboBoxModel cbxCenModel;
    private DefaultComboBoxModel cbxStatModel;
    private DefaultTableModel tblAppModel;
    private DefaultListModel lstSearchModel;
    private Map<String, Centre> centreDict;
    private Map<String, Vaccine> vacDataName;
    private Map<String, Citizen> czDict;
    private Map<String, NonCitizen> nczDict;
    private boolean cleared;
    private int pointer;
    private int last;
    private List<Integer> searchIdx;

    /**
     * Creates new form AdminAppoint
     */
    public AdminAppoint() {
        initComponents();
        this.setLocationRelativeTo(null);

        cleared = true;
        pointer = 0;
        searchIdx = new ArrayList<>();
        
        btnOK.setVisible(false);
        btnCancel.setVisible(false);
        btnClear.setVisible(false);

        cbxICPModel = (DefaultComboBoxModel) cbxICPNum.getModel();
        cbxVacModel = (DefaultComboBoxModel) cbxVaccine.getModel();
        cbxCenModel = (DefaultComboBoxModel) cbxCentre.getModel();
        cbxStatModel = (DefaultComboBoxModel) cbxStatus.getModel();
        tblAppModel = (DefaultTableModel) tblAppoint.getModel();
        tblAppoint.setDefaultEditor(Object.class, null);
        lstSearch.setModel(new DefaultListModel());
        lstSearchModel = (DefaultListModel) lstSearch.getModel();

        // Set JDateChooser date range and disable editting
        LocalDate min = LocalDate.now().plusWeeks(2);
        LocalDate max = min.plusMonths(2);
        dcDate.setMaxSelectableDate(Convert.LocalDateToDate(max));
        dcDate.setMinSelectableDate(Convert.LocalDateToDate(min));
        JTextFieldDateEditor dcEditor =
                (JTextFieldDateEditor) dcDate.getDateEditor();
        dcEditor.setEditable(false);

        // Add value to Status combo box
        for (AppointStatus s: AppointStatus.values()) {
            cbxStatModel.addElement(s);
        }

        // Add value to Vaccine combo box
        vacDataName = InitDictionary.vacData(true);
        if (vacDataName.size() > 0) {
            for (String name: vacDataName.keySet()) {
                cbxVacModel.addElement(name);
            }
        }

        // Add value to Centre combo box
        centreDict = InitDictionary.centreData();
        if (centreDict.size() > 0) {
            for (Centre centre: centreDict.values()) {
                cbxCenModel.addElement(
                        centre.getName() + " ("
                        + centre.getCentreID() + ")"
                );
            }
        }

        czDict = InitDictionary.citizenDict();
        nczDict = InitDictionary.noncitizenDict();
        Transfer.readAppoint(czDict, nczDict);

        cbxType.setSelectedItem("Citizen");
        btnClear.doClick();
        enableDisablePanel(false);
    }

    @Override
    public void enableDisablePanel(boolean state) {
        cbxType.setEnabled(state);
        cbxICPNum.setEnabled(state);
        cbxVaccine.setEnabled(state);
        cbxHour.setEnabled(state);
        cbxMinute.setEnabled(state);
        cbxCentre.setEnabled(state);
        dcDate.setEnabled(state);
        txtApp.setEnabled(state);
        cbxStatus.setEnabled(state);
        btnOK.setVisible(state);
        btnCancel.setVisible(state);
        btnClear.setVisible(state);
    }

    @Override
    public void enableDisableButton(boolean state) {
        tglAdd.setEnabled(state);
        tglModify.setEnabled(state);
        tglRemove.setEnabled(state);
        btnSearch.setEnabled(state);
        btnView.setEnabled(state);
        lstSearch.setEnabled(state);
        tblAppoint.setEnabled(state);
        cbxView.setEnabled(state);
        btnOK.setVisible(state);
        btnCancel.setVisible(state);
        btnClear.setVisible(state);
    }

    @Override
    public void write() {
        String role = (String) cbxType.getSelectedItem();
        String uID = (String) cbxICPNum.getSelectedItem();
        String aID = txtApp.getText();
        String vacName = (String) cbxVaccine.getSelectedItem();
        String cenID = (String) cbxCentre.getSelectedItem();
        cenID = cenID.split(" \\(")[1];
        cenID = cenID.substring(0, cenID.length()-1);
        String hour = String.valueOf(cbxHour.getSelectedItem());
        String minute = String.valueOf(cbxMinute.getSelectedItem());
        String hm = hour + ":" + minute;
        LocalTime time = Convert.StringToLocalTime(hm, "HH:mm");
        LocalDate date = Convert.DateToLocalDate(dcDate.getDate());
        LocalDateTime dt = LocalDateTime.of(date, time);

        List<Appointment> appList = null;
        
        if (role.equals("Citizen")) {
             appList = czDict.get(uID).getAppoint();
        }
        else {
             appList = nczDict.get(uID).getAppoint();
        }
        List<VaccineStock> vStock = centreDict.get(cenID).getStockVaccines();

        if (tglModify.isSelected()) {
            List<VaccineStock> oldStock = null;
            Vaccine oldVac = null;
            for (Appointment app: appList) {
                if (app.getID().equals(aID)) {
                    oldVac = app.getVaccine();
                    oldStock = app.getCentre().getStockVaccines();
                    app.setCentre(centreDict.get(cenID));
                    app.setDateTime(dt);
                    app.setID(aID);
                    app.setVaccine(vacDataName.get(vacName));
                    break;
                }
            }
            for (VaccineStock stock: oldStock) {
                if (stock.getID().equals(oldVac.getID())) {
                    stock.setQuantity(stock.getQuantity() + 1);
                }
            }
            for (VaccineStock stock: vStock) {
                if (stock.getID().equals(vacDataName.get(vacName).getID())) {
                    stock.setQuantity(stock.getQuantity() - 1);
                }
            }
        }
        if (tglAdd.isSelected()) {
            appList.add(new Appointment(
                    aID,
                    vacDataName.get(vacName),
                    centreDict.get(cenID),
                    dt
            ));
            for (VaccineStock stock: vStock) {
                if (stock.getID().equals(vacDataName.get(vacName).getID())) {
                    if (stock.getQuantity() > 0) {
                        stock.setQuantity(stock.getQuantity() - 1);
                    }
                }
            }
        }
        if (tglRemove.isSelected()) {
            for (Appointment app: appList) {
                if (app.getID().equals(aID)) {
                    appList.remove(app);
                    break;
                }
            }
            if (cbxStatus.getSelectedItem().toString().equals("Waiting")) {
                for (VaccineStock s: vStock) {
                    if (s.getName().equals(cbxVaccine.getSelectedItem())) {
                        s.setQuantity(s.getQuantity() + 1);
                    }
                }
            }
        }

        Citizen[] czArr = czDict.values().toArray(new Citizen[czDict.values().size()]);
        NonCitizen[] nczArr = nczDict.values().toArray(new NonCitizen[nczDict.values().size()]);
        Transfer.writeAppoint(czArr, nczArr);

        Centre[] centres = centreDict.values().toArray(new Centre[centreDict.values().size()]);
        Transfer.exportObject(centres, Centre.FILENAME);
    }

    public String check() {
        if (
                cbxVaccine.getSelectedIndex() == -1 ||
                cbxHour.getSelectedIndex() == -1 ||
                cbxMinute.getSelectedIndex() == -1 ||
                cbxCentre.getSelectedIndex() == -1
                ) {
            return "Please fill in all details";
        }
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        String DOB = null;
        boolean validDOB = false;
        try {
            DOB = fmt.format(dcDate.getDate());
            validDOB = true;
        }
        catch (NullPointerException e) {
            validDOB = false;
        }
        if (!validDOB) return "Please fill in all details";

        if (cbxICPNum.getSelectedItem().equals("")) {
            return "Invalid User ID";
        }

        String cenID = cbxCentre.getSelectedItem().toString().split(" \\(")[1];
        cenID = cenID.substring(0, cenID.length()-1);

        String vacName = cbxVaccine.getSelectedItem().toString();

        List<VaccineStock> vStock = centreDict.get(cenID).getStockVaccines();
        for (VaccineStock s: vStock) {
            if (s.getName().equals(vacName)) {
                if (s.getQuantity() == 0 && tglAdd.isSelected()) {
                    return "Vaccine out of stock in selected centre";
                }
                if (tglModify.isSelected()) {
                    if (tblAppoint.getValueAt(last, 3).equals(cenID) &&
                            tblAppoint.getValueAt(last, 2).equals(vacName)) {
                    }
                    else {
                        if (s.getQuantity() == 0) {
                            return "Vaccine out of stock in selected centre";
                        }
                    }
                }
            }
        }

        String ICP = cbxICPNum.getSelectedItem().toString();
        List<Appointment> appList = null;
        if (cbxType.getSelectedItem().equals("Citizen")) {
            appList = czDict.get(ICP).getAppoint();
        }
        else {
            appList = nczDict.get(ICP).getAppoint();
        }
        if (tglAdd.isSelected()) {
            if (appList.size() > 0) {
                Appointment first = appList.get(0);
                if (vacName.equals(first.getVaccine().getName())) {
                    if (first.getStatus().equals(AppointStatus.COMPLETED)) {
                        if (appList.size() == first.getVaccine().getDose()) {
                            return "Max dose reached";
                        }
                        return null;
                    }
                    return "There is an appointment in progress";
                }
                return "Vaccine does not match previous appointment."
                        + "\nPrevious: " + first.getVaccine().getName();
            }
        }
        return null;
    }

    @Override
    public void fillDetails() {
        // pointer == -1 when the table is disabled but clicked
        if (pointer != -1) {
            cbxType.setSelectedItem(cbxView.getSelectedItem());
            cbxICPNum.setSelectedItem(tblAppoint.getValueAt(pointer, 0));
            txtApp.setText((String) tblAppoint.getValueAt(pointer, 1));
            cbxVaccine.setSelectedItem(tblAppoint.getValueAt(pointer, 2));
            String cenID = (String) tblAppoint.getValueAt(pointer, 3);
            String cenName = centreDict.get(cenID).getName();
            cbxCentre.setSelectedItem(cenName + " (" + cenID + ")");
            LocalDateTime datetime =
                    Convert.StringToLocalDateTime(
                            (String) tblAppoint.getValueAt(pointer, 4),
                            "dd/MM/yyyy HH:mm"
                    );
            LocalDate date = datetime.toLocalDate();
            LocalTime time = datetime.toLocalTime();
            cbxHour.setSelectedItem(String.format("%02d", time.getHour()));
            cbxMinute.setSelectedItem(String.format("%02d", time.getMinute()));
            dcDate.getDateEditor().setDate(Convert.LocalDateToDate(date));
            cbxStatus.setSelectedItem(tblAppoint.getValueAt(pointer, 5));
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

        dialogSearch = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        dialogTxtSearch = new javax.swing.JTextField();
        dialogCbxCategory = new javax.swing.JComboBox<>();
        dialogBtnCancel = new javax.swing.JButton();
        dialogBtnOk = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnPeople = new javax.swing.JButton();
        btnAppointment = new javax.swing.JButton();
        btnSupply = new javax.swing.JButton();
        btnCentre = new javax.swing.JButton();
        btnVaccine = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        tglAdd = new javax.swing.JToggleButton();
        tglRemove = new javax.swing.JToggleButton();
        tglModify = new javax.swing.JToggleButton();
        btnView = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstSearch = new javax.swing.JList<>();
        cbxView = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblICPNumber = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbxType = new javax.swing.JComboBox<>();
        cbxICPNum = new javax.swing.JComboBox<>();
        cbxVaccine = new javax.swing.JComboBox<>();
        cbxHour = new javax.swing.JComboBox<>();
        cbxMinute = new javax.swing.JComboBox<>();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cbxCentre = new javax.swing.JComboBox<>();
        dcDate = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtApp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbxStatus = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAppoint = new javax.swing.JTable();

        dialogSearch.setTitle("Search");
        dialogSearch.setModal(true);
        dialogSearch.setResizable(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setText("Search");

        dialogCbxCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IC/Passport Number", "Appointment ID", "Vaccine", "Centre ID", "Status" }));

        dialogBtnCancel.setText("Cancel");
        dialogBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dialogBtnCancelActionPerformed(evt);
            }
        });

        dialogBtnOk.setText("OK");
        dialogBtnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dialogBtnOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dialogTxtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dialogCbxCategory, 0, 191, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(dialogBtnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dialogBtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(dialogTxtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dialogCbxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dialogBtnCancel)
                    .addComponent(dialogBtnOk))
                .addContainerGap())
        );

        javax.swing.GroupLayout dialogSearchLayout = new javax.swing.GroupLayout(dialogSearch.getContentPane());
        dialogSearch.getContentPane().setLayout(dialogSearchLayout);
        dialogSearchLayout.setHorizontalGroup(
            dialogSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dialogSearchLayout.setVerticalGroup(
            dialogSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Appointment (Admin)");

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        btnPeople.setText("People");
        btnPeople.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeopleActionPerformed(evt);
            }
        });

        btnAppointment.setText("Appointment");
        btnAppointment.setEnabled(false);

        btnSupply.setText("Supply");
        btnSupply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupplyActionPerformed(evt);
            }
        });

        btnCentre.setText("Centre");
        btnCentre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCentreActionPerformed(evt);
            }
        });

        btnVaccine.setText("Vaccine");
        btnVaccine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVaccineActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPeople, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSupply, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCentre, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVaccine, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPeople)
                    .addComponent(btnAppointment)
                    .addComponent(btnSupply)
                    .addComponent(btnCentre)
                    .addComponent(btnVaccine)
                    .addComponent(btnLogout))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tglAdd.setText("Add");
        tglAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglAddActionPerformed(evt);
            }
        });

        tglRemove.setText("Remove");
        tglRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglRemoveActionPerformed(evt);
            }
        });

        tglModify.setText("Modify");
        tglModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglModifyActionPerformed(evt);
            }
        });

        btnView.setText("View");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        lstSearch.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstSearch.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstSearchValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(lstSearch);

        cbxView.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Citizen", "Non-Citizen" }));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Citizen / Non-Citizen");

        lblICPNumber.setText("IC Number");

        jLabel4.setText("Vaccine");

        jLabel3.setText("Time");

        cbxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Citizen", "Non-Citizen" }));
        cbxType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTypeActionPerformed(evt);
            }
        });

        cbxICPNum.setEditable(true);
        cbxICPNum.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxICPNumItemStateChanged(evt);
            }
        });

        cbxHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "09", "10", "11", "12", "13", "14", "15" }));

        cbxMinute.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jLabel2.setText("Centre");

        dcDate.setDateFormatString("dd/MM/yyyy");

        jLabel5.setText("Date");

        jLabel6.setText("Appointment ID");

        txtApp.setEditable(false);

        jLabel7.setText("Status");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblICPNumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtApp, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(cbxICPNum, 0, 179, Short.MAX_VALUE)
                            .addComponent(cbxType, 0, 179, Short.MAX_VALUE)
                            .addComponent(cbxCentre, 0, 179, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbxHour, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbxMinute, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbxVaccine, 0, 179, Short.MAX_VALUE)
                            .addComponent(dcDate, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(cbxStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblICPNumber)
                    .addComponent(cbxICPNum, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbxVaccine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxCentre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(btnCancel)
                    .addComponent(btnClear))
                .addContainerGap())
        );

        jScrollPane3.setViewportView(jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tglAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tglRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tglModify, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(cbxView, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 105, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tglAdd)
                    .addComponent(tglRemove)
                    .addComponent(tglModify)
                    .addComponent(btnView)
                    .addComponent(cbxView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTypeActionPerformed
        // TODO add your handling code here:
        String type = (String) cbxType.getSelectedItem();
        int size = cbxICPNum.getItemCount();
        if (type.equals("Citizen")) {
            lblICPNumber.setText("IC Number");
            Citizen[] cz = czDict.values().toArray(new Citizen[0]);
            if (cz.length > 0) {
                for (Citizen data: cz) {
                    cbxICPModel.addElement((String) data.getID());
                }
                for (int i = size-1; i >= 0; i--) {
                    cbxICPModel.removeElementAt(i);
                }
            }
            else {
                cbxICPModel.removeAllElements();
                cbxICPModel.addElement("");
            }
        }
        else {
            lblICPNumber.setText("Passport Number");
            NonCitizen[] ncz = nczDict.values().toArray(new NonCitizen[0]);
            if (ncz.length > 0) {
                for (NonCitizen data: ncz) {
                    cbxICPModel.addElement((String) data.getID());
                }
                for (int i = size-1; i >= 0; i--) {
                    cbxICPModel.removeElementAt(i);
                }
            }
            else {
                cbxICPModel.removeAllElements();
                cbxICPModel.addElement("");
            }
        }
    }//GEN-LAST:event_cbxTypeActionPerformed

    private void cbxICPNumItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxICPNumItemStateChanged
        // TODO add your handling code here:
        String data = (String) cbxICPNum.getSelectedItem();
        int size = cbxICPNum.getItemCount();
        boolean match = false;
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                if (data.equals((String) cbxICPNum.getItemAt(i))) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                cbxICPNum.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_cbxICPNumItemStateChanged

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        // TODO add your handling code here:
        String result = check();
        if (result == null) {
            write();
            btnCancel.doClick();
            btnView.doClick();
            dialogBtnOk.doClick();
        }
        else {
            PopupDialog.error(result);
        }
    }//GEN-LAST:event_btnOKActionPerformed

    private void tglAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglAddActionPerformed
        // TODO add your handling code here:
        if (tglAdd.isSelected()) {
            LocalDate min = LocalDate.now().plusWeeks(2);
            LocalDate max = min.plusMonths(2);
            dcDate.setMaxSelectableDate(Convert.LocalDateToDate(max));
            dcDate.setMinSelectableDate(Convert.LocalDateToDate(min));
            btnView.doClick();
            btnClear.doClick();
            enableDisableButton(false);
            enableDisablePanel(true);
            txtApp.setText(Generator.generateAppID());
            tglAdd.setEnabled(true);
            cbxStatus.setEnabled(false);
        }
        else {
            btnCancel.doClick();
        }
    }//GEN-LAST:event_tglAddActionPerformed

    private void tglRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglRemoveActionPerformed
        // TODO add your handling code here:
        if (tglRemove.isSelected() && !cleared) {
            boolean ok = PopupDialog.confirm("Are you sure to delete this entry?");
            if (ok) {
                btnOK.doClick();
            }
            else {
                btnCancel.doClick();
            }
        }
        else {
            PopupDialog.error("Please select an entry first");
            btnCancel.doClick();
        }
    }//GEN-LAST:event_tglRemoveActionPerformed

    private void tglModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglModifyActionPerformed
        // TODO add your handling code here:
        if (tglModify.isSelected() && !cleared) {
            LocalDate max = LocalDate.of(2030, 1, 1);
            LocalDate min = LocalDate.of(2000, 1, 1);
            dcDate.setMaxSelectableDate(Convert.LocalDateToDate(max));
            dcDate.setMinSelectableDate(Convert.LocalDateToDate(min));
            if (cbxStatus.getSelectedItem().equals(AppointStatus.WAITING)) {
                last = pointer;
                btnView.doClick();
                enableDisableButton(false);
                enableDisablePanel(true);
                cbxICPNum.setEnabled(false);
                tglModify.setEnabled(true);
                cbxStatus.setEnabled(false);
                if (cbxType.getSelectedItem().equals("Citizen")) {
                    if (czDict.get((String) cbxICPNum.getSelectedItem()).getAppoint().size() > 1) {
                        cbxVaccine.setEnabled(false);
                    }
                }
            }
            else {
                PopupDialog.error("Unable to modify completed appointment");
                btnCancel.doClick();
            }
        }
        else if (tglModify.isSelected() && cleared) {
            PopupDialog.error("Please select an entry first");
            btnCancel.doClick();
        }
        else {
            btnCancel.doClick();
        }
    }//GEN-LAST:event_tglModifyActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        pointer = 0;
        tblAppModel.setRowCount(0);
        if (cbxView.getSelectedItem().equals("Citizen")) {
            Object[] colId = {
                "IC Number",
                "Appointment ID",
                "Vaccine Name",
                "Centre ID",
                "Date",
                "Status"
            };
            tblAppModel.setColumnIdentifiers(colId);
            for (Citizen cz: czDict.values()) {
                for (Appointment app: cz.getAppoint()) {
                    Object[] temp = {
                        cz.getID(),
                        app.getID(),
                        app.getVaccine().getName(),
                        app.getCentre().getCentreID(),
                        Convert.LocalDateTimeToString(app.getDateTime(), "dd/MM/yyyy HH:mm"),
                        app.getStatus(),
                    };
                    tblAppModel.addRow(temp);
                }
            }
        }
        else {
            Object[] colId = {
                "Passport Number",
                "Appointment ID",
                "Vaccine Name",
                "Centre ID",
                "Date",
                "Status"
            };
            tblAppModel.setColumnIdentifiers(colId);
            for (NonCitizen ncz: nczDict.values()) {
                for (Appointment app: ncz.getAppoint()) {
                    Object[] temp = {
                        ncz.getID(),
                        app.getID(),
                        app.getVaccine().getName(),
                        app.getCentre().getCentreID(),
                        Convert.LocalDateTimeToString(app.getDateTime(), "dd/MM/yyyy HH:mm"),
                        app.getStatus(),
                    };
                    tblAppModel.addRow(temp);
                }
            }
        }
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        enableDisableButton(true);
        enableDisablePanel(false);
        tglAdd.setSelected(false);
        tglRemove.setSelected(false);
        tglModify.setSelected(false);
        btnClear.doClick();
        LocalDate min = LocalDate.now().plusWeeks(1);
        LocalDate max = min.plusMonths(2);
        dcDate.setMaxSelectableDate(Convert.LocalDateToDate(max));
        dcDate.setMinSelectableDate(Convert.LocalDateToDate(min));
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        cbxCentre.setSelectedIndex(-1);
        cbxICPNum.setSelectedIndex(0);
        cbxHour.setSelectedIndex(-1);
        cbxMinute.setSelectedIndex(-1);
        cbxVaccine.setSelectedIndex(-1);
        txtApp.setText("");
        dcDate.getDateEditor().setDate(null);
        cbxStatus.setSelectedItem(AppointStatus.WAITING);
        cleared = true;
    }//GEN-LAST:event_btnClearActionPerformed

    private void tblAppointMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAppointMouseClicked
        // TODO add your handling code here:
        pointer = tblAppoint.getSelectedRow();
        fillDetails();
    }//GEN-LAST:event_tblAppointMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        dialogSearch.setLocationRelativeTo(this);
        dialogSearch.pack();
        dialogSearch.setVisible(true);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void dialogBtnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dialogBtnOkActionPerformed
        // TODO add your handling code here:
        btnView.doClick();
        searchIdx.clear();
        lstSearchModel.removeAllElements();
        int row = tblAppoint.getRowCount();
        int col = tblAppoint.getColumnCount();
        int selectedCol = dialogCbxCategory.getSelectedIndex();
        if (selectedCol == 4) selectedCol++;
        String keyword = dialogTxtSearch.getText();
        if (keyword.isBlank()) {
            return;
        }
        keyword = Convert.escapeSpecialChar(keyword);
        keyword = ".*" + keyword + ".*";
        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
        Matcher matcher;

        for (int i = 0; i < row; i++) {
            String cell = tblAppoint.getValueAt(i, selectedCol).toString();
            matcher = pattern.matcher(cell);
            if (matcher.find()) {
                searchIdx.add(i);
                String[] temp = new String[col];
                for (int j = 0; j < col; j++) {
                    temp[j] = tblAppoint.getValueAt(i, j).toString();
                }
                lstSearchModel.addElement(String.join(";", temp));
            }
        }

    }//GEN-LAST:event_dialogBtnOkActionPerformed

    private void dialogBtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dialogBtnCancelActionPerformed
        // TODO add your handling code here:
        dialogSearch.dispose();
    }//GEN-LAST:event_dialogBtnCancelActionPerformed

    private void lstSearchValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstSearchValueChanged
        // TODO add your handling code here:
        try {
            int idx = lstSearch.getSelectedIndex();
            pointer = searchIdx.get(idx);
            fillDetails();
        }
        catch (IndexOutOfBoundsException e) {
        }
    }//GEN-LAST:event_lstSearchValueChanged

    private void btnPeopleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeopleActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new AdminPeople().setVisible(true);
    }//GEN-LAST:event_btnPeopleActionPerformed

    private void btnSupplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupplyActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new AdminSupply().setVisible(true);
    }//GEN-LAST:event_btnSupplyActionPerformed

    private void btnCentreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCentreActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new AdminCentre().setVisible(true);
    }//GEN-LAST:event_btnCentreActionPerformed

    private void btnVaccineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaccineActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new AdminVaccine().setVisible(true);
    }//GEN-LAST:event_btnVaccineActionPerformed

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
            java.util.logging.Logger.getLogger(AdminAppoint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminAppoint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminAppoint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminAppoint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminAppoint().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAppointment;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCentre;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnPeople;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSupply;
    private javax.swing.JButton btnVaccine;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox<String> cbxCentre;
    private javax.swing.JComboBox<String> cbxHour;
    private javax.swing.JComboBox<String> cbxICPNum;
    private javax.swing.JComboBox<String> cbxMinute;
    private javax.swing.JComboBox<String> cbxStatus;
    private javax.swing.JComboBox<String> cbxType;
    private javax.swing.JComboBox<String> cbxVaccine;
    private javax.swing.JComboBox<String> cbxView;
    private com.toedter.calendar.JDateChooser dcDate;
    private javax.swing.JButton dialogBtnCancel;
    private javax.swing.JButton dialogBtnOk;
    private javax.swing.JComboBox<String> dialogCbxCategory;
    private javax.swing.JDialog dialogSearch;
    private javax.swing.JTextField dialogTxtSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblICPNumber;
    private javax.swing.JList<String> lstSearch;
    private javax.swing.JTable tblAppoint;
    private javax.swing.JToggleButton tglAdd;
    private javax.swing.JToggleButton tglModify;
    private javax.swing.JToggleButton tglRemove;
    private javax.swing.JTextField txtApp;
    // End of variables declaration//GEN-END:variables
}
