/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import javax.swing.DefaultListModel;

/**
 *
 * @author Nathaniel
 */
public class RestrictionListModel 
{
    private DefaultListModel dlm;
    
    private String[] restrictions;

    public RestrictionListModel(DefaultListModel dlm, String[] restrictions)
    {
        this.dlm = dlm;
        this.restrictions = restrictions;
    }
    
    public void getFName()
    {
        
    }
    
    public void setListElement(String restrictionValue, int index)
    {
        if(restrictionValue.length() > 0)
        {
            String existing = dlm.get(index).toString();
            
            if(existing.equals(restrictions[index]))
            {
                String replacedExisting = existing.replace(" any", "");
                dlm.set(index, replacedExisting+" '"+restrictionValue+"'");
            }
            else
                dlm.set(index, existing+" or '"+restrictionValue+"'");
            
            System.out.println("add fname restriction searchaddrestriciton");
        }
    }
    
    public String getQuery(int index, char letter, String databaseName)
    {
        String value = dlm.get(index).toString();
        String valueQuery = value.replaceFirst(restrictions[index].replaceFirst("any",""), "");
        if(valueQuery.equals("any"))
            valueQuery = "";
        else
        {
            //System.out.println("fname query: "+fnameQuery);
            String[] split = valueQuery.split(" or ");
            
            valueQuery = "";        
            for(int i=0; i<split.length; i++)
                valueQuery += letter+"."+databaseName+"="+split[i]+" or ";
            valueQuery=valueQuery.substring(0, valueQuery.length()-4);
            valueQuery= "("+valueQuery+")";
        }
        
        return valueQuery;
    }
}
