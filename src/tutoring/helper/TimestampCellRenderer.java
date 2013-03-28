/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.Color;
import java.awt.Component;
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
            if(((Timestamp)o).equals(Timestamp.valueOf("9999-12-31 12:00:00")))
            {
                System.out.println("here here");
                if(c == 10)
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
                    
                else if(c == 11)
                {
                    System.out.println("WORKING");
                    
                    this.setText("");
                    if(isSelected)
                    {
                        String cell;// = t.getValueAt(r, c-1).toString();
                        //System.out.println("CELL: "+cell);
                        if(((Timestamp)t.getValueAt(r, c-1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")))
                        {
                            setForeground(t.getSelectionForeground());
                            setBackground(Color.lightGray);
                            setBorder(new MatteBorder(3,3,3,3,Color.black));
                        }
                        else
                        {
                            setForeground(t.getSelectionForeground());
                            setBackground(Color.red);
                            setBorder(new MatteBorder(3,3,3,3,Color.black));
                        }
                    }
                    else
                    {
                        String cell;// = t.getValueAt(r, c-1).toString();
                        //System.out.println("CELL: "+cell);
                        if(((Timestamp)t.getValueAt(r, c-1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")))
                        {
                            setForeground(t.getSelectionForeground());
                            setBackground(Color.lightGray);
                            setBorder(new MatteBorder(3,3,3,3,Color.black));
                        }
                        else
                        {
                            setForeground(t.getSelectionForeground());
                            setBackground(Color.red);
                            setBorder(new MatteBorder(3,3,3,3,Color.black));
                        }
                    }
                }
                else
                {
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
        
        
        
        return this;
    }
    
    
    
}
