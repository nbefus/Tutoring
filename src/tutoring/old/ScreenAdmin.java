package tutoring.old;


import java.awt.FontMetrics;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.table.TableColumnModel;
import tutoring.entity.Category;
import tutoring.entity.Client;
import tutoring.entity.Course;
import tutoring.entity.Location;
import tutoring.helper.HibernateTest;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.entity.Paraprofessional;
import tutoring.entity.ParaprofessionalSession;
import tutoring.old.Term;
import tutoring.helper.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dabeefinator
 */
public class ScreenAdmin extends javax.swing.JFrame {

    Screen11 screen;
    int count = 0;
    /**
     * Creates new form Screen1
     */
    public ScreenAdmin() 
    {
        initComponents();
       // FakeValues fv = new FakeValues();
        DefaultCellEditor singleclick = new DefaultCellEditor(new JCheckBox());
        singleclick.setClickCountToStart(2);

    //set the editor as default on every column
   
        jTable1.setDefaultEditor(Boolean.class, singleclick);
        
        
       jTable1.setModel(new SessionTableModel());
       jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       jTable1.setAutoCreateRowSorter(true);
       jTable1.setFillsViewportHeight(true);
      // jTable1.getColumnModel().getColumn(10).setCellRenderer(new TimestampCellRenderer());
       
       jTable1.setDefaultRenderer(Timestamp.class, new TimestampCellRenderer());
       jTable1.getColumnModel().getColumn(9).setCellEditor(new TimestampCellEditor(new JTextField()));
       jTable1.getColumnModel().getColumn(10).setCellEditor(new TimestampCellEditor(new JTextField()));
       jTable1.getColumnModel().getColumn(11).setCellEditor(new TimestampCellEditor(new JTextField()));
       
       
       //jTable1.getColumnModel().getColumn(10).setCellRenderer(new ButtonCellRenderer());
       //jTable1.getColumnModel().getColumn(11).setCellRenderer(new ButtonCellRenderer());
       //jTable1.getColumnModel().getColumn(9).setCellEditor(new TimestampCellEditor(new JTextField()));
       //jTable1.getColumnModel().getColumn(10).setCellEditor(new ButtonCellEditor(new JCheckBox()));
       //jTable1.getColumnModel().getColumn(11).setCellEditor(new ButtonCellEditor(new JCheckBox()));
      
       
       FontMetrics fm = jTable1.getFontMetrics(jTable1.getFont());
       int fontHeight = fm.getHeight();
       jTable1.setRowHeight(fontHeight+8);
       
       
    
       
       ArrayList<Subject> subjects = (ArrayList<Subject>)HibernateTest.select("from Subject as s order by s.abbrevName");

       
       ArrayList<Paraprofessional> tutors = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p order by p.fName");


       ArrayList<Teacher> teachers = (ArrayList<Teacher>)HibernateTest.select("from Teacher as t order by t.fName");
       ArrayList<String> teacherslist = new ArrayList<String>();
              

       for(int i=0; i<teachers.size(); i++)
           teacherslist.add(teachers.get(i).getfName()+" "+teachers.get(i).getlName());
       
       
       ArrayList<String> listSomeString = new ArrayList<String>();        
        listSomeString.add("Snowboarding");
        listSomeString.add("Rowing");
        listSomeString.add("Knitting");
        listSomeString.add("Speed reading");
        listSomeString.add("Pool");
        listSomeString.add("None of the above");
       jTable1.getColumnModel().getColumn(5).setCellEditor(new ComboBoxCellEditor(listSomeString));
       
      
      // ArrayList<Term> terms = (ArrayList<Term>)HibernateTest.select("from Term");
       //String[] termslist = new String[terms.size()];
              

      // for(int i=0; i<terms.size(); i++)
      //     termslist[i]=terms.get(i).getName();
       //AutoComplete ac = new AutoComplete( jListTeacher, jTextFieldTeacher, jScrollPaneTeacher, teachers);
       //AutoCompleteComboBox accb11 = new AutoCompleteComboBox(jComboBoxTerm, termslist);
       
       
       
       ArrayList<Location> locations = (ArrayList<Location>)HibernateTest.select("from Location as l order by l.name");
       ArrayList<String> locationslist = new ArrayList<String>();
              

       for(int i=0; i<locations.size(); i++)
           locationslist.add(locations.get(i).getName());
       
       
       
       
        ArrayList<String> tutorslist = new ArrayList<String>();
       for(int i=0; i<tutors.size(); i++)
           tutorslist.add(tutors.get(i).getfName()+" "+tutors.get(i).getlName());
       
       
        ArrayList<String> subjectslist = new ArrayList<String>();
       for(int i=0; i<subjects.size(); i++)
           subjectslist.add(subjects.get(i).getAbbrevName());
       
       
       
       
       ArrayList<Client> clientFirst = (ArrayList<Client>)HibernateTest.select("from Client as c order by c.fName");
       ArrayList<Client> clientLast = (ArrayList<Client>)HibernateTest.select("from Client as c order by c.lName");
       ArrayList<String> clientsfirst = new ArrayList<String>();
       ArrayList<String> clientslast = new ArrayList<String>();

       for(int i=0; i<clientFirst.size(); i++)
           clientsfirst.add(clientFirst.get(i).getfName());
           
       for(int i=0; i<clientLast.size(); i++)
            clientslast.add(clientLast.get(i).getlName());
       
       /*
       AutoCompleteComboBox accb2 = new AutoCompleteComboBox(jComboBoxTutor, tutorslist);
       AutoCompleteComboBox accb22 = new AutoCompleteComboBox(jComboBoxCreator, tutorslist);
       AutoCompleteComboBox accb3 = new AutoCompleteComboBox(jComboBoxCourse, subjectslist);
       AutoCompleteComboBox accb111 = new AutoCompleteComboBox(jComboBoxLocation, locationslist);
       AutoCompleteComboBox accb4 = new AutoCompleteComboBox(jComboBoxClientFirstName, clientsfirst);
       AutoCompleteComboBox accb5 = new AutoCompleteComboBox(jComboBoxClientLastName, clientslast);
       AutoCompleteComboBox accb = new AutoCompleteComboBox(jComboBoxTeacher, teacherslist);
       */
       /* test ultimate
       JComboBox[] boxes = new  JComboBox[7];
       boxes[0]=jComboBoxTutor;
       boxes[1]=jComboBoxCreator;
       boxes[2]=jComboBoxCourse;
       boxes[3]=jComboBoxLocation;
       boxes[4]=jComboBoxClientFirstName;
       boxes[5]=jComboBoxClientLastName;
       boxes[6]=jComboBoxTeacher;
       
       ArrayList<ArrayList<String>> ultimateList = new ArrayList<ArrayList<String>>();
       ultimateList.add(tutorslist);
       ultimateList.add(tutorslist);
       ultimateList.add(subjectslist);
       ultimateList.add(locationslist);
       ultimateList.add(clientsfirst);
       ultimateList.add(clientslast);
       ultimateList.add(teacherslist);
       UltimateAutoComplete uac = new UltimateAutoComplete(ultimateList, boxes);
       */
       
       
       //Clients autocomplete
       JComboBox[] cboxes = new  JComboBox[2];
       cboxes[0]=jComboBoxClientFirstName;
       cboxes[1]=jComboBoxClientLastName;
       
       ArrayList<ArrayList<String>> cultimateList = new ArrayList<ArrayList<String>>();

       cultimateList.add(clientsfirst);
       cultimateList.add(clientslast);

       UltimateAutoCompleteClient uacc = new UltimateAutoCompleteClient(cultimateList, cboxes, clientFirst, clientLast);
       
       
       ArrayList<ParaprofessionalSession> sessions = (ArrayList<ParaprofessionalSession>)HibernateTest.select("from ParaprofessionalSession as ps where (ps.sessionStart IS NULL or ps.sessionEnd IS NULL) AND walkout='false'");

        if(sessions.size() > 0)
        {
            for(int i=0; i<sessions.size(); i++)
            {
                ((SessionTableModel) jTable1.getModel()).addRow(sessions.get(i)); 
            }
            
            jTable1.repaint();
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

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
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
        jComboBoxClientLastName = new javax.swing.JComboBox();
        jComboBoxClientFirstName = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxCreator = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jComboBoxLocation = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboBoxPhone = new javax.swing.JComboBox();
        jComboBoxEmail = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jComboBoxCategory = new javax.swing.JComboBox();

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
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(jTable1);

        jComboBoxClientLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxClientLastNameActionPerformed(evt);
            }
        });

        jLabel8.setText("Creator");

        jLabel9.setText("Location");

        jLabel10.setText("Phone");

        jLabel11.setText("Email");

        jComboBoxEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEmailActionPerformed(evt);
            }
        });

        jLabel12.setText("Category");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1403, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jComboBoxClientFirstName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jLabel2)
                                .add(101, 101, 101)
                                .add(jLabel10)
                                .add(55, 55, 55)
                                .add(jLabel11))
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jComboBoxClientLastName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jComboBoxPhone, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 85, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jComboBoxEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(10, 10, 10)
                                .add(jLabel3)
                                .add(35, 35, 35))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jComboBoxCourse, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jButton1)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(jLabel4)
                                        .add(39, 39, 39)
                                        .add(jLabel5)
                                        .add(110, 110, 110))
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .add(jTextFieldLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(jComboBoxTeacher, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 139, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(2, 2, 2)))
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel12)
                                    .add(jComboBoxCategory, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 109, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(jLabel6)
                                        .add(159, 159, 159)
                                        .add(jLabel7)
                                        .add(213, 213, 213)
                                        .add(jLabel8))
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(jTextFieldNotes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 159, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(18, 18, 18)
                                        .add(jComboBoxTutor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(18, 18, 18)
                                        .add(jCheckBoxFuture)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jCheckBoxGC)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jComboBoxCreator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(jComboBoxLocation, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 108, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jButton2)
                                .add(jButton3))
                            .add(jLabel9))))
                .addContainerGap(160, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jLabel2)
                    .add(jLabel3)
                    .add(jLabel4)
                    .add(jLabel5)
                    .add(jLabel7)
                    .add(jLabel8)
                    .add(jLabel9)
                    .add(jLabel10)
                    .add(jLabel11)
                    .add(jLabel6)
                    .add(jLabel12))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jComboBoxClientFirstName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jComboBoxClientLastName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jComboBoxCourse, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jComboBoxTeacher, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldNotes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jComboBoxTutor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jCheckBoxFuture)
                    .add(jCheckBoxGC)
                    .add(jComboBoxCreator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jComboBoxLocation, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jComboBoxPhone, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jComboBoxEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jComboBoxCategory, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(7, 7, 7)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton1)
                    .add(jButton2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                        .add(jButton3)
                        .add(31, 31, 31))))
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
        screen = new Screen11(ScreenAdmin.this, false);
        screen.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        //Paraprofessional t = new Paraprofessional(count, "TUTORFIRSTTEST", "TUTORLASTTEST", true);
        //Teacher teach = new Teacher(count, jComboBoxTeacher.getSelectedItem().toString(), "TestFirstName");
        //Subject sub = new Subject(count, jComboBoxCourse.getSelectedItem().toString(), "FullNameTest", new Category(count, "MABS"));
        
        ArrayList<Subject> subjects =(ArrayList<Subject>) HibernateTest.select("from Subject as s where s.abbrevName='"+jComboBoxCourse.getSelectedItem().toString()+"'");
        
        String[] tName = jComboBoxTeacher.getSelectedItem().toString().split(" ");
        String[] pName = jComboBoxTutor.getSelectedItem().toString().split(" ");
        String[] cName = jComboBoxCreator.getSelectedItem().toString().split(" ");
        String clientFName = jComboBoxClientFirstName.getSelectedItem().toString();
        String clientLName = jComboBoxClientLastName.getSelectedItem().toString();
        boolean GC = jCheckBoxGC.isSelected();
        String notes = jTextFieldNotes.getText().toString();
        String level = jTextFieldLevel.getText().toString();
        int intLevel = Integer.parseInt(level);
        String location = jComboBoxLocation.getSelectedItem().toString();
       // String term = jComboBoxTerm.getSelectedItem().toString();
        
        ArrayList<Teacher> teachers = (ArrayList<Teacher>)HibernateTest.select("from Teacher as t where t.fName='"+tName[0].trim()+"' and t.lName='"+tName[1].trim()+"'");
       
        ArrayList<Course> courses = (ArrayList<Course>)HibernateTest.select("from Course as c where c.subjectID="+subjects.get(0).getSubjectID()+" and c.teacherID="+teachers.get(0).getTeacherID() + " and c.level="+intLevel);
        
        ArrayList<Paraprofessional> paraprofessionals = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p where p.fName='"+pName[0].trim()+"' and p.lName='"+pName[1].trim()+"'");

        ArrayList<Paraprofessional> creators = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p where p.fName='"+cName[0].trim()+"' and p.lName='"+cName[1].trim()+"'");

        ArrayList<Client> clients = (ArrayList<Client>)HibernateTest.select("from Client as c where c.fName='"+clientFName.trim()+"' and c.lName='"+clientLName.trim()+"'");

        ArrayList<Location> locations = (ArrayList<Location>)HibernateTest.select("from Location as l where l.name='"+location.trim()+"'");
        
      //  ArrayList<Term> terms = (ArrayList<Term>)HibernateTest.select("from Term as t where t.name='"+term.trim()+"'");

        
        if(subjects.size() <= 0)
            System.out.println("Subjects less than 1");
        if(teachers.size() <= 0)
            System.out.println("Teachers less than 1");
        if(courses.size() <= 0)
            System.out.println("Courses less than 1");
        if(paraprofessionals.size() <= 0)
            System.out.println("Paraprofessionals less than 1");
        if(creators.size() <= 0)
            System.out.println("Creators less than 1");
        if(clients.size() <= 0)
            System.out.println("Clients less than 1");
        if(locations.size() <= 0)
            System.out.println("Locations less than 1");
      //  if(terms.size() <= 0)
      //      System.out.println("Terms less than 1");
        Timestamp now = new Timestamp((new Date()).getTime());
        
        ParaprofessionalSession ps = new ParaprofessionalSession(-1, paraprofessionals.get(0), clients.get(0), courses.get(0), locations.get(0), creators.get(0), now, null, null, GC, notes, false);
        HibernateTest.create(ps);
        
        System.out.println("NOW: "+now.toString());
        String query = "from ParaprofessionalSession as ps where ps.paraprofessionalID="+paraprofessionals.get(0).getParaprofessionalID()+" and ps.clientID="+clients.get(0).getClientID()+" and ps.courseID="+courses.get(0).getCourseID()+" and ps.locationID="+locations.get(0).getLocationID()+" and ps.paraprofessionalCreatorID="+creators.get(0).getParaprofessionalID()+" and ps.timeAndDateEntered='"+now.toString()+"' and ps.sessionStart IS NULL and ps.sessionEnd IS NULL and ps.grammarCheck='"+GC+"' and ps.notes='"+notes+"' and ps.walkout='false'";
        
        System.out.println(query);
        ArrayList<ParaprofessionalSession> sessions = (ArrayList<ParaprofessionalSession>)HibernateTest.select(query);

        
        
        if(sessions.size() <=0)
            System.out.println("SESSION WAS NOT CREATED ERROR");
        else
            System.out.println("ID: "+sessions.get(0).getParaprofessionalSessionID());
        ((SessionTableModel) jTable1.getModel()).addRow(sessions.get(0));
        jTable1.repaint();
        count++;

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBoxClientLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxClientLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxClientLastNameActionPerformed

    private void jComboBoxEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEmailActionPerformed

    
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
            java.util.logging.Logger.getLogger(ScreenAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ScreenAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ScreenAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScreenAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ScreenAdmin().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBoxFuture;
    private javax.swing.JCheckBox jCheckBoxGC;
    private javax.swing.JComboBox jComboBoxCategory;
    private javax.swing.JComboBox jComboBoxClientFirstName;
    private javax.swing.JComboBox jComboBoxClientLastName;
    private javax.swing.JComboBox jComboBoxCourse;
    private javax.swing.JComboBox jComboBoxCreator;
    private javax.swing.JComboBox jComboBoxEmail;
    private javax.swing.JComboBox jComboBoxLocation;
    private javax.swing.JComboBox jComboBoxPhone;
    private javax.swing.JComboBox jComboBoxTeacher;
    private javax.swing.JComboBox jComboBoxTutor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldLevel;
    private javax.swing.JTextField jTextFieldNotes;
    // End of variables declaration//GEN-END:variables
}
