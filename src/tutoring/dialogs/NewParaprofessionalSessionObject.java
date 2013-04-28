/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.dialogs;

import java.awt.Color;
import java.awt.Window;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.text.JTextComponent;
import tutoring.entity.Client;
import tutoring.entity.Course;
import tutoring.entity.Location;
import tutoring.entity.Paraprofessional;
import tutoring.entity.ParaprofessionalSession;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.helper.AgendaTableHelper;
import tutoring.helper.Data;
import tutoring.helper.DatabaseHelper;
import tutoring.helper.SessionTableHelper;
import tutoring.helper.SessionTableModel;
import tutoring.helper.UltimateAutoAutoComplete;
import tutoring.helper.UltimateAutoComplete;
import tutoring.helper.Validate;

/**
 *
 * @author Nathaniel
 */
public class NewParaprofessionalSessionObject extends javax.swing.JDialog {

    /**
     * Creates new form NewParaprofessionalSessionObject
     */
    private int sessionID = -1;
    private UltimateAutoComplete uac;
    
    private UltimateAutoAutoComplete uaacClient; 

    private UltimateAutoAutoComplete uaacCourse;
    public NewParaprofessionalSessionObject(java.awt.Frame parent, boolean modal, ArrayList<String> roles) {
        super(parent, modal);
        initComponents();
        
        notesField.setLineWrap(true);
        sessionstartField.setText("mm/dd/yyyy hh:mm aa");
        sessionendField.setText("mm/dd/yyyy hh:mm aa");
        editButton.setVisible(false);
        
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
       ArrayList<ArrayList<String>> cultimateList1 = new ArrayList<ArrayList<String>>();
       cultimateList1.add(Data.getFnameOrderedList());
       cultimateList1.add(Data.getLnameOrderedList());
       cultimateList1.add(Data.getPhoneOrderedList());
       cultimateList1.add(Data.getEmailOrderedList());
       
       uaacClient = new UltimateAutoAutoComplete(cultimateList, cboxes, cultimateList1);//Data.getClientFirst(), Data.getClientLast(), Data.getClientPhone(), Data.getClientEmail());
      
       
       JComboBox[] cboxes2 = new  JComboBox[3];
       cboxes2[0]=courseCombo;
       cboxes2[1]=levelCombo;
       cboxes2[2]=teacherCombo;
       //cboxes[3]=emailCombo;
       ArrayList<ArrayList<String>> cultimateList2 = new ArrayList<ArrayList<String>>();

       cultimateList2.add(Data.getSubjectslist());
       cultimateList2.add(Data.getLevelslist());
       cultimateList2.add(Data.getTeacherslist());
       ArrayList<ArrayList<String>> cultimateList22 = new ArrayList<ArrayList<String>>();
       cultimateList22.add(Data.getSubjectOrderedList());
       cultimateList22.add(Data.getLevelOrderedList());
       cultimateList22.add(Data.getTeacherOrderedList());
       uaacCourse = new UltimateAutoAutoComplete(cultimateList2, cboxes2, cultimateList22);//Data.getClientFirst(), Data.getClientLast(), Data.getClientPhone(), Data.getClientEmail());
      
       
       JComboBox[] boxes3 = new  JComboBox[3];
        
        boxes3[0]=creatorCombo;
        boxes3[1]=locationCombo;
        boxes3[2]=paraprofessionalCombo;

        ArrayList<ArrayList<String>> cultimateList3 = new ArrayList<ArrayList<String>>();
        
       cultimateList3.add(Data.getTutorslist());
        cultimateList3.add(Data.getLocationslist());
        cultimateList3.add(Data.getTutorslist());
       
        uac = new UltimateAutoComplete(cultimateList3, boxes3);
            
            
        clearComboBoxes();
                  
        this.setResizable(false);
  
        editButton.setVisible(false);
        
    }
    
