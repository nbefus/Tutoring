/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.ui;

import java.awt.Color;
import java.awt.Component;

import java.awt.GradientPaint;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

import javax.swing.JTable;
import javax.swing.JTextField;
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
import tutoring.helper.*;
import tutoring.entity.*;

/**
 *
 * @author shohe_i
 */
public class AdminView extends javax.swing.JFrame
{

    /**
     * Creates new form Admin
     */
    private UltimateAutoComplete uac;
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
        CPHONE(2, "Phone", "phone", 'd'),
        CEMAIL(3, "Email", "email", 'd'),
        CATEGORY(4, "Category", "name", 'a'),
        COURSE(5, "Course", "abbrevName", 's'),
        CREATOR(6, "Creator", "", 'e'),
        LEVEL(7, "Level", "level", 'c'),
        LOCATION(8, "Location", "location", 'l'),
        PARAPROFESSIONAL(9, "Tutor", "", 'p'),
        TEACHER(10, "Teacher", "concat_ws(' ', t.fname, t.lname)", 't'),
        PFNAME(11, "First Name", "fname", 'p'),
        PLNAME(12, "Last Name", "lname", 'p'),
        PROLE(13, "Role", "type", 'r'),
        PCATEGORY(14, "Category", "name", 'a'),
        PTERMINATIONDATE(15, "Termination Date", "terminationDate", 'p'),
        PHIREDATE(16, "Hire Date", "", 'p'),
        PCLOCKEDIN(17, "In?", "isClockedIn", 'p');
        private int indexOfCombo;
        private String displayName;
        private String databaseName;
        private char letter;

        private ComboBoxesIndexes(int i, String displayName, String databaseName, char letter)
        {
            indexOfCombo = i;
            this.displayName = displayName;
            this.databaseName = databaseName;
            this.letter = letter;
        }

        public char getLetter()
        {
            return letter;
        }

        public int getBoxIndex()
        {
            return indexOfCombo;
        }

        public String getDisplayName()
        {
            return displayName;
        }

        public String getDatabaseName()
        {
            return databaseName;
        }

