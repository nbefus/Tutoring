/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.text.JTextComponent;
import tutoring.entity.Category;
import tutoring.entity.Client;
import tutoring.entity.Course;
import tutoring.entity.Paraprofessional;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;

/**
 *
 * @author Nathaniel
 */
public class UltimateAutoCompleteCourse implements KeyListener, ActionListener, MouseListener, ItemListener
{
    private ArrayList<ArrayList<String>> keywords;
    private JComboBox[] boxes;
    private boolean[] isUpdating;
    private boolean[] zeroIndexSel;
    private boolean[] firstClick;
    private int[] lastSize;
    ArrayList<Course> courses;
    ArrayList<Teacher> teachers;
    ArrayList<Subject> subjects;
    ArrayList<Category> categories;
    ArrayList<Paraprofessional> paraprofessionals;
    
    ArrayList<ArrayList<String>> matches = new ArrayList<ArrayList<String>>();
    
    private ArrayList<Integer> activeBoxIndexes = new ArrayList<Integer>();
    private ArrayList<String> activeBoxValues = new ArrayList<String>();
    
    public UltimateAutoCompleteCourse(ArrayList<ArrayList<String>>keywords, JComboBox[] boxes, ArrayList<Course> courses, ArrayList<Teacher> teachers, ArrayList<Subject> subjects, ArrayList<Category> categories, ArrayList<Paraprofessional> paraprofessionals) {
        this.keywords = keywords;
        this.boxes = boxes;
        
        this.courses = courses;
        this.teachers = teachers;
        this.subjects = subjects;
        this.categories = categories;
        this.paraprofessionals = paraprofessionals;
        
        isUpdating = new boolean[boxes.length];
        zeroIndexSel = new boolean[boxes.length];
        firstClick = new boolean[boxes.length];
        lastSize = new int[boxes.length];
        
        //this.jcb = jcb;
        
        for(int i=0; i<boxes.length; i++)
        {
           // Arrays.sort(keywords[i]);
            isUpdating[i]=false;
            zeroIndexSel[i]=false;
            firstClick[i]=true;
            lastSize[i]=0;
            boxes[i].setEditable(true);
            

            matches.add(new ArrayList<String>());
            boxes[i].getEditor().getEditorComponent().addKeyListener(this);
            boxes[i].getEditor().getEditorComponent().addMouseListener(this);

            addPopupMouseListener(boxes[i]);
            boxes[i].addActionListener(this);

            boxes[i].setMaximumRowCount(5);
            
        }
        for(int i=0; i<boxes.length; i++)
            updateList(i, false);
    } 
    
    
    
    
    
 
    
    
    
    
    /*
    
    public void updateOther(int activeBoxIndex)
    {
        if(activeBoxIndex == 0)// && !firstClick[activeBoxIndex])
        {
            System.out.println("UPDATE LAST");
            int index = keywords.get(activeBoxIndex).indexOf(((MutableComboBoxModel)boxes[activeBoxIndex].getModel()).getSelectedItem());
            if(index != -1)
            {
                String text = ((JTextComponent)boxes[1].getEditor().getEditorComponent()).getText();
                if(!text.equals(clientsFirst.get(index).getlName()+""))
                {
                    ((JTextComponent)boxes[1].getEditor().getEditorComponent()).setText(clientsFirst.get(index).getlName()+"");
                    System.out.println("UPDATED Last with "+clientsLast.get(index).getlName());
                    updatelist(1, true);
                }
                else
                    System.out.println("NO NEED");
            }
        }
        if(activeBoxIndex == 1)// && !firstClick[activeBoxIndex])
        {
            System.out.println("UPDATE FIrSt");
            int index = keywords.get(activeBoxIndex).indexOf(((MutableComboBoxModel)boxes[activeBoxIndex].getModel()).getSelectedItem());
            if(index != -1)
            {
                String text = ((JTextComponent)boxes[0].getEditor().getEditorComponent()).getText();
                if(!text.equals(clientsFirst.get(index).getfName()+""))
                {
                    ((JTextComponent)boxes[0].getEditor().getEditorComponent()).setText(clientsLast.get(index).getfName()+"");
                    System.out.println("UPDATED FIrSt with "+clientsLast.get(index).getfName());
                    updatelist(0, true);
                }
                else
                    System.out.println("NO NEED");
            }
        }
        
    }*/
    
    public void updateList(int activeBoxIndex, boolean updatedOtherBoxes)
    {
        boolean moreChars;

        String text = ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).getText();
        if(text.length() > lastSize[activeBoxIndex])
            moreChars = true;
        else
            moreChars = false;
        
        
        
