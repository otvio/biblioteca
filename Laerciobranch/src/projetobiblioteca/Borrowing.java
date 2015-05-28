
package projetobiblioteca;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Borrowing 
{
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    private int code;
    private int codeUser;
    private int codeBook;
    private boolean returned;
    private Calendar dateBorrow;
    private Calendar dateReturn;
    private Calendar dateMax;
    
    public Borrowing(int code, int codeUser, int codeBook, boolean returned, String dateBorrow, String dateReturn, String dateMax)
    {
        String[] date;
        
        this.code = code;
        this.codeUser = codeUser;
        this.codeBook = codeBook;
        this.returned = returned;
        
        date = dateBorrow.split("/");
        
        this.dateBorrow = new GregorianCalendar(
                Integer.parseInt(date[2]), 
                Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0])
        );
        
        date = dateReturn.split("/");
        
        this.dateReturn = new GregorianCalendar(
                Integer.parseInt(date[2]), 
                Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0])
        );
        
        date = dateMax.split("/");
        
        this.dateMax = new GregorianCalendar(
                Integer.parseInt(date[2]), 
                Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0])
        );
    }
    
    public Calendar getDateMax() {
        return dateMax;
    }

    public void setDateMax(Calendar dateMax) {
        this.dateMax = dateMax;
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCodeUser() {
        return codeUser;
    }

    public void setCodeUser(int codeUser) {
        this.codeUser = codeUser;
    }

    public int getCodeBook() {
        return codeBook;
    }

    public void setCodeBook(int codeBook) {
        this.codeBook = codeBook;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public Calendar getDateBorrow() {
        return dateBorrow;
    }

    public void setDateBorrow(Calendar dateBorrow) {
        this.dateBorrow = dateBorrow;
    }

    public Calendar getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Calendar dateReturn) {
        this.dateReturn = dateReturn;
    }
    
    public void printBorrowing()
    {
        PrintStream pw = new PrintStream(System.out);
        
        pw.print("Codigo: " + this.getCode() + " ");
        pw.print("Codigo Usuario: " + this.getCodeUser() + " ");
        pw.print("Codigo Livro: " + this.getCodeBook() + " ");
        pw.print("Foi devolvido: " + this.isReturned() + " ");
        pw.print("Data de emprestimo: " + dateFormat.format(dateBorrow.getTime()) + " ");
        pw.println("Data de devolucao: " + dateFormat.format(dateReturn.getTime()));
    }
    
    public void addFileBorrowing() 
    {
        try 
        {
            File fp = new File("borrowings.txt");
            FileWriter fw = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo

            if(fp.exists() == false)
            { // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            }
            
            // Comandos que salvam os dados no arquivo

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

            // #Termina de gravar os itens no arquivo com uma quebra de linha no final do arquivo# //
            
            pw.close(); 
            fw.close();
        }
        catch(Exception e){
            System.out.println("Something wrong happens too D:\n");
        }
    }
}