        public String getDatabaseName(String DisplayName)
        {
            ComboBoxesIndexes[] components = ComboBoxesIndexes.class.getEnumConstants();
            for (int i = 0; i < components.length; i++)
            {
                if (components[i].getDisplayName().equalsIgnoreCase(DisplayName))
                {
                    return components[i].getDatabaseName();
                }
            }

            return "";
        }
    }

    public AdminView()
    {
        initComponents();
        // setUpReportTab();
        setUpSearchTab();
        setUpGeneralReportTab();
    }

    public void setUpGeneralReportTab()
    {


        DatabaseHelper.open();
        String[][] data = DatabaseHelper.getDataFromRegularQuery(
                "SELECT "
                + "abbrevName,"
                + "COUNT(paraprofessionalSessionID) as 'Total Sessions',"
                + "Sum(IF( TIMESTAMPDIFF("
                + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                + "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS '30-min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF( "
                + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                + "MINUTE , sessionStart, sessionEnd ) /30, 1))/count(paraprofessionalSessionID) as 'Avg. Session/Visit', "
                + "SUM(walkout) as 'Walkouts', "
                + "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart)) as 'Total Wait Time', "
                + "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart))/COUNT(paraprofessionalSessionID) as 'Avg. Wait Time' "
                + "FROM ParaprofessionalSession ps "
                + "join Course c on ps.courseID=c.courseID "
                + "join Subject s on c.subjectID=s.subjectID "
                + "group by abbrevName");

        String[][] categoryData = DatabaseHelper.getDataFromRegularQuery(
                "select c.name, count(paraprofessionalSessionID) as '# of Sessions'"
                + " from ParaprofessionalSession ps"
                + " join Course course on course.courseID=ps.courseID"
                + " join Subject s on course.subjectID=s.subjectID"
                + " join Category c on s.categoryID=c.categoryID"
                + " group by c.name");

        String[][] otherValues = DatabaseHelper.getDataFromRegularQuery(
                "SELECT "
                + "SUM(walkout) as 'Walkouts', "
                + "COUNT(paraprofessionalID) as 'Total Students',"
                + "Sum(IF( TIMESTAMPDIFF("
                + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                + "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS 'Total Sessions' "
                + "FROM ParaprofessionalSession ps");

        String[][] studentMinutes = DatabaseHelper.getDataFromRegularQuery(
                "SELECT "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) < 10"
                + " and TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) > 0, 1, 0))"
                + " AS '<10 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 10"
                + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) < 25, 1, 0))"
                + " AS '10-24 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 25"
                + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 35, 1, 0))"
                + " AS '25-35 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 35"
                + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 60, 1, 0))"
                + " AS '36-60 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 60"
                + ", 1, 0))"
                + " AS '>60 Min. Sessions' "
                + "FROM ParaprofessionalSession ps");

        DatabaseHelper.close();
        displayCharts(data, categoryData, otherValues, studentMinutes);
        


        // String[] columns = new String[c.size()];
        // for(int i=0; i<c.size(); i++)
        //     columns[i]=(String)c.get(i);


    }

    private void displayCharts(String[][] generalData, String[][] categoryData, String[][] otherValues, String[][] studentMinutes)
    {
        String[] columns =
        {
            "Subject", "Total Sessions", "30-min. Sessions", "Avg. Session/Visit", "Total Wait Time", "Avg. Wait Time"
        };

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setDataVector(generalData, columns);
        generalReportTable.setModel(dtm);

        String[] columns1 =
        {
            "Category", "# of Sessions"
        };

        DefaultTableModel dtm1 = new DefaultTableModel();
        dtm1.setDataVector(categoryData, columns1);
        generalReportTable3.setModel(dtm1);

        String[] columns2 =
        {
            "Walkouts", "Total Students", "Total Sessions"
        };

        DefaultTableModel dtm2 = new DefaultTableModel();
        dtm2.setDataVector(otherValues, columns2);
        generalReportTable2.setModel(dtm2);

        String[] columns3 =
        {
            "<10 Min.", "10-25 Min.", "25-35 Min.", "36-60 Min.", ">60 Min."
        };

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

        for (int i = 0; i < categoryData.length; i++)
        {
            barDataset1.addValue(Double.parseDouble(categoryData[i][1]), series, categoryData[i][0]);
            pieDataset1.setValue(categoryData[i][0], Double.parseDouble(categoryData[i][1]));
        }

        for (int i = 0; i < columns2.length; i++)
        {
            barDataset2.addValue(Double.parseDouble(otherValues[0][i]), series, columns2[i]);
        }

        for (int i = 0; i < columns3.length; i++)
        {
            barDataset3.addValue(Double.parseDouble(studentMinutes[0][i]), series, columns3[i]);
            pieDataset2.setValue(columns3[i], Double.parseDouble(studentMinutes[0][i]));
        }

        for (int i = 0; i < generalData.length; i++)
        {
            System.out.println(Double.parseDouble(generalData[i][1]) + " + " + generalData[i][0]);
            barDataset.addValue(Double.parseDouble(generalData[i][1]), series, generalData[i][0]);
            pieDataset.setValue(generalData[i][0], Double.parseDouble(generalData[i][1]));


        }

        final JFreeChart barChart = createChart(barDataset, "Total Student Sessions by Subject", "# of Student Sessions", "Subject", false, Color.green, Color.gray);
        final ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(generalChartPanelLong.getSize());
        System.out.println(barChartPanel.getPreferredSize().height + " " + barChartPanel.getPreferredSize().width);
        generalChartPanelLong.removeAll();
        generalChartPanelLong.add(barChartPanel);
        generalChartPanelLong.validate();


        final JFreeChart barChart1 = createChart(barDataset1, "Total Student Sessions by Category", "# of Student Sessions", "Category", false, Color.blue, Color.gray);
        final ChartPanel barChartPanel1 = new ChartPanel(barChart1);
        barChartPanel1.setPreferredSize(generalChartPanelLeft2.getSize());
        System.out.println(barChartPanel1.getPreferredSize().height + " " + barChartPanel1.getPreferredSize().width);
        generalChartPanelLeft2.removeAll();
        generalChartPanelLeft2.add(barChartPanel1);
        generalChartPanelLeft2.validate();

        final JFreeChart barChart2 = createChart(barDataset2, "Other Information", "Total #", "Statistic", false, Color.red, Color.gray);
        final ChartPanel barChartPanel2 = new ChartPanel(barChart2);
        barChartPanel2.setPreferredSize(generalChartPanelMid2.getSize());
        System.out.println(barChartPanel2.getPreferredSize().height + " " + barChartPanel2.getPreferredSize().width);
        generalChartPanelMid2.removeAll();
        generalChartPanelMid2.add(barChartPanel2);
        generalChartPanelMid2.validate();

        final JFreeChart barChart3 = createChart(barDataset3, "Session Length Overview", "# of Sessions of Length", "Length Period", false, Color.gray, Color.white);
        final ChartPanel barChartPanel3 = new ChartPanel(barChart3);
        barChartPanel3.setPreferredSize(generalChartPanelRight2.getSize());
        System.out.println(barChartPanel3.getPreferredSize().height + " " + barChartPanel3.getPreferredSize().width);
        generalChartPanelRight2.removeAll();
        generalChartPanelRight2.add(barChartPanel3);
        generalChartPanelRight2.validate();


        final JFreeChart pieChart = createChart(pieDataset, "Total Student Sessions by Subject");
        final ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(generalChartPanelLeft.getSize());
        System.out.println(pieChartPanel.getPreferredSize().height + " " + pieChartPanel.getPreferredSize().width);
        generalChartPanelLeft.removeAll();
        generalChartPanelLeft.add(pieChartPanel);
        generalChartPanelLeft.validate();

        final JFreeChart pieChart1 = createChart(pieDataset1, "Total Student Sessions by Category");
        final ChartPanel pieChartPanel1 = new ChartPanel(pieChart1);
        pieChartPanel1.setPreferredSize(generalChartPanelRight.getSize());
        System.out.println(pieChartPanel1.getPreferredSize().height + " " + pieChartPanel1.getPreferredSize().width);
        generalChartPanelRight.removeAll();
        generalChartPanelRight.add(pieChartPanel1);
        generalChartPanelRight.validate();

        final JFreeChart pieChart2 = createChart(pieDataset2, "Total Student Sessions by Length");
        final ChartPanel pieChartPanel2 = new ChartPanel(pieChart2);
        pieChartPanel2.setPreferredSize(generalChartPanelMid.getSize());
        System.out.println(pieChartPanel2.getPreferredSize().height + " " + pieChartPanel2.getPreferredSize().width);
        generalChartPanelMid.removeAll();
        generalChartPanelMid.add(pieChartPanel2);
        generalChartPanelMid.validate();

        // thechartPanel1.validate();
        //  reportPanel1.validate();
        // jScrollPane10.validate();
    }

    private JFreeChart createChart(PieDataset dataset, String title)
    {

        JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
                dataset, // data
                true, // include legend
                true,
                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        chart.setBorderVisible(false);
        plot.setBackgroundPaint(AdminView.this.getBackground());
        plot.setStartAngle(290);
        plot.setOutlineVisible(false);
        plot.setDirection(Rotation.CLOCKWISE);
        chart.setBackgroundPaint(AdminView.this.getBackground());
        plot.setForegroundAlpha(0.75f);

        LegendTitle lt = chart.getLegend();
        lt.setBackgroundPaint(AdminView.this.getBackground());
        lt.setBorder(0, 0, 0, 0);
        //lt.setBackgroundPaint(null);
        return chart;
    }

    private JFreeChart createChart(final CategoryDataset dataset, final String title, final String xText, final String yText, final boolean showLegend, Color start, Color end)
    {

        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
                title, // chart title
                xText, // domain axis label
                yText, // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                showLegend, // include legend
                true, // tooltips?
                false // URLs?
                );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(AdminView.this.getBackground());

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(AdminView.this.getBackground());
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
                0.0f, 0.0f, end);
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
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
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

        JComboBox[] boxes = new JComboBox[15];
        boxes[0] = searchfnameCombo;
        boxes[1] = searchlnameCombo;
        boxes[2] = searchphoneCombo;
        boxes[3] = searchemailCombo;
        boxes[4] = searchcategorysessionCombo;
        boxes[5] = searchcourseCombo;
        boxes[6] = searchcreatorCombo;
        boxes[7] = searchlevelCombo;
        boxes[8] = searchlocationCombo;
        boxes[9] = searchparaprofessionalCombo;
        boxes[10] = searchteacherCombo;
        boxes[11] = searchparaprofessionalfirstCombo;
        boxes[12] = searchparaprofessionallastCombo;
        boxes[13] = searchparaprofessionalroleCombo;
        //boxes[14]=searchparaprofessionalhireField;
        //boxes[15]=searchparaprofessionalterminationField;
        boxes[14] = searchparaprofessionalcategoryCombo;




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
        AdminScrollPanel = new javax.swing.JScrollPane();
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
        courseInfoPanel1 = new javax.swing.JPanel();
        courseLabel7 = new javax.swing.JLabel();
        courseCombo = new javax.swing.JComboBox();
        levelLabel8 = new javax.swing.JLabel();
        levelCombo = new javax.swing.JComboBox();
        teacherLabel5 = new javax.swing.JLabel();
        teacherCombo = new javax.swing.JComboBox();
        categoryLabel4 = new javax.swing.JLabel();
        categoryCombo = new javax.swing.JComboBox();
        paraprofessionalCombo = new javax.swing.JComboBox();
        ParaprofessionalLabel2 = new javax.swing.JLabel();
        otherInfoPanel1 = new javax.swing.JPanel();
        locationLabel3 = new javax.swing.JLabel();
        locationCombo = new javax.swing.JComboBox();
        creatorLabel3 = new javax.swing.JLabel();
        notesLabel3 = new javax.swing.JLabel();
        notesField = new javax.swing.JTextField();
        gcCheck1 = new javax.swing.JCheckBox();
        walkoutCheck1 = new javax.swing.JCheckBox();
        sessionstartLabel3 = new javax.swing.JLabel();
        sessionstartField = new javax.swing.JTextField();
        creatorCombo = new javax.swing.JComboBox();
        sessionendLabel2 = new javax.swing.JLabel();
        sessionendField = new javax.swing.JTextField();
        addSessionbutton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        sessionsTablePanel = new javax.swing.JPanel();
        sessionsTableScrollPanel = new javax.swing.JScrollPane();
        sessionsTable = new javax.swing.JTable();
        deleteSessionButton = new javax.swing.JButton();
        futureSessionsPanel = new javax.swing.JPanel();
        appointmentsTableScrollPanel = new javax.swing.JScrollPane();
        appointmentsTable = new javax.swing.JTable();
        deleteSessionButton3 = new javax.swing.JButton();
        createAgendaPanel1 = new javax.swing.JPanel();
        agendaCategoryLabel1 = new javax.swing.JLabel();
        agendaCategoryCombo1 = new javax.swing.JComboBox();
        agendaDateLabel1 = new javax.swing.JLabel();
        dateField1 = new javax.swing.JTextField();
        dateLabel1 = new javax.swing.JLabel();
        cancelButton1 = new javax.swing.JButton();
        submitbutton1 = new javax.swing.JButton();
        agendaTextAreaScrollPanel = new javax.swing.JScrollPane();
        agendaTextArea1 = new javax.swing.JTextArea();
        agendaPanel1 = new javax.swing.JPanel();
        deleteAgendaButton1 = new javax.swing.JButton();
        agendaTableScrollPanel = new javax.swing.JScrollPane();
        agendaTable1 = new javax.swing.JTable();
        autocompleteCheck = new javax.swing.JCheckBox();
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
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
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
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

        courseInfoPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        courseLabel7.setText("Course:");

        courseCombo.setEditable(true);

        levelLabel8.setText("Course#:");

        levelCombo.setEditable(true);

        teacherLabel5.setText("Teacher:");

        teacherCombo.setEditable(true);
        teacherCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teacherComboActionPerformed(evt);
            }
        });

        categoryLabel4.setText("Category:");

        categoryCombo.setEditable(true);

        paraprofessionalCombo.setEditable(true);

        ParaprofessionalLabel2.setText("Paraprofessional:");

        org.jdesktop.layout.GroupLayout courseInfoPanel1Layout = new org.jdesktop.layout.GroupLayout(courseInfoPanel1);
        courseInfoPanel1.setLayout(courseInfoPanel1Layout);
        courseInfoPanel1Layout.setHorizontalGroup(
            courseInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel7)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(courseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(levelLabel8)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(levelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(teacherLabel5)
                .add(18, 18, 18)
                .add(teacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 243, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(categoryLabel4)
                .add(18, 18, 18)
                .add(categoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(13, 13, 13)
                .add(ParaprofessionalLabel2)
                .add(18, 18, 18)
                .add(paraprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        courseInfoPanel1Layout.setVerticalGroup(
            courseInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanel1Layout.createSequentialGroup()
                .add(courseInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel7)
                    .add(courseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel8)
                    .add(levelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel5)
                    .add(teacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(categoryLabel4)
                    .add(categoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(paraprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ParaprofessionalLabel2))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        otherInfoPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Other Information"));

        locationLabel3.setText("Location:");

        creatorLabel3.setText("Creator:");

        notesLabel3.setText("Notes:");

        gcCheck1.setText("GC");

        walkoutCheck1.setText("Walkout");

        sessionstartLabel3.setText("Session Start:");

        sessionstartField.setText("dd/mm/yyyy hh:mm aa");

        creatorCombo.setEditable(true);

        sessionendLabel2.setText("Session End:");

        sessionendField.setText("dd/mm/yyyy hh:mm:ss aa");

        org.jdesktop.layout.GroupLayout otherInfoPanel1Layout = new org.jdesktop.layout.GroupLayout(otherInfoPanel1);
        otherInfoPanel1.setLayout(otherInfoPanel1Layout);
        otherInfoPanel1Layout.setHorizontalGroup(
            otherInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(locationLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(locationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(creatorLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(creatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(notesLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(notesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(sessionstartLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(sessionendLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(gcCheck1)
                .add(18, 18, 18)
                .add(walkoutCheck1)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        otherInfoPanel1Layout.setVerticalGroup(
            otherInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(otherInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(locationLabel3)
                    .add(locationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(creatorLabel3)
                    .add(creatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(notesLabel3)
                    .add(notesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionstartLabel3)
                    .add(sessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(gcCheck1)
                    .add(walkoutCheck1)
                    .add(sessionendLabel2)
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

        deleteSessionButton3.setText("Delete Session");
        deleteSessionButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSessionButton3ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout futureSessionsPanelLayout = new org.jdesktop.layout.GroupLayout(futureSessionsPanel);
        futureSessionsPanel.setLayout(futureSessionsPanelLayout);
        futureSessionsPanelLayout.setHorizontalGroup(
            futureSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(futureSessionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(futureSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(appointmentsTableScrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1288, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, futureSessionsPanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(deleteSessionButton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        futureSessionsPanelLayout.setVerticalGroup(
            futureSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, futureSessionsPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(appointmentsTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 179, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteSessionButton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        createAgendaPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Create New Agenda"));

        agendaCategoryLabel1.setText("Category:");

        agendaCategoryCombo1.setEditable(true);

        agendaDateLabel1.setText("Date:");

        dateLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        dateLabel1.setForeground(new java.awt.Color(102, 102, 102));
        dateLabel1.setText("(mm/dd/yyyy hh:mm a.a.)");

        cancelButton1.setForeground(new java.awt.Color(153, 0, 0));
        cancelButton1.setText("Cancel");

        submitbutton1.setForeground(new java.awt.Color(51, 102, 255));
        submitbutton1.setText("Submit");

        agendaTextArea1.setColumns(20);
        agendaTextArea1.setRows(5);
        agendaTextAreaScrollPanel.setViewportView(agendaTextArea1);

        org.jdesktop.layout.GroupLayout createAgendaPanel1Layout = new org.jdesktop.layout.GroupLayout(createAgendaPanel1);
        createAgendaPanel1.setLayout(createAgendaPanel1Layout);
        createAgendaPanel1Layout.setHorizontalGroup(
            createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAgendaPanel1Layout.createSequentialGroup()
                .add(createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(createAgendaPanel1Layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(cancelButton1)
                        .add(18, 18, 18)
                        .add(submitbutton1))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, createAgendaPanel1Layout.createSequentialGroup()
                        .add(101, 101, 101)
                        .add(agendaCategoryLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(agendaCategoryCombo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 123, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(agendaDateLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(dateLabel1)
                            .add(createAgendaPanel1Layout.createSequentialGroup()
                                .add(dateField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 167, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(agendaTextAreaScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 588, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        createAgendaPanel1Layout.setVerticalGroup(
            createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAgendaPanel1Layout.createSequentialGroup()
                .add(dateLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(agendaTextAreaScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(agendaCategoryLabel1)
                        .add(agendaCategoryCombo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(agendaDateLabel1)
                        .add(dateField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, Short.MAX_VALUE)
                .add(createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(submitbutton1)
                    .add(cancelButton1))
                .addContainerGap())
        );

        agendaPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Agendas"));

        deleteAgendaButton1.setText("Delete Agenda");

        agendaTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        agendaTableScrollPanel.setViewportView(agendaTable1);

        org.jdesktop.layout.GroupLayout agendaPanel1Layout = new org.jdesktop.layout.GroupLayout(agendaPanel1);
        agendaPanel1.setLayout(agendaPanel1Layout);
        agendaPanel1Layout.setHorizontalGroup(
            agendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(agendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(agendaTableScrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1294, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, agendaPanel1Layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(deleteAgendaButton1)))
                .addContainerGap())
        );
        agendaPanel1Layout.setVerticalGroup(
            agendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanel1Layout.createSequentialGroup()
                .add(agendaTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 204, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteAgendaButton1)
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
                        .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                                    .add(clearButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                    .add(addSessionbutton)
                                    .add(68, 68, 68))
                                .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                                    .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                        .add(org.jdesktop.layout.GroupLayout.LEADING, otherInfoPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(org.jdesktop.layout.GroupLayout.LEADING, courseInfoPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(studentInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .add(16, 16, 16)))
                            .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, futureSessionsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, sessionsTablePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                        .add(126, 126, 126)
                        .add(createAgendaPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(agendaPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                        .add(322, 322, 322)
                        .add(autocompleteCheck)))
                .addContainerGap(165, Short.MAX_VALUE))
        );
        sessionsAndAgendaPanelLayout.setVerticalGroup(
            sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                .add(autocompleteCheck)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(studentInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(courseInfoPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(otherInfoPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(12, 12, 12)
                .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(addSessionbutton)
                    .add(clearButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionsTablePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 298, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(futureSessionsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(agendaPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        AdminScrollPanel.setViewportView(sessionsAndAgendaPanel);

        adminPanel.addTab("Sessions / Agenda", AdminScrollPanel);

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

        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Save/Edit");

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
                                .add(18, 72, Short.MAX_VALUE)
                                .add(searchsearchButton)
                                .add(18, 18, 18)
                                .add(jButton1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jButton2)
                                .add(354, 354, 354))
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
                            .add(clearButton1)
                            .add(jButton1)
                            .add(jButton2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(searchresetrestrictionButton)
                        .add(18, 18, 18)
                        .add(searchclearrestrictionsButton)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(currentSessionsPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(302, Short.MAX_VALUE))
        );

        adminPanel.addTab("Search", sessionsPanel1);

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
                    .addContainerGap(1193, Short.MAX_VALUE)))
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .add(185, 185, 185)
                    .add(jScrollPane11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 698, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(436, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addContainerGap(126, Short.MAX_VALUE)))
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
            .add(adminPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(adminPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1392, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generalReportLoadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generalReportLoadButtonActionPerformed
        try
        {
            String begin = generalReportBeginField.getText().trim();
            String end = generalReportEndField.getText().trim();

            boolean isDateBegin = Validate.validateTimestamp(begin);
            boolean isDateEnd = Validate.validateTimestamp(end);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
            Timestamp beginDate = null;
            Timestamp endDate = null;
            if (isDateBegin)
            {
                beginDate = new Timestamp(sdf.parse(begin).getTime());
            }
            if (isDateEnd)
            {
                endDate = new Timestamp(sdf.parse(end).getTime());
            }

            if (beginDate != null && endDate != null && beginDate.before(endDate))
            {
                DatabaseHelper.open();
                String[][] data = DatabaseHelper.getDataFromRegularQuery(
                        "SELECT "
                        + "abbrevName,"
                        + "COUNT(paraprofessionalSessionID) as 'Total Sessions',"
                        + "Sum(IF( TIMESTAMPDIFF("
                        + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                        + "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS '30-min. Sessions', "
                        + "Sum(IF( TIMESTAMPDIFF( "
                        + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                        + "MINUTE , sessionStart, sessionEnd ) /30, 1))/count(paraprofessionalSessionID) as 'Avg. Session/Visit', "
                        + "SUM(walkout) as 'Walkouts', "
                        + "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart)) as 'Total Wait Time', "
                        + "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart))/COUNT(paraprofessionalSessionID) as 'Avg. Wait Time' "
                        + "FROM ParaprofessionalSession ps "
                        + "join Course c on ps.courseID=c.courseID "
                        + "join Subject s on c.subjectID=s.subjectID "
                        + "where "
                        + "timeAndDateEntered "
                        + "between "
                        + "'" + beginDate.toString() + "' and '" + endDate.toString() + "'"
                        + "group by abbrevName");

                String[][] categoryData = DatabaseHelper.getDataFromRegularQuery(
                        "select c.name, count(paraprofessionalSessionID) as '# of Sessions'"
                        + " from ParaprofessionalSession ps"
                        + " join Course course on course.courseID=ps.courseID"
                        + " join Subject s on course.subjectID=s.subjectID"
                        + " join Category c on s.categoryID=c.categoryID "
                        + "where "
                        + "timeAndDateEntered "
                        + "between "
                        + "'" + beginDate.toString() + "' and '" + endDate.toString() + "'"
                        + " group by c.name");

                String[][] otherValues = DatabaseHelper.getDataFromRegularQuery(
                        "SELECT "
                        + "SUM(walkout) as 'Walkouts', "
                        + "COUNT(paraprofessionalID) as 'Total Students', "
                        + "Sum(IF( TIMESTAMPDIFF("
                        + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                        + "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS 'Total Sessions' "+ "FROM ParaprofessionalSession "
                        + "where "
                        + "timeAndDateEntered "
                        + "between "
                        + "'" + beginDate.toString() + "' and '" + endDate.toString() + "'"
                        );

                String[][] studentMinutes = DatabaseHelper.getDataFromRegularQuery(
                        "SELECT "
                        + "Sum(IF( TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) < 10"
                        + " and TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) > 0, 1, 0))"
                        + " AS '<10 Min. Sessions', "
                        + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 10"
                        + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) < 25, 1, 0))"
                        + " AS '10-24 Min. Sessions', "
                        + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 25"
                        + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 35, 1, 0))"
                        + " AS '25-35 Min. Sessions', "
                        + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 35"
                        + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 60, 1, 0))"
                        + " AS '36-60 Min. Sessions' "+ "FROM ParaprofessionalSession ps "
                        + "where "
                        + "timeAndDateEntered "
                        + "between "
                        + "'" + beginDate.toString() + "' and '" + endDate.toString() + "'"
                        );

                DatabaseHelper.close();
                displayCharts(data, categoryData, otherValues, studentMinutes);

            }

        } catch (Exception e)
        {
            System.out.println("EXCEPTION on load");
        }

    }//GEN-LAST:event_generalReportLoadButtonActionPerformed

    private void paraprofessionalRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paraprofessionalRadioActionPerformed
        System.out.println("ACTION ON CLIENT RADIO");
        if (paraprofessionalRadio.isSelected())
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

    private void courseRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseRadioActionPerformed

        System.out.println("ACTION ON COURSE RADIO");
        if (courseRadio.isSelected())
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

    private void clientRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientRadioActionPerformed

        System.out.println("ACTION ON CLIENT RADIO");
        if (clientRadio.isSelected())
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

    private void searchclearrestrictionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchclearrestrictionsButtonActionPerformed
        /*
         String[] restrictions = restrictHelper.getRestrictions();

         for(int i=0; i<restrictions.length; i++)
         dlm.set(i, restrictions[i]);*/

        dlm.clear();
        dlm.addElement("Search for all records");

    }//GEN-LAST:event_searchclearrestrictionsButtonActionPerformed

    private void searchresetrestrictionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchresetrestrictionButtonActionPerformed

        //dlm.set(searchList.getSelectedIndex(), restrictHelper.getRestrictionAt(searchList.getSelectedIndex()));
        if (searchList.getSelectedIndex() > 1 && dlm.size() - 1 == searchList.getSelectedIndex())
        {
            dlm.setElementAt(dlm.getElementAt(dlm.size() - 2).toString().substring(0, dlm.getElementAt(dlm.size() - 2).toString().length() - 3), dlm.size() - 2);
        }
        if (searchList.getSelectedIndex() > 0)
        {
            dlm.removeElement(searchList.getSelectedValue());
        }

    }//GEN-LAST:event_searchresetrestrictionButtonActionPerformed

    private void searchAddRestrictionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchAddRestrictionsButtonActionPerformed

        ArrayList<String> restrictions = new ArrayList<String>();
        ArrayList<String> displayNames = new ArrayList<String>();
        if (clientRadio.isSelected())
        {

            String fname = ((JTextComponent) searchfnameCombo.getEditor().getEditorComponent()).getText();
            String lname = ((JTextComponent) searchlnameCombo.getEditor().getEditorComponent()).getText();
            String phone = ((JTextComponent) searchphoneCombo.getEditor().getEditorComponent()).getText();
            String email = ((JTextComponent) searchemailCombo.getEditor().getEditorComponent()).getText();

            if (fname.length() > 0)
            {
                restrictions.add(fname);
                displayNames.add(ComboBoxesIndexes.CFNAME.getDisplayName());
            }
            if (lname.length() > 0)
            {
                restrictions.add(lname);
                displayNames.add(ComboBoxesIndexes.CLNAME.getDisplayName());
            }
            if (phone.length() > 0)
            {
                restrictions.add(phone);
                displayNames.add(ComboBoxesIndexes.CPHONE.getDisplayName());
            }
            if (email.length() > 0)
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
        } else if (courseRadio.isSelected())
        {

            String teacher = ((JTextComponent) searchteacherCombo.getEditor().getEditorComponent()).getText();
            String subject = ((JTextComponent) searchcourseCombo.getEditor().getEditorComponent()).getText();
            String level = ((JTextComponent) searchlevelCombo.getEditor().getEditorComponent()).getText();

            if (teacher.length() > 0)
            {
                restrictions.add(teacher);
                displayNames.add(ComboBoxesIndexes.TEACHER.getDisplayName());
            }
            if (subject.length() > 0)
            {
                restrictions.add(subject);
                displayNames.add(ComboBoxesIndexes.COURSE.getDisplayName());
            }
            if (level.length() > 0)
            {
                restrictions.add(level);
                displayNames.add(ComboBoxesIndexes.LEVEL.getDisplayName());
            }
            restrictHelper.setListElement(restrictions, displayNames);
            /*
             restrictHelper.setListElement(subject, 0);
             restrictHelper.setListElement(level, 1);
             restrictHelper.setListElement(teacher, 2);*/
        } else if (paraprofessionalRadio.isSelected())
        {

            String fname = ((JTextComponent) searchparaprofessionalfirstCombo.getEditor().getEditorComponent()).getText();
            String lname = ((JTextComponent) searchparaprofessionallastCombo.getEditor().getEditorComponent()).getText();
            String role = ((JTextComponent) searchparaprofessionalroleCombo.getEditor().getEditorComponent()).getText();
            String hireDate = searchparaprofessionalhireField.getText();
            String terminationDate = searchparaprofessionalterminationField.getText();
            String isClockedIn = searchparaprofessionalclockedinCombo.getSelectedItem().toString();
            String category = ((JTextComponent) searchparaprofessionalcategoryCombo.getEditor().getEditorComponent()).getText();

            if (fname.length() > 0)
            {
                restrictions.add(fname);
                displayNames.add(ComboBoxesIndexes.PFNAME.getDisplayName());
            }
            if (lname.length() > 0)
            {
                restrictions.add(lname);
                displayNames.add(ComboBoxesIndexes.PLNAME.getDisplayName());
            }
            if (role.length() > 0)
            {
                restrictions.add(role);
                displayNames.add(ComboBoxesIndexes.PROLE.getDisplayName());
            }
            if (hireDate.length() > 0)
            {
                restrictions.add(hireDate);
                displayNames.add(ComboBoxesIndexes.PHIREDATE.getDisplayName());
            }
            if (terminationDate.length() > 0)
            {
                restrictions.add(terminationDate);
                displayNames.add(ComboBoxesIndexes.PTERMINATIONDATE.getDisplayName());
            }
            if (isClockedIn.length() > 0)
            {
                restrictions.add(isClockedIn);
                displayNames.add(ComboBoxesIndexes.PCLOCKEDIN.getDisplayName());
            }
            if (category.length() > 0)
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

    private void searchteacherComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchteacherComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchteacherComboActionPerformed

    private void clearButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButton1ActionPerformed
        clearComboBoxes();
    }//GEN-LAST:event_clearButton1ActionPerformed

    private void clearButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButton1MouseClicked

    private void searchsearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchsearchButtonActionPerformed

        String[] columns =
        {
            "fname", "lname", "phone", "email"
        };
        String table = "Client";
        char letter = 'c';
        String join;

        DefaultCellEditor dce = null;

        if (clientRadio.isSelected())
        {
            columns = new String[4];
            columns[0] = "fname";
            columns[1] = "lname";
            columns[2] = "phone";
            columns[3] = "email";
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
        } else if (courseRadio.isSelected())
        {
            columns = new String[3];
            columns[0] = "s.abbrevName";
            columns[1] = "c.level";
            columns[2] = "concat_ws(' ', t.fname, t.lname) as 'Teacher Name'";

            char lCourse = ComboBoxesIndexes.LEVEL.getLetter();
            char lTeacher = ComboBoxesIndexes.TEACHER.getLetter();
            char lSubject = ComboBoxesIndexes.COURSE.getLetter();

            table = "Course as " + lCourse;

            table += " join Teacher as " + lTeacher + " on " + lCourse + ".teacherID=" + lTeacher + ".teacherID join Subject " + lSubject + " on " + lCourse + ".subjectID=" + lSubject + ".subjectID";

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
        } else if (paraprofessionalRadio.isSelected())
        {
            columns = new String[7];
            columns[0] = "fname";
            columns[1] = "lname";
            columns[2] = "hireDate";
            columns[3] = "terminationDate";
            columns[4] = "isClockedIn";
            columns[5] = "type";
            columns[6] = "name";

            char lParaprofessional = ComboBoxesIndexes.PARAPROFESSIONAL.getLetter();
            char lCategory = ComboBoxesIndexes.PCATEGORY.getLetter();
            char lRole = ComboBoxesIndexes.PROLE.getLetter();

            table = "Paraprofessional as " + lParaprofessional;//+lCourse;

            table += " join Role as " + lRole + " on " + lParaprofessional + ".roleID=" + lRole + ".roleID join ParaprofessionalCategory as pc on pc.paraprofessionalID=" + lParaprofessional + ".paraprofessionalID join Category as " + lCategory + " on " + lCategory + ".categoryID=pc.categoryID";

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

        String fullQuery = null;

        /*
         if(clientRadio.isSelected())
         fullQuery = restrictHelper.createClientQuery(columns, table, letter);
         else if(courseRadio.isSelected())
         fullQuery = restrictHelper.createCourseQuery(columns);*/

        fullQuery = restrictHelper.createQuery(columns, table);

        System.out.println("QUERY: " + fullQuery);

        DatabaseHelper.open();
        DatabaseHelper.fillTableWithQuery(fullQuery, searchsearchTable, columns);
        DatabaseHelper.close();
        for (int i = 0; i < searchsearchTable.getColumnCount(); i++)
        {
            searchsearchTable.getColumnModel().getColumn(i).setCellEditor(dce);
        }

    }//GEN-LAST:event_searchsearchButtonActionPerformed

    private void searchsearchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchsearchButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_searchsearchButtonMouseClicked

    private void deleteSessionButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSessionButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteSessionButton1ActionPerformed

    private void teacherComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacherComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_teacherComboActionPerformed

    private void addSessionbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSessionbuttonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addSessionbuttonMouseClicked

    private void addSessionbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSessionbuttonActionPerformed
        //ADDING A SESSION
    }//GEN-LAST:event_addSessionbuttonActionPerformed

    private void clearButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButtonMouseClicked

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButtonActionPerformed

    private void deleteSessionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSessionButtonActionPerformed
        int[] selectedRows = sessionsTable.getSelectedRows();

        ((SessionTableModel) sessionsTable.getModel()).deleteRows(selectedRows);
    }//GEN-LAST:event_deleteSessionButtonActionPerformed

    private void deleteSessionButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSessionButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteSessionButton3ActionPerformed

    private void autocompleteCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autocompleteCheckActionPerformed

    }//GEN-LAST:event_autocompleteCheckActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        
        if(clientRadio.isSelected())
        {
            Validate.createClient(searchfnameCombo, searchlnameCombo, searchphoneCombo, searchemailCombo);
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

   
    
    
    
    public void clearComboBoxes()
    {
        for (int i = 0; i < uac.getBoxesLength(); i++)
        {
            uac.setComboValue("", i);
        }
    }

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new AdminView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane AdminScrollPanel;
    private javax.swing.JLabel ParaprofessionalLabel1;
    private javax.swing.JLabel ParaprofessionalLabel2;
    private javax.swing.JButton addSessionbutton;
    private javax.swing.JTabbedPane adminPanel;
    private javax.swing.JComboBox agendaCategoryCombo1;
    private javax.swing.JLabel agendaCategoryLabel1;
    private javax.swing.JLabel agendaDateLabel1;
    private javax.swing.JPanel agendaPanel1;
    private javax.swing.JTable agendaTable1;
    private javax.swing.JScrollPane agendaTableScrollPanel;
    private javax.swing.JTextArea agendaTextArea1;
    private javax.swing.JScrollPane agendaTextAreaScrollPanel;
    private javax.swing.JTable appointmentsTable;
    private javax.swing.JScrollPane appointmentsTableScrollPanel;
    private javax.swing.JCheckBox autocompleteCheck;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton1;
    private javax.swing.JComboBox categoryCombo;
    private javax.swing.JLabel categoryLabel1;
    private javax.swing.JLabel categoryLabel3;
    private javax.swing.JLabel categoryLabel4;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton clearButton1;
    private javax.swing.JRadioButton clientRadio;
    private javax.swing.JComboBox courseCombo;
    private javax.swing.JPanel courseInfoPanel1;
    private javax.swing.JLabel courseLabel1;
    private javax.swing.JLabel courseLabel3;
    private javax.swing.JLabel courseLabel4;
    private javax.swing.JLabel courseLabel5;
    private javax.swing.JLabel courseLabel6;
    private javax.swing.JLabel courseLabel7;
    private javax.swing.JRadioButton courseRadio;
    private javax.swing.JPanel createAgendaPanel1;
    private javax.swing.JComboBox creatorCombo;
    private javax.swing.JLabel creatorLabel1;
    private javax.swing.JLabel creatorLabel3;
    private javax.swing.JPanel currentSessionsPanel1;
    private javax.swing.JTextField dateField1;
    private javax.swing.JLabel dateLabel1;
    private javax.swing.JButton deleteAgendaButton1;
    private javax.swing.JButton deleteSessionButton;
    private javax.swing.JButton deleteSessionButton1;
    private javax.swing.JButton deleteSessionButton3;
    private javax.swing.JButton downloadButton1;
    private javax.swing.JComboBox emailCombo;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel emailLabel4;
    private javax.swing.JComboBox fnameCombo;
    private javax.swing.JLabel fnameLabel;
    private javax.swing.JLabel fnameLabel4;
    private javax.swing.JPanel futureSessionsPanel;
    private javax.swing.JCheckBox gcCheck1;
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JComboBox levelCombo;
    private javax.swing.JLabel levelLabel1;
    private javax.swing.JLabel levelLabel3;
    private javax.swing.JLabel levelLabel4;
    private javax.swing.JLabel levelLabel5;
    private javax.swing.JLabel levelLabel6;
    private javax.swing.JLabel levelLabel7;
    private javax.swing.JLabel levelLabel8;
    private javax.swing.JComboBox lnameCombo;
    private javax.swing.JLabel lnameLabel;
    private javax.swing.JLabel lnameLabel4;
    private javax.swing.JComboBox locationCombo;
    private javax.swing.JLabel locationLabel1;
    private javax.swing.JLabel locationLabel3;
    private javax.swing.JTextField notesField;
    private javax.swing.JLabel notesLabel1;
    private javax.swing.JLabel notesLabel3;
    private javax.swing.JPanel otherInfoPanel1;
    private javax.swing.JComboBox paraprofessionalCombo;
    private javax.swing.JRadioButton paraprofessionalRadio;
    private javax.swing.JComboBox phoneCombo;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JLabel phoneLabel4;
    private javax.swing.JPanel reportPanel1;
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
    private javax.swing.JTextField sessionendField;
    private javax.swing.JLabel sessionendLabel1;
    private javax.swing.JLabel sessionendLabel2;
    private javax.swing.JPanel sessionsAndAgendaPanel;
    private javax.swing.JPanel sessionsPanel1;
    private javax.swing.JRadioButton sessionsRadio;
    private javax.swing.JTable sessionsTable;
    private javax.swing.JPanel sessionsTablePanel;
    private javax.swing.JScrollPane sessionsTableScrollPanel;
    private javax.swing.JTextField sessionstartField;
    private javax.swing.JLabel sessionstartLabel1;
    private javax.swing.JLabel sessionstartLabel2;
    private javax.swing.JLabel sessionstartLabel3;
    private javax.swing.JPanel studentInfoPanel;
    private javax.swing.JButton submitbutton1;
    private javax.swing.JComboBox teacherCombo;
    private javax.swing.JLabel teacherLabel1;
    private javax.swing.JLabel teacherLabel3;
    private javax.swing.JLabel teacherLabel4;
    private javax.swing.JLabel teacherLabel5;
    private javax.swing.JRadioButton teacherRadio;
    private javax.swing.JPanel thechartPanel1;
    private javax.swing.JCheckBox walkoutCheck1;
    // End of variables declaration//GEN-END:variables
}
