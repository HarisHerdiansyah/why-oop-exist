### Class Design
```Java
enum Status {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED
}

class BookCategory {
    String name;
}

class Book {
    String title;
    BookCategory category;
    Date startDate;
    Date endDate;
    int currentPage;
    int totalPages;
    Status status;
}
```

### Flow Design
1. Manage Categories: Basic of CRUD
2. Manage Books:
    a. Create new book
        - Case 1: User can create new book by passing all required fields, by default status is NOT_STARTED and currentPage is 0.
        - Case 2: User can create new book and currentPage is higher than 1 and less than totalPages, the status is automatically set to IN_PROGRESS.
        - Case 3: User can create new book and currentPage is equal to totalPages, the status is automatically set to COMPLETED.
        - Case 4: User create new book with currentPage higher than totalPages will throw an exception.
        - Case 5: Any negative input of integer will throw an exception.
        - Case 6: User can input startDate or not and can choose by current date (when user input) or manually input.
    b. Update an existing book
        - Case 1: When currentPage is updated and user forgot to change status, the status will be automatically set to IN_PROGRESS.
        - Case 2: When currentPage is updated and it is equal to totalPages, the status will be automatically set to COMPLETED.
        - Case 3: When currentPage is higher than totalPages, it will throw an exception.
        - Case 4: User can't set status back to NOT_STARTED.
        - Caes 5: User can't set status to COMPLETED as long as the currentPage is not equal to totalPages.
    c. Delete a book
    d. Search for books by title or category
    e. View all books
    f. View book details

### SQL Schema
```MySQL
create table book_categories (
    id int auto_increment primary key,
    name varchar(255) not null default ''
);

create table books (
    id int auto_increment primary key,
    title varchar(255) not null default '',
    category_id int,
    start_date date,
    end_date date,
    current_page int default 0,
    total_pages int default 0,
    status enum('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED') not null default 'NOT_STARTED',
    foreign key (category_id) references book_categories(id)
);
```
