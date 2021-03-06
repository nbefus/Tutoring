/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.old;

import tutoring.old.SIAOld;
import tutoring.old.UltimateAutoCompleteClientOld;
import UIs.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.MouseWheelListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import tutoring.entity.*;
import tutoring.helper.*;
import tutoring.dialogs.NewAgendaObject;
import tutoring.dialogs.NewClientObject;


/**
 *
 * @author shohe_i
 */
public class SIAViewMeOld extends javax.swing.JFrame {

    /**
     * Creates new form SIA
     */
    
    
    public enum ComboBoxesIndexes
    {   
        CFNAME(0, "First Name", "fname", 'd'),
        CLNAME(1, "Last Name", "lname", 'd'),
        CPHONE(2,"Phone", "phone", 'd'),
        CEMAIL(3, "Email", "email", 'd'),
        //CATEGORY(4, "Category", "name", 'a'),
        COURSE(0, "Course", "abbrevName", 's'),
        CREATOR(0, "Creator", "", 'e'),
        LEVEL(1, "Level", "level", 'c'),
        LOCATION(1, "Location", "location",'l'),
        PARAPROFESSIONAL(2, "Tutor","", 'p'),
        TEACHER(2, "Teacher", "concat_ws(' ', t.fname, t.lname)", 't');
        
        private int indexOfCombo;
        private String displayName;
        private String databaseName;
        private char letter;
        
	private ComboBoxesIndexes(int i, String displayName, String databaseName, char letter) {
		indexOfCombo = i;
                this.displayName = displayName;
                this.databaseName = databaseName;
                this.letter = letter;
	}
        
        public char getLetter()
        {
            return letter;
        }
 
	public int getBoxIndex() {
		return indexOfCombo;
	}
        
        public String getDisplayName() {
		return displayName;
	}
        
        public String getDatabaseName()
        {
            return databaseName;
        }
        
        public String getDatabaseName(String DisplayName)
        {
            ComboBoxesIndexes[] components = ComboBoxesIndexes.class.getEnumConstants();
            for(int i=0; i< components.length; i++)
                if(components[i].getDisplayName().equalsIgnoreCase(DisplayName))
                    return components[i].getDatabaseName();
            
            return "";
        }
    }
    private UltimateAutoComplete uac;
    
    UltimateAutoAutoComplete uaacClient; 

    UltimateAutoAutoComplete uaacCourse;
    
