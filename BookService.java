
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
/**
 * This class handles the CRUD operations for books in the library database
 * the service first establishes a connection and then generates a table
 * */
public class BookService {
    private final Connection serviceConnection;
    PreparedStatement insertStatement;


    public BookService(Connection connection) throws SQLException {
        this.serviceConnection = connection;
        Statement statement = serviceConnection.createStatement();
        statement.executeUpdate("CREATE TABLE Book (ISBN varchar(255), Title varchar(255), Author varchar(255), Genre varchar(255), primary key(ISBN) )");
        insertStatement = serviceConnection.prepareStatement("insert into Book (ISBN, Title, Author, Genre) values(?,?,?,?)");

    }

    public Book getByIsbn(final String isbn) throws SQLException {
        PreparedStatement pstmnt = serviceConnection.prepareStatement("select * from Book where ISBN = ?");
        pstmnt.setString(1,isbn);

        ResultSet rset = pstmnt.executeQuery();

        if (!rset.next()) {
            return null;
        }
        return new Book(rset.getString(1),rset.getString(2),rset.getString(3), rset.getString(4));


    }
//
    public Collection<Book> getByAuthor(final String author) throws SQLException {
        Collection<Book> books = new ArrayList<>();
        PreparedStatement pstmnt = serviceConnection.prepareStatement("Select * from Book where Author = ?");
        pstmnt.setString(1,author);

        ResultSet rset = pstmnt.executeQuery();

        while (rset.next()) {
            books.add(new Book(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4)));
        }

        return books;


    }

    public Collection<Book> getByGenre(String genre) throws SQLException {
        Collection<Book> books = new ArrayList<>();
        PreparedStatement pstmnt = serviceConnection.prepareStatement("Select * from Book where genre = ?");
        pstmnt.setString(1,genre);

        ResultSet rset = pstmnt.executeQuery();

        if (rset.next()) {
            do {
                books.add(new Book(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4)));
            } while (rset.next());
            return books;
        }

        return null;

    }

    public void createBook(Book book) throws SQLException { // create book based on ISBN of book
        insertStatement.setString(1, book.getIsbn());
        insertStatement.setString(2, book.getTitle());
        insertStatement.setString(3, book.getAuthor());
        insertStatement.setString(4, book.getGenre());

        insertStatement.executeUpdate();

    }

    public void updateBook(Book book) throws SQLException { // update book based on ISBN of book
        PreparedStatement updateStatement = serviceConnection.prepareStatement("Update Book set Title = ?, Author = ?, Genre = ? where ISBN = ?");
        updateStatement.setString(1, book.getTitle());
        updateStatement.setString(2,book.getAuthor());
        updateStatement.setString(3, book.getGenre());
        updateStatement.setString(4, book.getIsbn());

        updateStatement.executeUpdate();

    }

    public void deleteBook(Book book) throws SQLException { // delete book based on ISBN of book
        PreparedStatement executeStatement = serviceConnection.prepareStatement("delete from Book where ISBN = ?");
        executeStatement.setString(1, book.getIsbn());
        executeStatement.execute();
    }
}
