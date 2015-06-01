
package projetobiblioteca.user;

// Classe Student que passa no construtor o nome, cpf, rg, número máximo de empréstimo, o dia máximo, código  e o tipo de usuário. 

public class Student extends User 
{
    public Student(String name, String cpf, String rg, int code)
    {
        super(name, cpf, rg, 4, 15, code, "S");
    } 
}