    public SIAViewMeOld() 
    {
        initComponents();
        
        sessionstartField.setText("mm/dd/yyyy hh:mm aa");
        sessionendField.setText("mm/dd/yyyy hh:mm aa");
        editSaveButton.setVisible(false);
        
        SessionTableHelper tableHelper = new SessionTableHelper(sessionsTable, false);
        SessionTableHelper tableHelperFuture = new SessionTableHelper(appointmentsTable, true);
        AgendaTableHelper tableHelperAgenda = new AgendaTableHelper(agendaTable);
         tableHelperAgenda.allowScrollingOnTable();
       
        tableHelperAgenda.increaseRowHeight(12);
        
        tableHelperFuture.allowScrollingOnTable();
        tableHelperFuture.increaseRowHeight(12);
        
        tableHelper.allowScrollingOnTable();
       
        tableHelper.increaseRowHeight(12);
       
       // sessionsTable.setCellSelectionEnabled(true);

        DatabaseHelper.open();
        
       Data d = new Data(false);
       System.out.println("DATA");
       //Clients autocomplete
      JComboBox[] cboxes = new  JComboBox[4];
       cboxes[0]=fnameCombo;
       cboxes[1]=lnameCombo;
       cboxes[2]=phoneCombo;
       cboxes[3]=emailCombo;
       
       ArrayList<ArrayList<String>> cultimateList = new ArrayList<ArrayList<String>>();
System.out.println("LIST1");
       cultimateList.add(Data.getClientsfirst());
       cultimateList.add(Data.getClientslast());
       cultimateList.add(Data.getClientsphone());
       cultimateList.add(Data.getClientsemail());
       System.out.println("DONE LIST1");
       ArrayList<ArrayList<String>> cultimateList1 = new ArrayList<ArrayList<String>>();
System.out.println("LIST 2");
       cultimateList1.add(Data.getFnameOrderedList());
       cultimateList1.add(Data.getLnameOrderedList());
       cultimateList1.add(Data.getPhoneOrderedList());
       cultimateList1.add(Data.getEmailOrderedList());
System.out.println("DONE LIST2");
       uaacClient = new UltimateAutoAutoComplete(cultimateList, cboxes, cultimateList1);//Data.getClientFirst(), Data.getClientLast(), Data.getClientPhone(), Data.getClientEmail());
      
       
       JComboBox[] cboxes2 = new  JComboBox[3];
       cboxes2[0]=courseCombo;
       cboxes2[1]=levelCombo;
       cboxes2[2]=teacherCombo;
       //cboxes[3]=emailCombo;
       System.out.println("LIST 3");
       ArrayList<ArrayList<String>> cultimateList2 = new ArrayList<ArrayList<String>>();

       cultimateList2.add(Data.getSubjectslist());
       cultimateList2.add(Data.getLevelslist());
       cultimateList2.add(Data.getTeacherslist());
System.out.println("DONE list 3");
       ArrayList<ArrayList<String>> cultimateList22 = new ArrayList<ArrayList<String>>();
System.out.println("LIst 4");
       cultimateList22.add(Data.getSubjectOrderedList());
       cultimateList22.add(Data.getLevelOrderedList());
       cultimateList22.add(Data.getTeacherOrderedList());
System.out.println("Done list 4");
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
      
        Timestamp now = new Timestamp((new Date()).getTime());
        /*
        Transaction trns = null;
        ArrayList<ParaprofessionalSession> result = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        //if(session == null)
        //    session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            Query query = session.createQuery("from ParaprofessionalSession as ps where (ps.sessionStart IS NULL or (ps.sessionStart <= '"+now.toString()+"' and ps.sessionEnd IS NULL)) AND walkout='false'");
            result = (ArrayList<ParaprofessionalSession>) query.list();
            if(result.size() > 0)
            {
                for(int i=0; i<result.size(); i++)
                {
                    ((SessionTableModel) sessionsTable.getModel()).addRow(result.get(i)); 
                }

                sessionsTable.repaint();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        trns = null;
        result = null;
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Query query = session.createQuery("from ParaprofessionalSession as ps where (ps.sessionStart IS NOT NULL and ps.sessionEnd IS NULL) AND ps.sessionStart >= '"+now.toString()+"' AND walkout='false'");
            result = (ArrayList<ParaprofessionalSession>) query.list();
            
             if(result.size() > 0)
            {
                for(int i=0; i<result.size(); i++)
                {
                    ((SessionTableModel) appointmentsTable.getModel()).addRow(result.get(i)); 
                }

                appointmentsTable.repaint();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }*/
        
       System.out.println("SESSIONS");
       
       String sessStartCol = ParaprofessionalSession.ParaSessTable.SESSIONSTART.getWithAlias();
       String sessEndCol = ParaprofessionalSession.ParaSessTable.SESSIONEND.getWithAlias();
       String walkoutCol = ParaprofessionalSession.ParaSessTable.WALKOUT.getWithAlias();
       
      String currentSessionsWhere = " where ("+sessStartCol+" IS NULL or ("+sessStartCol+" <= '"+now.toString()+"' and "+sessEndCol+" IS NULL)) AND "+walkoutCol+"='false'";

       ArrayList<ParaprofessionalSession> sessions = ParaprofessionalSession.selectAllParaprofessionalSession(currentSessionsWhere,DatabaseHelper.getConnection());
        if(sessions.size() > 0)
        {
            for(int i=0; i<sessions.size(); i++)
            {
                ((SessionTableModel) sessionsTable.getModel()).addRow(sessions.get(i)); 
            }
            
            sessionsTable.repaint();
        }
        
        System.out.println("SESSIONS AGIAN");
        
        String futureSessionsWhere = " where ("+sessStartCol+" IS NOT NULL and "+sessEndCol+" IS NULL) AND "+sessStartCol+" >= '"+now.toString()+"' AND "+walkoutCol+"='false'";

        ArrayList<ParaprofessionalSession> futureSessions = ParaprofessionalSession.selectAllParaprofessionalSession(futureSessionsWhere, DatabaseHelper.getConnection());
        if(futureSessions.size() > 0)
        {
            for(int i=0; i<futureSessions.size(); i++)
            {
                ((SessionTableModel) appointmentsTable.getModel()).addRow(futureSessions.get(i)); 
            }
            
            appointmentsTable.repaint();
        }
        
        
        /*
         ArrayList<ParaprofessionalSession> sessions = (ArrayList<ParaprofessionalSession>)HibernateTest.select("from ParaprofessionalSession as ps where (ps.sessionStart IS NULL or ps.sessionEnd IS NULL) AND walkout='false'");

        if(sessions.size() > 0)
        {
            for(int i=0; i<sessions.size(); i++)
            {
                ((SessionTableModel) sessionsTable.getModel()).addRow(sessions.get(i)); 
            }
            
            sessionsTable.repaint();
        }*/
        
        
        
        Timer timer = new Timer("Minute Update");
 
        //2- Taking an instance of class contains your repeated method.
        MinuteUpdate min = new MinuteUpdate((SessionTableModel)sessionsTable.getModel());
 
        MinuteUpdate min2 = new MinuteUpdate((SessionTableModel)appointmentsTable.getModel());

        timer.schedule(min, 0, 60000);
        timer.schedule(min2, 0, 60000);
        
        
        
        DefaultCellEditor dce = makeEditSessionCellEditor();
        setUpAgenda();
        tableHelper.setTableRendersAndEditors(true, dce);
        tableHelperFuture.setTableRendersAndEditors(true, dce);
        tableHelperAgenda.setTableRendersAndEditors(true, dce);
        //tableHelperAgenda.autoResizeColWidth();
        tableHelper.autoResizeColWidth();
        tableHelperFuture.autoResizeColWidth();
        //tableHelper.fasterScrolling(20);
            
    SIAScrollPanel.getVerticalScrollBar().setUnitIncrement(20);
    
    DatabaseHelper.close();
    }
    
    
    
