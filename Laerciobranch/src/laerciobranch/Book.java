
package Laerciobranch;

import java.util.Scanner;

public class Book {
    
    private int pages;
    private int issue;
    private int code;
    private String title;
    private String autor;
    private String type;
    private int quantity;
    private int available;
        
    public Book(int pages, int issue, int code, String title, String autor, String type, 
                int quantity, int available){
        
        this.pages = pages;
        this.issue = issue;
        this.code = code;
        this.title = title;
        this.autor = autor;
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
    
        public String getAutor() {
        return autor;
    }
    
}
