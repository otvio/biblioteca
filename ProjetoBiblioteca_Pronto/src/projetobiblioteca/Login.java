
package projetobiblioteca;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Login 
{
    private String user;            // String para armazenar o usuário
    private String password;        // String para armazenar o password
    private int code;               // Int para armazenar o código de empréstimo
    private int codeUser;           // Int para armazenar o código do usuário 

    public Login(int code, String user, String password, int codeUser) // Construtor para pegar as informações do usuário
    {        
        this.user = user;
        this.password = password;
        this.code = code;
        this.codeUser = codeUser;
    }

    // Getters e setters para  o objeto usuário //
    
    public int getCodeUser() 
    {
        return codeUser;
    }

    public void setCodeUser(int codeUser) 
    {
        this.codeUser = codeUser;
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
    
    public void addFileLogin()  // Método para adicionar o usuário no arquivo
    {
        try
        {
            File fp = new File("logins.txt");
            FileWriter fw = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(fw); // Cria um PrintWriter que irá escrever no arquivo

            if(fp.exists() == false)              // Caso o arquivo nao exista, cria um arquivo
            { 
                fp.createNewFile();
            }
            
            // Comandos que salvam os itens no arquivo
            
            pw.print(this.getCode());
            pw.print(",");
            pw.print(this.getUser());
            pw.print(",");
            pw.print(this.getPassword());
            pw.print(",");
            pw.println(this.getCodeUser());
            
            // #Termina de gravar os itens no arquivo com uma quebra de linha no final do arquivo# //
            
            pw.close(); 
            fw.close();
        }
        catch(Exception e){
            System.out.println("Something wrong happens D:\n");
        }
    }
}
