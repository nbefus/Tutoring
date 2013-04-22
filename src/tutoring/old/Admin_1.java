/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.old;

import UIs.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.event.MouseWheelListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.LogManager;
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
import javax.swing.text.JTextComponent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import tutoring.entity.Category;
import tutoring.entity.Client;
import tutoring.entity.Course;
import tutoring.entity.Location;
import tutoring.entity.Paraprofessional;
import tutoring.entity.ParaprofessionalSession;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.editor.ComboBoxCellEditor;
import tutoring.helper.Data;
import tutoring.helper.HibernateTest;
import tutoring.renderer.MinuteCellRenderer;
import tutoring.helper.MinuteUpdate;
import tutoring.helper.RestrictionListModel;
import tutoring.helper.SessionTableModel;
import tutoring.editor.TimestampCellEditor;
import tutoring.renderer.TimestampCellRenderer;
import tutoring.helper.UltimateAutoComplete;
import tutoring.old.UltimateAutoCompleteClientOld;
import tutoring.helper.Validate;
import tutoring.ui.AdminView;

/**
 *
 * @author shohe_i
 */
public class Admin_1 extends javax.swing.JFrame {

    /**
     * Creates new form Admin
     */
    private UltimateAutoComplete uac; 
    private UltimateAutoCompleteClientOld uacc;
    DefaultListModel dlm = new DefaultListModel();
    RestrictionListModel restrictHelper;
    
    
    public enum ComboBoxesIndexes
    {
        /* search
         * boxes[0]=searchfnameCombo;
        boxes[1]=searchlnameCombo;
        boxes[2]=searchphoneCombo;
        boxes[3]=searchemailCombo;
        boxes[4]=searchcategorysessionCombo;
        boxes[5]=searchcourseCombo;
        boxes[6]=searchcreatorCombo;
        boxes[7]=searchlevelCombo;
        boxes[8]=searchlocationCombo;
        boxes[9]=searchparaprofessionalCombo;
        boxes[10]=searchteacherCombo;
         */
        CFNAME(0, "First Name", "fname", 'd'),
        CLNAME(1, "Last Name", "lname", 'd'),
        CPHONE(2,"Phone", "phone", 'd'),
        CEMAIL(3, "Email", "email", 'd'),
        CATEGORY(4, "Category", "name", 'a'),
        COURSE(5, "Course", "abbrevName", 's'),
        CREATOR(6, "Creator", "", 'e'),
        LEVEL(7, "Level", "level", 'c'),
        LOCATION(8, "Location", "location",'l'),
        PARAPROFESSIONAL(9, "Tutor","", 'p'),
        TEACHER(10, "Teacher", "concat_ws(' ', t.fname, t.lname)", 't'),
        PFNAME(11, "First Name","fname", 'p'),
        PLNAME(12, "Last Name","lname", 'p'),
        PROLE(13, "Role", "type", 'r'),
        PCATEGORY(14, "Category","name", 'a'),
        PTERMINATIONDATE(15, "Termination Date","terminationDate", 'p'),
        PHIREDATE(16, "Hire Date","", 'p'),
        PCLOCKEDIN(17, "In?","isClockedIn", 'p');
        
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
    public Admin_1() {
        initComponents();
       // setUpReportTab();
        setUpSearchTab();
        setUpGeneralReportTab();
    }
    

    public void setUpGeneralReportTab()
    {
        

        String[][] data = HibernateTest.getDataFromRegularQuery(
                "SELECT "+

                "abbrevName,"+

                "COUNT(paraprofessionalSessionID) as 'Total Sessions',"+

                "Sum(IF( TIMESTAMPDIFF("+
                "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "+
                "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS '30-min. Sessions', "+

                "Sum(IF( TIMESTAMPDIFF( "+
                "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "+
                "MINUTE , sessionStart, sessionEnd ) /30, 1))/count(paraprofessionalSessionID) as 'Avg. Session/Visit', "+

                "SUM(walkout) as 'Walkouts', "+

                "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart)) as 'Total Wait Time', "+

                "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart))/COUNT(paraprofessionalSessionID) as 'Avg. Wait Time' "+

                "FROM ParaprofessionalSession ps "+
                "join Course c on ps.courseID=c.courseID "+
                "join Subject s on c.subjectID=s.subjectID "+

                "group by abbrevName"
                );
        
        String[][] categoryData = HibernateTest.getDataFromRegularQuery(
                "select c.name, count(paraprofessionalSessionID) as '# of Sessions'"
                + " from ParaprofessionalSession ps"
                + " join Course course on course.courseID=ps.courseID"
                + " join Subject s on course.subjectID=s.subjectID"
                + " join Category c on s.categoryID=c.categoryID"
                + " group by c.name");
        
        String[][] otherValues = HibernateTest.getDataFromRegularQuery(
                "SELECT "+
                
                "SUM(walkout) as 'Walkouts', "+
                
                "COUNT(paraprofessionalID) as 'Total Students',"+

                "Sum(IF( TIMESTAMPDIFF("+
                "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "+
                "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS 'Total Sessions' "+

                "FROM ParaprofessionalSession ps");
        
        String[][] studentMinutes = HibernateTest.getDataFromRegularQuery(
                "SELECT "+
                
                "Sum(IF( TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) < 10"+
                " and TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) > 0, 1, 0))"+
                " AS '<10 Min. Sessions', "+
                
                 "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 10"+
                " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) < 25, 1, 0))"+
                " AS '10-24 Min. Sessions', "+
                
                "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 25"+
                " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 35, 1, 0))"+
                " AS '25-35 Min. Sessions', "+
                
                "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 35"+
                " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 60, 1, 0))"+
                " AS '36-60 Min. Sessions', "+
                "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 60"+
                ", 1, 0))"+
                " AS '>60 Min. Sessions' "+

                "FROM ParaprofessionalSession ps");
        
        displayCharts(data, categoryData, otherValues, studentMinutes);
        


        // String[] columns = new String[c.size()];
        // for(int i=0; i<c.size(); i++)
        //     columns[i]=(String)c.get(i);
         

    }
    
