
package Laerciobranch;

import java.util.Scanner;

public class Book {
    
    private int pages;
    private int issue;
    private int code;
    private String title;
    private String autor;
    private String type;
    
        
    public Book(int pages, int issue, int code, String title, String autor, String type){
        
        this.pages = pages;
        this.issue = issue;
        this.code = code;
        this.title = title;
        this.autor = autor;
        this.type = type;
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
