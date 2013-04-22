package tutoring.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shohe_i
 */
public class User {
    
    public enum UserTable {

 
        USERNAME("userName", true, getTableAlias()+".userName"),
        ROLEID("roleID", true, getTableAlias()+".roleID"),
        LNAME("lName", true, getTableAlias()+".lName"),
        FNAME("fName", true, getTableAlias()+".fName"),
        PASSWORD("password", true, getTableAlias()+".password"),
        ROLETYPE("type", false, getRoleAlias()+".type");
        
        
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        
        private static final String tableAlias = "user";
        private static final String table = "User";
        private static final String roleAlias = "role";
        

        private UserTable(String name, boolean mainTableColumn, String withAlias) {
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
    
    private String userName;    // primary key
    private Role roleID;
    private String lName, fName, password;

    public User()
    {
        
    }
    
    public User(String userName, Role roleID, String lName, String fName, String password) {
        this.userName = userName;
        this.roleID = roleID;
        this.lName = lName;
        this.fName = fName;
        this.password = password;
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
    
    
     public static ArrayList<User> selectAllUser(String addedSQLToSelect, Connection connect) {
        //Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<User> users = new ArrayList<User>();
        
        try {
            // connect way #1
           // String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
           // String user = "nbefus_me";
           // String password = "heythere";

           // connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                UserTable [] cols = UserTable.class.getEnumConstants();
                String columnSetUp = "";
                for(int i=0; i<cols.length; i++)
                {
                    columnSetUp += cols[i].getWithAlias() + " as '"+cols[i].getWithAlias()+"', ";
                }
                columnSetUp = columnSetUp.substring(0, columnSetUp.length()-2);
                
                statement = connect.createStatement();

                String query = "SELECT "+columnSetUp+" FROM User "+UserTable.getTableAlias()+" join Role "+UserTable.getRoleAlias()+" on "+UserTable.ROLEID.getWithAlias()+" = "+UserTable.getRoleAlias()+"."+UserTable.ROLEID.getName();
                query += " "+addedSQLToSelect;
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    users.add(new User(resultSet.getString(UserTable.USERNAME.getWithAlias()),  new Role(resultSet.getInt(UserTable.ROLEID.getWithAlias()), resultSet.getString(UserTable.ROLETYPE.getWithAlias())), resultSet.getString(UserTable.LNAME.getWithAlias()), resultSet.getString(UserTable.FNAME.getWithAlias()), resultSet.getString(UserTable.PASSWORD.getWithAlias())));
                }
                
                 return users;
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
            return users;
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRoleID() {
        return roleID;
    }

    public void setRoleID(Role roleID) {
        this.roleID = roleID;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString()
    {
        return userName + " " + roleID.getType() + " " + lName + " " + fName + " " + password;
    }
     
    
}
