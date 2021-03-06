
package projetobiblioteca;

import java.io.BufferedReader;
import java.io.File;
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
import static java.lang.StrictMath.max;


public class ProjetoBiblioteca
{
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public static void main(String[] args)
    {
        int option_int;
        
        BufferedReader buffReader;
        
        List<User> userlist = new ArrayList<>();
        List<Book> bookslist = new ArrayList<>();
        List<Login> loginlist = new ArrayList<>();
        
        List<Borrowing> borrowingslist = new ArrayList<>();
        List<Borrowing> userBorrowings;
        
        Calendar date;
        User user;
        Book book = null;
        
        Scanner input;
        String option = "-";
        long penalty;
        String borrow;
        int codeBook = -1;
        
        try
        {
            buffReader = new BufferedReader(new FileReader("users.txt"));
            userlist = getUsersList(buffReader);
            buffReader.close();
            
            buffReader = new BufferedReader(new FileReader("logins.txt"));
            loginlist = getLoginList(buffReader);
            buffReader.close();
            
            buffReader = new BufferedReader(new FileReader("books.txt"));
            bookslist = getBooksList(buffReader);
            buffReader.close();
            
            buffReader = new BufferedReader(new FileReader("borrowings.txt"));
            borrowingslist = getBorrowingsList(buffReader);
            buffReader.close();
            
        } catch (Exception ex) { }
        
        input = new Scanner (System.in);
        
        date = askDate(input);
        user = askUser(userlist, loginlist, input);
        userBorrowings = borrowedBooks(borrowingslist, user.getCode());
        
        
        //loop principal do programa: checa a opcao escolhida pelo usuario e executa de acordo
        while (!("10").equals(option))
        {
            do
            {
                printHeader();
                printOptions();
                option = input.nextLine();
                option_int = Integer.parseInt(option);
            } while ((option_int < 1) && (option_int > 10));
            
            switch (option)
            {
                case "1": 
                    /*--------- (1).  Visualizar todos livros  ---------*/
                    System.out.println("\n\n\t||-----------------------------------------||"
                                       + "\n\t||   Lista de todos livros na biblioteca   ||"
                                       + "\n\t||-----------------------------------------||\n\n");
                    
                    for (Book b : bookslist)
                        b.printBook();
                    
                    System.out.println("\n\n:::   Listagem concluída com SUCESSO  :::\n");
                    
                    break;

                case "2":
                    /*--------- (2).  Visualizar histórico de empréstimos pessoais  ---------*/
                    System.out.println("\n\n\t||-------------------------------------------------||"
                                       + "\n\t   Histórico de empréstimos do usuário: \n\t\t\t" + user.getName()
                                       + "\n\t||-------------------------------------------------||\n\n");
                    
                    for (Borrowing b : userBorrowings)
                        b.printBorrowing(user, bookslist.get(b.getCodeBook()));
                    
                    System.out.println("\n\n:::   Listagem concluída com SUCESSO  :::\n");
                    
                    break; 
                    
                case "3": 
                    /*--------- (3).  Visualizar lista de empréstimos pessoais (ainda não devolvidos)  ---------*/
                    System.out.println("\n\n\t||-------------------------------------------------||"
                                       + "\n\t   Lista de empréstimos ainda não devolvidos \n\t\t do usuário: " + user.getName()
                                       + "\n\t||-------------------------------------------------||\n\n");
                    
                    for (Borrowing b : userBorrowings)
                        if (!b.isReturned())
                            b.printBorrowing(user, bookslist.get(b.getCodeBook()));
                    
                    System.out.println("\n\n:::   Listagem concluída com SUCESSO  :::\n");
                    break;
                    
                case "4":
                    
                    penalty = ProjetoBiblioteca.penalty(borrowingslist, user.getCode(), date);
        
                    if(penalty > 0){
                        System.out.println("\nInfelizmente você tem penalidade e não pode emprestar livros de nosso acervo enquanto houver este débito\n");
                        break;
                    }
                    
                    while(true){
                        ProjetoBiblioteca.borrowABook(borrowingslist, userBorrowings, bookslist, user, book, date);
                        System.out.println("Deseja Realizar outro empréstimo?\n(S) para sim e (N) para não:");
                        if(input.nextLine().equals("N")){
                            break;
                        }
                    }
                    
                    break;
                case "5":
                    System.out.println("Qual o titulo do livro que deseja devolver?\n");
                    borrow  = input.nextLine();
                    codeBook = -1;
                    for(int i = 0; i < bookslist.size(); i++){
                        if(bookslist.get(i).getTitle().equals(borrow) && userBorrowings.get(i).isReturned()==false)
                        {
                            codeBook = bookslist.get(i).getCode();
                            break;
                        }
                    }
                    
                    if(codeBook == -1){
                        System.out.println("Livro não se encontra em sua lista de emprestimos\n");
                        break;
                    }
                    
                    ProjetoBiblioteca.returnABook(borrowingslist, userBorrowings, date, codeBook, user.getCode());
                    for(int i = 0 ; i<bookslist.size(); i++)
                    {
                        if(bookslist.get(i).getCode()==codeBook)
                        {
                            bookslist.get(i).setAvailable(bookslist.get(i).getAvailable()+1);
                            break;
                        }                            
                    }
                    System.out.println("\nDevolução efetuada com sucesso.\n");
                    
                    break;
                case "6":
                    
                    penalty = ProjetoBiblioteca.penalty(borrowingslist,user.getCode(), date);
                    
                    System.out.println("\nVocê tem " + penalty + " dias de penalidade");
                    
                    break;
                case "7":
                    /*--------- (7).  Visualizar todos usuários do sistema  ---------*/
                    System.out.println("\n\n\t||-------------------------------------------||"
                                       + "\n\t||   Lista de todos usuários da biblioteca   ||"
                                       + "\n\t||-------------------------------------------||\n\n");
                    
                    for (User u : userlist)
                        u.printUser();
                    
                    System.out.println("\n\n:::   Listagem concluída com SUCESSO  :::\n");
                    
                    break;
                    
                case "8": 
                    
                    /*--------- (8).  Visualizar histórico de empréstimos do sistema  ---------*/
                    System.out.println("\n\n\t||--------------------------------------------||"
                                       + "\n\t||   Histórico de empréstimos da biblioteca   ||"
                                       + "\n\t||--------------------------------------------||\n\n");
                    
                    for (Borrowing b : borrowingslist)
                        b.printBorrowing(userlist.get(b.getCodeUser()), bookslist.get(b.getCodeBook()));
                    
                    System.out.println("\n\n:::   Listagem concluída com SUCESSO  :::\n");
                    
                    break;
                    
                case "9":
                    
                    /*--------- (9).  Adicionar livro novo na biblioteca  ---------*/
                    System.out.println("\n\n\t||----------------------------------------||"
                                       + "\n\t||   Adicionar livro novo na biblioteca   ||"
                                       + "\n\t||----------------------------------------||\n\n");
                    
                    askBook(bookslist, input);
                    
                    System.out.println("\n\n:::   Inserção concluída com SUCESSO  :::\n");
                    
                    break;                            
            }
            
            if (!("10").equals(option))
            {
                System.out.println("\nPressione ENTER para continuar...");
                option = input.nextLine();                
                option = (option.equals("10")) ? "-" : option;
            }
        }
        
        /*---------- FINALIZANDO PROGRAMA ----------*/
        
        // excluindo todos arquivos para serem reescritos corretamente (atualizados) no fim do programa
            
            File file = new File("users.txt");
            file.delete();
            
            file = new File("logins.txt");
            file.delete();

            file = new File("borrowings.txt");
            file.delete();

            file = new File("books.txt");
            file.delete();
        
        // salvando as listas nos arquivos
        
        for (Book b : bookslist)
            b.addFileBook();
        
        for (User b : userlist)
            b.addFileUser();
        
        for (Borrowing b : borrowingslist)
            b.addFileBorrowing();
        
        for (Login b : loginlist)
            b.addFileLogin();
    }
    
