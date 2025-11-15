package booktracker.service;

import booktracker.classes.Book;
import booktracker.classes.BookCategory;
import booktracker.classes.Status;
import booktracker.exception.*;
import booktracker.repository.BookRepository;
import booktracker.util.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService Unit Tests")
class BookServiceTest {

    @Mock
    private BookRepository repo;

    @InjectMocks
    private BookService service;

    private BookCategory category;

    @BeforeEach
    void setUp() {
        category = new BookCategory(1, "Fiction");
    }

    @Test
    @DisplayName("Create book with all valid fields and currentPage 0 should set status to NOT_STARTED and return success")
    void testCreate_ValidFields_CurrentPageZero_StatusNotStarted() {
        // Given
        String title = "Test Book";
        LocalDate startDate = LocalDate.now();
        int currentPage = 0;
        int totalPages = 100;
        when(repo.create(any(Book.class))).thenReturn(true);

        // When
        Response<Void> response = service.create(title, category, startDate, currentPage, totalPages);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Book created successfully.", response.getMessage());
        verify(repo, times(1)).create(argThat(book ->
            book.getTitle().equals(title) &&
            book.getCategory().equals(category) &&
            book.getStartDate().equals(startDate) &&
            book.getEndDate() == null &&
            book.getCurrentPage() == currentPage &&
            book.getTotalPages() == totalPages &&
            book.getStatus() == Status.NOT_STARTED
        ));
    }

    @Test
    @DisplayName("Create book with currentPage between 1 and totalPages should set status to IN_PROGRESS")
    void testCreate_CurrentPageInProgress_StatusInProgress() {
        // Given
        String title = "Test Book";
        LocalDate startDate = LocalDate.now();
        int currentPage = 50;
        int totalPages = 100;
        when(repo.create(any(Book.class))).thenReturn(true);

        // When
        Response<Void> response = service.create(title, category, startDate, currentPage, totalPages);

        // Then
        assertTrue(response.isSuccess());
        verify(repo, times(1)).create(argThat(book -> book.getStatus() == Status.IN_PROGRESS));
    }

    @Test
    @DisplayName("Create book with currentPage equal to totalPages should set status to COMPLETED")
    void testCreate_CurrentPageEqualsTotal_StatusCompleted() {
        // Given
        String title = "Test Book";
        LocalDate startDate = LocalDate.now();
        int currentPage = 100;
        int totalPages = 100;
        when(repo.create(any(Book.class))).thenReturn(true);

        // When
        Response<Void> response = service.create(title, category, startDate, currentPage, totalPages);

        // Then
        assertTrue(response.isSuccess());
        verify(repo, times(1)).create(argThat(book -> book.getStatus() == Status.COMPLETED));
    }

    @Test
    @DisplayName("Create book with null startDate should set startDate to current date")
    void testCreate_NullStartDate_SetsCurrentDate() {
        // Given
        String title = "Test Book";
        LocalDate startDate = null;
        int currentPage = 0;
        int totalPages = 100;
        when(repo.create(any(Book.class))).thenReturn(true);

        // When
        Response<Void> response = service.create(title, category, startDate, currentPage, totalPages);

        // Then
        assertTrue(response.isSuccess());
        verify(repo, times(1)).create(argThat(book -> book.getStartDate().equals(LocalDate.now())));
    }

    @Test
    @DisplayName("Create book with null title should throw InvalidBookTitleException")
    void testCreate_NullTitle_ThrowsException() {
        // Given
        String title = null;
        LocalDate startDate = LocalDate.now();
        int currentPage = 0;
        int totalPages = 100;

        // When & Then
        InvalidBookTitleException exception = assertThrows(InvalidBookTitleException.class,
            () -> service.create(title, category, startDate, currentPage, totalPages));
        assertEquals("Title cannot be null or empty.", exception.getMessage());
        verify(repo, never()).create(any(Book.class));
    }

