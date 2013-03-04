
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
    private Timestamp hireDate;
    private boolean isIn;

    public Tutor(int id, String lName, String fName, Timestamp hireDate, boolean isIn) {
        this.id = id;
        this.lName = lName;
        this.fName = fName;
        this.hireDate = hireDate;
        this.isIn = isIn;
    }

    public void insertTutor(Tutor tutor)
    {
        
        /*
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
     * @return the isIn
     */
    public boolean isIsIn() {
        return isIn;
    }

    
}
