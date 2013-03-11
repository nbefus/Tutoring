/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

/**
 *
 * @author shohe_i
 */
public class Term {
    private int termID; // primary key
    private String termName;

    public Term(int termID, String termName) {
        this.termID = termID;
        this.termName = termName;
    }

    /**
     * @return the termID
     */
    public int getTermID() {
        return termID;
    }

    /**
     * @param termID the termID to set
     */
    public void setTermID(int termID) {
        this.termID = termID;
    }

    /**
     * @return the termName
     */
    public String getTermName() {
        return termName;
    }

    /**
     * @param termName the termName to set
     */
    public void setTermName(String termName) {
        this.termName = termName;
    }
   
}
