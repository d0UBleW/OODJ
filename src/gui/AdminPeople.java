/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.toedter.calendar.JTextFieldDateEditor;
import entity.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import util.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LEGION
 */
public class AdminPeople extends javax.swing.JFrame implements FillDetails {

    private boolean success;
    private boolean ICPFilled;
    private boolean passwdFilled;
    private boolean emailFilled;
    private boolean phoneFilled;
    private boolean cleared;
    private String role;
    private DefaultTableModel tblModel;
    private int pointer;
    private DefaultListModel listModel;
    private List<Integer> searchIdx;
    private Map<String, Citizen> czDict;
    private Map<String, NonCitizen> nczDict;


    /**
     * Creates new form AdminP
     */
    public AdminPeople() {
        this.searchIdx = new ArrayList<Integer>();
        this.listModel = new DefaultListModel();
        initComponents();
        czDict = InitDictionary.citizenDict();
        nczDict = InitDictionary.noncitizenDict();
        pointer = 0;
        success = false;
        role = "Citizen";

        ICPFilled = false;
        passwdFilled = false;
        emailFilled = false;
        phoneFilled = false;
        cleared = true;

        LocalDate now = LocalDate.now();
        LocalDate min = LocalDate.of(1800, Month.JANUARY, 1);
        dcDOB.setMaxSelectableDate(Convert.LocalDateToDate(now));
        dcDOB.setMinSelectableDate(Convert.LocalDateToDate(min));
        JTextFieldDateEditor dcEditor =
                (JTextFieldDateEditor) dcDOB.getDateEditor();
        dcEditor.setEditable(false);

        tblModel = (DefaultTableModel) tblPeople.getModel();
        tblModel.setRowCount(0);
        tblPeople.setDefaultEditor(Object.class, null);
        tblPeople.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        btnRegister.setVisible(false);
        btnClear.setVisible(false);

        enableDisablePanel(false);

        lstSearch.setModel(listModel);

        btnView.doClick();

        txtICPNum.setEnabled(false);

        this.setLocationRelativeTo(null);
    }

    public String check() {
        boolean validICP = Checker.isICPNum(txtICPNum.getText(),
                (String) cbxRole.getSelectedItem());
        if (!validICP) return "Invalid IC/Passport Number";

        boolean validPasswd = Checker.isPassword(
                String.valueOf(passwd.getPassword())
        );
        if (!validPasswd) return "Invalid password";

        String email = txtEmail.getText();
        boolean validEmail = true;
        if (emailFilled && !Checker.isEmail(email)) {
            validEmail = false;
        }
        if (email.equals("-")) validEmail = true;
        if (!validEmail) return "Invalid email";

        String phone = txtPhone.getText();
        boolean validPhone = true;
        if (phoneFilled && !Checker.isPhone(phone)) {
            validPhone = false;
        }
        if (phone.equals("-")) validPhone = true;
        if (!validPhone) return "Invalid phone";

        String name = txtName.getText();
        char[] bad = {
            ';'
        };
        boolean validName = !name.isBlank() && Checker.hasNoBadChar(name, bad);
        if (!validName) return "Bad characters in name text field";

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        String DOB = null;
        boolean validDOB = false;
        try {
            DOB = fmt.format(dcDOB.getDate());
            validDOB = true;
        }
        catch (NullPointerException e) {
            validDOB = false;
        }
        if (!validDOB) return "Invalid DOB";

        return null;
    }