    public static void printHeader()
    {
        System.out.println("\n\n\n\n\n");
        System.out.println("\t::: LORDedalus :::\n");
    }
    
    public static void printOptions()
    {
        System.out.println("(1).  Visualizar todos livros");
        System.out.println("(2).  Visualizar histórico de empréstimos pessoais");
        System.out.println("(3).  Visualizar lista de empréstimos pessoais (ainda não devolvidos)");
        System.out.println("(4).  Realizar empréstimo");
        System.out.println("(5).  Devolver livro");
        System.out.println("(6).  Verificar situação de atraso");
        System.out.println("(7).  Visualizar todos usuários da biblioteca");
        System.out.println("(8).  Visualizar histórico de empréstimos da biblioteca");
        System.out.println("(9).  Adicionar livro novo na biblioteca");
        System.out.println("(10). Sair");
    }
    
    public static Book askBook(List<Book> bookslist, Scanner input)
    {
        int code = (!bookslist.isEmpty()) ? (bookslist.get(bookslist.size() - 1).getCode() + 1) : 0;
        
        int issue, pages, quantity;
        String title, author, type;
        
        Book book;
        
        printHeader();
        
        System.out.println("Digite os dados do livro conforme o solicitado.");
            
        System.out.print("Título: ");
        title = input.nextLine();

        System.out.print("Autor: ");
        author = input.nextLine();

        System.out.print("Edição: ");
        issue = input.nextInt();
        
        System.out.println("\nO livro se encaixa em qual categoria abaixo: ");
        System.out.println("   (1). Livro-texto");
        System.out.println("   (2). Geral");
        
        do
        {
            type = input.nextLine();
        }while ((!type.equals("1")) && (!type.equals("2")));

        type = (type.equals("1")) ? "T" : "G";

        System.out.print("Quantidade de páginas: ");
        pages = input.nextInt();
        
        System.out.print("Quantidade total de cópias: ");
        quantity = input.nextInt();
        
        book = new Book(pages, issue, code, title, author, type, quantity, quantity);
        
        bookslist.add(book);
        
        return (book);
    }
    