    public NewParaprofessionalSessionObject(java.awt.Frame parent, boolean modal, String lname, String password, String lname, String fname, String role, int sessionID) {
        super(parent, modal);
        initComponents();
      
        this.setResizable(false);
        roleCombo.setEditable(true);
        roleCombo.setModel(new DefaultComboBoxModel(roles.toArray()));
        roleCombo.setSelectedIndex(roles.indexOf(role));
            
        fnameField.setText(fname);
        lnameField.setText(lname);
        usernameField.setText(username);
        passwordField.setText(password);
        
        notesField.setLineWrap(true);
        sessionstartField.setText("mm/dd/yyyy hh:mm aa");
        sessionendField.setText("mm/dd/yyyy hh:mm aa");
        editButton.setVisible(false);
        
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
       ArrayList<ArrayList<String>> cultimateList1 = new ArrayList<ArrayList<String>>();
       cultimateList1.add(Data.getFnameOrderedList());
       cultimateList1.add(Data.getLnameOrderedList());
       cultimateList1.add(Data.getPhoneOrderedList());
       cultimateList1.add(Data.getEmailOrderedList());
       
       uaacClient = new UltimateAutoAutoComplete(cultimateList, cboxes, cultimateList1);//Data.getClientFirst(), Data.getClientLast(), Data.getClientPhone(), Data.getClientEmail());
      
       
       JComboBox[] cboxes2 = new  JComboBox[3];
       cboxes2[0]=courseCombo;
       cboxes2[1]=levelCombo;
       cboxes2[2]=teacherCombo;
       //cboxes[3]=emailCombo;
       ArrayList<ArrayList<String>> cultimateList2 = new ArrayList<ArrayList<String>>();

       cultimateList2.add(Data.getSubjectslist());
       cultimateList2.add(Data.getLevelslist());
       cultimateList2.add(Data.getTeacherslist());
       ArrayList<ArrayList<String>> cultimateList22 = new ArrayList<ArrayList<String>>();
       cultimateList22.add(Data.getSubjectOrderedList());
       cultimateList22.add(Data.getLevelOrderedList());
       cultimateList22.add(Data.getTeacherOrderedList());
       uaacCourse = new UltimateAutoAutoComplete(cultimateList2, cboxes2, cultimateList22);//Data.getClientFirst(), Data.getClientLast(), Data.getClientPhone(), Data.getClientEmail());
      
       
       JComboBox[] boxes3 = new  JComboBox[3];
        
        boxes3[0]=creatorCombo;
        boxes3[1]=locationCombo;
        boxes3[2]=paraprofessionalCombo;

        ArrayList<ArrayList<String>> cultimateList3 = new ArrayList<ArrayList<String>>();
        
       cultimateList3.add(Data.getTutorslist());
        cultimateList3.add(Data.getLocationslist());
        cultimateList3.add(Data.getTutorslist());
       
        uac = new UltimateAutoComplete(cultimateList3, boxes3);
            
            
        clearComboBoxes();
        
        editButton.setVisible(true);
        
    }
    
    private void close()
    {
        Window win = SwingUtilities.getWindowAncestor(this);
        if (win != null) {
           win.dispose();
        }
    }

