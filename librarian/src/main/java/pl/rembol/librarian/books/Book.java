package pl.rembol.librarian.books;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String author;

    private int available;

    private Book() {}

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
        this.available = 1;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getAvailable() {
        return available;
    }

    public void borrowed() {
        available--;
    }

    public void returned() {
        available++;
    }


}