    private void displayCharts(String[][] generalData, String[][] categoryData, String[][] otherValues, String[][] studentMinutes)
    {
          String[] columns = {"Subject","Total Sessions", "30-min. Sessions","Avg. Session/Visit","Total Wait Time", "Avg. Wait Time"};

         DefaultTableModel dtm = new DefaultTableModel();
         dtm.setDataVector(generalData, columns);
         generalReportTable.setModel(dtm);

         String[] columns1 = {"Category","# of Sessions"};

         DefaultTableModel dtm1 = new DefaultTableModel();
         dtm1.setDataVector(categoryData, columns1);
         generalReportTable3.setModel(dtm1);
         
         String[] columns2 = {"Walkouts","Total Students","Total Sessions"};

         DefaultTableModel dtm2 = new DefaultTableModel();
         dtm2.setDataVector(otherValues, columns2);
         generalReportTable2.setModel(dtm2);
         
         String[] columns3 = {"<10 Min.","10-25 Min.","25-35 Min.", "36-60 Min.", ">60 Min."};

         DefaultTableModel dtm3 = new DefaultTableModel();
         dtm3.setDataVector(studentMinutes, columns3);
         generalReportTable4.setModel(dtm3);



         DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
         DefaultCategoryDataset barDataset1 = new DefaultCategoryDataset();
         DefaultCategoryDataset barDataset2 = new DefaultCategoryDataset();
         DefaultCategoryDataset barDataset3 = new DefaultCategoryDataset();
         
         DefaultPieDataset pieDataset = new DefaultPieDataset();
         DefaultPieDataset pieDataset1 = new DefaultPieDataset();
         DefaultPieDataset pieDataset2 = new DefaultPieDataset();
         
         String series = "Category";
        

         // String[] categories = new String[data.length];
         // for(int i=0; i<data.length; i++)
         //     categories[i] = data[i][1];

         for(int i=0; i<categoryData.length; i++)
         {
             barDataset1.addValue(Double.parseDouble(categoryData[i][1]), series, categoryData[i][0]);
             pieDataset1.setValue(categoryData[i][0], Double.parseDouble(categoryData[i][1]));
         }
         
         for(int i=0; i<columns2.length; i++)
         {
            barDataset2.addValue(Double.parseDouble(otherValues[0][i]), series, columns2[i]);
         }
         
         for(int i=0; i<columns3.length; i++)
         {
            barDataset3.addValue(Double.parseDouble(studentMinutes[0][i]), series, columns3[i]);
            pieDataset2.setValue(columns3[i], Double.parseDouble(studentMinutes[0][i]));
         }
         
        for(int i=0; i<generalData.length; i++)
        {
            System.out.println(Double.parseDouble(generalData[i][1])+" + "+generalData[i][0]);
            barDataset.addValue(Double.parseDouble(generalData[i][1]), series, generalData[i][0]);
            pieDataset.setValue(generalData[i][0], Double.parseDouble(generalData[i][1]));
            
            
        }

        final JFreeChart barChart = createChart(barDataset, "Total Student Sessions by Subject","# of Student Sessions","Subject",false, Color.green, Color.gray);
        final ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(generalChartPanelLong.getSize());
        System.out.println(barChartPanel.getPreferredSize().height + " "+barChartPanel.getPreferredSize().width);
        generalChartPanelLong.removeAll();
        generalChartPanelLong.add(barChartPanel);
        generalChartPanelLong.validate();
        
        
        final JFreeChart barChart1 = createChart(barDataset1, "Total Student Sessions by Category","# of Student Sessions","Category",false, Color.blue, Color.gray);
        final ChartPanel barChartPanel1 = new ChartPanel(barChart1);
        barChartPanel1.setPreferredSize(generalChartPanelLeft2.getSize());
        System.out.println(barChartPanel1.getPreferredSize().height + " "+barChartPanel1.getPreferredSize().width);
        generalChartPanelLeft2.removeAll();
        generalChartPanelLeft2.add(barChartPanel1);
        generalChartPanelLeft2.validate();
        
       final JFreeChart barChart2 = createChart(barDataset2, "Other Information","Total #","Statistic",false, Color.red, Color.gray);
        final ChartPanel barChartPanel2 = new ChartPanel(barChart2);
        barChartPanel2.setPreferredSize(generalChartPanelMid2.getSize());
        System.out.println(barChartPanel2.getPreferredSize().height + " "+barChartPanel2.getPreferredSize().width);
        generalChartPanelMid2.removeAll();
        generalChartPanelMid2.add(barChartPanel2);
        generalChartPanelMid2.validate();
        
        final JFreeChart barChart3 = createChart(barDataset3, "Session Length Overview","# of Sessions of Length","Length Period",false, Color.gray, Color.white);
        final ChartPanel barChartPanel3 = new ChartPanel(barChart3);
        barChartPanel3.setPreferredSize(generalChartPanelRight2.getSize());
        System.out.println(barChartPanel3.getPreferredSize().height + " "+barChartPanel3.getPreferredSize().width);
        generalChartPanelRight2.removeAll();
        generalChartPanelRight2.add(barChartPanel3);
        generalChartPanelRight2.validate();
        
        
        final JFreeChart pieChart = createChart(pieDataset, "Total Student Sessions by Subject");
        final ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(generalChartPanelLeft.getSize());
        System.out.println(pieChartPanel.getPreferredSize().height + " "+pieChartPanel.getPreferredSize().width);
        generalChartPanelLeft.removeAll();
        generalChartPanelLeft.add(pieChartPanel);
        generalChartPanelLeft.validate();
        
        final JFreeChart pieChart1 = createChart(pieDataset1, "Total Student Sessions by Category");
        final ChartPanel pieChartPanel1 = new ChartPanel(pieChart1);
        pieChartPanel1.setPreferredSize(generalChartPanelRight.getSize());
        System.out.println(pieChartPanel1.getPreferredSize().height + " "+pieChartPanel1.getPreferredSize().width);
        generalChartPanelRight.removeAll();
        generalChartPanelRight.add(pieChartPanel1);
        generalChartPanelRight.validate();
        
        final JFreeChart pieChart2 = createChart(pieDataset2, "Total Student Sessions by Length");
        final ChartPanel pieChartPanel2 = new ChartPanel(pieChart2);
        pieChartPanel2.setPreferredSize(generalChartPanelMid.getSize());
        System.out.println(pieChartPanel2.getPreferredSize().height + " "+pieChartPanel2.getPreferredSize().width);
        generalChartPanelMid.removeAll();
        generalChartPanelMid.add(pieChartPanel2);
        generalChartPanelMid.validate();
        
       // thechartPanel1.validate();
      //  reportPanel1.validate();
       // jScrollPane10.validate();
    }
    
    private JFreeChart createChart(PieDataset dataset, String title) {
        
        JFreeChart chart = ChartFactory.createPieChart3D(title,          // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        chart.setBorderVisible(false);
        plot.setBackgroundPaint(Admin_1.this.getBackground());
        plot.setStartAngle(290);
        plot.setOutlineVisible(false);
        plot.setDirection(Rotation.CLOCKWISE);
        chart.setBackgroundPaint(Admin_1.this.getBackground());
        plot.setForegroundAlpha(0.75f);
        
        LegendTitle lt = chart.getLegend();
        lt.setBackgroundPaint(Admin_1.this.getBackground());
        lt.setBorder(0, 0, 0, 0);
        //lt.setBackgroundPaint(null);
        return chart;
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

                          final JFreeChart chart = createChart(dataset,"title","x","y",false, Color.blue, Color.gray);
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
    
    private JFreeChart createChart(final CategoryDataset dataset, final String title, final String xText, final String yText, final boolean showLegend, Color start, Color end) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            title,         // chart title
            xText,               // domain axis label
            yText,                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            showLegend,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Admin_1.this.getBackground());

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Admin_1.this.getBackground());
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        plot.setOutlineVisible(false);
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, start, 
            0.0f, 0.0f, end
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
        //ComboBoxesIndexes.class.getEnumConstants();
        //BASED ON TABLE SEARCHING..
        
 
        searchList.setModel(dlm);
        
        searchclientPanel.setVisible(true);
        searchcoursePanel.setVisible(false);
        searchsessionPanel.setVisible(false);
        searchparaprofessionalPanel.setVisible(false);
        searchteacherPanel.setVisible(false);
        searchagendaPanel.setVisible(false);
        searchsubjectPanel.setVisible(false);
        
        Data d = new Data(false);
        
        JComboBox[] boxes = new  JComboBox[15];
        boxes[0]=searchfnameCombo;
        boxes[1]=searchlnameCombo;
        boxes[2]=searchphoneCombo;
        boxes[3]=searchemailCombo;
        boxes[4]=searchcategorysessionCombo;
        boxes[5]=searchcourseCombo;
        boxes[6]=searchcreatorCombo;
        boxes[7]=searchlevelCombo;
        boxes[8]=searchlocationCombo;
        boxes[9]=searchparaprofessionalCombo;
        boxes[10]=searchteacherCombo;
        boxes[11]=searchparaprofessionalfirstCombo;
        boxes[12]=searchparaprofessionallastCombo;
        boxes[13]=searchparaprofessionalroleCombo;
        //boxes[14]=searchparaprofessionalhireField;
        //boxes[15]=searchparaprofessionalterminationField;
        boxes[14]=searchparaprofessionalcategoryCombo;
        
       
        

        ArrayList<ArrayList<String>> cultimateList = new ArrayList<ArrayList<String>>();
        cultimateList.add(Data.getClientsfirst());
        cultimateList.add(Data.getClientslast());
        cultimateList.add(Data.getClientsphone());
        cultimateList.add(Data.getClientsemail());
        cultimateList.add(Data.getCategorieslist());
        cultimateList.add(Data.getSubjectslist());
        cultimateList.add(Data.getTutorslist());
        cultimateList.add(Data.getLevelslist());
        cultimateList.add(Data.getLocationslist());
        cultimateList.add(Data.getTutorslist());
        cultimateList.add(Data.getTeacherslist());
        cultimateList.add(Data.getTutorsfirstlist());
        cultimateList.add(Data.getTutorslastlist());
        cultimateList.add(Data.getRoleslist());
        cultimateList.add(Data.getMulticategorylist());
        uac = new UltimateAutoComplete(cultimateList, boxes);
        
