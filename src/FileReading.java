
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dabeefinator
 */
public class FileReading {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException 
    {
       Scanner scan = new Scanner(new File("Hawaii Pacific University.html"));
       boolean scrapping = false;
       while(scan.hasNext())
       {
           
           String line = scan.nextLine();
           if(line.contains("searchResult"))
           {
               scrapping = true;
           }
           
           if(scrapping)
           {
               int count =0;
               if(line.contains("tr"))
               {
                   line = scan.nextLine();
                   //String[] data = line.split("")
               }
  
           }
           
       }
        
    }
    
    public static void instructorsAndCourses() throws FileNotFoundException
    {
        Scanner scan = new Scanner(new File("Spring2013.txt"));
       while(scan.hasNext())
       {
           
           String[] data = scan.nextLine().split("\t");
           
           for(int i=0; i<data.length; i++)
               System.out.println(data[i]);
       }
        
    }
}
