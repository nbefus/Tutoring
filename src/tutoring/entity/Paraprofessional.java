package tutoring.entity;


import java.sql.Timestamp;

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
    private int paraprofessionalID;         // primary key
    private Role roleID;                      // foreign key
    private String lName, fName;
    private Timestamp hireDate;
    private Timestamp terminationDate;
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

}
