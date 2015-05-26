
package t3;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;


public abstract class User
{
    private String name;
    private String CPF;
    private String RG;
    private int bookLimit;
    private int daysLimit;
    private int code;

    public User(String name, String cpf, String rg, int bookLimit, int daysLimit, int code)
    {
        this.name = name;
        this.CPF  = cpf;
        this.RG = rg;
        this.bookLimit = bookLimit;
        this.daysLimit = daysLimit;        
        this.code = code;
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

    public int getCode() 
    {
        return code;
    }
    
    public void insertUser(){
        Scanner sc = new Scanner(System.in);
        
        String input = sc.nextLine();
        
        if(input.equals("Teacher")){
            Teacher t = new Teacher(sc.nextLine(), sc.nextLine(), sc.nextLine(), 6, 60, 0);
            t.CreateTeacher(t);
        }
        else if(input.equals("Student")){
            Student s = new Student(sc.nextLine(), sc.nextLine(), sc.nextLine(), 4, 15, 0);
            s.CreateStudent(s);
        }
        else if(input.equals("Person")){
            Person p = new Person(sc.nextLine(), sc.nextLine(), sc.nextLine(), 2, 15, 0);
            p.CreatePerson(p);
        }
    }
    
    public static void main (String [] args){
        
        Teacher t = new Teacher("0", "0", "0", 0, 0, 0);

        t.insertUser();
    }
}
