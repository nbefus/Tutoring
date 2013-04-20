/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

/**
 *
 * @author dabeefinator
 */
public class AgendaCategory 
{
    private int agendaCategoryID;
    private String type;

    public AgendaCategory(int agendaCateogoryID, String type) {
        this.agendaCategoryID = agendaCateogoryID;
        this.type = type;
    }
    
    public AgendaCategory()
    {
        
    }

    /**
     * @return the agendaCateogoryID
     */
    public int getAgendaCategoryID() {
        return agendaCategoryID;
    }

    /**
     * @param agendaCateogoryID the agendaCateogoryID to set
     */
    public void setAgendaCategoryID(int agendaCateogoryID) {
        this.agendaCategoryID = agendaCateogoryID;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    
    
}
