@param java.util.List<org.example.lab1modernweb.books.BookDTO> books
@param org.springframework.data.domain.Page<org.example.lab1modernweb.books.BookDTO> bookPage
@param String title
@param String author
@param String genre

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Books</title>
</head>
<body>
<h1>Book List</h1>

<a href="/books/create">Create New Book</a>

<h2>Search Books</h2>
<form action="/books" method="get">
    <div>
        <label>Title:</label>
        <input type="text" name="title" value="${title != null ? title : ""}">
    </div>

    <div>
        <label>Author:</label>
        <input type="text" name="author" value="${author != null ? author : ""}">
    </div>

    <div>
        <label>Genre:</label>
        <input type="text" name="genre" value="${genre != null ? genre : ""}">
    </div>

    <button type="submit">Search</button>
    <a href="/books">Clear</a>
</form>

<table border="1">
    <thead>
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Genre</th>
        <th>Published Date</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    @for(org.example.lab1modernweb.books.BookDTO book : books)
        <tr>
            <td>${book.getTitle()}</td>
            <td>${book.getAuthor()}</td>
            <td>${book.getGenre()}</td>
            <td>${book.getPublishedDate() != null ? book.getPublishedDate().toString() : ""}</td>
            <td>
                <a href="/books/${book.getId()}/edit">Edit</a>
                <form action="/books/${book.getId()}/delete" method="post" style="display:inline;">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    @endfor
    </tbody>
</table>

@if(bookPage.getTotalPages() > 1)
    <div style="margin-top: 20px;">
        @if(bookPage.hasPrevious())
            <a href="/books?page=${bookPage.getNumber() - 1}&title=${title != null ? title : ""}&author=${author != null ? author : ""}&genre=${genre != null ? genre : ""}">
                Previous
            </a>
        @endif

        <span> Page ${bookPage.getNumber() + 1} of ${bookPage.getTotalPages()} </span>

        @if(bookPage.hasNext())
            <a href="/books?page=${bookPage.getNumber() + 1}&title=${title != null ? title : ""}&author=${author != null ? author : ""}&genre=${genre != null ? genre : ""}">
                Next
            </a>
        @endif
    </div>
@endif

</body>
</html>