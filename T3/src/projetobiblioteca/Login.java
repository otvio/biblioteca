
package projetobiblioteca;


public class Login
{
    private String user;
    private String password;
    private int code;
    
    
    public Login ( int code, String user, String password)
    {        
        this.user = user;
        this.password = password;
        this.code = code;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user) 
    {
        this.user = user;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code) 
    {
        this.code = code;
    }  
}
