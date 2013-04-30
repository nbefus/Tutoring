/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.dialogs;

import java.awt.Color;
import java.awt.Window;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import tutoring.entity.Category;
import tutoring.entity.Location;
import tutoring.helper.DatabaseHelper;

/**
 *
 * @author Nathaniel
 */
public class NewLocationObject extends javax.swing.JDialog {

    /**
     * Creates new form NewLocationObject
     */
   private int locationID = -1;
    public NewLocationObject(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.setResizable(false);
      
        editButton.setVisible(false);
        
    }
    
    public NewLocationObject(java.awt.Frame parent, boolean modal, String location, int locationID) {
        super(parent, modal);
        initComponents();
        
        locationField.setText(location);
        editButton.setVisible(true);
        this.locationID=locationID;
    }
    
    private void close()
    {
        Window win = SwingUtilities.getWindowAncestor(this);
        if (win != null) {
           win.dispose();
        }
    }
    
    private void validate(boolean update)
    {
        locationField.setBorder(null);
        
       
        String location = locationField.getText().trim();
        
        try
        {
            boolean goodLocation = true;
            if(location.length() < 1)
            {
                goodLocation = false;
                locationField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            
            
            if(goodLocation)
            {
                
                Location l = new Location(locationID, location);
                System.out.println(l.toString());
                boolean inserted = false;
                DatabaseHelper.open();
                if(!update)
                    inserted = DatabaseHelper.insert(Location.getValues(l), Location.LocationTable.getTable());
                else
                    inserted = DatabaseHelper.update(Location.getValues(l), Location.LocationTable.getTable());
                //Reload data and table
                
                if(inserted)
                    JOptionPane.showMessageDialog(null, "The location was successfully written to the database!");
                else
                    JOptionPane.showMessageDialog(null, "The location was NOT created! Please try again!");
                close();
                
            }

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "The location was NOT created! Please try again!");
        }
        finally
        {
            DatabaseHelper.close();
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

        searchagendacategoryPanel = new javax.swing.JPanel();
        levelLabel11 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        submitbutton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        locationField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        searchagendacategoryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Location Information"));

        levelLabel11.setText("Location*");

        cancelButton.setForeground(new java.awt.Color(153, 0, 0));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        submitbutton.setForeground(new java.awt.Color(51, 102, 255));
        submitbutton.setText("Create");
        submitbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitbuttonActionPerformed(evt);
            }
        });

        editButton.setText("Save/Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchagendacategoryPanelLayout = new javax.swing.GroupLayout(searchagendacategoryPanel);
        searchagendacategoryPanel.setLayout(searchagendacategoryPanelLayout);
        searchagendacategoryPanelLayout.setHorizontalGroup(
            searchagendacategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchagendacategoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchagendacategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchagendacategoryPanelLayout.createSequentialGroup()
                        .addComponent(levelLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(locationField))
                    .addGroup(searchagendacategoryPanelLayout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(submitbutton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchagendacategoryPanelLayout.setVerticalGroup(
            searchagendacategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchagendacategoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchagendacategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(levelLabel11)
                    .addComponent(locationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(searchagendacategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton)
                    .addGroup(searchagendacategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(submitbutton)
                        .addComponent(editButton)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchagendacategoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchagendacategoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        close();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void submitbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitbuttonActionPerformed

        validate(false);
    }//GEN-LAST:event_submitbuttonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed

        validate(true);
    }//GEN-LAST:event_editButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel levelLabel11;
    private javax.swing.JTextField locationField;
    private javax.swing.JPanel searchagendacategoryPanel;
    private javax.swing.JButton submitbutton;
    // End of variables declaration//GEN-END:variables
}