    @Override
    public void fillDetails() {
        if (pointer != -1) {
            String view = (String) cbxViewRole.getSelectedItem();
            if (view.equals("Citizen")) {
                txtICPNum.setText((String) tblPeople.getValueAt(pointer, 0));
                passwd.setText((String) tblPeople.getValueAt(pointer, 1));
                txtName.setText((String) tblPeople.getValueAt(pointer, 2));
                dcDOB.getDateEditor().setDate(Convert.StringToDate((String) tblPeople.getValueAt(pointer, 3),
                                "dd/MM/yyyy"
                        )
                );
                txtEmail.setText((String) tblPeople.getValueAt(pointer, 4));
                txtPhone.setText((String) tblPeople.getValueAt(pointer, 5));
                txtICPNum.setForeground(Color.black);
                passwd.setForeground(Color.black);
                txtPhone.setForeground(Color.black);
                txtEmail.setForeground(Color.black);
                ICPFilled = true;
                passwdFilled = true;
                emailFilled = true;
                phoneFilled = true;
            }
            else {
                txtICPNum.setText((String) tblPeople.getValueAt(pointer, 0));
                passwd.setText((String) tblPeople.getValueAt(pointer, 1));
                txtName.setText((String) tblPeople.getValueAt(pointer, 2));
                dcDOB.getDateEditor().setDate(Convert.StringToDate((String) tblPeople.getValueAt(pointer, 3),
                                "dd/MM/yyyy"
                        )
                );
                txtEmail.setText((String) tblPeople.getValueAt(pointer, 4));
                txtPhone.setText((String) tblPeople.getValueAt(pointer, 5));
                txtICPNum.setForeground(Color.black);
                passwd.setForeground(Color.black);
                txtPhone.setForeground(Color.black);
                txtEmail.setForeground(Color.black);
                ICPFilled = true;
                passwdFilled = true;
                emailFilled = true;
                phoneFilled = true;
            }
            cleared = false;
        }
    }

    public void enableDisableButton(boolean state) {
        tglRegister.setEnabled(state);
        tglModify.setEnabled(state);
        btnView.setEnabled(state);
        btnSearch.setEnabled(state);
        lstSearch.setEnabled(state);
        tblPeople.setEnabled(state);
        cbxViewRole.setEnabled(state);
    }

    public void enableDisablePanel(boolean state) {
        cbxRole.setEnabled(state);
//        txtICPNum.setEnabled(state);
        txtName.setEnabled(state);
        passwd.setEnabled(state);
        txtEmail.setEnabled(state);
        txtPhone.setEnabled(state);
        dcDOB.setEnabled(state);
    }

