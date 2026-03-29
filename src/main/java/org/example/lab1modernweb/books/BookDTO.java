package org.example.lab1modernweb.books;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class BookDTO {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String genre;
    private LocalDate publishedDate;


    public BookDTO() {
    }

    public BookDTO(Long id, String title, String author, String genre, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publishedDate = publishedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
