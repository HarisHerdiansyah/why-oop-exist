package booktracker.repository;

import booktracker.classes.Book;
import booktracker.classes.BookCategory;
import booktracker.classes.Status;
import module.database.Conn;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class BookRepository {
    public boolean create(Book book) {
        try {
            Conn instance = Conn.getInstance();
            Connection conn = instance.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO books (title, category_id, start_date, end_date, current_page, total_pages, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getCategory().getId());
            stmt.setObject(3, book.getStartDate());
            stmt.setObject(4, book.getEndDate());
            stmt.setInt(5, book.getCurrentPage());
            stmt.setInt(6, book.getTotalPages());
            stmt.setString(7, book.getStatus().name());
            stmt.executeUpdate();
            instance.closeConnection(conn);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try {
            Conn instance = Conn.getInstance();
            Connection conn = instance.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT b.*, bc.name FROM books b JOIN book_category bc ON b.category_id = bc.id");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BookCategory category = new BookCategory(
                    rs.getInt("category_id"),
                    rs.getString("name")
                );
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    category,
                    rs.getObject("start_date", LocalDate.class),
                    rs.getObject("end_date", LocalDate.class),
                    rs.getInt("current_page"),
                    rs.getInt("total_pages"),
                    Status.valueOf(rs.getString("status"))
                );
                books.add(book);
            }
            instance.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public Book getById(int id) {
        try {
            Conn instance = Conn.getInstance();
            Connection conn = instance.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT b.*, bc.name FROM books b JOIN book_category bc ON b.category_id = bc.id WHERE b.id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BookCategory category = new BookCategory(
                    rs.getInt("category_id"),
                    rs.getString("name")
                );
                return new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    category,
                    rs.getObject("start_date", LocalDate.class),
                    rs.getObject("end_date", LocalDate.class),
                    rs.getInt("current_page"),
                    rs.getInt("total_pages"),
                    Status.valueOf(rs.getString("status"))
                );
            }
            instance.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Book book) {
        try {
            Conn instance = Conn.getInstance();
            Connection conn = instance.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE books SET title = ?, category_id = ?, start_date = ?, end_date = ?, current_page = ?, total_pages = ?, status = ? WHERE id = ?"
            );
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getCategory().getId());
            stmt.setObject(3, book.getStartDate());
            stmt.setObject(4, book.getEndDate());
            stmt.setInt(5, book.getCurrentPage());
            stmt.setInt(6, book.getTotalPages());
            stmt.setString(7, book.getStatus().name());
            stmt.setInt(8, book.getId());
            stmt.executeUpdate();
            instance.closeConnection(conn);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            Conn instance = Conn.getInstance();
            Connection conn = instance.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            instance.closeConnection(conn);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
