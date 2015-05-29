
package projetobiblioteca;


public class Student extends User 
{
    public Student(String name, String cpf, String rg, int code)
    {
        super(name, cpf, rg, 4, 15, code, "S");
    } 
}
