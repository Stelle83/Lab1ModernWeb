@param org.example.lab1modernweb.books.CreateBookDTO book
@param org.springframework.validation.BindingResult bindingResult

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Book</title>
</head>
<body>
<h1>Create Book</h1>

<form action="/books/create" method="post">
    <div>
        <label>Title:</label>
        <input type="text" name="title" value="${book.getTitle() != null ? book.getTitle() : ""}">
        @if(bindingResult != null && bindingResult.hasFieldErrors("title"))
            <div>${bindingResult.getFieldError("title").getDefaultMessage()}</div>
        @endif
    </div>

    <div>
        <label>Author:</label>
        <input type="text" name="author" value="${book.getAuthor() != null ? book.getAuthor() : ""}">
        @if(bindingResult != null && bindingResult.hasFieldErrors("author"))
            <div>${bindingResult.getFieldError("author").getDefaultMessage()}</div>
        @endif
    </div>

    <div>
        <label>Genre:</label>
        <input type="text" name="genre" value="${book.getGenre() != null ? book.getGenre() : ""}">
        @if(bindingResult != null && bindingResult.hasFieldErrors("genre"))
            <div>${bindingResult.getFieldError("genre").getDefaultMessage()}</div>
        @endif
    </div>

    <div>
        <label>Published Date:</label>
        <input type="date" name="publishedDate" value="${book.getPublishedDate() != null ? book.getPublishedDate().toString() : ""}">
    </div>

    <button type="submit">Save</button>
</form>

<a href="/books">Back to list</a>
</body>
</html>