    public void setUpAgenda()
    {
        
        Timestamp now = new Timestamp((new Date()).getTime());
        
        String fromCurrentDateWhere = " where "+Agenda.AgendaTable.DATE.getWithAlias()+" >= '2012-04-18'";

        ArrayList<Agenda> agenda = Agenda.selectAllAgenda(fromCurrentDateWhere, DatabaseHelper.getConnection());

        if(agenda.size() > 0)
        {          
            
            for(int i=0; i<agenda.size(); i++)
            {
                
                Agenda a = agenda.get(i);
                ((AgendaTableModel) agendaTable.getModel()).addRow(a);
                //System.out.println("AGENDA : "+a.getAgendaID()+" "+ a.getDate()+" "+ a.getNotes()+" " +a.getAgendaCategoryID().getType());
                
            }
            
            agendaTable.repaint();
            
            /*agendaTable = new JTable(new DefaultTableModel(null,new Object[]{"Agenda ID", "Date", "Notes", "Type"}));
            DefaultTableModel dtm = (DefaultTableModel) agendaTable.getModel();
            
            for(int i=0; i<agenda.size(); i++)
            {
                
                Agenda a = agenda.get(i);
                dtm.addRow(new Object[]{a.getAgendaID(), a.getDate(), a.getNotes(), a.getAgendaCategoryID().getType()});
                System.out.println("AGENDA : "+a.getAgendaID()+" "+ a.getDate()+" "+ a.getNotes()+" " +a.getAgendaCategoryID().getType() +" "+ dtm.getColumnCount() + " "+dtm.getRowCount());
                
            }
                //model.addRow(new Object[] {""); 
            
            agendaTable.setModel(dtm);
            dtm.fireTableDataChanged();
            agendaTable.invalidate();
            agendaTable.repaint();*/
        }
        else
            System.out.println("EEEMMMMMMPPPPPPTTYYYYY");
        
        System.out.println("AGENDA AGENDA AGENDA AGENDA AGENDA DONE DONE DONE DONE DONE");
        
    }
    
