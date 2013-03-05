package tutoring.entity;


import java.sql.Timestamp;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shohe_i
 */
public class TutorSession {
    private int tutorSessionID;
    private String fName, lName;
    private Tutor tutor;
    private Subject subject;
    private Teacher teacher;
    private int level;
    private Timestamp timeAndDateEntered;
    private Timestamp sessionStart;
    private Timestamp sessionEnd;
    private boolean future;
    private boolean grammerCheck;
    private String notes;

    public TutorSession(int tutorSessionID, String fName, String lName, Tutor tutor, Subject subject, Teacher teacher, int level, Timestamp timeAndDateEntered, Timestamp sessionStart, Timestamp sessionEnd, boolean future, boolean grammerCheck, String notes) {
        this.tutorSessionID = tutorSessionID;
        this.fName = fName;
        this.lName = lName;
        this.tutor = tutor;
        this.subject = subject;
        this.teacher = teacher;
        this.level = level;
        this.timeAndDateEntered = timeAndDateEntered;
        this.sessionStart = sessionStart;
        this.sessionEnd = sessionEnd;
        this.future = future;
        this.grammerCheck = grammerCheck;
        this.notes = notes;
    }

    /**
     * @return the tutorSessionID
     */
    public int getTutorSessionID() {
        return tutorSessionID;
    }

    /**
     * @param tutorSessionID the tutorSessionID to set
     */
    public void setTutorSessionID(int tutorSessionID) {
        this.tutorSessionID = tutorSessionID;
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
     * @return the tutor
     */
    public Tutor getTutor() {
        return tutor;
    }

    /**
     * @param tutor the tutor to set
     */
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    /**
     * @return the subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * @return the teacher
     */
    public Teacher getTeacher() {
        return teacher;
    }

    /**
     * @param teacher the teacher to set
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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

    /**
     * @return the timeAndDateEntered
     */
    public Timestamp getTimeAndDateEntered() {
        return timeAndDateEntered;
    }

    /**
     * @param timeAndDateEntered the timeAndDateEntered to set
     */
    public void setTimeAndDateEntered(Timestamp timeAndDateEntered) {
        this.timeAndDateEntered = timeAndDateEntered;
    }

    /**
     * @return the sessionStart
     */
    public Timestamp getSessionStart() {
        return sessionStart;
    }

    /**
     * @param sessionStart the sessionStart to set
     */
    public void setSessionStart(Timestamp sessionStart) {
        this.sessionStart = sessionStart;
    }

    /**
     * @return the sessionEnd
     */
    public Timestamp getSessionEnd() {
        return sessionEnd;
    }

    /**
     * @param sessionEnd the sessionEnd to set
     */
    public void setSessionEnd(Timestamp sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    /**
     * @return the future
     */
    public boolean isFuture() {
        return future;
    }

    /**
     * @param future the future to set
     */
    public void setFuture(boolean future) {
        this.future = future;
    }

    /**
     * @return the grammerCheck
     */
    public boolean isGrammerCheck() {
        return grammerCheck;
    }

    /**
     * @param grammerCheck the grammerCheck to set
     */
    public void setGrammerCheck(boolean grammerCheck) {
        this.grammerCheck = grammerCheck;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    
    
}