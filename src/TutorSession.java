
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
    private int tutorID;
    private Timestamp timeAndDateEntered;
    private Timestamp sessionStart;
    private Timestamp sessionEnd;
    private Timestamp future;
    private boolean grammerCheck;
    private String notes;

    public TutorSession(int tutorSessionID, String fName, String lName, int tutorID, Timestamp timeAndDateEntered, Timestamp sessionStart, Timestamp sessionEnd, Timestamp future, boolean grammerCheck, String notes) {
        this.tutorSessionID = tutorSessionID;
        this.fName = fName;
        this.lName = lName;
        this.tutorID = tutorID;
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
    public Timestamp getFuture() {
        return future;
    }

    /**
     * @param future the future to set
     */
    public void setFuture(Timestamp future) {
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
     * @return the Notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param Notes the Notes to set
     */
    public void setNotes(String Notes) {
        this.notes = Notes;
    }
}
