/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseWheelListener;
import java.sql.Timestamp;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

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
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 11));
        
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
    
    public void setTableRendersAndEditors(boolean doubleClickBringsInfoUpTop, DefaultCellEditor dce, boolean showDateOnTimestamps)
    {
        DefaultCellEditor singleclick = new DefaultCellEditor(new JCheckBox());
        singleclick.setClickCountToStart(2);
        // DefaultCellEditor singleclickCombo = new DefaultCellEditor(new JComboBox());
        //singleclickCombo.setClickCountToStart(2);
        //set the editor as default on every column
   
       
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        
        table.getColumnModel().getColumn(17).setCellEditor(singleclick);
        
       // sessionsTable.getColumnModel().getColumn(10).setCellRenderer(new TimestampCellRenderer());

        table.setDefaultRenderer(Timestamp.class, new TimestampCellRenderer(showDateOnTimestamps));
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
            {
                if(i!=12 && i!=13 && i!=11 && i != 10 && i !=17)
                    table.getColumnModel().getColumn(i).setCellRenderer(new FontCellRenderer());
                if(i !=17)
                    table.getColumnModel().getColumn(i).setCellEditor(dce);
            }
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
    
    public JTable autoResizeColWidth()//, DefaultTableModel model) 
    {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //table.setModel(model);

    int margin = 5;

    for (int i = 0; i < table.getColumnCount(); i++) {
        int                     vColIndex = i;
        DefaultTableColumnModel colModel  = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn             col       = colModel.getColumn(vColIndex);
        int                     width     = 0;

        // Get width of column header
        TableCellRenderer renderer = col.getHeaderRenderer();

        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }

        Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);

        width = comp.getPreferredSize().width;

        // Get maximum width of column data
        for (int r = 0; r < table.getRowCount(); r++) {
            renderer = table.getCellRenderer(r, vColIndex);
            comp     = renderer.getTableCellRendererComponent(table, table.getValueAt(r, vColIndex), false, false,
                    r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }

        // Add margin
        width += 2 * margin;

        // Set the width
        col.setPreferredWidth(width);
    }

    ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(
        SwingConstants.LEFT);

    // table.setAutoCreateRowSorter(true);
    table.getTableHeader().setReorderingAllowed(false);

    return table;
}
}
