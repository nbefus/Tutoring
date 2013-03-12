
import tutoring.entity.HibernateTest;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tutoring.entity.Category;
import tutoring.entity.Course;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.entity.User;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nathaniel
 */
public class ScrapingLol {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
      
        String[] terms = {"201320"};//,"201310","201295","201290","201280"};
        updateCourses(terms);
      //  HibernateTest.select("from User");
        
   }
    
    public static void findDropDownValues() throws Exception
    {
        URL url = new URL("http://apps.hpu.edu/cis/web/index.php/search");
      BufferedReader reader = new BufferedReader
      (new InputStreamReader(url.openStream()));
      BufferedWriter writer = new BufferedWriter
      (new FileWriter("data.txt"));
      String line;
      boolean scrapping = false;
      int dropbox = -1;
      Object[] box = new Object[3];
      
      
      while ((line = reader.readLine()) != null) {
         // ArrayList<String> als = new ArrayList<String>();
          if(line.contains("select name=\"")&& dropbox < 3)
          {
              System.out.println("Setting array list");
              scrapping = true;
              dropbox++;
             // als = new ArrayList<String>();
          }
          if(line.contains("/select")&& dropbox < 3)
          {
              scrapping = false;
            //  box[dropbox]=als;
            //  als.clear();
          }
          if(scrapping && dropbox < 3)
          {
              if(line.contains("option"))
              {
                  //System.out.println(line.substring(line.indexOf(">")+1,line.lastIndexOf("<")));
                  String value = line.substring(line.indexOf(">")+1,line.lastIndexOf("<"));
                  System.out.println(value);
                  if(value != null && !value.contains("DO NOT USE") && !value.equals(""))
                  {     writer.write(value);
                        writer.newLine();
                  }
                  
              }
          }
         //System.out.println(line.trim());
         
      }
      reader.close();
      writer.close();
      /*
      for(int i=0; i<box.length; i++)
      {
          System.out.println("I: "+i);
          ArrayList<String> val = ((ArrayList<String>) box[i]);
          for(int j=0; j<val.size(); j++)
          {
              System.out.println("J: "+j);
              System.out.println(val.get(j));
          }
      }*/
    }
    
    private static int containsTeacher(ArrayList<Teacher> teachers, Teacher t)
    {
        for(int i=0; i<teachers.size(); i++)
        {
            if(teachers.get(i).equals(t))
                return i;
        }
        return -1;
    }
    
    private static int containsSubject(ArrayList<Subject> subjects, Subject t)
    {
        for(int i=0; i<subjects.size(); i++)
        {
            if(subjects.get(i).equals(t))
                return i;
        }
        return -1;
    }
    
    
    private static int containsCourse(ArrayList<Course> courses, Course t)
    {
        for(int i=0; i<courses.size(); i++)
        {
            if(courses.get(i).equals(t))
                return i;
        }
        return -1;
    }
    
    
    public static void updateCourses(String[] termCodes) throws Exception
    {
        
        
        
        ArrayList<Subject> subjects =(ArrayList<Subject>) HibernateTest.select("from Subject");
        ArrayList<Teacher> teachers = (ArrayList<Teacher>)HibernateTest.select("from Teacher");
        ArrayList<Course> courses = (ArrayList<Course>)HibernateTest.select("from Course");
        ArrayList<Category> categories = (ArrayList<Category>)HibernateTest.select("from Category");
        
        ArrayList<Subject> newsubjects = new ArrayList<Subject>();
        ArrayList<Teacher> newteachers = new ArrayList<Teacher>();
        ArrayList<Course> newcourses = new ArrayList<Course>();
        //ArrayList<Category> newcategories = (ArrayList<Category>)HibernateTest.select("from Category");
        
        for(int i=0; i<termCodes.length; i++)
        {
            
            
            
            /*
            try{
            //File fXmlFile = new File("/Users/mkyong/staff.xml");
            
             URL url = new URL("http://apps.hpu.edu/cis/web/index.php/search/search?term="+termCodes[i]);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(url.openStream());
 
	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();
 
	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
 
	NodeList nList = doc.getElementsByTagName("td");
      
	System.out.println("----------------------------");
 
	for (int temp = 0; temp < nList.getLength(); temp++) {
 
		Node nNode = nList.item(temp);
 
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
			Element e = (Element) nNode;
                        System.out.println("Element:"+e.getTextContent());
			//System.out.println("Staff id : " + eElement.getAttribute("id"));
			//System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
			//System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
			//System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
			//System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
 
		}
	}
    } catch (Exception e) {
	e.printStackTrace();
    }
            
            */
            
            
             URL url = new URL("http://apps.hpu.edu/cis/web/index.php/search/search?term="+termCodes[i]);
             BufferedReader reader = new BufferedReader
             (new InputStreamReader(url.openStream()));
            
             String line;      
             boolean concat = false;
             boolean read = false;
             String content = "";
             int count =0;
             String lname="", fname="", abbrev="";
             int level=0;
             while ((line = reader.readLine()) != null) {
                // ArrayList<String> als = new ArrayList<String>();
                 
                 if(line.contains("<td>"))
                 {
                     concat=true;
                     
                     //content+=line;
                     //String value = line.substring(line.indexOf(">")+1,line.lastIndexOf("<"));

                     //System.out.println("Value: "+value);

                    // als = new ArrayList<String>();
                 }
                 if(line.contains("</td>"))
                 {
                     content+=line;
                     concat=false;
                     read = true;
                    
                 }
                 if(concat)
                 {
                     content+=line;
                 }
                 if(read)
                 {
                     String value = content.substring(content.indexOf(">")+1,content.lastIndexOf("<")).trim();
                  //   System.out.println("CONTENT: "+content);
                  //   System.out.println("Value: "+value);
                     read=false;
                     content="";
                     count++;
                     System.out.println(count);
                     if(count %5 == 2)
                     {
                         String[] values = value.split(" ");
                         abbrev=values[0];
                         level = Integer.parseInt(values[1]);
                     }
                     else if(count%5 == 4)
                     {
                         String[] values = value.split(" ");
                         fname=values[0];
                         lname=values[1];
                     }
                 }
                 
                 if(count%5 == 4)
                 {
                      Teacher t = new Teacher(-1, lname, fname);
                      Subject s = new Subject(-1, abbrev, "", categories.get(categories.size()-1));
                      Course course = new Course(t, s, level);
                      
                     int hasSubject = containsSubject(subjects, s);
                     int hasTeacher = containsTeacher(teachers, t);
                     int hasCourse = containsCourse(courses, course);
                     
                     if(hasCourse == -1)
                     {
                        if(hasTeacher == -1)
                        {
                            //HibernateTest.create(t);
                            teachers.add(t);
                            newteachers.add(t);
                            System.out.println("Inserted teacher: "+t.getfName() + " "+t.getlName());
                        }
                        else
                        {
                            t.setTeacherID(teachers.get(hasTeacher).getTeacherID());
                        }
                        
                        if(hasSubject == -1)
                        {
                           // HibernateTest.create(s);
                            subjects.add(s);
                            newsubjects.add(s);
                            System.out.println("Inserted subject: "+s.getAbbrevName());
                        }
                        else
                        {
                            s.setSubjectID(subjects.get(hasSubject).getSubjectID());
                        }
                        
                        //HibernateTest.create(course);
                        courses.add(course);
                        newcourses.add(course);
                        System.out.println("Inserted course: "+course.getLevel());
                     }
                 }
             }
             
             HibernateTest.batchCreate(newsubjects.toArray());
             HibernateTest.batchCreate(newteachers.toArray());
             HibernateTest.batchCreate(newcourses.toArray());
        }
  
      /*
      for(int i=0; i<box.length; i++)
      {
          System.out.println("I: "+i);
          ArrayList<String> val = ((ArrayList<String>) box[i]);
          for(int j=0; j<val.size(); j++)
          {
              System.out.println("J: "+j);
              System.out.println(val.get(j));
          }
      }*/
    }
}


