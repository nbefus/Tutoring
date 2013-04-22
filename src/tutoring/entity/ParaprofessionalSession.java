package tutoring.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shohe_i
 */
public class ParaprofessionalSession {
    /*
     *  String query = "SELECT paraprofessionalSessionID," 
               +"p.paraprofessionalID as 'pParaprofessionalID',  p.fName as 'pFName', p.lName as 'pLName', p.hireDate as 'pHireDate', p.terminationDate as 'pTerminationDate', p.isClockedIn as 'pIsClockedIn', r.roleID as 'pRoleID', r.type as 'pType',"

               +"sess.paraprofessionalCreatorID, cre.fName as 'creFName', cre.lName as 'creLName', cre.hireDate as 'creHireDate', cre.terminationDate as 'creTerminationDate', cre.isClockedIn as 'creIsClockedIn', rr.roleID as 'creRoleID', rr.type as 'creType',"

               +"l.locationID, l.name as 'locName', "

               +"cli.clientID, cli.fName as 'cliFName', cli.lName as 'cliLName', phone, email,"

               +"cour.courseID, t.teacherID, s.subjectID, cour.level, t.fName as 'tFName', t.lName as 'tLName', cat.categoryID, abbrevName, cat.name as 'catName',"

               +"sess.timeAndDateEntered, sess.sessionStart, sess.sessionEnd, sess.grammarCheck, sess.notes, sess.walkout "

     */
    public enum ParaSessTable {

        PARAPROFESSIONALSESSIONID("paraprofessionalSessionID", true, getTableAlias()+".paraprofessionalSessionID"),
        PARAPROFESSIONALID("paraprofessionalID", true, getTableAlias()+".paraprofessionalID"),
        CLIENTID("clientID", true, getTableAlias()+".clientID"),
        COURSEID("courseID", true, getTableAlias()+".courseID"),
        LOCATIONID("locationID", true, getTableAlias()+".locationID"),
        PARAPROFESSIONALCREATORID("paraprofessionalCreatorID", true, getTableAlias()+".paraprofessionalCreatorID"),
        TIMEANDDATEENTERED("timeAndDateEntered", true, getTableAlias()+".timeAndDateEntered"),
        SESSIONSTART("sessionStart", true, getTableAlias()+".sessionStart"),
        SESSIONEND("sessionEnd", true, getTableAlias()+".sessionEnd"),
        GRAMMARCHECK("grammarCheck", true, getTableAlias()+".grammarCheck"),
        NOTES("notes", true, getTableAlias()+".notes"),
        WALKOUT("walkout", true, getTableAlias()+".walkout"),
        PARAPROFESSIONALFNAME("fName", false, getParaprofessionalAlias()+".fName"),
        PARAPROFESSIONALLNAME("lName", false, getParaprofessionalAlias()+".lName"),
        PARAPROFESSIONALHIREDATE("hireDate", false, getParaprofessionalAlias()+".hireDate"),
        PARAPROFESSIONALTERMINATIONDATE("terminationDate", false, getParaprofessionalAlias()+".terminationDate"),
        PARAPROFESSIONALISCLOCKEDIN("isClockedIn", false, getParaprofessionalAlias()+".isClockedIn"),
        PARAPROFESSIONALROLEID("roleID", false, getParaprofessionalAlias()+".roleID"),
        PARAPROFESSIONALROLETYPE("type", false, getParaprofessionalRoleAlias()+".type"),
        CREATORFNAME("fName", false, getCreatorAlias()+".fName"),
        CREATORLNAME("lName", false, getCreatorAlias()+".lName"),
        CREATORHIREDATE("hireDate", false, getCreatorAlias()+".hireDate"),
        CREATORTERMINATIONDATE("terminationDate", false, getCreatorAlias()+".terminationDate"),
        CREATORISCLOCKEDIN("isClockedIn", false, getCreatorAlias()+".isClockedIn"),
        CREATORROLEID("roleID", false, getCreatorAlias()+".roleID"),
        CREATORROLETYPE("type", false, getCreatorRoleAlias()+".type"),
        LOCATIONNAME("name", false, getLocationAlias()+".name"),
        CLIENTFNAME("fName", false, getClientAlias()+".fName"),
        CLIENTLNAME("lName", false, getClientAlias()+".lName"),
        CLIENTPHONE("phone", false, getClientAlias()+".phone"),
        CLIENTEMAIL("email", false, getClientAlias()+".email"),
        SUBJECTID("subjectID", false, getCourseAlias()+".subjectID"),
        SUBJECTABBREVNAME("abbrevName", false, getSubjectAlias()+".abbrevName"),
        SUBJECTCATEGORYID("categoryID", false, getSubjectAlias()+".categoryID"),
        SUBJECTCATEGORYNAME("name", false, getCategoryAlias()+".name"),
        TEACHERID("teacherID", false, getCourseAlias()+".teacherID"),
        TEACHERFNAME("fName", false, getTeacherAlias()+".fName"),
        TEACHERLNAME("lName", false, getTeacherAlias()+".lName"),
        COURSELEVEL("level", false, getCourseAlias()+".level");

        
        private String name;

