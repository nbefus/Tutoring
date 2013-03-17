package tutoring.entity;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;


public class SaveFile 
{


    public static void main(String[] args) 
    {
        //make sure the the file does not exist
        //gather data
        //choose location, press save, saves file
        //PrintWriter
        
        JFileChooser window = new JFileChooser();
        String filename = null;
        window.showSaveDialog(window);
        
        try 
        {
            PrintWriter outputFile = new PrintWriter(filename);
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(SaveFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //savesFile() returns boolean
        //setPath
        //getPath
        //setFilename
        //getFilename
        //getFeedback
        
    }
}
