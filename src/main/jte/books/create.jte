@param org.example.lab1modernweb.books.CreateBookDTO book

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
    </div>

    <div>
        <label>Author:</label>
        <input type="text" name="author" value="${book.getAuthor() != null ? book.getAuthor() : ""}">
    </div>

    <div>
        <label>Genre:</label>
        <input type="text" name="genre" value="${book.getGenre() != null ? book.getGenre() : ""}">
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