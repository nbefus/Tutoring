/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import tutoring.entity.*;

/**
 *
 * @author Nathaniel
 */
public class Data 
{
    /*
    private ArrayList<Subject> subjects;// =(ArrayList<Subject>) HibernateTest.select("from Subject as s where s.abbrevName='"+jComboBoxCourse.getSelectedItem().toString()+"'");
    private ArrayList<Teacher> teachers;// = (ArrayList<Teacher>)HibernateTest.select("from Teacher as t where t.fName='"+tName[0].trim()+"' and t.lName='"+tName[1].trim()+"'");
    private ArrayList<Course> courses;// = (ArrayList<Course>)HibernateTest.select("from Course as c where c.subjectID="+subjects.get(0).getSubjectID()+" and c.teacherID="+teachers.get(0).getTeacherID() + " and c.level="+intLevel);
    private ArrayList<Paraprofessional> paraprofessionals;// = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p where p.fName='"+pName[0].trim()+"' and p.lName='"+pName[1].trim()+"'");
    private ArrayList<Paraprofessional> creators;// = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p where p.fName='"+cName[0].trim()+"' and p.lName='"+cName[1].trim()+"'");
    private ArrayList<Client> clients;// = (ArrayList<Client>)HibernateTest.select("from Client as c where c.fName='"+clientFName.trim()+"' and c.lName='"+clientLName.trim()+"'");
    private ArrayList<Location> locations;// = (ArrayList<Location>)HibernateTest.select("from Location as l where l.name='"+location.trim()+"'");
    private ArrayList<ParaprofessionalSession> paraprofessionalSessions;// = (ArrayList<ParaprofessionalSession>)HibernateTest.select(query);
    private ArrayList<Category> categories;// = (ArrayList<Client>)HibernateTest.select("from Client as c where c.fName='"+clientFName.trim()+"' and c.lName='"+clientLName.trim()+"'");
    private ArrayList<Role> roles;// = (ArrayList<Client>)HibernateTest.select("from Client as c where c.fName='"+clientFName.trim()+"' and c.lName='"+clientLName.trim()+"'");
    private ArrayList<ParaprofessionalCategory> paraprofessionalCategories;// = (ArrayList<Client>)HibernateTest.select("from Client as c where c.fName='"+clientFName.trim()+"' and c.lName='"+clientLName.trim()+"'");
    private ArrayList<User> users;// = (ArrayList<Client>)HibernateTest.select("from Client as c where c.fName='"+clientFName.trim()+"' and c.lName='"+clientLName.trim()+"'");
*/
    
       private static ArrayList<Client> clientFirst;// = (ArrayList<Client>)HibernateTest.select("from Client as c order by c.fName");
       private static ArrayList<Client> clientLast;// = (ArrayList<Client>)HibernateTest.select("from Client as c order by c.lName");
       private static ArrayList<Client> clientPhone;// = (ArrayList<Client>)HibernateTest.select("from Client as c order by c.phone");
       private static ArrayList<Client> clientEmail;// = (ArrayList<Client>)HibernateTest.select("from Client as c order by c.email");
       
       private static ArrayList<String> clientsfirst;// = new ArrayList<String>();
       private static ArrayList<String> clientslast;// = new ArrayList<String>();
       private static ArrayList<String> clientsphone;// = new ArrayList<String>();
       private static ArrayList<String> clientsemail;// = new ArrayList<String>();
       
       private static ArrayList<Location> locations;// = (ArrayList<Location>)HibernateTest.select("from Location as l order by l.name");
       private static ArrayList<Paraprofessional> tutors;// = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p order by p.fName");
       private static ArrayList<Subject> subjects;// = (ArrayList<Subject>)HibernateTest.select("from Subject as s order by s.abbrevName");
       private static ArrayList<Teacher> teacherFirst;// = (ArrayList<Teacher>)HibernateTest.select("from Teacher as t order by t.fName");
       private static ArrayList<Category> categories;// = (ArrayList<Category>)HibernateTest.select("from Category as c order by c.name");
       private static ArrayList<Course> levels;// = (ArrayList<Course>)HibernateTest.select("from Course as c order by c.level");
       
       private static ArrayList<Teacher> teacherLast;
       private static ArrayList<Role> roles;
       
       private static ArrayList<Paraprofessional> tutorFirst;
       private static ArrayList<Paraprofessional> tutorLast;
       
       private static ArrayList<String> locationslist;// = new ArrayList<String>();
       private static  ArrayList<String> tutorslist;// = new ArrayList<String>();
       private static ArrayList<String> teacherslist;// = new ArrayList<String>();
       private static ArrayList<String> subjectslist;// = new ArrayList<String>();
       private static ArrayList<String> categorieslist;// = new ArrayList<String>();
       private static ArrayList<String> levelslist;// = new ArrayList<String>();

   
       private static ArrayList<String> teacherfirstlist;
       private static ArrayList<String> teacherlastlist;
       private static ArrayList<String> tutorsfirstlist;
       private static ArrayList<String> tutorslastlist;
       private static ArrayList<String> roleslist;
       
