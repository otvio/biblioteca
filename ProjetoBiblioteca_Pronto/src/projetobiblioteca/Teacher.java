
package projetobiblioteca;


public class Teacher extends User
{
    public Teacher(String name, String cpf, String rg, int code)
    {
        super(name, cpf, rg, 6 , 60, code, "T");
    }
}
