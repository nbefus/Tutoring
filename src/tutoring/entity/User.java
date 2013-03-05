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

    private int id;
    private String lName, fName, userName, password;
    private String role;
    
    public User(int id, String lName, String fName, String userName, String password)
            {
                this.id = id;
                this.lName = lName;
                this.fName = fName;
                this.password = password;
                this.userName = userName;
                this.role = role;
            }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
}
