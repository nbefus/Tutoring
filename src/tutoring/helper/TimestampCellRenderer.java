/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author dabeefinator
 */
public class TimestampCellRenderer extends DefaultTableCellRenderer
{
    public TimestampCellRenderer()
    {
        
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable t, Object o, boolean isSelected, boolean hasFocus, int r, int c)
    {
        super.getTableCellRendererComponent(t, o, isSelected, hasFocus, r, c);
        if(o instanceof Timestamp)
        {
            System.out.println("THE OVERALL TIME TIME TIME IS "+((Timestamp)o).toString());
            if(((Timestamp)o).equals(Timestamp.valueOf("9999-12-31 12:00:00")))
            {
                System.out.println("here here");
                if(c == 12)
                {
                    //System.out.println("WORKING");
                    
                    this.setText("Start");
                    
                    if(isSelected)
                    {
                        setForeground(t.getSelectionForeground());
                        setBackground(Color.green);
                        setBorder(new MatteBorder(3,3,3,3,Color.black));
                    }
                    else
                    {    
                        setForeground(t.getForeground());
                        setBackground(Color.green);
                        setBorder(new MatteBorder(3,3,3,3,Color.black));
                    }
                }
                    
                else if(c == 13)
                {
                    System.out.println("WORKING");
                    
                    this.setText("");
                    System.out.println("THIS THIS TIME IS "+t.getValueAt(r, c).toString());
                    if(isSelected)
                    {
                        String cell;// = t.getValueAt(r, c-1).toString();
                        System.out.println("TIME IS "+t.getValueAt(r, c-1).toString());
                        if(((Timestamp)t.getValueAt(r, c-1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")))
                        {
                            setForeground(t.getSelectionForeground());
                            setBackground(Color.lightGray);
                            setBorder(new MatteBorder(3,3,3,3,Color.black));
                        }
                        else
                        {
                            System.out.println("here under selected");
                            setForeground(t.getSelectionForeground());
                            setBackground(Color.red);
                            setBorder(new MatteBorder(3,3,3,3,Color.black));
                        }
                    }
                    else
                    {
                        String cell;// = t.getValueAt(r, c-1).toString();
                        //System.out.println("CELL: "+cell);
                        System.out.println("TIME IS "+t.getValueAt(r, c-1).toString());
                        if(((Timestamp)t.getValueAt(r, c-1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")))
                        {
                            setForeground(t.getSelectionForeground());
                            setBackground(Color.lightGray);
                            setBorder(new MatteBorder(3,3,3,3,Color.black));
                        }
                        else
                        {
                            System.out.println("here under not selected");
                            setForeground(t.getSelectionForeground());
                            setBackground(Color.red);
                            setBorder(new MatteBorder(3,3,3,3,Color.black));
                        }
                    }
                }
                else
                {
                    System.out.println("HERE IS THE BAD PART");
                    if(isSelected)
                    {
                        setForeground(t.getSelectionForeground());
                        setBackground(t.getSelectionBackground());
                    }
                    else
                    {
                        setForeground(t.getForeground());
                        setBackground(t.getBackground());
                    }
                }
            }
            else
            {
                System.out.println("getTableCellRendererComponent");
                String date = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").format((Date)o);
                this.setText(date);
                if(isSelected)
                {
                    setForeground(t.getSelectionForeground());
                    setBackground(t.getSelectionBackground());
                }
                else
                {
                    setForeground(t.getForeground());
                    setBackground(t.getBackground());
                }
            }
        }
        else
        {
            if(o != null)
                System.out.println(o.toString() + " value");
            else
                System.out.println("NULL NULL");
            this.setText("STOP");
            setForeground(t.getSelectionForeground());
            setBackground(Color.red);
            setBorder(new MatteBorder(3,3,3,3,Color.black));
        }
        
        
        this.setFont(new Font("Arial", Font.PLAIN, 10));
        
        return this;
    }
    
    
    
}
