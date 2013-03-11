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
    private String roleType;

    public Role(int roleID, String roleType) {
        this.roleID = roleID;
        this.roleType = roleType;
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
     * @return the roleType
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * @param roleType the roleType to set
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
    
}
