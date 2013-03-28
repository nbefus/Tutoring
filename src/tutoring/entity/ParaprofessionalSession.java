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
public class ParaprofessionalSession {
    private int paraprofessionalSessionID;      // primary key
    
    private Paraprofessional paraprofessionalID;        // foreign key
    private Client clientID;                              // foreign key
    private Course courseID;                              // foreign key
    //private Term termID;                                  // foreign key
    private Location locationID;                          // foreign key
    private Paraprofessional paraprofessionalCreatorID;   // foreign key
    
    private Timestamp timeAndDateEntered;
    private Timestamp sessionStart;
    private Timestamp sessionEnd;
    private boolean grammarCheck;
    private String notes;
    
    private boolean walkout;

      public ParaprofessionalSession()
    {
        
    }
      
    public ParaprofessionalSession(int paraprofessionalSessionID, Paraprofessional paraprofessionalID, Client client, Course course, Location location, Paraprofessional paraprofessionalCreator, Timestamp timeAndDateEntered, Timestamp sessionStart, Timestamp sessionEnd, boolean grammarCheck, String notes, boolean walkout) {
        this.paraprofessionalSessionID = paraprofessionalSessionID;
        this.paraprofessionalID = paraprofessionalID;
        this.clientID = client;
        this.courseID = course;
        //this.termID = term;
        this.locationID = location;
        this.paraprofessionalCreatorID = paraprofessionalCreator;
        this.timeAndDateEntered = timeAndDateEntered;
        this.sessionStart = sessionStart;
        this.sessionEnd = sessionEnd;
        this.grammarCheck = grammarCheck;
        this.notes = notes;
        this.walkout = walkout;
    }

    /**
     * @return the paraprofessionalSessionID
     */
    public int getParaprofessionalSessionID() {
        return paraprofessionalSessionID;
    }

    /**
     * @param paraprofessionalSessionID the paraprofessionalSessionID to set
     */
    public void setParaprofessionalSessionID(int paraprofessionalSessionID) {
        this.paraprofessionalSessionID = paraprofessionalSessionID;
    }

    /**
     * @return the paraprofessionalID
     */
    public Paraprofessional getParaprofessionalID() {
        return paraprofessionalID;
    }

    /**
     * @param paraprofessionalID the paraprofessionalID to set
     */
    public void setParaprofessionalID(Paraprofessional paraprofessionalID) {
        this.paraprofessionalID = paraprofessionalID;
    }

    /**
     * @return the clientID
     */
    public Client getClientID() {
        return clientID;
    }

    /**
     * @param clientID the clientID to set
     */
    public void setClientID(Client clientID) {
        this.clientID = clientID;
    }

    /**
     * @return the courseID
     */
    public Course getCourseID() {
        return courseID;
    }

    /**
     * @param courseID the courseID to set
     */
    public void setCourseID(Course courseID) {
        this.courseID = courseID;
    }

    /*
    
    public Term getTermID() {
        return termID;
    }

   
    public void setTermID(Term termID) {
        this.termID = termID;
    }*/

    /**
     * @return the locationID
     */
    public Location getLocationID() {
        return locationID;
    }

    /**
     * @param locationID the locationID to set
     */
    public void setLocationID(Location locationID) {
        this.locationID = locationID;
    }

    /**
     * @return the paraprofessionalCreatorID
     */
    public Paraprofessional getParaprofessionalCreatorID() {
        return paraprofessionalCreatorID;
    }

    /**
     * @param paraprofessionalCreatorID the paraprofessionalCreatorID to set
     */
    public void setParaprofessionalCreatorID(Paraprofessional paraprofessionalCreatorID) {
        this.paraprofessionalCreatorID = paraprofessionalCreatorID;
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
     * @return the grammarCheck
     */
    public boolean isGrammarCheck() {
        return grammarCheck;
    }

    /**
     * @param grammarCheck the grammarCheck to set
     */
    public void setGrammarCheck(boolean grammarCheck) {
        this.grammarCheck = grammarCheck;
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
    
    @Override
    public String toString()
    {
        return paraprofessionalSessionID + " " + paraprofessionalID.toString() + " " + clientID.toString() + " " + locationID.toString() + " " + paraprofessionalCreatorID.toString() + " " + timeAndDateEntered.toString() + " " + sessionStart.toGMTString() + " " + sessionEnd.toGMTString() + " " + grammarCheck + " " + notes + " " + walkout;
    }
    
     public boolean isWalkout() {
        return walkout;
    }

    /**
     * @param grammarCheck the grammarCheck to set
     */
    public void setWalkout(boolean walkout) {
        this.walkout = walkout;
    }
  
}