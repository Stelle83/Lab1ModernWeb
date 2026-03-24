package org.example.lab1modernweb.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = Mockito.mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    void search_shouldReturnPagedBookDTOs() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setGenre("Genre");
        book.setPublishedDate(LocalDate.of(2020, 1, 1));

        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> bookPage = new PageImpl<>(List.of(book));

        when(bookRepository
                .findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(
                        anyString(), anyString(), anyString(), any(Pageable.class)))
                .thenReturn(bookPage);

        Page<BookDTO> result = bookService.search("Test", "Author", "Genre", pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Test Book", result.getContent().get(0).getTitle());
        assertEquals("Author", result.getContent().get(0).getAuthor());
        assertEquals("Genre", result.getContent().get(0).getGenre());
    }

    @Test
    void create_shouldSaveBook() {
        CreateBookDTO dto = new CreateBookDTO();
        dto.setTitle("New Book");
        dto.setAuthor("Some Author");
        dto.setGenre("Drama");
        dto.setPublishedDate(LocalDate.of(2022, 1, 1));

        bookService.create(dto);

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void findUpdateDTOById_shouldReturnUpdateBookDTO() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Existing Book");
        book.setAuthor("Existing Author");
        book.setGenre("Fantasy");
        book.setPublishedDate(LocalDate.of(2019, 6, 15));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        UpdateBookDTO dto = bookService.findUpdateDTOById(1L);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Existing Book", dto.getTitle());
        assertEquals("Existing Author", dto.getAuthor());
        assertEquals("Fantasy", dto.getGenre());
        assertEquals(LocalDate.of(2019, 6, 15), dto.getPublishedDate());
    }

    @Test
    void findUpdateDTOById_shouldThrowExceptionWhenBookNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.findUpdateDTOById(99L));
    }

    @Test
    void update_shouldModifyAndSaveBook() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Old Title");
        existingBook.setAuthor("Old Author");
        existingBook.setGenre("Old Genre");
        existingBook.setPublishedDate(LocalDate.of(2000, 1, 1));

        UpdateBookDTO dto = new UpdateBookDTO();
        dto.setId(1L);
        dto.setTitle("New Title");
        dto.setAuthor("New Author");
        dto.setGenre("New Genre");
        dto.setPublishedDate(LocalDate.of(2023, 3, 10));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

        bookService.update(1L, dto);

        assertEquals("New Title", existingBook.getTitle());
        assertEquals("New Author", existingBook.getAuthor());
        assertEquals("New Genre", existingBook.getGenre());
        assertEquals(LocalDate.of(2023, 3, 10), existingBook.getPublishedDate());

        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void update_shouldThrowExceptionWhenBookNotFound() {
        UpdateBookDTO dto = new UpdateBookDTO();
        dto.setTitle("New Title");
        dto.setAuthor("New Author");
        dto.setGenre("New Genre");
        dto.setPublishedDate(LocalDate.of(2023, 3, 10));

        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.update(99L, dto));
    }

    @Test
    void delete_shouldDeleteBookWhenFound() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book to delete");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.delete(1L);

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void delete_shouldThrowExceptionWhenBookNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.delete(99L));
    }
}