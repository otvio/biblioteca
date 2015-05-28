
package projetobiblioteca;


public class Person extends User
{
    public Person(String name, String cpf, String rg, int code)
    {
        super(name, cpf, rg, 2, 15, code, "P");
    }
}
