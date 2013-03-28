package tutoring.entity;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shohe_i
 */
public class Course
{
    private int courseID;       // primary key
    private Teacher teacherID;    // foreign key
    private Subject subjectID;    // foreign key
    private int level;

    public Course(Teacher teacher, Subject subject, int level) {
        this.teacherID = teacher;
        this.subjectID = subject;
        this.level = level;
    }
      public Course()
    {
        
    }
    

    /**
     * @return the teacherID
     */
    public Teacher getTeacherID() {
        return teacherID;
    }

    /**
     * @param teacherID the teacherID to set
     */
    public void setTeacherID(Teacher teacherID) {
        this.teacherID = teacherID;
    }

    /**
     * @return the subjectID
     */
    public Subject getSubjectID() {
        return subjectID;
    }

    /**
     * @param subjectID the subjectID to set
     */
    public void setSubjectID(Subject subjectID) {
        this.subjectID = subjectID;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public boolean equals(Course c)
    {
        if(this.level == c.getLevel() && this.subjectID.equals(c.getSubjectID()) && this.teacherID.equals(c.getTeacherID()))
            return true;
        else
            return false;
    }
    
    @Override
    public String toString()
    {
        return courseID + " " + teacherID.toString() + " " + subjectID.toString() + " " + level;
    }
}
    
