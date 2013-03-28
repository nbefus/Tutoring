/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

/**
 *
 * @author shohe_i
 */
public class Location {
    private int locationID; // primary key
    private String name;

    public Location(int locationID, String locationName) {
        this.locationID = locationID;
        this.name = locationName;
    }

      public Location()
    {
        
    }
      
    /**
     * @return the locationID
     */
    public int getLocationID() {
        return locationID;
    }

    /**
     * @param locationID the locationID to set
     */
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    @Override
    public String toString()
    {
        return locationID + " " + name;
    }
    
}
