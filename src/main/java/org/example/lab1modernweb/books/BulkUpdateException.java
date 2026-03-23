package org.example.lab1modernweb.books;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BulkUpdateException extends RuntimeException {
    public BulkUpdateException(String message) {
        super(message);
    }
}
