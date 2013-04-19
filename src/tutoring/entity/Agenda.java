/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author dabeefinator
 */
public class Agenda {
    private int agendaID;
    private Date date;
    private String notes;
    private AgendaCategory agendaCategoryID;

    public Agenda(int agendaID, Date date, String notes, AgendaCategory agendaCategoryID) {
        this.agendaID = agendaID;
        this.date = date;
        this.notes = notes;
        this.agendaCategoryID = agendaCategoryID;
    }
    
    public Agenda()
    {
        
    }

    /**
     * @return the agendaID
     */
    public int getAgendaID() {
        return agendaID;
    }

    /**
     * @param agendaID the agendaID to set
     */
    public void setAgendaID(int agendaID) {
        this.agendaID = agendaID;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
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

    /**
     * @return the agendaCategoryID
     */
    public AgendaCategory getAgendaCategoryID() {
        return agendaCategoryID;
    }

    /**
     * @param agendaCategoryID the agendaCategoryID to set
     */
    public void setAgendaCategoryID(AgendaCategory agendaCategoryID) {
        this.agendaCategoryID = agendaCategoryID;
    }
    
}
