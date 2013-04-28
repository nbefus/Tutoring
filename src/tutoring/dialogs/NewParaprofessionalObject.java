/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.dialogs;

import java.awt.Color;
import java.awt.Window;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.text.JTextComponent;
import tutoring.entity.Category;
import tutoring.entity.Course;
import tutoring.entity.Paraprofessional;
import tutoring.entity.Role;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.helper.DatabaseHelper;

/**
 *
 * @author Nathaniel
 */
public class NewParaprofessionalObject extends javax.swing.JDialog {

    /**
     * Creates new form NewParaprofessionalObject
     */
    private int paraprofessionalID = -1;
    public NewParaprofessionalObject(java.awt.Frame parent, boolean modal, ArrayList<String> roles, ArrayList<String> categories) {
        super(parent, modal);
        initComponents();
        
        roleCombo.setEditable(true);
        categoryCombo.setEditable(true);
        
        
        this.setResizable(false);
               
        roleCombo.setModel(new DefaultComboBoxModel(roles.toArray()));
        roleCombo.setSelectedIndex(0);
        
        categoryCombo.setModel(new DefaultComboBoxModel(categories.toArray()));
        categoryCombo.setSelectedIndex(0);
        
        editButton.setVisible(false);
        
    }
    
    public NewParaprofessionalObject(java.awt.Frame parent, boolean modal, ArrayList<String> roles, ArrayList<String> categories, String role, String category, String fname, String lname, String clockedIn, String hireDate, String terminationDate, int paraprofessionalID) {
        super(parent, modal);
        initComponents();
        
        roleCombo.setEditable(true);
        categoryCombo.setEditable(true);
        
        
        this.setResizable(false);
               
        roleCombo.setModel(new DefaultComboBoxModel(roles.toArray()));
        roleCombo.setSelectedIndex(roles.indexOf(role));
        
        categoryCombo.setModel(new DefaultComboBoxModel(categories.toArray()));
        categoryCombo.setSelectedIndex(categories.indexOf(category));
        
        clockedInCombo.setSelectedIndex(((DefaultComboBoxModel)clockedInCombo.getModel()).getIndexOf(category));
        
        editButton.setVisible(true);
        
        fnameField.setText(fname);
        lnameField.setText(lname);
        hireField.setText(hireDate);
        terminationField.setText(terminationDate);
               
        this.paraprofessionalID=paraprofessionalID;
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
        lnameField.setBorder(null);
        fnameField.setBorder(null);
        hireField.setBorder(null);
        terminationField.setBorder(null);
        roleCombo.setBorder(null);
        categoryCombo.setBorder(null);
        
        String lname = lnameField.getText().trim();
        String fname = fnameField.getText().trim();
        String hireDate = hireField.getText().trim();
        String terminationDate = terminationField.getText().trim();
        String role = ((JTextComponent) roleCombo.getEditor().getEditorComponent()).getText();
        String categories = ((JTextComponent) categoryCombo.getEditor().getEditorComponent()).getText();
        String clockedIn = clockedInCombo.getSelectedItem().toString();
        
        Date hire = null;
        Date term = null;
        
        try
        {
            boolean goodFirst = true;
            if(fname.length() < 1)
            {
                goodFirst = false;
                fnameField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            boolean goodLast = true;
            if(lname.length() < 1)
            {
                goodLast = false;
                lnameField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            boolean goodHire = true;
            if(hireDate.length() > 0)
            {
                try
                {
                    SimpleDateFormat sdf  = new SimpleDateFormat("MM/dd/yyyy");
                    sdf.setLenient(false);
                    hire = sdf.parse(hireDate);
                }
                catch(Exception e)
                {
                    goodHire = false;
                    hireField.setBorder(new MatteBorder(3,3,3,3,Color.red));
                }
            } 
            
            boolean goodTermination = true;
            if(terminationDate.length() > 0 && !terminationDate.contains("yyyy"))
            {
                try
                {
                    SimpleDateFormat sdf  = new SimpleDateFormat("MM/dd/yyyy");
                    sdf.setLenient(false);
                    term = sdf.parse(terminationDate);
                }
                catch(Exception e)
                {
                    goodTermination = false;
                    terminationField.setBorder(new MatteBorder(3,3,3,3,Color.red));
                }
            } 
            
            boolean goodRole = true;
            
            DatabaseHelper.open();
            ArrayList<Role> validRole = (ArrayList<Role>)Role.selectAllRoles("where "+Role.RoleTable.TYPE.getWithAlias()+"='"+role+"'", DatabaseHelper.getConnection());
            
            if(validRole.size() != 1)
            {
                goodRole = false;
                roleCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            String categoryString = "";
            String[] categoryArray = categories.split(",");
            
            for(int i=0; i<categoryArray.length; i++)
                categoryString += "'"+categoryArray[i]+"', ";
            categoryString = categoryString.substring(0,categoryString.length()-2);
            
            boolean goodCategory = true;
            ArrayList<Subject> validCategories = (ArrayList<Subject>)Subject.selectAllSubjects("where "+Category.CategoryTable.NAME.getWithAlias()+" in ("+categoryString+")", DatabaseHelper.getConnection());
            
            if(validCategories.size() != categoryArray.length)
            {
                goodCategory = false;
                categoryCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }  
            
            if(goodLast && goodFirst && goodHire && goodTermination && goodCategory && goodRole)
            {
                
                Paraprofessional p = new Paraprofessional(paraprofessionalID, validRole.get(0), lname, fname, hire, term, Boolean.parseBoolean(clockedIn));
                System.out.println(p.toString());

                if(!update)
                    DatabaseHelper.insert(Paraprofessional.getValues(p), Paraprofessional.ParaTable.getTable());
                else
                    DatabaseHelper.update(Paraprofessional.getValues(p), Paraprofessional.ParaTable.getTable());
                //Reload data and table
                
                JOptionPane.showMessageDialog(null, "The paraprofessioanl was successfully written to the database!");
                
                close();
                
            }

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "The paraprofessional was NOT created! Please try again!");
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

        searchparaprofessionalPanel = new javax.swing.JPanel();
        courseLabel3 = new javax.swing.JLabel();
        levelLabel3 = new javax.swing.JLabel();
        teacherLabel3 = new javax.swing.JLabel();
        categoryLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        categoryCombo = new javax.swing.JComboBox();
        hireField = new javax.swing.JTextField();
        terminationField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        clockedInCombo = new javax.swing.JComboBox();
        fnameField = new javax.swing.JTextField();
        lnameField = new javax.swing.JTextField();
        roleCombo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        submitbutton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        searchparaprofessionalPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Paraprofessional Information"));

        courseLabel3.setText("First Name*");

        levelLabel3.setText("Last Name*");

        teacherLabel3.setText("hireDate*");

        categoryLabel3.setText("terminationDate:");

        jLabel1.setText("Role*");

        categoryCombo.setEditable(true);

        hireField.setText("dd/mm/yyyy");
        hireField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hireFieldActionPerformed(evt);
            }
        });

        terminationField.setText("dd/mm/yyyy");

        jLabel3.setText("Clocked In*");

        clockedInCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "True", "False" }));

        roleCombo.setEditable(true);

        jLabel2.setText("Category");

        jLabel4.setText("Optional Fields");

        cancelButton.setForeground(new java.awt.Color(153, 0, 0));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        editButton.setText("Save/Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        submitbutton.setForeground(new java.awt.Color(51, 102, 255));
        submitbutton.setText("Create");
        submitbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitbuttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchparaprofessionalPanelLayout = new javax.swing.GroupLayout(searchparaprofessionalPanel);
        searchparaprofessionalPanel.setLayout(searchparaprofessionalPanelLayout);
        searchparaprofessionalPanelLayout.setHorizontalGroup(
            searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchparaprofessionalPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(teacherLabel3)
                    .addComponent(levelLabel3)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(categoryLabel3)
                    .addComponent(courseLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lnameField)
                    .addComponent(clockedInCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fnameField)
                    .addComponent(terminationField)
                    .addComponent(roleCombo, 0, 152, Short.MAX_VALUE)
                    .addComponent(hireField)
                    .addComponent(categoryCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchparaprofessionalPanelLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(cancelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(submitbutton)
                .addContainerGap())
        );
        searchparaprofessionalPanelLayout.setVerticalGroup(
            searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchparaprofessionalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseLabel3)
                    .addComponent(fnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(levelLabel3)
                    .addComponent(lnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teacherLabel3)
                    .addComponent(hireField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(clockedInCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(roleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(categoryCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryLabel3)
                    .addComponent(terminationField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton)
                    .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(submitbutton)
                        .addComponent(editButton)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 295, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(searchparaprofessionalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 377, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(searchparaprofessionalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        close();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed

        validate(true);

    }//GEN-LAST:event_editButtonActionPerformed

    private void submitbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitbuttonActionPerformed

        validate(false);
    }//GEN-LAST:event_submitbuttonActionPerformed

    private void hireFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hireFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hireFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox categoryCombo;
    private javax.swing.JLabel categoryLabel3;
    private javax.swing.JComboBox clockedInCombo;
    private javax.swing.JLabel courseLabel3;
    private javax.swing.JButton editButton;
    private javax.swing.JTextField fnameField;
    private javax.swing.JTextField hireField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel levelLabel3;
    private javax.swing.JTextField lnameField;
    private javax.swing.JComboBox roleCombo;
    private javax.swing.JPanel searchparaprofessionalPanel;
    private javax.swing.JButton submitbutton;
    private javax.swing.JLabel teacherLabel3;
    private javax.swing.JTextField terminationField;
    // End of variables declaration//GEN-END:variables
}