    private boolean getParaprofessionalSession(boolean isUpdating)
    {
        try
        {
            boolean clientPanelCheck = true;
            boolean coursePanelCheck = true;
            boolean paraprofessionalPanelCheck = true;
            boolean creatorPanelCheck = true;
            boolean locationPanelCheck = true;
          //  boolean otherPanelCheck = true;
            
            courseCombo.setBorder(null);
            teacherCombo.setBorder(null);
            levelCombo.setBorder(null);
            paraprofessionalCombo.setBorder(null);
            creatorCombo.setBorder(null);
            fnameCombo.setBorder(null);
            lnameCombo.setBorder(null);
            locationCombo.setBorder(null);
            sessionendField.setBorder(null);
            sessionstartField.setBorder(null);
            studentInfoPanel.repaint();
           /// otherInfoPanel.repaint();
            courseInfoPanel.repaint();
           /// creatorInfoPanel.repaint();
            paraprofessionalInfoPanel.repaint();
           /// locationInfoPanel.repaint();
            
            //courseInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
            //studentInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
           // otherInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
           
            String course = ((JTextComponent)courseCombo.getEditor().getEditorComponent()).getText();
            if(course.length() <= 0)
            {
                courseCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                coursePanelCheck = false;
            }
             String tname = ((JTextComponent)teacherCombo.getEditor().getEditorComponent()).getText();
            if(tname.length() <= 0)
            {
                teacherCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                coursePanelCheck = false;
            }
            
                
            
            String level = ((JTextComponent)levelCombo.getEditor().getEditorComponent()).getText();
            Integer intLevel = null;
            try
            {
                intLevel = Integer.parseInt(level);
                
            }
            catch(Exception z)
            {
                levelCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                coursePanelCheck = false;
            }
            
            String pName = ((JTextComponent)paraprofessionalCombo.getEditor().getEditorComponent()).getText();
            if(pName.length() <= 0)
            {
                paraprofessionalCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                paraprofessionalPanelCheck = false;
            }
            
            String cName = ((JTextComponent)creatorCombo.getEditor().getEditorComponent()).getText();
            if(cName.length() <= 0)
            {
                creatorCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                creatorPanelCheck = false;
            }
            
            String clientFName = ((JTextComponent)fnameCombo.getEditor().getEditorComponent()).getText();
            if(clientFName.length() <= 0)
            {
                fnameCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                clientPanelCheck = false;
            }
            String clientLName = ((JTextComponent)lnameCombo.getEditor().getEditorComponent()).getText();
            if(clientLName.length() <= 0)
            {
                lnameCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                clientPanelCheck = false;
            }

            
            boolean GC = gcCheck.isSelected();
            String notes = notesField.getText().toString();
            
            String location = ((JTextComponent)locationCombo.getEditor().getEditorComponent()).getText();
            if(location.length() <= 0)
            {
                locationCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                locationPanelCheck = false;
            }
            
            
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
            Timestamp sessionStart = null;
            Timestamp sessionEnd = null;
            boolean hasSessionStart = true;
            
            if(sessionstartField.getText().trim().length() > 0 && !sessionstartField.getText().trim().equals("mm/dd/yyyy hh:mm aa"))
            {
                hasSessionStart= Validate.validateTimestamp(sessionstartField.getText().trim());
 
                if(hasSessionStart)
                    sessionStart = new Timestamp(sdf.parse(sessionstartField.getText().trim()).getTime());
                else
                {
                    sessionstartField.setBorder(new MatteBorder(3,3,3,3,Color.red));
                }
            }
         
            boolean hasSessionEnd = true;
            if(sessionendField.getText().trim().length() > 0 && !sessionendField.getText().trim().equals("mm/dd/yyyy hh:mm aa"))
            {
                hasSessionEnd = Validate.validateTimestamp(sessionendField.getText().trim());
                if(!hasSessionEnd)
                {
                    sessionendField.setBorder(new MatteBorder(3,3,3,3,Color.red));
                    
                }    
                else
                    sessionEnd = new Timestamp(sdf.parse(sessionendField.getText().trim()).getTime());
            }
            
            if((!hasSessionStart || !hasSessionEnd) || (!hasSessionStart && hasSessionEnd))
                throw new ParseException("parse exception with timestamp", 0);
                
 
            boolean walkout = walkoutCheck.isSelected();

           // String term = jComboBoxTerm.getSelectedItem().toString();

            String abbrevNameString = Subject.SubjectTable.ABBREVNAME.getWithAlias();
            String teachFNameString = Teacher.TeacherTable.FNAME.getWithAlias();
            String teachLNameString = Teacher.TeacherTable.LNAME.getWithAlias();
            String subjectIDString = Course.CourseTable.SUBJECTID.getWithAlias();
            String paraFNameString = Paraprofessional.ParaTable.FNAME.getWithAlias();
            String paraLNameString = Paraprofessional.ParaTable.LNAME.getWithAlias();
            String teachIDString = Course.CourseTable.TEACHERID.getWithAlias();
            String levelString = Course.CourseTable.LEVEL.getWithAlias();
            String clientFNameString = Client.ClientTable.FNAME.getWithAlias();
            String clientLNameString = Client.ClientTable.LNAME.getWithAlias();
            
            DatabaseHelper.open();

            ArrayList<Subject> subjects =(ArrayList<Subject>) Subject.selectAllSubjects("where "+abbrevNameString+"='"+course+"'", DatabaseHelper.getConnection());
            
            ArrayList<Teacher> teachers = (ArrayList<Teacher>) Teacher.selectAllTeacher("where concat(concat("+teachFNameString+",' '),"+teachLNameString+")='"+tname.trim()+"'", DatabaseHelper.getConnection());
            ArrayList<Course> courses = null;
            try
            {
                String query = "where "+subjectIDString+"="+subjects.get(0).getSubjectID()+" and "+teachIDString+"="+teachers.get(0).getTeacherID() + " and "+levelString+"="+intLevel.intValue();
                System.out.println(query);
                courses = (ArrayList<Course>)Course.selectAllCourse(query, DatabaseHelper.getConnection());
                
            }
            catch(Exception z)
            {
                z.printStackTrace();
                courses = new ArrayList<Course>();
                //coursePanelCheck = true;
            }
            ArrayList<Paraprofessional> paraprofessionals = (ArrayList<Paraprofessional>)Paraprofessional.selectAllParaprofessional("where concat(concat("+paraFNameString+",' '),"+paraLNameString+")='"+pName.trim()+"'", DatabaseHelper.getConnection());

            ArrayList<Paraprofessional> creators = (ArrayList<Paraprofessional>)Paraprofessional.selectAllParaprofessional("where concat(concat("+paraFNameString+",' '),"+paraLNameString+")='"+cName.trim()+"'", DatabaseHelper.getConnection());

            ArrayList<Client> clients = (ArrayList<Client>)Client.selectAllClients("where "+clientFNameString+"='"+clientFName.trim()+"' and "+clientLNameString+"='"+clientLName.trim()+"'", DatabaseHelper.getConnection());

            ArrayList<Location> locations = (ArrayList<Location>)Location.selectAllLocation("where "+Location.LocationTable.NAME.getWithAlias()+"='"+location.trim()+"'", DatabaseHelper.getConnection());

            courseInfoPanel.setBackground(this.getBackground());
            studentInfoPanel.setBackground(this.getBackground());
         ///   otherInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
         ///   creatorInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
            paraprofessionalInfoPanel.setBackground(this.getBackground());
         ///   locationInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
            
               
            System.out.println("CLIENTS SIZE: "+clients.size());
            if(subjects.size() <= 0 && coursePanelCheck)
            {
                System.out.println("Subjects less than 1");
                courseInfoPanel.setBackground(Color.red);
            }
            if(teachers.size() <= 0 && coursePanelCheck)
            {
                System.out.println("Teachers less than 1");
                courseInfoPanel.setBackground(Color.red);
            }
            if(courses.size() <= 0 && coursePanelCheck)
            {
                System.out.println("Courses less than 1");
                courseInfoPanel.setBackground(Color.red);
            }
            if(paraprofessionals.size() <= 0 && paraprofessionalPanelCheck)
            {
                System.out.println("Paraprofessionals less than 1");
                paraprofessionalInfoPanel.setBackground(Color.red);
            }
            if(creators.size() <= 0 && creatorPanelCheck)
            {
                System.out.println("Creators less than 1");
                creatorCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
            ///    creatorInfoPanel.setBackground(Color.red);
            }
            if(clients.size() <= 0 && clientPanelCheck)
            {
                System.out.println("Clients less than 1");
                studentInfoPanel.setBackground(Color.red);
            }
            if(locations.size() <= 0 && locationPanelCheck)
            {
                System.out.println("Locations less than 1");
                locationCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
              ///  locationInfoPanel.setBackground(Color.red);
            }
          //  if(terms.size() <= 0)
          //      System.out.println("Terms less than 1");
            Timestamp now = new Timestamp((new Date()).getTime());

            String sessionStartString;
            if(sessionStart != null)
                sessionStartString = "='"+sessionStart+"'";
            else
                sessionStartString = " is null";
            
            String sessionEndString;
            if(sessionEnd != null)
                sessionEndString = "='"+sessionEnd+"'";
            else
                sessionEndString = " is null";
            
            ParaprofessionalSession ps = new ParaprofessionalSession(-1, paraprofessionals.get(0), clients.get(0), courses.get(0), locations.get(0), creators.get(0), now, sessionStart, sessionEnd, GC, notes, walkout);
            String paraIDString = ParaprofessionalSession.ParaSessTable.PARAPROFESSIONALID.getWithAlias();
            String courseIDString = ParaprofessionalSession.ParaSessTable.COURSEID.getWithAlias();
            String locationIDString = ParaprofessionalSession.ParaSessTable.LOCATIONID.getWithAlias();
            String creatorIDString = ParaprofessionalSession.ParaSessTable.PARAPROFESSIONALCREATORID.getWithAlias();
            String enteredString = ParaprofessionalSession.ParaSessTable.TIMEANDDATEENTERED.getWithAlias();
            String sessStartString = ParaprofessionalSession.ParaSessTable.SESSIONSTART.getWithAlias();
            String sessEndString = ParaprofessionalSession.ParaSessTable.SESSIONEND.getWithAlias();
            String gcString = ParaprofessionalSession.ParaSessTable.GRAMMARCHECK.getWithAlias();
            String walkoutString = ParaprofessionalSession.ParaSessTable.WALKOUT.getWithAlias();
            String notesString = ParaprofessionalSession.ParaSessTable.NOTES.getWithAlias();
            String clientIDString = ParaprofessionalSession.ParaSessTable.CLIENTID.getWithAlias();
            if(!isUpdating)
            {
                DatabaseHelper.insert(ParaprofessionalSession.getValues(ps), ParaprofessionalSession.ParaSessTable.getTable());
               // HibernateTest.create(ps);

                System.out.println("NOW: "+now.toString());
                
                String query =  "where "+paraIDString+"="+paraprofessionals.get(0).getParaprofessionalID()+" and "+clientIDString+"="+clients.get(0).getClientID()+" and "+courseIDString+"="+courses.get(0).getCourseID()+" and "+locationIDString+"="+locations.get(0).getLocationID()+" and "+creatorIDString+"="+creators.get(0).getParaprofessionalID()+" and "+enteredString+"='"+now.toString()+"' and "+sessStartString+""+sessionStartString+" and "+sessEndString+""+sessionEndString+" and "+gcString+"="+GC+" and "+notesString+"='"+notes+"' and "+walkoutString+"="+walkout;
                System.out.println(query);
                ArrayList<ParaprofessionalSession> sessions = ParaprofessionalSession.selectAllParaprofessionalSession(query, DatabaseHelper.getConnection()); // (ArrayList<ParaprofessionalSession>)HibernateTest.select(query);

                if(sessions.size() <=0)
                {
                    System.out.println("SESSION WAS NOT CREATED ERROR");
                }
                else
                {

                    System.out.println("ID: "+sessions.get(0).getParaprofessionalSessionID());
                }
            }
            else
            {
                ps.setParaprofessionalSessionID(sessionID);
                sessionID = -1;
                    
                DatabaseHelper.update(ParaprofessionalSession.getValues(ps), ParaprofessionalSession.ParaSessTable.getTable());

                System.out.println("NOW: "+now.toString());
                String query =  "where "+clientIDString+"="+clients.get(0).getClientID()+" and "+courseIDString+"="+courses.get(0).getCourseID()+" and "+locationIDString+"="+locations.get(0).getLocationID()+" and "+creatorIDString+"="+creators.get(0).getParaprofessionalID()+" and "+enteredString+"='"+now.toString()+"' and "+sessStartString+""+sessionStartString+" and "+sessEndString+""+sessionEndString+" and "+gcString+"="+GC+" and "+notesString+"='"+notes+"' and "+walkoutString+"="+walkout;

               // String query = "where ps.clientID="+clients.get(0).getClientID()+" and ps.courseID="+courses.get(0).getCourseID()+" and ps.locationID="+locations.get(0).getLocationID()+" and ps.paraprofessionalCreatorID="+creators.get(0).getParaprofessionalID()+" and ps.timeAndDateEntered='"+now.toString()+"' and ps.sessionStart"+sessionStartString+" and ps.sessionEnd"+sessionEndString+" and ps.grammarCheck="+GC+" and ps.notes='"+notes+"' and ps.walkout="+walkout;

                System.out.println(query);
                ArrayList<ParaprofessionalSession> sessions = (ArrayList<ParaprofessionalSession>)ParaprofessionalSession.selectAllParaprofessionalSession(query,DatabaseHelper.getConnection());

                if(sessions.size() <=0)
                {
                    System.out.println("SESSION WAS NOT CREATED ERROR");
                }
                else if(sessions.size() > 1)
                {
                    System.out.println("ID: "+sessions.get(0).getParaprofessionalSessionID());
                }
                else
                {
                    //HibernateTest.update(ps);
                    //!!!
                    //Call reload data
                    //!!!
                    //Remove when finished
                }
            }

            return true;
        }
        catch(Exception e)
        {
            System.out.println("ERROR ADDING SESSION"+e.getMessage() +"\n\n");
            e.printStackTrace();
            return false;
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
        java.awt.GridBagConstraints gridBagConstraints;

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
        paraprofessionalInfoPanel = new javax.swing.JPanel();
        ParaprofessionalLabel = new javax.swing.JLabel();
        sessionstartLabel = new javax.swing.JLabel();
        sessionstartField = new javax.swing.JTextField();
        sessionendLabel = new javax.swing.JLabel();
        sessionendField = new javax.swing.JTextField();
        notesLabel = new javax.swing.JLabel();
        gcCheck = new javax.swing.JCheckBox();
        walkoutCheck = new javax.swing.JCheckBox();
        locationLabel = new javax.swing.JLabel();
        creatorLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notesField = new javax.swing.JTextArea();
        paraprofessionalCombo = new javax.swing.JComboBox();
        creatorCombo = new javax.swing.JComboBox();
        locationCombo = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        clearButton = new javax.swing.JButton();
        addSessionbutton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        studentInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        fnameLabel.setText("First Name*");

        fnameCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fnameComboActionPerformed(evt);
            }
        });

        lnameLabel.setText("Last Name*");

        emailLabel.setText("Email");

        phoneLabel.setText("Telephone");

        javax.swing.GroupLayout studentInfoPanelLayout = new javax.swing.GroupLayout(studentInfoPanel);
        studentInfoPanel.setLayout(studentInfoPanelLayout);
        studentInfoPanelLayout.setHorizontalGroup(
            studentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fnameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fnameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lnameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lnameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(emailLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emailCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(phoneLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phoneCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        studentInfoPanelLayout.setVerticalGroup(
            studentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(studentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fnameLabel)
                    .addComponent(fnameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lnameLabel)
                    .addComponent(lnameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailLabel)
                    .addComponent(emailCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneLabel)
                    .addComponent(phoneCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        courseInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        courseLabel.setText("Course*");

        courseCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseComboActionPerformed(evt);
            }
        });

        levelLabel.setText("Course#*");

        teacherLabel.setText("Teacher*");

        javax.swing.GroupLayout courseInfoPanelLayout = new javax.swing.GroupLayout(courseInfoPanel);
        courseInfoPanel.setLayout(courseInfoPanelLayout);
        courseInfoPanelLayout.setHorizontalGroup(
            courseInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(courseInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(courseLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(courseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(levelLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(levelCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(teacherLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teacherCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        courseInfoPanelLayout.setVerticalGroup(
            courseInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(courseInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(courseInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseLabel)
                    .addComponent(courseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(levelLabel)
                    .addComponent(levelCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(teacherLabel)
                    .addComponent(teacherCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        paraprofessionalInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Session Information"));
        paraprofessionalInfoPanel.setMaximumSize(new java.awt.Dimension(832, 155));
        paraprofessionalInfoPanel.setMinimumSize(new java.awt.Dimension(832, 155));

        ParaprofessionalLabel.setText("Paraprofessional*");

        sessionstartLabel.setText("Session Start");

        sessionstartField.setText("dd/mm/yyyy hh:mm aa");
        sessionstartField.setMaximumSize(new java.awt.Dimension(156, 28));
        sessionstartField.setMinimumSize(new java.awt.Dimension(156, 28));
        sessionstartField.setPreferredSize(new java.awt.Dimension(156, 28));

        sessionendLabel.setText("Session End");

        sessionendField.setText("dd/mm/yyyy hh:mm aa");
        sessionendField.setMaximumSize(new java.awt.Dimension(156, 28));
        sessionendField.setMinimumSize(new java.awt.Dimension(156, 28));
        sessionendField.setPreferredSize(new java.awt.Dimension(156, 28));

        notesLabel.setText("Notes");

        gcCheck.setText("GC");

        walkoutCheck.setText("Walkout");

        locationLabel.setText("Location*");

        creatorLabel.setText("Creator*");

        notesField.setColumns(20);
        notesField.setLineWrap(true);
        notesField.setRows(5);
        notesField.setMaximumSize(new java.awt.Dimension(185, 116));
        notesField.setMinimumSize(new java.awt.Dimension(185, 116));
        notesField.setPreferredSize(new java.awt.Dimension(185, 116));
        jScrollPane2.setViewportView(notesField);

        paraprofessionalCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paraprofessionalComboActionPerformed(evt);
            }
        });

        creatorCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creatorComboActionPerformed(evt);
            }
        });

        locationCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paraprofessionalInfoPanelLayout = new javax.swing.GroupLayout(paraprofessionalInfoPanel);
        paraprofessionalInfoPanel.setLayout(paraprofessionalInfoPanelLayout);
        paraprofessionalInfoPanelLayout.setHorizontalGroup(
            paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paraprofessionalInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ParaprofessionalLabel)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(creatorLabel)
                        .addComponent(locationLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paraprofessionalInfoPanelLayout.createSequentialGroup()
                        .addComponent(paraprofessionalCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(notesLabel))
                    .addComponent(creatorCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(locationCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sessionstartLabel)
                    .addComponent(sessionendLabel))
                .addGap(4, 4, 4)
                .addGroup(paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sessionendField, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, paraprofessionalInfoPanelLayout.createSequentialGroup()
                        .addComponent(walkoutCheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(gcCheck))
                    .addComponent(sessionstartField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27))
        );
        paraprofessionalInfoPanelLayout.setVerticalGroup(
            paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(paraprofessionalInfoPanelLayout.createSequentialGroup()
                    .addGroup(paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ParaprofessionalLabel)
                        .addComponent(notesLabel)
                        .addComponent(paraprofessionalCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(creatorLabel)
                        .addComponent(creatorCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(locationLabel)
                        .addComponent(locationCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(paraprofessionalInfoPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sessionstartLabel)
                        .addComponent(sessionstartField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(23, 23, 23)
                    .addGroup(paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sessionendField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sessionendLabel))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(paraprofessionalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(walkoutCheck)
                        .addComponent(gcCheck))))
        );

        jPanel5.setMaximumSize(new java.awt.Dimension(139, 218));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        clearButton.setForeground(new java.awt.Color(153, 0, 0));
        clearButton.setText("Clear");
        clearButton.setMaximumSize(new java.awt.Dimension(121, 50));
        clearButton.setMinimumSize(new java.awt.Dimension(121, 50));
        clearButton.setPreferredSize(new java.awt.Dimension(121, 50));
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 12);
        jPanel5.add(clearButton, gridBagConstraints);

        addSessionbutton.setForeground(new java.awt.Color(51, 102, 255));
        addSessionbutton.setText("Add Session");
        addSessionbutton.setMaximumSize(new java.awt.Dimension(121, 50));
        addSessionbutton.setMinimumSize(new java.awt.Dimension(121, 50));
        addSessionbutton.setPreferredSize(new java.awt.Dimension(121, 50));
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 12);
        jPanel5.add(addSessionbutton, gridBagConstraints);

        editButton.setText("Save/Edit");
        editButton.setMaximumSize(new java.awt.Dimension(121, 44));
        editButton.setMinimumSize(new java.awt.Dimension(121, 44));
        editButton.setPreferredSize(new java.awt.Dimension(121, 44));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 12);
        jPanel5.add(editButton, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(studentInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(courseInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(paraprofessionalInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(studentInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(courseInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(paraprofessionalInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fnameComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fnameComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fnameComboActionPerformed

    private void courseComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courseComboActionPerformed

    private void paraprofessionalComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paraprofessionalComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paraprofessionalComboActionPerformed

    private void creatorComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creatorComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_creatorComboActionPerformed

    private void locationComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_locationComboActionPerformed

    private void clearButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButtonMouseClicked

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearForm();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void addSessionbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSessionbuttonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addSessionbuttonMouseClicked

    private void addSessionbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSessionbuttonActionPerformed
        //Paraprofessional t = new Paraprofessional(count, "TUTORFIRSTTEST", "TUTORLASTTEST", true);
        //Teacher teach = new Teacher(count, jComboBoxTeacher.getSelectedItem().toString(), "TestFirstName");
        //Subject sub = new Subject(count, jComboBoxCourse.getSelectedItem().toString(), "FullNameTest", new Category(count, "MABS"));
        addSessionbutton.setEnabled(false);
        getParaprofessionalSession(false);
        addSessionbutton.setEnabled(true);
    }//GEN-LAST:event_addSessionbuttonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed

        //Check for errors and then save to database
        boolean updated = getParaprofessionalSession(true);

        if (updated)
        {
            editButton.setVisible(false);
        }
    }//GEN-LAST:event_editButtonActionPerformed

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
            java.util.logging.Logger.getLogger(NewParaprofessionalSessionObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewParaprofessionalSessionObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewParaprofessionalSessionObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewParaprofessionalSessionObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewParaprofessionalSessionObject dialog = new NewParaprofessionalSessionObject(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ParaprofessionalLabel;
    private javax.swing.JButton addSessionbutton;
    private javax.swing.JButton clearButton;
    private javax.swing.JComboBox courseCombo;
    private javax.swing.JPanel courseInfoPanel;
    private javax.swing.JLabel courseLabel;
    private javax.swing.JComboBox creatorCombo;
    private javax.swing.JLabel creatorLabel;
    private javax.swing.JButton editButton;
    private javax.swing.JComboBox emailCombo;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JComboBox fnameCombo;
    private javax.swing.JLabel fnameLabel;
    private javax.swing.JCheckBox gcCheck;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox levelCombo;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JComboBox lnameCombo;
    private javax.swing.JLabel lnameLabel;
    private javax.swing.JComboBox locationCombo;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JTextArea notesField;
    private javax.swing.JLabel notesLabel;
    private javax.swing.JComboBox paraprofessionalCombo;
    private javax.swing.JPanel paraprofessionalInfoPanel;
    private javax.swing.JComboBox phoneCombo;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JTextField sessionendField;
    private javax.swing.JLabel sessionendLabel;
    private javax.swing.JTextField sessionstartField;
    private javax.swing.JLabel sessionstartLabel;
    private javax.swing.JPanel studentInfoPanel;
    private javax.swing.JComboBox teacherCombo;
    private javax.swing.JLabel teacherLabel;
    private javax.swing.JCheckBox walkoutCheck;
    // End of variables declaration//GEN-END:variables
}