    public void clearField() {
        ICPFilled = false;
        passwdFilled = false;
        emailFilled = false;
        phoneFilled = false;
        txtICPNum.setForeground(Color.lightGray);
        if (role.equals("Citizen")) {
            txtICPNum.setText("ICXXXXXX");
        }
        else {
            txtICPNum.setText("PXXXXXX");
        }
        txtName.setText("");
        passwd.setText("");
        txtEmail.setForeground(Color.lightGray);
        txtPhone.setForeground(Color.lightGray);
        txtEmail.setText("user@example.com");
        txtPhone.setText("+XXXXXXXXXXX");
        dcDOB.getDateEditor().setDate(null);
        cleared = true;
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogFeedback = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        btnFeedback = new javax.swing.JButton();
        lblFeedback = new javax.swing.JLabel();
        dialogSearch = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        btnSearchOk = new javax.swing.JButton();
        lblFeedback1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        cbxSearchCategory = new javax.swing.JComboBox<>();
        btnCloseSearch = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnPeopleFrame = new javax.swing.JButton();
        btnAppointmentFrame = new javax.swing.JButton();
        btnSupplyFrame = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtICPNum = new javax.swing.JTextField();
        lblICPNum = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lblAst1 = new javax.swing.JLabel();
        lblAst2 = new javax.swing.JLabel();
        lblReq = new javax.swing.JLabel();
        lblPasswd = new javax.swing.JLabel();
        lblAst3 = new javax.swing.JLabel();
        passwd = new javax.swing.JPasswordField();
        cbxRole = new javax.swing.JComboBox<>();
        lblPassReq = new javax.swing.JLabel();
        chkShow = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        dcDOB = new com.toedter.calendar.JDateChooser();
        lblAst4 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnRegister = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        cbxViewRole = new javax.swing.JComboBox<>();
        tglModify = new javax.swing.JToggleButton();
        tglRegister = new javax.swing.JToggleButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstSearch = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPeople = new javax.swing.JTable();
        btnCentre = new javax.swing.JButton();
        btnVaccine = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        dialogFeedback.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogFeedback.setTitle("Notification");
        dialogFeedback.setAlwaysOnTop(true);
        dialogFeedback.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        dialogFeedback.setMinimumSize(new java.awt.Dimension(400, 85));
        dialogFeedback.setModal(true);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        btnFeedback.setText("OK");
        btnFeedback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFeedbackActionPerformed(evt);
            }
        });

        lblFeedback.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFeedback.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFeedback, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 311, Short.MAX_VALUE)
                        .addComponent(btnFeedback, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(lblFeedback)
                .addGap(18, 18, 18)
                .addComponent(btnFeedback)
                .addContainerGap())
        );

        javax.swing.GroupLayout dialogFeedbackLayout = new javax.swing.GroupLayout(dialogFeedback.getContentPane());
        dialogFeedback.getContentPane().setLayout(dialogFeedbackLayout);
        dialogFeedbackLayout.setHorizontalGroup(
            dialogFeedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dialogFeedbackLayout.setVerticalGroup(
            dialogFeedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        dialogSearch.setTitle("Search");
        dialogSearch.setModal(true);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        btnSearchOk.setText("OK");
        btnSearchOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchOkActionPerformed(evt);
            }
        });

        lblFeedback1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFeedback1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel3.setText("Search:");

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        cbxSearchCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IC/Passport Number", "Name", "DOB", "Email", "Phone" }));
        cbxSearchCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbxSearchCategoryKeyPressed(evt);
            }
        });

        btnCloseSearch.setText("Close");
        btnCloseSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFeedback1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSearchOk, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCloseSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(cbxSearchCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxSearchCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFeedback1)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearchOk)
                    .addComponent(btnCloseSearch))
                .addContainerGap())
        );

        javax.swing.GroupLayout dialogSearchLayout = new javax.swing.GroupLayout(dialogSearch.getContentPane());
        dialogSearch.getContentPane().setLayout(dialogSearchLayout);
        dialogSearchLayout.setHorizontalGroup(
            dialogSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dialogSearchLayout.setVerticalGroup(
            dialogSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("People (Admin)");

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        btnPeopleFrame.setText("People");
        btnPeopleFrame.setEnabled(false);

        btnAppointmentFrame.setText("Apointment");
        btnAppointmentFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAppointmentFrameActionPerformed(evt);
            }
        });

        btnSupplyFrame.setText("Supply");
        btnSupplyFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupplyFrameActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("User");

        txtICPNum.setForeground(java.awt.Color.lightGray);
        txtICPNum.setText("ICXXXXXX");
        txtICPNum.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtICPNumFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtICPNumFocusLost(evt);
            }
        });

        lblICPNum.setText("IC Number");

        lblName.setText("Name");

        lblEmail.setText("Email");

        txtEmail.setForeground(java.awt.Color.lightGray);
        txtEmail.setText("user@example.com");
        txtEmail.setToolTipText("");
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });

        lblPhone.setText("Phone");

        txtPhone.setForeground(java.awt.Color.lightGray);
        txtPhone.setText("+XXXXXXXXXXX");
        txtPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPhoneFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPhoneFocusLost(evt);
            }
        });

        lblAst1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAst1.setForeground(java.awt.Color.red);
        lblAst1.setText("*");

        lblAst2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAst2.setForeground(java.awt.Color.red);
        lblAst2.setText("*");

        lblReq.setForeground(java.awt.Color.red);
        lblReq.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblReq.setText("* Required field");

        lblPasswd.setText("Password");

        lblAst3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAst3.setForeground(java.awt.Color.red);
        lblAst3.setText("*");

        passwd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwdFocusLost(evt);
            }
        });

        cbxRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Citizen", "Non-Citizen" }));
        cbxRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxRoleActionPerformed(evt);
            }
        });

        lblPassReq.setText("min. 8, alphanum");

        chkShow.setBackground(new java.awt.Color(255, 255, 255));
        chkShow.setText("Show password");
        chkShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowActionPerformed(evt);
            }
        });

        jLabel2.setText("Date of Birth");

        dcDOB.setDateFormatString("dd/MM/yyyy");
        dcDOB.setMaxSelectableDate(new java.util.Date(253370743265000L));

        lblAst4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAst4.setForeground(java.awt.Color.red);
        lblAst4.setText("*");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblPasswd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(lblICPNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtName)
                                    .addComponent(passwd)
                                    .addComponent(cbxRole, 0, 187, Short.MAX_VALUE)
                                    .addComponent(txtICPNum, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblAst2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblAst3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblAst1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPassReq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(chkShow)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblReq)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                        .addComponent(txtPhone))
                                    .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAst4, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                        .addGap(106, 106, 106))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblICPNum)
                    .addComponent(lblAst1)
                    .addComponent(txtICPNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAst2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPasswd)
                    .addComponent(lblAst3)
                    .addComponent(passwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassReq))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkShow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmail)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPhone)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAst4)
                            .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addComponent(lblReq))
                    .addComponent(btnClear))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegister)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel4);

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnView.setText("View");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        cbxViewRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Citizen", "Non-Citizen" }));

        tglModify.setText("Modify");
        tglModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglModifyActionPerformed(evt);
            }
        });

        tglRegister.setText("Register");
        tglRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglRegisterActionPerformed(evt);
            }
        });

        lstSearch.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstSearch.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstSearchValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(lstSearch);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSearch)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tglRegister, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(tglModify)
                        .addGap(6, 6, 6)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbxViewRole, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnView)
                    .addComponent(cbxViewRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglModify)
                    .addComponent(tglRegister))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tblPeople.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPeople.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPeopleMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPeople);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnPeopleFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAppointmentFrame)
                        .addGap(18, 18, 18)
                        .addComponent(btnSupplyFrame)
                        .addGap(18, 18, 18)
                        .addComponent(btnCentre)
                        .addGap(18, 18, 18)
                        .addComponent(btnVaccine)
                        .addGap(18, 18, 18)
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPeopleFrame)
                    .addComponent(btnAppointmentFrame)
                    .addComponent(btnSupplyFrame)
                    .addComponent(btnCentre)
                    .addComponent(btnVaccine)
                    .addComponent(btnLogout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFeedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFeedbackActionPerformed
        // TODO add your handling code here:
        dialogFeedback.dispose();
    }//GEN-LAST:event_btnFeedbackActionPerformed

    private void tblPeopleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPeopleMouseClicked
        // TODO add your handling code here:
        pointer = tblPeople.getSelectedRow();
        fillDetails();
    }//GEN-LAST:event_tblPeopleMouseClicked

    private void tglRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglRegisterActionPerformed
        // TODO add your handling code here:
        if (tglRegister.isSelected()) {
            btnClear.doClick();
            btnRegister.setVisible(true);
            btnCancel.setVisible(true);
            btnClear.setVisible(true);
            txtICPNum.setEnabled(true);
            enableDisablePanel(true);
            enableDisableButton(false);
            tglRegister.setEnabled(true);
        }
        else {
            btnCancel.doClick();
        }
    }//GEN-LAST:event_tglRegisterActionPerformed

    private void tglModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglModifyActionPerformed
        // TODO add your handling code here:
        if (tglModify.isSelected() && !cleared) {
            btnSave.setVisible(true);
            btnCancel.setVisible(true);
            btnClear.setVisible(false);
            enableDisablePanel(true);
            enableDisableButton(false);
            tglModify.setEnabled(true);
        }
        else if (!tglModify.isSelected()) {
            btnCancel.doClick();
        }
        else {
            PopupDialog.error("Please select an entry first");
            btnCancel.doClick();
        }
    }//GEN-LAST:event_tglModifyActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        tblModel.setRowCount(0);
        pointer = 0;
        if (cbxViewRole.getSelectedItem().equals("Citizen")) {
            Citizen[] czArr = czDict.values().toArray(new Citizen[0]);
            Transfer.importObject(czArr, Citizen.FILENAME);
            Object[] colId = {
                "IC Number",
                "Password",
                "Name",
                "Date of Birth",
                "Email Address",
                "Phone Number"
            };
            tblModel.setColumnIdentifiers(colId);
            for (Citizen data: czArr) {
                tblModel.addRow(data.toArray());
            }
        }
        else {
            NonCitizen[] nczArr = nczDict.values().toArray(new NonCitizen[0]);
            Transfer.importObject(nczArr, NonCitizen.FILENAME);
            Object[] colId = {
                "Passport Number",
                "Password",
                "Name",
                "Date of Birth",
                "Email Address",
                "Phone Number"
            };
            tblModel.setColumnIdentifiers(colId);
            for (NonCitizen data: nczArr) {
                tblModel.addRow(data.toArray());
            }
        }
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        // TODO add your handling code here:
        String password = String.valueOf(passwd.getPassword());
        String result = check();
        if (result != null) {
            success = false;
            lblFeedback.setText(result);
        }
        else {
            String ICPNum = txtICPNum.getText();
            String name = txtName.getText();
            String email = txtEmail.getText();
            email = emailFilled ? email : "-";
            String phone = txtPhone.getText();
            phone = phoneFilled ? phone : "-";
            LocalDate DOB = Convert.DateToLocalDate(dcDOB.getDate());
            People newP = null;
            switch (role) {
                case "Citizen":
                newP = new Citizen(ICPNum, name, DOB, phone, email,
                    password);
                break;
                case "Non-Citizen":
                newP = new NonCitizen(ICPNum, name, DOB, phone, email,
                    password);
                break;
            }
            success = Auth.register(newP);
            if (success) {
                lblFeedback.setText("Registration complete.");
                if (newP instanceof Citizen) czDict.put(ICPNum, (Citizen) newP);
                else nczDict.put(ICPNum, (NonCitizen) newP);
            }
            else {
                lblFeedback.setText("Duplicate entry found "
                    + "for IC/Passport Number");
            }
        }
        dialogFeedback.pack();
        dialogFeedback.setLocationRelativeTo(this);
        dialogFeedback.setVisible(true);
        if (success) {
            cbxViewRole.setSelectedItem(role);
            btnCancel.doClick();
            btnView.doClick();
            btnSearchOk.doClick();
        }
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        tglModify.setSelected(false);
        tglRegister.setSelected(false);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        btnRegister.setVisible(false);
        btnClear.setVisible(false);
        txtICPNum.setEnabled(false);
        enableDisableButton(true);
        enableDisablePanel(false);
        btnClear.doClick();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        clearField();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String result = check();
        if (result != null) {
            PopupDialog.error(result);
            return;
        }
        String ICP = txtICPNum.getText();
        String password = String.valueOf(passwd.getPassword());
        String name = txtName.getText();
        LocalDate dob = Convert.DateToLocalDate(dcDOB.getDate());
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String view = (String) cbxViewRole.getSelectedItem();
        if (view.equals("Citizen")) {
            czDict.get(ICP).setPassword(password);
            czDict.get(ICP).setName(name);
            czDict.get(ICP).setDOB(dob);
            czDict.get(ICP).setEmail(email);
            czDict.get(ICP).setPhoneNumber(phone);
            Citizen[] czArr = czDict.values().toArray(new Citizen[0]);
            Transfer.exportObject(czArr, Citizen.FILENAME);
        }
        else {
            nczDict.get(ICP).setPassword(password);
            nczDict.get(ICP).setName(name);
            nczDict.get(ICP).setDOB(dob);
            nczDict.get(ICP).setEmail(email);
            nczDict.get(ICP).setPhoneNumber(phone);
            NonCitizen[] nczArr = nczDict.values().toArray(new NonCitizen[0]);
            Transfer.exportObject(nczArr, NonCitizen.FILENAME);
        }
        btnSearchOk.doClick();
        btnCancel.doClick();
        btnView.doClick();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void chkShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowActionPerformed
        // TODO add your handling code here:
        if (chkShow.isSelected()) {
            passwd.setEchoChar((char) 0);
        }
        else {
            passwd.setEchoChar('*');
        }
    }//GEN-LAST:event_chkShowActionPerformed

    private void cbxRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxRoleActionPerformed
        // TODO add your handling code here:
        role = (String) cbxRole.getSelectedItem();
        String ICPNum = txtICPNum.getText();
        if (role.equals("Citizen")) {
            lblICPNum.setText("IC Number");
            if (!ICPFilled) {
                txtICPNum.setText("ICXXXXXX");
            }
        }
        else {
            lblICPNum.setText("Passport Number");
            if (!ICPFilled) {
                txtICPNum.setText("PXXXXXX");
            }
        }
        boolean valid = Checker.isICPNum(ICPNum, role);
        if (!valid && ICPFilled) {
            txtICPNum.setForeground(Color.red);
        }
        else if (valid && ICPFilled) {
            txtICPNum.setForeground(Color.black);
        }
        else {
            txtICPNum.setForeground(Color.lightGray);
        }
    }//GEN-LAST:event_cbxRoleActionPerformed

    private void passwdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwdFocusLost
        // TODO add your handling code here:
        if (String.valueOf(passwd.getPassword()).isBlank()) {
            passwdFilled = false;
        }
        else {
            boolean valid = Checker.isPassword(
                String.valueOf(passwd.getPassword())
            );
            if (!valid) {
                passwd.setForeground(Color.red);
            }
            passwdFilled = true;
        }
    }//GEN-LAST:event_passwdFocusLost

    private void passwdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwdFocusGained
        // TODO add your handling code here:
        passwd.setForeground(Color.black);
    }//GEN-LAST:event_passwdFocusGained

    private void txtPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhoneFocusLost
        // TODO add your handling code here:
        String phone = txtPhone.getText();
        if (phone.isBlank()) {
            txtPhone.setForeground(Color.lightGray);
            txtPhone.setText("+XXXXXXXXXXX");
            phoneFilled = false;
        }
        else {
            boolean valid = Checker.isPhone(phone);
            if (!valid) {
                txtPhone.setForeground(Color.red);
            }
            phoneFilled = true;
        }
    }//GEN-LAST:event_txtPhoneFocusLost

    private void txtPhoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhoneFocusGained
        // TODO add your handling code here:
        txtPhone.setForeground(Color.black);
        if (txtPhone.getText().equals("+XXXXXXXXXXX") && !phoneFilled) {
            txtPhone.setText("");
        }
    }//GEN-LAST:event_txtPhoneFocusGained

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        // TODO add your handling code here:
        String email = txtEmail.getText();
        if (email.isBlank()) {
            txtEmail.setForeground(Color.lightGray);
            txtEmail.setText("user@example.com");
            emailFilled = false;
        }
        else {
            boolean valid = Checker.isEmail(email);
            if (!valid) {
                txtEmail.setForeground(Color.red);
            }
            emailFilled = true;
        }
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusGained
        // TODO add your handling code here:
        txtEmail.setForeground(Color.black);
        if (txtEmail.getText().equals("user@example.com") && !emailFilled) {
            txtEmail.setText("");
        }
    }//GEN-LAST:event_txtEmailFocusGained

    private void txtICPNumFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtICPNumFocusLost
        // TODO add your handling code here:
        String ICPNum = txtICPNum.getText();
        if (ICPNum.isBlank()) {
            ICPFilled = false;
            txtICPNum.setForeground(Color.lightGray);
            if (role.equals("Citizen"))
            txtICPNum.setText("ICXXXXXX");
            else
            txtICPNum.setText("PXXXXXX");
        }
        else {
            ICPFilled = true;
            if (ICPNum.length() > 1 && ICPNum.substring(0, 2).equals("IC")) {
                cbxRole.setSelectedItem("Citizen");
            }
            else if (ICPNum.length() > 0 &&
                ICPNum.substring(0, 1).equals("P")) {
                cbxRole.setSelectedItem("Non-Citizen");
            }
            boolean valid = Checker.isICPNum(ICPNum, role);
            if (!valid) {
                txtICPNum.setForeground(Color.red);
            }
        }
    }//GEN-LAST:event_txtICPNumFocusLost

    private void txtICPNumFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtICPNumFocusGained
        // TODO add your handling code here:
        txtICPNum.setForeground(Color.black);
        if (role.equals("Citizen")) {
            if (txtICPNum.getText().equals("ICXXXXXX") && !ICPFilled) {
                txtICPNum.setText("");
            }
        }
        else {
            if (txtICPNum.getText().equals("PXXXXXX") && !ICPFilled) {
                txtICPNum.setText("");
            }
        }
    }//GEN-LAST:event_txtICPNumFocusGained

    private void btnSearchOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchOkActionPerformed
        // TODO add your handling code here:
        btnView.doClick();
        listModel.removeAllElements();
        int row = tblPeople.getRowCount();
        int selectedCol = cbxSearchCategory.getSelectedIndex();
        if (selectedCol > 0) {
            selectedCol++;
        }
        String keyword = txtSearch.getText();
        if (keyword.isBlank()) {
            return;
        }

        keyword = Convert.escapeSpecialChar(keyword);
        keyword = ".*" + keyword + ".*";
        
        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        searchIdx.clear();

        for (int i = 0; i < row; i++) {
            String cell = (String) tblPeople.getValueAt(i, selectedCol);
            matcher = pattern.matcher(cell);
            if (matcher.find()) {
                searchIdx.add(i);
                String[] temp = new String[2];
                temp[0] = tblPeople.getValueAt(i, 0).toString();
                temp[1] = tblPeople.getValueAt(i, 2).toString();
                listModel.addElement(String.join(";", temp));
            }
            else {
            }
        }
    }//GEN-LAST:event_btnSearchOkActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        dialogSearch.setLocationRelativeTo(this);
        dialogSearch.pack();
        dialogSearch.setVisible(true);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            btnSearchOk.doClick();
        }
        else if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            btnCloseSearch.doClick();
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void btnCloseSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseSearchActionPerformed
        // TODO add your handling code here:
        dialogSearch.dispose();
    }//GEN-LAST:event_btnCloseSearchActionPerformed

    private void cbxSearchCategoryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxSearchCategoryKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            btnSearchOk.doClick();
        }
        else if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            btnCloseSearch.doClick();
        }
    }//GEN-LAST:event_cbxSearchCategoryKeyPressed

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

    private void btnAppointmentFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAppointmentFrameActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new AdminAppoint().setVisible(true);
    }//GEN-LAST:event_btnAppointmentFrameActionPerformed

    private void btnSupplyFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupplyFrameActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new AdminSupply().setVisible(true);
    }//GEN-LAST:event_btnSupplyFrameActionPerformed

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
            java.util.logging.Logger.getLogger(AdminPeople.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPeople.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPeople.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPeople.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPeople().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAppointmentFrame;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCentre;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCloseSearch;
    private javax.swing.JButton btnFeedback;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPeopleFrame;
    private javax.swing.JButton btnRegister;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchOk;
    private javax.swing.JButton btnSupplyFrame;
    private javax.swing.JButton btnVaccine;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox<String> cbxRole;
    private javax.swing.JComboBox<String> cbxSearchCategory;
    private javax.swing.JComboBox<String> cbxViewRole;
    private javax.swing.JCheckBox chkShow;
    private com.toedter.calendar.JDateChooser dcDOB;
    private javax.swing.JDialog dialogFeedback;
    private javax.swing.JDialog dialogSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAst1;
    private javax.swing.JLabel lblAst2;
    private javax.swing.JLabel lblAst3;
    private javax.swing.JLabel lblAst4;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFeedback;
    private javax.swing.JLabel lblFeedback1;
    private javax.swing.JLabel lblICPNum;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassReq;
    private javax.swing.JLabel lblPasswd;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblReq;
    private javax.swing.JList<String> lstSearch;
    private javax.swing.JPasswordField passwd;
    private javax.swing.JTable tblPeople;
    private javax.swing.JToggleButton tglModify;
    private javax.swing.JToggleButton tglRegister;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtICPNum;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