       private static ArrayList<String> multicategorylist;

    
       
    public Data(boolean initializeAll)
    {
       clientFirst = (ArrayList<Client>)HibernateTest.select("from Client as c order by c.fName");
       clientLast = (ArrayList<Client>)HibernateTest.select("from Client as c order by c.lName");
       clientPhone = (ArrayList<Client>)HibernateTest.select("from Client as c order by c.phone");
       clientEmail = (ArrayList<Client>)HibernateTest.select("from Client as c order by c.email");
       
       clientsfirst = new ArrayList<String>();
       clientslast = new ArrayList<String>();
       clientsphone = new ArrayList<String>();
       clientsemail = new ArrayList<String>();
       
       locations = (ArrayList<Location>)HibernateTest.select("from Location as l order by l.name");
       tutors = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p order by p.fName");
       subjects = (ArrayList<Subject>)HibernateTest.select("from Subject as s order by s.abbrevName");
       teacherFirst = (ArrayList<Teacher>)HibernateTest.select("from Teacher as t order by t.fName");
       teacherLast = (ArrayList<Teacher>)HibernateTest.select("from Teacher as t order by t.lName");
       categories = (ArrayList<Category>)HibernateTest.select("from Category as c order by c.name");
       levels = (ArrayList<Course>)HibernateTest.select("from Course as c order by c.level");
       
       tutorFirst = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as t order by t.fName");
       tutorLast = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as t order by t.lName");
       
       locationslist = new ArrayList<String>();
       tutorslist = new ArrayList<String>();
       teacherslist = new ArrayList<String>();
       subjectslist = new ArrayList<String>();
       categorieslist = new ArrayList<String>();
       levelslist = new ArrayList<String>();
       teacherlastlist = new ArrayList<String>();
       teacherfirstlist = new ArrayList<String>();
       roleslist = new ArrayList<String>();
       
       tutorsfirstlist = new ArrayList<String>();
       tutorslastlist = new ArrayList<String>();
       
       multicategorylist = new ArrayList<String>();
       
       roles = (ArrayList<Role>)HibernateTest.select("from Role as r order by r.type");

       for(int i=0; i<clientFirst.size(); i++)
           clientsfirst.add(clientFirst.get(i).getfName());
           
       for(int i=0; i<clientLast.size(); i++)
            clientslast.add(clientLast.get(i).getlName());
       
       for(int i=0; i<tutorFirst.size(); i++)
           tutorsfirstlist.add(tutorFirst.get(i).getfName());
           
       for(int i=0; i<tutorLast.size(); i++)
            tutorslastlist.add(tutorLast.get(i).getlName());
       
       
       for(int i=0; i<clientPhone.size(); i++)
            clientsphone.add(clientPhone.get(i).getPhone()+"");
       
       for(int i=0; i<clientEmail.size(); i++)
            clientsemail.add(clientEmail.get(i).getEmail());

       
       
       for(int i=0; i<locations.size(); i++)
           locationslist.add(locations.get(i).getName());
       
       for(int i=0; i<tutors.size(); i++)
           tutorslist.add(tutors.get(i).getfName()+" "+tutors.get(i).getlName());
       
       
       for(int i=0; i<levels.size(); i++)
          levelslist.add(levels.get(i).getLevel()+"");
       
       
       for(int i=0; i<categories.size(); i++)
           categorieslist.add(categories.get(i).getName());
       
       
       
       for(int i=0; i<teacherFirst.size(); i++)
           teacherslist.add(teacherFirst.get(i).getfName()+" "+teacherFirst.get(i).getlName());
       
       for(int i=0; i<teacherFirst.size(); i++)
           teacherfirstlist.add(teacherFirst.get(i).getfName());
       
       for(int i=0; i<teacherLast.size(); i++)
           teacherlastlist.add(teacherLast.get(i).getlName());
       
       for(int i=0; i<roles.size(); i++)
           roleslist.add(roles.get(i).getType());
       
       
        
       for(int i=0; i<subjects.size(); i++)
           subjectslist.add(subjects.get(i).getAbbrevName());
       
       multicategorylist = createMultiCat(categorieslist.size(), null);
       /*
        if(initializeAll)
        {
            subjects =(ArrayList<Subject>) HibernateTest.select("from Subject");
            teachers = (ArrayList<Teacher>)HibernateTest.select("from Teacher");
            courses = (ArrayList<Course>)HibernateTest.select("from Course");
            paraprofessionals = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional");
            creators = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional");
            clients = (ArrayList<Client>)HibernateTest.select("from Client");
            locations = (ArrayList<Location>)HibernateTest.select("from Location");
            paraprofessionalSessions = (ArrayList<ParaprofessionalSession>)HibernateTest.select("from ParaprofessionalSession");
            categories = (ArrayList<Category>)HibernateTest.select("from Category");
            roles = (ArrayList<Role>)HibernateTest.select("from Role");
            paraprofessionalCategories = (ArrayList<ParaprofessionalCategory>)HibernateTest.select("from ParaprofessionalCategory");
            users = (ArrayList<User>)HibernateTest.select("from User");
        }*/
    }
    