        private boolean mainTableColumn;
        private String withAlias;
        
        private static final String tableAlias = "paraprofessionalsession";
        private static final String paraprofessionalAlias = "pParaprofessional";
        private static final String creatorAlias = "creParaprofessional";
        private static final String paraprofessionalRoleAlias = "pRole";
        private static final String creatorRoleAlias = "creator";
        private static final String clientAlias = "client";
        private static final String locationAlias = "location";
        private static final String teacherAlias = "teacher";
        private static final String subjectAlias = "subject";
        private static final String courseAlias = "course";
        private static final String categoryAlias = "category";
        
        private static final String table = "ParaprofessionalSession";

        private ParaSessTable(String name, boolean mainTableColumn, String withAlias) {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
        }

        public String getName() {
            return name;
        }
        public boolean isMainTableColumn() {
            return mainTableColumn;
        }

        public String getWithAlias() {
            return withAlias;
        } 
        
        public static String getTableAlias()
        {
            return tableAlias;
        }
        
        public static String getParaprofessionalAlias()
        {
            return paraprofessionalAlias;
        }

        public static String getCreatorAlias()
        {
            return creatorAlias;
        }

        public static String getParaprofessionalRoleAlias()
        {
            return paraprofessionalRoleAlias;
        }

        public static String getCreatorRoleAlias()
        {
            return creatorRoleAlias;
        }
        
         public static String getTable()
        {
            return table;
        } 
        
        public static ArrayList<String> getMainTableColumns()
        {
            ArrayList<String> cols = new ArrayList<String>();
            ParaprofessionalSession.ParaSessTable[] columns = ParaprofessionalSession.ParaSessTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(columns[i].isMainTableColumn())
                    cols.add(columns[i].getName());
            }
            return cols;
        }

        public static String getClientAlias()
        {
            return clientAlias;
        }

        public static String getLocationAlias()
        {
            return locationAlias;
        }

        public static String getTeacherAlias()
        {
            return teacherAlias;
        }

        public static String getSubjectAlias()
        {
            return subjectAlias;
        }

        public static String getCourseAlias()
        {
            return courseAlias;
        }

        public static String getCategoryAlias()
        {
            return categoryAlias;
        }
        
