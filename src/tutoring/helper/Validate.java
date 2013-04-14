/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import tutoring.entity.Client;
import tutoring.entity.Course;
import tutoring.entity.Location;
import tutoring.entity.Paraprofessional;
import tutoring.entity.ParaprofessionalSession;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;

/**
 *
 * @author Nathaniel
 */
public class Validate 
{
    public Validate()
    {
        
    }
    
    public static boolean validateClient(Client c)
    {
        String query = "from Client as c where c.fName='"+c.getfName()+"' and c.lName='"+c.getlName()+"' and c.phone='"+c.getPhone()+"' and c.email='"+c.getEmail()+"'";
        System.out.println(query);
        if(HibernateTest.select(query).size() > 0)
            return true;
        return false;
    }
    
    public static boolean validateCourse(Course c)
    {
        if(HibernateTest.regularSelect("from Course as c join Teacher as t on c.teacherID=t.teacherID join Subject as s on c.subjectID=s.subjectID where c.level='"+c.getLevel()+"' and t.lName='"+c.getTeacherID().getlName()+"' and t.fname="+c.getTeacherID().getlName()+" s.abbrevName='"+c.getSubjectID().getAbbrevName()+"'").size() > 0)
            return true;
        return false;
    }
    
    public static boolean validateTimestamp(String t)
    {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
         
            String[] split;
            sdf.setLenient(false);
            Date d = sdf.parse(t.trim());
            
            split = t.trim().split("[/ :]");
            
            if(split[2].length() != 4)
                throw new Exception();
            
            int month = Integer.parseInt(split[0]);
            int day = Integer.parseInt(split[1]);
            int year = Integer.parseInt(split[2]);
            
            int hour = Integer.parseInt(split[3]);
            int min = Integer.parseInt(split[4]);
            
            String ampm = split[5];
            
            if(month <= 0 || month > 12)
                throw new Exception();
            if(day < 1 || day > 31)
                throw new Exception();
            if(year < 1000 || year > 9999)
                throw new Exception();
            if(hour < 1 || hour > 12)
                throw new Exception();
            if(min < 0 || min > 60)
                throw new Exception();
            if(!ampm.equalsIgnoreCase("am") && !ampm.equalsIgnoreCase("pm"))
                throw new Exception();
            //Calendar c = Calendar.getInstance();
            //c.setTimeInMillis(t.getTime());
            
        } catch (Exception e) {
            
            return false;
        }
        return true;
    }
    
    public boolean validateSession(ParaprofessionalSession ps)
    {
        try
        {
        ArrayList<Subject> subjects =(ArrayList<Subject>) HibernateTest.select("from Subject as s where s.abbrevName='"+courseCombo.getSelectedItem().toString()+"'");
        
        String[] tName = teacherCombo.getSelectedItem().toString().split(" ");
        String[] pName = paraprofessionalCombo.getSelectedItem().toString().split(" ");
        String[] cName = creatorCombo.getSelectedItem().toString().split(" ");
        String clientFName = fnameCombo.getSelectedItem().toString();
        String clientLName = lnameCombo.getSelectedItem().toString();
        boolean GC = gcCheck.isSelected();
        String notes = notesField.getText().toString();
        String level = levelCombo.getSelectedItem().toString();
        int intLevel = Integer.parseInt(level);
        String location = locationCombo.getSelectedItem().toString();
        boolean hasSessionStart = Validate.validateTimestamp(sessionstartField.getText().trim());
        boolean hasSessionEnd = Validate.validateTimestamp(sessionendField.getText().trim());
        Timestamp sessionStart = null;
        Timestamp sessionEnd = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
        if(hasSessionStart)
            sessionStart = new Timestamp(sdf.parse(sessionstartField.getText().trim()).getTime());
        if(hasSessionEnd)
            sessionEnd = new Timestamp(sdf.parse(sessionendField.getText().trim()).getTime());
        
        boolean walkout = walkoutCheck.isSelected();
            
       // String term = jComboBoxTerm.getSelectedItem().toString();
        
        ArrayList<Teacher> teachers = (ArrayList<Teacher>)HibernateTest.select("from Teacher as t where t.fName='"+tName[0].trim()+"' and t.lName='"+tName[1].trim()+"'");
       
        ArrayList<Course> courses = (ArrayList<Course>)HibernateTest.select("from Course as c where c.subjectID="+subjects.get(0).getSubjectID()+" and c.teacherID="+teachers.get(0).getTeacherID() + " and c.level="+intLevel);
        
        ArrayList<Paraprofessional> paraprofessionals = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p where p.fName='"+pName[0].trim()+"' and p.lName='"+pName[1].trim()+"'");

        ArrayList<Paraprofessional> creators = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p where p.fName='"+cName[0].trim()+"' and p.lName='"+cName[1].trim()+"'");

        ArrayList<Client> clients = (ArrayList<Client>)HibernateTest.select("from Client as c where c.fName='"+clientFName.trim()+"' and c.lName='"+clientLName.trim()+"'");

        ArrayList<Location> locations = (ArrayList<Location>)HibernateTest.select("from Location as l where l.name='"+location.trim()+"'");
        
      //  ArrayList<Term> terms = (ArrayList<Term>)HibernateTest.select("from Term as t where t.name='"+term.trim()+"'");

        
        if(subjects.size() <= 0)
            System.out.println("Subjects less than 1");
        if(teachers.size() <= 0)
            System.out.println("Teachers less than 1");
        if(courses.size() <= 0)
            System.out.println("Courses less than 1");
        if(paraprofessionals.size() <= 0)
            System.out.println("Paraprofessionals less than 1");
        if(creators.size() <= 0)
            System.out.println("Creators less than 1");
        if(clients.size() <= 0)
            System.out.println("Clients less than 1");
        if(locations.size() <= 0)
            System.out.println("Locations less than 1");
      //  if(terms.size() <= 0)
      //      System.out.println("Terms less than 1");
        Timestamp now = new Timestamp((new Date()).getTime());
        
        ParaprofessionalSession ps = new ParaprofessionalSession(-1, paraprofessionals.get(0), clients.get(0), courses.get(0), locations.get(0), creators.get(0), now, sessionStart, sessionEnd, GC, notes, walkout);
        HibernateTest.create(ps);
        
        System.out.println("NOW: "+now.toString());
        String query = "from ParaprofessionalSession as ps where ps.paraprofessionalID="+paraprofessionals.get(0).getParaprofessionalID()+" and ps.clientID="+clients.get(0).getClientID()+" and ps.courseID="+courses.get(0).getCourseID()+" and ps.locationID="+locations.get(0).getLocationID()+" and ps.paraprofessionalCreatorID="+creators.get(0).getParaprofessionalID()+" and ps.timeAndDateEntered='"+now.toString()+"' and ps.sessionStart='"+sessionStart+"' and ps.sessionEnd='"+sessionEnd+"' and ps.grammarCheck='"+GC+"' and ps.notes='"+notes+"' and ps.walkout='"+walkout+"'";
        
        System.out.println(query);
        ArrayList<ParaprofessionalSession> sessions = (ArrayList<ParaprofessionalSession>)HibernateTest.select(query);
        
        if(sessions.size() <=0)
            System.out.println("SESSION WAS NOT CREATED ERROR");
        else
            System.out.println("ID: "+sessions.get(0).getParaprofessionalSessionID());
        ((SessionTableModel) sessionsTable.getModel()).addRow(sessions.get(0));
        sessionsTable.repaint();
        }
        catch(Exception e)
        {
            System.out.println("ERROR ADDING SESSION"+e.getMessage() +"\n\n");
            e.printStackTrace();
        }
    }/*
    
    public boolean validateParaprofessional(Paraprofessiona p)
    {
        if(HibernateTest.select("from Paraprofessional as p where p.fName='"+c.getfName()+"' and c.lName='"+c.getlName()+"' and c.phone='"+c.getPhone()+"' and c.email='"+c.getEmail()+"'").size() > 0)
            return true;
        return false;
    }*/
}
