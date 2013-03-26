/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.Component;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author dabeefinator
 */
public class TimestampCellEditor extends DefaultCellEditor
{
    final JTextField jf;
    
    public TimestampCellEditor(final JTextField jtf)
    {
        super(jtf);
        jf = jtf;
        editorComponent = jtf;
        this.clickCountToStart = 2;
        
        delegate = new EditorDelegate()
        {
            @Override
            public void setValue(Object value)
            {
               System.out.println("SET VALUE: "+value.toString() + " begin: "+jtf.getText());
                //if()
                /*
                  Timestamp t;
                String sd = "";
                try {
                    t = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(jtf.getText()).getTime());
                    System.out.println("EDITING: "+t.toString());
                    Date d = new Date(t.getTime());
                    sd = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").format(d);
                    System.out.println("ED: "+sd);
                } catch (ParseException ex) {
                    Logger.getLogger(TimestampCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                
                
                //jtf.setText(value.toString());
               // jtf.setText(sd);
            }
            
            @Override
            public Object getCellEditorValue()
            {
                Timestamp t;
                String sd = "";
                try {
                    t = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(jtf.getText()).getTime());
                    System.out.println("EDITING: "+t.toString());
                    Date d = new Date(t.getTime());
                    sd = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").format(d);
                    System.out.println("ED: "+sd);
                } catch (ParseException ex) {
                    Logger.getLogger(TimestampCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                return "HEY";
            }
        };
        
        jtf.addActionListener(delegate);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
                Timestamp t;
                String sd = "";
                try {
                    t = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(value.toString()).getTime());
                    System.out.println("EDITING: "+t.toString());
                    Date d = new Date(t.getTime());
                    sd = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").format(d);
                    System.out.println("ED: "+sd);
                } catch (ParseException ex) {
                    Logger.getLogger(TimestampCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                //jtf.setText(value.toString());
                jf.setText(sd);
                
                
            //jf.setText("HEY IM HERE");
            return jf;
        }
}