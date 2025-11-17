package booktracker.service;

import booktracker.classes.BookCategory;
import booktracker.repository.BookCategoryRepository;
import booktracker.util.Response;
import booktracker.exception.InvalidBookCategoryNameException;
import booktracker.exception.BookCategoryNotFoundException;
import booktracker.exception.BookCategoryOperationException;

import java.util.List;

public class BookCategoryService {
    private final BookCategoryRepository repo;

    public BookCategoryService() {
        this.repo = new BookCategoryRepository();
    }

    public BookCategoryService(BookCategoryRepository repo) {
        this.repo = repo;
    }

    public boolean isInvalidName(String name) {
        return name == null || !name.matches("^[a-zA-Z ]{4,20}$");
    }

    public Response<Void> create(String name) throws InvalidBookCategoryNameException, BookCategoryOperationException {
        if (isInvalidName(name)) {
            throw new InvalidBookCategoryNameException("Name must be 4-20 characters long and contain only letters.");
        }
        boolean result = repo.create(name);
        if (!result) {
            throw new BookCategoryOperationException("Failed to create category.");
        }
        return new Response<>(true, "Category created successfully.");
    }

    public Response<List<BookCategory>> getAll(String query) {
        List<BookCategory> categories = repo.getAll(query);
        return new Response<>(true, "Categories retrieved successfully.", categories);
    }

    public Response<BookCategory> getById(int id) throws BookCategoryNotFoundException {
        BookCategory category = repo.getById(id);
        if (category == null) {
            throw new BookCategoryNotFoundException("Category not found.");
        }
        return new Response<>(true, "Category retrieved successfully.", category);
    }

    public Response<Void> update(BookCategory category) throws InvalidBookCategoryNameException, BookCategoryOperationException {
        if (isInvalidName(category.getName())) {
            throw new InvalidBookCategoryNameException("Name must be 4-20 characters long and contain only letters.");
        }
        BookCategory existing = repo.getById(category.getId());
        if (existing == null) {
            throw new BookCategoryNotFoundException("Category not found.");
        }
        boolean result = repo.update(category);
        if (!result) {
            throw new BookCategoryOperationException("Failed to update category.");
        }
        return new Response<>(true, "Category updated successfully.");
    }

    public Response<Void> delete(int id) throws BookCategoryNotFoundException {
        BookCategory existing = repo.getById(id);
        if (existing == null) {
            throw new BookCategoryNotFoundException("Category not found.");
        }
        boolean result = repo.delete(id);
        if (!result) {
            throw new BookCategoryOperationException("Failed to delete category.");
        }
        return new Response<>(true, "Category deleted successfully.");
    }
}
