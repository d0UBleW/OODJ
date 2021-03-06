/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.toedter.calendar.JTextFieldDateEditor;
import entity.*;
import java.awt.Color;
import java.text.SimpleDateFormat;
import util.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

/**
 *
 * @author LEGION
 */
public class RegisPeople extends javax.swing.JFrame {

    private boolean success;
    private boolean ICPFilled;
    private boolean passwdFilled;
    private boolean emailFilled;
    private boolean phoneFilled;
    private String role;

    /**
     * Creates new form RegisForm
     */
    public RegisPeople() {
        initComponents();
        role = "Citizen";
        ICPFilled = false;
        passwdFilled = false;
        emailFilled = false;
        phoneFilled = false;
        LocalDate now = LocalDate.now();
        LocalDate min = LocalDate.of(1800, Month.JANUARY, 1);
        dcDOB.setMaxSelectableDate(Convert.LocalDateToDate(now));
        dcDOB.setMinSelectableDate(Convert.LocalDateToDate(min));
        JTextFieldDateEditor dcEditor =
                (JTextFieldDateEditor) dcDOB.getDateEditor();
        dcEditor.setEditable(false);
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogFeedback = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        btnFeedback = new javax.swing.JButton();
        lblFeedback = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtICPNum = new javax.swing.JTextField();
        lblICPNum = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        btnRegister = new javax.swing.JButton();
        lblAst1 = new javax.swing.JLabel();
        lblAst2 = new javax.swing.JLabel();
        lblReq = new javax.swing.JLabel();
        lblPasswd = new javax.swing.JLabel();
        lblAst3 = new javax.swing.JLabel();
        passwd = new javax.swing.JPasswordField();
        btnBack = new javax.swing.JButton();
        cbxRole = new javax.swing.JComboBox<>();
        lblPassReq = new javax.swing.JLabel();
        chkShow = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        dcDOB = new com.toedter.calendar.JDateChooser();
        lblAst4 = new javax.swing.JLabel();

        dialogFeedback.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogFeedback.setTitle("Notification");
        dialogFeedback.setAlwaysOnTop(true);
        dialogFeedback.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        dialogFeedback.setMinimumSize(new java.awt.Dimension(400, 85));
        dialogFeedback.setModal(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnFeedback.setText("OK");
        btnFeedback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFeedbackActionPerformed(evt);
            }
        });

        lblFeedback.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFeedback.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFeedback, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 311, Short.MAX_VALUE)
                        .addComponent(btnFeedback, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dialogFeedbackLayout.setVerticalGroup(
            dialogFeedbackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registration Page");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Register as");

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

        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
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

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
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

        dcDOB.setDateFormatString("yyyy-MM-dd");
        dcDOB.setMaxSelectableDate(new java.util.Date(253370743265000L));

        lblAst4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAst4.setForeground(java.awt.Color.red);
        lblAst4.setText("*");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(226, 226, 226)
                                .addComponent(lblReq, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRegister))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblPasswd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(lblICPNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtName)
                                    .addComponent(passwd)
                                    .addComponent(cbxRole, 0, 187, Short.MAX_VALUE)
                                    .addComponent(txtICPNum, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblAst2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblAst3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblAst1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPassReq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(chkShow)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                .addComponent(txtPhone))
                            .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAst4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(106, 106, 106)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblICPNum)
                    .addComponent(lblAst1)
                    .addComponent(txtICPNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAst2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPasswd)
                    .addComponent(lblAst3)
                    .addComponent(passwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassReq))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkShow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhone)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAst4)
                            .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(lblReq)
                        .addGap(13, 13, 13))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegister)
                    .addComponent(btnBack))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            LocalDate DOB = dcDOB.getDate().toInstant().atZone(
                    ZoneId.systemDefault()
            ).toLocalDate();
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
                lblFeedback.setText("Registration complete. " + "Return to login page");
            }
            else {
                lblFeedback.setText("Duplicate entry found "
                        + "for IC/Passport Number");
            }
        }
        dialogFeedback.pack();
        dialogFeedback.setLocationRelativeTo(this);
        dialogFeedback.setVisible(true);
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnFeedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFeedbackActionPerformed
        // TODO add your handling code here:
        dialogFeedback.dispose();
        if (success) {
            this.dispose();
            new LoginForm().setVisible(true);
        }
    }//GEN-LAST:event_btnFeedbackActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new LoginForm().setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

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

    private void chkShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowActionPerformed
        // TODO add your handling code here:
        if (chkShow.isSelected()) {
            passwd.setEchoChar((char) 0);
        }
        else {
            passwd.setEchoChar('*');
        }
    }//GEN-LAST:event_chkShowActionPerformed

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
            java.util.logging.Logger.getLogger(RegisPeople.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisPeople.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisPeople.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisPeople.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisPeople().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnFeedback;
    private javax.swing.JButton btnRegister;
    private javax.swing.JComboBox<String> cbxRole;
    private javax.swing.JCheckBox chkShow;
    private com.toedter.calendar.JDateChooser dcDOB;
    private javax.swing.JDialog dialogFeedback;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAst1;
    private javax.swing.JLabel lblAst2;
    private javax.swing.JLabel lblAst3;
    private javax.swing.JLabel lblAst4;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFeedback;
    private javax.swing.JLabel lblICPNum;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassReq;
    private javax.swing.JLabel lblPasswd;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblReq;
    private javax.swing.JPasswordField passwd;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtICPNum;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables


}