    @Test
    @DisplayName("Create book with empty title should throw InvalidBookTitleException")
    void testCreate_EmptyTitle_ThrowsException() {
        // Given
        String title = "";
        LocalDate startDate = LocalDate.now();
        int currentPage = 0;
        int totalPages = 100;

        // When & Then
        InvalidBookTitleException exception = assertThrows(InvalidBookTitleException.class,
            () -> service.create(title, category, startDate, currentPage, totalPages));
        assertEquals("Title cannot be null or empty.", exception.getMessage());
        verify(repo, never()).create(any(Book.class));
    }

    @Test
    @DisplayName("Create book with null category should throw BookOperationException")
    void testCreate_NullCategory_ThrowsException() {
        // Given
        String title = "Test Book";
        BookCategory nullCategory = null;
        LocalDate startDate = LocalDate.now();
        int currentPage = 0;
        int totalPages = 100;

        // When & Then
        BookOperationException exception = assertThrows(BookOperationException.class,
            () -> service.create(title, nullCategory, startDate, currentPage, totalPages));
        assertEquals("Category cannot be null.", exception.getMessage());
        verify(repo, never()).create(any(Book.class));
    }

    @Test
    @DisplayName("Create book with negative currentPage should throw InvalidValueOfPageException")
    void testCreate_NegativeCurrentPage_ThrowsException() {
        // Given
        String title = "Test Book";
        LocalDate startDate = LocalDate.now();
        int currentPage = -1;
        int totalPages = 100;

        // When & Then
        InvalidValueOfPageException exception = assertThrows(InvalidValueOfPageException.class,
            () -> service.create(title, category, startDate, currentPage, totalPages));
        assertEquals("Current page must be non-negative and total pages must be positive.", exception.getMessage());
        verify(repo, never()).create(any(Book.class));
    }

    @Test
    @DisplayName("Create book with zero totalPages should throw InvalidValueOfPageException")
    void testCreate_ZeroTotalPages_ThrowsException() {
        // Given
        String title = "Test Book";
        LocalDate startDate = LocalDate.now();
        int currentPage = 0;
        int totalPages = 0;

        // When & Then
        InvalidValueOfPageException exception = assertThrows(InvalidValueOfPageException.class,
            () -> service.create(title, category, startDate, currentPage, totalPages));
        assertEquals("Current page must be non-negative and total pages must be positive.", exception.getMessage());
        verify(repo, never()).create(any(Book.class));
    }

    @Test
    @DisplayName("Create book with currentPage exceeding totalPages should throw InvalidValueOfPageException")
    void testCreate_CurrentPageExceedsTotal_ThrowsException() {
        // Given
        String title = "Test Book";
        LocalDate startDate = LocalDate.now();
        int currentPage = 150;
        int totalPages = 100;

        // When & Then
        InvalidValueOfPageException exception = assertThrows(InvalidValueOfPageException.class,
            () -> service.create(title, category, startDate, currentPage, totalPages));
        assertEquals("Current page cannot exceed total pages.", exception.getMessage());
        verify(repo, never()).create(any(Book.class));
    }

    @Test
    @DisplayName("Create book when repository fails should throw BookOperationException")
    void testCreate_RepoFails_ThrowsException() {
        // Given
        String title = "Test Book";
        LocalDate startDate = LocalDate.now();
        int currentPage = 0;
        int totalPages = 100;
        when(repo.create(any(Book.class))).thenReturn(false);

        // When & Then
        BookOperationException exception = assertThrows(BookOperationException.class,
            () -> service.create(title, category, startDate, currentPage, totalPages));
        assertEquals("Failed to create book.", exception.getMessage());
        verify(repo, times(1)).create(any(Book.class));
    }