    public static Calendar askDate(Scanner input)
    {
        int day, month, year;
        String date_choice;
        
        printHeader();
        
        System.out.println("A data utilizada será:\n"
                         + "   (1). A data atual (do sistema)\n"
                         + "   (2). A data desejada pelo usuário\n");
        
        do
        {
            date_choice = input.next();
        }while ((!date_choice.equals("1")) && (!date_choice.equals("2")));
      
        if (date_choice.equals("1"))
            return (Calendar.getInstance());
        else
        {
            System.out.println("Digite a data desejada:");
            System.out.print("   Dia: ");
            day = input.nextInt();
            System.out.print("   Mês: ");
            month = input.nextInt() - 1;
            System.out.print("   Ano: ");
            year = input.nextInt();
            System.out.println("");
            return(new GregorianCalendar(year, month, day));
        }
    }
    
    public static User askUser(List<User> userslist, List<Login> loginslist, Scanner input)
    {
        int codeUser, codeLogin;
        String name, CPF, RG, type;
        String username, password, passwordConfirm;
        String user_choice;
        User user = null;
        Login login;
        
        printHeader();
        
        System.out.println("Deseja entrar como um:\n"
                         + "   (1). Novo usuário\n"
                         + "   (2). Usuário existente\n");
        
        do
        {
            user_choice = input.nextLine();
            
            if ((user_choice.equals("2")) && userslist.isEmpty())
            {
                System.out.println("::: Não há usuários ainda, crie um novo! :::\n\n");
                user_choice = "3";
            }
                
        }while ((!user_choice.equals("1")) && (!user_choice.equals("2")));
        

        if (user_choice.equals("1"))
        {
            codeUser = (!userslist.isEmpty()) ? (userslist.get(userslist.size() - 1).getCode() + 1) : 0;
            
            System.out.println("Digite os dados pessoais conforme o solicitado.");
            
            System.out.print("Nome: ");
            name = input.nextLine();
            
            System.out.print("CPF: ");
            CPF = input.nextLine();
            
            System.out.print("RG: ");
            RG = input.nextLine();
            
            System.out.println("\nVocê se encaixa em qual categoria abaixo: ");
            System.out.println("   (1). Professor");
            System.out.println("   (2). Estudante");
            System.out.println("   (3). Outro");
            
            do
            {
                type = input.nextLine();
            }while ((!type.equals("1")) && (!type.equals("2")) && (!type.equals("3")));
            
            switch (type) 
            {
                case "1":
                    user = new Teacher(name, CPF, RG, codeUser);
                    break;
                case "2":
                    user = new Student(name, CPF, RG, codeUser);
                    break;
                case "3":
                    user = new Person(name, CPF, RG, codeUser);
                    break;
            }
            
            codeLogin = (!loginslist.isEmpty()) ? (loginslist.get(loginslist.size() - 1).getCode() + 1) : 0;
            
            System.out.println("\nDados para utilizar o sistema:");
            
            System.out.print("Nome de usuário: ");
            username = input.nextLine();
            
            do
            {
                System.out.print("Senha de login: ");
                password = input.nextLine();

                System.out.print("Confirme a senha: ");
                passwordConfirm = input.nextLine();
            
                if (!password.equals(passwordConfirm))
                    System.out.println(":::Senha incorreta! Tente novamente. :::\n");
                
            } while(!password.equals(passwordConfirm));
            
            login = new Login(codeLogin, username, password, codeUser);
            
            loginslist.add(login);
            userslist.add(user);

            System.out.println("\n::: Operação realizada com SUCESSO :::");
        }
        else
        {
            do
            {
                System.out.print("Nome de usuário: ");
                username = input.nextLine();

                System.out.print("senha: ");
                password = input.nextLine();

                user = isValidUser(loginslist, userslist, username, password);
                
                if (user == null)
                    System.out.println(":::Usuário/senha incorreto(s)! Tente novamente. :::\n");
                                
            } while (user == null);
        }
        
        if (user != null)
            System.out.println("\n\n::: Bem vindo ao LORDedalus, " + user.getName() + " :::");
        
        return(user);
    }
    
