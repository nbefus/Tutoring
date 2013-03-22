/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

/**
 *
 * @author shohe_i
 */
public class Role {
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
    
    public String toString()
    {
        return roleID + " " + type;
    }
    
    
}
