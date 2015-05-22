
package t3;


public abstract class User
{
    private String name;
    private String CPF;
    private String RG;
    private int bookLimit;
    private int daysLimit;
    
    
    public User(String name, String cpf, String rg, int bookLimit, int daysLimit)
    {
        this.name = name;
        this.CPF  = cpf;
        this.RG = rg;
        this.bookLimit = bookLimit;
        this.daysLimit = daysLimit;        
    }    

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCPF() 
    {
        return CPF;
    }

    public void setCPF(String CPF)
    {
        this.CPF = CPF;
    }

    public String getRG()
    {
        return RG;
    }

    public void setRG(String RG) 
    {
        this.RG = RG;
    }

    public int getBookLimit()
    {
        return bookLimit;
    }

    public void setBookLimit(int bookLimit) 
    {
        this.bookLimit = bookLimit;
    }

    public int getDaysLimit()
    {
        return daysLimit;
    }

    public void setDaysLimit(int daysLimit) 
    {
        this.daysLimit = daysLimit;
    }       
}
