
package tutoring.entity;

public class SignInOut 
{
    private String username = null;
    private String password = null;
    private boolean loginStatus = false;
    private boolean usernameStatus = false;
    private boolean passwordStatus = false;
    private String loginFeedback = "Please enter your 'username' and 'password' to receive feedback.";
    private String[][] users = new String[2][2];
    
    public SignInOut(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    
    public SignInOut()
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
    
    public boolean login()
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
        //implement database search here, mySql
        
        return userFound;
    }
    
    private boolean lookUpPassword()
    {
        boolean passwordFound = false;
        //implement database search here, mySql
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
