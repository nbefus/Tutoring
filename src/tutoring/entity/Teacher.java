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

      public Teacher()
    {
        
    }
    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
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
    
    public boolean equals(Teacher t)
    {
        if(this.fName.equals(t.fName) && this.lName.equals(t.lName))
            return true;
        else
            return false;
    }
    
    @Override
    public String toString()
    {
        return teacherID + " " + lName + " " + fName;
    }

}