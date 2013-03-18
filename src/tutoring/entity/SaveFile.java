package tutoring.entity;
import  tutoring.helper.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class SaveFile extends JFrame
{
    private static List data;
    private static boolean isSaved = false;
    private static String savingFeedback = "There's is no data to be saved. File not created.";
    
    public SaveFile(List data)
    {
        this.data = data;
    }
    
    public SaveFile()
    { 
    }
    
    public boolean isSaved()
    {
        return isSaved;
    }
    
    public String savingFeedback()
    {
        return savingFeedback;
    }
    
    public void saveFile() throws FileNotFoundException
    {
        File file = null;
        FileWriter fWriter; //ask for name
        PrintWriter outputFile;
        JFileChooser dialog = new JFileChooser();
        dialog.showSaveDialog(this);
        file = dialog.getSelectedFile();
        file = file.getAbsoluteFile();
        outputFile = new PrintWriter(file);
        outputFile.println(data);
        isSaved = true;
        
    }
    
    public static void main(String args[]) throws FileNotFoundException
    {
        SaveFile save = new SaveFile((ArrayList<User>) HibernateTest.select("from Subjects"));
        save.saveFile();
    }

}