    public DefaultCellEditor makeEditSessionCellEditor()
    {
        DefaultCellEditor dce = new DefaultCellEditor(new JTextField())
        {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                        boolean isSelected, int row, int column) 
            {
                uaacCourse.setComboValue(table.getValueAt(row, SessionTableModel.Columns.TEACHER.getColumnIndex()).toString(), ComboBoxesIndexes.TEACHER.getBoxIndex());
                uaacCourse.setComboValue(table.getValueAt(row, SessionTableModel.Columns.LEVEL.getColumnIndex()).toString(), ComboBoxesIndexes.LEVEL.getBoxIndex());
                uaacCourse.setComboValue(table.getValueAt(row, SessionTableModel.Columns.COURSE.getColumnIndex()).toString(), ComboBoxesIndexes.COURSE.getBoxIndex());
                uaacClient.setComboValue(table.getValueAt(row, SessionTableModel.Columns.CLIENTEMAIL.getColumnIndex()).toString(), ComboBoxesIndexes.CEMAIL.getBoxIndex());
                uaacClient.setComboValue(table.getValueAt(row, SessionTableModel.Columns.CLIENTPHONE.getColumnIndex()).toString(), ComboBoxesIndexes.CPHONE.getBoxIndex());
                uaacClient.setComboValue(table.getValueAt(row, SessionTableModel.Columns.CLIENTLASTNAME.getColumnIndex()).toString(), ComboBoxesIndexes.CLNAME.getBoxIndex());
                uaacClient.setComboValue(table.getValueAt(row, SessionTableModel.Columns.CLIENTFIRSTNAME.getColumnIndex()).toString(), ComboBoxesIndexes.CFNAME.getBoxIndex());
                //uac.setComboValue(table.getValueAt(row, SessionTableModel.Columns.CATEGORY.getColumnIndex()).toString(), ComboBoxesIndexes.CATEGORY.getBoxIndex());
                uac.setComboValue(table.getValueAt(row, SessionTableModel.Columns.PARAPROFESSIONAL.getColumnIndex()).toString(), ComboBoxesIndexes.PARAPROFESSIONAL.getBoxIndex());
                uac.setComboValue(table.getValueAt(row, SessionTableModel.Columns.LOCATION.getColumnIndex()).toString(), ComboBoxesIndexes.LOCATION.getBoxIndex());
                uac.setComboValue(table.getValueAt(row, SessionTableModel.Columns.CREATOR.getColumnIndex()).toString(), ComboBoxesIndexes.CREATOR.getBoxIndex());
                
                gcCheck.setSelected((Boolean)table.getValueAt(row, SessionTableModel.Columns.GC.getColumnIndex()));
                
                notesField.setText(table.getValueAt(row, SessionTableModel.Columns.NOTES.getColumnIndex()).toString());
                
               // System.out.println("LKJDSFLDSJLKDSJFLKSDJF DSLJDSFLKDSJ "+table.getValueAt(row, SessionTableModel.Columns.START.getColumnIndex()));
               // System.out.println("LKJDSFLDSJLKDSJFLKSDJF DSLJDSFLKDSJ "+table.getValueAt(row, SessionTableModel.Columns.STOP.getColumnIndex()));
                               
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
               
                boolean hasSessionStart = (table.getValueAt(row, SessionTableModel.Columns.START.getColumnIndex()) != null && Validate.validateTimestamp(sdf.format(new Date(((Timestamp)table.getValueAt(row, SessionTableModel.Columns.START.getColumnIndex())).getTime()))) && !sdf.format(new Date(((Timestamp)table.getValueAt(row, SessionTableModel.Columns.START.getColumnIndex())).getTime())).equals("12/31/9999 12:00 PM"));
                boolean hasSessionEnd = (table.getValueAt(row, SessionTableModel.Columns.STOP.getColumnIndex()) != null && Validate.validateTimestamp(sdf.format(new Date(((Timestamp)table.getValueAt(row, SessionTableModel.Columns.STOP.getColumnIndex())).getTime())))  && !sdf.format(new Date(((Timestamp)table.getValueAt(row, SessionTableModel.Columns.STOP.getColumnIndex())).getTime())).equals("12/31/9999 12:00 PM"));
                
                
                if(hasSessionStart)
                    sessionstartField.setText(sdf.format(new Date(((Timestamp)table.getValueAt(row, SessionTableModel.Columns.START.getColumnIndex())).getTime())));
                else
                    sessionstartField.setText("mm/dd/yyyy hh:mm aa");

                if(hasSessionEnd)
                    sessionendField.setText(sdf.format(new Date(((Timestamp)table.getValueAt(row, SessionTableModel.Columns.STOP.getColumnIndex())).getTime())));
                else
                    sessionendField.setText("mm/dd/yyyy hh:mm aa");
                
                walkoutCheck.setSelected((Boolean)table.getValueAt(row, SessionTableModel.Columns.WALKOUT.getColumnIndex()));
                
                editSaveButton.setVisible(true);
               
                return null;
            }
        };
        
