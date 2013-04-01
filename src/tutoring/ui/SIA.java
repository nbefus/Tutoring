/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.ui;

import UIs.*;
import java.awt.FontMetrics;
import java.awt.event.MouseWheelListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import tutoring.entity.Category;
import tutoring.entity.Client;
import tutoring.entity.Course;
import tutoring.entity.Location;
import tutoring.entity.Paraprofessional;
import tutoring.entity.ParaprofessionalSession;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.helper.*;

/**
 *
 * @author shohe_i
 */
public class SIA extends javax.swing.JFrame {

    /**
     * Creates new form SIA
     */
    private UltimateAutoComplete uac; 
    private UltimateAutoCompleteClientNew uacc;
    public SIA() {
        initComponents();
        DefaultCellEditor singleclick = new DefaultCellEditor(new JCheckBox());
        singleclick.setClickCountToStart(2);
       // DefaultCellEditor singleclickCombo = new DefaultCellEditor(new JComboBox());
        //singleclickCombo.setClickCountToStart(2);
    //set the editor as default on every column
   
        sessionsTable.setDefaultEditor(Boolean.class, singleclick);
        
        
       sessionsTable.setModel(new SessionTableModel());
       sessionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       sessionsTable.setAutoCreateRowSorter(true);
       sessionsTable.setFillsViewportHeight(true);
      // sessionsTable.getColumnModel().getColumn(10).setCellRenderer(new TimestampCellRenderer());
       
       sessionsTable.setDefaultRenderer(Timestamp.class, new TimestampCellRenderer());
       sessionsTable.setDefaultEditor(Timestamp.class, new TimestampCellEditor(new JTextField()));

       
       
    //   sessionsTable.getColumnModel().getColumn(11).setCellEditor(new TimestampCellEditor(new JTextField()));
    //   sessionsTable.getColumnModel().getColumn(12).setCellEditor(new TimestampCellEditor(new JTextField()));
    //   sessionsTable.getColumnModel().getColumn(13).setCellEditor(new TimestampCellEditor(new JTextField()));
       //sessionsTable.getColumnModel().getColumn(11).setCellRenderer(new TimestampCellRenderer());
       //sessionsTable.getColumnModel().getColumn(12).setCellRenderer(new TimestampCellRenderer());
      // sessionsTable.getColumnModel().getColumn(13).setCellRenderer(new TimestampCellRenderer());
       
       //sessionsTable.getColumnModel().getColumn(10).setCellRenderer(new ButtonCellRenderer());
       //sessionsTable.getColumnModel().getColumn(11).setCellRenderer(new ButtonCellRenderer());
       //sessionsTable.getColumnModel().getColumn(9).setCellEditor(new TimestampCellEditor(new JTextField()));
       //sessionsTable.getColumnModel().getColumn(10).setCellEditor(new ButtonCellEditor(new JCheckBox()));
       //sessionsTable.getColumnModel().getColumn(11).setCellEditor(new ButtonCellEditor(new JCheckBox()));
      
       
       FontMetrics fm = sessionsTable.getFontMetrics(sessionsTable.getFont());
       int fontHeight = fm.getHeight();
       sessionsTable.setRowHeight(fontHeight+12);
       
       
    
       
       
for (MouseWheelListener listener : sessionsTableScrollPanel.getMouseWheelListeners()) {
    sessionsTableScrollPanel.removeMouseWheelListener(listener);
}
       
       SIAScrollPanel.getVerticalScrollBar().setUnitIncrement(16);

     
       
       
      /* ArrayList<String> listSomeString = new ArrayList<String>();        
        listSomeString.add("Snowboarding");
        listSomeString.add("Rowing");
        listSomeString.add("Knitting");
        listSomeString.add("Speed reading");
        listSomeString.add("Pool");
        listSomeString.add("None of the above");*/
       
       
      
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
       
       
       
        ArrayList<Paraprofessional> tutors = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p order by p.fName");
        ArrayList<String> tutorslist = new ArrayList<String>();
       for(int i=0; i<tutors.size(); i++)
           tutorslist.add(tutors.get(i).getfName()+" "+tutors.get(i).getlName());
       
       
       ArrayList<Subject> subjects = (ArrayList<Subject>)HibernateTest.select("from Subject as s order by s.abbrevName");
       ArrayList<Teacher> teachers = (ArrayList<Teacher>)HibernateTest.select("from Teacher as t order by t.fName");
       ArrayList<Category> categories = (ArrayList<Category>)HibernateTest.select("from Category as c order by c.name");
       ArrayList<Course> levels = (ArrayList<Course>)HibernateTest.select("from Course as c order by c.level");
        
       ArrayList<String> levelslist = new ArrayList<String>();
       for(int i=0; i<levels.size(); i++)
          levelslist.add(levels.get(i).getLevel()+"");
       
        ArrayList<String> categorieslist = new ArrayList<String>();
       for(int i=0; i<categories.size(); i++)
           categorieslist.add(categories.get(i).getName());
       
       
       ArrayList<String> teacherslist = new ArrayList<String>();
       for(int i=0; i<teachers.size(); i++)
           teacherslist.add(teachers.get(i).getfName()+" "+teachers.get(i).getlName());
       
        ArrayList<String> subjectslist = new ArrayList<String>();
       for(int i=0; i<subjects.size(); i++)
           subjectslist.add(subjects.get(i).getAbbrevName());
       
       
       
       
     
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
       
       Data d = new Data(false);
       
       sessionsTable.getColumnModel().getColumn(14).setCellRenderer(new MinuteCellRenderer());

              
       sessionsTable.getColumnModel().getColumn(4).setCellEditor(new ComboBoxCellEditor(new JComboBox()));
       sessionsTable.getColumnModel().getColumn(3).setCellEditor(new ComboBoxCellEditor(new JComboBox()));
       sessionsTable.getColumnModel().getColumn(2).setCellEditor(new ComboBoxCellEditor(new JComboBox()));
       sessionsTable.getColumnModel().getColumn(1).setCellEditor(new ComboBoxCellEditor(new JComboBox()));

       
       //Clients autocomplete
       JComboBox[] cboxes = new  JComboBox[4];
       cboxes[0]=fnameCombo;
       cboxes[1]=lnameCombo;
       cboxes[2]=phoneCombo;
       cboxes[3]=emailCombo;
       
       ArrayList<ArrayList<String>> cultimateList = new ArrayList<ArrayList<String>>();

       cultimateList.add(Data.getClientsfirst());
       cultimateList.add(Data.getClientslast());
       cultimateList.add(Data.getClientsphone());
       cultimateList.add(Data.getClientsemail());

      uacc = new UltimateAutoCompleteClientNew(cultimateList, cboxes, Data.getClientFirst(), Data.getClientLast(), Data.getClientPhone(), Data.getClientEmail());
      
       
       ArrayList<ParaprofessionalSession> sessions = (ArrayList<ParaprofessionalSession>)HibernateTest.select("from ParaprofessionalSession as ps where (ps.sessionStart IS NULL or ps.sessionEnd IS NULL) AND walkout='false'");

        if(sessions.size() > 0)
        {
            for(int i=0; i<sessions.size(); i++)
            {
                ((SessionTableModel) sessionsTable.getModel()).addRow(sessions.get(i)); 
            }
            
            sessionsTable.repaint();
        }
        
        Timer timer = new Timer("Minute Update");
 
        //2- Taking an instance of class contains your repeated method.
        MinuteUpdate min = new MinuteUpdate((SessionTableModel)sessionsTable.getModel());
 
 
        //TimerTask is a class implements Runnable interface so
        //You have to override run method with your certain code black
 
        //Second Parameter is the specified the Starting Time for your timer in
        //MilliSeconds or Date
 
        //Third Parameter is the specified the Period between consecutive
        //calling for the method.
 
        timer.schedule(min, 0, 60000);
        
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SIAPannel = new javax.swing.JTabbedPane();
        SIAScrollPanel = new javax.swing.JScrollPane();
        sessionsAndAgendaPanel = new javax.swing.JPanel();
        studentInfoPanel = new javax.swing.JPanel();
        fnameLabel = new javax.swing.JLabel();
        fnameCombo = new javax.swing.JComboBox();
        lnameLabel = new javax.swing.JLabel();
        lnameCombo = new javax.swing.JComboBox();
        emailLabel = new javax.swing.JLabel();
        emailCombo = new javax.swing.JComboBox();
        phoneLabel = new javax.swing.JLabel();
        phoneCombo = new javax.swing.JComboBox();
        courseInfoPanel = new javax.swing.JPanel();
        courseLabel = new javax.swing.JLabel();
        courseCombo = new javax.swing.JComboBox();
        levelLabel = new javax.swing.JLabel();
        levelCombo = new javax.swing.JComboBox();
        teacherLabel = new javax.swing.JLabel();
        teacherCombo = new javax.swing.JComboBox();
        categoryLabel = new javax.swing.JLabel();
        categoryCombo = new javax.swing.JComboBox();
        paraprofessionalCombo = new javax.swing.JComboBox();
        ParaprofessionalLabel = new javax.swing.JLabel();
        otherInfoPanel = new javax.swing.JPanel();
        locationLabel = new javax.swing.JLabel();
        locationCombo = new javax.swing.JComboBox();
        creatorLabel = new javax.swing.JLabel();
        notesLabel = new javax.swing.JLabel();
        notesField = new javax.swing.JTextField();
        gcCheck = new javax.swing.JCheckBox();
        walkoutCheck = new javax.swing.JCheckBox();
        sessionstartLabel = new javax.swing.JLabel();
        sessionstartField = new javax.swing.JTextField();
        creatorCombo = new javax.swing.JComboBox();
        sessionendLabel = new javax.swing.JLabel();
        sessionendField = new javax.swing.JTextField();
        addSessionbutton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        sessionsTablePanel = new javax.swing.JPanel();
        sessionsTableScrollPanel = new javax.swing.JScrollPane();
        sessionsTable = new javax.swing.JTable();
        deleteSessionButton = new javax.swing.JButton();
        createAgendaPanel = new javax.swing.JPanel();
        agendaCategoryLabel = new javax.swing.JLabel();
        agendaCategoryCombo = new javax.swing.JComboBox();
        agendaDateLabel = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();
        dateLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        submitbutton = new javax.swing.JButton();
        agendaTextAreaScrollPanel = new javax.swing.JScrollPane();
        agendaTextArea = new javax.swing.JTextArea();
        agendaPanel = new javax.swing.JPanel();
        deleteAgendaButton = new javax.swing.JButton();
        agendaTableScrollPanel = new javax.swing.JScrollPane();
        agendaTable = new javax.swing.JTable();
        autocompleteCheck = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        studentInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        fnameLabel.setText("First Name:");

        fnameCombo.setEditable(true);

        lnameLabel.setText("Last Name:");

        lnameCombo.setEditable(true);

        emailLabel.setText("Email:");

        emailCombo.setEditable(true);

        phoneLabel.setText("Phone:");

        phoneCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout studentInfoPanelLayout = new org.jdesktop.layout.GroupLayout(studentInfoPanel);
        studentInfoPanel.setLayout(studentInfoPanelLayout);
        studentInfoPanelLayout.setHorizontalGroup(
            studentInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(fnameLabel)
                .add(18, 18, 18)
                .add(fnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(lnameLabel)
                .add(18, 18, 18)
                .add(lnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(emailLabel)
                .add(18, 18, 18)
                .add(emailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(phoneLabel)
                .add(18, 18, 18)
                .add(phoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 174, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        studentInfoPanelLayout.setVerticalGroup(
            studentInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanelLayout.createSequentialGroup()
                .add(studentInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(phoneLabel)
                    .add(emailLabel)
                    .add(lnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lnameLabel)
                    .add(fnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(fnameLabel)
                    .add(emailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(phoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6))
        );

        phoneCombo.getAccessibleContext().setAccessibleParent(studentInfoPanel);

        courseInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

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

        paraprofessionalCombo.setEditable(true);

        ParaprofessionalLabel.setText("Paraprofessional:");

        org.jdesktop.layout.GroupLayout courseInfoPanelLayout = new org.jdesktop.layout.GroupLayout(courseInfoPanel);
        courseInfoPanel.setLayout(courseInfoPanelLayout);
        courseInfoPanelLayout.setHorizontalGroup(
            courseInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(courseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(levelLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(levelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(teacherLabel)
                .add(18, 18, 18)
                .add(teacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 243, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(categoryLabel)
                .add(18, 18, 18)
                .add(categoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(13, 13, 13)
                .add(ParaprofessionalLabel)
                .add(18, 18, 18)
                .add(paraprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        courseInfoPanelLayout.setVerticalGroup(
            courseInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanelLayout.createSequentialGroup()
                .add(courseInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel)
                    .add(courseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel)
                    .add(levelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel)
                    .add(teacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(categoryLabel)
                    .add(categoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(paraprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ParaprofessionalLabel))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        otherInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Other Information"));

        locationLabel.setText("Location:");

        creatorLabel.setText("Creator:");

        notesLabel.setText("Notes:");

        gcCheck.setText("GC");

        walkoutCheck.setText("Walkout");

        sessionstartLabel.setText("Session Start:");

        sessionstartField.setText("dd/mm/yyyy hh:mm aa");

        creatorCombo.setEditable(true);

        sessionendLabel.setText("Session End:");

        sessionendField.setText("dd/mm/yyyy hh:mm:ss aa");

        org.jdesktop.layout.GroupLayout otherInfoPanelLayout = new org.jdesktop.layout.GroupLayout(otherInfoPanel);
        otherInfoPanel.setLayout(otherInfoPanelLayout);
        otherInfoPanelLayout.setHorizontalGroup(
            otherInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(locationLabel)
                .add(18, 18, 18)
                .add(locationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(creatorLabel)
                .add(18, 18, 18)
                .add(creatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(notesLabel)
                .add(18, 18, 18)
                .add(notesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(sessionstartLabel)
                .add(18, 18, 18)
                .add(sessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(sessionendLabel)
                .add(18, 18, 18)
                .add(sessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 21, Short.MAX_VALUE)
                .add(gcCheck)
                .add(18, 18, 18)
                .add(walkoutCheck)
                .addContainerGap())
        );
        otherInfoPanelLayout.setVerticalGroup(
            otherInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(otherInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(locationLabel)
                    .add(locationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(creatorLabel)
                    .add(creatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(notesLabel)
                    .add(notesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionstartLabel)
                    .add(sessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(gcCheck)
                    .add(walkoutCheck)
                    .add(sessionendLabel)
                    .add(sessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addSessionbutton.setForeground(new java.awt.Color(51, 102, 255));
        addSessionbutton.setText("Add Session");
        addSessionbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addSessionbuttonMouseClicked(evt);
            }
        });
        addSessionbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSessionbuttonActionPerformed(evt);
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

        sessionsTablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Current Sessions"));

        sessionsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        sessionsTableScrollPanel.setViewportView(sessionsTable);

        deleteSessionButton.setText("Delete Session");
        deleteSessionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSessionButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout sessionsTablePanelLayout = new org.jdesktop.layout.GroupLayout(sessionsTablePanel);
        sessionsTablePanel.setLayout(sessionsTablePanelLayout);
        sessionsTablePanelLayout.setHorizontalGroup(
            sessionsTablePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(sessionsTablePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(sessionsTableScrollPanel)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsTablePanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(deleteSessionButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        sessionsTablePanelLayout.setVerticalGroup(
            sessionsTablePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(sessionsTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 337, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteSessionButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        createAgendaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Create New Agenda"));

        agendaCategoryLabel.setText("Category:");

        agendaCategoryCombo.setEditable(true);

        agendaDateLabel.setText("Date:");

        dateLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        dateLabel.setForeground(new java.awt.Color(102, 102, 102));
        dateLabel.setText("(mm/dd/yyyy hh:mm a.a.)");

        cancelButton.setForeground(new java.awt.Color(153, 0, 0));
        cancelButton.setText("Cancel");

        submitbutton.setForeground(new java.awt.Color(51, 102, 255));
        submitbutton.setText("Submit");

        agendaTextArea.setColumns(20);
        agendaTextArea.setRows(5);
        agendaTextAreaScrollPanel.setViewportView(agendaTextArea);

        org.jdesktop.layout.GroupLayout createAgendaPanelLayout = new org.jdesktop.layout.GroupLayout(createAgendaPanel);
        createAgendaPanel.setLayout(createAgendaPanelLayout);
        createAgendaPanelLayout.setHorizontalGroup(
            createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAgendaPanelLayout.createSequentialGroup()
                .add(101, 101, 101)
                .add(agendaCategoryLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(agendaCategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 123, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(agendaDateLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(dateLabel)
                    .add(createAgendaPanelLayout.createSequentialGroup()
                        .add(dateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 167, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(agendaTextAreaScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 588, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, createAgendaPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(submitbutton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(cancelButton))
        );
        createAgendaPanelLayout.setVerticalGroup(
            createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAgendaPanelLayout.createSequentialGroup()
                .add(dateLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(createAgendaPanelLayout.createSequentialGroup()
                        .add(agendaTextAreaScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 4, Short.MAX_VALUE))
                    .add(createAgendaPanelLayout.createSequentialGroup()
                        .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(agendaCategoryLabel)
                            .add(agendaCategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(agendaDateLabel)
                            .add(dateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(cancelButton)
                            .add(submitbutton))))
                .addContainerGap())
        );

        agendaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Agendas"));

        deleteAgendaButton.setText("Delete Agenda");

        agendaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        agendaTableScrollPanel.setViewportView(agendaTable);
        agendaTable.getAccessibleContext().setAccessibleParent(agendaPanel);

        org.jdesktop.layout.GroupLayout agendaPanelLayout = new org.jdesktop.layout.GroupLayout(agendaPanel);
        agendaPanel.setLayout(agendaPanelLayout);
        agendaPanelLayout.setHorizontalGroup(
            agendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(agendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(agendaTableScrollPanel)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, agendaPanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(deleteAgendaButton)))
                .addContainerGap())
        );
        agendaPanelLayout.setVerticalGroup(
            agendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanelLayout.createSequentialGroup()
                .add(agendaTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 312, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteAgendaButton)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        autocompleteCheck.setSelected(true);
        autocompleteCheck.setText("AutoComplete");
        autocompleteCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autocompleteCheckActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout sessionsAndAgendaPanelLayout = new org.jdesktop.layout.GroupLayout(sessionsAndAgendaPanel);
        sessionsAndAgendaPanel.setLayout(sessionsAndAgendaPanelLayout);
        sessionsAndAgendaPanelLayout.setHorizontalGroup(
            sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, createAgendaPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                                .add(addSessionbutton)
                                .add(18, 18, 18)
                                .add(clearButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(studentInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, courseInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, otherInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(sessionsTablePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(agendaPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                        .add(173, 173, 173)
                        .add(autocompleteCheck)))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        sessionsAndAgendaPanelLayout.setVerticalGroup(
            sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                .add(autocompleteCheck)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(studentInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(courseInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(otherInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(12, 12, 12)
                .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(addSessionbutton)
                    .add(clearButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionsTablePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(createAgendaPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(agendaPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        SIAScrollPanel.setViewportView(sessionsAndAgendaPanel);

        SIAPannel.addTab("Sessions / Agenda", SIAScrollPanel);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(SIAPannel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1441, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(SIAPannel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1367, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButtonActionPerformed

    private void clearButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButtonMouseClicked

    private void addSessionbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSessionbuttonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addSessionbuttonMouseClicked

    private void teacherComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacherComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_teacherComboActionPerformed

    private void deleteSessionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSessionButtonActionPerformed
        int[] selectedRows = sessionsTable.getSelectedRows();
        
        ((SessionTableModel)sessionsTable.getModel()).deleteRows(selectedRows);
    }//GEN-LAST:event_deleteSessionButtonActionPerformed

    private void addSessionbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSessionbuttonActionPerformed
//Paraprofessional t = new Paraprofessional(count, "TUTORFIRSTTEST", "TUTORLASTTEST", true);
        //Teacher teach = new Teacher(count, jComboBoxTeacher.getSelectedItem().toString(), "TestFirstName");
        //Subject sub = new Subject(count, jComboBoxCourse.getSelectedItem().toString(), "FullNameTest", new Category(count, "MABS"));
        
        ArrayList<Subject> subjects =(ArrayList<Subject>) HibernateTest.select("from Subject as s where s.abbrevName='"+courseCombo.getSelectedItem().toString()+"'");
        
        String[] tName = teacherCombo.getSelectedItem().toString().split(" ");
        String[] pName = paraprofessionalCombo.getSelectedItem().toString().split(" ");
        String[] cName = creatorCombo.getSelectedItem().toString().split(" ");
        String clientFName = fnameCombo.getSelectedItem().toString();
        String clientLName = lnameCombo.getSelectedItem().toString();
        boolean GC = gcCheck.isSelected();
        String notes = notesField.getText().toString();
        String level = levelCombo.getSelectedItem().toString();
        int intLevel = Integer.parseInt(level);
        String location = locationCombo.getSelectedItem().toString();
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
        ((SessionTableModel) sessionsTable.getModel()).addRow(sessions.get(0));
        sessionsTable.repaint();
    }//GEN-LAST:event_addSessionbuttonActionPerformed

    private void autocompleteCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autocompleteCheckActionPerformed
        
        if(autocompleteCheck.isSelected())
        {
            uac.noMore();
            //Clients autocomplete
            JComboBox[] cboxes = new  JComboBox[4];
            cboxes[0]=fnameCombo;
            cboxes[1]=lnameCombo;
            cboxes[2]=phoneCombo;
            cboxes[3]=emailCombo;

            ArrayList<ArrayList<String>> cultimateList = new ArrayList<ArrayList<String>>();

            cultimateList.add(Data.getClientsfirst());
            cultimateList.add(Data.getClientslast());
            cultimateList.add(Data.getClientsphone());
            cultimateList.add(Data.getClientsemail());

           uacc = new UltimateAutoCompleteClientNew(cultimateList, cboxes, Data.getClientFirst(), Data.getClientLast(), Data.getClientPhone(), Data.getClientEmail());
           
        }
        else
        {
            uacc.noMore();
            
            JComboBox[] boxes = new  JComboBox[4];
            boxes[0]=fnameCombo;
            boxes[1]=lnameCombo;
            boxes[2]=phoneCombo;
            boxes[3]=emailCombo;

            ArrayList<ArrayList<String>> cultimateList = new ArrayList<ArrayList<String>>();
            cultimateList.add(Data.getClientsfirst());
            cultimateList.add(Data.getClientslast());
            cultimateList.add(Data.getClientsphone());
            cultimateList.add(Data.getClientsemail());
            uac = new UltimateAutoComplete(cultimateList, boxes);
        }
    }//GEN-LAST:event_autocompleteCheckActionPerformed

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
    private javax.swing.JLabel ParaprofessionalLabel;
    private javax.swing.JTabbedPane SIAPannel;
    private javax.swing.JScrollPane SIAScrollPanel;
    private javax.swing.JButton addSessionbutton;
    private javax.swing.JComboBox agendaCategoryCombo;
    private javax.swing.JLabel agendaCategoryLabel;
    private javax.swing.JLabel agendaDateLabel;
    private javax.swing.JPanel agendaPanel;
    private javax.swing.JTable agendaTable;
    private javax.swing.JScrollPane agendaTableScrollPanel;
    private javax.swing.JTextArea agendaTextArea;
    private javax.swing.JScrollPane agendaTextAreaScrollPanel;
    private javax.swing.JCheckBox autocompleteCheck;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox categoryCombo;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JButton clearButton;
    private javax.swing.JComboBox courseCombo;
    private javax.swing.JPanel courseInfoPanel;
    private javax.swing.JLabel courseLabel;
    private javax.swing.JPanel createAgendaPanel;
    private javax.swing.JComboBox creatorCombo;
    private javax.swing.JLabel creatorLabel;
    private javax.swing.JTextField dateField;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JButton deleteAgendaButton;
    private javax.swing.JButton deleteSessionButton;
    private javax.swing.JComboBox emailCombo;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JComboBox fnameCombo;
    private javax.swing.JLabel fnameLabel;
    private javax.swing.JCheckBox gcCheck;
    private javax.swing.JComboBox levelCombo;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JComboBox lnameCombo;
    private javax.swing.JLabel lnameLabel;
    private javax.swing.JComboBox locationCombo;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JTextField notesField;
    private javax.swing.JLabel notesLabel;
    private javax.swing.JPanel otherInfoPanel;
    private javax.swing.JComboBox paraprofessionalCombo;
    private javax.swing.JComboBox phoneCombo;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JTextField sessionendField;
    private javax.swing.JLabel sessionendLabel;
    private javax.swing.JPanel sessionsAndAgendaPanel;
    private javax.swing.JTable sessionsTable;
    private javax.swing.JPanel sessionsTablePanel;
    private javax.swing.JScrollPane sessionsTableScrollPanel;
    private javax.swing.JTextField sessionstartField;
    private javax.swing.JLabel sessionstartLabel;
    private javax.swing.JPanel studentInfoPanel;
    private javax.swing.JButton submitbutton;
    private javax.swing.JComboBox teacherCombo;
    private javax.swing.JLabel teacherLabel;
    private javax.swing.JCheckBox walkoutCheck;
    // End of variables declaration//GEN-END:variables
}