    public boolean checkCourse(Course c)
    {
        ArrayList<Subject> csubjects =(ArrayList<Subject>) HibernateTest.select("from Subject as s where s.abbrevName='"+c.getSubjectID().getAbbrevName().trim()+"'");

        ArrayList<Teacher> cteachers = (ArrayList<Teacher>)HibernateTest.select("from Teacher as t where t.fName='"+c.getTeacherID().getfName().trim()+"' and t.lName='"+c.getTeacherID().getlName().trim()+"'");
       
        if(cteachers.size() < 1)
        {
            // teacher doesn't exist
        }
        else if (csubjects.size() < 1)
        {
            // subject doesn't exist
        }
        else
        {
            ArrayList<Course> ccourses = (ArrayList<Course>)HibernateTest.select("from Course as c where c.subjectID="+csubjects.get(0).getSubjectID() +" and c.teacherID="+cteachers.get(0).getTeacherID() + " and c.level="+c.getLevel());
            if(ccourses.size() < 1)
            {
                // course doesn't exist
            }
        }

        // Update database with new Course??

        return true;
    }
    
    /*
    public boolean checkClient(Client c)
    {
        ArrayList<Client> cclients =(ArrayList<Client>) HibernateTest.select("from Client as c where c.fName='"+c.getfName().trim()+"' and c.lName='"+c.getlName().trim()+"'");

        //ArrayList<Teacher> cteachers = (ArrayList<Teacher>)HibernateTest.select("from Teacher as t where t.fName='"+c.getTeacherID().getfName().trim()+"' and t.lName='"+c.getTeacherID().getlName().trim()+"'");
       
        if(cclients.size() < 1)
        {
            // teacher doesn't exist
        }
        else if (csubjects.size() < 1)
        {
            // subject doesn't exist
        }
        else
        {
            ArrayList<Course> ccourses = (ArrayList<Course>)HibernateTest.select("from Course as c where c.subjectID="+csubjects.get(0).getSubjectID() +" and c.teacherID="+cteachers.get(0).getTeacherID() + " and c.level="+c.getLevel());
            if(ccourses.size() < 1)
            {
                // course doesn't exist
            }
        }

        // Update database with new Course??

        return true;
    }*/
    
    public static ArrayList<String> createMultiCat(int n,ArrayList<String> ps)
    {
        if(n<0)
        {
            return null;
        }
        
        if(n==0)
        {
            if(ps==null)
                ps=new ArrayList();
            ps.add(" ");
            return ps;
        }
        
        ps=createMultiCat(n-1, ps);
        
        ArrayList<String> tmp=new ArrayList<String>();
        
        for(String s:ps)
        {
            
            if(s.equals(" "))
                tmp.add(categorieslist.get(n-1));
            else
                tmp.add(s+" "+categorieslist.get(n-1));
        }
        
        ps.addAll(tmp);
        return ps;
    }
    
