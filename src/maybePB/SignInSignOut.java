package maybePB;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import tutoring.entity.*;
import tutoring.helper.HibernateTest;

public class SignInSignOut extends javax.swing.JFrame
{

    public SignInSignOut()
    {
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        errorLabel = new javax.swing.JLabel();
        nameCombo = new javax.swing.JComboBox();
        signInAndOutButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(603, 503));
        setPreferredSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Sign In/Out", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        nameLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nameLabel.setText("Name:");

        errorLabel.setForeground(java.awt.Color.red);
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorLabel.setEnabled(false);

        nameCombo.setEditable(false);
        nameCombo.setEnabled(true);
        nameCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                nameComboPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                nameComboPopupMenuWillBecomeVisible(evt);
            }
        });
        nameCombo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nameComboKeyPressed(evt);
            }
        });

        signInAndOutButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        signInAndOutButton.setForeground(new java.awt.Color(0, 153, 0));
        signInAndOutButton.setText("Status");
        signInAndOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signInAndOutButtonActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/preciousAreaSoNoMergeHappensLOL/pmslogo.PNG"))); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(nameLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(nameCombo, 0, 436, Short.MAX_VALUE))
                    .add(errorLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(signInAndOutButton)))
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jLabel1)
                .add(18, 18, 18)
                .add(errorLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(14, 14, 14)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(nameLabel)
                    .add(nameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(signInAndOutButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new java.awt.GridBagConstraints());

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nameComboPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_nameComboPopupMenuWillBecomeVisible

        for (int i = 0; i < list.size(); i++)
        {
            listOfParaprofessionals.add(list.get(i).getlName() + ", " + list.get(i).getfName());
        }

        nameCombo.setModel(new DefaultComboBoxModel(listOfParaprofessionals.toArray()));
    }//GEN-LAST:event_nameComboPopupMenuWillBecomeVisible

    private void nameComboPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_nameComboPopupMenuWillBecomeInvisible

        if ((list.get(nameCombo.getSelectedIndex()).isIsClockedIn()) == false)
        {
            signInAndOutButton.setText("Sign In");
            errorLabel.setText("Hi " + list.get(nameCombo.getSelectedIndex()).getfName() + ", you are currently logged out of PMS. Please sign in.");
        } else
        {
            signInAndOutButton.setText("Sign Out");
            errorLabel.setText("Hi " + list.get(nameCombo.getSelectedIndex()).getfName() + ", you are currently logged in PMS. Please sign out when you leave.");
        }

    }//GEN-LAST:event_nameComboPopupMenuWillBecomeInvisible

    private void nameComboKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameComboKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            signInAndOutButtonActionPerformed((java.awt.event.ActionEvent) signInAndOutButton.getAction());
        }
    }//GEN-LAST:event_nameComboKeyPressed

    private void signInAndOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signInAndOutButtonActionPerformed

        list.get(nameCombo.getSelectedIndex()).setIsClockedIn(!(list.get(nameCombo.getSelectedIndex()).isIsClockedIn()));
        HibernateTest.update(list.get(nameCombo.getSelectedIndex()));

        if ((list.get(nameCombo.getSelectedIndex()).isIsClockedIn()) == false)
        {
            signInAndOutButton.setText("Sign In");
            errorLabel.setText(list.get(nameCombo.getSelectedIndex()).getfName() + ", thank you for remembering to log out of PMS. Have a good day!");
        } else
        {
            signInAndOutButton.setText("Sign Out");
            errorLabel.setText(list.get(nameCombo.getSelectedIndex()).getfName() + ", you are currently logged in of PMS. Please sign out when you leave.");
        }

    }//GEN-LAST:event_signInAndOutButtonActionPerformed

    public static void main(String args[])
    {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(SignInSignOut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(SignInSignOut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(SignInSignOut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(SignInSignOut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new SignInSignOut().setVisible(true);
            }
        });
    }
    private ArrayList listOfParaprofessionals = new ArrayList();
    private ArrayList<Paraprofessional> list = (ArrayList<Paraprofessional>) HibernateTest.select("from Paraprofessional where terminationDate is null");
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox nameCombo;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton signInAndOutButton;
    // End of variables declaration//GEN-END:variables
}
