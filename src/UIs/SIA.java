/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIs;

/**
 *
 * @author shohe_i
 */
public class SIA extends javax.swing.JFrame {

    /**
     * Creates new form SIA
     */
    public SIA() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        coursePanel = new javax.swing.JPanel();
        pPanel = new javax.swing.JPanel();
        fnameLabel = new javax.swing.JLabel();
        fnameCombo = new javax.swing.JComboBox();
        lnameLabel = new javax.swing.JLabel();
        lnameCombo = new javax.swing.JComboBox();
        emailLabel = new javax.swing.JLabel();
        phoneCombo = new javax.swing.JComboBox();
        phoneLabel = new javax.swing.JLabel();
        emailCombo = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        courseLabel = new javax.swing.JLabel();
        courseCombo = new javax.swing.JComboBox();
        levelLabel = new javax.swing.JLabel();
        levelCombo = new javax.swing.JComboBox();
        teacherLabel = new javax.swing.JLabel();
        teacherCombo = new javax.swing.JComboBox();
        categoryLabel = new javax.swing.JLabel();
        categoryCombo = new javax.swing.JComboBox();
        locationLabel = new javax.swing.JLabel();
        locationCombo = new javax.swing.JComboBox();
        creatorLabel = new javax.swing.JLabel();
        creatorField = new javax.swing.JTextField();
        notesLabel = new javax.swing.JLabel();
        notesField = new javax.swing.JTextField();
        gcCheck = new javax.swing.JCheckBox();
        walkoutCheck = new javax.swing.JCheckBox();
        futureCheck = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        futureField = new javax.swing.JTextField();
        addSessionbutton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();

        org.jdesktop.layout.GroupLayout jDialog1Layout = new org.jdesktop.layout.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        fnameLabel.setText("First Name:");

        fnameCombo.setEditable(true);

        lnameLabel.setText("Last Name:");

        lnameCombo.setEditable(true);

        emailLabel.setText("Email:");

        phoneCombo.setEditable(true);

        phoneLabel.setText("Phone:");

        emailCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout pPanelLayout = new org.jdesktop.layout.GroupLayout(pPanel);
        pPanel.setLayout(pPanelLayout);
        pPanelLayout.setHorizontalGroup(
            pPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(fnameLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(fnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(lnameLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(63, 63, 63)
                .add(emailLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(phoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 295, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(63, 63, 63)
                .add(phoneLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(emailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pPanelLayout.setVerticalGroup(
            pPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pPanelLayout.createSequentialGroup()
                .add(pPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(emailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(phoneLabel)
                        .add(phoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(emailLabel)
                        .add(lnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(lnameLabel))
                    .add(pPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(fnameLabel)
                        .add(fnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(6, 6, 6))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Other Information"));

        courseLabel.setText("Course:");

        courseCombo.setEditable(true);

        levelLabel.setText("Course#:");

        levelCombo.setEditable(true);

        teacherLabel.setText("Teacher:");

        teacherCombo.setEditable(true);
        teacherCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teacherComboActionPerformed(evt);
            }
        });

        categoryLabel.setText("Category:");

        categoryCombo.setEditable(true);

        locationLabel.setText("Location:");

        creatorLabel.setText("Creator:");

        creatorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creatorFieldActionPerformed(evt);
            }
        });

        notesLabel.setText("Notes:");

        gcCheck.setText("GC");

        walkoutCheck.setText("Walkout");

        futureCheck.setText("Future");

        jLabel1.setText("Schedule:");

        addSessionbutton.setForeground(new java.awt.Color(51, 102, 255));
        addSessionbutton.setText("Add Session");
        addSessionbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addSessionbuttonMouseClicked(evt);
            }
        });

        clearButton.setForeground(new java.awt.Color(153, 0, 0));
        clearButton.setText("Clear");
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearButtonMouseClicked(evt);
            }
        });
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(courseLabel)
                    .add(creatorLabel))
                .add(27, 27, 27)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(creatorField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(notesLabel)
                        .add(18, 18, 18)
                        .add(notesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(gcCheck)
                        .add(18, 18, 18)
                        .add(walkoutCheck)
                        .add(12, 12, 12)
                        .add(futureCheck)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jLabel1))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(courseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(30, 30, 30)
                        .add(levelLabel)
                        .add(18, 18, 18)
                        .add(levelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(36, 36, 36)
                        .add(teacherLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(teacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 203, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(50, 50, 50)
                        .add(categoryLabel)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(categoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(futureField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(23, 23, 23)
                        .add(addSessionbutton)
                        .add(18, 18, 18)
                        .add(clearButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(locationLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(locationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(0, 7, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel)
                    .add(courseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel)
                    .add(teacherLabel)
                    .add(teacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(categoryLabel)
                    .add(categoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(locationLabel)
                    .add(locationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(creatorField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(creatorLabel)
                    .add(notesLabel)
                    .add(notesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(gcCheck)
                    .add(walkoutCheck)
                    .add(futureCheck)
                    .add(jLabel1)
                    .add(futureField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(addSessionbutton)
                    .add(clearButton))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Current Sessions"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "First", "Last", "Email", "Phone"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Delete Session");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 337, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout coursePanelLayout = new org.jdesktop.layout.GroupLayout(coursePanel);
        coursePanel.setLayout(coursePanelLayout);
        coursePanelLayout.setHorizontalGroup(
            coursePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(coursePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(coursePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        coursePanelLayout.setVerticalGroup(
            coursePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(coursePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(pPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 56, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(1, 1, 1)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .add(coursePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(coursePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Sessions", jPanel2);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 1280, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 594, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Agenda", jPanel4);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jTabbedPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 972, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 644, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void creatorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creatorFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_creatorFieldActionPerformed

    private void teacherComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacherComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_teacherComboActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButtonActionPerformed

    private void addSessionbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSessionbuttonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addSessionbuttonMouseClicked

    private void clearButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SIA().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSessionbutton;
    private javax.swing.JComboBox categoryCombo;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JButton clearButton;
    private javax.swing.JComboBox courseCombo;
    private javax.swing.JLabel courseLabel;
    private javax.swing.JPanel coursePanel;
    private javax.swing.JTextField creatorField;
    private javax.swing.JLabel creatorLabel;
    private javax.swing.JComboBox emailCombo;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JComboBox fnameCombo;
    private javax.swing.JLabel fnameLabel;
    private javax.swing.JCheckBox futureCheck;
    private javax.swing.JTextField futureField;
    private javax.swing.JCheckBox gcCheck;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox levelCombo;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JComboBox lnameCombo;
    private javax.swing.JLabel lnameLabel;
    private javax.swing.JComboBox locationCombo;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JTextField notesField;
    private javax.swing.JLabel notesLabel;
    private javax.swing.JPanel pPanel;
    private javax.swing.JComboBox phoneCombo;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JComboBox teacherCombo;
    private javax.swing.JLabel teacherLabel;
    private javax.swing.JCheckBox walkoutCheck;
    // End of variables declaration//GEN-END:variables
}
