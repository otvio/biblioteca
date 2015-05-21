
package javafxapplication2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaFXApplication2 extends Application 
{
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'qqqqqqqqqqqqqqqqqqqqqqqqqq");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static class Testando implements Serializable
    {
        int k;
        public Testando(int x)
        {
            this.k = x;
        }
        
        @Override
        public String toString()
        {
            return ("Testando Class: k = (" + this.k + ")\n");
        }
    }
    
    public static void main(String[] args)
    {
        try {
            
            PrintStream fw = new PrintStream(new File("teste.txt"));
            fw.println(new Testando(50));
            fw.println(new Testando(10));
            fw.println(new Testando(20));

            Scanner sc = new Scanner(new FileReader("teste.txt"));
            while (sc.hasNextLine())
                System.out.println(sc.nextLine());

        } catch (Exception ex) {
            Logger.getLogger(JavaFXApplication2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
