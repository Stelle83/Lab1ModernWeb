package org.example.lab1modernweb.books;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper = new BookMapper();

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<BookDTO> search(String title, String author, String genre, Pageable pageable) {
        String safeTitle = title == null ? "" : title;
        String safeAuthor = author == null ? "" : author;
        String safeGenre = genre == null ? "" : genre;

        return bookRepository
                .findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(
                        safeTitle, safeAuthor, safeGenre, pageable
                )
                .map(bookMapper::toDTO);
    }

    public void create(CreateBookDTO dto) {
        Book book = bookMapper.toEntity(dto);
        bookRepository.save(book);
    }

    public UpdateBookDTO findUpdateDTOById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));

        UpdateBookDTO dto = new UpdateBookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        dto.setPublishedDate(book.getPublishedDate());

        return dto;
    }

    public void update(Long id, UpdateBookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));

        bookMapper.updateEntity(dto, book);
        bookRepository.save(book);
    }

    public void delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
        bookRepository.delete(book);
    }
}