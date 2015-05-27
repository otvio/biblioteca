
package projetobiblioteca;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;


public abstract class User
{
    private String name;
    private String CPF;
    private String RG;
    private String type;
    private int bookLimit;
    private int daysLimit;
    private int code;

    public User(String name, String cpf, String rg, int bookLimit, int daysLimit, int code, String type)
    {
        this.name = name;
        this.CPF  = cpf;
        this.RG = rg;
        this.bookLimit = bookLimit;
        this.daysLimit = daysLimit;        
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
    public void addFileUser()
    {
        try
        {
            File fp = new File("users.txt"); // variavel que 'representara' o arquivo book
            FileWriter fw = new FileWriter(fp, true); // empacotar o file para que possa escrever nele, e adicionar o campo true para indicar append
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo

            if(fp.exists() == false){ // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            }

            pw.print(this.getCode());
            pw.print(",");
            pw.print(this.getName());
            pw.print(",");
            pw.print(this.getCPF());
            pw.print(",");
            pw.print(this.getRG());
            pw.print(",");
            pw.print(this.getBookLimit());
            pw.print(",");
            pw.print(this.getDaysLimit());
            pw.print(",");
            pw.println(this.getType());

            pw.close(); 
            fw.close();
        }

        catch(Exception e){
            System.out.println("Can't write in the file :/");
        }
    }
    
    public void printUser()
    {
        PrintStream pw = new PrintStream(System.out);
        
        pw.print("Codigo: " + this.getCode() + " ");
        pw.print("Nome: " + this.getName() + " ");
        pw.print("CPF: " + this.getCPF() + " ");
        pw.print("RG: " + this.getRG() + " ");
        pw.print("Limite de livros: " + this.getBookLimit() + " ");
        pw.print("Limite de dias: " + this.getDaysLimit() + " ");
        pw.println("Tipo: " + this.getType());
    }
    
    public void setType(String type) {
        this.type = type;
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
            //createUser(this);
        }
        else if(input.equals("Student")){
            Student s = new Student(sc.nextLine(), sc.nextLine(), sc.nextLine(), 4, 15, 0);
        }
        else if(input.equals("Person")){
            Person p = new Person(sc.nextLine(), sc.nextLine(), sc.nextLine(), 2, 15, 0);
        }
    }
    
    public static void main (String [] args){
        
        Teacher t = new Teacher("0", "0", "0", 0, 0, 0);
        
        t.insertUser();
    }
}
