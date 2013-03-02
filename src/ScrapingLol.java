
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

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
                  //System.out.println(value);
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
    }


