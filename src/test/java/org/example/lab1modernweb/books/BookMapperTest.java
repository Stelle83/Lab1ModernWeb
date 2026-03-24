package org.example.lab1modernweb.books;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    private final BookMapper bookMapper = new BookMapper();

    @Test
    void toEntity_shouldMapCreateBookDTOToBook() {
        CreateBookDTO dto = new CreateBookDTO();
        dto.setTitle("Clean Code");
        dto.setAuthor("Jack Norris");
        dto.setGenre("Fiction");
        dto.setPublishedDate(LocalDate.of(2008, 8, 1));

        Book book = bookMapper.toEntity(dto);

        assertNotNull(book);
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Jack Norris", book.getAuthor());
        assertEquals("Fiction", book.getGenre());
        assertEquals(LocalDate.of(2008, 8, 1), book.getPublishedDate());
    }


    @Test
    void updateEntity_shouldUpdateExistingBookFromUpdateBookDTO() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Old Title");
        book.setAuthor("Old Author");
        book.setGenre("Old Genre");
        book.setPublishedDate(LocalDate.of(2000, 1, 1));

        UpdateBookDTO dto = new UpdateBookDTO();
        dto.setId(1L);
        dto.setTitle("New Title");
        dto.setAuthor("New Author");
        dto.setGenre("New Genre");
        dto.setPublishedDate(LocalDate.of(2020, 5, 10));

        bookMapper.updateEntity(dto, book);

        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals("New Genre", book.getGenre());
        assertEquals(LocalDate.of(2020, 5, 10), book.getPublishedDate());
    }

    @Test
    void toDTO_shouldMapBookToBookDTO() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Spring in Action");
        book.setAuthor("Super Man");
        book.setGenre("Fairytale");
        book.setPublishedDate(LocalDate.of(2018, 10, 5));

        BookDTO dto = bookMapper.toDTO(book);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Spring in Action", dto.getTitle());
        assertEquals("Super Man", dto.getAuthor());
        assertEquals("Fairytale", dto.getGenre());
        assertEquals(LocalDate.of(2018, 10, 5), dto.getPublishedDate());
    }
}