        return dce;
    }
    
    public void clearComboBoxes()
    {
         for(int i=0; i<uac.getBoxesLength(); i++)
            uac.setComboValue("", i);
         for(int i=0; i<uaacClient.getBoxesLength(); i++)
            uaacClient.setComboValue("", i);
         for(int i=0; i<uaacCourse.getBoxesLength(); i++)
            uaacCourse.setComboValue("", i);
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
        otherInfoPanel = new javax.swing.JPanel();
        notesLabel = new javax.swing.JLabel();
        notesField = new javax.swing.JTextField();
        gcCheck = new javax.swing.JCheckBox();
        walkoutCheck = new javax.swing.JCheckBox();
        sessionstartLabel = new javax.swing.JLabel();
        sessionstartField = new javax.swing.JTextField();
        sessionendLabel = new javax.swing.JLabel();
        sessionendField = new javax.swing.JTextField();
        editSaveButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        addSessionbutton = new javax.swing.JButton();
        newStudentButton = new javax.swing.JButton();
        sessionsTablePanel = new javax.swing.JPanel();
        sessionsTableScrollPanel = new javax.swing.JScrollPane();
        sessionsTable = new javax.swing.JTable();
        deleteSessionButton = new javax.swing.JButton();
        futureSessionsPanel = new javax.swing.JPanel();
        appointmentsTableScrollPanel = new javax.swing.JScrollPane();
        appointmentsTable = new javax.swing.JTable();
        deleteSessionButton1 = new javax.swing.JButton();
        agendaPanel = new javax.swing.JPanel();
        deleteAgendaButton = new javax.swing.JButton();
        agendaTableScrollPanel = new javax.swing.JScrollPane();
        agendaTable = new javax.swing.JTable();
        addAgendaItemButton = new javax.swing.JButton();
        paraprofessionalInfoPanel = new javax.swing.JPanel();
        ParaprofessionalLabel = new javax.swing.JLabel();
        paraprofessionalCombo = new javax.swing.JComboBox();
        locationInfoPanel = new javax.swing.JPanel();
        locationCombo = new javax.swing.JComboBox();
        locationLabel = new javax.swing.JLabel();
        creatorInfoPanel = new javax.swing.JPanel();
        creatorCombo = new javax.swing.JComboBox();
        creatorLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        studentInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        fnameLabel.setText("First Name*");

        fnameCombo.setEditable(true);

        lnameLabel.setText("Last Name*");

        lnameCombo.setEditable(true);

        emailLabel.setText("Email");

        emailCombo.setEditable(true);

        phoneLabel.setText("Telephone");

        phoneCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout studentInfoPanelLayout = new org.jdesktop.layout.GroupLayout(studentInfoPanel);
        studentInfoPanel.setLayout(studentInfoPanelLayout);
        studentInfoPanelLayout.setHorizontalGroup(
            studentInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(fnameLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(fnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(lnameLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(emailLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(emailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(phoneLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(phoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 146, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
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

        courseLabel.setText("Course*");

        courseCombo.setEditable(true);

        levelLabel.setText("Course#*");

        levelCombo.setEditable(true);

        teacherLabel.setText("Teacher*");

        teacherCombo.setEditable(true);
        teacherCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teacherComboActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout courseInfoPanelLayout = new org.jdesktop.layout.GroupLayout(courseInfoPanel);
        courseInfoPanel.setLayout(courseInfoPanelLayout);
        courseInfoPanelLayout.setHorizontalGroup(
            courseInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .add(teacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 243, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        courseInfoPanelLayout.setVerticalGroup(
            courseInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(courseLabel)
                .add(courseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(levelLabel)
                .add(levelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(teacherLabel)
                .add(teacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        otherInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Other Information"));

        notesLabel.setText("Notes");

        gcCheck.setText("GC");

        walkoutCheck.setText("Walkout");

        sessionstartLabel.setText("Session Start");

        sessionstartField.setText("dd/mm/yyyy hh:mm aa");

        sessionendLabel.setText("Session End");

        sessionendField.setText("dd/mm/yyyy hh:mm:ss aa");

        editSaveButton.setText("Save/Edit");
        editSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSaveButtonActionPerformed(evt);
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

        newStudentButton.setText("New Student");
        newStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newStudentButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout otherInfoPanelLayout = new org.jdesktop.layout.GroupLayout(otherInfoPanel);
        otherInfoPanel.setLayout(otherInfoPanelLayout);
        otherInfoPanelLayout.setHorizontalGroup(
            otherInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(notesLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(notesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(sessionstartLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(sessionendLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 158, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(gcCheck)
                .add(18, 18, 18)
                .add(walkoutCheck)
                .add(27, 27, 27)
                .add(clearButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(newStudentButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 111, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(editSaveButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addSessionbutton)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        otherInfoPanelLayout.setVerticalGroup(
            otherInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanelLayout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(otherInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(notesLabel)
                    .add(notesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionstartLabel)
                    .add(sessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionendLabel)
                    .add(sessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(gcCheck)
                    .add(walkoutCheck)
                    .add(editSaveButton)
                    .add(clearButton)
                    .add(addSessionbutton)
                    .add(newStudentButton))
                .add(0, 0, 0))
        );

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
                .add(sessionsTableScrollPanel)
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsTablePanelLayout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(deleteSessionButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        sessionsTablePanelLayout.setVerticalGroup(
            sessionsTablePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsTablePanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(sessionsTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 229, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteSessionButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(108, 108, 108))
        );

        futureSessionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Future Sessions"));

        appointmentsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        appointmentsTableScrollPanel.setViewportView(appointmentsTable);

        deleteSessionButton1.setText("Delete Session");
        deleteSessionButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSessionButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout futureSessionsPanelLayout = new org.jdesktop.layout.GroupLayout(futureSessionsPanel);
        futureSessionsPanel.setLayout(futureSessionsPanelLayout);
        futureSessionsPanelLayout.setHorizontalGroup(
            futureSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(futureSessionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(futureSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(appointmentsTableScrollPanel)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, futureSessionsPanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(deleteSessionButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        futureSessionsPanelLayout.setVerticalGroup(
            futureSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, futureSessionsPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(appointmentsTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 179, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteSessionButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        agendaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Agendas"));

        deleteAgendaButton.setText("Delete Item");

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

        addAgendaItemButton.setText("Add Item");
        addAgendaItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAgendaItemButtonActionPerformed(evt);
            }
        });

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
                        .add(addAgendaItemButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(deleteAgendaButton)))
                .addContainerGap())
        );
        agendaPanelLayout.setVerticalGroup(
            agendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanelLayout.createSequentialGroup()
                .add(agendaTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 204, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(agendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(deleteAgendaButton)
                    .add(addAgendaItemButton))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paraprofessionalInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Paraprofesional Information"));

        ParaprofessionalLabel.setText("Paraprofessional*");

        paraprofessionalCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout paraprofessionalInfoPanelLayout = new org.jdesktop.layout.GroupLayout(paraprofessionalInfoPanel);
        paraprofessionalInfoPanel.setLayout(paraprofessionalInfoPanelLayout);
        paraprofessionalInfoPanelLayout.setHorizontalGroup(
            paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(paraprofessionalInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(ParaprofessionalLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(paraprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paraprofessionalInfoPanelLayout.setVerticalGroup(
            paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(paraprofessionalInfoPanelLayout.createSequentialGroup()
                .add(paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(paraprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ParaprofessionalLabel))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        locationInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Location Information"));

        locationLabel.setText("Location*");

        org.jdesktop.layout.GroupLayout locationInfoPanelLayout = new org.jdesktop.layout.GroupLayout(locationInfoPanel);
        locationInfoPanel.setLayout(locationInfoPanelLayout);
        locationInfoPanelLayout.setHorizontalGroup(
            locationInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(locationInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(locationLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(locationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        locationInfoPanelLayout.setVerticalGroup(
            locationInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, locationInfoPanelLayout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(locationInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(locationLabel)
                    .add(locationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        creatorInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Creator Information"));

        creatorCombo.setEditable(true);

        creatorLabel.setText("Creator*");

        org.jdesktop.layout.GroupLayout creatorInfoPanelLayout = new org.jdesktop.layout.GroupLayout(creatorInfoPanel);
        creatorInfoPanel.setLayout(creatorInfoPanelLayout);
        creatorInfoPanelLayout.setHorizontalGroup(
            creatorInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(creatorInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(creatorLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(creatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        creatorInfoPanelLayout.setVerticalGroup(
            creatorInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, creatorInfoPanelLayout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(creatorInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(creatorLabel)
                    .add(creatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        org.jdesktop.layout.GroupLayout sessionsAndAgendaPanelLayout = new org.jdesktop.layout.GroupLayout(sessionsAndAgendaPanel);
        sessionsAndAgendaPanel.setLayout(sessionsAndAgendaPanelLayout);
        sessionsAndAgendaPanelLayout.setHorizontalGroup(
            sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                        .add(studentInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(paraprofessionalInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                        .add(courseInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(locationInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(creatorInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(sessionsTablePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(futureSessionsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(agendaPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(otherInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(0, 165, Short.MAX_VALUE))
        );
        sessionsAndAgendaPanelLayout.setVerticalGroup(
            sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(paraprofessionalInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(studentInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(creatorInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(courseInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(locationInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(otherInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 34, Short.MAX_VALUE)
                .add(sessionsTablePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 308, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(futureSessionsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(agendaPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        SIAScrollPanel.setViewportView(sessionsAndAgendaPanel);

        SIAPannel.addTab("Sessions / Agenda", SIAScrollPanel);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(SIAPannel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(SIAPannel)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteSessionButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSessionButton1ActionPerformed
        
        int[] selectedRows = sessionsTable.getSelectedRows();

        ((SessionTableModel) appointmentsTable.getModel()).deleteRows(selectedRows);
    }//GEN-LAST:event_deleteSessionButton1ActionPerformed

    private void deleteSessionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSessionButtonActionPerformed
        int[] selectedRows = sessionsTable.getSelectedRows();

        ((SessionTableModel)sessionsTable.getModel()).deleteRows(selectedRows);
    }//GEN-LAST:event_deleteSessionButtonActionPerformed

    private void newStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newStudentButtonActionPerformed

        NewClientObject ndo = new NewClientObject(new Frame(),true);
        ndo.setVisible(true);
        //ndo.setLocationRelativeTo(null);

    }//GEN-LAST:event_newStudentButtonActionPerformed

    private void addSessionbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSessionbuttonActionPerformed
        //Paraprofessional t = new Paraprofessional(count, "TUTORFIRSTTEST", "TUTORLASTTEST", true);
        //Teacher teach = new Teacher(count, jComboBoxTeacher.getSelectedItem().toString(), "TestFirstName");
        //Subject sub = new Subject(count, jComboBoxCourse.getSelectedItem().toString(), "FullNameTest", new Category(count, "MABS"));
        getParaprofessionalSession(false);
    }//GEN-LAST:event_addSessionbuttonActionPerformed

    private void addSessionbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSessionbuttonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addSessionbuttonMouseClicked

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearComboBoxes();
        sessionstartField.setText("mm/dd/yyyy hh:mm aa");
        sessionendField.setText("mm/dd/yyyy hh:mm aa");
        editSaveButton.setVisible(false);
        notesField.setText("");
        gcCheck.setSelected(false);
        walkoutCheck.setSelected(false);
    }//GEN-LAST:event_clearButtonActionPerformed

    private void clearButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButtonMouseClicked

    private void editSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSaveButtonActionPerformed

        //Check for errors and then save to database
        boolean updated = getParaprofessionalSession(true);

        if(updated)
            editSaveButton.setVisible(false);
    }//GEN-LAST:event_editSaveButtonActionPerformed

    private void teacherComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacherComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_teacherComboActionPerformed

    private void addAgendaItemButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addAgendaItemButtonActionPerformed
    {//GEN-HEADEREND:event_addAgendaItemButtonActionPerformed

        NewAgendaObject ndo = new NewAgendaObject(new Frame(), true, Data.getAgendaCategoryList());
        ndo.setVisible(true);
        //ndo.setLocationRelativeTo(null);

    }//GEN-LAST:event_addAgendaItemButtonActionPerformed

    
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
            otherInfoPanel.repaint();
            courseInfoPanel.repaint();
            creatorInfoPanel.repaint();
            paraprofessionalInfoPanel.repaint();
            locationInfoPanel.repaint();
            
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
                courses = (ArrayList<Course>)Course.selectAllCourse("where "+subjectIDString+"="+subjects.get(0).getSubjectID()+" and "+teachIDString+"="+teachers.get(0).getTeacherID() + " and "+levelString+"="+intLevel.intValue(), DatabaseHelper.getConnection());
            }
            catch(Exception z)
            {
                courses = new ArrayList<Course>();
                //coursePanelCheck = true;
            }
            ArrayList<Paraprofessional> paraprofessionals = (ArrayList<Paraprofessional>)Paraprofessional.selectAllParaprofessional("where concat(concat("+paraFNameString+",' '),"+paraLNameString+")='"+pName.trim()+"'", DatabaseHelper.getConnection());

            ArrayList<Paraprofessional> creators = (ArrayList<Paraprofessional>)Paraprofessional.selectAllParaprofessional("where concat(concat("+paraFNameString+",' '),"+paraLNameString+")='"+cName.trim()+"'", DatabaseHelper.getConnection());

            ArrayList<Client> clients = (ArrayList<Client>)Client.selectAllClients("where "+clientFNameString+"='"+clientFName.trim()+"' and "+clientLNameString+"='"+clientLName.trim()+"'", DatabaseHelper.getConnection());

            ArrayList<Location> locations = (ArrayList<Location>)Location.selectAllLocation("where "+Location.LocationTable.NAME.getWithAlias()+"='"+location.trim()+"'", DatabaseHelper.getConnection());

            courseInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
            studentInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
            otherInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
            creatorInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
            paraprofessionalInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
            locationInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
            
               
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
                creatorInfoPanel.setBackground(Color.red);
            }
            if(clients.size() <= 0 && clientPanelCheck)
            {
                System.out.println("Clients less than 1");
                studentInfoPanel.setBackground(Color.red);
            }
            if(locations.size() <= 0 && locationPanelCheck)
            {
                System.out.println("Locations less than 1");
                locationInfoPanel.setBackground(Color.red);
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
                ((SessionTableModel) sessionsTable.getModel()).addRow(sessions.get(0));
            }
            else
            {
                

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
                    ps.setParaprofessionalSessionID(sessions.get(0).getParaprofessionalSessionID());
                    
                    DatabaseHelper.update(ParaprofessionalSession.getValues(ps), ParaprofessionalSession.ParaSessTable.getTable());
                    //HibernateTest.update(ps);
                    //!!!
                    //Call reload data
                    //!!!
                    //Remove when finished
                }
            }

            sessionsTable.repaint();
            
            Thread thread = new Thread(){
                public void run(){
                     int colorOfGreen = 160;

                    while(colorOfGreen < 255)
                    {
                        //sessionsAndAgendaPanel.setBackground(new Color( 0,colorOfGreen,0));
                        courseInfoPanel.setBackground(new Color( 0,colorOfGreen,0));
                        studentInfoPanel.setBackground(new Color(0,colorOfGreen,0));
                        otherInfoPanel.setBackground(new Color(0,colorOfGreen,0));
                        creatorInfoPanel.setBackground(new Color(0,colorOfGreen,0));
                        paraprofessionalInfoPanel.setBackground(new Color(0,colorOfGreen,0));
                        locationInfoPanel.setBackground(new Color(0,colorOfGreen,0));
                        colorOfGreen += 2;
                        try {
                            this.sleep(13);
                        } catch (InterruptedException ex) {
                        }
                    }  
                    //sessionsAndAgendaPanel.setBackground(sessionsTablePanel.getBackground());
                    courseInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
                    studentInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
                    otherInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground()); 
                    creatorInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
                    paraprofessionalInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
                    locationInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
                }
            };

            thread.start();
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
            java.util.logging.Logger.getLogger(SIAOld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SIAOld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SIAOld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SIAOld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SIAViewMeOld().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ParaprofessionalLabel;
    private javax.swing.JTabbedPane SIAPannel;
    private javax.swing.JScrollPane SIAScrollPanel;
    private javax.swing.JButton addAgendaItemButton;
    private javax.swing.JButton addSessionbutton;
    private javax.swing.JPanel agendaPanel;
    private javax.swing.JTable agendaTable;
    private javax.swing.JScrollPane agendaTableScrollPanel;
    private javax.swing.JTable appointmentsTable;
    private javax.swing.JScrollPane appointmentsTableScrollPanel;
    private javax.swing.JButton clearButton;
    private javax.swing.JComboBox courseCombo;
    private javax.swing.JPanel courseInfoPanel;
    private javax.swing.JLabel courseLabel;
    private javax.swing.JComboBox creatorCombo;
    private javax.swing.JPanel creatorInfoPanel;
    private javax.swing.JLabel creatorLabel;
    private javax.swing.JButton deleteAgendaButton;
    private javax.swing.JButton deleteSessionButton;
    private javax.swing.JButton deleteSessionButton1;
    private javax.swing.JButton editSaveButton;
    private javax.swing.JComboBox emailCombo;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JComboBox fnameCombo;
    private javax.swing.JLabel fnameLabel;
    private javax.swing.JPanel futureSessionsPanel;
    private javax.swing.JCheckBox gcCheck;
    private javax.swing.JComboBox levelCombo;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JComboBox lnameCombo;
    private javax.swing.JLabel lnameLabel;
    private javax.swing.JComboBox locationCombo;
    private javax.swing.JPanel locationInfoPanel;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JButton newStudentButton;
    private javax.swing.JTextField notesField;
    private javax.swing.JLabel notesLabel;
    private javax.swing.JPanel otherInfoPanel;
    private javax.swing.JComboBox paraprofessionalCombo;
    private javax.swing.JPanel paraprofessionalInfoPanel;
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
    private javax.swing.JComboBox teacherCombo;
    private javax.swing.JLabel teacherLabel;
    private javax.swing.JCheckBox walkoutCheck;
    // End of variables declaration//GEN-END:variables
}
