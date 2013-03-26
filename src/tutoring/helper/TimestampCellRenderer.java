/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.Component;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
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
            String date = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").format((Date)o);
            this.setText(date);
        }
        
        return this;
    }
    
    
    
}
