/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import tutoring.entity.*;

/**
 *
 * @author Nathaniel
 */
public class DatabaseHelper 
{
    private static Connection connect = null;
    
    public static void open()
    {
        String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
        String user = "nbefus_me";
        String password = "heythere";

        try{
        connect = DriverManager.getConnection(url1, user, password);
        }catch(Exception e)
        {
            
        }
    }
    
    public static Connection getConnection()
    {
        return connect;
    }
    
    public static String getDatabaseNameFromDisplayName(String displayName, String tableName)
    {
        System.out.println("DISPLAY NAME: "+displayName + " Table: "+tableName +":"+ Course.CourseTable.getTable() + ":"+Course.CourseTable.getDatabaseName(displayName));
        if(ParaprofessionalSession.ParaSessTable.getTable().equalsIgnoreCase(tableName))
            return ParaprofessionalSession.ParaSessTable.getDatabaseName(displayName);
        else if(Client.ClientTable.getTable().equalsIgnoreCase(tableName))
            return Client.ClientTable.getDatabaseName(displayName);
        else if(Course.CourseTable.getTable().equalsIgnoreCase(tableName))
            return Course.CourseTable.getDatabaseName(displayName);
        else if(Agenda.AgendaTable.getTable().equalsIgnoreCase(tableName))
            return Agenda.AgendaTable.getDatabaseName(displayName);
        else if(AgendaCategory.AgendaCategoryTable.getTable().equalsIgnoreCase(tableName))
            return AgendaCategory.AgendaCategoryTable.getDatabaseName(displayName);
        else if(Category.CategoryTable.getTable().equalsIgnoreCase(tableName))
            return Category.CategoryTable.getDatabaseName(displayName);
        else if(Location.LocationTable.getTable().equalsIgnoreCase(tableName))
            return Course.CourseTable.getDatabaseName(displayName);
        else if(Location.LocationTable.getTable().equalsIgnoreCase(tableName))
            return Course.CourseTable.getDatabaseName(displayName);
        else if(Paraprofessional.ParaTable.getTable().equalsIgnoreCase(tableName))
            return Paraprofessional.ParaTable.getDatabaseName(displayName);
        else if(ParaprofessionalSession.ParaSessTable.getTable().equalsIgnoreCase(tableName))
            return ParaprofessionalSession.ParaSessTable.getDatabaseName(displayName);
        else if(Role.RoleTable.getTable().equalsIgnoreCase(tableName))
            return Role.RoleTable.getDatabaseName(displayName);
        else if(Subject.SubjectTable.getTable().equalsIgnoreCase(tableName))
            return Subject.SubjectTable.getDatabaseName(displayName);
        else if(Teacher.TeacherTable.getTable().equalsIgnoreCase(tableName))
            return Teacher.TeacherTable.getDatabaseName(displayName);
        else if(User.UserTable.getTable().equalsIgnoreCase(tableName))
            return User.UserTable.getDatabaseName(displayName);
        
        return "";
    }
    
    public static void close()
    {
     try {
          if (connect != null) {
            connect.close();
          }
        } catch (Exception e) {

        } 
    }
    
    public static ArrayList<String> getTableColumns(String table)
    {
        ArrayList<String> cols = new ArrayList<String>();
        
        if(table.equals(Client.ClientTable.getTable()))
        {
            return Client.ClientTable.getMainTableColumns();
        }
        else if(table.equals(Agenda.AgendaTable.getTable()))
        {
            return Agenda.AgendaTable.getMainTableColumns();
        }
        else if(table.equals(AgendaCategory.AgendaCategoryTable.getTable()))
        {
            return AgendaCategory.AgendaCategoryTable.getMainTableColumns();
        }
        else if(table.equals(Category.CategoryTable.getTable()))
        {
            return Category.CategoryTable.getMainTableColumns();
        }
        else if(table.equals(Course.CourseTable.getTable()))
        {
            return Course.CourseTable.getMainTableColumns();
        }
        else if(table.equals(Location.LocationTable.getTable()))
        {
            return Location.LocationTable.getMainTableColumns();
        }
        else if(table.equals(Paraprofessional.ParaTable.getTable()))
        {
            return Paraprofessional.ParaTable.getMainTableColumns();
        }
        else if(table.equals(ParaprofessionalSession.ParaSessTable.getTable()))
        {
            return ParaprofessionalSession.ParaSessTable.getMainTableColumns();
        }
        else if(table.equals(Subject.SubjectTable.getTable()))
        {
            return Subject.SubjectTable.getMainTableColumns();
        }
        else if(table.equals(Teacher.TeacherTable.getTable()))
        {
            return Teacher.TeacherTable.getMainTableColumns();
        }
        else
        {
            return null;
        }
    }
     
