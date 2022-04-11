/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import util.AppointStatus;
import util.Convert;
import util.InitDictionary;
import util.Transfer;

/**
 *
 * @author LEGION
 */
public class UserStatus extends javax.swing.JFrame {

    private People user;
    private Map<String, Citizen> czDict;
    private Map<String, NonCitizen> nczDict;
    private DefaultListModel lstModel;
    private List<String> appIdList;
    private Map<String, Appointment> appDict;

    /**
     * Creates new form UserStatus
     */
    public UserStatus() {
        initComponents();
        this.setLocationRelativeTo(null);
        appDict = new HashMap<>();
        czDict = InitDictionary.citizenDict();
        nczDict = InitDictionary.noncitizenDict();
        Transfer.readAppoint(czDict, nczDict);
        user = czDict.get("IC000001");
        List<Appointment> appList = this.user.getAppoint();
        Collections.sort(appList);
        lstVac.setModel(new DefaultListModel());
        lstModel = (DefaultListModel) lstVac.getModel();
        appIdList = new ArrayList<>();
        for (Appointment app: appList) {
            if (app.getStatus().equals(AppointStatus.COMPLETED)) {
                appDict.put(app.getID(), app);
                String vacName = app.getVaccine().getName();
                String date = Convert.LocalDateTimeToString(app.getDateTime(), "dd/MM/yyyy");
                String temp = String.format("%s (%s)", vacName, date);
                lstModel.addElement(temp);
                appIdList.add(app.getID());
            }
        }
        String dose = null;
        if (appIdList.size() > 0) {
            dose = String.format("%d/%d", appIdList.size(), appList.get(0).getVaccine().getDose());
        }
        else {
            dose = "0/0";
        }
        lblDoseCount.setText("Dose count: " + dose);
    }

