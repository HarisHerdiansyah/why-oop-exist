package booktracker.service;

import booktracker.classes.BookCategory;
import booktracker.exception.BookCategoryNotFoundException;
import booktracker.exception.BookCategoryOperationException;
import booktracker.exception.InvalidBookCategoryNameException;
import booktracker.repository.BookCategoryRepository;
import booktracker.util.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookCategoryService Unit Tests")
class BookCategoryServiceTest {

    @Mock
    private BookCategoryRepository repo;

    @InjectMocks
    private BookCategoryService service;

    @Test
    @DisplayName("Create category with valid name should return success response")
    void testCreate_ValidName_ReturnsSuccess() {
        // Given
        String validName = "Fiction";
        when(repo.create(validName)).thenReturn(true);

        // When
        Response<Void> response = service.create(validName);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Category created successfully.", response.getMessage());
        verify(repo, times(1)).create(validName);
    }

    @Test
    @DisplayName("Create category with name too short should throw InvalidBookCategoryNameException")
    void testCreate_NameTooShort_ThrowsException() {
        // Given
        String invalidName = "Fi";

        // When & Then
        InvalidBookCategoryNameException exception = assertThrows(InvalidBookCategoryNameException.class, () -> service.create(invalidName));
        assertEquals("Name must be 4-20 characters long and contain only letters.", exception.getMessage());
        verify(repo, never()).create(anyString());
    }

    @Test
    @DisplayName("Create category with name too long should throw InvalidBookCategoryNameException")
    void testCreate_NameTooLong_ThrowsException() {
        // Given
        String invalidName = "ThisIsAVeryLongCategoryName";

        // When & Then
        InvalidBookCategoryNameException exception = assertThrows(InvalidBookCategoryNameException.class, () -> service.create(invalidName));
        assertEquals("Name must be 4-20 characters long and contain only letters.", exception.getMessage());
        verify(repo, never()).create(anyString());
    }

    @Test
    @DisplayName("Create category with name containing numbers should throw InvalidBookCategoryNameException")
    void testCreate_NameWithNumbers_ThrowsException() {
        // Given
        String invalidName = "Fiction123";

        // When & Then
        InvalidBookCategoryNameException exception = assertThrows(InvalidBookCategoryNameException.class, () -> service.create(invalidName));
        assertEquals("Name must be 4-20 characters long and contain only letters.", exception.getMessage());
        verify(repo, never()).create(anyString());
    }

    @Test
    @DisplayName("Create category with name containing special characters should throw InvalidBookCategoryNameException")
    void testCreate_NameWithSpecialChars_ThrowsException() {
        // Given
        String invalidName = "Fiction!";

        // When & Then
        InvalidBookCategoryNameException exception = assertThrows(InvalidBookCategoryNameException.class, () -> service.create(invalidName));
        assertEquals("Name must be 4-20 characters long and contain only letters.", exception.getMessage());
        verify(repo, never()).create(anyString());
    }

    @Test
    @DisplayName("Create category with null name should throw InvalidBookCategoryNameException")
    void testCreate_NullName_ThrowsException() {
        // Given
        String invalidName = null;

        // When & Then
        InvalidBookCategoryNameException exception = assertThrows(InvalidBookCategoryNameException.class, () -> service.create(invalidName));
        assertEquals("Name must be 4-20 characters long and contain only letters.", exception.getMessage());
        verify(repo, never()).create(anyString());
    }

    @Test
    @DisplayName("Create category when repository fails should throw BookCategoryOperationException")
    void testCreate_RepoFails_ThrowsException() {
        // Given
        String validName = "Fiction";
        when(repo.create(validName)).thenReturn(false);

        // When & Then
        BookCategoryOperationException exception = assertThrows(BookCategoryOperationException.class, () -> service.create(validName));
        assertEquals("Failed to create category.", exception.getMessage());
        verify(repo, times(1)).create(validName);
    }

    @Test
    @DisplayName("Get all categories should return success response with list")
    void testGetAll_ReturnsSuccess() {
        // Given
        List<BookCategory> categories = Arrays.asList(new BookCategory(1, "Fiction"), new BookCategory(2, "Non-Fiction"));
        when(repo.getAll()).thenReturn(categories);

        // When
        Response<List<BookCategory>> response = service.getAll();

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Categories retrieved successfully.", response.getMessage());
        assertEquals(categories, response.getData());
        verify(repo, times(1)).getAll();
    }

    @Test
    @DisplayName("Get category by valid ID should return success response")
    void testGetById_ValidId_ReturnsSuccess() {
        // Given
        int id = 1;
        BookCategory category = new BookCategory(id, "Fiction");
        when(repo.getById(id)).thenReturn(category);

        // When
        Response<BookCategory> response = service.getById(id);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Category retrieved successfully.", response.getMessage());
        assertEquals(category, response.getData());
        verify(repo, times(1)).getById(id);
    }

