/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.ui;

import UIs.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.event.MouseWheelListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import tutoring.entity.Category;
import tutoring.entity.Client;
import tutoring.entity.Course;
import tutoring.entity.Location;
import tutoring.entity.Paraprofessional;
import tutoring.entity.ParaprofessionalSession;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.helper.ComboBoxCellEditor;
import tutoring.helper.Data;
import tutoring.helper.HibernateTest;
import tutoring.helper.MinuteCellRenderer;
import tutoring.helper.MinuteUpdate;
import tutoring.helper.SessionTableModel;
import tutoring.helper.TimestampCellEditor;
import tutoring.helper.TimestampCellRenderer;
import tutoring.helper.UltimateAutoComplete;
import tutoring.helper.UltimateAutoCompleteClientNew;

/**
 *
 * @author shohe_i
 */
public class Admin extends javax.swing.JFrame {

    /**
     * Creates new form Admin
     */
    private UltimateAutoComplete uac; 
    private UltimateAutoCompleteClientNew uacc;
    public Admin() {
        initComponents();
        
        setUpReportTab();

    }
    
    
    public void setUpReportTab()
    {
        DefaultListModel dlm = new DefaultListModel();
        dlm.add(0, "Select all Users");
        reportList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        reportList.setModel(dlm);
        
        reportList.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = reportList.getSelectedIndex();
                if(index != -1)
                {
                    if(((DefaultListModel)reportList.getModel()).get(index).equals("Select all Users"))
                    {
                        List l = HibernateTest.regularSelect("select count(paraprofessionalSessionID) as 'Count', c.name from ParaprofessionalSession ps join Course course on course.courseID=ps.courseID join Subject s on course.subjectID=s.subjectID join Category c on s.categoryID=c.categoryID group by c.name");
                        //List c = HibernateTest.regularSelect("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'User'");
                        
                        String[][] data = null;
                        boolean firstTime = true;
                        Iterator it = l.iterator();
                         
                        int count = 0;
                         while(it.hasNext()) {
                            Object[] row = (Object[]) it.next();
                            
                            if(firstTime)
                            {
                                data = new String[l.size()][row.length];
                                firstTime = false;
                            }
                            
                            for(int i=0; i<row.length; i++)
                            {
                                data[count][i] = row[i].toString();
                                System.out.print("\t\t"+row[i]+"--"+row[i].getClass().toString());
                            }
                            
                           
                            
                            count++;
                         }
                         
                         
                         
                        // String[] columns = new String[c.size()];
                        // for(int i=0; i<c.size(); i++)
                        //     columns[i]=(String)c.get(i);
                           String[] columns = {"count","name"};
                         
                         DefaultTableModel dtm = new DefaultTableModel();
                         dtm.setDataVector(data, columns);
                         reportTable.setModel(dtm);
                         
                         
                         
                         
                         DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                         String series = "Category";

                         // String[] categories = new String[data.length];
                         // for(int i=0; i<data.length; i++)
                         //     categories[i] = data[i][1];

                          for(int i=0; i<data.length; i++)
                              dataset.addValue(Integer.parseInt(data[i][0]), series, data[i][1]);

                          final JFreeChart chart = createChart(dataset);
                          final ChartPanel chartPanel = new ChartPanel(chart);
                          chartPanel.setPreferredSize(thechartPanel.getSize());
                          thechartPanel.removeAll();
                          thechartPanel.add(chartPanel);
                          thechartPanel.validate();
                         // thechartPanel.setVisible(true);
                         // thechartPanel.repaint();
                            
                        
                        //reportTable
                    }
                }
            }
        });
    }
    
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "Bar Chart Demo",         // chart title
            "Category",               // domain axis label
            "Value",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, Color.lightGray
        );
        /*
        final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.lightGray
        );*/
        renderer.setSeriesPaint(0, gp0);
        /*
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);*/

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
        
    }
    
    public void setUpSearchTab()
    {
       sessionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       sessionsTable.setAutoCreateRowSorter(true);
       sessionsTable.setFillsViewportHeight(true);

       sessionsTable.setDefaultRenderer(Timestamp.class, new TimestampCellRenderer());

       
       FontMetrics fm = sessionsTable.getFontMetrics(sessionsTable.getFont());
       int fontHeight = fm.getHeight();
       sessionsTable.setRowHeight(fontHeight+12);

       
        for (MouseWheelListener listener : sessionsTableScrollPanel.getMouseWheelListeners()) {
            sessionsTableScrollPanel.removeMouseWheelListener(listener);
        }
       
       jScrollPane4.getVerticalScrollBar().setUnitIncrement(16);
       
       
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
    }
    
    public void setUpSessionsTab()
    {
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
       
       jScrollPane4.getVerticalScrollBar().setUnitIncrement(16);

     
       
       
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
 
        MinuteUpdate min = new MinuteUpdate((SessionTableModel)sessionsTable.getModel());
 
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

        adminPanel = new javax.swing.JTabbedPane();
        sessionsPanel = new javax.swing.JPanel();
        currentSessionsPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        sessionssessionTable = new javax.swing.JTable();
        sessionsdeleteSessionButton = new javax.swing.JButton();
        sessionsaddSessionbutton = new javax.swing.JButton();
        sessionsclearButton = new javax.swing.JButton();
        otherInfoPanel = new javax.swing.JPanel();
        locationLabel = new javax.swing.JLabel();
        sessionslocationCombo = new javax.swing.JComboBox();
        creatorLabel = new javax.swing.JLabel();
        notesLabel = new javax.swing.JLabel();
        sessionsnotesField = new javax.swing.JTextField();
        gcCheck = new javax.swing.JCheckBox();
        walkoutCheck = new javax.swing.JCheckBox();
        sessionstartLabel = new javax.swing.JLabel();
        sessionssessionstartField = new javax.swing.JTextField();
        sessionscreatorCombo = new javax.swing.JComboBox();
        sessionendLabel = new javax.swing.JLabel();
        sessionssessionendField = new javax.swing.JTextField();
        courseInfoPanel = new javax.swing.JPanel();
        courseLabel = new javax.swing.JLabel();
        sessionscourseCombo = new javax.swing.JComboBox();
        levelLabel = new javax.swing.JLabel();
        sessionslevelCombo = new javax.swing.JComboBox();
        teacherLabel = new javax.swing.JLabel();
        sessionsteacherCombo = new javax.swing.JComboBox();
        categoryLabel = new javax.swing.JLabel();
        sessionscategoryCombo = new javax.swing.JComboBox();
        sessionsparaprofessionalCombo = new javax.swing.JComboBox();
        ParaprofessionalLabel = new javax.swing.JLabel();
        studentInfoPanel1 = new javax.swing.JPanel();
        fnameLabel3 = new javax.swing.JLabel();
        sessionsfnameCombo = new javax.swing.JComboBox();
        lnameLabel3 = new javax.swing.JLabel();
        sessionslnameCombo = new javax.swing.JComboBox();
        emailLabel3 = new javax.swing.JLabel();
        sessionsemailCombo = new javax.swing.JComboBox();
        phoneLabel3 = new javax.swing.JLabel();
        sessionsphoneCombo = new javax.swing.JComboBox();
        autocompleteCheck = new javax.swing.JCheckBox();
        agendaPanel = new javax.swing.JPanel();
        createAgendaPanel = new javax.swing.JPanel();
        agendaCategoryLabel = new javax.swing.JLabel();
        agendaCategoryCombo = new javax.swing.JComboBox();
        agendaDateLabel = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();
        dateLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        agendaTextArea = new javax.swing.JTextArea();
        submitbutton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        agendaTable = new javax.swing.JTable();
        deleteAgendaButton = new javax.swing.JButton();
        adminScrollPanel = new javax.swing.JScrollPane();
        sessionsAndAgendaPanel = new javax.swing.JPanel();
        studentInfoPanel2 = new javax.swing.JPanel();
        fnameLabel = new javax.swing.JLabel();
        fnameCombo = new javax.swing.JComboBox();
        lnameLabel = new javax.swing.JLabel();
        lnameCombo = new javax.swing.JComboBox();
        emailLabel = new javax.swing.JLabel();
        emailCombo = new javax.swing.JComboBox();
        phoneLabel = new javax.swing.JLabel();
        phoneCombo = new javax.swing.JComboBox();
        courseInfoPanel2 = new javax.swing.JPanel();
        courseLabel2 = new javax.swing.JLabel();
        courseCombo2 = new javax.swing.JComboBox();
        levelLabel2 = new javax.swing.JLabel();
        levelCombo2 = new javax.swing.JComboBox();
        teacherLabel2 = new javax.swing.JLabel();
        teacherCombo2 = new javax.swing.JComboBox();
        categoryLabel2 = new javax.swing.JLabel();
        categoryCombo2 = new javax.swing.JComboBox();
        otherInfoPanel2 = new javax.swing.JPanel();
        locationLabel2 = new javax.swing.JLabel();
        locationCombo2 = new javax.swing.JComboBox();
        creatorLabel2 = new javax.swing.JLabel();
        creatorField2 = new javax.swing.JTextField();
        notesLabel2 = new javax.swing.JLabel();
        notesField2 = new javax.swing.JTextField();
        gcCheck2 = new javax.swing.JCheckBox();
        walkoutCheck2 = new javax.swing.JCheckBox();
        futureCheck2 = new javax.swing.JCheckBox();
        scheduleLabel = new javax.swing.JLabel();
        futureField2 = new javax.swing.JTextField();
        addSessionbutton2 = new javax.swing.JButton();
        clearButton2 = new javax.swing.JButton();
        sessionsTablePanel = new javax.swing.JPanel();
        sessionsTableScrollPanel = new javax.swing.JScrollPane();
        sessionsTable = new javax.swing.JTable();
        deleteSessionButton2 = new javax.swing.JButton();
        createAgendaPanel2 = new javax.swing.JPanel();
        agendaCategoryLabel2 = new javax.swing.JLabel();
        agendaCategoryCombo2 = new javax.swing.JComboBox();
        agendaDateLabel2 = new javax.swing.JLabel();
        dateField2 = new javax.swing.JTextField();
        dateLabel2 = new javax.swing.JLabel();
        cancelButton2 = new javax.swing.JButton();
        submitbutton2 = new javax.swing.JButton();
        agendaTextAreaScrollPanel = new javax.swing.JScrollPane();
        agendaTextArea2 = new javax.swing.JTextArea();
        agendaPanel2 = new javax.swing.JPanel();
        deleteAgendaButton2 = new javax.swing.JButton();
        agendaTableScrollPanel = new javax.swing.JScrollPane();
        agendaTable2 = new javax.swing.JTable();
        sessionsPanel1 = new javax.swing.JPanel();
        currentSessionsPanel1 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        searchsearchTable = new javax.swing.JTable();
        deleteSessionButton1 = new javax.swing.JButton();
        addSessionbutton1 = new javax.swing.JButton();
        clearButton1 = new javax.swing.JButton();
        otherInfoPanel1 = new javax.swing.JPanel();
        locationLabel1 = new javax.swing.JLabel();
        searchlocationCombo = new javax.swing.JComboBox();
        creatorLabel1 = new javax.swing.JLabel();
        notesLabel1 = new javax.swing.JLabel();
        searchnotesField = new javax.swing.JTextField();
        gcCheck1 = new javax.swing.JCheckBox();
        walkoutCheck1 = new javax.swing.JCheckBox();
        sessionstartLabel1 = new javax.swing.JLabel();
        searchsessionstartField = new javax.swing.JTextField();
        searchcreatorCombo = new javax.swing.JComboBox();
        sessionendLabel1 = new javax.swing.JLabel();
        searchsessionendField = new javax.swing.JTextField();
        courseInfoPanel1 = new javax.swing.JPanel();
        courseLabel1 = new javax.swing.JLabel();
        searchcourseCombo = new javax.swing.JComboBox();
        levelLabel1 = new javax.swing.JLabel();
        searchlevelCombo = new javax.swing.JComboBox();
        teacherLabel1 = new javax.swing.JLabel();
        searchteacherCombo = new javax.swing.JComboBox();
        categoryLabel1 = new javax.swing.JLabel();
        searchcategoryCombo = new javax.swing.JComboBox();
        searchparaprofessionalCombo = new javax.swing.JComboBox();
        ParaprofessionalLabel1 = new javax.swing.JLabel();
        studentInfoPanel3 = new javax.swing.JPanel();
        fnameLabel4 = new javax.swing.JLabel();
        searchfnameCombo = new javax.swing.JComboBox();
        lnameLabel4 = new javax.swing.JLabel();
        searchlnameCombo = new javax.swing.JComboBox();
        emailLabel4 = new javax.swing.JLabel();
        searchemailCombo = new javax.swing.JComboBox();
        phoneLabel4 = new javax.swing.JLabel();
        searchphoneCombo = new javax.swing.JComboBox();
        searchautocompleteCheck = new javax.swing.JCheckBox();
        reportPanel = new javax.swing.JPanel();
        downloadButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        reportList = new javax.swing.JList();
        thechartPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        currentSessionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Current Sessions"));

        sessionssessionTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(sessionssessionTable);

        sessionsdeleteSessionButton.setText("Delete Session");
        sessionsdeleteSessionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sessionsdeleteSessionButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout currentSessionsPanelLayout = new org.jdesktop.layout.GroupLayout(currentSessionsPanel);
        currentSessionsPanel.setLayout(currentSessionsPanelLayout);
        currentSessionsPanelLayout.setHorizontalGroup(
            currentSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, currentSessionsPanelLayout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(sessionsdeleteSessionButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(currentSessionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane4)
                .addContainerGap())
        );
        currentSessionsPanelLayout.setVerticalGroup(
            currentSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, currentSessionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 201, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(sessionsdeleteSessionButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        sessionsaddSessionbutton.setForeground(new java.awt.Color(51, 102, 255));
        sessionsaddSessionbutton.setText("Add Session");
        sessionsaddSessionbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sessionsaddSessionbuttonMouseClicked(evt);
            }
        });
        sessionsaddSessionbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sessionsaddSessionbuttonActionPerformed(evt);
            }
        });

        sessionsclearButton.setForeground(new java.awt.Color(153, 0, 0));
        sessionsclearButton.setText("Clear");
        sessionsclearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sessionsclearButtonMouseClicked(evt);
            }
        });
        sessionsclearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sessionsclearButtonActionPerformed(evt);
            }
        });

        otherInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Other Information"));

        locationLabel.setText("Location:");

        creatorLabel.setText("Creator:");

        notesLabel.setText("Notes:");

        gcCheck.setText("GC");

        walkoutCheck.setText("Walkout");

        sessionstartLabel.setText("Session Start:");

        sessionssessionstartField.setText("dd/mm/yyyy hh:mm aa");

        sessionscreatorCombo.setEditable(true);

        sessionendLabel.setText("Session End:");

        sessionssessionendField.setText("dd/mm/yyyy hh:mm:ss aa");

        org.jdesktop.layout.GroupLayout otherInfoPanelLayout = new org.jdesktop.layout.GroupLayout(otherInfoPanel);
        otherInfoPanel.setLayout(otherInfoPanelLayout);
        otherInfoPanelLayout.setHorizontalGroup(
            otherInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(locationLabel)
                .add(18, 18, 18)
                .add(sessionslocationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(creatorLabel)
                .add(18, 18, 18)
                .add(sessionscreatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(notesLabel)
                .add(18, 18, 18)
                .add(sessionsnotesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(sessionstartLabel)
                .add(18, 18, 18)
                .add(sessionssessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(sessionendLabel)
                .add(18, 18, 18)
                .add(sessionssessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .add(sessionslocationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(creatorLabel)
                    .add(sessionscreatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(notesLabel)
                    .add(sessionsnotesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionstartLabel)
                    .add(sessionssessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(gcCheck)
                    .add(walkoutCheck)
                    .add(sessionendLabel)
                    .add(sessionssessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        courseInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        courseLabel.setText("Course:");

        sessionscourseCombo.setEditable(true);

        levelLabel.setText("Course#:");

        sessionslevelCombo.setEditable(true);

        teacherLabel.setText("Teacher:");

        sessionsteacherCombo.setEditable(true);
        sessionsteacherCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sessionsteacherComboActionPerformed(evt);
            }
        });

        categoryLabel.setText("Category:");

        sessionscategoryCombo.setEditable(true);

        sessionsparaprofessionalCombo.setEditable(true);

        ParaprofessionalLabel.setText("Paraprofessional:");

        org.jdesktop.layout.GroupLayout courseInfoPanelLayout = new org.jdesktop.layout.GroupLayout(courseInfoPanel);
        courseInfoPanel.setLayout(courseInfoPanelLayout);
        courseInfoPanelLayout.setHorizontalGroup(
            courseInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(sessionscourseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(levelLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(sessionslevelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(teacherLabel)
                .add(18, 18, 18)
                .add(sessionsteacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 243, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(categoryLabel)
                .add(18, 18, 18)
                .add(sessionscategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(13, 13, 13)
                .add(ParaprofessionalLabel)
                .add(18, 18, 18)
                .add(sessionsparaprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );
        courseInfoPanelLayout.setVerticalGroup(
            courseInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanelLayout.createSequentialGroup()
                .add(courseInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel)
                    .add(sessionscourseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel)
                    .add(sessionslevelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel)
                    .add(sessionsteacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(categoryLabel)
                    .add(sessionscategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionsparaprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ParaprofessionalLabel))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        studentInfoPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        fnameLabel3.setText("First Name:");

        sessionsfnameCombo.setEditable(true);

        lnameLabel3.setText("Last Name:");

        sessionslnameCombo.setEditable(true);

        emailLabel3.setText("Email:");

        sessionsemailCombo.setEditable(true);

        phoneLabel3.setText("Phone:");

        sessionsphoneCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout studentInfoPanel1Layout = new org.jdesktop.layout.GroupLayout(studentInfoPanel1);
        studentInfoPanel1.setLayout(studentInfoPanel1Layout);
        studentInfoPanel1Layout.setHorizontalGroup(
            studentInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(fnameLabel3)
                .add(18, 18, 18)
                .add(sessionsfnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(lnameLabel3)
                .add(18, 18, 18)
                .add(sessionslnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(emailLabel3)
                .add(18, 18, 18)
                .add(sessionsemailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(phoneLabel3)
                .add(18, 18, 18)
                .add(sessionsphoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 174, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(340, Short.MAX_VALUE))
        );
        studentInfoPanel1Layout.setVerticalGroup(
            studentInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanel1Layout.createSequentialGroup()
                .add(studentInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(phoneLabel3)
                    .add(emailLabel3)
                    .add(sessionslnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lnameLabel3)
                    .add(sessionsfnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(fnameLabel3)
                    .add(sessionsemailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionsphoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6))
        );

        autocompleteCheck.setSelected(true);
        autocompleteCheck.setText("AutoComplete");
        autocompleteCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autocompleteCheckActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout sessionsPanelLayout = new org.jdesktop.layout.GroupLayout(sessionsPanel);
        sessionsPanel.setLayout(sessionsPanelLayout);
        sessionsPanelLayout.setHorizontalGroup(
            sessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(sessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(currentSessionsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsPanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(sessionsaddSessionbutton)
                        .add(2, 2, 2)
                        .add(sessionsclearButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(otherInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(courseInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(studentInfoPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .add(sessionsPanelLayout.createSequentialGroup()
                .add(209, 209, 209)
                .add(autocompleteCheck)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sessionsPanelLayout.setVerticalGroup(
            sessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(autocompleteCheck)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(studentInfoPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(courseInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(7, 7, 7)
                .add(otherInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(10, 10, 10)
                .add(sessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(sessionsaddSessionbutton)
                    .add(sessionsclearButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(currentSessionsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(290, Short.MAX_VALUE))
        );

        adminPanel.addTab("Sessions", sessionsPanel);

        agendaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Agenda"));

        createAgendaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Create New Agenda"));

        agendaCategoryLabel.setText("Category:");

        agendaCategoryCombo.setEditable(true);

        agendaDateLabel.setText("Date:");

        dateLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        dateLabel.setForeground(new java.awt.Color(102, 102, 102));
        dateLabel.setText("(mm/dd/yyyy hh:mm a.a.)");

        agendaTextArea.setColumns(20);
        agendaTextArea.setRows(5);
        jScrollPane5.setViewportView(agendaTextArea);

        submitbutton.setForeground(new java.awt.Color(51, 102, 255));
        submitbutton.setText("Submit");

        cancelButton.setForeground(new java.awt.Color(153, 0, 0));
        cancelButton.setText("Cancel");

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
        jScrollPane3.setViewportView(agendaTable);

        deleteAgendaButton.setText("Delete Agenda");

        org.jdesktop.layout.GroupLayout createAgendaPanelLayout = new org.jdesktop.layout.GroupLayout(createAgendaPanel);
        createAgendaPanel.setLayout(createAgendaPanelLayout);
        createAgendaPanelLayout.setHorizontalGroup(
            createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAgendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(agendaCategoryLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(agendaCategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 123, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(40, 40, 40)
                .add(agendaDateLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(dateLabel)
                    .add(dateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 167, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jScrollPane5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 450, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, createAgendaPanelLayout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, createAgendaPanelLayout.createSequentialGroup()
                        .add(submitbutton)
                        .add(18, 18, 18)
                        .add(cancelButton))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 934, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, deleteAgendaButton)))
        );
        createAgendaPanelLayout.setVerticalGroup(
            createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAgendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(dateLabel)
                .add(5, 5, 5)
                .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(agendaCategoryLabel)
                        .add(agendaCategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(agendaDateLabel)
                        .add(dateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(submitbutton)
                    .add(cancelButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 387, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(deleteAgendaButton)
                .add(0, 182, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout agendaPanelLayout = new org.jdesktop.layout.GroupLayout(agendaPanel);
        agendaPanel.setLayout(agendaPanelLayout);
        agendaPanelLayout.setHorizontalGroup(
            agendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanelLayout.createSequentialGroup()
                .add(112, 112, 112)
                .add(createAgendaPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(279, Short.MAX_VALUE))
        );
        agendaPanelLayout.setVerticalGroup(
            agendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanelLayout.createSequentialGroup()
                .add(createAgendaPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminPanel.addTab("Agenda", agendaPanel);

        studentInfoPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        fnameLabel.setText("First Name:");

        fnameCombo.setEditable(true);

        lnameLabel.setText("Last Name:");

        lnameCombo.setEditable(true);

        emailLabel.setText("Email:");

        emailCombo.setEditable(true);

        phoneLabel.setText("Phone:");

        phoneCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout studentInfoPanel2Layout = new org.jdesktop.layout.GroupLayout(studentInfoPanel2);
        studentInfoPanel2.setLayout(studentInfoPanel2Layout);
        studentInfoPanel2Layout.setHorizontalGroup(
            studentInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanel2Layout.createSequentialGroup()
                .add(108, 108, 108)
                .add(fnameLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(fnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(lnameLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(emailLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(emailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(71, 71, 71)
                .add(phoneLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(phoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 295, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        studentInfoPanel2Layout.setVerticalGroup(
            studentInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanel2Layout.createSequentialGroup()
                .add(studentInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
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

        courseInfoPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        courseLabel2.setText("Course:");

        courseCombo2.setEditable(true);

        levelLabel2.setText("Course#:");

        levelCombo2.setEditable(true);

        teacherLabel2.setText("Teacher:");

        teacherCombo2.setEditable(true);
        teacherCombo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teacherCombo2ActionPerformed(evt);
            }
        });

        categoryLabel2.setText("Category:");

        categoryCombo2.setEditable(true);

        org.jdesktop.layout.GroupLayout courseInfoPanel2Layout = new org.jdesktop.layout.GroupLayout(courseInfoPanel2);
        courseInfoPanel2.setLayout(courseInfoPanel2Layout);
        courseInfoPanel2Layout.setHorizontalGroup(
            courseInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanel2Layout.createSequentialGroup()
                .add(133, 133, 133)
                .add(courseLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(courseCombo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(59, 59, 59)
                .add(levelLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(levelCombo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(44, 44, 44)
                .add(teacherLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(teacherCombo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 203, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(37, 37, 37)
                .add(categoryLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(categoryCombo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        courseInfoPanel2Layout.setVerticalGroup(
            courseInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanel2Layout.createSequentialGroup()
                .add(courseInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel2)
                    .add(courseCombo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel2)
                    .add(levelCombo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel2)
                    .add(teacherCombo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(categoryLabel2)
                    .add(categoryCombo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        otherInfoPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Other Information"));

        locationLabel2.setText("Location:");

        creatorLabel2.setText("Creator:");

        creatorField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creatorField2ActionPerformed(evt);
            }
        });

        notesLabel2.setText("Notes:");

        gcCheck2.setText("GC");

        walkoutCheck2.setText("Walkout");

        futureCheck2.setText("Future");
        futureCheck2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                futureCheck2ActionPerformed(evt);
            }
        });

        scheduleLabel.setText("Schedule:");

        org.jdesktop.layout.GroupLayout otherInfoPanel2Layout = new org.jdesktop.layout.GroupLayout(otherInfoPanel2);
        otherInfoPanel2.setLayout(otherInfoPanel2Layout);
        otherInfoPanel2Layout.setHorizontalGroup(
            otherInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanel2Layout.createSequentialGroup()
                .add(125, 125, 125)
                .add(locationLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(locationCombo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(57, 57, 57)
                .add(creatorLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(creatorField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(notesLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(notesField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(47, 47, 47)
                .add(scheduleLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(futureField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(otherInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(otherInfoPanel2Layout.createSequentialGroup()
                        .add(gcCheck2)
                        .add(36, 36, 36)
                        .add(futureCheck2))
                    .add(walkoutCheck2))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        otherInfoPanel2Layout.setVerticalGroup(
            otherInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(otherInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(otherInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(creatorLabel2)
                        .add(creatorField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(notesLabel2)
                        .add(notesField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(scheduleLabel)
                        .add(futureField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(otherInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(locationLabel2)
                        .add(locationCombo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .add(otherInfoPanel2Layout.createSequentialGroup()
                .add(otherInfoPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(gcCheck2)
                    .add(futureCheck2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(walkoutCheck2))
        );

        addSessionbutton2.setForeground(new java.awt.Color(51, 102, 255));
        addSessionbutton2.setText("Add Session");
        addSessionbutton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addSessionbutton2MouseClicked(evt);
            }
        });

        clearButton2.setForeground(new java.awt.Color(153, 0, 0));
        clearButton2.setText("Clear");
        clearButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearButton2MouseClicked(evt);
            }
        });
        clearButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButton2ActionPerformed(evt);
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

        deleteSessionButton2.setText("Delete Session");

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
                        .add(deleteSessionButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        sessionsTablePanelLayout.setVerticalGroup(
            sessionsTablePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(sessionsTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 337, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteSessionButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        createAgendaPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Create New Agenda"));

        agendaCategoryLabel2.setText("Category:");

        agendaCategoryCombo2.setEditable(true);

        agendaDateLabel2.setText("Date:");

        dateLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        dateLabel2.setForeground(new java.awt.Color(102, 102, 102));
        dateLabel2.setText("(mm/dd/yyyy hh:mm a.a.)");

        cancelButton2.setForeground(new java.awt.Color(153, 0, 0));
        cancelButton2.setText("Cancel");

        submitbutton2.setForeground(new java.awt.Color(51, 102, 255));
        submitbutton2.setText("Submit");

        agendaTextArea2.setColumns(20);
        agendaTextArea2.setRows(5);
        agendaTextAreaScrollPanel.setViewportView(agendaTextArea2);

        org.jdesktop.layout.GroupLayout createAgendaPanel2Layout = new org.jdesktop.layout.GroupLayout(createAgendaPanel2);
        createAgendaPanel2.setLayout(createAgendaPanel2Layout);
        createAgendaPanel2Layout.setHorizontalGroup(
            createAgendaPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAgendaPanel2Layout.createSequentialGroup()
                .add(101, 101, 101)
                .add(agendaCategoryLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(agendaCategoryCombo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 123, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(agendaDateLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(dateLabel2)
                    .add(createAgendaPanel2Layout.createSequentialGroup()
                        .add(dateField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 167, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(agendaTextAreaScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 588, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, createAgendaPanel2Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(submitbutton2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(cancelButton2))
        );
        createAgendaPanel2Layout.setVerticalGroup(
            createAgendaPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAgendaPanel2Layout.createSequentialGroup()
                .add(dateLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(createAgendaPanel2Layout.createSequentialGroup()
                        .add(agendaTextAreaScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 4, Short.MAX_VALUE))
                    .add(createAgendaPanel2Layout.createSequentialGroup()
                        .add(createAgendaPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(agendaCategoryLabel2)
                            .add(agendaCategoryCombo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(agendaDateLabel2)
                            .add(dateField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(createAgendaPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(cancelButton2)
                            .add(submitbutton2))))
                .addContainerGap())
        );

        agendaPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Agendas"));

        deleteAgendaButton2.setText("Delete Agenda");

        agendaTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        agendaTableScrollPanel.setViewportView(agendaTable2);

        org.jdesktop.layout.GroupLayout agendaPanel2Layout = new org.jdesktop.layout.GroupLayout(agendaPanel2);
        agendaPanel2.setLayout(agendaPanel2Layout);
        agendaPanel2Layout.setHorizontalGroup(
            agendaPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(agendaPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(agendaTableScrollPanel)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, agendaPanel2Layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(deleteAgendaButton2)))
                .addContainerGap())
        );
        agendaPanel2Layout.setVerticalGroup(
            agendaPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanel2Layout.createSequentialGroup()
                .add(agendaTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 312, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteAgendaButton2)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout sessionsAndAgendaPanelLayout = new org.jdesktop.layout.GroupLayout(sessionsAndAgendaPanel);
        sessionsAndAgendaPanel.setLayout(sessionsAndAgendaPanelLayout);
        sessionsAndAgendaPanelLayout.setHorizontalGroup(
            sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, createAgendaPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                        .add(addSessionbutton2)
                        .add(18, 18, 18)
                        .add(clearButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(studentInfoPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, courseInfoPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, otherInfoPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(sessionsTablePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(agendaPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(1243, Short.MAX_VALUE))
        );
        sessionsAndAgendaPanelLayout.setVerticalGroup(
            sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(studentInfoPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(courseInfoPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(otherInfoPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(12, 12, 12)
                .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(addSessionbutton2)
                    .add(clearButton2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionsTablePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(createAgendaPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(agendaPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        adminScrollPanel.setViewportView(sessionsAndAgendaPanel);

        adminPanel.addTab("Sessions / Agenda", adminScrollPanel);

        currentSessionsPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Current Sessions"));

        searchsearchTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(searchsearchTable);

        deleteSessionButton1.setText("Delete Session");
        deleteSessionButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSessionButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout currentSessionsPanel1Layout = new org.jdesktop.layout.GroupLayout(currentSessionsPanel1);
        currentSessionsPanel1.setLayout(currentSessionsPanel1Layout);
        currentSessionsPanel1Layout.setHorizontalGroup(
            currentSessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, currentSessionsPanel1Layout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(deleteSessionButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(currentSessionsPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane6)
                .addContainerGap())
        );
        currentSessionsPanel1Layout.setVerticalGroup(
            currentSessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, currentSessionsPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 201, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(deleteSessionButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        addSessionbutton1.setForeground(new java.awt.Color(51, 102, 255));
        addSessionbutton1.setText("Search");
        addSessionbutton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addSessionbutton1MouseClicked(evt);
            }
        });
        addSessionbutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSessionbutton1ActionPerformed(evt);
            }
        });

        clearButton1.setForeground(new java.awt.Color(153, 0, 0));
        clearButton1.setText("Clear");
        clearButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearButton1MouseClicked(evt);
            }
        });
        clearButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButton1ActionPerformed(evt);
            }
        });

        otherInfoPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Other Information"));

        locationLabel1.setText("Location:");

        creatorLabel1.setText("Creator:");

        notesLabel1.setText("Notes:");

        gcCheck1.setText("GC");

        walkoutCheck1.setText("Walkout");

        sessionstartLabel1.setText("Session Start:");

        searchsessionstartField.setText("dd/mm/yyyy hh:mm aa");

        searchcreatorCombo.setEditable(true);

        sessionendLabel1.setText("Session End:");

        searchsessionendField.setText("dd/mm/yyyy hh:mm:ss aa");

        org.jdesktop.layout.GroupLayout otherInfoPanel1Layout = new org.jdesktop.layout.GroupLayout(otherInfoPanel1);
        otherInfoPanel1.setLayout(otherInfoPanel1Layout);
        otherInfoPanel1Layout.setHorizontalGroup(
            otherInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(locationLabel1)
                .add(18, 18, 18)
                .add(searchlocationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(creatorLabel1)
                .add(18, 18, 18)
                .add(searchcreatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(notesLabel1)
                .add(18, 18, 18)
                .add(searchnotesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(sessionstartLabel1)
                .add(18, 18, 18)
                .add(searchsessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(sessionendLabel1)
                .add(18, 18, 18)
                .add(searchsessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(gcCheck1)
                .add(18, 18, 18)
                .add(walkoutCheck1)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        otherInfoPanel1Layout.setVerticalGroup(
            otherInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(otherInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(locationLabel1)
                    .add(searchlocationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(creatorLabel1)
                    .add(searchcreatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(notesLabel1)
                    .add(searchnotesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionstartLabel1)
                    .add(searchsessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(gcCheck1)
                    .add(walkoutCheck1)
                    .add(sessionendLabel1)
                    .add(searchsessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        courseInfoPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        courseLabel1.setText("Course:");

        searchcourseCombo.setEditable(true);

        levelLabel1.setText("Course#:");

        searchlevelCombo.setEditable(true);

        teacherLabel1.setText("Teacher:");

        searchteacherCombo.setEditable(true);
        searchteacherCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchteacherComboActionPerformed(evt);
            }
        });

        categoryLabel1.setText("Category:");

        searchcategoryCombo.setEditable(true);

        searchparaprofessionalCombo.setEditable(true);

        ParaprofessionalLabel1.setText("Paraprofessional:");

        org.jdesktop.layout.GroupLayout courseInfoPanel1Layout = new org.jdesktop.layout.GroupLayout(courseInfoPanel1);
        courseInfoPanel1.setLayout(courseInfoPanel1Layout);
        courseInfoPanel1Layout.setHorizontalGroup(
            courseInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchcourseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(levelLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchlevelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(teacherLabel1)
                .add(18, 18, 18)
                .add(searchteacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 243, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(categoryLabel1)
                .add(18, 18, 18)
                .add(searchcategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(13, 13, 13)
                .add(ParaprofessionalLabel1)
                .add(18, 18, 18)
                .add(searchparaprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        courseInfoPanel1Layout.setVerticalGroup(
            courseInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanel1Layout.createSequentialGroup()
                .add(courseInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel1)
                    .add(searchcourseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel1)
                    .add(searchlevelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel1)
                    .add(searchteacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(categoryLabel1)
                    .add(searchcategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(searchparaprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ParaprofessionalLabel1))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        studentInfoPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        fnameLabel4.setText("First Name:");

        searchfnameCombo.setEditable(true);

        lnameLabel4.setText("Last Name:");

        searchlnameCombo.setEditable(true);

        emailLabel4.setText("Email:");

        searchemailCombo.setEditable(true);

        phoneLabel4.setText("Phone:");

        searchphoneCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout studentInfoPanel3Layout = new org.jdesktop.layout.GroupLayout(studentInfoPanel3);
        studentInfoPanel3.setLayout(studentInfoPanel3Layout);
        studentInfoPanel3Layout.setHorizontalGroup(
            studentInfoPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(fnameLabel4)
                .add(18, 18, 18)
                .add(searchfnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(lnameLabel4)
                .add(18, 18, 18)
                .add(searchlnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(emailLabel4)
                .add(18, 18, 18)
                .add(searchemailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(phoneLabel4)
                .add(18, 18, 18)
                .add(searchphoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 174, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        studentInfoPanel3Layout.setVerticalGroup(
            studentInfoPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanel3Layout.createSequentialGroup()
                .add(studentInfoPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(phoneLabel4)
                    .add(emailLabel4)
                    .add(searchlnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lnameLabel4)
                    .add(searchfnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(fnameLabel4)
                    .add(searchemailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(searchphoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6))
        );

        searchautocompleteCheck.setSelected(true);
        searchautocompleteCheck.setText("AutoComplete");
        searchautocompleteCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchautocompleteCheckActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout sessionsPanel1Layout = new org.jdesktop.layout.GroupLayout(sessionsPanel1);
        sessionsPanel1.setLayout(sessionsPanel1Layout);
        sessionsPanel1Layout.setHorizontalGroup(
            sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(currentSessionsPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsPanel1Layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(addSessionbutton1)
                        .add(2, 2, 2)
                        .add(clearButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(otherInfoPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(courseInfoPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(studentInfoPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .add(sessionsPanel1Layout.createSequentialGroup()
                .add(209, 209, 209)
                .add(searchautocompleteCheck)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sessionsPanel1Layout.setVerticalGroup(
            sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(searchautocompleteCheck)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(studentInfoPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(courseInfoPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(7, 7, 7)
                .add(otherInfoPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(10, 10, 10)
                .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(addSessionbutton1)
                    .add(clearButton1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(currentSessionsPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(290, Short.MAX_VALUE))
        );

        adminPanel.addTab("Search", sessionsPanel1);

        reportPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        downloadButton.setText("Download");

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(reportTable);

        jScrollPane2.setViewportView(reportList);

        thechartPanel.setLayout(new java.awt.GridBagLayout());

        org.jdesktop.layout.GroupLayout reportPanelLayout = new org.jdesktop.layout.GroupLayout(reportPanel);
        reportPanel.setLayout(reportPanelLayout);
        reportPanelLayout.setHorizontalGroup(
            reportPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(reportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(reportPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(reportPanelLayout.createSequentialGroup()
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 275, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(reportPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(reportPanelLayout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1103, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(reportPanelLayout.createSequentialGroup()
                                .add(372, 372, 372)
                                .add(downloadButton))))
                    .add(thechartPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1335, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        reportPanelLayout.setVerticalGroup(
            reportPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(reportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(thechartPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(reportPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(reportPanelLayout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 189, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(downloadButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 257, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        adminPanel.addTab("Report", reportPanel);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(adminPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1354, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(adminPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 851, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 144, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sessionsclearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sessionsclearButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sessionsclearButtonActionPerformed

    private void sessionsclearButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sessionsclearButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_sessionsclearButtonMouseClicked

    private void sessionsaddSessionbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sessionsaddSessionbuttonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_sessionsaddSessionbuttonMouseClicked

    private void teacherCombo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacherCombo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_teacherCombo2ActionPerformed

    private void creatorField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creatorField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_creatorField2ActionPerformed

    private void futureCheck2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_futureCheck2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_futureCheck2ActionPerformed

    private void addSessionbutton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSessionbutton2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addSessionbutton2MouseClicked

    private void clearButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButton2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButton2MouseClicked

    private void clearButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButton2ActionPerformed

    private void sessionsteacherComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sessionsteacherComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sessionsteacherComboActionPerformed

    private void sessionsdeleteSessionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sessionsdeleteSessionButtonActionPerformed
        int[] selectedRows = sessionsTable.getSelectedRows();
        
        ((SessionTableModel)sessionsTable.getModel()).deleteRows(selectedRows);
    }//GEN-LAST:event_sessionsdeleteSessionButtonActionPerformed

    private void sessionsaddSessionbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sessionsaddSessionbuttonActionPerformed
        //Paraprofessional t = new Paraprofessional(count, "TUTORFIRSTTEST", "TUTORLASTTEST", true);
        //Teacher teach = new Teacher(count, jComboBoxTeacher.getSelectedItem().toString(), "TestFirstName");
        //Subject sub = new Subject(count, jComboBoxCourse.getSelectedItem().toString(), "FullNameTest", new Category(count, "MABS"));
        
        ArrayList<Subject> subjects =(ArrayList<Subject>) HibernateTest.select("from Subject as s where s.abbrevName='"+sessionscourseCombo.getSelectedItem().toString()+"'");
        
        String[] tName = sessionsteacherCombo.getSelectedItem().toString().split(" ");
        String[] pName = sessionsparaprofessionalCombo.getSelectedItem().toString().split(" ");
        String[] cName = sessionscreatorCombo.getSelectedItem().toString().split(" ");
        String clientFName = fnameCombo.getSelectedItem().toString();
        String clientLName = lnameCombo.getSelectedItem().toString();
        boolean GC = gcCheck.isSelected();
        String notes = sessionsnotesField.getText().toString();
        String level = sessionslevelCombo.getSelectedItem().toString();
        int intLevel = Integer.parseInt(level);
        String location = sessionslocationCombo.getSelectedItem().toString();
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
    }//GEN-LAST:event_sessionsaddSessionbuttonActionPerformed

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

    private void deleteSessionButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSessionButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteSessionButton1ActionPerformed

    private void addSessionbutton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSessionbutton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addSessionbutton1MouseClicked

    private void addSessionbutton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSessionbutton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addSessionbutton1ActionPerformed

    private void clearButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButton1MouseClicked

    private void clearButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButton1ActionPerformed

    private void searchteacherComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchteacherComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchteacherComboActionPerformed

    private void searchautocompleteCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchautocompleteCheckActionPerformed
        studentInfoPanel3.setVisible(false);
        //courseInfoPanel1.setLocation(studentInfoPanel3.getLocation().x, studentInfoPanel3.getLocation().y);
        
        
    }//GEN-LAST:event_searchautocompleteCheckActionPerformed

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
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Admin().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ParaprofessionalLabel;
    private javax.swing.JLabel ParaprofessionalLabel1;
    private javax.swing.JButton addSessionbutton1;
    private javax.swing.JButton addSessionbutton2;
    private javax.swing.JTabbedPane adminPanel;
    private javax.swing.JScrollPane adminScrollPanel;
    private javax.swing.JComboBox agendaCategoryCombo;
    private javax.swing.JComboBox agendaCategoryCombo2;
    private javax.swing.JLabel agendaCategoryLabel;
    private javax.swing.JLabel agendaCategoryLabel2;
    private javax.swing.JLabel agendaDateLabel;
    private javax.swing.JLabel agendaDateLabel2;
    private javax.swing.JPanel agendaPanel;
    private javax.swing.JPanel agendaPanel2;
    private javax.swing.JTable agendaTable;
    private javax.swing.JTable agendaTable2;
    private javax.swing.JScrollPane agendaTableScrollPanel;
    private javax.swing.JTextArea agendaTextArea;
    private javax.swing.JTextArea agendaTextArea2;
    private javax.swing.JScrollPane agendaTextAreaScrollPanel;
    private javax.swing.JCheckBox autocompleteCheck;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton cancelButton2;
    private javax.swing.JComboBox categoryCombo2;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JLabel categoryLabel1;
    private javax.swing.JLabel categoryLabel2;
    private javax.swing.JButton clearButton1;
    private javax.swing.JButton clearButton2;
    private javax.swing.JComboBox courseCombo2;
    private javax.swing.JPanel courseInfoPanel;
    private javax.swing.JPanel courseInfoPanel1;
    private javax.swing.JPanel courseInfoPanel2;
    private javax.swing.JLabel courseLabel;
    private javax.swing.JLabel courseLabel1;
    private javax.swing.JLabel courseLabel2;
    private javax.swing.JPanel createAgendaPanel;
    private javax.swing.JPanel createAgendaPanel2;
    private javax.swing.JTextField creatorField2;
    private javax.swing.JLabel creatorLabel;
    private javax.swing.JLabel creatorLabel1;
    private javax.swing.JLabel creatorLabel2;
    private javax.swing.JPanel currentSessionsPanel;
    private javax.swing.JPanel currentSessionsPanel1;
    private javax.swing.JTextField dateField;
    private javax.swing.JTextField dateField2;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel dateLabel2;
    private javax.swing.JButton deleteAgendaButton;
    private javax.swing.JButton deleteAgendaButton2;
    private javax.swing.JButton deleteSessionButton1;
    private javax.swing.JButton deleteSessionButton2;
    private javax.swing.JButton downloadButton;
    private javax.swing.JComboBox emailCombo;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel emailLabel3;
    private javax.swing.JLabel emailLabel4;
    private javax.swing.JComboBox fnameCombo;
    private javax.swing.JLabel fnameLabel;
    private javax.swing.JLabel fnameLabel3;
    private javax.swing.JLabel fnameLabel4;
    private javax.swing.JCheckBox futureCheck2;
    private javax.swing.JTextField futureField2;
    private javax.swing.JCheckBox gcCheck;
    private javax.swing.JCheckBox gcCheck1;
    private javax.swing.JCheckBox gcCheck2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JComboBox levelCombo2;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JLabel levelLabel1;
    private javax.swing.JLabel levelLabel2;
    private javax.swing.JComboBox lnameCombo;
    private javax.swing.JLabel lnameLabel;
    private javax.swing.JLabel lnameLabel3;
    private javax.swing.JLabel lnameLabel4;
    private javax.swing.JComboBox locationCombo2;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JLabel locationLabel1;
    private javax.swing.JLabel locationLabel2;
    private javax.swing.JTextField notesField2;
    private javax.swing.JLabel notesLabel;
    private javax.swing.JLabel notesLabel1;
    private javax.swing.JLabel notesLabel2;
    private javax.swing.JPanel otherInfoPanel;
    private javax.swing.JPanel otherInfoPanel1;
    private javax.swing.JPanel otherInfoPanel2;
    private javax.swing.JComboBox phoneCombo;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JLabel phoneLabel3;
    private javax.swing.JLabel phoneLabel4;
    private javax.swing.JList reportList;
    private javax.swing.JPanel reportPanel;
    private javax.swing.JTable reportTable;
    private javax.swing.JLabel scheduleLabel;
    private javax.swing.JCheckBox searchautocompleteCheck;
    private javax.swing.JComboBox searchcategoryCombo;
    private javax.swing.JComboBox searchcourseCombo;
    private javax.swing.JComboBox searchcreatorCombo;
    private javax.swing.JComboBox searchemailCombo;
    private javax.swing.JComboBox searchfnameCombo;
    private javax.swing.JComboBox searchlevelCombo;
    private javax.swing.JComboBox searchlnameCombo;
    private javax.swing.JComboBox searchlocationCombo;
    private javax.swing.JTextField searchnotesField;
    private javax.swing.JComboBox searchparaprofessionalCombo;
    private javax.swing.JComboBox searchphoneCombo;
    private javax.swing.JTable searchsearchTable;
    private javax.swing.JTextField searchsessionendField;
    private javax.swing.JTextField searchsessionstartField;
    private javax.swing.JComboBox searchteacherCombo;
    private javax.swing.JLabel sessionendLabel;
    private javax.swing.JLabel sessionendLabel1;
    private javax.swing.JPanel sessionsAndAgendaPanel;
    private javax.swing.JPanel sessionsPanel;
    private javax.swing.JPanel sessionsPanel1;
    private javax.swing.JTable sessionsTable;
    private javax.swing.JPanel sessionsTablePanel;
    private javax.swing.JScrollPane sessionsTableScrollPanel;
    private javax.swing.JButton sessionsaddSessionbutton;
    private javax.swing.JComboBox sessionscategoryCombo;
    private javax.swing.JButton sessionsclearButton;
    private javax.swing.JComboBox sessionscourseCombo;
    private javax.swing.JComboBox sessionscreatorCombo;
    private javax.swing.JButton sessionsdeleteSessionButton;
    private javax.swing.JComboBox sessionsemailCombo;
    private javax.swing.JComboBox sessionsfnameCombo;
    private javax.swing.JComboBox sessionslevelCombo;
    private javax.swing.JComboBox sessionslnameCombo;
    private javax.swing.JComboBox sessionslocationCombo;
    private javax.swing.JTextField sessionsnotesField;
    private javax.swing.JComboBox sessionsparaprofessionalCombo;
    private javax.swing.JComboBox sessionsphoneCombo;
    private javax.swing.JTable sessionssessionTable;
    private javax.swing.JTextField sessionssessionendField;
    private javax.swing.JTextField sessionssessionstartField;
    private javax.swing.JLabel sessionstartLabel;
    private javax.swing.JLabel sessionstartLabel1;
    private javax.swing.JComboBox sessionsteacherCombo;
    private javax.swing.JPanel studentInfoPanel1;
    private javax.swing.JPanel studentInfoPanel2;
    private javax.swing.JPanel studentInfoPanel3;
    private javax.swing.JButton submitbutton;
    private javax.swing.JButton submitbutton2;
    private javax.swing.JComboBox teacherCombo2;
    private javax.swing.JLabel teacherLabel;
    private javax.swing.JLabel teacherLabel1;
    private javax.swing.JLabel teacherLabel2;
    private javax.swing.JPanel thechartPanel;
    private javax.swing.JCheckBox walkoutCheck;
    private javax.swing.JCheckBox walkoutCheck1;
    private javax.swing.JCheckBox walkoutCheck2;
    // End of variables declaration//GEN-END:variables
}
