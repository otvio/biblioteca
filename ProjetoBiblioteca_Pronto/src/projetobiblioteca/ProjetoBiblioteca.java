
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
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");   // Pega a data do sistema
    
    public static void main(String[] args)
    {
        int codeBook;           // Variável para o código do livro
        long penalty;           // Variável para a penalidade do usuário quando houver atrasos na devolução
        boolean readOnly;       // Variável para verificação se será somente leitura (caso seja uma data passada) ou se o sistema funcionará na sua forma casual.
        
        BufferedReader buffReader;      // Objeto para ler os arquivos
        
        List<User> userlist = new ArrayList<>();    // Lista para armazenar os usuários
        List<Book> bookslist = new ArrayList<>();   // Lista para armazenar os livros
        List<Login> loginlist = new ArrayList<>();  // Lista para armazenar os logins dos usuários
        
        List<Borrowing> borrowingslist = new ArrayList<>(); // Lista para armazenar os empréstimos
        List<Borrowing> userBorrowings;                     // Lista para armazenar os empréstimos dos usuários
        
        Calendar date;                              // Objeto para uma data pré-determinada
        Calendar today = Calendar.getInstance();    // Objeto para pegar a data atual
        
        User user;
        Book book = null;
        
        Scanner input;        // Objeto para pegar dados de entrada
        String option = "-";
        String borrow;
        
        
        try
        {
            buffReader = new BufferedReader(new FileReader("users.csv")); // Leitura dos usuários no arquivo
            userlist = getUsersList(buffReader);                          // Armazena os usuários na lista de usuários
            buffReader.close();
            
            buffReader = new BufferedReader(new FileReader("logins.csv")); // Leitura dos logins no arquivo
            loginlist = getLoginList(buffReader);                          // Armazena os logins na lista de logins
            buffReader.close();
            
            buffReader = new BufferedReader(new FileReader("books.csv")); // Leitura dos livros armazenados no arquivo
            bookslist = getBooksList(buffReader);                         // Armazena os livros na lista de livros
            buffReader.close();
            
            buffReader = new BufferedReader(new FileReader("borrowings.csv")); // Leitura dos empréstimos feitos no arquivo
            borrowingslist = getBorrowingsList(buffReader);                    // Armazena os empréstimos na lista de empréstimos
            buffReader.close();
            
        } catch (Exception ex) { }
        
        input = new Scanner (System.in);
                
        for (Borrowing b : borrowingslist)
            today = max(today, max(b.getDateBorrow(), b.getDateReturn()));
        
        date = askDate(input);                   // O objeto recebe a data atual do sistema ou uma determinada pelo usuário. Isso depende de cada usuário
        
        if (date == null)
            date = today;
        
        System.out.println("Hoje é: " + dateFormat.format(date.getTime()) + "\n");
        
        readOnly = (date.compareTo(today) < 0);  // Se a data fornecida for uma data passada ai será ativado só o modo leitura para o usuário
        
        user = askUser(userlist, loginlist, input);  // Recebera um usuário pré-existente ou um recém criado
        userBorrowings = borrowedBooks(borrowingslist, user.getCode());  // Lista de empréstimos do usuário
        
        //loop principal do programa: checa a opcao escolhida pelo usuario e executa de acordo
        while (!("10").equals(option))
        {
            do
            {
                printHeader();
                printOptions();
                option = input.nextLine();
            } while (!option.equals("1") && !option.equals("2") && !option.equals("3") 
                    && !option.equals("4") && !option.equals("5") && !option.equals("6") 
                    && !option.equals("7") && !option.equals("8") && !option.equals("9")
                    && !option.equals("10"));
            
            if ((readOnly) && (option.equals("4") || option.equals("5") || option.equals("9")))
            {
                System.out.println("\n::: Opção desabilitada quando acessada no passado :::\n");
                System.out.println("\nPressione ENTER para continuar...");
                option = input.nextLine();                
                option = (option.equals("10")) ? "-" : option;
                continue;
            }
            
            switch (option)
            {
                case "1": 
                    /*--------- (1).  Visualizar todos livros  ---------*/
                    System.out.println("\n\n\t||-----------------------------------------||"
                                       + "\n\t||   Lista de todos livros na biblioteca   ||"
                                       + "\n\t||-----------------------------------------||\n\n");
                    
                    for (Book b : bookslist) // Loop para imprimir todos os livros disponiveis
                        b.printBook();
                    
                    if (!bookslist.isEmpty()) 
                        System.out.println("\n\n:::   Listagem concluída com SUCESSO  :::\n");
                    else
                        System.out.println("\n\n:::   Nada a ser listado  :::\n");  // Caso a lista esteja vazia então não existe livros disponiveis
                    
                    break;

                case "2":
                    /*--------- (2).  Visualizar histórico de empréstimos pessoais  ---------*/
                    System.out.println("\n\n\t||-------------------------------------------------||"
                                       + "\n\t   Histórico de empréstimos do usuário: \n\t\t\t" + user.getName()
                                       + "\n\t||-------------------------------------------------||\n\n");
                    
                    for (Borrowing b : userBorrowings)   // Loop para mostrar o histórico de empréstimo de um determinado usuário
                        if (date.compareTo(b.getDateBorrow()) >= 0)
                            b.printBorrowing(user, bookslist.get(b.getCodeBook()));
                    
                    if (!userBorrowings.isEmpty())
                        System.out.println("\n\n:::   Listagem concluída com SUCESSO  :::\n");
                    else
                        System.out.println("\n\n:::   Nada a ser listado  :::\n");  // Caso o usuário não tenha efetuado nenhum empréstimo nada será listado
                    
                    break; 
                    
                case "3": 
                    /*--------- (3).  Visualizar lista de empréstimos pessoais (ainda não devolvidos)  ---------*/
                    System.out.println("\n\n\t||-------------------------------------------------||"
                                       + "\n\t   Lista de empréstimos ainda não devolvidos \n\t\t do usuário: " + user.getName()
                                       + "\n\t||-------------------------------------------------||\n\n");
                    
                    for (Borrowing b : userBorrowings)  // Caso ainda não tenha sido devolvido o livro será mostrado
                        if ((!b.isReturned()) && (date.compareTo(b.getDateBorrow()) >= 0))
                            b.printBorrowing(user, bookslist.get(b.getCodeBook()));
                    
                    if (!userBorrowings.isEmpty())
                        System.out.println("\n\n:::   Listagem concluída com SUCESSO  :::\n");
                    else
                        System.out.println("\n\n:::   Nada a ser listado  :::\n"); // Caso não exista nenhuma devolução pendente então nada será mostrado
                    
                    break;
                    
                case "4":
                    
                    penalty = ProjetoBiblioteca.penalty(borrowingslist, user.getCode(), date, user);
        
                    if(penalty > 0)  // Verifica se o usuário tem alguma penalidade para não ser autorizado a efetuar um novo empréstimo
                    {
                        System.out.println("\nInfelizmente você tem penalidade de " + penalty + 
                                           " dia(s), e não pode emprestar livros de nosso acervo enquanto houver este débito\n");
                        break;
                    }
                    
                    while(true)  //Loop para efetuar os empréstimo dos livros
                    {
                        ProjetoBiblioteca.borrowABook(borrowingslist, userBorrowings, bookslist, user, book, date);
                        System.out.println("Deseja Realizar outro empréstimo?\n(S) para sim e (N) para não:");
                        if(!input.nextLine().toUpperCase().equals("S")){
                            break;
                        }
                    }
                    
                    break;
                    
                case "5":
                    
                    System.out.println("Qual o titulo do livro que deseja devolver?\n");
                    borrow  = input.nextLine();    // Nome do livro a ser devolvido
                    codeBook = -1;
                    
                    if (!userBorrowings.isEmpty() && !bookslist.isEmpty())
                    {
                        for(Borrowing b: userBorrowings)
                        {
                            if(bookslist.get(b.getCodeBook()).getTitle().equals(borrow) && b.isReturned() == false) // Se for o livro e ainda não foi devolvido então é pego o código do livro
                            {
                                codeBook = b.getCodeBook();
                                break;
                            }
                        }
                        
                    }
                    else
                    {
                        System.out.println("::: Não foi possível concluir a operação solicitada! :::\n");
                        break;
                    }
                    
                    if (returnABook(borrowingslist, userBorrowings, 
                            date, codeBook, user.getCode(), userlist, bookslist) == true)  // É chamado o método para efetuar a devolução
                    {
                        for (Book bookslist1 : bookslist)   // Loop para devolver a quantidade de livros disponiveis na bibliteca após a devolução
                        {
                            if (bookslist1.getCode() == codeBook) 
                            {
                                bookslist1.setAvailable(bookslist1.getAvailable() + 1);
                                break;
                            }
                        }

                        System.out.println("\n::: Devolução efetuada com SUCESSO. :::\n");
                    }
                    else
                        System.out.println("\n::: Operação NÃO REALIZADA com sucesso! :::\n");
                    
                    break;
                    
                case "6":
                    
                    penalty = ProjetoBiblioteca.penalty(borrowingslist,user.getCode(), date, user); // Verifica a situação  de atraso do usuário
                    
                    System.out.println("\nVocê tem " + penalty + " dias de penalidade");
                    
                    break;
                    
                case "7":
                    /*--------- (7).  Visualizar todos usuários do sistema  ---------*/
                    System.out.println("\n\n\t||-------------------------------------------||"
                                       + "\n\t||   Lista de todos usuários da biblioteca   ||"
                                       + "\n\t||-------------------------------------------||\n\n");
                    
                    for (User u : userlist)  // Loop para mostrar todos os usuários cadastrados na biblioteca
                        u.printUser();
                    
                    if (!userlist.isEmpty())
                        System.out.println("\n\n:::   Listagem concluída com SUCESSO  :::\n");
                    else
                        System.out.println("\n\n:::   Nada a ser listado  :::\n"); // Caso não exista nenhum cadastro na biblioteca
                    
                    break;
                    
                case "8": 
                    
                    /*--------- (8).  Visualizar histórico de empréstimos do sistema  ---------*/
                    System.out.println("\n\n\t||--------------------------------------------||"
                                       + "\n\t||   Histórico de empréstimos da biblioteca   ||"
                                       + "\n\t||--------------------------------------------||\n\n");
                    sortBorrowingsList(borrowingslist);
                    for (Borrowing b : borrowingslist)  // Loop para verificar os livros emprestado na biblioteca
                        if (date.compareTo(b.getDateBorrow()) >= 0)
                            b.printBorrowing(userlist.get(b.getCodeUser()), bookslist.get(b.getCodeBook()));
                    
                    if (!borrowingslist.isEmpty())
                        System.out.println("\n\n:::   Listagem concluída com SUCESSO  :::\n");
                    else
                        System.out.println("\n\n:::   Nada a ser listado  :::\n"); // Caso não tenha sido feito nenhum empréstimo
                    
                    break;
                    
                case "9":
                    
                    /*--------- (9).  Adicionar livro novo na biblioteca  ---------*/
                    System.out.println("\n\n\t||----------------------------------------||"
                                       + "\n\t||   Adicionar livro novo na biblioteca   ||"
                                       + "\n\t||----------------------------------------||\n\n");
                    
                    if (askBook(bookslist, input) != null)  // Chama o método para adicionar um novo livro
                        System.out.println("\n\n:::   Inserção concluída com SUCESSO  :::\n");
                    else
                        System.out.println("\n\n:::   Inserção NÃO REALIZADA com sucesso.  :::\n");
                    
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
            
            File file = new File("users.csv");
            file.delete();
            
            file = new File("logins.csv");
            file.delete();

            file = new File("borrowings.csv");
            file.delete();

            file = new File("books.csv");
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
    
    public static Calendar max(Calendar a, Calendar b)
    {
        return ((a.compareTo(b) < 0) ? b : a);
    }
    
    public static void printHeader()  // Método do cabeçalho do nome do sistema
    {
        System.out.println("\n\n\n\n\n");
        System.out.println("\t::: LORDedalus :::\n");
    }
    
    public static void printOptions()  // Menu inicial do sistema
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
        int code = (!bookslist.isEmpty()) ? (bookslist.get(bookslist.size() - 1).getCode() + 1) : 0; // Código do livro a partir do tamanho da lista de livros
        
        int issue, pages, quantity;   // Variáveis para as informações do livro
        String title, author, type;   // Strings para armazenar as informações do livro
        
        String option_existingBook;
        
        Book book;                    // Livro  a ser criado
        
        printHeader();
        
        System.out.println("Digite os dados do livro conforme o solicitado.");
            
        System.out.print("Título: ");
        title = input.nextLine();    // Recebe o título do livro

        System.out.print("Autor: ");
        author = input.nextLine();  // Recebe o autor do livro

        System.out.print("Edição: ");
        issue = input.nextInt();   // Recebe a edição do livro
        
        System.out.println("\nO livro se encaixa em qual categoria abaixo: "); // Define a categoria do livro
        System.out.println("   (1). Livro-texto");
        System.out.println("   (2). Geral");
        
        do
        {
            type = input.nextLine();
        }while ((!type.equals("1")) && (!type.equals("2")));

        type = (type.equals("1")) ? "T" : "G";   // Se o tipo for 1 ele será livro-texto , caso contrário será tipo geral

        System.out.print("Quantidade de páginas: ");
        pages = input.nextInt();     // Recebe a quantidade de páginas do livro
        
        System.out.print("Quantidade total de cópias: ");
        quantity = input.nextInt(); // Quantos livros terá no sistema
        
        book = new Book(pages, issue, code, title, author, type, quantity, quantity); // Novo livro criado
        
        for (Book b : bookslist)
        {
            if (b.compareTo(book) == true)
            {
                System.out.println("\n::: O livro já existe! Deseja somar na quantidade de exemplares? :::\n");
                System.out.println("(S) para sim e (N) para não: ");
                do
                {
                    option_existingBook = input.nextLine();
                } while ((!option_existingBook.toUpperCase().equals("S")) && (!option_existingBook.toUpperCase().equals("N")));
                
                if (option_existingBook.toUpperCase().equals("S"))
                {
                    b.setQuantity(b.getQuantity() + book.getQuantity());
                    b.setAvailable(b.getAvailable() + book.getQuantity());
                    
                    return (b);
                }
                else
                    return (null);
            }
        }
        
        bookslist.add(book);  // Livro adicionado no sistema
        
        return (book);
    }
    
    public static Calendar askDate(Scanner input) // Método para adquirir a data do sistema
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
        }while ((!date_choice.equals("1")) && (!date_choice.equals("2"))); // Loop para escolher somente uma das duas opções disponiveis
      
        if (date_choice.equals("1"))
            return (null);       // Caso a opção seja 1 então será retornada a data atual do sistema
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
            
            if ((day < 0) || (month < 0) || (year < 0))
            {
                System.out.println("\n::: Data inválida! A data do sistema será a data atual. :::\n");
                return (null); 
            }
            else
                return (new GregorianCalendar(year, month, day)); // Caso a opção seja 2 a data que será retornada será a fornecida pelo usuário
        }
    }
    
    public static User askUser(List<User> userslist, List<Login> loginslist, Scanner input)
    {
        int codeUser, codeLogin;   // Variáveis para os dados pessoais do usuário
        String name, CPF, RG, type; // Strings para os dados pessoais do usuário
        String username, password, passwordConfirm; // Strings para a confirmação de acesso do usuário
        String user_choice;  // String para a opção de acesso no sistema
        User user = null;
        Login login;
        
        printHeader();
        
        System.out.println("Deseja entrar como um:\n"
                         + "   (1). Novo usuário\n"
                         + "   (2). Usuário existente\n");
        
        do
        {
            user_choice = input.nextLine();
            
            if ((user_choice.equals("2")) && userslist.isEmpty()) // Caso ainda não exista nenhum usuário na lista de usuários
            {
                System.out.println("::: Não há usuários ainda, crie um novo! :::\n\n");
                user_choice = "3";
            }
                
        }while ((!user_choice.equals("1")) && (!user_choice.equals("2"))); // Loop para escolher uma das duas opções
        

        if (user_choice.equals("1"))  // Caso a opção escolhida seja 1, então será adicionado um novo usuário ao sistema
        {
            codeUser = (!userslist.isEmpty()) ? (userslist.get(userslist.size() - 1).getCode() + 1) : 0;  // Código que será adicionado para o usuário
            
            System.out.println("Digite os dados pessoais conforme o solicitado.");
            
            System.out.print("Nome: ");
            name = input.nextLine();   // Armazena o nome fornecido pelo usuário 
            
            System.out.print("CPF: ");
            CPF = input.nextLine();   // Fornece o CPF fornecido pelo usuário
            
            System.out.print("RG: ");
            RG = input.nextLine();    // Fornece o RG fornecido pelo usuário
            
            System.out.println("\nVocê se encaixa em qual categoria abaixo: ");  // Pergunta qual é o perfil que o usuário pré cadastrado se encaixa
            System.out.println("   (1). Professor");
            System.out.println("   (2). Estudante");
            System.out.println("   (3). Outro");
            
            do
            {
                type = input.nextLine();
            }while ((!type.equals("1")) && (!type.equals("2")) && (!type.equals("3"))); // Loop para a escolha de uma entre as três opções disponiveis
            
            switch (type) 
            {
                case "1":
                    user = new Teacher(name, CPF, RG, codeUser);  // Se for tipo 1 o usuário será do tipo professor
                    break;
                case "2":
                    user = new Student(name, CPF, RG, codeUser);  // Se for  tipo 2 o usuário será do tipo estudante
                    break;
                case "3":
                    user = new Person(name, CPF, RG, codeUser);  // Se for tipo 3 o usuário será do tipo de uma pessoa em geral
                    break;
            }
            
            codeLogin = (!loginslist.isEmpty()) ? (loginslist.get(loginslist.size() - 1).getCode() + 1) : 0;  // Código de login do usuário conforme a lista de login
            
            System.out.println("\nDados para utilizar o sistema:");
            
            System.out.print("Nome de usuário: ");
            username = input.nextLine(); // Irá armazenar o username do usuário pré-criado
            
            do
            {
                System.out.print("Senha de login: ");
                password = input.nextLine();  // Irá armazenar a senha pré criada pelo usuário

                System.out.print("Confirme a senha: ");
                passwordConfirm = input.nextLine();  // Confirmação da senha pré fornecida pelo usuário
            
                if (!password.equals(passwordConfirm))
                    System.out.println(":::Senha incorreta! Tente novamente. :::\n");
                
            } while(!password.equals(passwordConfirm));  //Loop para o usuário digitar e confirmar as senhas corretas
            
            login = new Login(codeLogin, username, password, codeUser);  // Criando o login para o usuário
            
            loginslist.add(login);   // Adicionando o login usuário criado na lista de login
            userslist.add(user);     // Adicionando o usuário criado na lista de usuário

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

                user = isValidUser(loginslist, userslist, username, password);  // Verifica se o usuário já existe no sistema
                
                if (user == null)  // Caso o usuário ou senha seja digitada errada ele informa que deve ser repetida a operação
                    System.out.println(":::Usuário/senha incorreto(s)! Tente novamente. :::\n");
                                
            } while (user == null); // Loop para o usuário digitar corretamente seu username e password
        }
        
        if (user != null)
            System.out.println("\n\n::: Bem vindo ao LORDedalus, " + user.getName() + " :::");
        
        return(user);
    }
    
    public static void printDate(Calendar date)  // Método para imrpimir a data do sistema
    {
        System.out.println("Data: " + dateFormat.format(date.getTime()));
    }
    
    public static void newBorrowing(List<Borrowing> borrowingslist, 
            List<Borrowing> userBorrowings, User user, Book book, Calendar current)  // Método para um novo empréstimo do livro
    {
        sortBorrowingsList(borrowingslist);  // Ordena a lista de empréstimos
        
        int code = (!borrowingslist.isEmpty()) ? (borrowingslist.get(0).getCode() + 1) : 0;
        Calendar dateMax = (Calendar) current.clone();
        dateMax.add(Calendar.DAY_OF_MONTH, user.getDaysLimit());  // Data maxima de entrega do usuário

        Borrowing b = new Borrowing(
                code, user.getCode(), book.getCode(), false, 
                dateFormat.format(current.getTime()), "00/00/0000", 
                dateFormat.format(dateMax.getTime())
        );
        
        borrowingslist.add(b);  // Atualizando a lista de empréstimos
        userBorrowings.add(b); // Atualizando a lista de empréstimos do usuário
        
        sortBorrowingsList(borrowingslist);  // Ordena a lista de empréstimos
        sortBorrowingsList(userBorrowings); // Ordena a lista de empréstimos do usuário
    }
    
    public static long getDateDiff(Calendar date1, Calendar date2)  // Método para verificar a diferença de datas para as penalidades
    {
        long diff = date1.getTime().getTime() - date2.getTime().getTime();
        return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }
    
    public static boolean isLate(Borrowing borrowing, Calendar current)  // Método para verificar se a devolução está atrasada
    {
        return (current.compareTo(borrowing.getDateReturn()) > 0);
    }
    
    public static Borrowing borrowingAt(List<Borrowing> borrowingslist, int codeBorrowing)
    {
        return ((!borrowingslist.isEmpty()) ? (borrowingslist.get(borrowingslist.size() - 1 - codeBorrowing)) : null);
    }
    
    public static List<User> getUsersList(BufferedReader buffReader) throws IOException  // Irá criar uma lista de usuários a partir dos dados do arquivo
    {
        List<User> userlist = new ArrayList<>();  // Lista de usuário
        String line;                            // String para a linha do arquivo de dados
        String usertype;                
        
        while (buffReader.ready()) // enquanto tem todas as linhas do arquivo
        {
            line = buffReader.readLine(); // Pega os dados vindo do buffReader

            String[] userdata = line.split(",");  // Método que coloca em um vetor as string que estavam separadas por virgula
            usertype = userdata[userdata.length - 1];  // Verifica o tipo do usuário para adicionar na lista especifica

            switch (usertype) 
            {
                case "T":    // Se for do tipo T será adicionado na lista de professores
                    userlist.add(new Teacher(userdata[1], userdata[2], userdata[3], Integer.parseInt(userdata[0])));
                    break;
                case "P":    // Se for do tipo P será adicionado na lista de professores
                    userlist.add(new Person(userdata[1], userdata[2], userdata[3], Integer.parseInt(userdata[0])));
                    break;
                case "S":   // Se for do tipo S será adicionado na lista de professores
                    userlist.add(new Student(userdata[1], userdata[2], userdata[3], Integer.parseInt(userdata[0])));
                    break;
            }  
        }

        Collections.sort(userlist, new Comparator<User>()   // Ordenação da lista de usuários
        {
            @Override
            public int compare(User o1, User o2) 
            {
                return ((o1.getCode() < o2.getCode()) ? -1 : 1);
            }
        });
        
        return (userlist);
    }
    
    public static List<Book> getBooksList(BufferedReader buffReader) throws IOException  // Irá pegar do arquivo a lista de livros existentes
    {
        List<Book> bookslist = new ArrayList<>();
        String line;
        
        while(buffReader.ready())
        {
            line = buffReader.readLine();
            String[] bookdata = line.split(","); // Método que coloca em um vetor as string que estavam separadas por virgula

            // logo apos pegar as variaveis, pega os valores, cria um novo livro, e o adiciona na lista //
            bookslist.add(
                    new Book(Integer.parseInt(bookdata[5]), Integer.parseInt(bookdata[3]), 
                    Integer.parseInt(bookdata[0]), bookdata[1], bookdata[2], bookdata[4], 
                    Integer.parseInt(bookdata[7]), Integer.parseInt(bookdata[6]))
            );
        }

        Collections.sort(bookslist, new Comparator<Book>()  // Ordena a lista de livros
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
            String[] borrowingdata = line.split(","); // Método que coloca em um vetor as string que estavam separadas por virgula

            borrowingslist.add(
                    new Borrowing(Integer.parseInt(borrowingdata[0]), 
                            Integer.parseInt(borrowingdata[1]), Integer.parseInt(borrowingdata[2]), 
                            Boolean.parseBoolean(borrowingdata[3]), borrowingdata[4], borrowingdata[5], borrowingdata[6])
            );
        }
        
        sortBorrowingsList(borrowingslist);  // Ordena a lista de empréstimos
        
        return (borrowingslist);
    }
    
    private static void sortBorrowingsList(List<Borrowing> list)  // Método para a ordenação da lista de empréstimos
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
    public static long penalty(List <Borrowing> borrowing, int codeUSer, Calendar today, User user)
    {
        long late = 0;
        long greaterDiff = 0;
        //date1.getTime().getTime() - date2.getTime().getTime()
        long diffLate;
        long diffMiddle;
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
                
                diffMiddle = today.getTime().getTime() - borrow.getDateMax().getTime().getTime();
                diffMiddle = (TimeUnit.DAYS.convert(diffMiddle, TimeUnit.MILLISECONDS));
                //System.out.println(diffMiddle);
                // caso o livro seja entregue com atraso, entra nesta condição
                
                
                if(diffLate > 0 && diffLate >= diffForward && (diffForward >= 0))
                {
                    greaterDiff = StrictMath.max(greaterDiff, diffForward); // recebe o valor da diferença máxima
                    late += diffLate; // acumula as multas
                }
                else if(diffMiddle > 0 && borrow.isReturned() == false)
                    late += diffMiddle;
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
            String[] logindata = line.split(","); // Método que coloca em um vetor as string que estavam separadas por virgula

            loginlist.add(
                    new Login(Integer.parseInt(logindata[0]),logindata[1], 
                    logindata[2], Integer.parseInt(logindata[3]))
            );
        }

        Collections.sort(loginlist, new Comparator<Login>()  // Ordenação da lista de login
        {
            @Override
            public int compare(Login o1, Login o2) 
            {
                return ((o1.getCode() < o2.getCode()) ? -1 : 1);
            }
        });   
        
        return (loginlist);
    }
    
    public static User isValidUser(List<Login> loginlist, List<User> userlist, String user, String password) // Método para verificar a existência do usuário no sistema
    {
        if ((loginlist == null) || (userlist == null))  // Caso a lista de login ou a lista de usuários não exista é retornado null
            return (null);
        
        for (Login login : loginlist)  // Loop para verificar se existe o usuário solicitado no sistema
            if (login.getUser().equals(user) && login.getPassword().equals(password)) 
                return(userlist.get(login.getCodeUser()));               

        return (null);  // Retorna null caso não ache o usuário
    }
    
    public static void borrowABook(List <Borrowing> borrowingslist, List<Borrowing> userBorrowings, List<Book> bookslist,
                                      User user, Book book, Calendar date)  // Método para emprestar um livro
    {
     
        int totalNotReturned = 0;
        String choice, bookTitle;
        System.out.println("\nDigite o título do livro solicitado:");
        
        Scanner input = new Scanner (System.in);
        bookTitle = input.nextLine();   // Armazena o nome do livro que será solicitado para empréstismo
        
        if (bookTitle.length() == 0)
        {
            System.out.println("\n::: Nome inválido! :::\n");
            return ;
        }
        
        for(Book b : bookslist)  // Loop para achar o livro solicitado
        {
            if(b.getTitle().equals(bookTitle))
            {
                book = b;
                break;
            }
        }
        
        for (Borrowing b : userBorrowings)  // Loop para verificar se o livro já está emprestado para o usuário
        {
            //b.printBorrowing(userlist.get(b.getCodeUser()), bookslist.get(b.getCodeBook()));
            if(b.getCodeBook() == book.getCode() && b.getCodeUser() == user.getCode() && b.isReturned() == false)
            {
                System.out.println("\n::: Você já possui este empréstimo, tente pegar outro livro! ::: \n");
                return;
            }
        }
        
        for (Borrowing b : userBorrowings) // Loop para verificar os livros que não foram retornados pelo usuário
            if (!b.isReturned())
                totalNotReturned++;
        
        if(book != null && book.getAvailable() > 0 && user.getBookLimit() > totalNotReturned) // Verifica se o livro solicitado está disponível
        {
            System.out.println("\nÉ este o livro desejado? " + "\nTítulo: " + book.getTitle() + "\nAutor: " + book.getAuthor() + "\n"
                    + "(S) para sim e (N) para não\n");
            
            do
            {
                choice = input.nextLine();
            }while ((!choice.toUpperCase().equals("S")) && (!choice.toUpperCase().equals("N"))); // Loop para escolher somente uma das duas opções disponiveis
            
            if(choice.toUpperCase().equals("S"))  // Caso seja escolhido 'Sim' o livro é adicionado conforme o tipo do usuário
            {
                if (book.getType().equals("T") && user.getType().equals("P"))
                {
                    System.out.println("\n\t\t::: Empréstimo NÃO REALIZADO com sucesso :::\n"
                                     + "\n::: Usuários do tipo Pessoa (comunidade) não podem emprestar livros-texto! :::\n");
                }
                else
                {
                    book.setAvailable(book.getAvailable() - 1); // decrementa a qtd disponivel do livro na biblioteca
                    newBorrowing(borrowingslist, userBorrowings, user, book, date);
                    System.out.println("\n::: Empréstimo feito com SUCESSO :::\n");
                }
            }
            else  // Caso o usuário não queira efetuar o empréstimo
                System.out.println("\n::: Empréstimo NÃO REALIZADO com sucesso :::\n");
        }
        
        else if(book == null) // Caso o livro não exista ainda no sistema
        {
            System.out.println("\n::: Livro ainda não existe em nosso acervo :::\n");
        }
        
        else if(book.getAvailable() == 0)  // Caso se o livro não esteja disponivel no sistema
        {
            System.out.println("\n::: Todos os livros deste título em novo acervo foram emprestados :::\n");
        }
        
        else if(user.getBookLimit() == userBorrowings.size()) // Caso o limite de empréstimo do usuário seja atingido ele não será autorizado a efetuar o empréstimo
        {
            System.out.println("\n::: Você atingiu o limite de " + user.getBookLimit() + " livros no momento :::\n");
        }
    }
    
    public static boolean returnABook(List <Borrowing> borrowingslist, List <Borrowing> userborrowings, Calendar date, int codeBook, int codeUser, List <User> userlist, List <Book> bookslist)
    {  // Método para a devolução de um livro
       
        Borrowing borrowing = null;
        
        for (Borrowing b : userborrowings){
            //b.printBorrowing(userlist.get(b.getCodeUser()), bookslist.get(b.getCodeBook()));
            if(b.getCodeBook() == codeBook && b.getCodeUser() == codeUser && b.isReturned() == false) // Verifica se é o livro e se ele não foi devolvido
                borrowing = b;
        }
        if (borrowing != null)
        {
            if (date.compareTo(borrowing.getDateBorrow()) < 0) // se está visitando o sistema no passado
            {
            }
            else
            {
                borrowing.setDateReturn(date);  // Seta a data de devolução 
                borrowing.setReturned(true);    // E seta o retorno como verdadeiro
                
                return (true);
            }
        }
        return (false);
    }
}