        lastSize[activeBoxIndex] = text.length();
        //mcbm.
        MutableComboBoxModel mcbm = (MutableComboBoxModel)boxes[activeBoxIndex].getModel();
        
        System.out.println("TEXT IN UPDATELIST: "+text);
        
       
        //vect = new Vector<String>();
 
        if(updatedOtherBoxes)
        {

            System.out.println("MORE CHARS");
            int max = matches.get(activeBoxIndex).size();
            Object[] values = matches.get(activeBoxIndex).toArray();

            //ArrayList<Integer> indexesToRemove = new ArrayList<Integer>();
            for(int i=0; i<max; i++)
            {
                if(!((String)values[i]).toUpperCase().contains(text.toUpperCase()))
                {

                    mcbm.removeElement(((String)values[i]));
                    //indexesToRemove.add(i);
                    matches.get(activeBoxIndex).remove(((String)values[i]));
                   // System.out.println("CONTAINS: "+keywords[i]);
                }   
            }

            for(int i=0; i<keywords.get(activeBoxIndex).size(); i++)
            {
                if(!matches.get(activeBoxIndex).contains(keywords.get(activeBoxIndex).get(i)) && keywords.get(activeBoxIndex).get(i).toUpperCase().contains(text.toUpperCase()))
                {
                    //matches.add(keywords[i]);
                    mcbm.addElement(keywords.get(activeBoxIndex).get(i));

                    matches.get(activeBoxIndex).add(keywords.get(activeBoxIndex).get(i));

                   // System.out.println("CONTAINS: "+keywords[i]);
                }

            }
            
        }
        else
        {
            if(moreChars)
            {
                System.out.println("MORE CHARS");
                int max = matches.get(activeBoxIndex).size();
                Object[] values = matches.get(activeBoxIndex).toArray();
                
                //ArrayList<Integer> indexesToRemove = new ArrayList<Integer>();
                for(int i=0; i<max; i++)
                {
                    if(!((String)values[i]).toUpperCase().contains(text.toUpperCase()))
                    {

                        mcbm.removeElement(((String)values[i]));
                        //indexesToRemove.add(i);
                        matches.get(activeBoxIndex).remove(((String)values[i]));
                       // System.out.println("CONTAINS: "+keywords[i]);
                    }   
                }
                
               /* System.out.println("SIZE: "+max);
                for(int i=0; i<indexesToRemove.size(); i++)
                    matches.remove(matches.get(indexesToRemove.get(i).intValue()));
                System.out.println("AFTER: "+matches.size());*/
            }
            else
            {
                for(int i=0; i<keywords.get(activeBoxIndex).size(); i++)
                {
                    if(!matches.get(activeBoxIndex).contains(keywords.get(activeBoxIndex).get(i)) && keywords.get(activeBoxIndex).get(i).toUpperCase().contains(text.toUpperCase()))
                    {
                        //matches.add(keywords[i]);
                        mcbm.addElement(keywords.get(activeBoxIndex).get(i));

                        matches.get(activeBoxIndex).add(keywords.get(activeBoxIndex).get(i));

                       // System.out.println("CONTAINS: "+keywords[i]);
                    }
                }
            }
        }
            
            
       // System.out.println("SIZE: "+mcbm.getSize());
      
        
    //System.out.println("Match is: "+matches.get(activeBoxIndex).get(0));
    //System.out.println("Actual is: "+mcbm.getSelectedItem() + " OR "+mcbm.getElementAt(0));
  
   //// if(!updatedOtherBoxes)
   ////     updateOther(activeBoxIndex);
      
    if(!firstClick[activeBoxIndex] && mcbm.getSize() > 0)
    {
        
        setActiveValues(activeBoxIndex, mcbm.getSelectedItem().toString());
       
        
        updateOtherList();

        //updateOtherList(activeBoxIndex, mcbm.getSelectedItem().toString());
    }
    
        //jcb.setMaximumRowCount(mcbm.getSize());
    if(!firstClick[activeBoxIndex] && mcbm.getSize()>0)
            boxes[activeBoxIndex].setSelectedIndex(0);
        else
            System.out.println("FIRST CLICK OR MCBM <=0");
        //jcb.setMaximumRowCount(5);
        
