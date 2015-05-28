
package projetobiblioteca;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Login 
{
    private String user;
    private String password;
    private int code;
    private int codeUser;

    public Login(int code, String user, String password, int codeUser)
    {        
        this.user = user;
        this.password = password;
        this.code = code;
        this.codeUser = codeUser;
    }

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
    
    public void addFileLogin() 
    {
        try{
            File fp = new File("logins.txt");
            FileWriter fw = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo

            if(fp.exists() == false){ // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            }
            
            // Comandos que salvam os itens no arquivo
            
            pw.print(this.getCode());
            pw.print(",");
            pw.print(this.getUser());
            pw.print(",");
            pw.print(this.getPassword());
            pw.print(",");
            pw.print(this.getCodeUser());
            
            // #Termina de gravar os itens no arquivo com uma quebra de linha no final do arquivo# //
            
            pw.close(); 
            fw.close();
        }
        catch(Exception e){
            System.out.println("Something wrong happens D:\n");
        }
    }
}
