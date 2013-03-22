package tutoring.entity;
import  tutoring.helper.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
        
        file = dialog.getSelectedFile().getAbsoluteFile();
        
        outputFile = new PrintWriter(file);
        
        ArrayList<Subject> list = (ArrayList<Subject>) data;
        
        for (int i = 0; i < 2; i++) 
        {
            outputFile.println(list.get(i).toString());
            System.out.println(list.get(i).toString());
        }
        
        outputFile.close();

        isSaved = true;
        
    }
    
    public static void main(String args[]) throws FileNotFoundException, ClassNotFoundException
    {
        List select = HibernateTest.select("from Subject");
        SaveFile save = new SaveFile(HibernateTest.select("from Subject"));
        save.saveFile();

        
        
        
        
    }

}
