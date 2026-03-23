package org.example.lab1modernweb.books;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends ListCrudRepository<Book, Long> {


    @Query("SELECT b from Book b where b.title like %:title%")
    List<Book> books(@Param("title") String title);

    @Modifying
    @Query("UPDATE Book b SET b.title = :newTitle WHERE b.id IN :ids")
    int updateTitles(@Param("ids") List<Long> ids,
                     @Param("newTitle") String newTitle);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Book b SET b.title = :newTitle WHERE b.id = :id")
    int updateTitle(@Param("id") Long id,
                    @Param("newTitle") String newTitle);

    Page<Book> findAllBy(Pageable pageable);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorContainingIgnoreCase(String author);


}
