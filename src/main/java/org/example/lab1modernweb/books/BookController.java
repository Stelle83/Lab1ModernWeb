package org.example.lab1modernweb.books;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

@Controller
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
        return "books/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new CreateBookDTO());
        return "books/create";
    }

    @PostMapping("/create")
    public String createBook(@Valid @ModelAttribute("book") CreateBookDTO dto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/create";
        }

        bookService.create(dto);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findUpdateDTOById(id));
        return "books/update";
    }

    @PostMapping("/{id}/edit")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute("book") UpdateBookDTO dto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/update";
        }

        bookService.update(id, dto);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
