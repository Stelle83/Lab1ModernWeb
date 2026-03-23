package org.example.lab1modernweb.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    private final BookMapper bookMapper = new BookMapper();

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    public BookDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
        return bookMapper.toDTO(book);
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
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        bookRepository.deleteById(id);
    }

}
