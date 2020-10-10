package com.example.interviewapp.repository.book;

import com.example.interviewapp.domain.Book;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(generator = "BOOK_ID_GENERATOR")
    @GenericGenerator(name = "BOOK_ID_GENERATOR", strategy = "enhanced-sequence",
            parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "book_sequence"))
    private Long id;

    @NotBlank(message = "Name of the book can't be blank")
    private String name;

    @NotBlank(message = "Author of the book can't be blank")
    private String author;

    public BookEntity() {}

    public BookEntity(Long id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book toBook() {
        return new Book(this.id, this.name, this.author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
