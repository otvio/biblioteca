
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
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//Calendar teste = new GregorianCalendar(2012, 1, 29);
//System.out.println(dateFormat.format(teste.getTime()));

public class ProjetoBiblioteca
{
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public static void main(String[] args) 
    {
        String date_choice;
        
        BufferedReader buffReader;
        List<User> userlist;
        List<Book> bookslist;
        List<Borrowing> borrowingslist;
        List <Login> loginlist;
        
        Calendar date;
        int day, month, year;
        
        Scanner input = new Scanner (System.in);
        
        try
        {
            buffReader = new BufferedReader(new FileReader("users.txt"));
            userlist = getUsersList(buffReader);
            
            buffReader = new BufferedReader(new FileReader("books.txt"));
            bookslist = getBooksList(buffReader);
            
            buffReader = new BufferedReader(new FileReader("borrowings.txt"));
            borrowingslist = getBorrowingsList(buffReader);
            
            buffReader = new BufferedReader(new FileReader("logins.txt"));
            loginlist = getLoginList (buffReader);
 
        } catch (Exception ex) { }
        
        System.out.println("Bem vindo ao programa LORDedalus.\n");
        
        System.out.println("A data utilizada será:\n"
                         + "   1. A data atual (do sistema)\n"
                         + "   2. A data desejada pelo usuário\n");
        
        do
        {
            date_choice = input.next();
        }while ((!date_choice.equals("1")) && (!date_choice.equals("2")));
      
        if (date_choice.equals("1"))
            date = Calendar.getInstance();
        else
        {
            System.out.println("Digite a data desejada:");
            System.out.print("   Dia: ");
            day = input.nextInt();
            System.out.print("   Mes: ");
            month = input.nextInt() - 1;
            System.out.print("   Ano: ");
            year = input.nextInt();
            
            date = new GregorianCalendar(year, month, day);
        }
    }
    
    public static long getDateDiff(Calendar date1, Calendar date2) 
    {
        long diff = date1.getTime().getTime() - date2.getTime().getTime();
        return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }
    
    public static boolean isLate(Borrowing borrowing, Calendar current)
    {
        return (current.compareTo(borrowing.getDateReturn()) > 0);
    }
    
    public static Borrowing borrowingAt(List<Borrowing> borrowingslist, int codeBorrowing)
    {
        return (borrowingslist.get(borrowingslist.size() - 1 - codeBorrowing));
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
    
    public static List<Login> getLoginList(BufferedReader buffReader) throws IOException
    {
        List<Login> loginlist = new ArrayList<>();
        String line;
        
        while(buffReader.ready())
        {
            line = buffReader.readLine();
            String[] logindata = line.split(","); // metodo que tira as figuras e coloca em um vetor as string que estavam separadas por virgula

            // logo apos pegar as variaveis, pega os valores, cria um novo livro, e o adiciona na lista //
            loginlist.add(
                    new Login(Integer.parseInt(logindata[0]),logindata[1], logindata[2])
            );
        }

        Collections.sort(loginlist, new Comparator<Login>()
        {
            @Override
            public int compare(Login o1, Login o2) 
            {
                return ((o1.getCode() < o2.getCode()) ? -1 : 1);
            }
        });            
        return (loginlist);
    } 
    
    public static boolean isUser(List<Login> loginlist, String user, String password)
    {
        for (Login login : loginlist)
        {
            if (login.getUser().equals(user) && login.getPassword().equals(password))            
                return(true);               
        }
        return (false);  
    }  
}
