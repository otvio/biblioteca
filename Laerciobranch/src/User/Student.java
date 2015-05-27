
package t3;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Student extends User 
{

    public Student(String name, String cpf, String rg, int bookLimit, int daysLimit, int code)
    {
        super(name, cpf, rg, 4, 15, code, 'S');
    } 
}
