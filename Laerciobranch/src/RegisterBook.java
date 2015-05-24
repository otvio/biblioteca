
import Laerciobranch.Book;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;


public class RegisterBook extends Book{

    public RegisterBook(int pages, int issue, int code, String title, String author, String type, int quantity, int available) {
        super(pages, issue, code, title, author, type, quantity, available);
    }
    
    public static void InsertBook(Book bk) {
        try{
            File fp = new File("book.txt"); // variavel que 'representara' o arquivo book
            FileWriter fw = new FileWriter(fp, true); // empacotar o file para que possa escrever nele, e adicionar o campo true para indicar append
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo

            if(fp.exists() == false){ // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            }
            // ### Escrever os itens do livro aqui nesta linha ### //
            // Comandos que salvam os itens no arquivo
            
            pw.print(bk.getCode());
            pw.print(",");
            pw.print(bk.getTitle());
            pw.print(",");
            pw.print(bk.getAuthor());
            pw.print(",");
            pw.print(bk.getIssue());
            pw.print(",");
            pw.print(bk.getType());
            pw.print(",");
            pw.print(bk.getPages());
            pw.print(",");
            pw.print(bk.getAvailable());
            pw.print(",");
            pw.println(bk.getQuantity());
            
            // #Termina de gravar os itens no arquivo com uma quebra de linha no final do arquivo# //
            
            pw.close(); 
            fw.close();
        }
        catch(Exception e){
            System.out.println("Something wrong happens D:\n");
        }
    }
    
    public static int SearchBook(String arg){
        try{   
            // # Cria um FileReader, e o empacota no BufferedReader para fazer a leitura do arquivo # //
            File fp = new File("book.txt"); // variavel que 'representara' o arquivo book
            FileReader fr = new FileReader(fp);
            BufferedReader br = new BufferedReader(fr);
            List<Book> books = new ArrayList <Book>(); // Lista que armazenará os livros //
            
            // #Enquanto ouver elementos no arquivo, faz a leitura e printa no terminal# //
            while(br.ready()){
                String line = br.readLine();
                String[] tokens = line.split(","); // metodo que tira as figuras e coloca em um vetor as string que estavam separadas por virgula
 
                // #Pega as informações que obteve na string e coloca em uma lista de livros# //
                int code = Integer.parseInt(tokens[0]);
                String title = tokens[1];
                String author = tokens[2];
                int issue = Integer.parseInt(tokens[3]);
                String type = tokens[4];
                int pages = Integer.parseInt(tokens[5]);
                int available = Integer.parseInt(tokens[6]);
                int quantity = Integer.parseInt(tokens[7]);
                // logo apos pegar as variaveis, pega os valores, cria um novo livro, e o adiciona na lista //
                books.add(new Book(pages, issue, code, title, author, type, quantity, available));
            }
            // #verifica a disponibilidade do livro, se o livro esta na biblioteca #//
            for(Book b : books){
                if(arg.equals(b.getTitle())){
                    System.out.println("Founded");
                    // Chamar metodo que verifica se ha livros disponiveis na biblioteca //
                }   
            }
            
            br.close();
            fr.close();
        }
        catch(Exception e){
            System.out.println("Something wrong D:");
        }
        return 1;
    }
    
    public static void main (String[] args){
        
        // # Testes # //
        try{ 
            InsertBook(new Book(10, 1, 0, "Hello World", "Kenji", "Text", 1, 1));
            InsertBook(new Book(10, 1, 0, "Hello", "Kenji", "Text", 1, 1));
            SearchBook("Hello World");
        }
        catch(Exception e){
            System.out.println("Something wrong happens D:\n");
        }
    }
    
}
