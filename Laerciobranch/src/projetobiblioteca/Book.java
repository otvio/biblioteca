
package projetobiblioteca;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

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
        PrintStream pw = new PrintStream(System.out);
            
        pw.println("//--------------------------------------");   
        pw.println("||Código: " + this.getCode());
        pw.println("||Título: " + this.getTitle());
        pw.println("||Autor: " + this.getAuthor());
        pw.println("||Edição: " + this.getIssue());
        pw.println("||Tipo: " +((this.getType().equals("T")) ? "Texto" : "Geral"));
        pw.println("||Quantidade de páginas: " + this.getPages());
        pw.println("||Quantidade disponível: " + this.getAvailable());
        pw.println("||Quantidade total: " + this.getQuantity());
        pw.println("\\\\--------------------------------------");
    }
    
    public void addFileBook() 
    {
        try{
            File fp = new File("books.txt"); // variavel que 'representara' o arquivo book
            FileWriter fw = new FileWriter(fp, true); // empacotar o file para que possa escrever nele, e adicionar o campo true para indicar append
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo

            if(fp.exists() == false){ // caso o arquivo nao exista, cria um arquivo
                System.out.println("NAO EXISTE ARQUIVO");
                fp.createNewFile();
            }
            // ### Escrever os itens do livro aqui nesta linha ### //
            // Comandos que salvam os itens no arquivo
            
            pw.print(this.getCode());
            pw.print(",");
            pw.print(this.getTitle());
            pw.print(",");
            pw.print(this.getAuthor());
            pw.print(",");
            pw.print(this.getIssue());
            pw.print(",");
            pw.print(this.getType());
            pw.print(",");
            pw.print(this.getPages());
            pw.print(",");
            pw.print(this.getAvailable());
            pw.print(",");
            pw.println(this.getQuantity());
            
            // #Termina de gravar os itens no arquivo com uma quebra de linha no final do arquivo# //
            
            pw.close(); 
            fw.close();
        }
        catch(Exception e){
            System.out.println("Something wrong happens D:\n");
        }
    }
    
}
