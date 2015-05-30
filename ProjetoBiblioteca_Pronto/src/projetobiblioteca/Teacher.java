
package projetobiblioteca;

// Classe Teacher que passa no construtor o nome, cpf, rg, número máximo de empréstimo, o dia máximo, código  e o tipo de usuário. 

public class Teacher extends User
{
    public Teacher(String name, String cpf, String rg, int code)
    {
        super(name, cpf, rg, 6 , 60, code, "T");
    }
}
