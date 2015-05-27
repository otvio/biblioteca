
package projetobiblioteca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//Calendar teste = new GregorianCalendar(2012, 1, 29);
//System.out.println(dateFormat.format(teste.getTime()));

public class ProjetoBiblioteca
{
    public static void main(String[] args) 
    {
        BufferedReader buffReader;
        List<User> userlist;
        List<Book> bookslist;
        List<Borrowing> borrowingslist;
        
        try
        {
            buffReader = new BufferedReader(new FileReader("users.txt"));
            userlist = getUsersList(buffReader);
            
            buffReader = new BufferedReader(new FileReader("books.txt"));
            bookslist = getBooksList(buffReader);
            
            buffReader = new BufferedReader(new FileReader("borrowings.txt"));
            borrowingslist = getBorrowingsList(buffReader);
 
        } catch (Exception ex) { }
        
        
    }
    
    public static List<User> getUsersList(BufferedReader buffReader) throws IOException
    {
        List<User> userlist = new ArrayList<>();
        String line;
        String usertype;
        
        while (buffReader.ready()) // enquanto tem todas as linhas do arquivo
        {
            line = buffReader.readLine();

            String[] userdata = line.split(",");
            usertype = userdata[userdata.length - 1];

            if ("T".equals(usertype))
            {
                userlist.add(
                        new Teacher(userdata[1], userdata[2], userdata[3], 
                        Integer.parseInt(userdata[4]), Integer.parseInt(userdata[5]), Integer.parseInt(userdata[0]))
                );
            }
            else if ("P".equals(usertype))
            {
                userlist.add(
                        new Person(userdata[1], userdata[2], userdata[3], 
                        Integer.parseInt(userdata[4]), Integer.parseInt(userdata[5]), Integer.parseInt(userdata[0]))
                );
            }
            else if ("S".equals(usertype))
            {
                userlist.add(
                        new Student(userdata[1], userdata[2], userdata[3], 
                        Integer.parseInt(userdata[4]), Integer.parseInt(userdata[5]), Integer.parseInt(userdata[0]))
                );
            }             
        }

        Collections.sort(userlist, new Comparator<User>()
        {
            @Override
            public int compare(User o1, User o2) 
            {
                return ((o1.getCode() < o2.getCode()) ? -1 : 1);
            }
        });
            
        return (userlist);
    }
    
    public static List<Book> getBooksList(BufferedReader buffReader) throws IOException
    {
        List<Book> bookslist = new ArrayList<>();
        String line;
        
        while(buffReader.ready())
        {
            line = buffReader.readLine();
            String[] bookdata = line.split(","); // metodo que tira as figuras e coloca em um vetor as string que estavam separadas por virgula

            // logo apos pegar as variaveis, pega os valores, cria um novo livro, e o adiciona na lista //
            bookslist.add(
                    new Book(Integer.parseInt(bookdata[5]), Integer.parseInt(bookdata[3]), 
                    Integer.parseInt(bookdata[0]), bookdata[1], bookdata[2], bookdata[4], 
                    Integer.parseInt(bookdata[7]), Integer.parseInt(bookdata[6]))
            );
        }

        Collections.sort(bookslist, new Comparator<Book>()
        {
            @Override
            public int compare(Book o1, Book o2) 
            {
                return ((o1.getCode() < o2.getCode()) ? -1 : 1);
            }
        });
            
        return (bookslist);
    }

    private static List<Borrowing> getBorrowingsList(BufferedReader buffReader) throws IOException 
    {
        List<Borrowing> borrowingslist = new ArrayList<>();
        String line;

        while(buffReader.ready())
        {
            line = buffReader.readLine();
            String[] borrowingdata = line.split(","); // metodo que tira as figuras e coloca em um vetor as string que estavam separadas por virgula

            borrowingslist.add(
                    new Borrowing(Integer.parseInt(borrowingdata[0]), 
                            Integer.parseInt(borrowingdata[1]), Integer.parseInt(borrowingdata[2]), 
                            Boolean.parseBoolean(borrowingdata[3]), borrowingdata[4], borrowingdata[5])
            );
        }

        Collections.sort(borrowingslist, new Comparator<Borrowing>()
        {
            @Override
            public int compare(Borrowing o1, Borrowing o2) 
            {
                return ((o1.getCode() < o2.getCode()) ? 1 : -1);
            }
        });
            
        return (borrowingslist);
    }
}
