package tutoring.entity;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shohe_i
 */
public class User {
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
    
    public String toString()
    {
        return userName + " " + roleID.getType() + " " + lName + " " + fName + " " + password;
    }
     
    
}