       // jcb.setLightWeightPopupEnabled(false);
        
        
        
    }
    
    
    public void setActiveValues(int activeBoxIndex, String value)
    {
        if(!activeBoxIndexes.contains(activeBoxIndex))
        {
            activeBoxIndexes.add(activeBoxIndex);
            activeBoxValues.add(value);
        }
        else
        {
            System.out.println("REMOVING: "+activeBoxIndex + "  "+value);
            //activeBoxIndexes.add(activeBoxIndex);
           //.set(activeBoxIndexes.activeBoxIndex, text);
            for(int i=0; i<activeBoxValues.size(); i++)
                System.out.println("abv: "+activeBoxValues.get(i)+ " abi: "+activeBoxIndexes.get(i));
            
           int index = activeBoxIndexes.indexOf(activeBoxIndex);
           System.out.println("INDEX of removal: "+index);
           activeBoxValues.remove(index);
           activeBoxIndexes.remove((Integer)activeBoxIndex);//.add(mcbm.getSelectedItem().toString());
            
            activeBoxIndexes.add(activeBoxIndex);
           activeBoxValues.add(value);
        }
    }
    
    public void restartActiveValues()
    {
        activeBoxValues.removeAll(activeBoxValues);//.set(activeBoxIndexes.activeBoxIndex, text);
            activeBoxIndexes.removeAll(activeBoxIndexes);//.add(mcbm.getSelectedItem().toString());
            
    }
    
 
    public void updateOtherList()
    {
        
        for(int i=0; i<boxes.length; i++)
        { 
            MutableComboBoxModel mcbm = (MutableComboBoxModel)boxes[i].getModel();
            
            if(!activeBoxIndexes.contains(i) && activeBoxIndexes.size()> 0)//ns
            {
                System.out.println("MORE CHARS");
                int max = matches.get(i).size();
                Object[] values = matches.get(i).toArray();

                System.out.println("UPDATE LAST");
                ArrayList<Integer> indexesOfValue = new ArrayList<Integer>();
                
               // for(int j=0; j<activeBoxIndexes.size(); j++)
               // {
                System.out.println("VALUE ENTERED IS: "+activeBoxValues.get(activeBoxValues.size()-1));
                    indexesOfValue.add(keywords.get(activeBoxIndexes.get(activeBoxIndexes.size()-1)).indexOf(activeBoxValues.get(activeBoxValues.size()-1)));
               // }
                   //int index= keywords.get(activeBoxIndexes.get(activeBoxIndexes.size()-1)).indexOf(activeBoxValues.get(activeBoxValues.size()-1));
                
                //int index = keywords.get(activeBoxIndex).indexOf(value);
                //System.out.println("initial value: "+keywords.get(activeBoxIndex).get(index));
                /////////for(int k=0; k<activeBoxIndexes.size(); k++)
                    
                if(indexesOfValue.get(indexesOfValue.size()-1) != -1)
                {
                    String otherText = ((JTextComponent)boxes[i].getEditor().getEditorComponent()).getText();
                    String stringToFind;
                    if(i == 0)
                        stringToFind = subjects.get(indexesOfValue.get(indexesOfValue.size()-1)).getAbbrevName();
                    else if(i == 1)
                        stringToFind = courses.get(indexesOfValue.get(indexesOfValue.size()-1)).getLevel()+"";
                    else if(i == 2)
                        stringToFind = teachers.get(indexesOfValue.get(indexesOfValue.size()-1)).getfName()+" "+teachers.get(indexesOfValue.get(indexesOfValue.size()-1)).getlName();
                    else if(i == 3)
                        stringToFind = categories.get(indexesOfValue.get(indexesOfValue.size()-1)).getName();
                    else
                        stringToFind = paraprofessionals.get(indexesOfValue.get(indexesOfValue.size()-1)).getfName() + " " + paraprofessionals.get(indexesOfValue.get(indexesOfValue.size()-1)).getlName();
                    
                    if(!otherText.equals(stringToFind))
                    {
                        //clear matches
                        for(int j=0; j<max; j++)
                        {
                            mcbm.removeElement(((String)values[j]));
                            //indexesToRemove.add(i);
                            matches.get(i).remove(((String)values[j]));
                           // System.out.println("CONTAINS: "+keywords[i]);
                        }

                        ((JTextComponent)boxes[i].getEditor().getEditorComponent()).setText(stringToFind);
                      //  System.out.println("UPDATED Last in other with "+clientsFirst.get(index).getlName());

                        boolean moreResults = true;
                        int j = indexesOfValue.get(indexesOfValue.size()-1)-1;
                        String find = activeBoxValues.get(activeBoxValues.size()-1);
                        
                        while(moreResults)
                        {

                            j++;
                           // System.out.println("Checking keyword"+j+ "  " + keywords.get(activeBoxIndexes.get(activeBoxValues.size()-1)).get(j));
                            if(j < keywords.get(activeBoxIndexes.get(activeBoxValues.size()-1)).size() && keywords.get(activeBoxIndexes.get(activeBoxValues.size()-1)).get(j).equals(find))
                            {
                                System.out.println("keywords: "+keywords.get(activeBoxIndexes.get(activeBoxValues.size()-1)).get(j) + " has "+find);
                                /*
                                String element;
                                if(activeBoxIndex != 1)
                                    element = clientsFirst.get(j).getlName();
                                else
                                    element = clientsLast.get(j).getfName();
                                */
                                Course celement;
                                Subject selement;
                                Teacher telement;
                                Paraprofessional pelement;
                                
                                if(activeBoxIndexes.get(activeBoxIndexes.size()-1) == 0)//(i == 1)
                                {
                                    celement = clientsFirst.get(j);
                                }
                                else if(activeBoxIndexes.get(activeBoxIndexes.size()-1) == 1)
                                    celement = clientsLast.get(j);
                               else if(activeBoxIndexes.get(activeBoxIndexes.size()-1) == 2)
                                    celement = clientsPhone.get(j);
                                else
                                    celement = clientsEmail.get(j);
                                
                                System.out.println("Client element is "+celement.getfName()+" "+celement.getlName());
                                boolean containsAll = true;
                                for(int k=0; k<activeBoxValues.size(); k++)
                                {
                                    String elementk = activeBoxValues.get(k);
                                    System.out.println("Checking "+activeBoxValues.get(k));
                                    if(activeBoxIndexes.get(k) == 0 && !elementk.equals(celement.getfName()))
                                            containsAll = false;
                                    if(activeBoxIndexes.get(k) == 1 && !elementk.equals(celement.getlName()))
                                            containsAll = false;
                                    if(activeBoxIndexes.get(k) == 2 && !elementk.equals(celement.getPhone()+""))
                                            containsAll = false;
                                    if(activeBoxIndexes.get(k) == 3 && !elementk.equals(celement.getEmail()))
                                            containsAll = false;
                                    
                                    
                                    
                                    /*
                                    System.out.println("Checking "+activeBoxValues.get(k));
                                    
                                    String elementk;
                                    if(activeBoxIndexes.get(k) == 0)
                                    {
                                        elementk = activeBoxValues.get(k);
                                        if(elementk.equals(celement.getfName()))
                                        {
                                            mcbm.addElement(celement.getlName());

                                            matches.get(i).add(celement.getlName());
                                            System.out.println("FOUND AND ADDED FOR LNAME: "+celement.getlName());
                                        }
                                    }
                                    else// if(activeBoxIndexes.get(k) == 1)
                                    {
                                        elementk = activeBoxValues.get(k);//clientsLast.get(j).getfName();
                                        if(elementk.equals(celement.getlName()))
                                        {
                                            mcbm.addElement(celement.getfName());

                                            matches.get(i).add(celement.getfName());
                                            System.out.println("FOUND AND ADDED FOR FNAME: "+celement.getfName());
                                        }
                                    }
                                    */
                                    
                                    
                                    //if(element)
                                }
                                
                                if(containsAll)
                                {
                                    System.out.print("Passed test and added to "+i);
                                    if(i == 0 && !matches.get(i).contains(celement.getfName()))
                                    {
                                        System.out.println(celement.getfName());
                                        mcbm.addElement(celement.getfName());

                                        matches.get(i).add(celement.getfName());
                                    }
                                    else if(i == 1 && !matches.get(i).contains(celement.getlName()))
                                    {
                                        System.out.println(celement.getlName());
                                        mcbm.addElement(celement.getlName());

                                        matches.get(i).add(celement.getlName());
                                    }
                                    else if(i==2 && !matches.get(i).contains(celement.getPhone()))
                                    {
                                        System.out.println(celement.getPhone());
                                        mcbm.addElement(celement.getPhone());

                                        matches.get(i).add(celement.getPhone()+"");
                                    }
                                    else if(i==3 && !matches.get(i).contains(celement.getEmail()))
                                    {
                                        
                                       
                                        System.out.println(celement.getEmail());
                                            mcbm.addElement(celement.getEmail());

                                            matches.get(i).add(celement.getEmail());
                                        
                                    }
                                    System.out.println("END");
                                }
                                
                                //System.out.println("FOUND AND ADDED: "+clientsFirst.get(j).getlName());
                                
                                
                            }
                            else
                                moreResults = false;
                        }

                        boxes[i].setSelectedIndex(0);//mcbm.setSelectedItem(0);

                        //updatelist(1, true);
                    }
                    else
                        System.out.println("NO NEED");
 
                }
            }
        }
    }
    
    

    @Override
    public void keyReleased(KeyEvent evt) 
    {
        int activeBoxIndex = -1;
        
        
        for(int i=0; i<boxes.length; i++)
        {
            if(boxes[i].getEditor().getEditorComponent() == evt.getSource())
            {
                activeBoxIndex = i;
            }
        }
        if(activeBoxIndex != -1)
        {
            int cursorPos = ((JTextField)boxes[activeBoxIndex].getEditor().getEditorComponent()).getCaretPosition();
            
            if(firstClick[activeBoxIndex] && evt.getKeyCode() != KeyEvent.VK_TAB)
            {
                firstClick[activeBoxIndex]=false;

                ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).setText((""+evt.getKeyChar()).trim());

            }

            String text = ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).getText();

            System.out.println("CURRENT SELECTION: "+boxes[activeBoxIndex].getSelectedIndex());
            if(boxes[activeBoxIndex].getSelectedIndex() > -1)
                System.out.println("WITH" +boxes[activeBoxIndex].getSelectedItem().toString());
            if (evt.getKeyCode() == KeyEvent.VK_DOWN)
            {
                isUpdating[activeBoxIndex] = true;
                zeroIndexSel[activeBoxIndex] = true;

                boxes[activeBoxIndex].setPopupVisible(true);
                System.out.println("UPDATE OTHER DOWN");
                //////updateOther(activeBoxIndex);
                
                setActiveValues(activeBoxIndex, text);

                updateOtherList();
                /////updateOtherList(activeBoxIndex, text);
            }
            
            
            if(!isUpdating[activeBoxIndex] && evt.getKeyCode() != KeyEvent.VK_LEFT && evt.getKeyCode() != KeyEvent.VK_RIGHT)
            {
                boxes[activeBoxIndex].hidePopup();
                updateList(activeBoxIndex, false);
                restartActiveValues();
                boxes[activeBoxIndex].showPopup();
                ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).setText(text);
                if(((JTextField)boxes[activeBoxIndex].getEditor().getEditorComponent()).getText().length() >= cursorPos)
                    ((JTextField)boxes[activeBoxIndex].getEditor().getEditorComponent()).setCaretPosition(cursorPos);

                int size = boxes[activeBoxIndex].getModel().getSize();

                if(size == 0)
                {

                    System.out.println("SIZE 0;;");

                    boxes[activeBoxIndex].hidePopup();
                }

            }
            else
            {
                if(!isUpdating[activeBoxIndex])
                {
                    boxes[activeBoxIndex].hidePopup();
                }
            }

            if (evt.getKeyCode() == KeyEvent.VK_UP && zeroIndexSel[activeBoxIndex])
            {
                System.out.println("UP PRESSED and ZERO INDEX");

                isUpdating[activeBoxIndex]=false;
                //jcb.setPopupVisible(false);
                boxes[activeBoxIndex].hidePopup();
            }
            else if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            {
                String selected = boxes[activeBoxIndex].getSelectedItem().toString();
                
                updateList(activeBoxIndex, false);
                
                System.out.println("ENTER PRESSED");
                if(boxes[activeBoxIndex].getSelectedIndex() >= 0)
                    ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).setText(selected);

                isUpdating[activeBoxIndex]=false;

                System.out.println("LDSJLKFJSDLFJ: "+boxes[activeBoxIndex].getSelectedItem().toString());
                //jcb.setPopupVisible(false);
                boxes[activeBoxIndex].hidePopup();
                
                //try
           //     System.out.println("UPDATE OTHER ENTER");
               /////// updateOther(activeBoxIndex);
                
           //     activeBoxIndexes.add(activeBoxIndex);
          //      activeBoxValues.add(text);

          //      updateOtherList();
                
               ///// updateOtherList(activeBoxIndex, text);

                //jcb.requestFocusInWindow();
            }

            else if(evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_LEFT && evt.getKeyCode() != KeyEvent.VK_RIGHT)
            {
                isUpdating[activeBoxIndex]=false;
            }

            if(boxes[activeBoxIndex].getSelectedIndex() == 0)
            {
                zeroIndexSel[activeBoxIndex] = true;
            }
            else
            {
                zeroIndexSel[activeBoxIndex] = false;
            }
            
            if(evt.getKeyCode() == KeyEvent.VK_UP)
            {
                System.out.println("UPDATE OTHER UP");
                ////updateOther(activeBoxIndex);
                
                
                setActiveValues(activeBoxIndex, text);

                updateOtherList();
                ///////updateOtherList(activeBoxIndex, text);
            }
            
        }
        else
            System.out.println("ERRROR Type");

    }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {}

    @Override
    public void actionPerformed(ActionEvent ae) {
        //System.out.println("ACTION" + ae.getActionCommand() + "   "+ae.paramString() + "   "+ae.getm );
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
        System.out.println("MOUSE CLICKED");
        int activeBoxIndex = -1;
        for(int i=0; i<boxes.length; i++)
        {
            if(boxes[i].getEditor().getEditorComponent() == me.getSource())
                activeBoxIndex = i;
        }
        if(activeBoxIndex != -1)
        {
            boxes[activeBoxIndex].setPopupVisible(true);
        }
        else
            System.out.println("ERRROR mouse click");
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.MOUSE_EVENT_MASK)
            System.out.println("MOUSE MASK");
        else if(e.getStateChange() == ItemEvent.SELECTED)
            System.out.println("SELECTED I");
    }

    
    private void addPopupMouseListener(JComboBox box) {
        try {
                  Field popupInBasicComboBoxUI = BasicComboBoxUI.class.getDeclaredField("popup");
                popupInBasicComboBoxUI.setAccessible(true);
                BasicComboPopup popup = (BasicComboPopup) popupInBasicComboBoxUI.get(box.getUI());
                
                Field scrollerInBasicComboPopup = BasicComboPopup.class.getDeclaredField("scroller");
                scrollerInBasicComboPopup.setAccessible(true);
                JScrollPane scroller = (JScrollPane) scrollerInBasicComboPopup.get(popup);

                scroller.getViewport().getView().addMouseListener(listener());
//                ((JViewport) ((JScrollPane) ((BasicComboPopup) popupInBasicComboBoxUI.get(box.getUI())).getComponents()[0]).getComponents()[0]).getComponents()[0].addMouseListener(this);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private MouseAdapter listener() {
        return new MouseAdapter(){
            public void mouseClicked(MouseEvent mouseEvent) {
                //System.out.println("mouseClicked");
            }

            public void mousePressed(MouseEvent mouseEvent) {
               // System.out.println("mousePressed");
            }

            public void mouseReleased(MouseEvent mouseEvent) {
                //System.out.println("mouseReleased");
                System.out.println("ACION");    
                int activeBoxIndex = -1;
                for(int i=0; i<boxes.length; i++)
                {
                   try{
                    Field popupInBasicComboBoxUI = BasicComboBoxUI.class.getDeclaredField("popup");
                popupInBasicComboBoxUI.setAccessible(true);
                BasicComboPopup popup = (BasicComboPopup) popupInBasicComboBoxUI.get(boxes[i].getUI());
                Field scrollerInBasicComboPopup = BasicComboPopup.class.getDeclaredField("scroller");
                scrollerInBasicComboPopup.setAccessible(true);
                JScrollPane scroller = (JScrollPane) scrollerInBasicComboPopup.get(popup);

                if(scroller.getViewport().getView() == mouseEvent.getComponent())
                            activeBoxIndex = i;
              
                 }
                    catch (NoSuchFieldException es) {
                        es.printStackTrace();
                    }
                    catch (IllegalAccessException es) {
                        es.printStackTrace();
                    }
                        
                    System.out.println(mouseEvent.getComponent());
                  
                }
                if(activeBoxIndex != -1)
                {

                    //if(.getModifiers() == ActionEvent.MOUSE_EVENT_MASK)
                    //{
                        System.out.println("ACION");
                       ///// updatelist(activeBoxIndex, false);
                        String text = ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).getText();
                        setActiveValues(activeBoxIndex, text);
                        updateOtherList();
                
                       // System.out.println("UPDATE OTHER MOUSE");
                        //updateOther(activeBoxIndex);
                   // }

                }
                else
                    System.out.println("ERRROR action");
            }

            public void mouseEntered(MouseEvent mouseEvent) {
                //System.out.println("mouseEntered");
            }

            public void mouseExited(MouseEvent mouseEvent) {
                //System.out.println("mouseExited");
            }
        };
    }
    
}
