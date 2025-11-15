package booktracker.service;

import booktracker.classes.Book;
import booktracker.classes.BookCategory;
import booktracker.classes.Status;
import booktracker.repository.BookRepository;
import booktracker.util.Response;
import booktracker.exception.InvalidBookTitleException;
import booktracker.exception.InvalidValueOfPageException;
import booktracker.exception.InvalidBookStatusException;
import booktracker.exception.BookNotFoundException;
import booktracker.exception.BookOperationException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    private final BookRepository repo = new BookRepository();

    public Response<Void> create(String title, BookCategory category, LocalDate startDate, int currentPage, int totalPages) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidBookTitleException("Title cannot be null or empty.");
        }
        if (category == null) {
            throw new BookOperationException("Category cannot be null.");
        }
        if (currentPage < 0 || totalPages <= 0) {
            throw new InvalidValueOfPageException("Current page must be non-negative and total pages must be positive.");
        }
        if (currentPage > totalPages) {
            throw new InvalidValueOfPageException("Current page cannot exceed total pages.");
        }
        LocalDate start = startDate != null ? startDate : LocalDate.now();
        Status status;
        if (currentPage == totalPages) {
            status = Status.COMPLETED;
        } else if (currentPage > 0) {
            status = Status.IN_PROGRESS;
        } else {
            status = Status.NOT_STARTED;
        }
        Book book = new Book(title, category, start, null, currentPage, totalPages, status);
        boolean result = repo.create(book);
        if (!result) {
            throw new BookOperationException("Failed to create book.");
        }
        return new Response<>(true, "Book created successfully.");
    }

    public Response<Void> update(Book book) {
        Book existing = repo.getById(book.getId());
        if (existing == null) {
            throw new BookNotFoundException("Book not found.");
        }
        if (book.getCurrentPage() < 0 || book.getTotalPages() <= 0 || book.getCurrentPage() > book.getTotalPages()) {
            throw new InvalidValueOfPageException("Invalid page values.");
        }
        if (book.getStatus() == Status.NOT_STARTED && book.getCurrentPage() > 0) {
            throw new InvalidBookStatusException("Cannot set status to NOT_STARTED if current page > 0.");
        }
        if (book.getStatus() == Status.COMPLETED && book.getCurrentPage() != book.getTotalPages()) {
            throw new InvalidBookStatusException("Cannot set status to COMPLETED unless current page equals total pages.");
        }
        // Adjust status based on currentPage
        if (book.getCurrentPage() == book.getTotalPages()) {
            book.setStatus(Status.COMPLETED);
        } else if (book.getCurrentPage() > 0) {
            book.setStatus(Status.IN_PROGRESS);
        } else {
            book.setStatus(Status.NOT_STARTED);
        }
        boolean result = repo.update(book);
        if (!result) {
            throw new BookOperationException("Failed to update book.");
        }
        return new Response<>(true, "Book updated successfully.");
    }

    public Response<Void> delete(int id) {
        Book existing = repo.getById(id);
        if (existing == null) {
            throw new BookNotFoundException("Book not found.");
        }
        boolean result = repo.delete(id);
        if (!result) {
            throw new BookOperationException("Failed to delete book.");
        }
        return new Response<>(true, "Book deleted successfully.");
    }

    public Response<List<Book>> searchByTitle(String title) {
        List<Book> allBooks = repo.getAll();
        List<Book> filtered = allBooks.stream()
            .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
            .collect(Collectors.toList());
        return new Response<>(true, "Books found.", filtered);
    }

    public Response<List<Book>> getAll() {
        List<Book> books = repo.getAll();
        return new Response<>(true, "Books retrieved successfully.", books);
    }

    public Response<List<Book>> getByCategory(BookCategory category) {
        List<Book> allBooks = repo.getAll();
        List<Book> filtered = allBooks.stream()
            .filter(b -> b.getCategory().getId() == category.getId())
            .collect(Collectors.toList());
        return new Response<>(true, "Books retrieved successfully.", filtered);
    }

    public Response<Book> getById(int id) {
        Book book = repo.getById(id);
        if (book == null) {
            throw new BookNotFoundException("Book not found.");
        }
        return new Response<>(true, "Book retrieved successfully.", book);
    }
}