    public static boolean insert(Object[] values, String table) {
        //Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List l = new ArrayList();
        boolean inserted = false;
        
        try {
            // connect way #1
         //   String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
         //   String user = "nbefus_me";
         //   String password = "heythere";
           
          //  connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                statement = connect.createStatement();

                String valuesString = "";
               for(int i=1; i<values.length; i++)
               {
                   //System.out.println("VALUE: "+values[i].getClass().toString() + " "+values[i]);
                   if(values[i] instanceof Integer)
                   {
                       valuesString+=values[i].toString()+",";
                   }
                   else if(values[i] instanceof Timestamp || values[i] instanceof String)
                   {
                       valuesString+="'"+values[i].toString()+"',";
                   }
                   else if(values[i] instanceof Date)
                   {
                       Timestamp t = new Timestamp(((Date)values[i]).getTime());
                       valuesString+="'"+t.toString()+"',";
                   }
                   else if(values[i] instanceof Boolean)
                   {
                       valuesString+=values[i].toString()+",";
                   }
                   else if(values[i] == null)
                   {
                       valuesString+="null,";
                   }
                   else
                       System.out.println("UNKNOWN VALUE TYPE");
                   
                   if(i == values.length-1)
                       valuesString = valuesString.substring(0, valuesString.length()-1);
               }
               
               ArrayList<String> columns = getTableColumns(table);
               String columnsString = "";
               for(int i=1; i<columns.size(); i++)
                   columnsString += columns.get(i)+",";
               columnsString = columnsString.substring(0, columnsString.length()-1);
                
                
               String query = "insert into "+table+" ("+columnsString+") values ("+valuesString+")";
               
               System.out.println(query);
                statement.executeUpdate(query);
                System.out.println("TRUE");
                inserted = true;
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
            
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }

          
          
          /*
          if (connect != null) {
            connect.close();
          }*/
        } catch (Exception e) {
           
        }    
            
        }
        return inserted;
    }
    
    
    public static boolean update(Object[] values, String table) {
        //Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List l = new ArrayList();
        
        try {
            // connect way #1
         //   String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
         //   String user = "nbefus_me";
         //   String password = "heythere";
           
          //  connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                statement = connect.createStatement();

                ArrayList<String> columns = getTableColumns(table);
                
                String valuesString = "";
                String whereString = "";
               for(int i=0; i<values.length; i++)
               {
                   if(i == 0)
                   {
                       whereString+="where "+columns.get(i)+" = " +values[i].toString();
                   }
                   else
                   {
                       valuesString += columns.get(i)+" = ";
                        if(values[i] instanceof Integer)
                        {
                            valuesString+=values[i].toString()+", ";
                        }
                        else if(values[i] instanceof Timestamp || values[i] instanceof String)
                        {
                            valuesString+="'"+values[i].toString()+"', ";
                        }
                        else if(values[i] instanceof Date)
                        {
                            Timestamp t = new Timestamp(((Date)values[i]).getTime());
                            valuesString+="'"+t.toString()+"', ";
                        }
                        else if(values[i] instanceof Boolean)
                        {
                            valuesString+=values[i].toString()+", ";
                        }
                        else if(values[i] == null)
                        {
                            valuesString+="null,";
                        }
                        else
                            System.out.println("UNKNOWN VALUE TYPE" + values[i].getClass().toString());
                   }
                   if(i == values.length-1)
                       valuesString = valuesString.substring(0, valuesString.length()-2);
               }
               
               String query = "update "+table+" set "+valuesString+" "+whereString;
               System.out.println(query);
                statement.executeUpdate(query);
                
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }

          /*
          if (connect != null) {
            connect.close();
          }*/
        } catch (Exception e) {

        }    
            return false;
        }
    }
    
      public static List selectAll(String query) {
        //Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List l = new ArrayList();
        
        try {
            // connect way #1
         //   String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
         //   String user = "nbefus_me";
         //   String password = "heythere";
           
          //  connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                statement = connect.createStatement();

               
               resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                   
                    Object[] o = new Object[resultSet.getMetaData().getColumnCount()];
                    for(int i=0; i<resultSet.getMetaData().getColumnCount(); i++)
                        o[i]=resultSet.getObject(i+1);
                        
                   l.add(o);
                }
                
                 return l;
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }

          /*
          if (connect != null) {
            connect.close();
          }*/
        } catch (Exception e) {

        }    
            return l;
        }
    }
      
    public static String[][] getDataFromRegularQuery(String query)
    {
        System.out.println(query);
        List l = DatabaseHelper.selectAll(query);
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
                //System.out.println(data[count][i] + ":"+row[i].getClass().toString());
                if(row[i] == null)
                    row[i] = "NONE";
                data[count][i] = row[i].toString();
                System.out.print("\t\t"+row[i]+"--"+row[i].getClass().toString());
            }



            count++;
         }
         
         return data;
    }
    
    public static void fillTableWithQuery(String query, JTable table, String[] columns) {
        List l = DatabaseHelper.selectAll(query);
        //List c = HibernateTest.regularSelect("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'User'");

        String[][] data = null;
        boolean firstTime = true;
        Iterator it = l.iterator();

        int count = 0;
        while (it.hasNext()) {
            Object[] row = (Object[]) it.next();

            if (firstTime) {
                data = new String[l.size()][row.length];
                firstTime = false;
            }

            //if(showID)
            //{
                for (int i = 0; i < row.length; i++) {
                    if(row[i] != null)
                        data[count][i] = row[i].toString();
                    else
                        data[count][i] = "";
                   // System.out.print("\t\t" + row[i] + "--" + row[i].getClass().toString());
                }
            /*}
            else
            {
                for (int i = 1; i < row.length; i++) {
                    if(row[i] != null)
                        data[count][i-1] = row[i].toString();
                    else
                        data[count][i-1] = "";
                   // System.out.print("\t\t" + row[i] + "--" + row[i].getClass().toString());
                }
            }
*/
            count++;
        }

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setDataVector(data, columns);
        table.setModel(dtm);
        autoResizeColWidth(table);
    }
    
    public static JTable autoResizeColWidth(JTable table)//, DefaultTableModel model) 
    {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //table.setModel(model);

    int margin = 1;

    for (int i = 0; i < table.getColumnCount(); i++) {
        int                     vColIndex = i;
        DefaultTableColumnModel colModel  = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn             col       = colModel.getColumn(vColIndex);
        int                     width     = 0;

        // Get width of column header
        TableCellRenderer renderer = col.getHeaderRenderer();

        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }

        Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);

        width = comp.getPreferredSize().width;

        // Get maximum width of column data
        for (int r = 0; r < table.getRowCount(); r++) {
            renderer = table.getCellRenderer(r, vColIndex);
            System.out.println("COL: "+vColIndex+" and ROW: "+r+"   "+renderer.toString());
            comp     = renderer.getTableCellRendererComponent(table, table.getValueAt(r, vColIndex), false, false,
                    r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }

        // Add margin
        width += 2 * margin;

        // Set the width
        col.setPreferredWidth(width);
    }

    ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(
        SwingConstants.LEFT);

     table.setAutoCreateRowSorter(true);
    //table.getTableHeader().setReorderingAllowed(false);

    return table;
    }
}
