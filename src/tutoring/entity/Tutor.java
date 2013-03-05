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
public class Tutor 
{
    private int id;
    private String lName, fName;
    private boolean isClockedIn;

    public Tutor(int id, String fName, String lName, boolean isClockedIn) {
        this.id = id;
        this.lName = lName;
        this.fName = fName;
        this.isClockedIn = isClockedIn;
    }

    

    
    public void insertTutor()
    {/*
        preparedStatement = connect.prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
        // "myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
        // Parameters start with 1
        preparedStatement.setString(1, "Test");
        preparedStatement.setString(2, "TestEmail");
        preparedStatement.setString(3, "TestWebpage");
        preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
        preparedStatement.setString(5, "TestSummary");
        preparedStatement.setString(6, "TestComment");
        preparedStatement.executeUpdate();
    
     String query = "insert into Tutors values(default,"+fName+", "+lName+", "+phone+", "+hireDate.toString()+", "+notes+")";
    */
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
