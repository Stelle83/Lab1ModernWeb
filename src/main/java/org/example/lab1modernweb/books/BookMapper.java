package org.example.lab1modernweb.books;

public class BookMapper {

    public Book toEntity(CreateBookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setGenre(dto.getGenre());
        book.setPublishedDate(dto.getPublishedDate());
        return book;
    }

    public void updateEntity(UpdateBookDTO dto, Book book) {
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setGenre(dto.getGenre());
        book.setPublishedDate(dto.getPublishedDate());
    }

    public BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublishedDate()
        );
    }

}