    @Test
    @DisplayName("Get category by invalid ID should throw BookCategoryNotFoundException")
    void testGetById_InvalidId_ThrowsException() {
        // Given
        int id = 999;
        when(repo.getById(id)).thenReturn(null);

        // When & Then
        BookCategoryNotFoundException exception = assertThrows(BookCategoryNotFoundException.class, () -> service.getById(id));
        assertEquals("Category not found.", exception.getMessage());
        verify(repo, times(1)).getById(id);
    }

    @Test
    @DisplayName("Update category with valid data should return success response")
    void testUpdate_ValidData_ReturnsSuccess() {
        // Given
        BookCategory category = new BookCategory(1, "Fiction");
        when(repo.getById(1)).thenReturn(category);
        when(repo.update(category)).thenReturn(true);

        // When
        Response<Void> response = service.update(category);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Category updated successfully.", response.getMessage());
        verify(repo, times(1)).getById(1);
        verify(repo, times(1)).update(category);
    }

    @Test
    @DisplayName("Update category with invalid name should throw InvalidBookCategoryNameException")
    void testUpdate_InvalidName_ThrowsException() {
        // Given
        BookCategory category = new BookCategory(1, "Fi");

        // When & Then
        InvalidBookCategoryNameException exception = assertThrows(InvalidBookCategoryNameException.class, () -> service.update(category));
        assertEquals("Name must be 4-20 characters long and contain only letters.", exception.getMessage());
        verify(repo, never()).getById(anyInt());
        verify(repo, never()).update(any(BookCategory.class));
    }

    @Test
    @DisplayName("Update non-existing category should throw BookCategoryNotFoundException")
    void testUpdate_NonExisting_ThrowsException() {
        // Given
        BookCategory category = new BookCategory(999, "Fiction");
        when(repo.getById(999)).thenReturn(null);

        // When & Then
        BookCategoryNotFoundException exception = assertThrows(BookCategoryNotFoundException.class, () -> service.update(category));
        assertEquals("Category not found.", exception.getMessage());
        verify(repo, times(1)).getById(999);
        verify(repo, never()).update(any(BookCategory.class));
    }

    @Test
    @DisplayName("Update category when repository fails should throw BookCategoryOperationException")
    void testUpdate_RepoFails_ThrowsException() {
        // Given
        BookCategory category = new BookCategory(1, "Fiction");
        when(repo.getById(1)).thenReturn(category);
        when(repo.update(category)).thenReturn(false);

        // When & Then
        BookCategoryOperationException exception = assertThrows(BookCategoryOperationException.class, () -> service.update(category));
        assertEquals("Failed to update category.", exception.getMessage());
        verify(repo, times(1)).getById(1);
        verify(repo, times(1)).update(category);
    }

    @Test
    @DisplayName("Delete existing category should return success response")
    void testDelete_Existing_ReturnsSuccess() {
        // Given
        int id = 1;
        BookCategory category = new BookCategory(id, "Fiction");
        when(repo.getById(id)).thenReturn(category);
        when(repo.delete(id)).thenReturn(true);

        // When
        Response<Void> response = service.delete(id);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Category deleted successfully.", response.getMessage());
        verify(repo, times(1)).getById(id);
        verify(repo, times(1)).delete(id);
    }

    @Test
    @DisplayName("Delete non-existing category should throw BookCategoryNotFoundException")
    void testDelete_NonExisting_ThrowsException() {
        // Given
        int id = 999;
        when(repo.getById(id)).thenReturn(null);

        // When & Then
        BookCategoryNotFoundException exception = assertThrows(BookCategoryNotFoundException.class, () -> service.delete(id));
        assertEquals("Category not found.", exception.getMessage());
        verify(repo, times(1)).getById(id);
        verify(repo, never()).delete(anyInt());
    }

    @Test
    @DisplayName("Delete category when repository fails should throw BookCategoryOperationException")
    void testDelete_RepoFails_ThrowsException() {
        // Given
        int id = 1;
        BookCategory category = new BookCategory(id, "Fiction");
        when(repo.getById(id)).thenReturn(category);
        when(repo.delete(id)).thenReturn(false);

        // When & Then
        BookCategoryOperationException exception = assertThrows(BookCategoryOperationException.class, () -> service.delete(id));
        assertEquals("Failed to delete category.", exception.getMessage());
        verify(repo, times(1)).getById(id);
        verify(repo, times(1)).delete(id);
    }
}