    public ParaprofessionalSession checkValidSession(ParaprofessionalSession ps)
    {
        ArrayList<Subject> csubjects =(ArrayList<Subject>) HibernateTest.select("from Subject as s where s.abbrevName='"+ps.getCourseID().getSubjectID().getAbbrevName().trim()+"'");

        ArrayList<Teacher> cteachers = (ArrayList<Teacher>)HibernateTest.select("from Teacher as t where t.fName='"+ps.getCourseID().getTeacherID().getfName().trim()+"' and t.lName='"+ps.getCourseID().getTeacherID().getlName().trim()+"'");
       
        ArrayList<Course> ccourses = (ArrayList<Course>)HibernateTest.select("from Course as c where c.subjectID="+ps.getCourseID().getSubjectID().getSubjectID()+" and c.teacherID="+ps.getCourseID().getTeacherID().getTeacherID() + " and c.level="+ps.getCourseID().getLevel());
        
        ArrayList<Paraprofessional> cparaprofessionals = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p where p.fName='"+ps.getParaprofessionalID().getfName().trim()+"' and p.lName='"+ps.getParaprofessionalID().getlName().trim().trim()+"'");

        ArrayList<Paraprofessional> ccreators = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p where p.fName='"+ps.getParaprofessionalCreatorID().getfName().trim()+"' and p.lName='"+ps.getParaprofessionalCreatorID().getlName().trim()+"'");

        ArrayList<Client> cclients = (ArrayList<Client>)HibernateTest.select("from Client as c where c.fName='"+ps.getClientID().getfName().trim()+"' and c.lName='"+ps.getClientID().getlName().trim()+"'");

        ArrayList<Location> clocations = (ArrayList<Location>)HibernateTest.select("from Location as l where l.name='"+ps.getLocationID().getName().trim()+"'");
        
        if(csubjects.size() <= 0)
        {
            System.out.println("Subjects less than 1");
            return null;
        }
        if(cteachers.size() <= 0)
        {
            System.out.println("Teachers less than 1");
            return null;
        }
        if(ccourses.size() <= 0)
        {
            System.out.println("Courses less than 1");
            return null;
        }
        if(cparaprofessionals.size() <= 0)
        {
            System.out.println("Paraprofessionals less than 1");
            return null;
        }
        if(ccreators.size() <= 0)
        {
            System.out.println("Creators less than 1");
            return null;
        }
        if(cclients.size() <= 0)
        {
            System.out.println("Clients less than 1");
            return null;
        }
        if(clocations.size() <= 0)
        {
            System.out.println("Locations less than 1");
            return null;
        }
        
        Timestamp now = new Timestamp((new Date()).getTime());
        
        ParaprofessionalSession paraSession = new ParaprofessionalSession(-1, cparaprofessionals.get(0), cclients.get(0), ccourses.get(0), clocations.get(0), ccreators.get(0), now, null, null, ps.isGrammarCheck(), ps.getNotes(), false);

        return paraSession;
    }
    
    public void updateSubjectsFromID(int id)
    {
       ArrayList<Subject> updatedSubjects =(ArrayList<Subject>) HibernateTest.select("from Subject as s where s.subjectID > "+id);
       for(int i=0; i<updatedSubjects.size(); i++)
           getSubjects().add(updatedSubjects.get(i));
    }
    
    /*
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Paraprofessional> getParaprofessionals() {
        return paraprofessionals;
    }

    public ArrayList<Paraprofessional> getCreators() {
        return creators;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public ArrayList<ParaprofessionalSession> getParaprofessionalSessions() {
        return paraprofessionalSessions;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public ArrayList<ParaprofessionalCategory> getParaprofessionalCategories() {
        return paraprofessionalCategories;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    */

    public static ArrayList<Client> getClientFirst() {
        return clientFirst;
    }


    public static ArrayList<Client> getClientLast() {
        return clientLast;
    }

    public static ArrayList<Client> getClientPhone() {
        return clientPhone;
    }

    public static ArrayList<Client> getClientEmail() {
        return clientEmail;
    }

    public static ArrayList<String> getClientsfirst() {
        return clientsfirst;
    }

    public static ArrayList<String> getClientslast() {
        return clientslast;
    }

    public static ArrayList<String> getClientsphone() {
        return clientsphone;
    }

    public static ArrayList<String> getClientsemail() {
        return clientsemail;
    }
    
     public static ArrayList<Location> getLocations() {
        return locations;
    }

    public static ArrayList<Paraprofessional> getTutors() {
        return tutors;
    }

    public static ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public static ArrayList<Teacher> getTeacherFirst() {
        return teacherFirst;
    }

    public static ArrayList<Category> getCategories() {
        return categories;
    }

    public static ArrayList<Course> getLevels() {
        return levels;
    }

    public static ArrayList<String> getLocationslist() {
        return locationslist;
    }

    public static ArrayList<String> getTutorslist() {
        return tutorslist;
    }

    public static ArrayList<String> getTeacherslist() {
        return teacherslist;
    }

    public static ArrayList<String> getSubjectslist() {
        return subjectslist;
    }

    public static ArrayList<String> getCategorieslist() {
        return categorieslist;
    }

    public static ArrayList<String> getLevelslist() {
        return levelslist;
    }

    /**
     * @return the teacherfirstlist
     */
    public static ArrayList<String> getTeacherfirstlist() {
        return teacherfirstlist;
    }

    /**
     * @return the teacherlastlist
     */
    public static ArrayList<String> getTeacherlastlist() {
        return teacherlastlist;
    }

    /**
     * @return the roleslist
     */
    public static ArrayList<String> getRoleslist() {
        return roleslist;
    }
    /**
     * @return the multicategorylist
     */
    public static ArrayList<String> getMulticategorylist() {
        return multicategorylist;
    }

    /**
     * @return the tutorsfirstlist
     */
    public static ArrayList<String> getTutorsfirstlist() {
        return tutorsfirstlist;
    }

    /**
     * @return the tutorslastlist
     */
    public static ArrayList<String> getTutorslastlist() {
        return tutorslastlist;
    }

}
