package tutoring.entity;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shohe_i
 */
public class Teacher
{
    private int teacherID;
    private String lName, fName;

    public Teacher(int id, String lName, String fName) {
        this.teacherID = id;
        this.lName = lName;
        this.fName = fName;
    }

    /**
     * @return the id
     */
    public int getId() {
        return teacherID;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.teacherID = id;
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

}