        clientRadio.setSelected(true);
        
        //String[] restrictions = new String[4];
        
        /*restrictions[0]="First Name is any";
        restrictions[1]="Last Name is any";
        restrictions[2]="Phone is any";
        restrictions[3]="Email is any";*/

       // for(int i=0; i<restrictions.length; i++)
           // dlm.addElement(restrictions[i]);
        
        dlm.addElement("Search for all records");
        restrictHelper = new RestrictionListModel(dlm);//, restrictions);
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
       
       sessionsTable.setDefaultRenderer(Timestamp.class, new TimestampCellRenderer(false));
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

      uacc = new UltimateAutoCompleteClientOld(cultimateList, cboxes, Data.getClientFirst(), Data.getClientLast(), Data.getClientPhone(), Data.getClientEmail());
      
      
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
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
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
        searchsearchButton = new javax.swing.JButton();
        clearButton1 = new javax.swing.JButton();
        searchsessionPanel = new javax.swing.JPanel();
        locationLabel1 = new javax.swing.JLabel();
        searchlocationCombo = new javax.swing.JComboBox();
        creatorLabel1 = new javax.swing.JLabel();
        notesLabel1 = new javax.swing.JLabel();
        searchnotesField = new javax.swing.JTextField();
        sessionstartLabel1 = new javax.swing.JLabel();
        searchsessionstartField = new javax.swing.JTextField();
        searchcreatorCombo = new javax.swing.JComboBox();
        sessionendLabel1 = new javax.swing.JLabel();
        searchsessionendField = new javax.swing.JTextField();
        categoryLabel1 = new javax.swing.JLabel();
        searchcategorysessionCombo = new javax.swing.JComboBox();
        ParaprofessionalLabel1 = new javax.swing.JLabel();
        searchparaprofessionalCombo = new javax.swing.JComboBox();
        sessionstartLabel2 = new javax.swing.JLabel();
        searchsessionstartField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        searchgcCombo = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        searchwalkoutCombo = new javax.swing.JComboBox();
        searchcoursePanel = new javax.swing.JPanel();
        courseLabel1 = new javax.swing.JLabel();
        searchcourseCombo = new javax.swing.JComboBox();
        levelLabel1 = new javax.swing.JLabel();
        searchlevelCombo = new javax.swing.JComboBox();
        teacherLabel1 = new javax.swing.JLabel();
        searchteacherCombo = new javax.swing.JComboBox();
        searchclientPanel = new javax.swing.JPanel();
        fnameLabel4 = new javax.swing.JLabel();
        searchfnameCombo = new javax.swing.JComboBox();
        lnameLabel4 = new javax.swing.JLabel();
        searchlnameCombo = new javax.swing.JComboBox();
        emailLabel4 = new javax.swing.JLabel();
        searchemailCombo = new javax.swing.JComboBox();
        phoneLabel4 = new javax.swing.JLabel();
        searchphoneCombo = new javax.swing.JComboBox();
        jScrollPane7 = new javax.swing.JScrollPane();
        searchList = new javax.swing.JList();
        searchAddRestrictionsButton = new javax.swing.JButton();
        searchresetrestrictionButton = new javax.swing.JButton();
        searchclearrestrictionsButton = new javax.swing.JButton();
        searchparaprofessionalPanel = new javax.swing.JPanel();
        courseLabel3 = new javax.swing.JLabel();
        searchparaprofessionalfirstCombo = new javax.swing.JComboBox();
        levelLabel3 = new javax.swing.JLabel();
        searchparaprofessionallastCombo = new javax.swing.JComboBox();
        teacherLabel3 = new javax.swing.JLabel();
        categoryLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        searchparaprofessionalroleCombo = new javax.swing.JComboBox();
        searchparaprofessionalcategoryCombo = new javax.swing.JComboBox();
        levelLabel6 = new javax.swing.JLabel();
        searchparaprofessionalhireField = new javax.swing.JTextField();
        searchparaprofessionalterminationField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        searchparaprofessionalclockedinCombo = new javax.swing.JComboBox();
        searchteacherPanel = new javax.swing.JPanel();
        courseLabel4 = new javax.swing.JLabel();
        searchfnameteacherCombo = new javax.swing.JComboBox();
        levelLabel4 = new javax.swing.JLabel();
        searchlnameteacherCombo = new javax.swing.JComboBox();
        searchsubjectPanel = new javax.swing.JPanel();
        courseLabel5 = new javax.swing.JLabel();
        searchsubjectnameCombo = new javax.swing.JComboBox();
        levelLabel5 = new javax.swing.JLabel();
        searchsubjectcategoryCombo = new javax.swing.JComboBox();
        searchagendaPanel = new javax.swing.JPanel();
        courseLabel6 = new javax.swing.JLabel();
        searchagendanotesCombo = new javax.swing.JComboBox();
        levelLabel7 = new javax.swing.JLabel();
        searchagendacategoryCombo = new javax.swing.JComboBox();
        teacherLabel4 = new javax.swing.JLabel();
        searchsessionstartField4 = new javax.swing.JTextField();
        clientRadio = new javax.swing.JRadioButton();
        courseRadio = new javax.swing.JRadioButton();
        sessionsRadio = new javax.swing.JRadioButton();
        teacherRadio = new javax.swing.JRadioButton();
        paraprofessionalRadio = new javax.swing.JRadioButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        reportPanel = new javax.swing.JPanel();
        downloadButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        reportList = new javax.swing.JList();
        thechartPanel = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        reportPanel1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        generalReportTable2 = new javax.swing.JTable();
        generalReportBeginField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        generalReportEndField = new javax.swing.JTextField();
        downloadButton1 = new javax.swing.JButton();
        generalReportLoadButton = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        generalReportTable = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        generalReportTable3 = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        generalReportTable4 = new javax.swing.JTable();
        thechartPanel1 = new javax.swing.JPanel();
        generalChartPanelLeft = new javax.swing.JPanel();
        generalChartPanelMid = new javax.swing.JPanel();
        generalChartPanelRight = new javax.swing.JPanel();
        generalChartPanelLong = new javax.swing.JPanel();
        generalChartPanelMid2 = new javax.swing.JPanel();
        generalChartPanelLeft2 = new javax.swing.JPanel();
        generalChartPanelRight2 = new javax.swing.JPanel();

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
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 20, Short.MAX_VALUE)
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
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(784, Short.MAX_VALUE))
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
                .add(0, 705, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout agendaPanelLayout = new org.jdesktop.layout.GroupLayout(agendaPanel);
        agendaPanel.setLayout(agendaPanelLayout);
        agendaPanelLayout.setHorizontalGroup(
            agendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanelLayout.createSequentialGroup()
                .add(112, 112, 112)
                .add(createAgendaPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(375, Short.MAX_VALUE))
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
                .addContainerGap(528, Short.MAX_VALUE))
        );

        adminScrollPanel.setViewportView(sessionsAndAgendaPanel);

        adminPanel.addTab("Sessions / Agenda", adminScrollPanel);

        currentSessionsPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Search Results"));

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
                .add(jScrollPane6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteSessionButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        searchsearchButton.setForeground(new java.awt.Color(51, 102, 255));
        searchsearchButton.setText("Search");
        searchsearchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchsearchButtonMouseClicked(evt);
            }
        });
        searchsearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchsearchButtonActionPerformed(evt);
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

        searchsessionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Other Information"));

        locationLabel1.setText("Location:");

        creatorLabel1.setText("Creator:");

        notesLabel1.setText("Notes:");

        sessionstartLabel1.setText("Session Start:");

        searchsessionstartField.setText("dd/mm/yyyy hh:mm aa");

        searchcreatorCombo.setEditable(true);

        sessionendLabel1.setText("Session End:");

        searchsessionendField.setText("dd/mm/yyyy hh:mm:ss aa");

        categoryLabel1.setText("Category:");

        searchcategorysessionCombo.setEditable(true);

        ParaprofessionalLabel1.setText("Paraprofessional:");

        searchparaprofessionalCombo.setEditable(true);

        sessionstartLabel2.setText("Entered Date:");

        searchsessionstartField1.setText("dd/mm/yyyy hh:mm aa");

        jLabel2.setText("Grammar Check:");

        searchgcCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Either", "True", "False" }));

        jLabel4.setText("Walkout:");

        searchwalkoutCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Either", "True", "False" }));

        org.jdesktop.layout.GroupLayout searchsessionPanelLayout = new org.jdesktop.layout.GroupLayout(searchsessionPanel);
        searchsessionPanel.setLayout(searchsessionPanelLayout);
        searchsessionPanelLayout.setHorizontalGroup(
            searchsessionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchsessionPanelLayout.createSequentialGroup()
                .add(searchsessionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(searchsessionPanelLayout.createSequentialGroup()
                        .add(15, 15, 15)
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
                        .add(searchsessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(searchsessionPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(categoryLabel1)
                        .add(18, 18, 18)
                        .add(searchcategorysessionCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(ParaprofessionalLabel1)
                        .add(18, 18, 18)
                        .add(searchparaprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(sessionstartLabel2)
                        .add(18, 18, 18)
                        .add(searchsessionstartField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(searchgcCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(searchwalkoutCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchsessionPanelLayout.setVerticalGroup(
            searchsessionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchsessionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(searchsessionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(locationLabel1)
                    .add(searchlocationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(creatorLabel1)
                    .add(searchcreatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(notesLabel1)
                    .add(searchnotesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionstartLabel1)
                    .add(searchsessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionendLabel1)
                    .add(searchsessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(searchsessionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(categoryLabel1)
                    .add(searchcategorysessionCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ParaprofessionalLabel1)
                    .add(searchparaprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionstartLabel2)
                    .add(searchsessionstartField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2)
                    .add(searchgcCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4)
                    .add(searchwalkoutCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchcoursePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

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

        org.jdesktop.layout.GroupLayout searchcoursePanelLayout = new org.jdesktop.layout.GroupLayout(searchcoursePanel);
        searchcoursePanel.setLayout(searchcoursePanelLayout);
        searchcoursePanelLayout.setHorizontalGroup(
            searchcoursePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchcoursePanelLayout.createSequentialGroup()
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
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchcoursePanelLayout.setVerticalGroup(
            searchcoursePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchcoursePanelLayout.createSequentialGroup()
                .add(searchcoursePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel1)
                    .add(searchcourseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel1)
                    .add(searchlevelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel1)
                    .add(searchteacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchclientPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        fnameLabel4.setText("First Name:");

        searchfnameCombo.setEditable(true);

        lnameLabel4.setText("Last Name:");

        searchlnameCombo.setEditable(true);

        emailLabel4.setText("Email:");

        searchemailCombo.setEditable(true);

        phoneLabel4.setText("Phone:");

        searchphoneCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout searchclientPanelLayout = new org.jdesktop.layout.GroupLayout(searchclientPanel);
        searchclientPanel.setLayout(searchclientPanelLayout);
        searchclientPanelLayout.setHorizontalGroup(
            searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchclientPanelLayout.createSequentialGroup()
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
        searchclientPanelLayout.setVerticalGroup(
            searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchclientPanelLayout.createSequentialGroup()
                .add(searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
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

        jScrollPane7.setViewportView(searchList);

        searchAddRestrictionsButton.setText("Add Restrictions");
        searchAddRestrictionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchAddRestrictionsButtonActionPerformed(evt);
            }
        });

        searchresetrestrictionButton.setText("Reset Restriction");
        searchresetrestrictionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchresetrestrictionButtonActionPerformed(evt);
            }
        });

        searchclearrestrictionsButton.setText("Clear Restrictions");
        searchclearrestrictionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchclearrestrictionsButtonActionPerformed(evt);
            }
        });

        searchparaprofessionalPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        courseLabel3.setText("First Name:");

        searchparaprofessionalfirstCombo.setEditable(true);

        levelLabel3.setText("Last Name:");

        searchparaprofessionallastCombo.setEditable(true);

        teacherLabel3.setText("hireDate:");

        categoryLabel3.setText("terminationDate:");

        jLabel1.setText("Role:");

        searchparaprofessionalroleCombo.setEditable(true);

        searchparaprofessionalcategoryCombo.setEditable(true);

        levelLabel6.setText("Category:");

        searchparaprofessionalhireField.setText("dd/mm/yyyy hh:mm aa");

        searchparaprofessionalterminationField.setText("dd/mm/yyyy hh:mm aa");

        jLabel3.setText("Clocked In:");

        searchparaprofessionalclockedinCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Either", "True", "False" }));

        org.jdesktop.layout.GroupLayout searchparaprofessionalPanelLayout = new org.jdesktop.layout.GroupLayout(searchparaprofessionalPanel);
        searchparaprofessionalPanel.setLayout(searchparaprofessionalPanelLayout);
        searchparaprofessionalPanelLayout.setHorizontalGroup(
            searchparaprofessionalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchparaprofessionalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(searchparaprofessionalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(courseLabel3)
                    .add(levelLabel6))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchparaprofessionalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(searchparaprofessionalPanelLayout.createSequentialGroup()
                        .add(searchparaprofessionalfirstCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(levelLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(searchparaprofessionallastCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(teacherLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(searchparaprofessionalhireField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(categoryLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(searchparaprofessionalterminationField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(searchparaprofessionalPanelLayout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(searchparaprofessionalcategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 161, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchparaprofessionalclockedinCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchparaprofessionalroleCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 135, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchparaprofessionalPanelLayout.setVerticalGroup(
            searchparaprofessionalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchparaprofessionalPanelLayout.createSequentialGroup()
                .add(searchparaprofessionalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel3)
                    .add(searchparaprofessionalfirstCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel3)
                    .add(searchparaprofessionallastCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel3)
                    .add(categoryLabel3)
                    .add(jLabel1)
                    .add(searchparaprofessionalroleCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(searchparaprofessionalhireField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(searchparaprofessionalterminationField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(searchparaprofessionalclockedinCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(12, 12, 12)
                .add(searchparaprofessionalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(levelLabel6)
                    .add(searchparaprofessionalcategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchteacherPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        courseLabel4.setText("First Name:");

        searchfnameteacherCombo.setEditable(true);

        levelLabel4.setText("Last Name:");

        searchlnameteacherCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout searchteacherPanelLayout = new org.jdesktop.layout.GroupLayout(searchteacherPanel);
        searchteacherPanel.setLayout(searchteacherPanelLayout);
        searchteacherPanelLayout.setHorizontalGroup(
            searchteacherPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchteacherPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchfnameteacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(levelLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchlnameteacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchteacherPanelLayout.setVerticalGroup(
            searchteacherPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchteacherPanelLayout.createSequentialGroup()
                .add(searchteacherPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel4)
                    .add(searchfnameteacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel4)
                    .add(searchlnameteacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchsubjectPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        courseLabel5.setText("Name:");

        searchsubjectnameCombo.setEditable(true);

        levelLabel5.setText("Category:");

        searchsubjectcategoryCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout searchsubjectPanelLayout = new org.jdesktop.layout.GroupLayout(searchsubjectPanel);
        searchsubjectPanel.setLayout(searchsubjectPanelLayout);
        searchsubjectPanelLayout.setHorizontalGroup(
            searchsubjectPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchsubjectPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchsubjectnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(levelLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchsubjectcategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchsubjectPanelLayout.setVerticalGroup(
            searchsubjectPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchsubjectPanelLayout.createSequentialGroup()
                .add(searchsubjectPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel5)
                    .add(searchsubjectnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel5)
                    .add(searchsubjectcategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        searchagendaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        courseLabel6.setText("Notes:");

        searchagendanotesCombo.setEditable(true);

        levelLabel7.setText("Category:");

        searchagendacategoryCombo.setEditable(true);

        teacherLabel4.setText("Date:");

        searchsessionstartField4.setText("dd/mm/yyyy hh:mm aa");

        org.jdesktop.layout.GroupLayout searchagendaPanelLayout = new org.jdesktop.layout.GroupLayout(searchagendaPanel);
        searchagendaPanel.setLayout(searchagendaPanelLayout);
        searchagendaPanelLayout.setHorizontalGroup(
            searchagendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchagendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel6)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchagendanotesCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(levelLabel7)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchagendacategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(teacherLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchsessionstartField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchagendaPanelLayout.setVerticalGroup(
            searchagendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchagendaPanelLayout.createSequentialGroup()
                .add(searchagendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel6)
                    .add(searchagendanotesCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel7)
                    .add(searchagendacategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel4)
                    .add(searchsessionstartField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroup1.add(clientRadio);
        clientRadio.setText("Client");
        clientRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(courseRadio);
        courseRadio.setText("Course");
        courseRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(sessionsRadio);
        sessionsRadio.setText("Sessions");

        buttonGroup1.add(teacherRadio);
        teacherRadio.setText("Teacher");

        buttonGroup1.add(paraprofessionalRadio);
        paraprofessionalRadio.setText("Paraprofessional");
        paraprofessionalRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paraprofessionalRadioActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout sessionsPanel1Layout = new org.jdesktop.layout.GroupLayout(sessionsPanel1);
        sessionsPanel1.setLayout(sessionsPanel1Layout);
        sessionsPanel1Layout.setHorizontalGroup(
            sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(searchsessionPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(searchcoursePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(searchclientPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(searchparaprofessionalPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(searchteacherPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(searchsubjectPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(searchagendaPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(currentSessionsPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsPanel1Layout.createSequentialGroup()
                        .add(jScrollPane7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 331, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(6, 6, 6)
                        .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(sessionsPanel1Layout.createSequentialGroup()
                                .add(288, 288, 288)
                                .add(clearButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(searchAddRestrictionsButton)
                                .add(18, 18, Short.MAX_VALUE)
                                .add(searchsearchButton)
                                .add(528, 528, 528))
                            .add(sessionsPanel1Layout.createSequentialGroup()
                                .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(searchresetrestrictionButton)
                                    .add(searchclearrestrictionsButton))
                                .add(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .add(sessionsPanel1Layout.createSequentialGroup()
                .add(322, 322, 322)
                .add(clientRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(courseRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionsRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(teacherRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(paraprofessionalRadio)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sessionsPanel1Layout.setVerticalGroup(
            sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsPanel1Layout.createSequentialGroup()
                .add(18, 18, 18)
                .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(clientRadio)
                    .add(courseRadio)
                    .add(sessionsRadio)
                    .add(teacherRadio)
                    .add(paraprofessionalRadio))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchclientPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchcoursePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchsessionPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchparaprofessionalPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchteacherPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchsubjectPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchagendaPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jScrollPane7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 234, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionsPanel1Layout.createSequentialGroup()
                        .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(searchsearchButton)
                            .add(searchAddRestrictionsButton)
                            .add(clearButton1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(searchresetrestrictionButton)
                        .add(18, 18, 18)
                        .add(searchclearrestrictionsButton)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(currentSessionsPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(205, Short.MAX_VALUE))
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
                .addContainerGap(42, Short.MAX_VALUE))
        );
        reportPanelLayout.setVerticalGroup(
            reportPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(reportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(thechartPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1478, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(reportPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(reportPanelLayout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 189, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(downloadButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 257, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane9.setViewportView(reportPanel);

        adminPanel.addTab("tab7", jScrollPane9);

        reportPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        reportPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(1300, 258));

        generalReportTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(generalReportTable2);

        generalReportBeginField.setText("mm/dd/yyyy hh:mm aa");

        jLabel5.setText("Begin");

        jLabel6.setText("End");

        generalReportEndField.setText("mm/dd/yyyy hh:mm aa");

        downloadButton1.setText("Download");

        generalReportLoadButton.setText("Load");
        generalReportLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generalReportLoadButtonActionPerformed(evt);
            }
        });

        generalReportTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(generalReportTable);

        generalReportTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane12.setViewportView(generalReportTable3);

        generalReportTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane13.setViewportView(generalReportTable4);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(598, 598, 598)
                        .add(downloadButton1))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(183, 183, 183)
                        .add(jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 698, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 376, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jScrollPane12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 376, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(56, 56, 56))
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(generalReportEndField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(generalReportBeginField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(12, 12, 12)
                                .add(jLabel5))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(21, 21, 21)
                                .add(jLabel6)))
                        .add(generalReportLoadButton))
                    .addContainerGap(1151, Short.MAX_VALUE)))
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .add(185, 185, 185)
                    .add(jScrollPane11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 698, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(436, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(jScrollPane12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(downloadButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(17, 17, 17))
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jLabel5)
                    .add(1, 1, 1)
                    .add(generalReportBeginField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jLabel6)
                    .add(1, 1, 1)
                    .add(generalReportEndField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(18, 18, 18)
                    .add(generalReportLoadButton)
                    .addContainerGap(105, Short.MAX_VALUE)))
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .add(16, 16, 16)
                    .add(jScrollPane11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(159, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipady = -4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 23, 520, 62);
        reportPanel1.add(jPanel1, gridBagConstraints);

        thechartPanel1.setPreferredSize(new java.awt.Dimension(1300, 1200));

        generalChartPanelLeft.setLayout(new java.awt.GridBagLayout());

        generalChartPanelMid.setLayout(new java.awt.GridBagLayout());

        generalChartPanelRight.setLayout(new java.awt.GridBagLayout());

        generalChartPanelLong.setLayout(new java.awt.GridBagLayout());

        generalChartPanelMid2.setLayout(new java.awt.GridBagLayout());

        generalChartPanelLeft2.setLayout(new java.awt.GridBagLayout());

        generalChartPanelRight2.setLayout(new java.awt.GridBagLayout());

        org.jdesktop.layout.GroupLayout thechartPanel1Layout = new org.jdesktop.layout.GroupLayout(thechartPanel1);
        thechartPanel1.setLayout(thechartPanel1Layout);
        thechartPanel1Layout.setHorizontalGroup(
            thechartPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(thechartPanel1Layout.createSequentialGroup()
                .add(16, 16, 16)
                .add(generalChartPanelLeft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 430, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(generalChartPanelMid, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 431, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(generalChartPanelRight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 430, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(thechartPanel1Layout.createSequentialGroup()
                .add(10, 10, 10)
                .add(generalChartPanelLeft2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 436, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(generalChartPanelMid2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 431, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(generalChartPanelRight2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 431, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(thechartPanel1Layout.createSequentialGroup()
                .add(10, 10, 10)
                .add(generalChartPanelLong, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1310, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        thechartPanel1Layout.setVerticalGroup(
            thechartPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(thechartPanel1Layout.createSequentialGroup()
                .add(11, 11, 11)
                .add(thechartPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(generalChartPanelLeft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 249, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generalChartPanelMid, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 249, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generalChartPanelRight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 249, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6)
                .add(thechartPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(generalChartPanelLeft2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generalChartPanelMid2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generalChartPanelRight2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6)
                .add(generalChartPanelLong, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -428;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 23, 0, 62);
        reportPanel1.add(thechartPanel1, gridBagConstraints);

        jScrollPane10.setViewportView(reportPanel1);

        adminPanel.addTab("Report", jScrollPane10);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(adminPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1466, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(adminPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1392, Short.MAX_VALUE)
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

           uacc = new UltimateAutoCompleteClientOld(cultimateList, cboxes, Data.getClientFirst(), Data.getClientLast(), Data.getClientPhone(), Data.getClientEmail());
           
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

    private void searchsearchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchsearchButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_searchsearchButtonMouseClicked

    private void searchsearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchsearchButtonActionPerformed
        
        String[] columns = {"fname","lname","phone","email"};
        String table = "Client";
        char letter = 'c';
        String join;
        
        DefaultCellEditor dce =null;
        
        if(clientRadio.isSelected())
        {
            columns = new String[4];
            columns[0]="fname";
            columns[1]="lname";
            columns[2]="phone";
            columns[3]="email";
            table = "Client";
            letter = 'c';
            
            dce = new DefaultCellEditor(new JTextField())
            {

                @Override
                public Component getTableCellEditorComponent(JTable table, Object value,
                            boolean isSelected, int row, int column) 
                {
                   /* boxes[0]=searchfnameCombo;
                    boxes[1]=searchlnameCombo;
                    boxes[2]=searchphoneCombo;
                    boxes[3]=searchemailCombo;
                    boxes[4]=searchcategoryCombo;
                    boxes[5]=searchcourseCombo;
                    boxes[6]=searchcreatorCombo;
                    boxes[7]=searchlevelCombo;
                    boxes[8]=searchlocationCombo;
                    boxes[9]=searchparaprofessionalCombo;
                    boxes[10]=searchteacherCombo;*/

                    uac.setComboValue(table.getValueAt(row, 0).toString(), ComboBoxesIndexes.CFNAME.getBoxIndex());
                    uac.setComboValue(table.getValueAt(row, 1).toString(), ComboBoxesIndexes.CLNAME.getBoxIndex());
                    uac.setComboValue(table.getValueAt(row, 2).toString(), ComboBoxesIndexes.CPHONE.getBoxIndex());
                    uac.setComboValue(table.getValueAt(row, 3).toString(), ComboBoxesIndexes.CEMAIL.getBoxIndex());

                    return null;
                }
            };
        }
        else if(courseRadio.isSelected())
        {
            columns = new String[3];
            columns[0]="s.abbrevName";
            columns[1]="c.level";
            columns[2]="concat_ws(' ', t.fname, t.lname) as 'Teacher Name'";
            
            char lCourse = ComboBoxesIndexes.LEVEL.getLetter();
            char lTeacher = ComboBoxesIndexes.TEACHER.getLetter();
            char lSubject = ComboBoxesIndexes.COURSE.getLetter();
            
            table = "Course as "+lCourse;
            
            table += " join Teacher as "+lTeacher+" on "+lCourse+".teacherID="+lTeacher+".teacherID join Subject "+lSubject+" on "+lCourse+".subjectID="+lSubject+".subjectID";
            
            dce = new DefaultCellEditor(new JTextField())
            {

                @Override
                public Component getTableCellEditorComponent(JTable table, Object value,
                            boolean isSelected, int row, int column) 
                {

                    uac.setComboValue(table.getValueAt(row, 0).toString(), ComboBoxesIndexes.COURSE.getBoxIndex());
                    uac.setComboValue(table.getValueAt(row, 1).toString(), ComboBoxesIndexes.LEVEL.getBoxIndex());
                    uac.setComboValue(table.getValueAt(row, 2).toString(), ComboBoxesIndexes.TEACHER.getBoxIndex());

                    return null;
                }
            };
        }
        else if(paraprofessionalRadio.isSelected())
        {
            columns = new String[7];
            columns[0]="fname";
            columns[1]="lname";
            columns[2]="hireDate";
            columns[3]="terminationDate";
            columns[4]="isClockedIn";
            columns[5]="type";
            columns[6]="name";
            
            char lParaprofessional = ComboBoxesIndexes.PARAPROFESSIONAL.getLetter();
            char lCategory = ComboBoxesIndexes.PCATEGORY.getLetter();
            char lRole = ComboBoxesIndexes.PROLE.getLetter();
            
            table = "Paraprofessional as "+lParaprofessional;//+lCourse;
            
            table += " join Role as "+lRole+" on "+lParaprofessional+".roleID="+lRole+".roleID join ParaprofessionalCategory as pc on pc.paraprofessionalID="+lParaprofessional+".paraprofessionalID join Category as "+lCategory+" on "+lCategory+".categoryID=pc.categoryID";
            
            dce = new DefaultCellEditor(new JTextField())
            {

                @Override
                public Component getTableCellEditorComponent(JTable table, Object value,
                            boolean isSelected, int row, int column) 
                {

                    uac.setComboValue(table.getValueAt(row, 0).toString(), ComboBoxesIndexes.COURSE.getBoxIndex());
                    
                    uac.setComboValue(table.getValueAt(row, 1).toString(), ComboBoxesIndexes.LEVEL.getBoxIndex());
                    uac.setComboValue(table.getValueAt(row, 2).toString(), ComboBoxesIndexes.TEACHER.getBoxIndex());

                    return null;
                }
            };
        }
        
        String fullQuery=null;
        
        /*
        if(clientRadio.isSelected())
            fullQuery = restrictHelper.createClientQuery(columns, table, letter);
        else if(courseRadio.isSelected())
            fullQuery = restrictHelper.createCourseQuery(columns);*/
        
        fullQuery = restrictHelper.createQuery(columns, table);
        
        System.out.println("QUERY: "+fullQuery);
        
         HibernateTest.fillTableWithQuery(fullQuery, searchsearchTable, columns);
        
        for(int i=0; i<searchsearchTable.getColumnCount(); i++)
            searchsearchTable.getColumnModel().getColumn(i).setCellEditor(dce);
        
    }//GEN-LAST:event_searchsearchButtonActionPerformed

    private void clearButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButton1MouseClicked

    private void clearButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButton1ActionPerformed
        clearComboBoxes();
    }//GEN-LAST:event_clearButton1ActionPerformed

    private void searchteacherComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchteacherComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchteacherComboActionPerformed

    private void searchAddRestrictionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchAddRestrictionsButtonActionPerformed
        
        
        ArrayList<String> restrictions = new ArrayList<String>();
        ArrayList<String> displayNames = new ArrayList<String>();
        if(clientRadio.isSelected())
        {
            
            String fname = ((JTextComponent)searchfnameCombo.getEditor().getEditorComponent()).getText();
            String lname = ((JTextComponent)searchlnameCombo.getEditor().getEditorComponent()).getText();
            String phone = ((JTextComponent)searchphoneCombo.getEditor().getEditorComponent()).getText();
            String email = ((JTextComponent)searchemailCombo.getEditor().getEditorComponent()).getText();
            
            
            
            if(fname.length() > 0)
            {
                restrictions.add(fname);
                displayNames.add(ComboBoxesIndexes.CFNAME.getDisplayName());
            }
            if(lname.length() > 0)
            {
                restrictions.add(lname);
                displayNames.add(ComboBoxesIndexes.CLNAME.getDisplayName());
            }
            if(phone.length() > 0)
            {
                restrictions.add(phone);
                displayNames.add(ComboBoxesIndexes.CPHONE.getDisplayName());
            }
            if(email.length() > 0)
            {
                restrictions.add(email);
                displayNames.add(ComboBoxesIndexes.CEMAIL.getDisplayName());
            }
            
            restrictHelper.setListElement(restrictions, displayNames);
            /*
            restrictHelper.setListElement(fname, ComboBoxesIndexes.FNAME.indexOfCombo);
            restrictHelper.setListElement(lname, ComboBoxesIndexes.LNAME.indexOfCombo);
            restrictHelper.setListElement(phone, ComboBoxesIndexes.PHONE.indexOfCombo);
            restrictHelper.setListElement(email, ComboBoxesIndexes.EMAIL.indexOfCombo);*/
        }
        else if(courseRadio.isSelected())
        {
            
            String teacher = ((JTextComponent)searchteacherCombo.getEditor().getEditorComponent()).getText();
            String subject = ((JTextComponent)searchcourseCombo.getEditor().getEditorComponent()).getText();
            String level = ((JTextComponent)searchlevelCombo.getEditor().getEditorComponent()).getText();
            
            if(teacher.length() > 0)
            {
                restrictions.add(teacher);
                displayNames.add(ComboBoxesIndexes.TEACHER.getDisplayName());
            }
            if(subject.length() > 0)
            {
                restrictions.add(subject);
                displayNames.add(ComboBoxesIndexes.COURSE.getDisplayName());
            }
            if(level.length() > 0)
            {
                restrictions.add(level);
                displayNames.add(ComboBoxesIndexes.LEVEL.getDisplayName());
            }
            restrictHelper.setListElement(restrictions, displayNames);
            /*
            restrictHelper.setListElement(subject, 0);
            restrictHelper.setListElement(level, 1);
            restrictHelper.setListElement(teacher, 2);*/
        }
        else if(paraprofessionalRadio.isSelected())
        {
            
            String fname = ((JTextComponent)searchparaprofessionalfirstCombo.getEditor().getEditorComponent()).getText();
            String lname = ((JTextComponent)searchparaprofessionallastCombo.getEditor().getEditorComponent()).getText();
            String role = ((JTextComponent)searchparaprofessionalroleCombo.getEditor().getEditorComponent()).getText();
            String hireDate = searchparaprofessionalhireField.getText();
            String terminationDate = searchparaprofessionalterminationField.getText();
            String isClockedIn = searchparaprofessionalclockedinCombo.getSelectedItem().toString();
            String category = ((JTextComponent)searchparaprofessionalcategoryCombo.getEditor().getEditorComponent()).getText();

            if(fname.length() > 0)
            {
                restrictions.add(fname);
                displayNames.add(ComboBoxesIndexes.PFNAME.getDisplayName());
            }
            if(lname.length() > 0)
            {
                restrictions.add(lname);
                displayNames.add(ComboBoxesIndexes.PLNAME.getDisplayName());
            }
            if(role.length() > 0)
            {
                restrictions.add(role);
                displayNames.add(ComboBoxesIndexes.PROLE.getDisplayName());
            }
            if(hireDate.length() > 0)
            {
                restrictions.add(hireDate);
                displayNames.add(ComboBoxesIndexes.PHIREDATE.getDisplayName());
            }
            if(terminationDate.length() > 0)
            {
                restrictions.add(terminationDate);
                displayNames.add(ComboBoxesIndexes.PTERMINATIONDATE.getDisplayName());
            }
            if(isClockedIn.length() > 0)
            {
                restrictions.add(isClockedIn);
                displayNames.add(ComboBoxesIndexes.PCLOCKEDIN.getDisplayName());
            }
            if(category.length() > 0)
            {
                restrictions.add(category);
                displayNames.add(ComboBoxesIndexes.PCATEGORY.getDisplayName());
            }
            restrictHelper.setListElement(restrictions, displayNames);
            /*
            restrictHelper.setListElement(subject, 0);
            restrictHelper.setListElement(level, 1);
            restrictHelper.setListElement(teacher, 2);*/
        }
        
        
        
        System.out.println("DONE searchaddrestriciton");
        
       clearComboBoxes();

    }//GEN-LAST:event_searchAddRestrictionsButtonActionPerformed

    
    private void searchresetrestrictionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchresetrestrictionButtonActionPerformed

        //dlm.set(searchList.getSelectedIndex(), restrictHelper.getRestrictionAt(searchList.getSelectedIndex()));
        if(searchList.getSelectedIndex() > 1 && dlm.size()-1 == searchList.getSelectedIndex() )
            dlm.setElementAt(dlm.getElementAt(dlm.size()-2).toString().substring(0, dlm.getElementAt(dlm.size()-2).toString().length()-3), dlm.size()-2);
        if(searchList.getSelectedIndex() > 0)
            dlm.removeElement(searchList.getSelectedValue());
        
    }//GEN-LAST:event_searchresetrestrictionButtonActionPerformed

    private void searchclearrestrictionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchclearrestrictionsButtonActionPerformed
        /*
        String[] restrictions = restrictHelper.getRestrictions();
        
        
        for(int i=0; i<restrictions.length; i++)
            dlm.set(i, restrictions[i]);*/
        
        dlm.clear();
        dlm.addElement("Search for all records");
        
    }//GEN-LAST:event_searchclearrestrictionsButtonActionPerformed

    private void clientRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientRadioActionPerformed

        System.out.println("ACTION ON CLIENT RADIO");
        if(clientRadio.isSelected())
        {
            searchclientPanel.setVisible(true);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(false);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(false);

            dlm.clear();

            
            /*
            String[] restrictions = new String[4];
            restrictions[0]="First Name is any";
            restrictions[1]="Last Name is any";
            restrictions[2]="Phone is any";
            restrictions[3]="Email is any";

            for(int i=0; i<restrictions.length; i++)
                dlm.addElement(restrictions[i]);*/
            
            dlm.addElement("Search for all records");
           // restrictHelper.setRestrictions(restrictions);
            

        }
        
    }//GEN-LAST:event_clientRadioActionPerformed

    private void courseRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseRadioActionPerformed
        
        System.out.println("ACTION ON COURSE RADIO");
        if(courseRadio.isSelected())
        {
            searchclientPanel.setVisible(false);
            searchcoursePanel.setVisible(true);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(false);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(false);

            dlm.clear();
            /*
            String[] restrictions = new String[3];
            restrictions[0]="Course is any";
            restrictions[1]="Course# is any";
            restrictions[2]="Teacher is any";

            for(int i=0; i<restrictions.length; i++)
                dlm.addElement(restrictions[i]);*/
            
           // restrictHelper.setRestrictions(restrictions);
            
            dlm.addElement("Search for all records");
        }
    }//GEN-LAST:event_courseRadioActionPerformed

    private void generalReportLoadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generalReportLoadButtonActionPerformed
        try{
            String begin = generalReportBeginField.getText().trim();
            String end = generalReportEndField.getText().trim();

            boolean isDateBegin = Validate.validateTimestamp(begin);
            boolean isDateEnd = Validate.validateTimestamp(end);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
            Timestamp beginDate = null;
            Timestamp endDate = null;
            if(isDateBegin)
                beginDate = new Timestamp(sdf.parse(begin).getTime());
            if(isDateEnd)
                endDate = new Timestamp(sdf.parse(end).getTime());

            if(beginDate != null && endDate != null && beginDate.before(endDate))
            {
                String[][] data = HibernateTest.getDataFromRegularQuery(
                "SELECT "+

                "abbrevName,"+

                "COUNT(paraprofessionalSessionID) as 'Total Sessions',"+

                "Sum(IF( TIMESTAMPDIFF("+
                "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "+
                "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS '30-min. Sessions', "+

                "Sum(IF( TIMESTAMPDIFF( "+
                "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "+
                "MINUTE , sessionStart, sessionEnd ) /30, 1))/count(paraprofessionalSessionID) as 'Avg. Session/Visit', "+

                "SUM(walkout) as 'Walkouts', "+

                "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart)) as 'Total Wait Time', "+

                "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart))/COUNT(paraprofessionalSessionID) as 'Avg. Wait Time' "+

                "FROM ParaprofessionalSession ps "+
                "join Course c on ps.courseID=c.courseID "+
                "join Subject s on c.subjectID=s.subjectID "+

                "where "+
                "timeAndDateEntered "+
                "between "+
                "'"+beginDate.toString()+"' and '"+endDate.toString()+"'"+

                "group by abbrevName"
                );
                
                String[][] categoryData = HibernateTest.getDataFromRegularQuery(
                "select c.name, count(paraprofessionalSessionID) as '# of Sessions'"
                + " from ParaprofessionalSession ps"
                + " join Course course on course.courseID=ps.courseID"
                + " join Subject s on course.subjectID=s.subjectID"
                + " join Category c on s.categoryID=c.categoryID"
                       +"where "+
                "timeAndDateEntered "+
                "between "+
                "'"+beginDate.toString()+"' and '"+endDate.toString()+"'"+

                " group by c.name");
        
        String[][] otherValues = HibernateTest.getDataFromRegularQuery(
                "SELECT "+
                
                "SUM(walkout) as 'Walkouts', "+
                
                "COUNT(paraprofessionalID) as 'Total Students',"+

                "Sum(IF( TIMESTAMPDIFF("+
                "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "+
                "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS 'Total Sessions', "+

                "where "+
                "timeAndDateEntered "+
                "between "+
                "'"+beginDate.toString()+"' and '"+endDate.toString()+"'"+

                
                "FROM ParaprofessionalSession ps");
        
        
        String[][] studentMinutes = HibernateTest.getDataFromRegularQuery(
                "SELECT "+
                
                "Sum(IF( TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) < 10"+
                " and TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) > 0, 1, 0))"+
                " AS '<10 Min. Sessions', "+
                
                 "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 10"+
                " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) < 25, 1, 0))"+
                " AS '10-24 Min. Sessions', "+
                
                "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 25"+
                " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 35, 1, 0))"+
                " AS '25-35 Min. Sessions', "+

                "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 35"+
                " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 60, 1, 0))"+
                " AS '36-60 Min. Sessions', "+

                "where "+
                "timeAndDateEntered "+
                "between "+
                "'"+beginDate.toString()+"' and '"+endDate.toString()+"'"+

                
                "FROM ParaprofessionalSession ps");
        
        displayCharts(data, categoryData, otherValues, studentMinutes);

            }
                 
        }
        catch(Exception e)
        {
            System.out.println("EXCEPTION on load");
        }        
        
    }//GEN-LAST:event_generalReportLoadButtonActionPerformed

    private void paraprofessionalRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paraprofessionalRadioActionPerformed
        System.out.println("ACTION ON CLIENT RADIO");
        if(paraprofessionalRadio.isSelected())
        {
            searchclientPanel.setVisible(false);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(true);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(false);

            dlm.clear();

            
            /*
            String[] restrictions = new String[4];
            restrictions[0]="First Name is any";
            restrictions[1]="Last Name is any";
            restrictions[2]="Phone is any";
            restrictions[3]="Email is any";

            for(int i=0; i<restrictions.length; i++)
                dlm.addElement(restrictions[i]);*/
            
            dlm.addElement("Search for all records");
           // restrictHelper.setRestrictions(restrictions);
            

        }
    }//GEN-LAST:event_paraprofessionalRadioActionPerformed

    
    public void clearComboBoxes()
    {
         for(int i=0; i<uac.getBoxesLength(); i++)
            uac.setComboValue("", i);
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
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Admin_1().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ParaprofessionalLabel;
    private javax.swing.JLabel ParaprofessionalLabel1;
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton cancelButton2;
    private javax.swing.JComboBox categoryCombo2;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JLabel categoryLabel1;
    private javax.swing.JLabel categoryLabel2;
    private javax.swing.JLabel categoryLabel3;
    private javax.swing.JButton clearButton1;
    private javax.swing.JButton clearButton2;
    private javax.swing.JRadioButton clientRadio;
    private javax.swing.JComboBox courseCombo2;
    private javax.swing.JPanel courseInfoPanel;
    private javax.swing.JPanel courseInfoPanel2;
    private javax.swing.JLabel courseLabel;
    private javax.swing.JLabel courseLabel1;
    private javax.swing.JLabel courseLabel2;
    private javax.swing.JLabel courseLabel3;
    private javax.swing.JLabel courseLabel4;
    private javax.swing.JLabel courseLabel5;
    private javax.swing.JLabel courseLabel6;
    private javax.swing.JRadioButton courseRadio;
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
    private javax.swing.JButton downloadButton1;
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
    private javax.swing.JCheckBox gcCheck2;
    private javax.swing.JPanel generalChartPanelLeft;
    private javax.swing.JPanel generalChartPanelLeft2;
    private javax.swing.JPanel generalChartPanelLong;
    private javax.swing.JPanel generalChartPanelMid;
    private javax.swing.JPanel generalChartPanelMid2;
    private javax.swing.JPanel generalChartPanelRight;
    private javax.swing.JPanel generalChartPanelRight2;
    private javax.swing.JTextField generalReportBeginField;
    private javax.swing.JTextField generalReportEndField;
    private javax.swing.JButton generalReportLoadButton;
    private javax.swing.JTable generalReportTable;
    private javax.swing.JTable generalReportTable2;
    private javax.swing.JTable generalReportTable3;
    private javax.swing.JTable generalReportTable4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JComboBox levelCombo2;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JLabel levelLabel1;
    private javax.swing.JLabel levelLabel2;
    private javax.swing.JLabel levelLabel3;
    private javax.swing.JLabel levelLabel4;
    private javax.swing.JLabel levelLabel5;
    private javax.swing.JLabel levelLabel6;
    private javax.swing.JLabel levelLabel7;
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
    private javax.swing.JPanel otherInfoPanel2;
    private javax.swing.JRadioButton paraprofessionalRadio;
    private javax.swing.JComboBox phoneCombo;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JLabel phoneLabel3;
    private javax.swing.JLabel phoneLabel4;
    private javax.swing.JList reportList;
    private javax.swing.JPanel reportPanel;
    private javax.swing.JPanel reportPanel1;
    private javax.swing.JTable reportTable;
    private javax.swing.JLabel scheduleLabel;
    private javax.swing.JButton searchAddRestrictionsButton;
    private javax.swing.JList searchList;
    private javax.swing.JPanel searchagendaPanel;
    private javax.swing.JComboBox searchagendacategoryCombo;
    private javax.swing.JComboBox searchagendanotesCombo;
    private javax.swing.JComboBox searchcategorysessionCombo;
    private javax.swing.JButton searchclearrestrictionsButton;
    private javax.swing.JPanel searchclientPanel;
    private javax.swing.JComboBox searchcourseCombo;
    private javax.swing.JPanel searchcoursePanel;
    private javax.swing.JComboBox searchcreatorCombo;
    private javax.swing.JComboBox searchemailCombo;
    private javax.swing.JComboBox searchfnameCombo;
    private javax.swing.JComboBox searchfnameteacherCombo;
    private javax.swing.JComboBox searchgcCombo;
    private javax.swing.JComboBox searchlevelCombo;
    private javax.swing.JComboBox searchlnameCombo;
    private javax.swing.JComboBox searchlnameteacherCombo;
    private javax.swing.JComboBox searchlocationCombo;
    private javax.swing.JTextField searchnotesField;
    private javax.swing.JComboBox searchparaprofessionalCombo;
    private javax.swing.JPanel searchparaprofessionalPanel;
    private javax.swing.JComboBox searchparaprofessionalcategoryCombo;
    private javax.swing.JComboBox searchparaprofessionalclockedinCombo;
    private javax.swing.JComboBox searchparaprofessionalfirstCombo;
    private javax.swing.JTextField searchparaprofessionalhireField;
    private javax.swing.JComboBox searchparaprofessionallastCombo;
    private javax.swing.JComboBox searchparaprofessionalroleCombo;
    private javax.swing.JTextField searchparaprofessionalterminationField;
    private javax.swing.JComboBox searchphoneCombo;
    private javax.swing.JButton searchresetrestrictionButton;
    private javax.swing.JButton searchsearchButton;
    private javax.swing.JTable searchsearchTable;
    private javax.swing.JPanel searchsessionPanel;
    private javax.swing.JTextField searchsessionendField;
    private javax.swing.JTextField searchsessionstartField;
    private javax.swing.JTextField searchsessionstartField1;
    private javax.swing.JTextField searchsessionstartField4;
    private javax.swing.JPanel searchsubjectPanel;
    private javax.swing.JComboBox searchsubjectcategoryCombo;
    private javax.swing.JComboBox searchsubjectnameCombo;
    private javax.swing.JComboBox searchteacherCombo;
    private javax.swing.JPanel searchteacherPanel;
    private javax.swing.JComboBox searchwalkoutCombo;
    private javax.swing.JLabel sessionendLabel;
    private javax.swing.JLabel sessionendLabel1;
    private javax.swing.JPanel sessionsAndAgendaPanel;
    private javax.swing.JPanel sessionsPanel;
    private javax.swing.JPanel sessionsPanel1;
    private javax.swing.JRadioButton sessionsRadio;
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
    private javax.swing.JLabel sessionstartLabel2;
    private javax.swing.JComboBox sessionsteacherCombo;
    private javax.swing.JPanel studentInfoPanel1;
    private javax.swing.JPanel studentInfoPanel2;
    private javax.swing.JButton submitbutton;
    private javax.swing.JButton submitbutton2;
    private javax.swing.JComboBox teacherCombo2;
    private javax.swing.JLabel teacherLabel;
    private javax.swing.JLabel teacherLabel1;
    private javax.swing.JLabel teacherLabel2;
    private javax.swing.JLabel teacherLabel3;
    private javax.swing.JLabel teacherLabel4;
    private javax.swing.JRadioButton teacherRadio;
    private javax.swing.JPanel thechartPanel;
    private javax.swing.JPanel thechartPanel1;
    private javax.swing.JCheckBox walkoutCheck;
    private javax.swing.JCheckBox walkoutCheck2;
    // End of variables declaration//GEN-END:variables
}