        public static String getSelectQuery()
        {
            ParaprofessionalSession.ParaSessTable [] ps = ParaprofessionalSession.ParaSessTable.class.getEnumConstants();
                String columnSetUp = "";
                for(int i=0; i<ps.length; i++)
                {
                    columnSetUp += ps[i].getWithAlias() + " as '"+ps[i].getWithAlias()+"', ";
                }
                columnSetUp = columnSetUp.substring(0, columnSetUp.length()-2);
                
                String query = "SELECT " + columnSetUp
                    /*    + "paraprofessionalSessionID," 
               +"p.paraprofessionalID as 'pParaprofessionalID',  p.fName as 'pFName', p.lName as 'pLName', p.hireDate as 'pHireDate', p.terminationDate as 'pTerminationDate', p.isClockedIn as 'pIsClockedIn', r.roleID as 'pRoleID', r.type as 'pType',"

               +"sess.paraprofessionalCreatorID, cre.fName as 'creFName', cre.lName as 'creLName', cre.hireDate as 'creHireDate', cre.terminationDate as 'creTerminationDate', cre.isClockedIn as 'creIsClockedIn', rr.roleID as 'creRoleID', rr.type as 'creType',"

               +"l.locationID, l.name as 'locName', "

               +"cli.clientID, cli.fName as 'cliFName', cli.lName as 'cliLName', phone, email,"

               +"cour.courseID, t.teacherID, s.subjectID, cour.level, t.fName as 'tFName', t.lName as 'tLName', cat.categoryID, abbrevName, cat.name as 'catName',"

               +"sess.timeAndDateEntered, sess.sessionStart, sess.sessionEnd, sess.grammarCheck, sess.notes, sess.walkout "*/

               +" FROM ParaprofessionalSession "+ParaSessTable.getTableAlias()+" join Paraprofessional "+ParaSessTable.getParaprofessionalAlias()+" on "+ParaSessTable.PARAPROFESSIONALID.getWithAlias()+
                        "="+ParaSessTable.getParaprofessionalAlias()+"."+ParaSessTable.PARAPROFESSIONALID.getName()+
                        " join Role "+ParaSessTable.getParaprofessionalRoleAlias()+" on "+ParaSessTable.PARAPROFESSIONALROLEID.getWithAlias()+
                        " = "+ParaSessTable.getParaprofessionalRoleAlias()+"."+ParaSessTable.PARAPROFESSIONALROLEID.getName()+
                        " join Paraprofessional "+ParaSessTable.getCreatorAlias()+" on "+ParaSessTable.PARAPROFESSIONALCREATORID.getWithAlias()+
                        "="+ParaSessTable.getCreatorAlias()+"."+ParaSessTable.PARAPROFESSIONALID.getName()+" join Role "+ParaSessTable.getCreatorRoleAlias()+" on "+ParaSessTable.CREATORROLEID.getWithAlias()+
                        "="+ParaSessTable.getCreatorRoleAlias()+"."+ParaSessTable.CREATORROLEID.getName()+" join Client "+ParaSessTable.getClientAlias()+" on "+ParaSessTable.CLIENTID.getWithAlias()+
                        "="+ParaSessTable.getClientAlias()+"."+ParaSessTable.CLIENTID.getName()+" join Course "+ParaSessTable.getCourseAlias()+
                        " on "+ParaSessTable.COURSEID.getWithAlias()+"="+ParaSessTable.getCourseAlias()+"."+ParaSessTable.COURSEID.getName()+
                        " join Teacher "+ParaSessTable.getTeacherAlias()+" on "+ParaSessTable.TEACHERID.getWithAlias()+"="+ParaSessTable.getTeacherAlias()+"."+ParaSessTable.TEACHERID.getName()+
                        " join Subject "+ParaSessTable.getSubjectAlias()+" on "+ParaSessTable.SUBJECTID.getWithAlias()+"="+ParaSessTable.getSubjectAlias()+"."+ParaSessTable.SUBJECTID.getName()+
                        " join Category "+ParaSessTable.getCategoryAlias()+" on "+ParaSessTable.SUBJECTCATEGORYID.getWithAlias()+"="+ParaSessTable.getCategoryAlias()+"."+ParaSessTable.SUBJECTCATEGORYID.getName()+
                        " join Location "+ParaSessTable.getLocationAlias()+" on "+ParaSessTable.LOCATIONID.getWithAlias()+"="+ParaSessTable.getLocationAlias()+"."+ParaSessTable.LOCATIONID.getName();
               return query;
        }
    }
    
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
      
      public static void test()
      {
          System.out.println(ParaSessTable.getSelectQuery());
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
    
    public static Object[] getValues(ParaprofessionalSession ps)
    {
        Object[] values = new Object[12];
        values[0]=ps.getParaprofessionalSessionID();
        values[1]=ps.getParaprofessionalID().getParaprofessionalID();
        values[2]=ps.getClientID().getClientID();
        values[3]=ps.getCourseID().getCourseID();
        values[4]=ps.getLocationID().getLocationID();
        values[5]=ps.getParaprofessionalCreatorID().getParaprofessionalID();
        values[6]=ps.getTimeAndDateEntered();
        values[7]=ps.getSessionStart();
        values[8]=ps.getSessionEnd();
        values[9]=ps.isGrammarCheck();
        values[10]=ps.getNotes();
        values[11]=ps.isWalkout();
        return values;
    }
 
     public static ArrayList<ParaprofessionalSession> selectAllParaprofessionalSession(String addedSQLToSelect, Connection connect) {
      //  Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<ParaprofessionalSession> paraprofessionalSessions = new ArrayList<ParaprofessionalSession>();
        
        try {
            // connect way #1
            //String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
            //String user = "nbefus_me";
            //String password = "heythere";
            
            Timestamp now = new Timestamp((new Date()).getTime());
            //connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                statement = connect.createStatement();

               // ParaprofessionalSession.ParaSessTable ps = ParaprofessionalSession.ParaSessTable.;
                String query = ParaprofessionalSession.ParaSessTable.getSelectQuery();
               query += " "+addedSQLToSelect;
               
               resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                   
                    Location l = new Location(resultSet.getInt(ParaSessTable.LOCATIONID.getWithAlias()),resultSet.getString(ParaSessTable.LOCATIONNAME.getWithAlias()));
                    Paraprofessional p = new Paraprofessional(resultSet.getInt(ParaSessTable.PARAPROFESSIONALID.getWithAlias()),  new Role(resultSet.getInt(ParaSessTable.PARAPROFESSIONALROLEID.getWithAlias()), resultSet.getString(ParaSessTable.PARAPROFESSIONALROLETYPE.getWithAlias())), resultSet.getString(ParaSessTable.PARAPROFESSIONALLNAME.getWithAlias()), resultSet.getString(ParaSessTable.PARAPROFESSIONALFNAME.getWithAlias()), resultSet.getTimestamp(ParaSessTable.PARAPROFESSIONALHIREDATE.getWithAlias()), resultSet.getTimestamp(ParaSessTable.PARAPROFESSIONALTERMINATIONDATE.getWithAlias()), resultSet.getBoolean(ParaSessTable.PARAPROFESSIONALISCLOCKEDIN.getWithAlias()));
                    Paraprofessional cre = new Paraprofessional(resultSet.getInt(ParaSessTable.PARAPROFESSIONALCREATORID.getWithAlias()),  new Role(resultSet.getInt(ParaSessTable.CREATORROLEID.getWithAlias()), resultSet.getString(ParaSessTable.CREATORROLETYPE.getWithAlias())), resultSet.getString(ParaSessTable.CREATORLNAME.getWithAlias()), resultSet.getString(ParaSessTable.CREATORFNAME.getWithAlias()), resultSet.getTimestamp(ParaSessTable.CREATORHIREDATE.getWithAlias()), resultSet.getTimestamp(ParaSessTable.CREATORTERMINATIONDATE.getWithAlias()), resultSet.getBoolean(ParaSessTable.CREATORISCLOCKEDIN.getWithAlias()));
                    Course c = new Course(resultSet.getInt(ParaSessTable.COURSEID.getWithAlias()), new Teacher(resultSet.getInt(ParaSessTable.TEACHERID.getWithAlias()), resultSet.getString(ParaSessTable.TEACHERLNAME.getWithAlias()), resultSet.getString(ParaSessTable.TEACHERFNAME.getWithAlias())), new Subject(resultSet.getInt(ParaSessTable.SUBJECTID.getWithAlias()), resultSet.getString(ParaSessTable.SUBJECTABBREVNAME.getWithAlias()), new Category(resultSet.getInt(ParaSessTable.SUBJECTCATEGORYID.getWithAlias()),resultSet.getString(ParaSessTable.SUBJECTCATEGORYNAME.getWithAlias()))), resultSet.getInt(ParaSessTable.COURSELEVEL.getWithAlias()));
                    Client cli = new Client(resultSet.getInt(ParaSessTable.CLIENTID.getWithAlias()), resultSet.getString(ParaSessTable.CLIENTFNAME.getWithAlias()), resultSet.getString(ParaSessTable.CLIENTLNAME.getWithAlias()), resultSet.getString(ParaSessTable.CLIENTEMAIL.getWithAlias()), resultSet.getString(ParaSessTable.CLIENTPHONE.getWithAlias()));
                    
                    paraprofessionalSessions.add(new ParaprofessionalSession(resultSet.getInt(ParaSessTable.PARAPROFESSIONALSESSIONID.getWithAlias()), p, cli, c, l, cre, resultSet.getTimestamp(ParaSessTable.TIMEANDDATEENTERED.getWithAlias()), resultSet.getTimestamp(ParaSessTable.SESSIONSTART.getWithAlias()), resultSet.getTimestamp(ParaSessTable.SESSIONEND.getWithAlias()), resultSet.getBoolean(ParaSessTable.GRAMMARCHECK.getWithAlias()), resultSet.getString(ParaSessTable.NOTES.getWithAlias()), resultSet.getBoolean(ParaSessTable.WALKOUT.getWithAlias())));
                }
                
                 return paraprofessionalSessions;
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid " +ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }

          /*
          if (connect != null) {
            connect.close();
          }*/
        } catch (Exception e) {

        }    
            return paraprofessionalSessions;
        }
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
    /*
    
    @Override
    public String toString()
    {
        return paraprofessionalSessionID + " " + paraprofessionalID.toString() + " " + clientID.toString() + " " + locationID.toString() + " " + paraprofessionalCreatorID.toString() + " " + timeAndDateEntered.toString() + " " + sessionStart.toGMTString() + " " + sessionEnd.toGMTString() + " " + grammarCheck + " " + notes + " " + walkout;
    }*/
    
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