    public UserStatus(People user) {
        initComponents();
        this.setLocationRelativeTo(null);
        appDict = new HashMap<>();
        czDict = InitDictionary.citizenDict();
        nczDict = InitDictionary.noncitizenDict();
        Transfer.readAppoint(czDict, nczDict);
        if (user instanceof Citizen) {
            this.user = czDict.get(user.getID());
        }
        else {
            this.user = nczDict.get(user.getID());
        }
        List<Appointment> appList = this.user.getAppoint();
        Collections.sort(appList);
        lstVac.setModel(new DefaultListModel());
        lstModel = (DefaultListModel) lstVac.getModel();
        appIdList = new ArrayList<>();
        for (Appointment app: appList) {
            if (app.getStatus().equals(AppointStatus.COMPLETED)) {
                appDict.put(app.getID(), app);
                String vacName = app.getVaccine().getName();
                String date = Convert.LocalDateTimeToString(app.getDateTime(), "dd/MM/yyyy");
                String temp = String.format("%s (%s)", vacName, date);
                lstModel.addElement(temp);
                appIdList.add(app.getID());
            }
        }
        String dose = null;
        if (appIdList.size() > 0) {
            dose = String.format("%d/%d", appIdList.size(), appList.get(0).getVaccine().getDose());
        }
        else {
            dose = "0/0";
        }
        lblDoseCount.setText("Dose count: " + dose);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogCert = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblAppID = new javax.swing.JLabel();
        lblVacID = new javax.swing.JLabel();
        lblVacName = new javax.swing.JLabel();
        lblCenID = new javax.swing.JLabel();
        lblCenName = new javax.swing.JLabel();
        lblStreet = new javax.swing.JLabel();
        lblCity = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblDoseNo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnMyAccount = new javax.swing.JButton();
        btnAppoint = new javax.swing.JButton();
        btnCentre = new javax.swing.JButton();
        btnVac = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstVac = new javax.swing.JList<>();
        lblDoseCount = new javax.swing.JLabel();

        dialogCert.setTitle("Detail");
        dialogCert.setModal(true);
        dialogCert.setResizable(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Appointment ID");

        jLabel2.setText("Vaccine ID");

        jLabel3.setText("Vaccine Name");

        jLabel4.setText("Centre ID");

        jLabel5.setText("Centre Name");

        jLabel6.setText("Centre Address");

        jLabel7.setText("Street");

        jLabel8.setText("City");

        jLabel9.setText("Date");

        lblAppID.setText("jLabel10");

        lblVacID.setText("jLabel11");

        lblVacName.setText("jLabel12");

        lblCenID.setText("jLabel13");

        lblCenName.setText("jLabel14");

        lblStreet.setText("jLabel15");

        lblCity.setText("jLabel16");

        lblDate.setText("jLabel17");

        jLabel10.setText("Dose No");

        lblDoseNo.setText("jLabel11");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAppID, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                    .addComponent(lblVacID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblVacName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCenID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCenName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblStreet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDoseNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblAppID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblVacID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblVacName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCenID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblCenName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblStreet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblCity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblDoseNo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dialogCertLayout = new javax.swing.GroupLayout(dialogCert.getContentPane());
        dialogCert.getContentPane().setLayout(dialogCertLayout);
        dialogCertLayout.setHorizontalGroup(
            dialogCertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dialogCertLayout.setVerticalGroup(
            dialogCertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vaccination Status");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnMyAccount.setText("My Account");
        btnMyAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMyAccountActionPerformed(evt);
            }
        });

        btnAppoint.setText("Appointment");
        btnAppoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAppointActionPerformed(evt);
            }
        });

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

        jButton8.setText("Vaccination Status");
        jButton8.setEnabled(false);

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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMyAccount)
                .addGap(18, 18, 18)
                .addComponent(btnAppoint)
                .addGap(18, 18, 18)
                .addComponent(btnCentre)
                .addGap(18, 18, 18)
                .addComponent(btnVac, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addGap(18, 18, 18)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMyAccount)
                    .addComponent(btnAppoint)
                    .addComponent(btnCentre)
                    .addComponent(btnVac)
                    .addComponent(jButton8)
                    .addComponent(btnLogout))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lstVac.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstVac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstVacMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(lstVac);

        lblDoseCount.setText("jLabel10");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                    .addComponent(lblDoseCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(lblDoseCount)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void lstVacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstVacMouseClicked
        // TODO add your handling code here:
        if (lstModel.getSize() != 0) {
            int idx = lstVac.getSelectedIndex();
            String appID = appIdList.get(idx);
            Appointment app = appDict.get(appID);
            lblAppID.setText(app.getID());
            lblVacID.setText(app.getVaccine().getID());
            lblVacName.setText(app.getVaccine().getName());
            lblCenID.setText(app.getCentre().getCentreID());
            lblCenName.setText(app.getCentre().getName());
            lblStreet.setText(app.getCentre().getAddr().getStreet());
            lblCity.setText(app.getCentre().getAddr().getCity());
            lblDate.setText(Convert.LocalDateTimeToString(app.getDateTime(), "dd/MM/yyyy HH:mm"));
            lblDoseNo.setText(String.valueOf(idx+1));
            dialogCert.setLocationRelativeTo(this);
            dialogCert.pack();
            dialogCert.setVisible(true);
        }
    }//GEN-LAST:event_lstVacMouseClicked

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new LoginForm().setVisible(true);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnMyAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMyAccountActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new UserAccount(user).setVisible(true);
    }//GEN-LAST:event_btnMyAccountActionPerformed

    private void btnAppointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAppointActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new UserAppoint(user).setVisible(true);
    }//GEN-LAST:event_btnAppointActionPerformed

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
            java.util.logging.Logger.getLogger(UserStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserStatus().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAppoint;
    private javax.swing.JButton btnCentre;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMyAccount;
    private javax.swing.JButton btnVac;
    private javax.swing.JDialog dialogCert;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAppID;
    private javax.swing.JLabel lblCenID;
    private javax.swing.JLabel lblCenName;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDoseCount;
    private javax.swing.JLabel lblDoseNo;
    private javax.swing.JLabel lblStreet;
    private javax.swing.JLabel lblVacID;
    private javax.swing.JLabel lblVacName;
    private javax.swing.JList<String> lstVac;
    // End of variables declaration//GEN-END:variables
}
