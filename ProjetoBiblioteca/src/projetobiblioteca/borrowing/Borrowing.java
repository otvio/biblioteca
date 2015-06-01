
package projetobiblioteca.borrowing;

import projetobiblioteca.book.Book;
import projetobiblioteca.user.User;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Borrowing 
{
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Objeto para a data do sistema
    
    private int code;                       // código do empréstímo
    private int codeUser;                   // código do usuário
    private int codeBook;                   // código do livro
    private boolean returned;               // variável para verificar a devolução do livro
    private Calendar dateBorrow;            // Irá armazenar a data de empréstimo
    private Calendar dateReturn;            // Irá armazenar a data de devolução
    private Calendar dateMax;               // Irá armazenar a data máxima de devolução

    
    public Borrowing(int code, int codeUser, int codeBook, boolean returned, 
            String dateBorrow, String dateReturn, String dateMax)   // Construtor para a classe de empréstimo
    {
        String[] date;                      // String para armazenar a data
        
        this.code = code;                   // Armazena o código de empréstimo
        this.codeUser = codeUser;           // Armazena o código do usuário
        this.codeBook = codeBook;           // Armazena o código do livro 
        this.returned = returned;           // Verifica a devolução do livro 'true' devolvido e 'false' para pendência na devolução
        
        date = dateBorrow.split("/");       // Divisão com barras da data armazenada na variável date
        
        this.dateBorrow = new GregorianCalendar     // Irá pegar a data  de empréstimo
        (
                Integer.parseInt(date[2]), 
                Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0])
        );
        
        date = dateReturn.split("/");       // Divisão com barras da data armazenada na variável date
        
        this.dateReturn = new GregorianCalendar   // Irá pegar a data de devolução
        (
                Integer.parseInt(date[2]), 
                Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0])
        );
        
        date = dateMax.split("/");                 // Divisão com barras da data armazenada na variável date
        
        this.dateMax = new GregorianCalendar(      //  Irá armazenar a data máxima de devolução dependendo do tipo do usuário
                Integer.parseInt(date[2]), 
                Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0])
        );
    }
    // Getters e setters da classe de empréstimos //
    
    public Calendar getDateMax()
    {
        return dateMax;
    }

    public void setDateMax(Calendar dateMax)
    {
        this.dateMax = dateMax;
    }
    
    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public int getCodeUser()
    {
        return codeUser;
    }

    public void setCodeUser(int codeUser)
    {
        this.codeUser = codeUser;
    }

    public int getCodeBook()
    {
        return codeBook;
    }

    public void setCodeBook(int codeBook)
    {
        this.codeBook = codeBook;
    }

    public boolean isReturned()
    {
        return returned;
    }

    public void setReturned(boolean returned)
    {
        this.returned = returned;
    }

    public Calendar getDateBorrow()
    {
        return dateBorrow;
    }

    public void setDateBorrow(Calendar dateBorrow)
    {
        this.dateBorrow = dateBorrow;
    }

    public Calendar getDateReturn()
    {
        return dateReturn;
    }

    public void setDateReturn(Calendar dateReturn)
    {
        this.dateReturn = dateReturn;
    }
    
    public void printBorrowing(User user, Book book)   //Método que irá imprimir na saída todas as especificações do usuário
    {
        PrintStream pw = new PrintStream(System.out);
        
        pw.println("//--------------------------------------");   
        pw.println("||Código: " + this.getCode());
        
        if (user != null)
            pw.println("||Nome do usuário: " + user.getName());
        
        if (book != null)
            pw.println("||Nome do Livro: " + book.getTitle());
        
        pw.println("||Foi devolvido: " + (this.isReturned() ? "Sim" : "Não"));
        pw.println("||Data de empréstimo: " + dateFormat.format(dateBorrow.getTime()));
        
        if (this.isReturned())
            pw.println("||Data de devolução: " + dateFormat.format(dateReturn.getTime()));
        
        pw.println("||Data máxima de devolução: " + dateFormat.format(dateMax.getTime()));
        pw.println("\\\\--------------------------------------");
    }
    
    public void addFileBorrowing()  //método que irá armazenar no arquivo a situação de empréstimo do usuário
    {
        try 
        {
            File fp = new File("borrowings.csv");
            FileWriter fw = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo

            if(fp.exists() == false)
            { // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            }
            
            // Comandos que salvam os dados no arquivo, observe que após a adição de uma parte do livro é adicionada uma vírgula para separa-los

            pw.print(this.getCode());
            pw.print(",");
            pw.print(this.getCodeUser());
            pw.print(",");
            pw.print(this.getCodeBook());
            pw.print(",");
            pw.print(this.isReturned());
            pw.print(",");
            pw.print(dateFormat.format(this.getDateBorrow().getTime()));
            pw.print(",");
            pw.print(dateFormat.format(this.getDateReturn().getTime()));
            pw.print(",");
            pw.println(dateFormat.format(this.getDateMax().getTime()));

            // #Termina de gravar os itens no arquivo com uma quebra de linha no final do arquivo# //
            
            pw.close(); 
            fw.close();
        }
        catch(Exception e){
            System.out.println("Something wrong happens too D:\n");
        }
    }
}
