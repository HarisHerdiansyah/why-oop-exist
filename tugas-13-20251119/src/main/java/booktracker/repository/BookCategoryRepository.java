package booktracker.repository;

import booktracker.classes.BookCategory;
import module.database.Conn;

import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookCategoryRepository {
    public boolean create(String name) {
        try {
            Conn instance = Conn.getInstance();
            Connection conn = instance.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO book_categories (name) VALUES (?)");
            stmt.setString(1, name);
            stmt.executeUpdate();
            instance.closeConnection(conn);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<BookCategory> getAll(String name) {
        List<BookCategory> categories = new ArrayList<>();
        try {
            Conn instance = Conn.getInstance();
            Connection conn = instance.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book_categories");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BookCategory category = new BookCategory(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                categories.add(category);
            }
            instance.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public BookCategory getById(int id) {
        try {
            Conn instance = Conn.getInstance();
            Connection conn = instance.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book_categories WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BookCategory category = new BookCategory(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                return category;
            }
            instance.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(BookCategory category) {
        try {
            Conn instance = Conn.getInstance();
            Connection conn = instance.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE book_categories SET name = ? WHERE id = ?");
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            stmt.executeUpdate();
            instance.closeConnection(conn);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            Conn instance = Conn.getInstance();
            Connection conn = instance.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM book_categories WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            instance.closeConnection(conn);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
