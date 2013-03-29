package preciousAreaSoNoMergeHappensLOL;
import java.util.ArrayList;
import tutoring.entity.*;
import tutoring.helper.HibernateTest;

public class Login 
{
    private String username = null;
    private String password = null;
    private boolean loginStatus = false;
    private boolean usernameStatus = false;
    private boolean passwordStatus = false;
    private String loginFeedback = "Please enter your 'username' and 'password' to receive feedback.";
    final private ArrayList<User> list = (ArrayList<User>) HibernateTest.select("from User");
    private String realPassword = null;
    
    public Login(String username, String password)
    {
        this.username = username;
        this.password = password;
        login();
    }
    
    public Login()
    {
        
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public final boolean login()
    {
        usernameStatus = lookUpUser();
        passwordStatus = lookUpPassword();
        
        if((usernameStatus == true) && (passwordStatus == true))
        {
            loginStatus = true;
            return loginStatus;
        }
        else 
        {
            loginStatus = false;
            return loginStatus;
        }
       
    }
    
    private boolean lookUpUser()
    {
        boolean userFound = false;
        
        for (int i = 0; i < list.size()-1; i++) 
        {
            if(username.equals(list.get(i).getUserName()))
            {
               userFound = true;
               realPassword = list.get(i).getPassword();
               return userFound;
            }
        }
        
        return userFound;
    }
    
    private boolean lookUpPassword()
    {
        boolean passwordFound = false;

        if(password.equals(realPassword))
        {
           passwordFound = true;
           return passwordFound;
        }
        
        return passwordFound;
    }
    
    public String loginFeedback()
    {
        if(loginStatus == true)
        {
            loginFeedback = "Username and passwords match.";
            return loginFeedback;
        }
        else if(usernameStatus == false)
        {
            loginFeedback = "The username that you entered was not found.";
            return loginFeedback;
        }
        else if(passwordStatus == false)
        {
            loginFeedback = "The password that you entered is invalid.";
            return loginFeedback;
        }
        
        return loginFeedback;
    }
    

    
}
