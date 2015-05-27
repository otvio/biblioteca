
package projetobiblioteca;

import java.io.PrintStream;

public class Book 
{
    
    private int pages;
    private int issue;
    private int code;
    private String title;
    private String author;
    private String type;
    private int quantity;
    private int available;
        
    public Book(int pages, int issue, int code, String title, String author, String type, 
                int quantity, int available)
    {
        
        this.pages = pages;
        this.issue = issue;
        this.code = code;
        this.title = title;
        this.author = author;
        this.type = type;
        this.available = available;
        this.quantity = quantity;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAvailable() {
        return available;
    }

    public String getType() {
        return type;
    }
    
    public int getPages() {
        return pages;
    }

    public int getIssue() {
        return issue;
    }

    public int getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void printBook()
    {
        PrintStream pw = new PrintStream(System.out); // cria um PrintStream que irá escrever na tela
            
        pw.print("Codigo:" + this.getCode() + " ");
        pw.print("Titulo:" + this.getTitle() + " ");
        pw.print("Autor:" + this.getAuthor() + " ");
        pw.print("Edicao:" + this.getIssue() + " ");
        pw.print("Tipo:" + this.getType() + " ");
        pw.print("Paginas:" + this.getPages() + " ");
        pw.print("Quantidade disponivel:" + this.getAvailable() + " ");
        pw.println("Quantidade total:" + this.getQuantity());
    }
}
