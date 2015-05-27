
package uuser;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;


public class Teacher extends User
{

    public Teacher(String name, String cpf, String rg, int bookLimit, int daysLimit, int code)
    {
        super(name, cpf, rg, 6 , 60, code, 'T');
    }
    
}
