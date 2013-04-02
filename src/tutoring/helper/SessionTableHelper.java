/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.FontMetrics;
import java.awt.event.MouseWheelListener;
import java.sql.Timestamp;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Nathaniel
 */
public class SessionTableHelper 
{
    private JTable table;
    
    public SessionTableHelper(JTable table)
    {
        this.table = table;
        table.setModel(new SessionTableModel());
    }
    
    public void increaseRowHeight(int increase)
    {
        FontMetrics fm = table.getFontMetrics(table.getFont());
        int fontHeight = fm.getHeight();
        table.setRowHeight(fontHeight+increase);
    }
    
    public void allowScrollingOnTable()
    {
        JScrollPane jsp = ((JScrollPane) table.getParent().getParent());
        for (MouseWheelListener listener : jsp.getMouseWheelListeners()) 
        {
            jsp.removeMouseWheelListener(listener);
        }
    }
    
    public void fasterScrolling(int fastness)
    {
        ((JScrollPane) table.getParent().getParent()).getVerticalScrollBar().setUnitIncrement(fastness);
    }
    
    public void setTableRendersAndEditors(boolean doubleClickBringsInfoUpTop, DefaultCellEditor dce)
    {
        DefaultCellEditor singleclick = new DefaultCellEditor(new JCheckBox());
        singleclick.setClickCountToStart(2);
        // DefaultCellEditor singleclickCombo = new DefaultCellEditor(new JComboBox());
        //singleclickCombo.setClickCountToStart(2);
        //set the editor as default on every column
   
       
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
       // sessionsTable.getColumnModel().getColumn(10).setCellRenderer(new TimestampCellRenderer());

        table.setDefaultRenderer(Timestamp.class, new TimestampCellRenderer());
        table.getColumnModel().getColumn(14).setCellRenderer(new MinuteCellRenderer());
        
        if(!doubleClickBringsInfoUpTop)
        {
            table.setDefaultEditor(Timestamp.class, new TimestampCellEditor(new JTextField()));
            
            table.getColumnModel().getColumn(4).setCellEditor(new ComboBoxCellEditor(new JComboBox()));
            table.getColumnModel().getColumn(3).setCellEditor(new ComboBoxCellEditor(new JComboBox()));
            table.getColumnModel().getColumn(2).setCellEditor(new ComboBoxCellEditor(new JComboBox()));
            table.getColumnModel().getColumn(1).setCellEditor(new ComboBoxCellEditor(new JComboBox()));
            table.setDefaultEditor(Boolean.class, singleclick);
        }
        else
        {
            for(int i=0; i<table.getColumnCount(); i++)
                table.getColumnModel().getColumn(i).setCellEditor(dce);
            table.getColumnModel().getColumn(12).setCellEditor(new TimestampCellEditor(new JTextField()));
            table.getColumnModel().getColumn(13).setCellEditor(new TimestampCellEditor(new JTextField()));

        }
        


        //   sessionsTable.getColumnModel().getColumn(11).setCellEditor(new TimestampCellEditor(new JTextField()));
        //   sessionsTable.getColumnModel().getColumn(12).setCellEditor(new TimestampCellEditor(new JTextField()));
        //   sessionsTable.getColumnModel().getColumn(13).setCellEditor(new TimestampCellEditor(new JTextField()));
        //sessionsTable.getColumnModel().getColumn(11).setCellRenderer(new TimestampCellRenderer());
        //sessionsTable.getColumnModel().getColumn(12).setCellRenderer(new TimestampCellRenderer());
        // sessionsTable.getColumnModel().getColumn(13).setCellRenderer(new TimestampCellRenderer());

        //sessionsTable.getColumnModel().getColumn(10).setCellRenderer(new ButtonCellRenderer());
        //sessionsTable.getColumnModel().getColumn(11).setCellRenderer(new ButtonCellRenderer());
        //sessionsTable.getColumnModel().getColumn(9).setCellEditor(new TimestampCellEditor(new JTextField()));
        //sessionsTable.getColumnModel().getColumn(10).setCellEditor(new ButtonCellEditor(new JCheckBox()));
        //sessionsTable.getColumnModel().getColumn(11).setCellEditor(new ButtonCellEditor(new JCheckBox()));

    }
}
