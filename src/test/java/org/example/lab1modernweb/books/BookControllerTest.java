package org.example.lab1modernweb.books;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Keep this
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    // ---------- LIST ----------
    @Test
    void testListBooks() throws Exception {
        Page<BookDTO> page = new PageImpl<>(
                List.of(new BookDTO(1L, "Test Title", "Author", "Genre", LocalDate.now()))
        );

        Mockito.when(bookService.search(any(), any(), any(), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/list"))
                .andExpect(model().attributeExists("bookPage"))
                .andExpect(model().attributeExists("books"));
    }

    // ---------- CREATE GET ----------
    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/books/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/create"))
                .andExpect(model().attributeExists("book"));
    }

    // ---------- CREATE POST SUCCESS ----------
    @Test
    void testCreateBookSuccess() throws Exception {
        mockMvc.perform(post("/books/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Valid Title")
                        .param("author", "Author")
                        .param("genre", "Genre"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        Mockito.verify(bookService).create(any(CreateBookDTO.class));
    }

    // ---------- CREATE POST VALIDATION ERROR ----------
    @Test
    void testCreateBookValidationError() throws Exception {
        mockMvc.perform(post("/books/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "")) // invalid title
                .andExpect(status().isOk())
                .andExpect(view().name("books/create"));
    }

    // ---------- UPDATE GET ----------
    @Test
    void testShowUpdateForm() throws Exception {
        UpdateBookDTO dto = new UpdateBookDTO();
        dto.setTitle("T1");

        Mockito.when(bookService.findUpdateDTOById(1L)).thenReturn(dto);

        mockMvc.perform(get("/books/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/update"))
                .andExpect(model().attributeExists("book"));
    }

    // ---------- UPDATE POST SUCCESS ----------
    @Test
    void testUpdateBookSuccess() throws Exception {
        mockMvc.perform(post("/books/1/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "New Title")
                        .param("author", "A")
                        .param("genre", "G"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        Mockito.verify(bookService).update(eq(1L), any(UpdateBookDTO.class));
    }

    // ---------- UPDATE POST VALIDATION ERROR ----------
    @Test
    void testUpdateBookValidationError() throws Exception {
        mockMvc.perform(post("/books/1/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "")) // invalid
                .andExpect(status().isOk())
                .andExpect(view().name("books/update"));
    }

    // ---------- DELETE ----------
    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(post("/books/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        Mockito.verify(bookService).delete(1L);
    }
}