package tutoring.ui;


import java.awt.FontMetrics;
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.table.TableColumnModel;
import tutoring.entity.Subject;
import tutoring.entity.SubjectCategory;
import tutoring.entity.Teacher;
import tutoring.entity.Tutor;
import tutoring.helper.AutoComplete;
import tutoring.helper.AutoCompleteComboBox;
import tutoring.helper.FakeValues;
import tutoring.helper.SessionTableModel;
import tutoring.helper.TimestampCellEditor;
import tutoring.helper.TimestampCellRenderer;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dabeefinator
 */
public class Screen1 extends javax.swing.JFrame {

    Screen11 screen;
    int count = 0;
    /**
     * Creates new form Screen1
     */
    public Screen1() {
        initComponents();
        FakeValues fv = new FakeValues();
        
       jTable1.setModel(new SessionTableModel());
       jTable1.setAutoCreateRowSorter(true);
       jTable1.setFillsViewportHeight(true);
      // jTable1.getColumnModel().getColumn(10).setCellRenderer(new TimestampCellRenderer());
       jTable1.setDefaultRenderer(Timestamp.class, new TimestampCellRenderer());
       jTable1.getColumnModel().getColumn(10).setCellEditor(new TimestampCellEditor(new JTextField()));
       
       FontMetrics fm = jTable1.getFontMetrics(jTable1.getFont());
       int fontHeight = fm.getHeight();
       jTable1.setRowHeight(fontHeight+8);
       
       
       for(int i=0; i<fv.getTutorSessions().size(); i++)
        ((SessionTableModel) jTable1.getModel()).addRow(fv.getTutorSessions().get(i));
       
       MutableComboBoxModel mcbm = (MutableComboBoxModel)jComboBoxCourse.getModel();
       
       for(int i=0; i<fv.getSubjects().size(); i++)
           mcbm.addElement(fv.getSubjects().get(i).getAbbrevName());
       
       
       MutableComboBoxModel mcbm2 = (MutableComboBoxModel)jComboBoxTutor.getModel();
       
       for(int i=0; i<fv.getSubjects().size(); i++)
           mcbm2.addElement(fv.getTutors().get(i).getfName()+" "+fv.getTutors().get(i).getlName());
       
    
       String[] teachers = new String[fv.getTeachers().size()];
       for(int i=0; i<teachers.length; i++)
           teachers[i]=fv.getTeachers().get(i).getfName()+" "+fv.getTeachers().get(i).getlName();
       //AutoComplete ac = new AutoComplete( jListTeacher, jTextFieldTeacher, jScrollPaneTeacher, teachers);
       AutoCompleteComboBox accb = new AutoCompleteComboBox(jComboBoxTeacher, teachers);
       
       
       
        String[] tutors = new String[fv.getTutors().size()];
       for(int i=0; i<tutors.length; i++)
           tutors[i]=fv.getTutors().get(i).getfName()+" "+fv.getTutors().get(i).getlName();
       //AutoComplete ac = new AutoComplete( jListTeacher, jTextFieldTeacher, jScrollPaneTeacher, teachers);
       AutoCompleteComboBox accb2 = new AutoCompleteComboBox(jComboBoxTutor, tutors);
       
       
        String[] subjects = new String[fv.getSubjects().size()];
       for(int i=0; i<subjects.length; i++)
           subjects[i]=fv.getSubjects().get(i).getAbbrevName();
       //AutoComplete ac = new AutoComplete( jListTeacher, jTextFieldTeacher, jScrollPaneTeacher, teachers);
       AutoCompleteComboBox accb3 = new AutoCompleteComboBox(jComboBoxCourse, subjects);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldFName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextFieldLName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxCourse = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldLevel = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldNotes = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxTutor = new javax.swing.JComboBox();
        jCheckBoxFuture = new javax.swing.JCheckBox();
        jCheckBoxGC = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBoxTeacher = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("fname");

        jLabel2.setText("lname");

        jButton1.setText("Add Session");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("jButton3");

        jLabel3.setText("course");

        jLabel4.setText("course#");

        jLabel5.setText("teacher");

        jLabel6.setText("Notes");

        jLabel7.setText("tutor");

        jCheckBoxFuture.setText("Future");
        jCheckBoxFuture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxFutureActionPerformed(evt);
            }
        });

        jCheckBoxGC.setText("GC");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jButton3)
                .add(18, 18, 18))
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldFName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldLName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jComboBoxCourse, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jComboBoxTeacher, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldNotes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel7)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jComboBoxTutor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jCheckBoxFuture)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jCheckBoxGC)
                        .add(18, 18, 18)
                        .add(jButton1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton2))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(19, 19, 19)
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1004, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(190, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(18, 18, 18)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jTextFieldFName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2)
                    .add(jTextFieldLName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(jComboBoxCourse, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4)
                    .add(jTextFieldLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5)
                    .add(jLabel6)
                    .add(jTextFieldNotes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7)
                    .add(jComboBoxTutor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jCheckBoxFuture)
                    .add(jCheckBoxGC)
                    .add(jButton1)
                    .add(jButton2)
                    .add(jComboBoxTeacher, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 132, Short.MAX_VALUE)
                .add(jButton3)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBoxFutureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFutureActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxFutureActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        screen = new Screen11(Screen1.this, false);
        screen.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Tutor t = new Tutor(count, "TUTORFIRSTTEST", "TUTORLASTTEST", true);
        Teacher teach = new Teacher(count, jComboBoxTeacher.getSelectedItem().toString(), "TestFirstName");
        Subject sub = new Subject(count, jComboBoxCourse.getSelectedItem().toString(), "FullNameTest", new SubjectCategory(count, "MABS"));

        ((SessionTableModel) jTable1.getModel()).addRow(jTextFieldFName.getText(), jTextFieldLName.getText(), sub, Integer.parseInt(jTextFieldLevel.getText()), teach, jTextFieldNotes.getText(), t, jCheckBoxFuture.isSelected(), jCheckBoxGC.isSelected());
        jTable1.repaint();
        count++;

    }//GEN-LAST:event_jButton1ActionPerformed

    
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
            java.util.logging.Logger.getLogger(Screen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Screen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Screen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Screen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Screen1().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBoxFuture;
    private javax.swing.JCheckBox jCheckBoxGC;
    private javax.swing.JComboBox jComboBoxCourse;
    private javax.swing.JComboBox jComboBoxTeacher;
    private javax.swing.JComboBox jComboBoxTutor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldFName;
    private javax.swing.JTextField jTextFieldLName;
    private javax.swing.JTextField jTextFieldLevel;
    private javax.swing.JTextField jTextFieldNotes;
    // End of variables declaration//GEN-END:variables
}
