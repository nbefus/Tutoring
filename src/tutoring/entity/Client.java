/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

/**
 *
 * @author shohe_i
 */
public class Client {
    private int clientID; // primary key
    private String fName, lName, email;
    private String phone;

    public Client(int clientID, String fName, String lName, String email, String phone) {
        this.clientID = clientID;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phone = phone;
    }

      public Client()
    {
        
    }
      
    /**
     * @return the clientID
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * @param clientID the clientID to set
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString()
    {
        return clientID + " " + fName + " " + lName + " " + email + " " + phone;
    }
    
    
}
