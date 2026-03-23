package org.example.lab1modernweb.books;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;


    public BookController(BookService bookService) {
        log.info("BookController constructor");
        log.info("BookService type {}", bookService.getClass().getName());
        this.bookService = bookService;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "book/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new CreateBookDTO());
        return "book/create";
    }

    @PostMapping("/create")
    public String createBook(@Valid @ModelAttribute("book") CreateBookDTO dto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/create";
        }

        bookService.create(dto);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findUpdateDTOById(id));
        return "book/update";
    }

    @PostMapping("/{id}/edit")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute("book") UpdateBookDTO dto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/update";
        }

        bookService.update(id, dto);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

//    @GetMapping
//    public List<Book> getBooks() {
//        log.info("Real service class is: {} ", this.bookService.getClass().getName());
//        return bookService.getAllBooks();
//    }
//
//    @PostMapping
//    public Book createBook(@RequestParam String title) {
//        return bookService.saveBook(title);
//    }
//
//    @GetMapping("/update/{id}")
//    public void updateBookTitle(@PathVariable long id, @RequestParam String title) {
//        bookService.updateTitle(id, title);
//    }
//
//    @GetMapping("/page")
//    public Page<Book> booksPaged(Pageable pageable) {
//        return bookService.findBooks(pageable);
//    }
//
//    @GetMapping("/books")
//    public Page<Book> booksPagedWithDefaults(
//            @PageableDefault(size = 2, sort = "title") Pageable pageable) {
//        return bookService.findBooks(pageable);
//    }
//
//    /**
//     * Demonstrate transaction rollback.
//     * URL: /books/bulk-update?ids=1,2&newTitle=Updated&fail=true
//     */
//    @PutMapping("/bulk-update")
//    public String bulkUpdate(
//            @RequestParam List<Long> ids,
//            @RequestParam String newTitle,
//            @RequestParam(defaultValue = "false") boolean fail) {
//        bookService.updateTitlesInTransaction(ids, newTitle, fail);
//        return "Bulk update successful!";
//    }

}
