/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

import java.util.ArrayList;

/**
 *
 * @author shohe_i
 */
public class Role {
    
    public enum RoleTable {
        
        LOCATIONID("Location ID", "locationID", true, getTableAlias()+".locationID", true),
        NAME("Location", "name", true, getTableAlias()+".name", false);
        
        
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private String displayName;
        
        private static final String tableAlias = "location";
        private static final String table = "Location";
        

        private RoleTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID) {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
            this.isID = isID;
            this.displayName = displayName;
        }

        public String getName() {
            return name;
        }
        
        public String getDisplayName(){
            return displayName;
        }

        public boolean isID(){
            return isID;
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
            Role.RoleTable[] columns = Role.RoleTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(columns[i].isMainTableColumn())
                    cols.add(columns[i].getName());
            }
            return cols;
        }
        
        public static ArrayList<String> getTableColumnsWithoutIDs()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Role.RoleTable[] columns = Role.RoleTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID())
                    cols.add(columns[i].getName());
            }
            return cols;
        }
        
        public static String getDatabaseName(String DisplayName)
        {
            Role.RoleTable[] columns = Role.RoleTable.class.getEnumConstants();
            for (int i = 0; i < columns.length; i++)
            {
                if (columns[i].getDisplayName().equalsIgnoreCase(DisplayName))
                {
                    return columns[i].getWithAlias();
                }
            }

            return "";
        }
    }
    
    private int roleID; // primary key
    private String type;

    public Role(int roleID, String roleType) {
        this.roleID = roleID;
        this.type = roleType;
    }
    
    public Role()
    {
        
    }

    /**
     * @return the roleID
     */
    public int getRoleID() {
        return roleID;
    }

    /**
     * @param roleID the roleID to set
     */
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString()
    {
        return roleID + " " + type;
    }
    
    
}