    public static void printDate(Calendar date)
    {
        System.out.println("Data: " + dateFormat.format(date.getTime()));
    }
    
    public static void newBorrowing(List<Borrowing> borrowingslist, 
            List<Borrowing> userBorrowings, User user, Book book, Calendar current)
    {
        sortBorrowingsList(borrowingslist);
        
        int code = (!borrowingslist.isEmpty()) ? (borrowingslist.get(0).getCode() + 1) : 0;
        Calendar dateMax = (Calendar) current.clone();
        dateMax.add(Calendar.DAY_OF_MONTH, user.getDaysLimit());

        Borrowing b = new Borrowing(
                code, user.getCode(), book.getCode(), false, 
                dateFormat.format(current.getTime()), "00/00/0000", 
                dateFormat.format(dateMax.getTime())
        );
        
        borrowingslist.add(b);
        userBorrowings.add(b);
        
        sortBorrowingsList(borrowingslist);
        sortBorrowingsList(userBorrowings);
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
        return ((!borrowingslist.isEmpty()) ? (borrowingslist.get(borrowingslist.size() - 1 - codeBorrowing)) : null);
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

            switch (usertype) 
            {
                case "T":
                    userlist.add(new Teacher(userdata[1], userdata[2], userdata[3], Integer.parseInt(userdata[0])));
                    break;
                case "P":
                    userlist.add(new Person(userdata[1], userdata[2], userdata[3], Integer.parseInt(userdata[0])));
                    break;
                case "S":
                    userlist.add(new Student(userdata[1], userdata[2], userdata[3], Integer.parseInt(userdata[0])));
                    break;
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
                            Boolean.parseBoolean(borrowingdata[3]), borrowingdata[4], borrowingdata[5], borrowingdata[6])
            );
        }
        
        sortBorrowingsList(borrowingslist);
        
        return (borrowingslist);
    }
    
    private static void sortBorrowingsList(List<Borrowing> list)
    {
        Collections.sort(list, new Comparator<Borrowing>()
        {
            @Override
            public int compare(Borrowing o1, Borrowing o2) 
            {
                return ((o1.getCode() < o2.getCode()) ? 1 : -1);
            }
        });
    }
    
    // Caso haja atrasado, retorna o valor, se nao retorna zero
    public static long penalty(List <Borrowing> borrowing, int codeUSer, Calendar today)
    {
        long late = 0;
        long greaterDiff = 0;
        //date1.getTime().getTime() - date2.getTime().getTime()
        long diffLate;
        long diffForward;
        
        // ordernar o vetor pela data de devolução do livro
        
        Collections.sort(borrowing, new Comparator<Borrowing>()
        {
            @Override
            public int compare(Borrowing o1, Borrowing o2) 
            {
                return (o1.getDateReturn().compareTo(o2.getDateReturn()));
            }
        });
        
        // percorre a lista de emprestimos a procura do usuario que contem
        for(Borrowing borrow : borrowing){
            //caso encontre o usuario, entra na condição para efetuar o calculo
            if(borrow.getCodeUser() == codeUSer){
                // diffLate recebe o valor da diferença da data de retorno, com a data maxima que ele pode entregar o livro
                diffLate = borrow.getDateReturn().getTime().getTime() - borrow.getDateMax().getTime().getTime();
                diffLate = (TimeUnit.DAYS.convert(diffLate, TimeUnit.MILLISECONDS));
                // diffForward recebe o valor da diferença entre o dia atual e a data de retorno do livro
                
                diffForward = today.getTime().getTime() - borrow.getDateReturn().getTime().getTime();
                diffForward = (TimeUnit.DAYS.convert(diffForward, TimeUnit.MILLISECONDS));
                
                // caso o livro seja entregue com atraso, entra nesta condição
                
                
                if(diffLate > 0 && diffLate >= diffForward){
                    greaterDiff = max(greaterDiff, diffForward); // recebe o valor da diferença máxima
                    late += diffLate; // acumula as multas
                }
            }
        }
        
        return (late == 0) ? 0 : (late - greaterDiff);
    }
    
    public static List<Borrowing> borrowedBooks(List <Borrowing> borrowing, int codeUSer)
    {
        // ordernar o vetor pela data de devolução do livro
        
        List <Borrowing> userBorrows = new ArrayList<>();
        
        // percorre a lista procurando determinado usuario
        for(Borrowing borrow : borrowing)
        {
            // caso encontre o usuario, adiciona na lista
            if(borrow.getCodeUser() == codeUSer)
            {
                userBorrows.add(borrow);
            }
        }
        
        // ordena pelo codigo do livro
        Collections.sort(userBorrows, new Comparator<Borrowing>()
        {
            @Override
            public int compare(Borrowing o1, Borrowing o2) 
            {
                return (o1.getDateMax().compareTo(o2.getDateMax()));
            }
        });
        
        // retorna a lista de emprestimos de livros
        return (userBorrows);
    }
    
    public static List<Login> getLoginList(BufferedReader buffReader) throws IOException
    {
        List<Login> loginlist = new ArrayList<>();
        String line;
        
        while(buffReader.ready())
        {
            line = buffReader.readLine();
            String[] logindata = line.split(","); // metodo que tira as figuras e coloca em um vetor as string que estavam separadas por virgula

            loginlist.add(
                    new Login(Integer.parseInt(logindata[0]),logindata[1], 
                    logindata[2], Integer.parseInt(logindata[3]))
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
    
    public static User isValidUser(List<Login> loginlist, List<User> userlist, String user, String password)
    {
        if ((loginlist == null) || (userlist == null))
            return (null);
        
        for (Login login : loginlist)
            if (login.getUser().equals(user) && login.getPassword().equals(password)) 
                return(userlist.get(login.getCodeUser()));               

        return (null);  
    }
    
    public static void borrowABook(List <Borrowing> borrowingslist, List<Borrowing> userBorrowings, List<Book> bookslist,
                                      User user, Book book, Calendar date){
     
        
        System.out.println("\nDigite o titulo do livro solicitado:");
        
        Scanner input = new Scanner (System.in);
        
        for(Book b : bookslist){
            if(b.getTitle().equals(input.nextLine())){
                book = b;
                break;
            }
        }

        if(book != null && book.getAvailable() > 0){
            System.out.println("\nÉ este o livro desejado? " + "\nTitulo: " + book.getTitle() + "\nAutor: " + book.getAuthor() + "\n"
                    + "(S) para sim e (N) para não\n");
            if(input.nextLine().equals("S")){
                book.setAvailable(book.getAvailable()-1); // retira um livro da biblioteca
                ProjetoBiblioteca.newBorrowing(borrowingslist, userBorrowings, user, book, date);
                System.out.println("\nEmpréstimo feito com sucesso\n");
            }
        }
        else if(book == null){
            System.out.println("\nLivro não existe em nosso ainda acervo\n");
        }
        else if(book.getAvailable() <= 0){
            System.out.println("\nTodos os livros deste titulo em novo acervo foram emprestados");
        }
    }
    
    public static void returnABook(List <Borrowing> borrowingslist, List <Borrowing> userborrowings, Calendar date, int codeBook, int codeUser){
       
        for(int i = 0; i < userborrowings.size(); i++){
            if(userborrowings.get(i).getCode() == codeBook && userborrowings.get(i).getCodeUser() == codeUser){
                userborrowings.get(i).setDateReturn(date);
                userborrowings.get(i).setReturned(true);
            }
        }
       
        for(int i = 0; i < borrowingslist.size(); i++){
            if(borrowingslist.get(i).getCode() == codeBook && borrowingslist.get(i).getCodeUser()== codeUser){
                borrowingslist.get(i).setDateReturn(date);
                borrowingslist.get(i).setReturned(true);
            }
       }
    }
}
