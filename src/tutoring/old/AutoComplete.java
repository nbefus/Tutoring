package tutoring.old;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dabeefinator
 */

public class AutoComplete implements KeyListener, ListSelectionListener, MouseListener
{
    private String[] keywords;// = {"hey", "How are you","howdy","hinting","hinter","hunter"};
    private String entry = "";
    private boolean isUpdating = false;
    private boolean zeroIndexSel = false;
    private JTextField jTextField1;
    private JList jList1;
    private JScrollPane jScrollPane1;
    private String originalText = "";
    
    public AutoComplete(JList jl, JTextField jtf, JScrollPane jsp, String[] keywords)
    {
        this.keywords = keywords;
        Arrays.sort(keywords);
        jTextField1 = jtf;
        jScrollPane1 = jsp;
        jTextField1.addKeyListener(AutoComplete.this);
        jList1 = jl;
        jList1.addKeyListener(AutoComplete.this);
        jList1.addListSelectionListener(AutoComplete.this);
        jList1.addMouseListener(AutoComplete.this);
        
    }
    
    public void setList(JList l, ArrayList<String> s)
    {
        //s.addAll(Arrays.asList(keywords));
        l.setListData(keywords);
        
    }
    
    public void updatelist(JList l, JTextField t)
    {
        String text = t.getText();
        ArrayList<String> matches = new ArrayList<String>();
        //vect = new Vector<String>();
        for(int i=0; i<keywords.length; i++)
        {
            if(keywords[i].toUpperCase().contains(text.toUpperCase()))
            {
                matches.add(keywords[i]);
            }
        }
        
        l.setListData(matches.toArray());
        
        
    }
    //test
    public String getEntry()
    {
        return entry;
    }

    

    @Override
    public void keyReleased(KeyEvent evt) 
    {
        System.out.println("TESTING KEY RELEASED");
        if(evt.getSource() == jTextField1)
        {
            if (evt.getKeyCode() == KeyEvent.VK_DOWN)
            {
                isUpdating = true;
                zeroIndexSel = true;

                originalText = jTextField1.getText();
                jList1.requestFocusInWindow();
                jList1.setSelectedIndex(0);

            }
            if(!jTextField1.getText().equals("") && !isUpdating)
            {
                jScrollPane1.setVisible(true);
                jList1.setVisible(true);
                jScrollPane1.setBorder(BorderFactory.createCompoundBorder());
                updatelist(jList1, jTextField1);
                int size = jList1.getModel().getSize();
                if(size == 0)
                {
                    jScrollPane1.setVisible(false);
                    jList1.setVisible(false);
                    jScrollPane1.setBorder(null);

                }
                else
                {
                    /*set size of dropdown, not currently working
                    if(size < 6)
                    {
                        jList1.setVisibleRowCount(size);
                    }
                    else
                    {
                        jList1.setVisibleRowCount(5);
                    }*/
                }
            }
            else
            {
                if(!isUpdating)
                {
                jList1.setVisible(false);
                jScrollPane1.setBorder(null);
                jScrollPane1.setVisible(false);
                }
            }
        }
        else if(evt.getSource() == jList1)
        {
            if (evt.getKeyCode() == KeyEvent.VK_UP && zeroIndexSel)
            {

                jTextField1.setText(originalText);
                isUpdating=false;
                jList1.clearSelection();
                jTextField1.requestFocusInWindow();
            }
            else if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            {
                jTextField1.setText(jList1.getSelectedValue().toString());
                isUpdating=false;
                jList1.setVisible(false);
                jScrollPane1.setVisible(false);
                jScrollPane1.setBorder(null);
                jTextField1.requestFocusInWindow();
            }
            else if(evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN)
            {
                if(Character.isLetter(evt.getKeyChar()) || Character.isDigit(evt.getKeyChar()) || evt.getKeyChar() == '.' || evt.getKeyChar() == '\'')
                {
                    jTextField1.setText(originalText + evt.getKeyChar());
                }
                else
                {
                    jTextField1.setText(originalText);
                }

                isUpdating=false;
                jList1.clearSelection();
                jTextField1.requestFocusInWindow();
            }

            if(jList1.getSelectedIndex() == 0)
            {
                zeroIndexSel = true;
            }
            else
            {
                zeroIndexSel = false;
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent evt) {
        System.out.println("TESTING VALUE CHANGED");
        if(evt.getSource() == jList1)
        {
            if(!jList1.isSelectionEmpty() && jList1.getSelectedValue().toString() != null && !jList1.getSelectedValue().toString().equals(""))
            {
                //jTextField1.setSelectionStart(WIDTH);
                jTextField1.setText(jList1.getSelectedValue().toString());
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        System.out.println("TESTING MOUSE CLICKED");
        if(evt.getSource()==jList1)
        {
            JList list = (JList)evt.getSource();
            int index = list.locationToIndex(evt.getPoint());

            jTextField1.setText(jList1.getModel().getElementAt(index).toString());
            isUpdating=false;
            jList1.setVisible(false);
            jScrollPane1.setVisible(false);
            jScrollPane1.setBorder(null);
            jTextField1.requestFocusInWindow();
        }
    }

    
    
    
    @Override
    public void keyTyped(KeyEvent ke) {}
    
    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void keyPressed(KeyEvent ke) {}
}
