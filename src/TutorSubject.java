/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shohe_i
 */
public class TutorSubject
{
    private int tutorID;
    private int subjectID;
    private int level;

    public TutorSubject(int tutorID, int subjectID, int level) {
        this.tutorID = tutorID;
        this.subjectID = subjectID;
        this.level = level;
    }

    /**
     * @return the tutorID
     */
    public int getTutorID() {
        return tutorID;
    }

    /**
     * @param tutorID the tutorID to set
     */
    public void setTutorID(int tutorID) {
        this.tutorID = tutorID;
    }

    /**
     * @return the subjectID
     */
    public int getSubjectID() {
        return subjectID;
    }

    /**
     * @param subjectID the subjectID to set
     */
    public void setSubjectID(int subjectID) {
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
            
}