    @Test
    @DisplayName("Update book with valid data and currentPage less than total should set status to IN_PROGRESS")
    void testUpdate_ValidData_SetsStatusInProgress() {
        // Given
        Book book = new Book(1, "Test Book", category, LocalDate.now(), null, 50, 100, Status.NOT_STARTED);
        when(repo.getById(1)).thenReturn(book);
        when(repo.update(any(Book.class))).thenReturn(true);

        // When
        Response<Void> response = service.update(book);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Book updated successfully.", response.getMessage());
        assertEquals(Status.IN_PROGRESS, book.getStatus());
        verify(repo, times(1)).update(book);
    }

    @Test
    @DisplayName("Update book with currentPage equal to totalPages should set status to COMPLETED")
    void testUpdate_CurrentPageEqualsTotal_SetsStatusCompleted() {
        // Given
        Book book = new Book(1, "Test Book", category, LocalDate.now(), null, 100, 100, Status.IN_PROGRESS);
        when(repo.getById(1)).thenReturn(book);
        when(repo.update(any(Book.class))).thenReturn(true);

        // When
        Response<Void> response = service.update(book);

        // Then
        assertTrue(response.isSuccess());
        assertEquals(Status.COMPLETED, book.getStatus());
        verify(repo, times(1)).update(book);
    }

    @Test
    @DisplayName("Update book with status NOT_STARTED but currentPage > 0 should throw InvalidBookStatusException")
    void testUpdate_StatusNotStartedButPagesRead_ThrowsException() {
        // Given
        Book book = new Book(1, "Test Book", category, LocalDate.now(), null, 50, 100, Status.NOT_STARTED);

        // When & Then
        InvalidBookStatusException exception = assertThrows(InvalidBookStatusException.class, () -> service.update(book));
        assertEquals("Cannot set status to NOT_STARTED if current page > 0.", exception.getMessage());
        verify(repo, never()).getById(anyInt());
        verify(repo, never()).update(any(Book.class));
    }

    @Test
    @DisplayName("Update book with status COMPLETED but currentPage not equal to totalPages should throw InvalidBookStatusException")
    void testUpdate_StatusCompletedButNotFinished_ThrowsException() {
        // Given
        Book book = new Book(1, "Test Book", category, LocalDate.now(), null, 50, 100, Status.COMPLETED);

        // When & Then
        InvalidBookStatusException exception = assertThrows(InvalidBookStatusException.class, () -> service.update(book));
        assertEquals("Cannot set status to COMPLETED unless current page equals total pages.", exception.getMessage());
        verify(repo, never()).getById(anyInt());
        verify(repo, never()).update(any(Book.class));
    }

