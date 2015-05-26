
package t3;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Student extends User 
{

    public Student(String name, String cpf, String rg, int bookLimit, int daysLimit, int code)
    {
        super(name, cpf, rg, 4, 15, code);
    }
    
    public void CreateStudent(Student student){
        try{
            File fp = new File("users.txt"); // variavel que 'representara' o arquivo book
            FileWriter fw = new FileWriter(fp, true); // empacotar o file para que possa escrever nele, e adicionar o campo true para indicar append
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo

            if(fp.exists() == false){ // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            }

            pw.print(student.getCode());
            pw.print(",");
            pw.print(student.getName());
            pw.print(",");
            pw.print(student.getCPF());
            pw.print(",");
            pw.print(student.getRG());
            pw.print(",");
            pw.print(student.getBookLimit());
            pw.print(",");
            pw.println(student.getDaysLimit());

            pw.close(); 
            fw.close();
        }

        catch(Exception e){
            System.out.println("Can't write in the file :/");
        }
    }
    
}
