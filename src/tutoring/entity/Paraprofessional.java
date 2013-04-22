package tutoring.entity;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dabeefinator
 */
public class Paraprofessional 
{
     public enum ParaTable {

 
        PARAPROFESSIONALID("paraprofessionalID", true, getTableAlias()+".paraprofessionalID"),
        ROLEID("roleID", true, getTableAlias()+".roleID"),
        LNAME("lName", true, getTableAlias()+".lName"),
        FNAME("fName", true, getTableAlias()+".fName"),
        HIREDATE("hireDate", true, getTableAlias()+".hireDate"),
        TERMINATIONDATE("terminationDate", true, getTableAlias()+".terminationDate"),
        ISCLOCKEDIN("isClockedIn", true, getTableAlias()+".isClockedIn"),
        ROLETYPE("type", false, getRoleAlias()+".type");
        
        
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        
        private static final String tableAlias = "paraprofessional";
        private static final String table = "Paraprofessional";
        private static final String roleAlias = "role";
        

        private ParaTable(String name, boolean mainTableColumn, String withAlias) {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
        }

        public String getName() {
            return name;
        }

        public boolean isMainTableColumn() {
            return mainTableColumn;
        }

        public String getWithAlias() {
            return withAlias;
        } 
        
        public static String getTableAlias()
        {
            return tableAlias;
        }
        
         public static String getTable()
        {
            return table;
        } 
        
        public static ArrayList<String> getMainTableColumns()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Paraprofessional.ParaTable[] columns = Paraprofessional.ParaTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(columns[i].isMainTableColumn())
                    cols.add(columns[i].getName());
            }
            return cols;
        }

        public static String getRoleAlias()
        {
            return roleAlias;
        }
    }
     
    private int paraprofessionalID;         // primary key
    private Role roleID;                      // foreign key
    private String lName, fName;
    private Timestamp hireDate = null;
    private Timestamp terminationDate = null;
    private boolean isClockedIn;

      public Paraprofessional()
    {
        
    }
    public Paraprofessional(int paraprofessionalID, Role role, String lName, String fName, Timestamp hireDate, Timestamp terminationDate, boolean isClockedIn) {
        this.paraprofessionalID = paraprofessionalID;
        this.roleID = role;
        this.lName = lName;
        this.fName = fName;
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
        this.isClockedIn = isClockedIn;
    }
    
    public static Object[] getValues(Paraprofessional p)
    {
        Object[] values = new Object[7];
        values[0]=p.getParaprofessionalID();
        values[1]=p.getRoleID();
        values[2]=p.getlName();
        values[3]=p.getfName();
        values[4]=p.getHireDate();
        values[5]=p.getTerminationDate();
        values[6]=p.isIsClockedIn();
        return values;
    }
    
    
     public static ArrayList<Paraprofessional> selectAllParaprofessional(String addedSQLToSelect, Connection connect) {
        //Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Paraprofessional> paraprofessionals = new ArrayList<Paraprofessional>();
        
        try {
            // connect way #1
           // String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
           // String user = "nbefus_me";
           // String password = "heythere";

           // connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                Paraprofessional.ParaTable [] cols = Paraprofessional.ParaTable.class.getEnumConstants();
                String columnSetUp = "";
                for(int i=0; i<cols.length; i++)
                {
                    columnSetUp += cols[i].getWithAlias() + " as '"+cols[i].getWithAlias()+"', ";
                }
                columnSetUp = columnSetUp.substring(0, columnSetUp.length()-2);
                
                statement = connect.createStatement();

                String query = "SELECT "+columnSetUp+" FROM Paraprofessional "+ParaTable.getTableAlias()+" join Role "+ParaTable.getRoleAlias()+" on "+ParaTable.ROLEID.getWithAlias()+" = "+ParaTable.getRoleAlias()+"."+ParaTable.ROLEID.getName();
                query += " "+addedSQLToSelect;
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    paraprofessionals.add(new Paraprofessional(resultSet.getInt(ParaTable.PARAPROFESSIONALID.getWithAlias()),  new Role(resultSet.getInt(ParaTable.ROLEID.getWithAlias()), resultSet.getString(ParaTable.ROLETYPE.getWithAlias())), resultSet.getString(ParaTable.LNAME.getWithAlias()), resultSet.getString(ParaTable.FNAME.getWithAlias()), resultSet.getTimestamp(ParaTable.HIREDATE.getWithAlias()), resultSet.getTimestamp(ParaTable.TERMINATIONDATE.getWithAlias()), resultSet.getBoolean(ParaTable.ISCLOCKEDIN.getWithAlias())));
                }
                
                 return paraprofessionals;
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        } finally {
            try
            {
            if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }
            }
            catch(Exception e)
            {
                
            }
            return paraprofessionals;
        }
    }

    /**
     * @return the paraprofessionalID
     */
    public int getParaprofessionalID() {
        return paraprofessionalID;
    }

    /**
     * @param paraprofessionalID the paraprofessionalID to set
     */
    public void setParaprofessionalID(int paraprofessionalID) {
        this.paraprofessionalID = paraprofessionalID;
    }

    /**
     * @return the roleID
     */
    public Role getRoleID() {
        return roleID;
    }

    /**
     * @param roleID the roleID to set
     */
    public void setRoleID(Role roleID) {
        this.roleID = roleID;
    }

    /**
     * @return the lName
     */
    public String getlName() {
        return lName;
    }

    /**
     * @param lName the lName to set
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     * @return the fName
     */
    public String getfName() {
        return fName;
    }

    /**
     * @param fName the fName to set
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * @return the hireDate
     */
    public Timestamp getHireDate() {
        return hireDate;
    }

    /**
     * @param hireDate the hireDate to set
     */
    public void setHireDate(Timestamp hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * @return the terminationDate
     */
    public Timestamp getTerminationDate() {
        return terminationDate;
    }

    /**
     * @param terminationDate the terminationDate to set
     */
    public void setTerminationDate(Timestamp terminationDate) {
        this.terminationDate = terminationDate;
    }

    /**
     * @return the isClockedIn
     */
    public boolean isIsClockedIn() {
        return isClockedIn;
    }

    /**
     * @param isClockedIn the isClockedIn to set
     */
    public void setIsClockedIn(boolean isClockedIn) {
        this.isClockedIn = isClockedIn;
    }
    
    public String hi()
    {
        return paraprofessionalID + " " + roleID.toString() + " " + lName + " " + fName + " " + hireDate.toString() + " " + terminationDate.toString() + " " + isClockedIn;
    }
    
    @Override
    public String toString()
    {
        return paraprofessionalID + " " + roleID.toString() + " " + lName + " " + fName + " " + hireDate + " " + terminationDate + " " + isClockedIn;
    }
    


}