    @Test
    @DisplayName("Update non-existing book should throw BookNotFoundException")
    void testUpdate_NonExistingBook_ThrowsException() {
        // Given
        Book book = new Book(999, "Test Book", category, LocalDate.now(), null, 0, 100, Status.NOT_STARTED);
        when(repo.getById(999)).thenReturn(null);

        // When & Then
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> service.update(book));
        assertEquals("Book not found.", exception.getMessage());
        verify(repo, times(1)).getById(999);
        verify(repo, never()).update(any(Book.class));
    }

    @Test
    @DisplayName("Update book with invalid page values should throw InvalidValueOfPageException")
    void testUpdate_InvalidPages_ThrowsException() {
        // Given
        Book book = new Book(1, "Test Book", category, LocalDate.now(), null, 150, 100, Status.IN_PROGRESS);

        // When & Then
        InvalidValueOfPageException exception = assertThrows(InvalidValueOfPageException.class, () -> service.update(book));
        assertEquals("Invalid page values.", exception.getMessage());
        verify(repo, never()).getById(anyInt());
        verify(repo, never()).update(any(Book.class));
    }

    @Test
    @DisplayName("Delete existing book should return success response")
    void testDelete_ExistingBook_ReturnsSuccess() {
        // Given
        int id = 1;
        Book book = new Book(id, "Test Book", category, LocalDate.now(), null, 0, 100, Status.NOT_STARTED);
        when(repo.getById(id)).thenReturn(book);
        when(repo.delete(id)).thenReturn(true);

        // When
        Response<Void> response = service.delete(id);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Book deleted successfully.", response.getMessage());
        verify(repo, times(1)).getById(id);
        verify(repo, times(1)).delete(id);
    }

    @Test
    @DisplayName("Delete non-existing book should throw BookNotFoundException")
    void testDelete_NonExistingBook_ThrowsException() {
        // Given
        int id = 999;
        when(repo.getById(id)).thenReturn(null);

        // When & Then
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> service.delete(id));
        assertEquals("Book not found.", exception.getMessage());
        verify(repo, times(1)).getById(id);
        verify(repo, never()).delete(anyInt());
    }

    @Test
    @DisplayName("Search books by title should return filtered list")
    void testSearchByTitle_ReturnsFilteredList() {
        // Given
        List<Book> allBooks = Arrays.asList(
            new Book(1, "Java Programming", category, LocalDate.now(), null, 0, 100, Status.NOT_STARTED),
            new Book(2, "Python Guide", category, LocalDate.now(), null, 0, 100, Status.NOT_STARTED),
            new Book(3, "JavaScript Basics", category, LocalDate.now(), null, 0, 100, Status.NOT_STARTED)
        );
        when(repo.getAll()).thenReturn(allBooks);

        // When
        Response<List<Book>> response = service.searchByTitle("Java");

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Books found.", response.getMessage());
        assertEquals(2, response.getData().size());
        assertTrue(response.getData().stream().allMatch(b -> b.getTitle().contains("Java")));
        verify(repo, times(1)).getAll();
    }

    @Test
    @DisplayName("Get all books should return success response with list")
    void testGetAll_ReturnsSuccess() {
        // Given
        List<Book> books = Arrays.asList(
            new Book(1, "Book1", category, LocalDate.now(), null, 0, 100, Status.NOT_STARTED),
            new Book(2, "Book2", category, LocalDate.now(), null, 0, 100, Status.NOT_STARTED)
        );
        when(repo.getAll()).thenReturn(books);

        // When
        Response<List<Book>> response = service.getAll();

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Books retrieved successfully.", response.getMessage());
        assertEquals(books, response.getData());
        verify(repo, times(1)).getAll();
    }

    @Test
    @DisplayName("Get books by category should return filtered list")
    void testGetByCategory_ReturnsFilteredList() {
        // Given
        BookCategory otherCategory = new BookCategory(2, "Non-Fiction");
        List<Book> allBooks = Arrays.asList(
            new Book(1, "Book1", category, LocalDate.now(), null, 0, 100, Status.NOT_STARTED),
            new Book(2, "Book2", otherCategory, LocalDate.now(), null, 0, 100, Status.NOT_STARTED)
        );
        when(repo.getAll()).thenReturn(allBooks);

        // When
        Response<List<Book>> response = service.getByCategory(category);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Books retrieved successfully.", response.getMessage());
        assertEquals(1, response.getData().size());
        assertEquals("Book1", response.getData().get(0).getTitle());
        verify(repo, times(1)).getAll();
    }

    @Test
    @DisplayName("Get book by valid ID should return success response")
    void testGetById_ValidId_ReturnsSuccess() {
        // Given
        int id = 1;
        Book book = new Book(id, "Test Book", category, LocalDate.now(), null, 0, 100, Status.NOT_STARTED);
        when(repo.getById(id)).thenReturn(book);

        // When
        Response<Book> response = service.getById(id);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Book retrieved successfully.", response.getMessage());
        assertEquals(book, response.getData());
        verify(repo, times(1)).getById(id);
    }

    @Test
    @DisplayName("Get book by invalid ID should throw BookNotFoundException")
    void testGetById_InvalidId_ThrowsException() {
        // Given
        int id = 999;
        when(repo.getById(id)).thenReturn(null);

        // When & Then
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> service.getById(id));
        assertEquals("Book not found.", exception.getMessage());
        verify(repo, times(1)).getById(id);
    }
}
